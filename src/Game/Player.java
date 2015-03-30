package Game;

import com.google.gson.Gson;

import java.util.ArrayList;


enum playerCondition{CLEAR, CHARGING, CHARGED, HIT, RECOVERING, INACTION, STUNNED, DEAD}
enum playerAction{NONE,BUSTER,CANNON,SWORD,BOMB}

public class Player implements GameEntity {
    public Connection connection;

	private double health;
    private playerCondition condition;
	playerAction action;
    int actionIndex;
    Tile position;
    Arena arena;
	private int x;
	private int y;
    private PlayerSide side;
    private int chargeCount = 0;
    private int hitIndex = 0;
    private int recoveryIndex = 0;
    private int menuPointer = 0;
    boolean handLock = false;
    ChipManager chipManager;

	private ArrayList<String> newActions = new ArrayList<String>();

	public Player(Connection connection, Arena arena, int x, int y, PlayerSide side) {
		health = 10;
		this.connection = connection;
        this.arena = arena;
		this.x = x;
		this.y = y;
        this.condition = playerCondition.CLEAR;
        this.side = side;
        this.action = playerAction.NONE;
        this.actionIndex = 0;
        this.chipManager = new ChipManager(this, arena);
	}
	
	public String toString(){
		PlayerState ps = new PlayerState();
		ps.x = x;
		ps.y = y;
		ps.condition = condition;
		ps.health = health;
        ps.side = side;
        ps.action = action;
        ps.actionIndex = actionIndex;
		Gson gson = new Gson();
		return gson.toJson(ps);
	}

    public void clearPlayerAction(){
        action = playerAction.NONE;
        actionIndex = 0;
    }

    public void setPlayerAction(playerAction a, int ai){
        action = a;
        actionIndex = ai;
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

    public PlayerSide getSide() {
        return this.side;
    }

	public boolean isDead()
	{
		return health <= 0;
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
        x += deltaX;
        y += deltaY;
        position = arena.getTile(x, y);

        System.out.println(x + ", " + y);
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
//            System.out.println(newActions.size());
            if (!newActions.isEmpty()) {
                inString = newActions.get(0);
                System.out.println(inString);
            }
            newActions.clear();
        }


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

        if (input == null){
            input = new Input();
            input.event = "";
            input.value = "";
        }
//        if (input != null && input.value != null)
//            System.out.println(input.value);

        return input;
    }

