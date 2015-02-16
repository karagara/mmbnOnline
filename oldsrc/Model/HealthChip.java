package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HealthChip implements ChipInterface {
	
	//private BufferedImage currentHealthSprite;
	private BufferedImage megamanSpriteSheet;
	private BufferedImage largeMenuIcon;
	private BufferedImage smallMenuIcon;
	private BufferedImage currentMegaman;
	private BufferedImage smallLetter;
	private BufferedImage largeLetter;
	private BufferedImage damageValue;
	private int range;
	private int aniNumber;
	private int startXPos;
	private int startYPos;
	private int healthIncrease;
    private String letter;
    private String type;
	private GameBoard gameBoard;
	private boolean isExpired;
	private final int HEALTHFRAME = 12;
	
	public HealthChip(GameBoard gameBoard){
		this.healthIncrease = 10;
		this.letter = "A";
		this.type = "health10";	
		aniNumber = 0;
		isExpired = false;
		this.gameBoard = gameBoard;
		
		try {
			megamanSpriteSheet = ImageIO.read(new File("./src/Img/healthsprite/megamanhealth.png"));
			largeMenuIcon = ImageIO.read(new File("./src/Img/healthsprite/card069.gif"));
			smallMenuIcon = ImageIO.read(new File("./src/Img/healthsprite/smallicon.png"));
			smallLetter = ImageIO.read(new File("./src/Img/cannonsprite/cannonletters.png")).getSubimage(0, 0, 6, 5);
			largeLetter = ImageIO.read(new File("./src/Img/battlemenu/alphabetV1.png")).getSubimage(7, 0, 7, 11);

		} catch (IOException e) {
			e.printStackTrace();
		}
		currentMegaman = megamanSpriteSheet.getSubimage(0, 0, 50, 51);
	}
      

	@Override
	public int getDamage() {
		return healthIncrease;
	}

	@Override
	public int getRange() {
		return 0;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getLetter() {
		return letter;
	}

	@Override
	public boolean getExpired() {
		return isExpired;
	}

	@Override
	public void startChip(int xPos, int yPos) {
		startXPos = xPos;
		startYPos = yPos;
	}

	@Override
	public BufferedImage getLargeMenuIcon() {
		return largeMenuIcon;
	}

	@Override
	public BufferedImage getSmallMenuIcon() {
		return smallMenuIcon;
	}

	@Override
	public void update() {
		if( !isExpired){
			currentMegaman = megamanSpriteSheet.getSubimage(aniNumber * 50, 0, 50, 50);
			aniNumber++;
		}
        
		if(aniNumber > 11){
			isExpired = true;
		}

		if( aniNumber == HEALTHFRAME ){
			if( gameBoard.getPlayer().getHealth() != 100)
			   gameBoard.getPlayer().setHealth((gameBoard.getPlayer().getHealth() + this.healthIncrease));
		}
	}
	
	@Override
	public void paintRequest(Graphics2D clone) {
		clone.drawImage(currentMegaman, 40*startXPos + 3, 42+24*startYPos, null);
	}


	@Override
	public BufferedImage getSmallLetter() {
		// TODO Auto-generated method stub
		return smallLetter;
	}


	@Override
	public boolean getIsParticle() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public BufferedImage getLargeLetter() {
		// TODO Auto-generated method stub
		return largeLetter;
	}


	@Override
	public BufferedImage getDamageValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
