package Game;

import java.util.ArrayList;

import javafx.event.Event;

 	enum gameStatus{ONGOING, PAUSED, OVER}

public class Game implements Runnable{
	private Player p1;
	private Player p2;
	private Arena arena;
	private ArrayList<Action> actions = new ArrayList<Action>();
	private ArrayList<String> newActions = new ArrayList<String>();
	private int gameId;
	
	private gameStatus status;
	
	public Game(Connection c1, Connection c2, int gameId)
	{
		p1 = new Player(c1, 0, 1); //starts on left
		p2 = new Player(c2, 5, 1); //starts on right
		//TODO: redirect players to Game Page
		status = gameStatus.PAUSED;
		this.gameId = gameId;
	}
	
	public void run()
	{
		status = gameStatus.ONGOING;
		while(!p1.isDead() && !p2.isDead())
		{
			for(String s : newActions)
			{
				actions.add(ActionFactory.createAction(s));
			}
			newActions.clear();
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
	
	public void handleEvent(String s)
	{
		newActions.add(s);
	}
}