    public Action handleInput(Input input) {
        if (input==null)
            return null;
//        if (input.value == null)


        if (this.condition == playerCondition.CLEAR || this.condition == playerCondition.RECOVERING
                || this.condition == playerCondition.CHARGING || this.condition == playerCondition.CHARGED){
            if (input.event.contentEquals("movement")){
                System.out.println(input.value);
                //For each direction
                //Check to see if the tile is available to be moved on
                //If yes, create a movement action and return it
                if (input.value.contentEquals("up") && arena.isValidMove(x, y-1, this.side)){

                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.UP );
                } else if (input.value.contentEquals("down") && arena.isValidMove(x, y+1, this.side)) {
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.DOWN );
                } else if (input.value.contentEquals("left") && arena.isValidMove(x-1, y, this.side)) {
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.LEFT );
                } else if (input.value.contentEquals("right") && arena.isValidMove(x+1, y, this.side)) {
                    this.condition = playerCondition.INACTION;
                    return new PlayerMovementAction(this, arena.getTile(x,y), arena, MovementDirection.RIGHT );
                }
            }

            if (input.event.contentEquals("buster")) {
                System.out.println(input.value);
                if (input.value.contentEquals("down")){
                    if(! (this.condition == playerCondition.CHARGED))
                        this.condition = playerCondition.CHARGING;
                }
                if (input.value.contentEquals("up")) {
                    System.out.println("Buster Away!");
                    boolean fullyCharged = false;
                    if(this.condition == playerCondition.CHARGED)
                        fullyCharged = true;
                    this.condition = playerCondition.INACTION;
                    this.chargeCount = 0;
                    return new PlayerBusterAction(this, this.position, this.arena, fullyCharged);
                }

            }

            if (input.event.contentEquals("chip")) {
                Chip bChip = chipManager.getTopBattleChip();
                System.out.println("Using a chip");
                if ( bChip != null){
                    this.condition = playerCondition.INACTION;
                    return bChip.createAction();
                }
            }
        }

        return null;
    }

    public void update(){
        if(this.condition == playerCondition.CHARGING){
            System.out.println("Charging buster, count: "+chargeCount);
            chargeCount++;
            if (chargeCount > 20){
                condition = playerCondition.CHARGED;
                chargeCount = 0;
            }
        }
        else if(this.condition == playerCondition.HIT){
        	hitIndex++;
        	System.out.println("THAT HURT!");
        	if(hitIndex == 3){
        		condition = playerCondition.RECOVERING;
        		hitIndex = 0;
        	}
        }
        else if(this.condition == playerCondition.RECOVERING){
        	recoveryIndex++;
        	System.out.println("Wait for it..");
        	if(recoveryIndex == 30){
        		System.out.println("WOO");
        		condition = playerCondition.CLEAR;
        		recoveryIndex = 0;
        	}
        }
    }

    @Override
    public void damageEntity(int damage) {
        this.changeHealth(-damage);
        this.condition = playerCondition.HIT;
        this.clearPlayerAction();
        hitIndex = 0;
    }

    @Override
    public String getState() {
        return null;
    }

    public void incActionIndex() {
        actionIndex++;
    }

    public String getChipString() {
        ArrayList<Chip> mchips = chipManager.mchips.getMenuChipList();
        ArrayList<Chip> qchips = chipManager.mchips.getQueuedList();
        ArrayList<Boolean> bools = chipManager.mchips.getIsSelectedList();
        //get menu chips

        ArrayList<playerChips.JSONMenuChip> jmc = new ArrayList<playerChips.JSONMenuChip>();
        for (int i = 0; i < mchips.size(); i++){
            playerChips.JSONMenuChip jc = new playerChips.JSONMenuChip();
            jc.type = mchips.get(i).chip;
            jc.letter = mchips.get(i).l;
            jc.isSelected = bools.get(i);
            jmc.add(jc);
        }
        //get selected chips
        ArrayList<playerChips.JSONSelectedChip> jsc = new ArrayList<playerChips.JSONSelectedChip>(qchips.size());
        for (int i = 0; i < qchips.size(); i++){
            playerChips.JSONSelectedChip jc = new playerChips.JSONSelectedChip();
            jc.type = qchips.get(i).chip;
            jsc.add(jc);
        }

        playerChips pchips = new playerChips();
        pchips.menuChips = jmc;
        pchips.selectedChips = jsc;
        pchips.cursorPos = menuPointer;

        Gson json = new Gson();
        String output = json.toJson(pchips);
        return output;
    }

    public void chipMenuInput(Input input) {
        if (handLock == false) {
            if (input.event.contentEquals("movement")) {
                if (input.value.contentEquals("left")) {
                    System.out.println("move cursor left");
                    if (menuPointer > 0)
                        menuPointer--;
                } else if (input.value.contentEquals("right")) {
                    System.out.println("move cursor left");
                    if (menuPointer < 3)
                        menuPointer++;
                }
            }

            if (input.event.contentEquals("buster")) {
                chipManager.removeLastHandChip();
            }

            if (input.event.contentEquals("chip")) {
                chipManager.addChipToHand(menuPointer);
            }

            if (input.event.contentEquals("menu")) {
                handLock = true;
            }
        }
    }

    public void menuToBattle(){
        handLock = false;
        chipManager.lockInHand();
        menuPointer = 0;
    }

    public class PlayerState{
    	public int x;
    	public int y;
    	public double health;
    	public playerCondition condition;
        public PlayerSide side;
        public playerAction action;
        public int actionIndex;
    }


}

