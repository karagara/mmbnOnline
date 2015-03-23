package Game;

/**
 * Created by karagara on 21/03/15.
 */
public class CannonChipAction extends Action {

    CannonChipAction(Player player, Tile tile, Arena arena) {
        super(player, arena, tile);
        spritePath = "playerBuster.png";

        //If this action is created, consume chip
        //player.removeChip();
    }

    @Override
    public void update() {
        //on Attack frame, check for targets
        if (index == 10){
            //apply damage to first in row
            //arena.damageFirstInRow(player.getYPos(), 60);
            isComplete = true;
        }
        index++;
    }
}
