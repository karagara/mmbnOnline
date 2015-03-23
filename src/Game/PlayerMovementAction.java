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
        //move on the correct frame
        if (index == 0) {
            //Check for valid state here?
            switch (direction){
                case UP:
                    player.move(0,1);
                    break;
                case DOWN:
                    player.move(0,-1);
                    break;
                case LEFT:
                    player.move(-1,0);
                    break;
                case RIGHT:
                    player.move(1,0);
                    break;
            }
        }
        if (index == 3)
            isComplete = true;

        index++;

	}

    @Override
    public String getChipSequence() {
        return null;
    }
}