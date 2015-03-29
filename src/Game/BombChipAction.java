package Game;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BombChipAction extends Action{

    private ArrayList<FrameEvent> frameEventSequence = new ArrayList<FrameEvent>();

    BombChipAction(Player player, Tile tile, Arena arena) {
    	super(player, arena, tile);
    	spritePath =  "playerBomb.png";
        setupFrameEvents();
    }
    private void setupFrameEvents() {
        int fIndex[] = {0, 1, 2, 3, 4, 5, 6};
        int sIndex[] = {0, 1, 2, 3, 4, 5, 6};
        String sSrc[] = {"bomb", "bomb", "bomb", "bomb", "bomb", "bomb", "bomb"};
        int xDis[] = {0, 0, 0, 0, 1, 2, 3};
        int yDis[] = {0, 0, 0, 0, 0, 0, 3};
        String tEffect[] = {"", "", "", "", "", ""};
        int dmg[] = {0, 0, 0, 0, 0, 0, 50};
        playerCondition pcon[] = {playerCondition.INACTION, playerCondition.INACTION, playerCondition.INACTION, playerCondition.INACTION, playerCondition.CLEAR, playerCondition.CLEAR, playerCondition.CLEAR};
        int pIndex[] = {0, 1, 2, 3, 4, 5, 6};
        String pSrc[] = {"playerThrow", "playerThrow", "playerThrow", "playerThrow", "", "", ""};

        for (int i = 0; i < 6; i++) {
            FrameEvent f = new FrameEvent();
            f.frameIndex = fIndex[i];
            FrameEvent.Sprite s = new FrameEvent.Sprite();
            s.spriteSrc = sSrc[i];
            s.spriteIndex = sIndex[i];
            f.sprite = s;
            f.playerState = pcon[i];
            f.xDisplacement = xDis[i];
            f.yDisplacement = yDis[i];
            f.tileEffect = tEffect[i];
            f.damage = dmg[i];
            FrameEvent.Sprite ps = new FrameEvent.Sprite();
            ps.spriteSrc = pSrc[i];
            ps.spriteIndex = pIndex[i];
            f.playerSprite = ps;
            frameEventSequence.add(f);
        }
    }


    public void update() {
        if (player.getCondition() == playerCondition.HIT && index < 4){
            isComplete = true;
            return;
        }
        int landLocationX=player.getXPos();
        int landLocationY=player.getYPos();
            if (index == 4){
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
        Gson gson = new Gson();
        String message = gson.toJson(frameEventSequence);
        return message;
    }
}
