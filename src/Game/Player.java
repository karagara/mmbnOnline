package Game;
enum playerStatus{ALIVE, STUNNED, DEAD};

public class Player {
	Connection connection;
	
	private double health;
	private playerStatus status;
	//tile position;
	private int x;
	private int y;
	
	public Player(Connection connection, int x, int y)
	{
		health = 10;
		status = playerStatus.ALIVE;
		this.connection = connection;
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
