package Model;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//import Model.Tile.tileOwner;
//import Model.Tile.tileState;
//import Model.audioManager.Volume;

import Shared.SpriteInterface;
import Shared.UserCommands;
import Shared.UserCommands.Direction;
import Model.Tile.tileOwner;
import Model.Tile.tileState;
import Model.audioManager;

public class GameBoard {	
	enum gameBoardState{
		inTitle, 
		chipSelectMenu,
		inBattle,
		slideIn, //Not used
		slideOut,
		defeat, 
		victory,
		nextRound,
	}	
	private gameBoardState currentGameState;
	//private gameBoardState previousState;
	private ArrayList<SpriteInterface> spriteList;
	private ArrayList<Tile> tiles;
	private ArrayList<Tile> recoveryList;
	private Player player;
//	private EnemyInterface enemy;
//	private EnemyInterface enemy1;
	private BattleChips bChips;
	private MenuChips mChips;
	private TotalChips tChips;
	private BattleMenu bMenu;
	private TimerBar timerBar;
	private BattleMessage battleMessage;
	private Background background;
	private Title title;
	private int transitionCount;
	private audioManager am;
	private ArrayList<EnemyInterface> enemyList;
	private boolean isAllDead;
	private int tileRecovery;
	private int tileGrabRecovery;
	private int enemyNum = 0;
	private int roundCount;
	private boolean mettuarAdd;
	private static final int TOTALROUND = 5;
	//private Volume currentVolume;

	//private tileState currentState;
	//private tileOwner currentOwner;
	
	/* This class represent the current gameBoard that for the game. In the constructor, it create all the useful and possible object that used for the gameboard.
	 * It also include the sprites function that can set all the sprite. It can update object depend on the user input. eg, current player and current enemy. 
	 * And It can also detect the winning condition and winner of the game.
	 */
	
	public GameBoard(){
		this.roundCount = 0;
		initialBoard();
		roundCount++;
	}
	
	public void reset(){
		initialBoard();
		roundCount++;
	}
	
