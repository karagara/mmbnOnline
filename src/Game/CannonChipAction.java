package Game;

/**
 * Created by karagara on 21/03/15.
 */
public class CannonChipAction extends Action {

    CannonChipAction(Player player, Tile tile, Arena arena) {
        super(player, arena, tile);
        spritePath = "playerCannon.png";

        //If this action is created, consume chip
        //player.removeChip();
    }

    @Override
    public void update() {
        if (index == 10){
            //apply damage to first in row
            //arena.damageFirstInRow(player.getYPos(), 60);
            boolean hasHitTarget = false;
            if (player.getSide() == PlayerSide.RED){
                for(int i=player.getXPos(); i < 6 && !hasHitTarget; i++){
                    if(arena.isTileOccupied(i,player.getYPos())){
                        ;
                        GameEntity entity = arena.getTileEntity(i, player.getYPos());
                        if (entity != null) {
                            System.out.println("Damaging Enemy");
                            entity.damageEntity(40);
                        }
                        hasHitTarget = true;
                    }
                }
            }

            if (player.getSide() == PlayerSide.BLUE){
                for(int i=player.getXPos(); i >= 0 && !hasHitTarget; i--){
                    if(arena.isTileOccupied(i,player.getYPos())){
                        GameEntity entity = arena.getTileEntity(i, player.getYPos());
                        if (entity != null)
                            entity.damageEntity(40);
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
