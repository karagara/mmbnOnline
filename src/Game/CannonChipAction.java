package Game;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by karagara on 21/03/15.
 */
public class CannonChipAction extends Action {

    private ArrayList<FrameEvent> frameEventSequence = new ArrayList<FrameEvent>();

    CannonChipAction(Player player, Tile tile, Arena arena) {
        super(player, arena, tile);
        spritePath = "playerCannon.png";

        //If this action is created, consume chip
        //player.removeChip();
    }

    private void setupFrameEvents() {
        int fIndex[] = {0, 1, 2, 3, 4, 5};
        int sIndex[] = {0, 1, 2, 3, 4, 5};
        String sSrc[] = {"cannon", "cannon", "cannon", "cannon", "cannon", "cannon"};
        int xDis[] = {0, 0, 0, 0, 0, 0};
        int yDis[] = {0, 0, 0, 0, 0, 0};
        String tEffect[] = {"", "", "", "", "", ""};
        int dmg[] = {0, 0, 0, 0, 0, 40};
        playerCondition pcon[] = {playerCondition.INACTION, playerCondition.INACTION, playerCondition.INACTION, playerCondition.INACTION, playerCondition.INACTION, playerCondition.INACTION};
        int pIndex[] = {0, 1, 2, 3, 4, 5};
        String pSrc[] = {"playerCannon", "playerCannon", "playerCannon", "playerCannon", "playerCannon", "playerCannon"};

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

    @Override
    public void update() {
        if (player.getCondition() == playerCondition.HIT){
            isComplete = true;
            return;
        }

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
        Gson gson = new Gson();
        String message = gson.toJson(frameEventSequence);
        return message;
    }
}
