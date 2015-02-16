package Model;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import Model.GameBoard.gameBoardState;
import Shared.SpriteInterface;
import View.ViewInterface;

/*
 * @author Colten Normore and Yan Liu
 * 
 * The Model contains a GameBoard. 
 * It also creates a timer that, when fired, is used to update the Model based on internal states and user input. 
 * The firing of the timer also tells the View that the Model has changed. 
 * 
 */

public class Model implements ModelInterface, ActionListener {

	private GameBoard gameBoard;
	private ViewInterface view;
	private Timer updateTimer;
	private boolean pauseActive;
    private int enterCount;
    private static final int WAITTIME = 1000;
    private int count;

	/*
	 * public Model() Constructor for the Model class.
	 */

	public Model() {
		initialModel();
	}

	public void update() {
		if( gameBoard.getGameBoardState() == gameBoardState.defeat || gameBoard.getGameBoardState() == gameBoardState.victory){
			if( view != null){
				if( view.getUserInput().getEnterPressed() == true ){
					updateTimer.stop();
					gameBoard.getAudioManager().stop();
					initialModel();
				}
			}
		}
		if( gameBoard.getGameBoardState() == gameBoardState.nextRound){
			for( int i = 0; i <= WAITTIME; i++){
				count++;
				if( count == WAITTIME){
					updateTimer.stop();
					gameBoard.getAudioManager().stop();
					nextRound();
				}
			}
		}
		
	}	
	
	public void nextRound(){
		this.gameBoard.reset();
		this.pauseActive = false;
		this.enterCount = 0;
		this.count = 0;
		updateTimer = new Timer(33, this);
		updateTimer.start();	
	}
	
	public void initialModel(){
		this.gameBoard = new GameBoard();
		this.pauseActive = false;
		this.enterCount = 0;
		this.count = 0;
		updateTimer = new Timer(43, this);
		updateTimer.start();	
	}


	public void setView(ViewInterface view) {
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.ModelInterface#getSprites()
	 */
	@Override
	public ArrayList<SpriteInterface> getSprites() {
		return gameBoard.getSprites();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * When the timer is fired, the model updates the gameboard and calls the
	 * view to update
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (view != null) {
			if( pauseActive == false && enterCount == 0){ 
				//System.out.println("The game is Running");
				gameBoard.update(view.getUserInput());
				view.updateView();
				this.update();
				if(view.getUserInput().getRPressed() ){
					pauseActive = true;
					view.getUserInput().setRPressed(false);
				}
			}
			if( pauseActive == true){
				enterCount = -1;
				//System.out.println("The game is Pausing");
				if( view.getUserInput().getRPressed() == true ){
				     enterCount++;
				     pauseActive = false;
				     view.getUserInput().setRPressed(false);
				}
			}
		}
	}
}
