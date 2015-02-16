package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Shared.SpriteInterface;

/* This is the class represent the enemy Health that display the two number of the health.
 * The position of the display is under the current enemy.
 */
public class EnemyHealth implements SpriteInterface {

	private BufferedImage healthSpriteSheet;
	private BufferedImage currentHealth1;
	private BufferedImage currentHealth2;
	private BufferedImage currentHealth3;
	private EnemyInterface enemy;
    private int a = 0;
    private int b = 0;
	public EnemyHealth(EnemyInterface enemy) {

		this.enemy = enemy;

		try {
			healthSpriteSheet = ImageIO.read(new File("./src/Img/healthnumsV2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		update();
	}
    
	public void update() {
		a = enemy.getHealth() / 10;
		b = enemy.getHealth() % 10;
		if( a == 10){
			currentHealth1 = healthSpriteSheet.getSubimage(1 * 8, 0, 8, 13);
			currentHealth2 = healthSpriteSheet.getSubimage(0 * 8, 0, 8, 13);
			currentHealth3 = healthSpriteSheet.getSubimage(0 * 8, 0, 8, 13);
		}
		else{
		    currentHealth1 = healthSpriteSheet.getSubimage(a * 8, 0, 8, 13);
		    currentHealth2 = healthSpriteSheet.getSubimage(b * 8, 0, 8, 13);
		}
	}

	@Override
	public void paintRequest(Graphics2D clone) {
		clone.drawImage(currentHealth1, 42 * enemy.getXpos() + 2,24 * enemy.getYpos() + 85, null);
		clone.drawImage(currentHealth2, 42 * enemy.getXpos() + 10,24 * enemy.getYpos() + 85, null);
		if( a == 10){
			clone.drawImage(currentHealth3, 42 * enemy.getXpos() + 18,24 * enemy.getYpos() + 85, null);
		}
	}

}
