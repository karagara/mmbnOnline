package View;

import Shared.UserCommands;


/**
 * @author Yan Liu and Colten Normore
 * <p>
 * Interface for the View class used by the Model. 
 * The View should be able to send actions the Model and to receive information about the model.
 */
public interface ViewInterface {
	/**
	 * getUserInput is used by the Model to grab an object representing user inputs (InputCommands) from the view. 
	 * @return a list of inputs in the form of a UserCommand
	 */
	public UserCommands getUserInput();
	/**
	 * Used to notify the view that the Model has changed and that it can draw the new updates
	 */
	public void updateView();
}
