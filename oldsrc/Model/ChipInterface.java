package Model;
import java.awt.image.BufferedImage;

import Shared.SpriteInterface;

//The interface of the Chip 
public interface ChipInterface extends SpriteInterface {
	
	//get the damage of the chip
    public int getDamage();  
    
    //get the hit range of the chip
    public int getRange();
    
    //get the unique type of the Chip
    public String getType();
    
    //get the constant letter of the chip
    public String getLetter();
    
    //get the current active battle chip is expired or not.
    public boolean getExpired();
    
    public boolean getIsParticle();
    
    //the position the animation start
    public void startChip(int xPos, int yPos);
    
    //the two icons used for the menu chips and battle chips
    public BufferedImage getLargeMenuIcon();
    
    public BufferedImage getSmallMenuIcon();
    
    public BufferedImage getSmallLetter();
    
    public BufferedImage getLargeLetter();
    
    public BufferedImage getDamageValue();
    //update the current damage of the chip. Determine if it hit some object .
	public void update();
}
