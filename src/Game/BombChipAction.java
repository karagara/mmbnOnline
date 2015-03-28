package Game;

public class BombChipAction extends Action{


    BombChipAction(Player player, Tile tile, Arena arena) {
    	super(player, arena, tile);
    	spritePath =  "playerBomb.png";
    }

    public void update() {
	int landLocationX=player.getXPos();
	int landLocationY=player.getYPos();
        if (index ==4){
                
            player.setCondition(playerCondition.CLEAR);
             
       }
       if (index == 10){

            if (player.getSide() == PlayerSide.RED){
                GameEntity entity = arena.getTileEntity(landLocationX + 3,landLocationY);
                if (entity != null) {
                    System.out.println("Damaging Enemy");
                    entity.damageEntity(50);

                }
            }
            if (player.getSide() == PlayerSide.BLUE){
                GameEntity entity = arena.getTileEntity(landLocationX + 3,landLocationY);
                if (entity != null) {
                    System.out.println("Damaging Enemy");
                    entity.damageEntity(50);
                }
            }
            isComplete = true;
       }
       index++;
  }

    @Override
    public String getChipSequence() {
        return null;
    }
}
