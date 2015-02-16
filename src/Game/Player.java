package game;
enum playerStatus{ALIVE, STUNNED, DEAD};

public class Player {
	private double health;
	private playerStatus status;
	//tile position;
	private int x;
	private int y;
	
	public Player(int x, int y)
	{
		health = 10;
		status = playerStatus.ALIVE;
		this.x = x;
		this.y = y;
	}
	
	public double getHealth()
	{
		return health;
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
	
}
