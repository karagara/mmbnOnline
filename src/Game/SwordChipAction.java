package Game;

public class SwordChipAction extends Action{

    SwordChipAction(Player player, Tile tile, Arena arena) {
    	super(player, arena, tile);
    	spritePath =  "playerSword.png";
        player.action = playerAction.SWORD;
        player.actionIndex = 0;
    }

    @Override
    public void update() {
        if (player.getCondition() == playerCondition.HIT){
            player.clearPlayerAction();
            isComplete = true;
            return;
        }
        if(index == 2){
            //apply damage to tile infront of player if entity exists.
            if (player.getSide() == PlayerSide.RED){
                GameEntity entity = arena.getTileEntity(player.getXPos() + 1,player.getYPos());
                if (entity != null) {
                    System.out.println("Damaging Enemy");
                    entity.damageEntity(80);
                }
            }

            if (player.getSide() == PlayerSide.BLUE){
                GameEntity entity = arena.getTileEntity(player.getXPos() - 1,player.getYPos());
                if (entity != null) {
                    System.out.println("Damaging Enemy");
                    entity.damageEntity(80);
                }
            }
        }

        //on Attack frame, checks tile infront of player
        if (index == 7){
            isComplete = true;
            player.setCondition(playerCondition.CLEAR);
            player.clearPlayerAction();
        }
        if (!isComplete){
            player.incActionIndex();
            index++;
        }
    }

    @Override
    public String getChipSequence() {
        return null;
    }
}
