package Game;

/**
 * Created by karagara on 21/03/15.
 */
public class PlayerBusterAction extends Action{

    private boolean isCharged;

    PlayerBusterAction(Player player, Tile tile, Arena arena, boolean isCharged) {
    	super(player, arena, tile);
    	spritePath =  "playerBuster.png";
        this.isCharged = isCharged;
    }

    @Override
    public void update() {
        //on Attack frame, check for targets
        if (index == 4){
            //apply damage to first in row
            //arena.damageFirstInRow(player.getYPos(), 10);
            boolean hasHitTarget = false;
            if (player.getSide() == PlayerSide.RED){
                for(int i=player.getXPos(); i < 6 && !hasHitTarget; i++){
                    if(arena.isTileOccupied(i,player.getYPos())){
                        GameEntity entity = arena.getTileEntity(i, player.getYPos());
                        entity.damageEntity((isCharged) ? 10 : 1);
                        hasHitTarget = true;
                    }
                }
            }

            if (player.getSide() == PlayerSide.BLUE){
                for(int i=player.getXPos(); i >= 0 && !hasHitTarget; i--){
                    if(arena.isTileOccupied(i,player.getYPos())){
                        GameEntity entity = arena.getTileEntity(i, player.getYPos());
                        if (entity != null)
                            entity.damageEntity((isCharged) ? 10 : 1);
                        hasHitTarget = true;
                    }
                }
            }

            isComplete = true;
            player.setCondition(playerCondition.CLEAR);
        }
        index++;
    }

    @Override
    public String getChipSequence() {
        return null;
    }
}
