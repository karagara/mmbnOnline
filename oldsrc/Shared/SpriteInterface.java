package Shared;

import java.awt.Graphics2D;

/*
 * The SpriteInterface is what the Model and View use to communicate what needs to be drawn on the canvas. 
 * The interface contains one method: paintResquest(graphics g). Implementing this method draws a picture or shape to clone. 
 * Painter then takes all of the graphics objects and draws them to the canvas.
 */
public interface SpriteInterface {
	/*
	 * Implementing this method draws a picture or shape to clone.
	 */
	public void paintRequest(Graphics2D clone);
}
