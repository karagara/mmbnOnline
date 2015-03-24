package Game;

import Game.Action;
import Game.Player;
import Game.Tile;
import Game.Arena;

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
        FrameEvent frame = new FrameEvent();
        //Frame 1
        frame.frameIndex = 0;
        frame.sprite = "";
        switch (direction){
            case UP:
                frame.xDelta = player.getXPos();
                frame.yDelta = player.getYPos()+1;
                break;
            case DOWN:
                frame.xDelta = player.getXPos();
                frame.yDelta = player.getYPos()-1;
                break;
            case LEFT:
                frame.xDelta = player.getXPos()-1;
                frame.yDelta = player.getYPos();
                break;
            case RIGHT:
                frame.xDelta = player.getXPos()+1;
                frame.yDelta = player.getYPos();
                break;
        }
        frame.tileEffect="";
        frame.damage=0;
        frame.playerState = "INACTION";
        frame.playerSprite = "{\"spriteSrc\":\"playerMovement\",\"spriteIndex\":0}";

        frameEventSequence.add(frame);

        //Frame 2
        frame.playerSprite = "{\"spriteSrc\":\"playerMovement\",\"spriteIndex\":1}";

        frameEventSequence.add(frame);

        //Frame 3
        frame.playerSprite = "{\"spriteSrc\":\"playerMovement\",\"spriteIndex\":2}";

        frameEventSequence.add(frame);

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
        return null;
    }
}