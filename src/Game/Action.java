package Game;
import java.lang.Boolean;

public abstract class Action {
	protected Player player;
	protected Arena arena;
	protected Tile tile;

    protected String spritePath;
    protected Integer index;
    protected Boolean isComplete;
    
	public abstract void update();
	
	public Action(Player player, Arena arena, Tile tile){
		this.player = player;
		this.arena = arena;
		this.tile = tile;
		index = 0;
    	isComplete = false;
	}
	
	public Boolean isEventComplete() {
        return isComplete;
    }
	
	public Player getPlayer(){
		return player;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	public Tile getTile(){
		return tile;
	}
	
	public String getSpritePath(){
		return spritePath;
	}
	
	public Integer getIndex(){
		return index;
	}
}