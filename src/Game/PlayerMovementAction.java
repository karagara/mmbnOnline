package Game;

import Game.Action;
import Game.Player;
import Game.Tile;
import Game.Arena;

enum MovementDirection{UP, DOWN, LEFT, RIGHT};

public class PlayerMovementAction implements Action {
	private Player player;
	private Tile currentTile;
	private Arena arena;
	private MovementDirection direction;
    private String spritePath = "playerMovement.png";;
    private Integer index = 0;
	private Boolean isComplete = false;

	public PlayerMovementAction(Player player, Tile currentTile, Arena arena, MovementDirection direction){
		this.player = player;
		this.currentTile = currentTile;
		this.arena = arena;
		this.direction = direction;

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

	@Override
	public Boolean isEventComplete(){
		return isComplete;
	}
}