package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.Event;
import com.google.gson.Gson;

import javax.swing.*;


enum gameStatus{ONGOING, OVER, CHIPMENU}

public class Game implements Runnable, ActionListener {
    private Timer timer;

	private Player p1;
	private Player p2;
	private Arena arena = new Arena();
	private ArrayList<Action> actions = new ArrayList<Action>();
	
	private gameStatus status;
    private int menuTimer;
    private int playTimer;

    public Game(Connection c1, Connection c2)
    {
		p1 = new Player(c1, arena, 0, 1, PlayerSide.RED); //starts on left
		p2 = new Player(c2, arena, 5, 1, PlayerSide.BLUE); //starts on right
        arena.setTileEntity(0,1,p1);
        arena.setTileEntity(5,1,p2);
		status = gameStatus.CHIPMENU;
        menuTimer = 900;
        playTimer = 300;
	}
	
	public void run() {
        //setup thread timer
        System.out.println("Setting up Timer");
        timer = new Timer(33, this);
        timer.start();
        while (this.status != gameStatus.OVER){
            //do nothing, keep the thread busy
        }
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check to see if the game is still active
        if(status != gameStatus.OVER)
            this.update();
//        else
//        	if both players have left then delete self

    }

    private void update(){
        System.out.println("Updating game state");
        //Grab and process current inputs
        Input i1 = p1.getPendingInput();
        Input i2 = p2.getPendingInput();

//        System.out.println(i1.event);

        //Change state based inputs/state
        switch (status){
            case ONGOING:
                if ((i1.event.contentEquals("menu") || i2.event.contentEquals("menu")) && playTimer <= 0) {
                    menuTimer = 900; //30 ticks/second * 30 seconds
                    status = gameStatus.CHIPMENU;
                    Input tempInput = new Input();
                    tempInput.event = "";
                    tempInput.value = "";
                    i1 = tempInput;
                    i2 = tempInput;
                }
                if (p1.isDead() || p2.isDead())
                    status = gameStatus.OVER;
                break;
            case OVER:
                //Nothing to see here :P
                break;
            case CHIPMENU:
                //if time has hit limit, or both players have locked in
                if ((p1.handLock && p2.handLock) || menuTimer <= 0) {
                    p1.menuToBattle();
                    p2.menuToBattle();
                    playTimer = 300;
                    status = gameStatus.ONGOING;
                }
                break;
        }

        //Update game based on game state
        switch (status){
            case ONGOING:
                //For all game objects
                //Create Actions based upon inputs and current game state
                //Hand the object an input, and get an action back
                //Add Actions to queue
                Action a1 = p1.handleInput(i1);
                if (a1 != null)
                    synchronized (actions) {
                        actions.add(a1);
                    }
                Action a2 = p2.handleInput(i2);
                if (a2 != null)
                    synchronized (actions) {
                        actions.add(a2);
                    }


                //Update all valid actions
                synchronized (actions) {
                    for (Iterator<Action> it=actions.iterator(); it.hasNext();) {
                        Action a = it.next();
                        a.update();
                        //Remove actions that have expired
                        if (a.isEventComplete()) {
                            System.out.println("Removing Action");
                            it.remove();
                        }

                    }
                }

                //update player state with new from new info
                p1.update();
                p2.update();

                playTimer--;

                break;
            case OVER:
                break;
            case CHIPMENU:
                p1.chipMenuInput(i1);
                p2.chipMenuInput(i2);
                menuTimer--;
                break;
        }

    }
	
	public void handleEvent(String playerName, String message)
	{
        //System.out.println(message);

		if(p1.isPlayer(playerName))
			p1.addAction(message);
		else if(p2.isPlayer(playerName))
			p2.addAction(message);
	}
	
	public String getState(String playerName){
		GameState gs = new GameState();
		gs.state = status;
		if(p1.isPlayer(playerName)){
			gs.myState = p1.toString();
			gs.enemyState = p2.toString();
            gs.myChips = p1.getChipString();
		}
		else if(p2.isPlayer(playerName)){
			gs.myState = p2.toString();
			gs.enemyState = p1.toString();
            gs.myChips = p2.getChipString();
		}

        synchronized (actions) {
            for (Iterator<Action> it=actions.iterator(); it.hasNext();) {
                Action a = it.next();
                System.out.println(a.getChipSequence());
            }
        }

//		gs.actions = formatActions();
//		gs.tileStates = formatTiles();
		Gson gson = new Gson();
		String message = gson.toJson(gs);
//		System.out.println(message);

		return message;
	}
	
	private String[] formatActions(){
		String actionsJSON[] = new String[actions.size()];
		Gson gson = new Gson();
        synchronized (actions) {
            for (int i = 0; i < actions.size(); i++) {
            	Action a = actions.get(i);
                ActionState aState = new ActionState();
                aState.xPos = a.getTile().getXPos();
                aState.yPos = a.getTile().getYPos();
                aState.spriteMap = a.getSpritePath();
                aState.isComplete = a.isEventComplete();
                aState.index = a.getIndex();
                actionsJSON[i] = gson.toJson(aState);
            }
        }

		return actionsJSON;
	}
	
	private String[] formatTiles(){
		String tilesJSON[] = new String[18];
		Gson gson = new Gson();
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 6; x++){
				Tile t = arena.getTile(x, y);
				TileState tState = new TileState();
				tState.color = t.getColor();
				tState.state = t.getTerrain();
				tilesJSON[6*y + x] = gson.toJson(tState);
			}
		}
		
		return tilesJSON;
	}
	
	public void playerLeft(String playerName){
//		System.out.println(playerName + "Has left the game!");
//		status = gameStatus.OVER;
	}



    public class GameState{
		public gameStatus state;
		public String myState;
		public String enemyState;
        public String myChips;
		public String actions[];
		public String tileStates[];
	}
    
    public class ActionState{
    	public int xPos;
    	public int yPos;
    	public String spriteMap;
    	public boolean isComplete;
    	public int index;
    }
    
    public class TileState{
    	public PlayerSide color;
    	public int state;
    }
}
