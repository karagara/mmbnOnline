package Model;

import java.util.ArrayList;

import Shared.SpriteInterface;

/*
 * @author Yan Liu and Colten Normore
 * 
 * Interface for the Model class used by the View. 
 * The Model should respond to input commands from the View, 
 * and be able to provide information to the View about the state of a needed element.
 */

public interface ModelInterface {
	/*
	 * Create a list of objects that can be painted by the Painter class, in the order that they are supposed to be rendered
	 * 
	 * @returns a List of SpriteInterfaces to be drawn
	 */
	public ArrayList<SpriteInterface> getSprites();
}
