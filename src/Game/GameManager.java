package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.post;
import Game.Game;

public class GameManager{
	
	Connection waitingPlayer;
	ArrayList<Game> games;//List of Games
	Map<String, Game> userMap; //Map players to game in games list
	
	public GameManager() {
		games = new ArrayList<Game>();
		userMap = new HashMap<String, Game>();
	}
	
	
	public String newGame(String userName) throws InterruptedException{	
		System.out.println(userName + " request a new Game");
		if(waitingPlayer != null)
		{
			Game g = new Game(waitingPlayer, new Connection(userName));
			games.add(g);
			userMap.put(userName, g);
			userMap.put(waitingPlayer.getUserName(), g);
			Thread t = new Thread(g);
			t.start();
			waitingPlayer = null;
			return "/game";
		}
		else
		{
			waitingPlayer = new Connection(userName);
			return "/Gamequeue";
		}
	}
	
	public String updateGame(String userName, String httpMessage)
	{
		System.out.println(userName + " sent an action!");
		Game g = userMap.get(userName); 
		if(g == null){
			return "ERROR";
		}
		g.handleEvent(userName, httpMessage);
		return "OKAY";
	}
	
	public String getGameState(String userName){
		System.out.println(userName + " requested a game state");
		Game g = userMap.get(userName);
		if(g == null){
			return "ERROR";
		}
		return g.getState();
	}
	
}
