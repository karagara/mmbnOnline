package Game;

/**
 * Created by karagara on 29/03/15.
 */
public class ChipManager {
    TotalChips tchips;
    MenuChips mchips;
    BattleChips bchips;

    public ChipManager(Player p, Arena a){
        tchips = new TotalChips(p, a);
        bchips = new BattleChips();
        mchips = new MenuChips(tchips,bchips);
    }

    public void addChipToHand(int index){
        mchips.setSelected(index);
    }

    public void removeLastHandChip(){
        mchips.removeQueuedChip();
    }

    public void lockInHand(){
        mchips.setBattleChips();
    }

    public Chip getTopBattleChip(){
        return bchips.getActiveChip();
    }
}
