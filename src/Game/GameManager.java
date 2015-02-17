package Game;

import java.util.LinkedList;
import java.util.Queue;

import Game.Connection;

public class GameManager{
	
	Queue<com.sun.corba.se.pept.transport.Connection> waitingPlayers = new LinkedList<Connection>();
	public GameManager()
	{
		
	}
	
	//Need to : handle case when player decides to leave the queue
	
	public void requestGame(Connection c)
	{
		if(waitingPlayers.size() == 1)
		{
			Thread t = new Thread(new Game(waitingPlayer, c, 6, 3));
			t.start();
			waitingPlayers.clear();
		}
		else
		{
			//wait your turn in queue
			waitingPlayers.add(c);
			//redirect c to queue page;
		}
	}
}