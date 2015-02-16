package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Shared.SpriteInterface;

public class Title implements SpriteInterface{
    
	private BufferedImage titleSpriteSheet;
	private BufferedImage startSpriteSheet;
	private BufferedImage megamanTitleSpriteSheet;
	private BufferedImage currentTitle;
	private int aniNumber;
	private int timer;
	public Title(){
		aniNumber = 0;
		timer = 0;
		try {
			titleSpriteSheet = ImageIO.read(new File("./src/Img/titlemenu/title.png"));
			megamanTitleSpriteSheet = ImageIO.read(new File("./src/Img/titlemenu/MegamanTitle.png"));
			startSpriteSheet = ImageIO.read(new File("./src/Img/titlemenu/start.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentTitle = titleSpriteSheet.getSubimage(0, 0, 240, 159);
	}
	
	public void update(){
		aniNumber++;
		if( aniNumber > 9){
			aniNumber = 0;
		}
		currentTitle = titleSpriteSheet.getSubimage((((aniNumber) % 4) * 248), (((aniNumber) / 4) * 168) + 672, 240,160);
		if( timer < 25)
			timer++;
		else
			timer = 0;
	}
	
	public void paintRequest(Graphics2D clone) {
		clone.drawImage(currentTitle, 0, 0, null);
		clone.drawImage(megamanTitleSpriteSheet, -39, 5, null);
		if( timer > 10)
           clone.drawImage(startSpriteSheet,73,100,null);
	}
   
}