	public void initialBoard(){
		this.background = new Background();
		this.title = new Title();
		this.spriteList = new ArrayList<SpriteInterface>();
		this.tiles = new ArrayList<Tile>();
		this.recoveryList = new ArrayList<Tile>();
		this.enemyList = new ArrayList<EnemyInterface>();//store the multi enemy list
		this.player = new Player(100,1,2,tiles,this);
		this.battleMessage = new BattleMessage(this);
        //initial the audio manager,load all the sound file
        this.am = new audioManager();
		this.timerBar = new TimerBar();
		this.bChips = new BattleChips();
		this.tChips = new TotalChips(this);
		this.mChips = new MenuChips(tChips, bChips);		
		this.bMenu = new BattleMenu(mChips);
		player.setBattleChips(bChips);
		this.isAllDead = false;
		this.transitionCount = 0;
        

		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 6; j++){
				tiles.add(new Tile(j, i,this));
			}
		}
		initialEnemy();
		am.play(0);
		if( roundCount == 0)
		   this.currentGameState = gameBoardState.inTitle;
		else
		   this.currentGameState = gameBoardState.chipSelectMenu;
		tileRecovery = 0;
		tileGrabRecovery= 0;
		this.mettuarAdd = false;
	}
	
	public void initialEnemy(){
		//enemyList.add(new Canodumb(100,3,0,tiles,this));
		//enemyList.add(new Canodumb(100,4,1,tiles,this));
		for( int i = 0 ; i <= roundCount;i++){	
			if( roundCount < 2 ){
		       enemyList.add(new Mettuar(40,  3 + i , i,tiles,this));
		       this.getTile(3+i, i).setIsFilled(true);
			}
			else{
			   if( i < 3){
			       enemyList.add(new Mettuar(40,  3 + i , i,tiles,this));
		           this.getTile(3+i, i).setIsFilled(true);      
			   }
			   if( i >= 3 ){
			   int num = i - 3;
			   int count = 0;
		       for( int y = 0; y < 3; y++){
		    	   for ( int x = 3; x <= 5; x++){
		    		   if ( !this.getTile(x, y).isFilled() && count  <=  num ){
		    			   enemyList.add(new Canodumb(60,x,y,tiles,this));
		    			   count++;
		    		   }			   
		    	   }
		       }
			}
		  }
		}
	}
	
	public audioManager getAudioManager(){
		return am;
	}
	//This is return the desired tile that used for detecting the tile state and check the movement.
	public Tile getTile(int xPos, int yPos){
		return tiles.get(xPos + yPos * 6);	
	}
	
	public ArrayList<SpriteInterface> getSprites(){
		spriteList.clear();
		switch (currentGameState){
		case inTitle:
			spriteList.add(title);
			break;
		case inBattle:
			spriteList.add(background);
			for(int i = 0; i < tiles.size(); i++){
				spriteList.add(tiles.get(i));
			}
			spriteList.add(player);
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i));
			}
			if(bChips.getActiveChip() != null){
				spriteList.add(bChips.getActiveChip());
			}
			if(bChips.getParticles().size() > 0){
				for(int i = 0; i < bChips.getParticles().size(); i++){
					spriteList.add(bChips.getParticles().get(i));
				}
			}
			if(player.getBuster() != null){
				spriteList.add(player.getBuster());
			}
			spriteList.add(bChips);
			spriteList.add(player.getHealthSprite());
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i).getHealthSprite());
			}
			spriteList.add(timerBar);
			break;
		case chipSelectMenu:
			spriteList.add(background);
			for(int i = 0; i < tiles.size(); i++){
				spriteList.add(tiles.get(i));
			}
			spriteList.add(player);
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i));
			}
			if(bChips.getActiveChip() != null){
				spriteList.add(bChips.getActiveChip());
			}
			if(bChips.getParticles().size() > 0){
				for(int i = 0; i < bChips.getParticles().size(); i++){
					spriteList.add(bChips.getParticles().get(i));
				}
			}
			if(player.getBuster() != null){
				spriteList.add(player.getBuster());
			}
			spriteList.add(bChips);
			spriteList.add(player.getHealthSprite());
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i).getHealthSprite());
			}
			spriteList.add(timerBar);
			spriteList.add(bMenu);
			
			break;
		case slideOut:
			spriteList.add(background);
			for(int i = 0; i < tiles.size(); i++){
				spriteList.add(tiles.get(i));
			}
			spriteList.add(player);
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i));
			}
			if(bChips.getActiveChip() != null){
				spriteList.add(bChips.getActiveChip());
			}
			if(bChips.getParticles().size() > 0){
				for(int i = 0; i < bChips.getParticles().size(); i++){
					spriteList.add(bChips.getParticles().get(i));
				}
			}
			if(player.getBuster() != null){
				spriteList.add(player.getBuster());
			}
			spriteList.add(bChips);
			spriteList.add(player.getHealthSprite());
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i).getHealthSprite());
			}
			spriteList.add(timerBar);
			spriteList.add(battleMessage);
			break;
		case defeat:
			spriteList.add(background);
			for(int i = 0; i < tiles.size(); i++){
				spriteList.add(tiles.get(i));
			}
			spriteList.add(player);
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i));
			}
			if(bChips.getActiveChip() != null){
				spriteList.add(bChips.getActiveChip());
			}
			if(bChips.getParticles().size() > 0){
				for(int i = 0; i < bChips.getParticles().size(); i++){
					spriteList.add(bChips.getParticles().get(i));
				}
			}
			if(player.getBuster() != null){
				spriteList.add(player.getBuster());
			}
			spriteList.add(bChips);
			spriteList.add(player.getHealthSprite());
			for(int i = 0; i< enemyList.size();i++){
				spriteList.add(enemyList.get(i).getHealthSprite());
			}
			spriteList.add(timerBar);
			spriteList.add(battleMessage);
			break;
		case victory:
			spriteList.add(background);
			for(int i = 0; i < tiles.size(); i++){
				spriteList.add(tiles.get(i));
			}
			spriteList.add(player);
//			for(int i = 0; i< enemyList.size();i++){
//				spriteList.add(enemyList.get(i));
//			}
			if(bChips.getActiveChip() != null){
				spriteList.add(bChips.getActiveChip());
			}
			if(bChips.getParticles().size() > 0){
				for(int i = 0; i < bChips.getParticles().size(); i++){
					spriteList.add(bChips.getParticles().get(i));
				}
			}
			if(player.getBuster() != null){
				spriteList.add(player.getBuster());
			}
			spriteList.add(bChips);
			spriteList.add(player.getHealthSprite());
