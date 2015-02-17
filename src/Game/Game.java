package Game;

import java.util.ArrayList;

import javafx.event.Event;

 	enum gameStatus{ONGOING, PAUSED, OVER}

public class Game implements Runnable{
	private Player p1;
	private Player p2;
	private Arena arena;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	private gameStatus status;
	
	public Game(Connection c1, Connection c2, int width, int height)
	{
		p1 = new Player(c1, 0, height/2); //starts on left
		p2 = new Player(c2, width, height/2); //starts on right
		//TODO: redirect players to Game Page
		status = gameStatus.PAUSED;
	}
	
	public void run()
	{
		status = gameStatus.ONGOING;
		while(!p1.isDead() && !p2.isDead())
		{
			//check for messages from players
			//create new events
			for(Event e : events)
			{
				e.update();
				if(e.isEventComplete())
					events.remove(e);
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
}
