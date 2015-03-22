package Game;

import com.google.gson.Gson;

import java.util.ArrayList;

enum playerStatus{ALIVE, DEAD};
enum playerCondition{CLEAR, HIT, RECOVERING, INACTION, STUNNED}

public class Player {
	Connection connection;

	private double health;
	private playerStatus status;
    private playerCondition condition;
	Tile position;
    Arena arena;
	private int x;
	private int y;

	private ArrayList<String> newActions = new ArrayList<String>();

	public Player(Connection connection, Arena arena, int x, int y) {
		health = 10;
		status = playerStatus.ALIVE;
		this.connection = connection;
        this.arena = arena;
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

    public Input getPendingInput() {
        //Grab first input, clear the rest
        String inString = newActions.get(0);
        newActions.clear();

        //If we got a string, try to parse it
        Input input = new Input();
        if (inString != null) {
            Gson json = new Gson();
            try {
                json.fromJson(inString, Input.class);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return input;
    }

    public Action handleInput(Input input) {
        if (this.condition == playerCondition.CLEAR || this.condition == playerCondition.RECOVERING){
            if (input.event == "move"){
                //For each direction
                //Check to see if the tile is available to be moved on
                //If yes, create a movement action and return it
                boolean isVaildMovement = false;

                if (input.value == "up"){

                } else if (input.value == "down") {

                } else if (input.value == "left") {

                } else if (input.value == "right") {

                }

                if (isVaildMovement){
                    this.condition = playerCondition.INACTION;
                }
            }

            if (input.event == "buster") {
                this.condition = playerCondition.INACTION;
                return new PlayerBusterAction(this, this.position, this.arena);
            }
        }

        return null;
    }
}
