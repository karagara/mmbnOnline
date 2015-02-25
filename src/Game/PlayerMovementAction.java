package Game;

import Game.Action;
import Game.Player;
import Game.Tile;
import Game.Arena;

public class PlayerMovementAction implements Action {

	private Player player;
	private Tile currentTile;
	private Arena arena;
	private int direction;
	private Boolean isComplete = false;

	public PlayerMovementAction(Player player, Tile currentTile, Arena arena, int direction){
		this.player = player;
		this.currentTile = currentTile;
		this.arena = arena;
		this.direction = direction;
	}

	@Override
	public void update(){
		// //Check player state to see if they are free to move
		// if (player.state == Player.CLEAR){
		// 	//move the player based on the direction
		// }
		// isComplete = true;
	}

	@Override
	public Boolean isEventComplete(){
		return isComplete;
	}
}