package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Shared.SpriteInterface;

/* This is the class represent the player health. It can draw the number of the top left of the screen.
 * 
 */

public class PlayerHealth implements SpriteInterface {
	private BufferedImage healthSpriteSheet;
	private BufferedImage currentHealth1;
	private BufferedImage currentHealth2;
	private BufferedImage currentHealth3;
	private int a = 0;
	private int b = 0;
	private Player player;
    //pointer to the current player
	public PlayerHealth(Player player) {
		this.player = player;
		try{
			healthSpriteSheet = ImageIO.read(new File("./src/Img/mHealthV3.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		currentHealth1 = healthSpriteSheet.getSubimage(0 * 6, 0, 6, 11);
		currentHealth2 = healthSpriteSheet.getSubimage(0 * 6, 0, 6, 11);
		currentHealth3 = healthSpriteSheet.getSubimage(0 * 6, 0, 6, 11);
	}

	public void update() {
		a = player.getHealth() / 10;
		b = player.getHealth() % 10;
		if( a == 10 ){
			currentHealth1 = healthSpriteSheet.getSubimage(1 * 6, 0, 6, 11);
			currentHealth2 = healthSpriteSheet.getSubimage(0 * 6, 0, 6, 11);
			currentHealth3 = healthSpriteSheet.getSubimage(0 * 6, 0, 6, 11);
		}else{
		    currentHealth1 = healthSpriteSheet.getSubimage(a * 6, 0, 6, 11);
		    currentHealth2 = healthSpriteSheet.getSubimage(b * 6, 0, 6, 11);
		}
	}

	@Override
	public void paintRequest(Graphics2D clone) {
		clone.setColor(Color.gray);
		clone.fillRect(4, 2, 40, 14);
		clone.drawImage(currentHealth1, 20, 3, null);
		clone.drawImage(currentHealth2, 28, 3, null);
		if( a == 10)
		   clone.drawImage(currentHealth3, 36, 3, null);
	}

}
