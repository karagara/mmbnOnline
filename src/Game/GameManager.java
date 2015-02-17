package Game;

import java.util.ArrayList;

public class GameManager{
	
	Connection waitingPlayer;
	ArrayList<Game> games = new ArrayList<Game>();
	int gameCount;
	
	public GameManager()
	{
		gameCount = 0;
	}
	
	//Need to : handle case when player decides to leave the queue
	
	public void newGame(Connection c)
	{
		if(waitingPlayer)
		{
			//maybe add check if id already exists?
			Game g = new Game(waitingPlayer, c, gameCount++);
			games.add(g);
			Thread t = new Thread(g);
			t.start();
			waitingPlayer = null
		}
		else
		{
			waitingPlayer = c;
			//redirect c to queue page;
		}
	}
	
	public void updateGame(Connection s)
	{
		for(Game g : games)
		{
			//if g.getId() matches
			//g.handleEvent();
			//break;
		}
	}
}