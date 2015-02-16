package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/* This is the one Cannon Chip that implement the chip interface¡£¡¡
 * The damage and some other constant is defined in the constructor. 
 */
public class CannonChip implements ChipInterface {

	enum cannonState {firing, explosion}
	private cannonState currentState;
	private BufferedImage attackSpriteSheet;
	private BufferedImage currentAttack;
	private BufferedImage megamanSpriteSheet;
//	private BufferedImage currentMegaman;
	private BufferedImage largeMenuIcon;
	private BufferedImage smallMenuIcon;
	private BufferedImage smallLetter;
	private BufferedImage largeLetter;
	private BufferedImage damageValue;
	private BufferedImage particleSpriteSheet;
	private BufferedImage currentParticle;
	private int range;
	private int aniNumber;
	private int startXPos;
	private int startYPos;
	private int hitXPos;
	private int hitYPos;
	private int damage;
    private String letter;
    private String type;
	private boolean isExpired;
	private boolean isParticle;
	private final int CANNONDONE = 15;
	private final int CANNONHITFRAME = 14;
	
	private GameBoard gameBoard;
	
	public CannonChip(GameBoard gameBoard){
		this.damage = 25;
		this.letter = "A";
		this.type = "cannon";
		currentState = cannonState.firing;
		aniNumber = 0;
		isExpired = false;
		isParticle = false;
		this.gameBoard = gameBoard;
		BufferedImage tempNums = null;
		
		try {
			attackSpriteSheet = ImageIO.read(new File("./src/Img/cannonsprite/cannonspriteV1.png"));
			megamanSpriteSheet = ImageIO.read(new File("./src/Img/cannonsprite/megamancannonV1.png"));
			particleSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/explosion.png"));
			largeMenuIcon = ImageIO.read(new File("./src/Img/cannonsprite/card001.gif"));
			smallMenuIcon = ImageIO.read(new File("./src/Img/cannonsprite/cannonmenuV1.png"));
			smallLetter = ImageIO.read(new File("./src/Img/cannonsprite/cannonletters.png")).getSubimage(0, 0, 6, 5);
			largeLetter = ImageIO.read(new File("./src/Img/battlemenu/alphabetV1.png")).getSubimage(7, 0, 7, 11);
			tempNums = ImageIO.read(new File("./src/Img/mHealthV3.png")); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		currentAttack = attackSpriteSheet.getSubimage(aniNumber*48,0,48,56);
		currentParticle = particleSpriteSheet.getSubimage(aniNumber * 60, 0, 60, 59);
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
		switch (currentState){
		case firing:
			if(aniNumber > CANNONDONE){
				isParticle = true;
				boolean enemyHit = false;
				for (int i = startXPos; i < 6; i++){
					for (int j = 0; j < gameBoard.getEnemyList().size(); j++){
						if(gameBoard.getEnemyList().get(j).getXpos() == i && gameBoard.getEnemyList().get(j).getYpos() == startYPos){
							gameBoard.getEnemyList().get(j).takeDamage(damage);
							enemyHit = true;
							hitXPos = gameBoard.getEnemyList().get(j).getXpos();
							hitYPos = gameBoard.getEnemyList().get(j).getYpos();
							break;
						}
					}
					if (enemyHit)
						break;
				}
				if (enemyHit){
					aniNumber = 0;
					currentState = cannonState.explosion;
				}else{
					isExpired = true;
					isParticle = false;
				}
				break;
			}
			currentAttack = attackSpriteSheet.getSubimage(aniNumber*48,0,48,56);
			aniNumber++;
			break;
		case explosion:
			if (aniNumber > 12){
				isExpired = true;
				break;
			}
			currentParticle = particleSpriteSheet.getSubimage(aniNumber*60,0,60,59);
			aniNumber++;
			break;
		}
//		if(aniNumber > CANNONDONE){
//			isParticle = true;
//		}
//		if(aniNumber == CANNONHITFRAME){
//			boolean enemyHit = false;
//			for (int i = startXPos; i < 6; i++){
//				for (int j = 0; j < gameBoard.getEnemyList().size(); j++){
//					if(gameBoard.getEnemyList().get(j).getXpos() == i && gameBoard.getEnemyList().get(j).getYpos() == startYPos){
//						gameBoard.getEnemyList().get(j).takeDamage(damage);
//						enemyHit = true;
//						break;
//					}
//				}
//				if (enemyHit)
//					break;
//			}
//		}
//		if(!isExpired){
//			currentAttack = attackSpriteSheet.getSubimage(aniNumber*48,0,48,56);
//			aniNumber++;
//		}
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
		switch (currentState){
		case firing:
			clone.drawImage(megamanSpriteSheet, 40*startXPos + 3, 42+24*startYPos, null);
			clone.drawImage(currentAttack, 40*startXPos + 3 + 30, 32+24*startYPos, null);
			break;
		case explosion:
			clone.drawImage(currentParticle, 40 * (hitXPos) -2 , 40 + 24 * (hitYPos) , null);
			break;
		}

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
		return isParticle;
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
