package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Shared.SpriteInterface;

public class basicAttack implements SpriteInterface {
	private int damage;
	private BufferedImage attackSpriteSheet;
	private BufferedImage currentAttack;
	private int aniNumber;
	private int startXPos;
	private int startYPos;	
	private final int BASIC_ATTACK = 3;
	private boolean isExpired;
	private static final int BUSTER_DAMAGE = 1;
	private GameBoard gameBoard;
	
	public basicAttack(GameBoard gameBoard){
		this.gameBoard = gameBoard;
		
		aniNumber = 0;
		try{
			attackSpriteSheet = ImageIO.read(new File("./src/Img/megamanbusterV2.png"));

		} catch(IOException e){
			e.printStackTrace();
		}
		currentAttack = attackSpriteSheet.getSubimage(aniNumber*80, 0, 80, 48);
		this.damage = BUSTER_DAMAGE;
	}
	public int getDamage(){
		return damage;
	}
	
	public void startAttack(int xPos, int yPos) {
		startXPos = xPos;
		startYPos = yPos;
	}
	
	public void update(){
		if(!isExpired){
			currentAttack = attackSpriteSheet.getSubimage(aniNumber*80, 0, 80, 48);
			aniNumber++;
		}
		if(aniNumber == BASIC_ATTACK){
			boolean enemyHit = false;
			for (int i = startXPos; i < 6; i++){
				for (int j = 0; j < gameBoard.getEnemyList().size(); j++){
					if(gameBoard.getEnemyList().get(j).getXpos() == i && gameBoard.getEnemyList().get(j).getYpos() == startYPos){
						gameBoard.getEnemyList().get(j).takeDamage(damage);
						enemyHit = true;
						break;
					}
				}
				if (enemyHit)
					break;
			}
			isExpired = true;
		}
//		if(aniNumber > BASIC_ATTACK){
//			if(gameBoard.getEnemy().getYpos() == startYPos){
//				gameBoard.getEnemy().takeDamage(BUSTER_DAMAGE);
//			}
//			isExpired = true;
//		}
	}
	
	public boolean getExpired() {
		return isExpired;
	}
	
	public void paintRequest(Graphics2D clone){
		clone.drawImage(currentAttack, 40*startXPos + 3 , 42+24*startYPos, null);
	}		
}
