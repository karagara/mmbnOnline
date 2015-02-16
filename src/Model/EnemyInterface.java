package Model;

import Shared.SpriteInterface;

//The interface of the enemy.
public interface EnemyInterface extends SpriteInterface{
	     
	     // set the health
		 public void setHealth(int health);
		 
		 //set the x position of the enemy
		 public void setXpos(int xPos);
		 
		 //get the current enemy x position
		 public int getXpos();
		 
		 //get the curernt enemy y position
		 public int getYpos();
		 
		 //set the y position of the enemy
		 public void setYpos(int yPos);
		 
		 //get the current health of the enemy
		 public int getHealth();
		 
		 //return the SpriteInterface corresponding to the enemy's health
		 public SpriteInterface getHealthSprite();
		 
		 //update the enemy object,including the sprite and state and the current health
		 public void update();
		 
		 //update the enemy sprite depend on the different state 
		 public void updateSprite();
		 
		 //update the enemy state
		 public void updateState();
		 
		 //deal the damage 
		 public void takeDamage(int damage);
		 
		 //return whether if the enemy has zero health or not.
		 public boolean getIsDead();

		public void setIsLock(boolean isLock);
		 

}
