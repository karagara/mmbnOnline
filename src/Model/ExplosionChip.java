package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Model.Tile.tileState;

public class ExplosionChip implements ChipInterface {
	
	//Sprites for attacks
	private BufferedImage attackSpriteSheet;
	private BufferedImage currentAttack;
	private BufferedImage currentMegaman;
	private BufferedImage currentBall;
	private BufferedImage megamanSpriteSheet;
    private BufferedImage ballSpriteSheet;
    
    //Sprites for menu icons
	private BufferedImage largeMenuIcon;
	private BufferedImage smallMenuIcon;
	private BufferedImage smallLetter;
	private BufferedImage largeLetter;
	private BufferedImage damageValue;

	//Internal logic
	enum bombState{throwing, inAir, explode};
	private bombState currentState;
	private int aniNumber;
	private int startXPos;
	private int startYPos;
	private int damage;
	private String letter;
	private String type;
	private int range;
	private boolean isExpired;
	private boolean isParticle;
	private boolean isManExpired;
	private boolean isBallArrived;
	private int xArrived;
	private int yArrived;
	private int ballX;
	private int ballY;
	private int ballXSpeed;
	private int ballYSpeed;
	private int delay;
	
	//list of constants (used for logic)
//	private final int EXPLOSIONEXPIRE = 12;
//	private final int EXPLOSIONFRAME = 11;
//	private final int MEGAMANEXPIRE = 3;
	private final int YACCEL = -3;
	private final int CHIPDAMAGE = 30;
	//private final int BALLEXPIRE = 3;
	
	//pointers
	private Tile tiles;
	private GameBoard gameBoard;


