package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Shared.SpriteInterface;

/* This class represent the back ground of the game.Use the sprite for display the animation of the back ground.
 */
public class Background implements SpriteInterface {

	private BufferedImage bgSpriteSheet;
	private BufferedImage currentBg;
	private int aniNumber;
	
	public Background(){
		try{
			bgSpriteSheet = ImageIO.read(new File("./src/Img/battleScreen/ovenbg.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		currentBg = bgSpriteSheet.getSubimage(8, 8, 32, 32);
	}
	
	public void update(){
		aniNumber++;
		if (aniNumber > 54){
			aniNumber = 0;
		}
		currentBg = bgSpriteSheet.getSubimage(((aniNumber % 11)*40)+8, ((aniNumber / 11)*40)+8, 32, 32 );
	}
	@Override
	public void paintRequest(Graphics2D clone) {
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 6; j++){
				clone.drawImage(currentBg, i*32, j*32, null);
			}
		}

	}

}
