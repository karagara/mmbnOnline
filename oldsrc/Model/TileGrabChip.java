package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Model.Tile.tileOwner;

public class TileGrabChip implements ChipInterface {
	private BufferedImage currentMegaman;
	private BufferedImage megamanSpriteSheet;
	private BufferedImage largeMenuIcon;
	private BufferedImage smallMenuIcon;
	private BufferedImage smallLetter;
	private BufferedImage largeLetter;
	private BufferedImage damageValue;
	private int aniNumber;
	private int startXPos;
	private int startYPos;
	private int grabTileXpos;
	private int grabTileYpos;
	private int range;
    private String letter;
    private String type;
	private boolean isExpired;
	private boolean isParticle;
	private GameBoard gameBoard;
    private boolean recovery;
    private static final int CHIP_DAMAGE = 10;

	public TileGrabChip(GameBoard gameBoard){
		this.range = 1;
		this.letter = "B";
		this.type = "tilegrab";
		aniNumber = 0;
		isExpired = false;
		isParticle = false;
		this.grabTileXpos = 0;
		this.grabTileYpos = 0;
		this.gameBoard = gameBoard;
		this.isExpired = false;
		BufferedImage tempNums = null;
		try {
			
			largeMenuIcon = ImageIO.read(new File("./src/Img/tilegrab/tilegrabchip.png"));
			smallMenuIcon = ImageIO.read(new File("./src/Img/tilegrab/tilegrabsmall.png"));
			smallLetter = ImageIO.read(new File("./src/Img/swordsprite/swordletters.png"));
			smallLetter = ImageIO.read(new File("./src/Img/cannonsprite/cannonletters.png")).getSubimage(6, 0, 6, 5);
			largeLetter = ImageIO.read(new File("./src/Img/battlemenu/alphabetV1.png")).getSubimage(14, 0, 7, 11);
			tempNums = ImageIO.read(new File("./src/Img/mHealthV3.png")); 

		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	public int getDamage() {
		return 0;
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
		grabTileXpos = startXPos;
		grabTileYpos = startYPos;
		while( gameBoard.getTile(grabTileXpos , grabTileYpos).getCurrentOwner() == tileOwner.player && grabTileXpos < 5 ){
		     grabTileXpos++; 
		}
		if( !isExpired){
			grabTileYpos = 0;
			for ( int i = 0; i < 3; i++){
				for( int j = 0; j < gameBoard.getEnemyList().size();j++){
					if( gameBoard.getEnemyList().get(j).getXpos() == grabTileXpos && gameBoard.getEnemyList().get(j).getYpos() == (grabTileYpos + i) ){
						gameBoard.getEnemyList().get(j).takeDamage(CHIP_DAMAGE);
					}
					else{
						gameBoard.getTile(grabTileXpos, grabTileYpos + i).updateOwner(grabTileXpos);
					}
				}
			}
		    isExpired = true;
		}
	}

	public void paintRequest(Graphics2D clone) {
		gameBoard.getPlayer().paintRequest(clone);
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
		return null;
	}

}
