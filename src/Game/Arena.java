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

    public boolean isValidMove(int x, int y, PlayerSide side) {

        System.out.println(x+", "+y);
        //check to see if x or y are oob
        if (x > 5 || x < 0 || y > 2 || y < 0)
            return false;

        //check specific tile
        Tile t = board[6*y + x];
        if (t.isOccupied() || !t.isSameSide(side))
            return false;
        else
            return true;
    }

    public Tile getTile(int x, int y) {
        return board[6*y + x];
    }

    public boolean isTileOccupied(int x, int y) {
        if (x > 5 || x < 0 || y > 2 || y < 0)
            return false;
        return board[6*y+x].isOccupied();
    }

    public void moveEntity(int oldX, int oldY, int newX, int newY, GameEntity entity){
        if (!(newX > 5 || newX < 0 || newY > 2 || newY < 0)){
            board[6*oldY + oldX].setEntity(null);
            board[6*newY + newX].setEntity(entity);
        }
    }

    public void setTileEntity(int x, int y, GameEntity entity){
        if (!(x > 5 || x < 0 || y > 2 || y < 0))
            board[6*y + x].setEntity(entity);

    }

    public GameEntity getTileEntity(int x, int y){
        if (!(x > 5 || x < 0 || y > 2 || y < 0))
            return board[6*y + x].getEntity();
        else
            return null;
    }
}
