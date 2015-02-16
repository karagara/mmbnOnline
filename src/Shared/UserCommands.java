package Shared;

/**
 * @author Colten Normoer and Yan Liu
 *
 *The UserCommands object is a collection of Booleans and enumerations 
 *that the View updates and the Model uses to update its internal states. 
 *It contains a enumeration representing a direction, and Booleans representing whether or not 
 *A, B (translated from a keystroke of s by the ButtonListener), or space is pressed.
 */

public class UserCommands {
	public enum Direction {none, up, down, left, right};
	private Direction currentDirection;
	private boolean isAPressed;
	private boolean isBPressed;
	private boolean isSpacePressed;
	private boolean isEnterPressed;
	private boolean isRPressed;
	/**
	 * constructor for UserCommands. All fields are set to false or none.
	 */
	public UserCommands(){
		this.currentDirection = Direction.none;
		this.isAPressed = false;
		this.isBPressed = false;
		this.isSpacePressed = false;
		this.isEnterPressed = false;
		this.isRPressed = false;
	}
	
	/**
	 * constructor for UserCommands. All fields are set to the values passed to them.
	 */
	public UserCommands(Direction direction, boolean aPress, boolean bPress, boolean spacePress,boolean enterPress,boolean rPress){
		this.currentDirection = direction;
		this.isAPressed = aPress;
		this.isBPressed = bPress;
		this.isSpacePressed = spacePress;
		this.isEnterPressed = enterPress;
		this.isRPressed = rPress;
	}
	
	/*
	 * A list of setters and getters for the UserCommands object
	 */
	public Direction getDirection(){
		return currentDirection;
	}
	public boolean getAPressed(){
		return isAPressed;
	}
	public boolean getBPressed(){
		return isBPressed;
	}
	public boolean getSpacePressed(){
		return isSpacePressed;
	}
	public boolean getEnterPressed(){
		return isEnterPressed;
	}
	public boolean getRPressed(){
		return isRPressed;
	}
	public void setDirection(Direction direction){
		currentDirection = direction;
	}
	public void setAPressed(boolean isPressed){
		isAPressed = isPressed;
	}
	public void setBPressed(boolean isPressed){
		isBPressed = isPressed;
	}
	public void setSpacePressed(boolean isPressed){
		isSpacePressed = isPressed;
	}
	public void setEnterPressed(boolean isPressed){
		isEnterPressed = isPressed;
	}
	public void setRPressed(boolean isPressed){
		isRPressed = isPressed;
	}
	public boolean isPlayerMove(){
		if( currentDirection == Direction.up || currentDirection == Direction.down)
			return true;
		return false;	
	}
}
