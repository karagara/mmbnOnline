package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SwordChip implements ChipInterface {

	private BufferedImage attackSpriteSheet;
	private BufferedImage currentAttack;
	private BufferedImage megamanSpriteSheet;
	private BufferedImage currentMegaman;
	private BufferedImage particleSpriteSheet;
	private BufferedImage currentParticle;
	
	//menu stuff
	private BufferedImage largeMenuIcon;
	private BufferedImage smallMenuIcon;
	private BufferedImage smallLetter;
	private BufferedImage largeLetter;
	private BufferedImage damageValue;
	
	private int range;
	private int aniNumber;
	private int startXPos;
	private int startYPos;
	private int damage;
    private String letter;
    private String type;
	private boolean isExpired;
	private final int SWORDEXPIRE = 6;
	private final int SWORDHITFRAME = 3;
	
	private GameBoard gameBoard;
	
	public SwordChip(GameBoard gameBoard){
		this.damage = 80;
		this.letter = "L";
		this.type = "sword";
		
		aniNumber = 0;
		isExpired = false;
		this.gameBoard = gameBoard;
		BufferedImage tempNums = null;
		
		try {
			attackSpriteSheet = ImageIO.read(new File("./src/Img/swordsprite/swordspriteV1.png"));
			megamanSpriteSheet = ImageIO.read(new File("./src/Img/swordsprite/megamanswordV1.png"));
			particleSpriteSheet = ImageIO.read(new File("./src/Img/swordsprite/swordparticles.png"));
			largeMenuIcon = ImageIO.read(new File("./src/Img/swordsprite/card013.gif"));
			smallMenuIcon = ImageIO.read(new File("./src/Img/swordsprite/swordmenuV1.png"));
			smallLetter = ImageIO.read(new File("./src/Img/swordsprite/swordletters.png"));
			largeLetter = ImageIO.read(new File("./src/Img/battlemenu/alphabetV1.png")).getSubimage(84, 0, 7, 11);
			tempNums = ImageIO.read(new File("./src/Img/mHealthV3.png")); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		currentMegaman = megamanSpriteSheet.getSubimage(aniNumber*44,0,44,48);
		currentAttack = attackSpriteSheet.getSubimage(aniNumber*71,0,71,54);
		currentParticle = particleSpriteSheet.getSubimage(aniNumber*37,0,37,22);
		damageValue = new BufferedImage(13, 11, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)damageValue.getGraphics();
		g.drawImage(tempNums.getSubimage((damage/10)*6, 0, 6, 11), 0, 0, null);
		g.drawImage(tempNums.getSubimage((damage%10)*6, 0, 6, 11), 7, 0, null);
	}
	@Override
	public int getDamage() {
		return damage;
	}
	
	@Override
	public void update() {
		if(aniNumber >= SWORDEXPIRE){
			isExpired = true;
		}
//		if(aniNumber == CANNONHITFRAME){
//			for(int i = 0; i < gameBoard.getEnemyList().size();i++){
//				if(gameBoard.getEnemyList().get(i).getYpos() == startYPos){
//					gameBoard.getEnemyList().get(i).takeDamage(damage);
//				}
//			}
//		}
		if(aniNumber == SWORDHITFRAME){
			for (int j = 0; j < gameBoard.getEnemyList().size(); j++){
				if(gameBoard.getEnemyList().get(j).getXpos() == (startXPos + 1) && gameBoard.getEnemyList().get(j).getYpos() == startYPos){
					gameBoard.getEnemyList().get(j).takeDamage(damage);
					break;
				}
			}
		}
		if(!isExpired){
			currentMegaman = megamanSpriteSheet.getSubimage(aniNumber*44,0,44,48);
			currentAttack = attackSpriteSheet.getSubimage((aniNumber)*71,0,71,54);
			currentParticle = particleSpriteSheet.getSubimage((aniNumber)*37,0,37,22);
			aniNumber++;
		}
	}
	
	@Override
	public void startChip(int xPos, int yPos) {
		startXPos = xPos;
		startYPos = yPos;
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
	public BufferedImage getLargeMenuIcon() {
		return largeMenuIcon;
	}

	@Override
	public BufferedImage getSmallMenuIcon() {
		return smallMenuIcon;
	}
	
	@Override
	public void paintRequest(Graphics2D clone) {
		clone.drawImage(currentMegaman, 40*startXPos + 3, 42+24*startYPos, null);
		clone.drawImage(currentAttack, 40*startXPos - 1, 35+24*startYPos, null);
		clone.drawImage(currentParticle,40*(startXPos+1) + 3, 55+24*startYPos, null);
	}

	@Override
	public int getRange() {
		return range;
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
		return damageValue;
	}
}
