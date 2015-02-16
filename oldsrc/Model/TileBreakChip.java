package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Model.Tile.tileState;

public class TileBreakChip implements ChipInterface {
	private BufferedImage currentMegaman;
	private BufferedImage megamanSpriteSheet;
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
	private GameBoard gameBoard;

	public TileBreakChip(GameBoard gameBoard){
		this.range = 1;
		this.damage = 10;
		this.letter = "C";
		this.type = "tilebreak";
		aniNumber = 0;
		isExpired = false;
		this.gameBoard = gameBoard;
		BufferedImage tempNums = null;
		
		try {
			megamanSpriteSheet = ImageIO.read(new File("./src/Img/tilebreak/megaman.png"));
			largeMenuIcon = ImageIO.read(new File("./src/Img/tilebreak/breaklarge.png"));
			smallMenuIcon = ImageIO.read(new File("./src/Img/tilebreak/breaksmall.png"));
			smallLetter = ImageIO.read(new File("./src/Img/cannonsprite/cannonletters.png")).getSubimage(12, 0, 6, 5);
			largeLetter = ImageIO.read(new File("./src/Img/battlemenu/alphabetV1.png")).getSubimage(21, 0, 7, 11);
			tempNums = ImageIO.read(new File("./src/Img/mHealthV3.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}		
		currentMegaman = megamanSpriteSheet.getSubimage(0,0,100,68);
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
	
    public int getRange(){
    	return range;
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
	
    public int getDamage(){
    	return damage;
    }
    
	@Override
	public void update() {
		if( aniNumber >= 13){
			isExpired = true;
		}
		if( isExpired){
			boolean isEnemyAhead = false;
			int enemyIndex = 0;
			for(int i = 0; i < gameBoard.getEnemyList().size(); i++){
				if( gameBoard.getEnemyList().get(i).getXpos() == (startXPos + range) && gameBoard.getEnemyList().get(i).getYpos() == (startYPos)){
					isEnemyAhead = true;
					gameBoard.getEnemyList().get(i).takeDamage(damage);
				}
			}
			if(!isEnemyAhead){
				gameBoard.getTile(startXPos + range, startYPos).setCurrentState(tileState.Damaged);
				gameBoard.getTile(startXPos + range, startYPos).update(startXPos + range, startYPos);
			}
		}
		
		if( !isExpired){
			currentMegaman = megamanSpriteSheet.getSubimage(aniNumber*100,0,100,68);
			aniNumber++;
		}
	}
	
	@Override
	public void paintRequest(Graphics2D clone) {
		clone.drawImage(currentMegaman, 40*startXPos + 1, 42+24*startYPos - 15, null);
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
