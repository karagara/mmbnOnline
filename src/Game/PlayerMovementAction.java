package Game;

import Game.Action;
import Game.Player;
import Game.Tile;
import Game.Arena;
import com.google.gson.Gson;

import java.util.ArrayList;

enum MovementDirection{UP, DOWN, LEFT, RIGHT};

public class PlayerMovementAction extends Action {
	private MovementDirection direction;
    private ArrayList<FrameEvent> frameEventSequence = new ArrayList<FrameEvent>();

	public PlayerMovementAction(Player player, Tile currentTile, Arena arena, MovementDirection direction){
		super(player, arena, currentTile);
		this.direction = direction;
        this.setupFrameEvents();
		spritePath = "playerMovement.png";

        //ToDo: Evaluate putting the direction and status check in here
	}

    private void setupFrameEvents() {
        int fIndex[] = {0,1,2};
        int sIndex[] = {0,0,0};
        String sSrc[] = {"","",""};

        int xDis[] = {0,0,0};
        int yDis[] = {0,0,0};

        int x = player.getXPos();
        int y = player.getYPos();
        switch (direction){
            case UP:
                xDis = new int[]{0,0,0};
                yDis = new int[]{1,1,1};
                break;
            case DOWN:
                xDis = new int[]{0,0,0};
                yDis = new int[]{-1,-1,-1};
                break;
            case LEFT:
                xDis = new int[]{-1,-1,-1};
                yDis = new int[]{0,0,0};
                break;
            case RIGHT:
                xDis = new int[]{1,1,1};
                yDis = new int[]{0,0,0};
                break;
        }
        String tEffect[] = {"","",""};
        int dmg[] = {0,0,0};
        playerCondition pcon[] = {playerCondition.INACTION,playerCondition.INACTION,playerCondition.INACTION};
        int pIndex[] = {0,1,2};
        String pSrc[] = {"playerMovement","playerMovement","playerMovement"};

        for (int i=0; i < 3; i++){
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
	public void update(){
    	System.out.println("Updating movement for " + player.connection.getUserName());
        //move on the correct frame
//        System.out.println(index);
//        System.out.println(isComplete);
        if (index == 0) {
            //Check for valid state here?
            int x = player.getXPos();
            int y = player.getYPos();
            switch (direction){
                case UP:
                    arena.moveEntity(x, y, x, y+1, player);
                    player.move(0,1);
                    break;
                case DOWN:
                    arena.moveEntity(x, y, x, y-1, player);
                    player.move(0,-1);
                    break;
                case LEFT:
                    arena.moveEntity(x, y, x-1, y, player);
                    player.move(-1,0);
                    break;
                case RIGHT:
                    arena.moveEntity(x, y, x+1, y, player);
                    player.move(1,0);
                    break;
            }
        }

        if (index == 3) {
            System.out.println("Clearing Action");
            isComplete = true;
            this.player.setCondition(playerCondition.CLEAR);
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