package Game;
import Game.Tile;

public class Arena {
	private Tile board[];

    public Arena(){
        board = new Tile[18];
        for (int i=0; i<18; i++){
            board[i] = new Tile();
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

    public boolean isTileOccupied(int xPos, int yPos) {
        return false;
    }
}
