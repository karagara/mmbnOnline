package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Shared.SpriteInterface;
import Shared.UserCommands.Direction;

/* This class represent the tile object, it self is predefined the owner and state. The owner and state will be changed depend on the specific Chip function.
 * It has the x and y position. It also have the function to check the next tile that the enemy or player move to is valid or not valid. And It can check whether
 * the enemy and player can move. The tile owner will be set in the constructor depend on the x and y position of the tile.Futhermore, the difficulty of the game 
 * can be selected. So the tile owner can be selected .
 */

public class Tile implements SpriteInterface {
    enum tileOwner {player, enemy};
	enum tileState {Normal,Crumbled,Damaged};
	private BufferedImage currentSprite;
	private BufferedImage spriteSheet;
	private BufferedImage breakSheet;
	private BufferedImage currentBreak;
	private BufferedImage normalSheet;
	private BufferedImage currentNormal;
	private int xIndex;
	private int yIndex;
	private tileOwner originalOwner;
	private tileOwner currentOwner;
	private tileState originalState;
	private tileState currentState;
	private GameBoard currentGameboard;
	private boolean timeToRecovery;
	private boolean isBreak;
	private boolean isTileGrab;
	private int breakXPos;
	private int breakYPos;
	private boolean isFilled;
	public Tile(int xIndex, int yIndex, GameBoard currentGameboard ){
		assert xIndex <= 5 && xIndex >= 0;
		assert yIndex <= 2 && yIndex >= 0;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.currentGameboard = currentGameboard;
		originalState = currentState = tileState.Normal;
		if (xIndex <= 2) originalOwner = currentOwner = tileOwner.player;
		else originalOwner = currentOwner = tileOwner.enemy;		
		try {
			spriteSheet = ImageIO.read(new File("./src/Img/bTilesV1.png"));
			breakSheet = ImageIO.read(new File("./src/Img/breakTilesV1.png"));
			normalSheet = ImageIO.read(new File("./src/Img/tilebreak/normaltile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		if (spriteSheet != null){
			switch (currentOwner){
			case player:
				currentSprite = spriteSheet.getSubimage(0, yIndex*32, 40, 32);
				break;
			case enemy:
				currentSprite = spriteSheet.getSubimage(40, yIndex*32, 40, 32);
				break;
			}
		}
		this.isBreak = false;
		this.timeToRecovery = true;
		this.isTileGrab = false;
		this.isFilled = false;
	}
	
	public void update(int xPos, int yPos){

		breakXPos = xPos;
		breakYPos = yPos;
		isBreak = true;
//		switch (currentOwner){
//		case player:
//			if(isBreak)
//				currentSprite = breakSheet.getSubimage(0, yIndex*32, 40, 32);
//			else
//				currentSprite = spriteSheet.getSubimage(0, yIndex*32, 40, 32);
//			break;
//		case enemy:
//			if(isBreak)
//				currentSprite = breakSheet.getSubimage(40, yIndex*32, 40, 32);
//			else
//				currentSprite = spriteSheet.getSubimage(40, yIndex*32, 40, 32);
//			break;
//		}
	}
	
	public void updateOwner(int xPos){
		if (xIndex <= xPos) originalOwner = currentOwner = tileOwner.player;
		else originalOwner = currentOwner = tileOwner.enemy;	
		if (spriteSheet != null){
			switch (currentOwner){
			case player:
				if(isBreak)
					currentSprite = breakSheet.getSubimage(0, yIndex*32, 40, 32);
				else
					currentSprite = spriteSheet.getSubimage(0, yIndex*32, 40, 32);
				break;
			case enemy:
				if(isBreak)
					currentSprite = breakSheet.getSubimage(40, yIndex*32, 40, 32);
				else
					currentSprite = spriteSheet.getSubimage(40, yIndex*32, 40, 32);
				break;
			}
		}
		this.isTileGrab = true;
	}
	
	public boolean isFilled(){
		return isFilled;
	}
	
	public void setIsFilled(boolean isFilled){
		this.isFilled = isFilled;
	}
	
	public boolean isTileGrab(){
		return this.isTileGrab;
	}
	
	public void setIsGrab(boolean isGrab){
		this.isTileGrab = isGrab;
	}
	
	public boolean isTileBreak(){
		return isBreak;
	}
	
	public void setIsBreak(boolean isBreak){
		this.isBreak = isBreak;
	}
	
	//Accessors and Multators of the tilestate,tileOwner,x and y position
	public tileState getCurrentState(){
		return currentState;
	}
	
	public tileState getOriginalState(){
		return originalState;
	}
	
	public void setCurrentState(tileState currentState){
		this.currentState = currentState;
	}
	
	public tileOwner getCurrentOwner(){
		return currentOwner;
	}
	
	public void setCurrentOwner(tileOwner owner){
		currentOwner = owner;
	}
	
	public tileOwner getOriginalOwner(){
		return originalOwner;
	}
	
	public int getxIndex(){
		return xIndex;
	}
	
	public int getyIndex(){
		return yIndex;
	}
	
	public void setXIndex(int xIndex){
		this.xIndex = xIndex;
	}
	
	public void setYIndex(int yIndex){
		this.yIndex = yIndex;
	}
	
	// check the next movement of the player or enemy is valid or not valid
	public boolean checkMovement(int xPos, int yPos, Direction direction){
		switch(direction){
		case up:
			if (yPos == 0 || checkTileValid(xPos, yPos-1 ) == false ){
				return false;
			} else {
				return true;
			}
		case right:
			if (xPos == 5 || checkTileValid(xPos+1 , yPos) == false ){
				return false;
			} else {
				return true;
			}
		case down:
			if (yPos == 2 || checkTileValid(xPos, yPos+1) == false ){
				return false;
			} else {
				return true;
			}
		case left:
			if (xPos == 0 || checkTileValid(xPos-1,yPos) == false ){
				return false;
			} else {
				return true;
			}
		default:
			return false;
		}
	}
	
	public boolean checkEnemyMove(int xPos, int yPos){
		if( checkTileValid( xPos, yPos) == true){
			return true;
		}
		return false;
	}
	
	private boolean checkTileValid(int xPos, int yPos) {
		Tile checkTile = currentGameboard.getTile(xPos, yPos);
		if ( checkTile.getCurrentOwner() == tileOwner.player /*&& checkTile.getCurrentState().equals(tileState.Normal)*/ ){
			return true;
		}
		return false;
	}
	
	public void paintRequest(Graphics2D clone) {
		  
//		 if( isBreak == true )
//		    clone.drawImage(currentBreak, 40 * breakXPos,  72 + 24 * breakYPos, null);
//		 if( isBreak == false)
		switch(currentOwner){
			case player:
				if(isBreak)
					currentSprite = breakSheet.getSubimage(0, yIndex*32, 40, 32);
				else
					currentSprite = spriteSheet.getSubimage(0, yIndex*32, 40, 32);
				break;
			case enemy:
				if(isBreak)
					currentSprite = breakSheet.getSubimage(40, yIndex*32, 40, 32);
				else
					currentSprite = spriteSheet.getSubimage(40, yIndex*32, 40, 32);
				break;
		}
		clone.drawImage(currentSprite, 40*xIndex, 73+24*yIndex, null);	
		 
	}
	
}
