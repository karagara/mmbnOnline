package Game;
import Game.Tile;

public class Arena {
	private Tile board[];

    public Arena(){
        board = new Tile[18];
        for (int y=0; y<3; y++){
        	for(int x=0; x<6; x++){
        		if(x < 3)
        			board[6*y + x] = new Tile(PlayerSide.RED, x, y);
        		else 
        			board[6*y + x] = new Tile(PlayerSide.BLUE, x, y);
        	}
        		
        }
    }

    public boolean isValidMove(int x, int y) {

        System.out.println(x+", "+y);
        //check to see if x or y are oob
        if (x > 5 || x < 0 || y > 2 || y < 0)
            return false;

        //check specific tile
//        if (board[6*y + x].isOccupied())
//            return false;
//        else
            return true;
    }

    public Tile getTile(int x, int y) {
        return board[6*y + x];
    }
}
