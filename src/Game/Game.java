package Game;

import java.util.ArrayList;

import javafx.event.Event;
import com.google.gson.Gson;


enum gameStatus{ONGOING, OVER, CHIPMENU}

public class Game implements Runnable{
	private Player p1;
	private Player p2;
	private Arena arena;
	private ArrayList<Action> actions = new ArrayList<Action>();
	
	private gameStatus status;
	
	public Game(Connection c1, Connection c2)
	{
		p1 = new Player(c1, 0, 1); //starts on left
		p2 = new Player(c2, 5, 1); //starts on right
		status = gameStatus.CHIPMENU;
	}
	
	public void run()
	{
		status = gameStatus.ONGOING;
		while(!p1.isDead() && !p2.isDead())
		{
			for(String s : p1.getPendingActions())
			{
				actions.add(ActionFactory.createAction(s));
			}
			for(String s : p2.getPendingActions()){
				actions.add(ActionFactory.createAction(s));
			}
			for(Action a : actions)
			{
				a.update();
				if(a.isEventComplete())
					actions.remove(a);
			}
			updatePlayer(p1);
			updatePlayer(p2);
		}
		status = gameStatus.OVER;
		updatePlayer(p1);
		updatePlayer(p2);
		//wait for signal of both players left?
	}
	
	private void updatePlayer(Player p)
	{
		if(p.isDead())
		{
			//LOSER
		}
		else if(status == gameStatus.OVER){
			//WINNER WINNER CHICKEN DINNER
		}
		else
		{
			//Continue Fighting
		}
	}
	
	public void handleEvent(String playerName, String message)
	{
		if(p1.isPlayer(playerName))
			p1.addAction(message);
		else if(p2.isPlayer(playerName))
			p2.addAction(message);
	}
	
	public String getState(String playerName){
		GameState gs = new GameState();
		gs.state = status;
		if(p1.isPlayer(playerName)){
			gs.enemyPlayerStatus = p2.getStatus();
			gs.enemyPlayerPosition = "(" + p2.getXPos() + ", " + p2.getYPos() + ")";
			gs.myStatus = p1.getStatus();
			gs.myPosition = "(" + p1.getXPos() + ", " + p1.getYPos() + ")";
		}
		else if(p2.isPlayer(playerName))
		{
			gs.myStatus = p2.getStatus();
			gs.myPosition = "(" + p2.getXPos() + ", " + p2.getYPos() + ")";
			gs.enemyPlayerStatus = p1.getStatus();
			gs.enemyPlayerPosition = "(" + p1.getXPos() + ", " + p1.getYPos() + ")";
		}	
		
		//gs.actions = {""};
		Gson gson = new Gson();
		String message = gson.toJson(gs);
		System.out.println(message);
		return message;
	}
	
	public void playerLeft(String playerName){
		status = gameStatus.OVER;
		
	}
	
	public class GameState{
		public gameStatus state;
		public playerStatus enemyPlayerStatus;
		public String enemyPlayerPosition;
		public playerStatus myStatus;
		public String myPosition;
		public String actions[];
	}
}
