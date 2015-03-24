package Game;

import Game.GameEntity;

public class Tile {

	int terrain;
	int xPos;
	int yPos;
	GameEntity object;
	PlayerSide color;

    public Tile(PlayerSide color, int xPos, int yPos){
    	this.color = color;
    	this.xPos = xPos;
    	this.yPos = yPos;
    }

	public void setColor(PlayerSide color){
		this.color = color;
	}
	
	public void setTerrain(int newTerrainState){
		this.terrain= newTerrainState;
	}
	public int getXPos(){
		return this.xPos;
	}
	public int getYPos(){
		return this.yPos;
	}
	public GameEntity getEntity(){
		return this.object;
	}
	public int getTerrain(){
		return this.terrain;
	}
	public PlayerSide getColor(){
		return this.color;
	}
    public boolean isOccupied(){
        if (this.object == null)
            return false;
        else
            return true;
    }

	
}
