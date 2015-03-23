package Game;

import com.google.gson.Gson;

import java.util.ArrayList;

enum playerCondition{CLEAR, HIT, RECOVERING, INACTION, STUNNED, DEAD}

public class Player implements GameEntity {
    public Connection connection;

	private double health;
    private playerCondition condition;
	Tile position;
    Arena arena;
	private int x;
	private int y;
    private PlayerSide side;

	private ArrayList<String> newActions = new ArrayList<String>();

	public Player(Connection connection, Arena arena, int x, int y, PlayerSide side) {
		health = 10;
		this.connection = connection;
        this.arena = arena;
		this.x = x;
		this.y = y;
        this.condition = playerCondition.CLEAR;
        this.side = side;
	}

	public boolean isPlayer(String playerName){
		return connection.getUserName().compareTo(playerName) == 0;
	}

	public double getHealth() {
		return health;
	}

    public playerCondition getCondition(){
        return condition;
    }

    public void setCondition(playerCondition con){
        this.condition = con;
    }

	public int getXPos(){
		return x;
	}

	public int getYPos(){
		return y;
	}

	public boolean isDead()
	{
		return condition == playerCondition.DEAD;
	}

	public void changeHealth(double val)
	{
		health += val;
		if(health <= 0)
			condition = playerCondition.DEAD;
	}

	public boolean canAttack()
	{
		return condition == playerCondition.CLEAR;
	}

	public void move(int deltaX, int deltaY)
	{
		if(arena.isValidMove(x + deltaX, y + deltaY)){
			x += deltaX;
			y += deltaY;
			position = arena.getTile(x, y);
			System.out.println(x + ", " + y);
		}
	}

	public void addAction(String action){
		synchronized (newActions){
            newActions.add(action);
        }
	}

    public Input getPendingInput() {
        //Grab first input, clear the rest
        String inString = "";
        synchronized (newActions) {
            System.out.println(newActions.size());
            if (!newActions.isEmpty())
                inString = newActions.get(0);
            newActions.clear();
        }

//        System.out.println(inString);
        //If we got a string, try to parse it
        Input input = new Input();
        if (inString != null) {
            Gson json = new Gson();
            try {
                input = json.fromJson(inString, Input.class);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
//        if (input != null && input.value != null)
//            System.out.println(input.value);

        return input;
    }

    public Action handleInput(Input input) {
        if (input==null)
            return null;
//        if (input.value == null)

        if (this.condition == playerCondition.CLEAR || this.condition == playerCondition.RECOVERING){
            if (input.event.contentEquals("movement")){
                System.out.println(input.value);
                //For each direction
                //Check to see if the tile is available to be moved on
                //If yes, create a movement action and return it
                if (input.value.contentEquals("up") && arena.isValidMove(x, y+1)){
                    System.out.println("Creating up action!");
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.UP );
                } else if (input.value.contentEquals("down") && arena.isValidMove(x, y-1)) {
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.DOWN );
                } else if (input.value.contentEquals("left") && arena.isValidMove(x-1, y)) {
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.LEFT );
                } else if (input.value.contentEquals("right") && arena.isValidMove(x+1, y)) {
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.RIGHT );
                }
            }

            if (input.event.contentEquals("buster")) {
                this.condition = playerCondition.INACTION;
                return new PlayerBusterAction(this, this.position, this.arena);
            }
        }

        return null;
    }

    @Override
    public String getState() {
        return null;
    }
}
