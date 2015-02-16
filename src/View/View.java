package View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import Model.ModelInterface;
import Shared.SpriteInterface;
import Shared.UserCommands;

/**
 * @author Yan Liu and Colten Normore
 * <p> 
 * The view class is a higher level container for the objects that make up the view.
 * It communicates with the Model through a the ModelInterface. Parts of the View 
 * are responsible for sending user inputs to the Model and displaying the Model when called to.
 * <p>
 * The View contains a ButtonListener that takes in responses from the View, and a Painter that will
 * contact the Model through the View to obtain a list of SpriteInterfaces. The View also contains a 
 * private class, TAdapter used for listening to key events. 
 * 
 */
public class View extends JFrame implements ViewInterface {
	private static final long serialVersionUID = -3828747119847587146L;
	private ModelInterface model;
	private Painter painter;
	private ButtonListener buttonListener;
	
	/**
	 * Constructor for the view class. Creates all the underlying objects.
	 */
	
	public View(){
		this.painter = new Painter();
		this.buttonListener = new ButtonListener();
//		add(this.buttonListener);
		add(this.painter);
		
		addKeyListener(new TAdapter());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(246, 188);
        setLocationRelativeTo(null);
        setTitle("MMBN");
        setVisible(true);
        setResizable(false);
        
	}
	
	/**
	 * Sets the Model within the View. The Model and View need to be linked with these
	 * operations before any other action is taken.
	 * 
	 * @param ModelInterface model: the desired Model that will be linked with the View
	 */
	
	public void setModel(ModelInterface model){
		this.model = model;
		painter.setModel(model);
	}
	
	/**
	 * Method to obtain the list of SpriteInterfaces from the Model for the Painter
	 * @return ArrayList<SpriteInterface>: The list of SpriteInterfaces from the Model.
	 */
	public ArrayList<SpriteInterface> getSprites(){
		return model.getSprites();
	}
	/**
	 * Calls the painter to repaint itself 
	 */
	public void updateView(){
		painter.repaint();
	}

	/**
	 * gets the current user inputs detected by the view
	 * @return UserCommands An object representing all the relevent user commands
	 */
	@Override
	public UserCommands getUserInput() {
		// TODO Auto-generated method stub
		return buttonListener.getInputs();
	}
	
	/**
	 * @author Yan Liu and Colten Normore
	 *<p>
	 *The TAdapter class inherits from Java's key adapter. It sends relavent messages
	 *to the buttonListener so that it can properly update the UserCommands object.
	 */
	private class TAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			buttonListener.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			buttonListener.keyReleased(e);
		}
	}

}
