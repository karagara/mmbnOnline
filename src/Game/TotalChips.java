package Game;

import java.util.ArrayList;
import java.util.Random;

public class TotalChips {
	private ArrayList<Chip> chipList;
	private Random generator;
	public TotalChips(Player player, Arena arena){
		//currently have 3 chips \
        char letters[] = {'a','b','c','a','b','c','a','b','c'};
        ChipType types[] = {ChipType.CANNON,ChipType.CANNON,ChipType.CANNON,ChipType.SWORD,ChipType.SWORD,ChipType.SWORD,ChipType.CANNON,ChipType.CANNON,ChipType.CANNON};
		chipList  = new ArrayList<Chip>();
		for ( int i = 0; i < 9; i++){
			chipList.add(new Chip(types[i], letters[i], player, arena));
		}
		generator = new Random();
	}
	public ArrayList<Chip> selectTotalChips(int numChips){
		ArrayList<Chip> tList = new ArrayList<Chip>();
		if(chipList.size() > numChips){
			for (int i = 0; i < numChips; i++){
				int randIndex = generator.nextInt(chipList.size());
				tList.add(chipList.get(randIndex));
				chipList.remove(randIndex);
			}
		}else if(numChips > chipList.size() && chipList.size() != 0){
			for (int i = 0; i <= chipList.size(); i++){
				int randIndex = generator.nextInt(chipList.size());
				tList.add(chipList.get(randIndex));
				chipList.remove(randIndex);
			}
		}
		return tList;
	}
}
