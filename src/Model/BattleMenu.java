package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//import Model.Player.PlayerState;
import Shared.SpriteInterface;
import Shared.UserCommands;
import Shared.UserCommands.Direction;

/*
 * @author Colten Normore an Yan Liu
 * <p>
 * The BattleMenu implements SpriteInterface. 
 * Its constructor requires a MenuChip object, and the BattleMenu creates and has an image that represents the chip selection menu. 
 * The BattleMenu updates based on user commands. It is capable of sending notifications about these commands to the MenuChip object. 
 * For now, the BattleMenu’s paint request draws it and the MenuChip object on the canvas. 
 * Ideally it would be able to return the MenuChip object as a SpriteInterface for higher level classes to obtain.
 *
 */
public class BattleMenu implements SpriteInterface {

	private BufferedImage spriteSheet;
	//private int aniNumber;
	private int switchTime;
	private final int TRANSITION = 5;
	//private PlayerState currentState;

	private int cursorXPos;
	private int cursorYPos;
	private boolean isChipsSelected;
	//private int menuCapacity = 5;

	
	private MenuChips mChips;
//	private TotalChips tChips;
	
	/* BattleMenu(MenuChips menuChip)
	 * constructor for the BattleMenu
	 */
	public BattleMenu(MenuChips menuChip){
		
		try {
			spriteSheet = ImageIO.read(new File("./src/Img/battlemenuV1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mChips = menuChip;
		
		cursorXPos = 0;
		cursorYPos = 0;
	}
	/* public void update(UserCommands input)
	 * Updates the BattleMenu based on user inputs
	 * 
	 * @params input the list of user inputs as a UserCommands object
	 */
	public void update(UserCommands input){
		switchTime++;
		if(switchTime >= TRANSITION){
			switch(input.getDirection()){
			case up:
				cursorYPos--;
				if(cursorYPos < 0)
					cursorYPos = 1;
				break;
			case down:
				cursorYPos++;
				if(cursorYPos > 1)
					cursorYPos = 0;
				break;
			case left:
				cursorXPos--;
				if(cursorXPos < 0)
					cursorXPos = 5;
				break;
			case right:
				cursorXPos++;
				if(cursorXPos > 5)
					cursorXPos = 0;
				break;
			default:
				break;
			}
			
			if(input.getDirection() != Direction.none){
				switchTime = 0;
			}
			
			if(input.getAPressed()){
				if (cursorXPos == 5 && cursorYPos == 0){
					isChipsSelected = true;
					mChips.setBattleChips();
				}else if(cursorXPos == 5 && cursorYPos == 1){
					isChipsSelected = true;
					mChips.recyleChips();
				}
				else{
					mChips.setSelected(cursorXPos+5*cursorYPos);
				}
			}
			
			if(input.getBPressed()){
				mChips.removeQueuedChip();
			}
		}
	}
	
	/* public boolean isChipsSelected()
	 * getter function for boolean isChipsSelected
	 */
	
	public boolean isChipsSelected(){
		return isChipsSelected;
	}
	
	/* public void resetBattleMenuStates()
	 * resets the BattleMenu's cursor and sets isChipsSelected to false;
	 */
	public void resetBattleMenuStates(){
		cursorXPos = 0;
		cursorYPos = 0;
		isChipsSelected = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see Shared.SpriteInterface#paintRequest(java.awt.Graphics2D)
	 */
	@Override
	public void paintRequest(Graphics2D clone) {
		// TODO Auto-generated method stub
		clone.drawImage(spriteSheet, 0, 0, null);
		mChips.paintRequest(clone);
		
		if (cursorXPos == 5){
			clone.drawImage(mChips.getNoDataImage(), 8, 24, null);
			if(cursorYPos == 0){
				clone.setColor(Color.orange);
				clone.drawRect(89, 115, 21, 17);
			}else{
				clone.setColor(Color.orange);
				clone.drawRect(89, 138, 20, 12);
			}
		}else{
			clone.drawImage(mChips.getLargeMenuIcon(cursorXPos+5*cursorYPos), 8, 24, null);
			if(mChips.getLargeLetter(cursorXPos+5*cursorYPos) != null)
				clone.drawImage(mChips.getLargeLetter(cursorXPos+5*cursorYPos), 10, 82, null);
			if(mChips.getDamageValue(cursorXPos+5*cursorYPos) != null)
				clone.drawImage(mChips.getDamageValue(cursorXPos+5*cursorYPos), 58, 82, null);
			clone.setColor(Color.orange);
			clone.drawRect(9+(16*cursorXPos), 105+(24*cursorYPos), 13, 13);
		}
		
	}

}
