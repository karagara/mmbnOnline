package Game;

/**
 * Created by karagara on 21/03/15.
 */
public class CannonChipAction implements Action {
    private Player player;
    private Tile origin;
    private Arena arena;
    private String spritePath = "playerBuster.png";;
    private Integer index = 0;
    private Boolean isComplete = false;

    CannonChipAction(Player player, Tile tile, Arena arena) {
        this.player = player;
        this.origin = tile;
        this.arena = arena;

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

    @Override
    public Boolean isEventComplete() {
        return isComplete;
    }
}
