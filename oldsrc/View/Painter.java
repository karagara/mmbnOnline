package View;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Model.ModelInterface;
import Shared.SpriteInterface;

/**
 * @author Yan Liu and Colten Normore
 * <p> 
 * The Painter class is capable of sending a request to the Model to get
 * all the sprites the Model contains. It then cycles through the list of SpriteInterfaces, invoking
 * the paintRequest method on all of them.
 * 
 */

public class Painter extends JPanel {		
	private ModelInterface model;
	
	/**
	 * Constructor for the painter object. The model is not set until the View is linked with a model
	 */
	public Painter(){
		setBackground(Color.BLACK);
		
		setDoubleBuffered(true);
	}
	
	/**
	 * Links the Painter class with a ModelInterface
	 * @param model the instance of the Model that the Painter will be linked with.
	 */
	public void setModel(ModelInterface model){
		this.model = model;
	}
	
//	@override
	/**
	 * Once invoked, the painter will grab the relavent sprite objects from the Model
	 * and begin drawing them in the JFrame.
	 * @param g the graphics context that the painter should use.
	 */
	protected void paintComponent(Graphics g){

		
        Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
        
		for(SpriteInterface pc : model.getSprites()) {
			Graphics2D clone = (Graphics2D) g2d.create() ;
			pc.paintRequest(clone) ;
			clone.dispose() ; 
		} 
        
	}
	/**
	 * Tells the Painter object to repaint itself.
	 */
	public void updateView(){
		repaint();
	}
}