//			for(int i = 0; i< enemyList.size();i++){
//				spriteList.add(enemyList.get(i).getHealthSprite());
//			}
			spriteList.add(timerBar);
			spriteList.add(battleMessage);
			break;
		default:
			break;
		}
		return spriteList;
	}
	
	public void update(UserCommands input){
		//get the recovery of the tile from the break
//	    for( int i = 0; i < tiles.size(); i++){
//	    	if( tiles.get(i).isTileBreak() ){
//	    		tiles.get(i).setCurrentState(tileState.Damaged);
//	    		tileRecovery++;
//	    		System.out.println(tileRecovery);
//	    		for( int j = 0; j < enemyList.size() - 1; j++){
//	    			if( (enemyList.get(j).getXpos() + enemyList.get(j).getYpos() * 6) == i){
//	    				enemyList.get(j).setIsLock(true);
//	    				enemyNum = j;
//	    			}
//	    		}
//	  	    	if( tileRecovery == 150){// the time for lock the enemy when the enemy in the break tile
//	  	    		System.out.println("The tile is Recovery");// testing the recovery tile
//	 	    		tiles.get(i).setIsBreak(false);
//	 	    		tiles.get(i).setCurrentState(tileState.Normal);
//	 	    		enemyList.get(enemyNum).setIsLock(false);
//	 	    		tileRecovery = 0;
//	    		}
//	    	}
//	    	
//	    	if( tiles.get(i).isTileGrab()){
//	    		if( tiles.get(i).isTileGrab() && this.getPlayer().getXpos() != tiles.get(i).getxIndex() && this.getPlayer().getYpos() != tiles.get(i).getyIndex()){
//	    			tileGrabRecovery++;
//	    		}
//	    		System.out.println(tileGrabRecovery);
//	    		tiles.get(i).setCurrentOwner(tileOwner.player);
//	    		if(tileGrabRecovery == 150 ){
//	    			this.getTile(3, 0).updateOwner(2);
//	    			this.getTile(3, 1).updateOwner(2);
//	    			this.getTile(3, 2).updateOwner(2);
//	    			this.getTile(3, 0).setIsGrab(false);
//	    			this.getTile(3, 1).setIsGrab(false);
//	    			this.getTile(3, 2).setIsGrab(false);
//	    			tileGrabRecovery = 0;
//	    		}
//	    		System.out.println(tiles.get(i).isTileGrab());
//	    		tiles.get(i).setCurrentOwner(tiles.get(i).getOriginalOwner());
//	    	}
//	    }

		
		
		//determine the victory or defeat condition
		if(player.getIsDead()){
			currentGameState = gameBoardState.defeat;
		}
		if( !player.getIsDead()&& roundCount == TOTALROUND){
			currentGameState = gameBoardState.victory;	
		}
		
		if( isAllDead && roundCount != TOTALROUND ){
			currentGameState = gameBoardState.nextRound;	
		}
		
		
		for(int i = 0;i < enemyList.size();i++){
			if(enemyList.get(i).getHealth()==0 && currentGameState == gameBoardState.inBattle){
				EnemyInterface removedEnemy = enemyList.get(i);
				enemyList.remove(removedEnemy);
		   }
			if(enemyList.size() == 0)
				  isAllDead = true;
		}

//		if (input.getAPressed()){
//			if(currentGameState == gameBoardState.chipSelectMenu){
//				currentGameState = gameBoardState.inBattle;
//			}
//		}
		
		if(input.getEnterPressed() && currentGameState == gameBoardState.inTitle){
			currentGameState = gameBoardState.chipSelectMenu;
		}
		
		switch (currentGameState){
		case inTitle:
			title.update();
			break;
		case inBattle:
			if (input.getSpacePressed()){
					if (timerBar.isTimerFull()){
						currentGameState = gameBoardState.chipSelectMenu;
						timerBar.resetTimer();
					}
			}
			player.update(input);
			for(int i = 0; i< enemyList.size();i++){
				enemyList.get(i).update();
			}
			
			 for( int i = 0; i < tiles.size(); i++){
			    	if( tiles.get(i).isTileBreak() ){
			    		tiles.get(i).setCurrentState(tileState.Damaged);
			    		tileRecovery++;
			    		System.out.println(tileRecovery);
			    		for( int j = 0; j < enemyList.size() - 1; j++){
			    			if( (enemyList.get(j).getXpos() + enemyList.get(j).getYpos() * 6) == i){
			    				enemyList.get(j).setIsLock(true);
			    				enemyNum = j;
			    			}
			    		}
			  	    	if( tileRecovery == 150){// the time for lock the enemy when the enemy in the break tile
			  	    		System.out.println("The tile is Recovery");// testing the recovery tile
			 	    		tiles.get(i).setIsBreak(false);
			 	    		tiles.get(i).setCurrentState(tileState.Normal);
			 	    		enemyList.get(enemyNum).setIsLock(false);
			 	    		tileRecovery = 0;
			    		}
			    	}
			    	
			    	if( tiles.get(i).isTileGrab()){
			    		if( tiles.get(i).isTileGrab() && this.getPlayer().getXpos() != tiles.get(i).getxIndex() && this.getPlayer().getYpos() != tiles.get(i).getyIndex()){
			    			tileGrabRecovery++;
			    		}
			    		System.out.println(tileGrabRecovery);
			    		tiles.get(i).setCurrentOwner(tileOwner.player);
			    		if(tileGrabRecovery == 150 ){
			    			this.getTile(3, 0).updateOwner(2);
			    			this.getTile(3, 1).updateOwner(2);
			    			this.getTile(3, 2).updateOwner(2);
			    			this.getTile(3, 0).setIsGrab(false);
			    			this.getTile(3, 1).setIsGrab(false);
			    			this.getTile(3, 2).setIsGrab(false);
			    			tileGrabRecovery = 0;
			    		}
			    		System.out.println(tiles.get(i).isTileGrab());
			    		tiles.get(i).setCurrentOwner(tiles.get(i).getOriginalOwner());
			    	}
			    }
			
//			enemy.update();
//			enemy1.update();
			timerBar.update();
			background.update();
			bChips.updateChips();
			break;
		case chipSelectMenu:
			bMenu.update(input);
			if (bMenu.isChipsSelected()){
				bMenu.resetBattleMenuStates();
				currentGameState = gameBoardState.slideOut;
			}
			background.update();
			break;
		case slideOut:
			battleMessage.update();
			transitionCount++;
			if(transitionCount >= 10){
				currentGameState = gameBoardState.inBattle;
				transitionCount = 0;
			}
			background.update();
			break;
		case defeat:
			battleMessage.update();
			background.update();
			break;
		case victory:
		    battleMessage.update();
			background.update();
			if(input.getEnterPressed()){
				am.stop();
				roundCount = 0;
				initialBoard();
			}
			break;
		default:
			break;
		}
	}
	
	public int getRoundNum(){
		return roundCount;
	}
	
	//get the current player object in this gameboard
	//Get current player object 
	public Player getPlayer(){
		return player;
	}

