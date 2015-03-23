package Game;

/**
 * Created by karagara on 21/03/15.
 */
public class PlayerBusterAction extends Action{

    PlayerBusterAction(Player player, Tile tile, Arena arena) {
    	super(player, arena, tile);
    	spritePath =  "playerBuster.png";
    }

    @Override
    public void update() {
        //on Attack frame, check for targets
        if (index == 6){
            //apply damage to first in row
            //arena.damageFirstInRow(player.getYPos(), 10);
            isComplete = true;
        }
        index++;
    }

    @Override
    public String getChipSequence() {
        return null;
    }
}
