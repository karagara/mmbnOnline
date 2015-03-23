package Game;

import Game.Action;
import Game.Player;
import Game.Tile;
import Game.Arena;

enum MovementDirection{UP, DOWN, LEFT, RIGHT};

public class PlayerMovementAction extends Action {
	private MovementDirection direction;

	public PlayerMovementAction(Player player, Tile currentTile, Arena arena, MovementDirection direction){
		super(player, arena, currentTile);
		this.direction = direction;
		spritePath = "playerMovement.png";
		
        //ToDo: Evaluate putting the direction and status check in here
	}

	@Override
	public void update(){
        //move on the correct frame
        if (index == 3) {
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
        index++;
        if (index == 6)
            isComplete = true;
	}
}