//	public EnemyInterface getEnemy(){
//		return enemy;
//	}
	
	public ArrayList<EnemyInterface> getEnemyList(){
        return enemyList;
	}
	
	public gameBoardState getGameBoardState(){
		return currentGameState;
	}
	
	public boolean isValidMove(int xPos, int yPos, Direction direction, tileOwner tOwner){
		boolean isValidMove = true;
		//check to see if the movement is even on the map
		if(xPos == 0 && direction == Direction.left)
			isValidMove = false;
		if(xPos == 5 && direction == Direction.right)
			isValidMove = false;
		if(yPos == 0 && direction == Direction.up)
			isValidMove = false;
		if(yPos == 2 && direction == Direction.down)
			isValidMove = false;
		
		if(!isValidMove){
			return isValidMove;
		}
		
		
		switch (direction){
		case up:
			yPos += -1;
			break;
		case down:
			yPos += 1;
			break;
		case left:
			xPos += -1;
			break;
		case right:
			xPos += 1;
			break;
		default:
			break;
		}
		
		//check if the tile is owned by the correct side
		if(!(tiles.get((yPos * 6) + xPos).getCurrentOwner() == tOwner)){
			isValidMove = false;
		}
		
		//check to see if the tile is broken
		if(tiles.get((yPos * 6) + xPos).isTileBreak()){
			isValidMove = false;
		}
		
		
		//check for other entities.
		if(player.getXpos() == xPos && player.getYpos() == yPos){
			isValidMove = false;
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			if (enemyList.get(i).getXpos() == xPos && enemyList.get(i).getYpos() == yPos)
				isValidMove = false;
		}
		
		return isValidMove;



	}
}
