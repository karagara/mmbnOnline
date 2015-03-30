package Game;

import java.util.ArrayList;

public class MenuChips {
	private ArrayList<Chip> menuChipList;
	private ArrayList<Chip> queuedList;
	private ArrayList<Boolean> isSelectedList;
	private ArrayList<Integer> queuedPosition;
	private TotalChips totalChips;
	private BattleChips battleChips;
	//the menu can show 5 chips total
	private  int CHIPLIMITINMENU = 4;
	
	public MenuChips(TotalChips totalChips, BattleChips battleChips){
		this.totalChips = totalChips;
		this.battleChips = battleChips;
		menuChipList = totalChips.selectTotalChips(CHIPLIMITINMENU);
		queuedList = new ArrayList<Chip>();
		isSelectedList = new ArrayList<Boolean>();
		for ( int i = 0; i < menuChipList.size(); i++){
			isSelectedList.add(false);
		}
		queuedPosition = new ArrayList<Integer>();
	}
	
	//gets a list of random chips from the totalchips list
	//numChips - number of chips from totalChips
	public ArrayList<Chip> getRandomChips(int numChips){
		return totalChips.selectTotalChips(numChips);
	}
	
	public void setBattleChips(){
		ArrayList<Chip> tList = new ArrayList<Chip>(queuedList);
		battleChips.loadBattleChips(tList);
		for( int i = isSelectedList.size() -1 ; i >=0; i--){
			if( isSelectedList.get(i).booleanValue()){
				menuChipList.remove(i);
			}
		}
		if( menuChipList.size() < CHIPLIMITINMENU ){
			menuChipList.addAll(getRandomChips(CHIPLIMITINMENU-menuChipList.size()));
		}
		isSelectedList.clear();
		for ( int i= menuChipList.size() -1; i>=0; i--){
			isSelectedList.add(false);
		}
		queuedList.clear();
		queuedPosition.clear();
	}
	
	public void recycleChips(){
		CHIPLIMITINMENU =  CHIPLIMITINMENU + queuedList.size();
		if (CHIPLIMITINMENU > 10)
			CHIPLIMITINMENU = 10;
		for(int i = isSelectedList.size() - 1; i >= 0; i--){
			if (isSelectedList.get(i).booleanValue()){
				menuChipList.remove(i);
			}
		}
		if(menuChipList.size() < CHIPLIMITINMENU){
			menuChipList.addAll(getRandomChips(CHIPLIMITINMENU-menuChipList.size()));
		}
		isSelectedList.clear();
		for(int i = menuChipList.size() - 1; i >= 0; i--){
			isSelectedList.add(false);
		}
		queuedList.clear();
		queuedPosition.clear();
	}
	
	//add a chip to the queuedchip list based on a desired index
	public void setSelected(int index){
		if(index < menuChipList.size()){
			if(!isSelectedList.get(index)  && queuedList.size() <= 4 ){
				queuedList.add(menuChipList.get(index));
				queuedPosition.add(index);
				isSelectedList.set(index, true);
			}
		}
	}
	
	//remove the last queued chip, nothing happes if there is no more queue chips
	public void removeQueuedChip(){
		int qSize = queuedList.size();
		if (qSize > 0){
			isSelectedList.set(queuedPosition.get(qSize - 1), false);
			queuedPosition.remove(qSize - 1);
			queuedList.remove(qSize - 1);
		}
	}

    public ArrayList<Chip> getMenuChipList(){
        return menuChipList;
    }

    public ArrayList<Chip> getQueuedList(){
        return queuedList;
    }

    public ArrayList<Boolean> getIsSelectedList(){
        return isSelectedList;
    }
}
