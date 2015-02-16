package game;

 	enum gameStatus{ONGOING, PAUSED, OVER}

public class Game {
	private Player p1;
	private Player p2;
	private gameStatus status;
	
	public Game(int width, int height)//change later to include addresses?
	{
		p1 = new Player(0, height/2); //starts on left
		p2 = new Player(width, height/2); //starts on right
		status = gameStatus.PAUSED;
	}
	
	public void playGame()
	{
		status = gameStatus.ONGOING;
		while(!p1.isDead() && !p2.isDead())
		{
			//check for messages from players
			//perform action
			updatePlayer(p1);
			updatePlayer(p2);
		}
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
		
	}
}
