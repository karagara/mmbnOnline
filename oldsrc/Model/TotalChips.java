package Model;

import java.util.ArrayList;
import java.util.Random;

/*
 * @author Yan Liu and Colten Nomore
 * 
 * The TotalChips is the representation of the entire bag of chips that the MenuChips can draw from. 
 * 
 */
public class TotalChips {
	private ArrayList<ChipInterface> chipList;
	private Random generator;
	
	/* public TotalChips(GameBoard gameBoard)
	 * Constructor for the TotalChips Object
	 */
	public TotalChips(GameBoard gameBoard){
		chipList = new ArrayList<ChipInterface>();
		for(int i = 0; i < 5; i++){
			chipList.add(new CannonChip(gameBoard)); //Create an array of 30 chips
			chipList.add(new ExplosionChip(gameBoard));
			chipList.add(new SwordChip(gameBoard));
			chipList.add(new HealthChip(gameBoard));
			chipList.add(new TileBreakChip(gameBoard));
		}
		
		chipList.add(new TileGrabChip(gameBoard));
		generator = new Random();
	}
	/* public ArrayList<ChipInterface> drawChips(int numChips)
	 * sends a list of ChipInterfaces, then removes these chips from chipLisy
	 * 
	 * @params numChips The number of chips that is to be drawn
	 * @returns a random list of chips
	 */
	public ArrayList<ChipInterface> drawChips(int numChips){
		ArrayList<ChipInterface> tList = new ArrayList<ChipInterface>();
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
