package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Model.Tile.tileState;
import Shared.SpriteInterface;

/* This is the Canodumb enemy. it has their own x position, y position and their health. They have their different state.
 * The searching state is for the enemy finding the player and give the player a hit. The found state showed they found the player
 * After the found state, it direct to the firing state
 */
public class Canodumb implements  EnemyInterface {

	enum CanodumbState{nothing, searching, found, firing,dead}
	private tileState currentTile;
	//Personal Canodumb attributes
	private int health;
	private int xPos;
	private int yPos;
	private int attackXPos;
	private int attackYPos;
	private boolean isDead;
	private CanodumbState currentState;
	private EnemyHealth enemyHealth;
	
	//pointers to other objects
	private ArrayList<Tile> tiles;
	GameBoard gameBoard;
	private boolean isLock;
	//attributes dealing with animation
	private int aniNumber;
	private BufferedImage currentEnemy;
	private BufferedImage enemySpriteSheet;
	private BufferedImage currentAttack;
	private BufferedImage attackSpriteSheet;
	private BufferedImage deadSpriteSheet;
	private BufferedImage currentDead;
	private final int HITFRAME = 3;
	private final int CANNONDMG = 20;
	 
	public Canodumb(int health,int xPos,int yPos,ArrayList<Tile> tiles, GameBoard currentGameBoard){
		this.health = health;
		this.isLock = false;
		this.xPos = xPos;
		this.yPos = yPos;
		this.tiles = tiles;
		this.gameBoard = currentGameBoard;
		this.enemyHealth = new EnemyHealth(this);
		
		try {
			enemySpriteSheet = ImageIO.read(new File("./src/Img/bCanodumb/bCanodumbV1.png"));
			attackSpriteSheet = ImageIO.read(new File("./src/Img/bCanodumb/bCanodumbAttack.png"));
			deadSpriteSheet = ImageIO.read(new File("./src/Img/explosionsprite/explosion.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentEnemy = enemySpriteSheet.getSubimage(0, 0, 42, 50);
		currentAttack = attackSpriteSheet.getSubimage(0, 0, 36, 56);
		currentDead = deadSpriteSheet.getSubimage(0, 0, 60, 59);
		
		this.isDead = false;
		this.aniNumber = 0;
		this.currentState = CanodumbState.nothing;
		this.attackXPos = xPos;
		this.attackYPos = yPos;
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
	public void takeDamage(int damage) {
		this.health = this.health - damage;
		if(this.health <= 0){
			this.health = 0;
            currentState = CanodumbState.dead; 
			isDead = true;
		}
	}
	
	
	public void update(){
		updateState();
		updateSprite();
		enemyHealth.update();
	}
	
	
	@Override
	public void updateState() {
		switch(currentState){
		case nothing:
     		int diff = yPos - gameBoard.getPlayer().getYpos();
//     		  if( isLock == false && gameBoard.getTile(xPos, yPos).getCurrentState() == tileState.Normal){
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
//     		  }
			if(this.gameBoard.getPlayer().getYpos() == this.yPos){
				this.currentState = CanodumbState.searching;
				this.aniNumber = 0;
				this.attackXPos = xPos;
				this.attackYPos = yPos;
			}
			break;
		case searching:
			aniNumber++;
			if(aniNumber >= 15){
				attackXPos = attackXPos - 1;
				aniNumber = 0;
			}
			if (attackXPos == gameBoard.getPlayer().getXpos() && attackYPos == gameBoard.getPlayer().getYpos()){
				currentState = CanodumbState.found;
				aniNumber = 0;
			}
			if (attackXPos < 0){
				attackXPos = xPos;
				currentState = CanodumbState.nothing;
			}
			break;
		case found:
			aniNumber++;
			if(aniNumber >= 15){
				aniNumber = 0;
				currentState = CanodumbState.firing;
			}
			break;
		case firing:
			aniNumber++;
//			if(aniNumber == HITFRAME){
//				if(gameBoard.getPlayer().getYpos() == yPos){
//					gameBoard.getPlayer().takeDamage(CANNONDMG);
//				}
//			}
			if(aniNumber > 4){
				aniNumber = 0;
				if(gameBoard.getPlayer().getYpos() == yPos){
					gameBoard.getPlayer().takeDamage(CANNONDMG);
				}
				currentState = CanodumbState.nothing;
			}
			break;
		case dead:
			int delay = 0;
			for( int i = 0; i <= 40; i++){
				delay++;
			}
			if( delay == 40){
				aniNumber++;
			}
			if( aniNumber > 12)
				currentState = CanodumbState.nothing;
			break;
		}
	}
	
	@Override
	public void updateSprite() {
		switch(currentState){
		case nothing:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 42, 50);
			currentAttack= attackSpriteSheet.getSubimage(0, 0, 36, 56);
			break;
		case searching:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 42, 50);
			currentAttack= attackSpriteSheet.getSubimage(0, 0, 36, 56);
			break;
		case found:
			currentEnemy = enemySpriteSheet.getSubimage(0, 0, 42, 50);
			currentAttack= attackSpriteSheet.getSubimage(36, 0, 36, 56);
			break;
		case firing:
			currentEnemy= enemySpriteSheet.getSubimage(aniNumber*42,0,42,50);
			currentAttack= attackSpriteSheet.getSubimage(aniNumber*36+ 72, 0, 36, 56);
			break;
		case dead:
			currentDead = deadSpriteSheet.getSubimage(aniNumber*60,0,60,59);
			break;
		}
	}
	
	@Override
	public void paintRequest(Graphics2D clone) {
		switch(currentState){
		case nothing:
			clone.drawImage(currentEnemy, 40*xPos + 3, 42+24*yPos, null);
			break;
		case searching:
			clone.drawImage(currentEnemy, 40*xPos + 3, 42+24*yPos, null);
			clone.drawImage(currentAttack, 40*attackXPos + 3, 42+24*attackYPos, null);
			break;
		case found:
			clone.drawImage(currentEnemy, 40*xPos + 3, 42+24*yPos, null);
			clone.drawImage(currentAttack, 40*attackXPos + 3, 42+24*attackYPos, null);
			break;
		case firing:
			clone.drawImage(currentEnemy, 40*xPos + 3, 42+24*yPos, null);
			clone.drawImage(currentAttack, 40*xPos - 30, 24+24*yPos, null);
			break;
		case dead:
			clone.drawImage(currentDead, 40*xPos + 3, 42+24*yPos, null);
			break;
		}
//		enemyHealth.paintRequest(clone);
	}


	@Override
	public boolean getIsDead() {
		return isDead;
	}

	@Override
	public SpriteInterface getHealthSprite() {
		return enemyHealth;
	}


	@Override
	public void setIsLock(boolean isLock) {
		this.isLock = isLock;
	}

}
