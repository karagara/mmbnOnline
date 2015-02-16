package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Model.Tile.tileOwner;
import Shared.SpriteInterface;
import Shared.UserCommands;
import Shared.UserCommands.Direction;

/* This class represent the player object. The player object can give the buster shot and chip attack. And it can detect the damage.
 * It update the player state and every separate sprites. It have the paint method to draw the current health and current location of 
 * the player
 */
public class Player implements SpriteInterface {

	enum PlayerState {
		nothing, movement, damage, buster, chip
	}
	// Attributes used for player holds
	private int health;
	private int xPos;
	private int yPos;
	GameBoard currentGameBoard;
	private ArrayList<Tile> tiles;
	//private ChipInterface chip;
	private basicAttack buster;
	private BattleChips bChips;
	private PlayerHealth pHealth;
	private boolean isDead = false;
	private boolean recentDamage = false;
	private int recentDamageCount = 0;
	// UserCommands inputs;

	// Attributes used for animation and sprites
	private BufferedImage currentSprite;
	private BufferedImage spriteSheet;
	private RescaleOp damageFilter;

	private int aniNumber;
	private PlayerState currentState;

	public Player(int health, int xPos, int yPos, ArrayList<Tile> tiles, GameBoard currentGameBoard) {
		this.health = health;
		this.xPos = xPos;
		this.yPos = yPos;
		this.tiles = tiles;
		this.aniNumber = 0;
		this.currentState = PlayerState.nothing;
//		this.chip = new CannonChip();
//		this.buster = new basicAttack(currentGameBoard);
		this.pHealth = new PlayerHealth(this);
		this.currentGameBoard = currentGameBoard;
		try {
			spriteSheet = ImageIO.read(new File(
					"./src/Img/megamanV2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		currentSprite = spriteSheet.getSubimage(0, 0, 44, 48);
		float[] scales = { 1f, 1f, 1f, 0.5f };
		float[] offsets = new float[4];
		damageFilter = new RescaleOp(scales, offsets, null);
	}
    
	// Get the buster damage, that damage is pre-define in the constructor of the buster.
	public int getBusterDamage() {
		return buster.getDamage();
	}
    
	// Get the chip attack damage, that damage is pre-defined in the constructor of the Chip
	public int getChipDamage() {
		ChipInterface ActiveChip = bChips.getActiveChip();
		int currentDamage = ActiveChip.getDamage();
		return currentDamage;
	}
    
	//Accessor and mutator for the health and location function
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public SpriteInterface getHealthSprite(){
		return pHealth;
	}
	
	public SpriteInterface getBuster(){
		return buster;
	}

	public int getXpos() {
		return xPos;
	}

	public int getYpos() {
		return yPos;
	}

	public boolean getIsDead() {
		return isDead;
	}
   
	public void takeDamage(int damage) {
		if (!recentDamage){
		this.health = this.health - damage;
		aniNumber = 0;
		currentState = PlayerState.damage;
		recentDamage = true;
		recentDamageCount = 0;
		buster = null;
		bChips.clearActiveChip();
		if (this.health <= 0) {
			this.health = 0;
			this.isDead = true;
		}
		}
	}

	//set the battle chips to the current player from the menu chips.
	public void setBattleChips(BattleChips bChips) {
		this.bChips = bChips;
		this.bChips.setPlayer(this);
	}
    
	//Depend on the user input, the player state should be updated and used for different animation.
	public void update(UserCommands input) {
		respondToUserInputs(input);
		updateState();
		updateSprite();
		// currentGameBoard.getEnemy().update();
		// updateBattleChips();
	}
    
	
	public void respondToUserInputs(UserCommands input) {
		tryToMove(input.getDirection());
		tryToUseChip(input.getAPressed());
		tryToBasicAttack(input.getBPressed());
	}
	
    //Determine the player move direction and move range.
	public void tryToMove(Direction direction) {
		if (currentState == PlayerState.nothing) {
//			if (tiles.get((yPos * 6) + xPos).checkMovement(xPos, yPos,
//					direction)) {
			if(currentGameBoard.isValidMove(xPos, yPos, direction, tileOwner.player)){
				switch (direction) {
				case up:
					yPos = yPos - 1;
					break;
				case right:
					xPos = xPos + 1;
					break;
				case down:
					yPos = yPos + 1;
					break;
				case left:
					xPos = xPos - 1;
					break;
				default:
					break;
				}
				if (direction != Direction.none) {
					currentState = PlayerState.movement;
					aniNumber = 0;
				}
			}
		}
	}
    
	//Try to use the basic attack depend on the user input, after the basic attack is active, it will change the player state.
	public void tryToBasicAttack(boolean isBPressed) {
		if (currentState == PlayerState.nothing) {
			if (isBPressed) {
				buster = new basicAttack(currentGameBoard);
				currentState = PlayerState.buster;
				aniNumber = 0;
				buster.startAttack(xPos, yPos);
			}
		}
	}

	//get the current battle chips that the player hold
	public BattleChips getBattleChips() {
		return bChips;
	}
	
    //try to use the battle chips that in the current active chip list.
	public void tryToUseChip(boolean isAPressed) {
		if (currentState == PlayerState.nothing) {
			if (isAPressed && bChips.isReserveChips()) {
				bChips.tryToUseChip();
				currentState = PlayerState.chip;
				aniNumber = 0;
				// chip.startChip(xPos, yPos);
			}
		}
	}
	
    // update the different sprite depend on the different player state.
	private void updateSprite() {
		switch (currentState) {
		case nothing:
			currentSprite = spriteSheet.getSubimage(aniNumber * 44, 0, 44, 48);
			break;
		case movement:
			currentSprite = spriteSheet.getSubimage(aniNumber * 44, 48, 44, 48);
			break;
		case damage:
		 currentSprite = spriteSheet.getSubimage((aniNumber/2)*44, 144, 44, 48);
		 break;
		default:
			break;
		}
	}
    
	//It will update the player state that can do the corresponding animation
	private void updateState() {
		aniNumber++;
		switch (currentState) {
		case nothing:
			if (aniNumber > 3)
				aniNumber = 0;
			break;
		case movement:
			if (aniNumber > 2) {
				aniNumber = 0;
				currentState = PlayerState.nothing;
			}
			break;
		case chip:
			aniNumber = 0;
			bChips.updateChips();
			if (!bChips.isActiveChip()) {
				currentState = PlayerState.nothing;
				// chip = new CannonChip();
			}
			break;
		case buster:
			aniNumber = 0;
			buster.update();
			if (buster.getExpired()) {
				currentState = PlayerState.nothing;
				buster = null;
			}
			break;
		case damage:
			if (aniNumber > 6) {
				aniNumber = 0;
				currentState = PlayerState.nothing;
			}
			break;
		default:
			break;
		}

		pHealth.update();
		recentDamageCount++;
		if(recentDamageCount > 10){
			recentDamage = false;
		}
	}

	@Override
	public void paintRequest(Graphics2D clone) {
		switch (currentState) {
		case chip:
//			bChips.paintRequest(clone);
			break;
		case buster:
//			buster.paintRequest(clone);
			break;
		default:
			if (recentDamage){
//				clone.drawImage(currentSprite, damageFilter, 40 * xPos + 3, 42 + 24 * yPos);
				clone.drawImage(currentSprite, 40 * xPos + 3, 42 + 24 * yPos, null);
			}else{
				clone.drawImage(currentSprite, 40 * xPos + 3, 42 + 24 * yPos, null);
			}
			break;
		}
//		bChips.paintRequest(clone);
//		pHealth.paintRequest(clone);

		// currentSprite.flush();
	}
}