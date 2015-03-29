package Game;
import java.util.ArrayList;

public class BattleChips {
	private ArrayList<Chip> reserveChips;
	private Chip activeChip;
	private ArrayList<Chip> chipList;
	private Player player;
	//set to true/false on active chips
	private boolean isActiveChip;
	
	//all the chips that are available for the player use in battle
	public BattleChips(){
		reserveChips = new ArrayList<Chip>();
		chipList = new ArrayList<Chip>();
		isActiveChip = false;
	}
	
	public void setPlayer(Player player){
		this.player =  player;
	}
	
	public boolean isActiveChip(){
		return isActiveChip;
	}
	
	public void clearActiveChip(){
		activeChip = null;
		isActiveChip = false;
	}
	
	//check the reserveChips have any chips for the BattleChips
	public boolean isReserveChips(){
		if( reserveChips.size() > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getNumberOfReserveChips(){
		return reserveChips.size();
	}
	
	public Chip getActiveChip(){
		return activeChip;
	}
	
	public ArrayList<Chip> getParticles(){
		return chipList;
	}
	
	//move a chip from reserve chips to the active chip position
	public void chipActivation(){
		if( reserveChips.size() != 0 && !isActiveChip){
			activeChip = reserveChips.get(0);
			reserveChips.remove(0);
			isActiveChip = true;
		}
	}
	
	//@TODO
	public void updateChips(){
		if ( activeChip != null){
			activeChip.createAction();
		}
	}
	
	public void loadBattleChips(ArrayList<Chip> inChips){
		reserveChips.clear();
		reserveChips = inChips;
	}
}