	public ExplosionChip(GameBoard gameBoard) {
		this.damage = CHIPDAMAGE;
		this.letter = "B";
		this.type = "bomb";
		this.range = 3;
        this.xArrived = 0;
        this.yArrived = 0;
        this.ballX = 0;
        this.ballY = 0;
        this.ballXSpeed = 12;
        this.ballYSpeed = 12;
        this.delay = 0;
        currentState = bombState.throwing;
		aniNumber = 0;
		isExpired = false;
        isBallArrived = false;
		this.gameBoard = gameBoard;
		BufferedImage tempNums = null;
		
		try {
			attackSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/explosion.png"));
//			megamanSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/megamanexplosion.png"));
			megamanSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/mmexplosionV2.png"));
			largeMenuIcon = ImageIO.read(new File("./src/Img/explosionsprite/card009.gif"));
			smallMenuIcon = ImageIO.read(new File("./src/Img/explosionsprite/smalliconexplosion.png"));
			ballSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/ball.png"));
			smallLetter = ImageIO.read(new File("./src/Img/explosionsprite/bombletters.png")).getSubimage(0, 0, 6, 5);
			largeLetter = ImageIO.read(new File("./src/Img/battlemenu/alphabetV1.png")).getSubimage(14, 0, 7, 11);
			tempNums = ImageIO.read(new File("./src/Img/mHealthV3.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentAttack = attackSpriteSheet.getSubimage(aniNumber * 60, 0, 60, 59);
		currentMegaman = megamanSpriteSheet.getSubimage(aniNumber *47 , 0, 47, 50);
		currentBall = ballSpriteSheet.getSubimage(0 , 0, 18, 30);
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
	public int getRange() {
		return range;
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
    
	public boolean getManExpired(){
		return isManExpired;
	}
	@Override
	public void startChip(int xPos, int yPos) {
		startXPos = xPos;
		startYPos = yPos;
		ballX = xPos;
		ballY = yPos;
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
//		xArrived = gameBoard.getPlayer().getXpos() + range;
//		yArrived = gameBoard.getPlayer().getYpos();
//		if( aniNumber >= MEGAMANEXPIRE){
//			isManExpired = true;
//		}
//		if(aniNumber > EXPLOSIONEXPIRE){
//			isExpired = true;
//		}
//		if( ballX == xArrived && ballY == yArrived ){
//			isBallArrived = true;
//		}
//		if( aniNumber == EXPLOSIONFRAME ){
//			//gameBoard.getTile(startXPos + range, startYPos).setCurrentState(tileState.Damaged);
//			//System.out.println(gameBoard.getTile(startXPos + range, startYPos).getCurrentState());
//			for( int i = 0; i < gameBoard.getEnemyList().size();i++){
//				if( gameBoard.getEnemyList().get(i).getYpos() == yArrived &&  gameBoard.getEnemyList().get(i).getXpos() == xArrived ){
//					gameBoard.getEnemyList().get(i).takeDamage(damage);
//				}
//			}
//		}
//		if(!isExpired && isManExpired ){
//			currentAttack = attackSpriteSheet.getSubimage(aniNumber*60,0,60,59);
//			aniNumber++;
//		}
//		if(!isManExpired && !isExpired){
//			currentMegaman = megamanSpriteSheet.getSubimage(aniNumber *80 , 0, 80, 53);
//			aniNumber++;
//		}
//				
//		if( isManExpired  && !isExpired && !isBallArrived ){
//			currentBall = ballSpriteSheet.getSubimage(0 , 0, 18, 30);
//			ballX = ballX + 1;
//			if( ballX - startXPos == 1)
//				ballY = ballY -1;
//			if( ballX - startXPos == range)
//				ballY = ballY + 1;
//		}	
		
		switch (currentState){
		case throwing:
			System.out.println("throwing");
			if (aniNumber > 12){
				isParticle = true;
				currentState = bombState.inAir;
				aniNumber = 0;
				break;
			}
			currentMegaman = megamanSpriteSheet.getSubimage((aniNumber/3) *47 , 0, 47, 50);
			currentBall = ballSpriteSheet.getSubimage(0 , 0, 18, 45);
			aniNumber++;
			break;
		case inAir:
			System.out.println("in air");
			ballX += ballXSpeed;
			ballY += ballYSpeed;
			ballYSpeed += YACCEL;
			//sprite for the ball
			if(aniNumber > 10){
				aniNumber = 0;
				currentState = bombState.explode;
				break;
			}
			currentBall = ballSpriteSheet.getSubimage((aniNumber % 5)*18 , 0, 18, 45);
			aniNumber++;
			break;
		case explode:
			System.out.println("exploding");
			if (aniNumber == 1){
				for(int i = 0; i < gameBoard.getEnemyList().size(); i++){
					if (gameBoard.getEnemyList().get(i).getXpos() == (startXPos + 3) && gameBoard.getEnemyList().get(i).getYpos() == startYPos){
						gameBoard.getEnemyList().get(i).takeDamage(CHIPDAMAGE);
					}
				}
			}
			if (aniNumber > 12){
				isExpired = true;
				break;
			}
			currentAttack = attackSpriteSheet.getSubimage(aniNumber*60,0,60,59);
			aniNumber++;
			break;
		}
	}

	public void paintRequest(Graphics2D clone) {
//		clone.drawImage(currentMegaman, 40 * startXPos + 3, 42 + 24 * startYPos, null);
//		clone.drawImage(currentBall, 40 * ballX + 3, 42 + 24 * ballY, null);
//		clone.drawImage(currentAttack, 40 * xArrived  + 3 , 42 + 24 * yArrived , null);
		//gameBoard.getTile(startXPos + range - 1, startYPos).paintRequest(clone);
		switch (currentState){
		case throwing:
			clone.drawImage(currentMegaman, 40 * startXPos + 3, 42 + 24 * startYPos, null);
			clone.drawImage(currentBall, 40 * ballX + 3, 42 + 24 * ballY, null);
			break;
		case inAir:
			clone.drawImage(currentBall, (40 * startXPos + 3) + ballX, (42 + 24 * startYPos) - ballY, null);
			break;
		case explode:
			clone.drawImage(currentAttack, 40 * (startXPos + 3) -2 , 40 + 24 * (startYPos) , null);
			break;
		}
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
