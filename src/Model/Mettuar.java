package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Model.Canodumb.CanodumbState;
import Model.Tile.tileOwner;
import Model.Tile.tileState;
import Shared.SpriteInterface;
import Shared.UserCommands.Direction;

public class Mettuar implements EnemyInterface {

	enum MettuarState {
		nothing, found, moving, firing, attackSearch, dead
	}

	// Personal Mettuar attributes
	private int health;
	private int xPos;
	private int yPos;
	private int attackXPos;
	private int attackYPos;
	private boolean isDead;
	private MettuarState currentState;
	private EnemyHealth enemyHealth;
	private ArrayList<Tile> tiles;
	GameBoard gameBoard;
    private boolean isLock;
	private int aniNumber;
	private BufferedImage currentEnemy;
	private BufferedImage enemySpriteSheet;
	private BufferedImage currentAttack;
	private BufferedImage attackSpriteSheet;
	private BufferedImage deadSpriteSheet;
	private BufferedImage currentDead;
	private BufferedImage enemyAttackSpriteSheet;
	private BufferedImage currentEnemyAttack;
	private static int mettaurCount = 0;
	private static int mettaurTurn = 0;
	private int mettaurID = 0;
	private static final int METTUARDAMAGE = 10;

	public Mettuar(int health, int xPos, int yPos, ArrayList<Tile> tiles, GameBoard currentGameBoard) {
		this.isLock = false;
		this.health = health;
		this.xPos = xPos;
		this.yPos = yPos;
		this.tiles = tiles;
		this.gameBoard = currentGameBoard;
		this.enemyHealth = new EnemyHealth(this);
		mettaurID = getMettaurCount();
		setMettaurCount(getMettaurCount() + 1);
		try {
			enemySpriteSheet = ImageIO.read(new File("./src/Img/Mettuar/enemyMettaur.png")); 
			enemyAttackSpriteSheet = ImageIO.read(new File("./src/Img/Mettuar/MettaurV1.png"));
			attackSpriteSheet = ImageIO.read(new File("./src/Img/Mettuar/attack.png"));
			deadSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentEnemy = enemySpriteSheet.getSubimage(0, 0, 35, 36);
		currentAttack = attackSpriteSheet.getSubimage(0, 0, 60, 70);
		currentEnemyAttack = enemyAttackSpriteSheet.getSubimage(0, 0, 60,46);
		currentDead = deadSpriteSheet.getSubimage(0, 0, 60, 59);
		this.isDead = false;
		this.aniNumber = 0;
		this.currentState = MettuarState.nothing;
		this.attackXPos = xPos;
		this.attackYPos = yPos;
	}

	private static int getMettaurCount(){
		return mettaurCount;
	}
	private static void setMettaurCount(int mc){
		mettaurCount = mc;
	}
	private static int getMettaurTurn(){
		return mettaurTurn;
	}
	private static void setMettaurTurn(int mt){
		mettaurTurn = mt;
	}
	
	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void setXpos(int xPos) {
		this.xPos = xPos;
	}

	@Override
	public int getXpos() {
		return this.xPos;
	}

	@Override
	public int getYpos() {
		return this.yPos;
	}

	@Override
	public void setYpos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public SpriteInterface getHealthSprite() {
		return enemyHealth;
	}

	@Override
	public void update() {
		updateState();
		updateSprite();
		enemyHealth.update();
	}

	@Override
	public void updateSprite() {
		switch (currentState) {
		case nothing:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 35, 36);
			currentEnemyAttack = enemyAttackSpriteSheet.getSubimage(0, 0, 60,46);
			break;
		case moving:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 35, 36);
			currentEnemyAttack = enemyAttackSpriteSheet.getSubimage(0, 0, 60,46);
			break;
		case found:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 35, 36);
			break;
		case firing:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 35, 36);
			currentEnemyAttack = enemyAttackSpriteSheet.getSubimage(aniNumber * 60, 0, 40, 46);
			break;
		case attackSearch:
			currentAttack = attackSpriteSheet.getSubimage(0, 0, 60, 70);
		}
	}

	@Override
	public void updateState() {
//		if(getMettaurTurn() == mettaurID){
		switch (currentState) {
		case nothing:
     		int diff = yPos - gameBoard.getPlayer().getYpos();
     		if(diff != 0){
     			Direction tempDirection = Direction.none;
     			if(diff < 0)
     				tempDirection = Direction.down;
     			else if(diff > 0)
     				tempDirection = Direction.up;
     		
     			if(gameBoard.isValidMove(xPos, yPos, tempDirection, tileOwner.enemy)){
    				if(yPos != 0 && diff < 0){
    					yPos = yPos + 1;
    				}
					if(yPos != 2 && diff > 0 ){
						yPos = yPos - 1;
					}
					if(yPos == 0 && diff < 0 ){
						yPos = yPos + 1;
					}
					if(yPos == 2 && diff > 0){
						yPos = yPos - 1;
					}
					
					currentState = MettuarState.moving;
					aniNumber = 0;
					break;
     			}
//			if(  isLock == false && gameBoard.getTile(xPos, yPos).getCurrentState() == tileState.Normal) {
//				if(yPos != 0 && diff < 0){
//					yPos = yPos + 1;
//				}
//				if(yPos != 2 && diff > 0 ){
//					yPos = yPos - 1;
//				}
//				if(yPos == 0 && diff < 0 ){
//					yPos = yPos + 1;
//				}
//				if(yPos == 2 && diff > 0){
//					yPos = yPos - 1;
//				}
//			}
     		}
 			if (this.gameBoard.getPlayer().getYpos() == this.yPos) {
 				this.currentState = MettuarState.found;
 				this.aniNumber = 0;
 				this.attackXPos = xPos;
 				this.attackYPos = yPos;
 				break;
 			}
			break;
		case moving:
			aniNumber++;
			if (aniNumber > 32){
				currentState = MettuarState.nothing;
				aniNumber = 0;
			}
			break;
		case found:
			aniNumber++;
			if (aniNumber >= 35) {// after the enemy find ,after 50 loop,moving
				aniNumber = 1;
				currentState = MettuarState.firing;
			}
			break;
		case firing:
			aniNumber++;
			if (aniNumber > 9) {
				aniNumber = 0;
				currentState = MettuarState.attackSearch;
			}
			break;
		case attackSearch:
			aniNumber++;
			if (aniNumber > 4) {
				attackXPos = attackXPos - 1;
				aniNumber = 0;
			}
			if (attackXPos < 0) {
				aniNumber = 0;
				attackXPos = xPos;
				currentState = MettuarState.nothing;
			}
			if (attackXPos == gameBoard.getPlayer().getXpos() && attackYPos == gameBoard.getPlayer().getYpos()) {
				gameBoard.getPlayer().takeDamage(METTUARDAMAGE);
				currentState = MettuarState.moving;
//				setMettaurTurn(getMettaurTurn() + 1);
//					if(getMettaurTurn() > getMettaurCount()){
//						setMettaurTurn(0);
//					}
			}
			break;
		}
//		}
	}

	@Override
	public void takeDamage(int damage) {
		this.health = this.health - damage;
		if (this.health <= 0) {
			this.health = 0;
			currentState = MettuarState.dead;
			isDead = true;
		}
	}

	@Override
	public void paintRequest(Graphics2D clone) {
		switch (currentState) {
		case nothing:
			clone.drawImage(currentEnemy, 40 * xPos + 3, 60 + 24 * yPos, null);
			break;
		case moving:
			clone.drawImage(currentEnemy, 40 * xPos + 3, 60 + 24 * yPos, null);
			break;
		case found:
			clone.drawImage(currentEnemyAttack, 40 * xPos - 9, 43 + 24 * yPos,null);
			break;
		case firing:
			clone.drawImage(currentEnemyAttack, 40 * xPos - 9, 43 + 24 * yPos,null);
			break;
		case attackSearch:
			clone.drawImage(currentEnemy, 40 * xPos + 3, 60 + 24 * yPos, null);
			if( attackXPos < xPos)
			   clone.drawImage(currentAttack, 38 * attackXPos,45 + 24 * attackYPos, null);
			break;
		}
	}

	@Override
	public boolean getIsDead() {
		return isDead;
	}

	@Override
	public void setIsLock(boolean isLock) {
		this.isLock = isLock;
		
	}
}
