package Model;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Shared.SpriteInterface;

/*
 * @author Colten Normore and Yan Liu
 * 
 * BattleChips is the representation of all the chips that are available for the player to use in battle.
 */
public class BattleChips implements SpriteInterface{
	
	private ArrayList<ChipInterface> reserveChips;
	private ChipInterface activeChip;
	private ArrayList<ChipInterface> chipParticles;
	private Player player;
	private boolean isActiveChip; // needs to be set to true/false every time something operates on activeChip
	
	/* public BattleChips()
	 * Constructor for the BattleChips object
	 */
	public BattleChips(){
		reserveChips = new ArrayList<ChipInterface>();
		chipParticles = new ArrayList<ChipInterface>();
		isActiveChip = false;
	}
	
	/* public void setPlayer(Player player)
	 * Setter function for the player attribute
	 * 
	 * @param player the desired player object
	 */
	public void setPlayer(Player player){
		this.player = player;
	}
	
	/* public boolean isActiveChip()
	 * getter function for boolean isActiveChip, a boolean set if an active chip exists
	 * 
	 * @returns the boolean isActiveChips
	 */
	
	public boolean isActiveChip(){
		return isActiveChip;
	}
	
	public void clearActiveChip(){
		activeChip = null;
		isActiveChip = false;
	}
	
	/* public boolean isReserveChips()
	 * a check to see if reserveChips has any objects
	 * 
	 * @returns a boolean value representing whether or not BattleChips is loaded with any chips
	 */
	public boolean isReserveChips(){
		if(reserveChips.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/* public int getNumberOfChips()
	 * getter function for the number of reserve chips
	 * 
	 * @returns the size of returnChips
	 */
	public int getNumberOfChips(){
		return reserveChips.size();
	}
	
	/* public ChipInterface getActiveChip()
	 * getter function for the activeChip
	 * 
	 * @returns the chip in the active chip position
	 */
    public ChipInterface getActiveChip(){
    	return activeChip;
    }
    
    public ArrayList<ChipInterface> getParticles(){
    	return chipParticles;
    }
    
    /* public void tryToUseChip()
     * Invoking this function tries to move a chip from the list of reserveChips to the active chip position.
     */
	public void tryToUseChip(){
		if (reserveChips.size() != 0 && !isActiveChip){
			activeChip = reserveChips.get(0);
			activeChip.startChip(player.getXpos(), player.getYpos());
			reserveChips.remove(0);
			isActiveChip = true;
		}
	}
	
	
	/* public void updateChips(int mmAniNumber)
	 * calls the update function on the activeChip
	 * 
	 * The parameter mmAniNumber is not used at this time
	 */
	public void updateChips(){
		if(activeChip != null){
			activeChip.update();
			if(activeChip.getExpired()){
				activeChip = null;
				isActiveChip = false;
			}
		}
		if(activeChip != null){
			if(activeChip.getIsParticle()){
				chipParticles.add(activeChip);
				activeChip = null;
				isActiveChip = false;
			}
		}
		if(chipParticles.size() > 0){ 
			for(int i = chipParticles.size()-1; i >= 0; i--){
				chipParticles.get(i).update();
				if(chipParticles.get(i).getExpired()){
					chipParticles.remove(i);
				}
			}
		}
	}
	
	/* public void loadBattleChips(ArrayList<ChipInterface> inChips)
	 * 
	 * Clears the reserveChips and sets the reserveChips to the desired List of Chips
	 * 
	 * @param inChips the list of chips to be set as the reserveChips
	 */
	public void loadBattleChips(ArrayList<ChipInterface> inChips){
		reserveChips.clear();
		reserveChips = inChips;
	}

	/*
	 * (non-Javadoc)
	 * @see Shared.SpriteInterface#paintRequest(java.awt.Graphics2D)
	 */
	@Override
	public void paintRequest(Graphics2D clone) {
		for(int i = reserveChips.size() - 1; i >= 0; i--){
			clone.drawImage(reserveChips.get(i).getSmallMenuIcon(), 18 + (40*player.getXpos()) - (2*i), 30 + (24 * player.getYpos()) - (2 * i), null);
//			
//			clone.drawImage(currentSprite, 40*xPos + 3, 42+24*yPos, null);
		}
//		if(activeChip != null)
//			activeChip.paintRequest(clone);
		
	}
	
}
