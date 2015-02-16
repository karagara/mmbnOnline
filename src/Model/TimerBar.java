package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Model.Player.PlayerState;
import Shared.SpriteInterface;
/* 
 * @author Colten Normore and Yan Liu
 * 
 * The TimerBar implements SpriteInterface. It is a representation of the bar displayed at the top of the screen.
 * The TimerBar changes its current sprite to an empty bar that is filled depending on the value of the integer. 
 * If it is above a certain value, the TimerBar’s current sprite alternates between three images every 5 frames. 
 * The TimerBar’s paint request merely displays the current sprite.
 */
public class TimerBar implements SpriteInterface {

	private BufferedImage currentSprite;
	private BufferedImage spriteSheet;
	private BufferedImage barFill;
	private int timerNumber;
	private int aniNumber;
	private boolean isDone;
	
	
	/*
	 * Constructor for the TimerBar. Loads in spritesheets and sets interal states to 0.
	 */
	public TimerBar(){
		
		try {
			spriteSheet = ImageIO.read(new File("./src/Img/timerbarV1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currentSprite = spriteSheet.getSubimage(0, 16, 142, 16);
		barFill = spriteSheet.getSubimage(0,0,1,16);
		aniNumber = 0;
		timerNumber = 0;
		isDone = false;
	}
	
	/* public void resetTimer()
	 * Resets the Timer class to its orginal state
	 */
	public void resetTimer(){
		timerNumber = 0;
		aniNumber = 0;
		isDone = false;
		currentSprite = spriteSheet.getSubimage(0, 16, 142, 16);
	}
	
	/* public boolean isTimerFull()
	 * getter function for isDone
	 */
	public boolean isTimerFull(){
		return isDone;
	}
	
	/* public void update()
	 * calls the TimerBar to update itself. This function updates the state and the sprite
	 */
	public void update(){
		if(!isDone){
			timerNumber ++;
			if (timerNumber >= 256)
				isDone = true;
		}else{
			aniNumber++;
			if (aniNumber > 14 ) aniNumber = 0;
			currentSprite = spriteSheet.getSubimage(0, 32 + (aniNumber/5)*16, 142, 16);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see Shared.SpriteInterface#paintRequest(java.awt.Graphics2D)
	 */
	@Override
	public void paintRequest(Graphics2D clone) {
		// TODO Auto-generated method stub
		if (!isDone){
			clone.drawImage(currentSprite, 52, 0, null);
			for (int i = 0; i < timerNumber; i++){
				clone.drawImage(barFill, 59+(i/2), 0, null);	
			}
		}else{
			clone.drawImage(currentSprite, 52, 0, null);
		}
	}

}
