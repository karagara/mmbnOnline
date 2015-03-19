package Game;

import java.util.ArrayList;

enum playerStatus{ALIVE, STUNNED, DEAD};

public class Player {
	Connection connection;
	
	private double health;
	private playerStatus status;
	//tile position;
	private int x;
	private int y;
	
	private ArrayList<String> newActions = new ArrayList<String>();
	
	public Player(Connection connection, int x, int y)
	{
		health = 10;
		status = playerStatus.ALIVE;
		this.connection = connection;
		this.x = x;
		this.y = y;
	}
	
	public boolean isPlayer(String playerName){
		return connection.getUserName().compareTo(playerName) == 0;
	}
	
	public double getHealth()
	{
		return health;
	}
	
	public playerStatus getStatus(){
		return status;
	}
	
	public int getXPos(){
		return x;
	}
	
	public int getYPos(){
		return y;
	}
	
	public boolean isDead()
	{
		return status == playerStatus.DEAD;
	}
	
	public void changeHealth(double val)
	{
		health += val;
		if(health <= 0)
			status = playerStatus.DEAD;
	}
	
	public boolean canAttack()
	{
		return status == playerStatus.ALIVE;
	}
	
	public void move(int deltaX, int deltaY)
	{
		x += deltaX;
		y += deltaY;
	}
	
	public void addAction(String action){
		newActions.add(action);
	}
	
	public ArrayList<String> getPendingActions(){
		ArrayList<String> a = newActions;
		newActions.clear();
		return a;
	}
}
