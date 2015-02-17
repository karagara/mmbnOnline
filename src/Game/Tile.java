package Game;

import Game.GameEntity;

public class Tile {

	int terrain;
	int xPos;
	int yPos;
	GameEntity object;
	int ownership;

	public void setOwnership(int newOwnership){
		this.ownership=newOwnership;
	}
	public void setXPos(int newX){
		this.xPos = newX;
	}
	public void setYPos(int newX){
		this.yPos = newX;
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
	public int getEntity(){
		return this.object;
	}
	public int getTerrain(){
		return this.terrain;
	}

	
}
