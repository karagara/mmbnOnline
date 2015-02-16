package Shared;

import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.Toolkit;

import javax.swing.ImageIcon;

/*
 * A temporary class used to test the SpriteInterface, and to test how objects were going to be drawn to the screen.
 */

public class tempSprite implements SpriteInterface {

	Image tileTest;
	
	public tempSprite(){
        ImageIcon ii = new ImageIcon(this.getClass().getResource("../img/tileTest.png"));
        tileTest = ii.getImage();
	}
	
	@Override
	public void paintRequest(Graphics2D clone) {
        clone.drawImage(tileTest, 0, 0, null);
//        Toolkit.getDefaultToolkit().sync();
	}

}
