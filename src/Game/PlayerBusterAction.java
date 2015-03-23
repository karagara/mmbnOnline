package Game;

/**
 * Created by karagara on 21/03/15.
 */
public class PlayerBusterAction implements Action{
    private Player player;
    private Tile origin;
    private Arena arena;
    private String spritePath = "playerBuster.png";;
    private Integer index = 0;
    private Boolean isComplete = false;

    PlayerBusterAction(Player player, Tile tile, Arena arena) {
        this.player = player;
        this.origin = tile;
        this.arena = arena;
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
    public Boolean isEventComplete() {
        return null;
    }

    @Override
    public String getChipSequence() {
        return null;
    }
}
