package View;

import java.awt.event.KeyEvent;

import Shared.UserCommands;
import Shared.UserCommands.Direction;
/**
 * @author Yan Liu and Colten Normore
 * The ButtonListener receives inputs from the View and translates those events into
 * enumerated and Boolean values that are stored in its UserCommand object. 
 * The ButtonListener is capable of sending these UserCommands on request by use of a getter function.
 * 
 */
public class ButtonListener {
	

	private UserCommands inputs;
	
	/**
	 * Constructor for the ButtonListener.
	 */
	public ButtonListener(){
		this.inputs = new UserCommands();
		
	}

	/**
	 * getter function for the UserCommands object
	 * @return a list of detected inputs in the form of a UserCommands object
	 */
	public UserCommands getInputs(){
		return inputs;
	}
	
/**
 * Responds to events passed to it by the View, and changes the variables within it's UserCommands object
 * @param e The event passed to the ButtonListener
 */
		public void keyPressed(KeyEvent e) {
        	int keycode = e.getKeyCode();
			switch (keycode) {
            case KeyEvent.VK_LEFT:
            	inputs.setDirection(Direction.left);
                break;
            case KeyEvent.VK_RIGHT:
            	inputs.setDirection(Direction.right);
                break;
            case KeyEvent.VK_DOWN:
            	inputs.setDirection(Direction.down);
                break;
            case KeyEvent.VK_UP:
            	inputs.setDirection(Direction.up);
                break;
            case KeyEvent.VK_SPACE:
            	inputs.setSpacePressed(true);
                break;
            case KeyEvent.VK_ENTER:
            	inputs.setEnterPressed(true);
            	break;
            case 'r':
            	inputs.setRPressed(true);
            	inputs.setAPressed(false);
            	inputs.setBPressed(false);
            	break;
            case 'R':
            	inputs.setRPressed(true);
            	inputs.setAPressed(false);
            	inputs.setBPressed(false);
            	break;
            case 'a':
            	inputs.setBPressed(false);
            	inputs.setRPressed(false);
            	inputs.setAPressed(true);
                break;
            case 'A':
            	inputs.setBPressed(false);
            	inputs.setAPressed(true);
            	inputs.setRPressed(false);
                break;
            case 's':
            	inputs.setAPressed(false);
            	inputs.setBPressed(true);
            	inputs.setRPressed(false);
                break;
            case 'S':
            	inputs.setAPressed(false);
            	inputs.setBPressed(true);
            	inputs.setRPressed(false);
                break;
            }
        }
		
/**
* Responds to events passed to it by the View, and changes the variables within it's UserCommands object
* @param e The event passed to the ButtonListener
*/
		public void keyReleased(KeyEvent e) {
			int keycode = e.getKeyCode();
			switch (keycode) {
            case KeyEvent.VK_LEFT:
            	inputs.setDirection(Direction.none);
                break;
            case KeyEvent.VK_RIGHT:
            	inputs.setDirection(Direction.none);
                break;
            case KeyEvent.VK_DOWN:
            	inputs.setDirection(Direction.none);
                break;
            case KeyEvent.VK_UP:
            	inputs.setDirection(Direction.none);
                break;
            case KeyEvent.VK_SPACE:
            	inputs.setSpacePressed(false);
                break;
            case KeyEvent.VK_ENTER:
                inputs.setEnterPressed(false);
                break;
            case 'a':
            	inputs.setAPressed(false);
                break;
            case 'A':
            	inputs.setAPressed(false);
                break;
            case 's':
            	inputs.setBPressed(false);
                break;
            case 'S':
            	inputs.setBPressed(false);
                break;
            case 'r':
            	inputs.setRPressed(false);
            	break;
            case 'R':
            	inputs.setRPressed(false);
            	break;
            }
        }	
}
