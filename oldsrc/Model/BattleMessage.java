package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Shared.SpriteInterface;

/* This is the battle message that showing the message of the victory and lose.Only the animation depend on the current game board state
 * 
 */
public class BattleMessage implements SpriteInterface {

	private GameBoard gameBoard;
	private BufferedImage currentMessage;
	private BufferedImage messageSpriteSheet;
	//pointer for the other object
	public BattleMessage(GameBoard gameBoard){
		this.gameBoard = gameBoard;
		
		try{
			messageSpriteSheet = ImageIO.read(new File("./src/Img/battlescreen/bmessagesV2.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		currentMessage = messageSpriteSheet.getSubimage(0, 0, 133, 18);
	}
	
	public void update(){
		switch(gameBoard.getGameBoardState()){
		case slideOut:
			currentMessage = messageSpriteSheet.getSubimage(0, 0, 133, 18) ;
			break;
		case defeat:
			currentMessage = messageSpriteSheet.getSubimage(0, 36, 133, 18) ;
			break;
		case victory:
			currentMessage = messageSpriteSheet.getSubimage(0, 18, 133, 18) ;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void paintRequest(Graphics2D clone) {
		clone.drawImage(currentMessage, 56, 81, null);
	}

}
