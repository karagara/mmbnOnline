package Game;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by karagara on 21/03/15.
 */
public class PlayerBusterAction extends Action{

    private boolean isCharged;
    private ArrayList<FrameEvent> frameEventSequence = new ArrayList<FrameEvent>();

    PlayerBusterAction(Player player, Tile tile, Arena arena, boolean isCharged) {
    	super(player, arena, tile);
    	spritePath =  "playerBuster.png";
        this.isCharged = isCharged;
        player.action = playerAction.BUSTER;
        player.actionIndex = 0;
    }

    private void setupFrameEvents() {
        int fIndex[] = {0,1,2,3};
        int sIndex[] = {0,1,2,3};
        String sSrc[] = {"buster","buster","buster","buster"};
        int xDis[] = {0,0,0,0};
        int yDis[] = {0,0,0,0};
        String tEffect[] = {"","","","","",""};
        int dmg[] = {0,0,0,0};
        playerCondition pcon[] = {playerCondition.INACTION,playerCondition.INACTION,playerCondition.INACTION,playerCondition.INACTION};
        int pIndex[] = {0,1,2,3};
        String pSrc[] = {"playerBuster","playerBuster","playerBuster","playerBuster"};

        for (int i=0; i < 4; i++){
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
        //on Attack frame, check for targets
        if (player.getCondition() == playerCondition.HIT){
            player.clearPlayerAction();
            isComplete = true;
            return;
        }

        if (index == 4){
            //apply damage to first in row
            //arena.damageFirstInRow(player.getYPos(), 10);
            boolean hasHitTarget = false;
            if (player.getSide() == PlayerSide.RED){
                for(int i=player.getXPos()+1; i < 6 && !hasHitTarget; i++){
                    if(arena.isTileOccupied(i,player.getYPos())){
                        GameEntity entity = arena.getTileEntity(i, player.getYPos());
                        entity.damageEntity((isCharged) ? 10 : 1);
                        System.out.println("damaged " + isCharged);
                        hasHitTarget = true;
                    }
                }
            }

            if (player.getSide() == PlayerSide.BLUE){
                for(int i=player.getXPos()-1; i >= 0 && !hasHitTarget; i--){
                    if(arena.isTileOccupied(i,player.getYPos())){
                        GameEntity entity = arena.getTileEntity(i, player.getYPos());
                        if (entity != null)
                            entity.damageEntity((isCharged) ? 10 : 1);
                        hasHitTarget = true;
                    }
                }
            }
            player.setCondition(playerCondition.CLEAR);
            player.clearPlayerAction();
            isComplete = true;
        }
        if (!isComplete){
            player.incActionIndex();
            index++;
        }
    }

    @Override
    public String getChipSequence() {
        Gson gson = new Gson();
        String message = gson.toJson(frameEventSequence);
        return message;
    }
}
