package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Shared.SpriteInterface;

/*
 * @author Yan Liu and Colten Normore
 * 
 * MenuChips is the representation of all the chips within the BattleMenu.
 */

public class MenuChips implements SpriteInterface {
	private ArrayList<ChipInterface> menuList;
	private ArrayList<ChipInterface> queuedList;
	private ArrayList<Boolean> isSelectedList;
	private ArrayList<Integer> queuedPosition;
	private TotalChips totalChips;
	private BattleChips battleChips;
	private int menuCap = 5;
	private BufferedImage noData;
	private String qType;
	private String qLetter;
	
	/*
	 * Constructor for the MenuChips object.
	 * 
	 * @params totalChips the desired TotalChips object
	 * @params battleChips the desired BattleChips object
	 */
	public MenuChips (TotalChips totalChips, BattleChips battleChips){
		this.totalChips = totalChips;
		this.battleChips = battleChips;
		
		try {
			noData = ImageIO.read(new File("./src/Img/battlemenu/nodata.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		menuList = totalChips.drawChips(menuCap);
		queuedList = new ArrayList<ChipInterface>();
		isSelectedList = new ArrayList<Boolean>();
		for(int i = 0; i < menuList.size(); i++){
			isSelectedList.add(false);
		}
		queuedPosition = new ArrayList<Integer>();
		
		qType = null;
		qLetter = null;
	}
	
	/* public ArrayList<ChipInterface> getRandomChips(int numChips)
	 * gets an list of random chips from the TotalChips object
	 * 
	 * @param numChips the number of chips to be drawn from TotalChips
	 * @returns an ArrayList of random chips
	 */
	public ArrayList<ChipInterface> getRandomChips(int numChips){ //make this private
		return totalChips.drawChips(numChips);
	}
	
	/* public BufferedImage getNoDataImage()
	 * getter for the no data image
	 * 
	 * @returns the no data image
	 */
	public BufferedImage getNoDataImage(){
		return noData;
	}
	
	/* public void setBattleChips()
	 * loads the list of queued chips into the Battle chips object
	 * After this, the queued chip list is cleared and random chips are drawn
	 */
	public void setBattleChips(){
		ArrayList<ChipInterface> copy = new ArrayList<ChipInterface>(queuedList);
//		for(int i = 0; i < queuedList.size(); i ++){
//			copy.add(queuedList.get(i));
//		}
		
		battleChips.loadBattleChips(copy);
		for(int i = isSelectedList.size() - 1; i >= 0; i--){
			if (isSelectedList.get(i).booleanValue()){
				menuList.remove(i);
			}
		}
		
		if(menuList.size() < menuCap){
//			if(getRandomChips(menuCap-menuList.size()) != null){
				menuList.addAll(getRandomChips(menuCap-menuList.size()));
//			}
		}
		
		isSelectedList.clear();
		for(int i = menuList.size() - 1; i >= 0; i--){
			isSelectedList.add(false);
		}
		
		queuedList.clear();
		queuedPosition.clear();
		qLetter = null;
		qType = null;
	}
	
	public void recyleChips(){
		menuCap = menuCap + queuedList.size();
		if (menuCap > 10)
			menuCap = 10;
		for(int i = isSelectedList.size() - 1; i >= 0; i--){
			if (isSelectedList.get(i).booleanValue()){
				menuList.remove(i);
			}
		}
		
		if(menuList.size() < menuCap){
//			if(getRandomChips(menuCap-menuList.size()) != null){
				menuList.addAll(getRandomChips(menuCap-menuList.size()));
//			}
		}
		
		isSelectedList.clear();
		for(int i = menuList.size() - 1; i >= 0; i--){
			isSelectedList.add(false);
		}
		
		queuedList.clear();
		queuedPosition.clear();
		qLetter = null;
		qType = null;
	}
	
	/* public void setSelected(int index)
	 * adds a chip to the queuedchip list based on a desired index
	 * 
	 * @param index the desired index
	 */
	public void setSelected(int index){
		if(index < menuList.size()){
			if(!isSelectedList.get(index) && queueCheck(index) && queuedList.size() <= 4 ){
				queuedList.add(menuList.get(index));
				queuedPosition.add(index);
				isSelectedList.set(index, true);
				updateQueueChecks();
			}
		}
	}
	
	private boolean queueCheck(int index){
		if (qType == null && qLetter == null)
			return true;
		if (menuList.get(index).getType() == qType || menuList.get(index).getLetter() == qLetter)
			return true;
		return false;
	}
	
//	private boolean logicalXOR(boolean x, boolean y) {
//	    return ( ( x || y ) && ! ( x && y ) );
//	}
	
	/* removeQueuedChip()
	 * Tries to remove the last queued chip. if there are no queued chips, nothing happens.
	 */
	public void removeQueuedChip(){
		int qSize = queuedList.size();
		if (qSize > 0){
			isSelectedList.set(queuedPosition.get(qSize - 1), false);
			queuedPosition.remove(qSize - 1);
			queuedList.remove(qSize - 1);
			updateQueueChecks();
		}
	}
	
	private void updateQueueChecks() {
		if (queuedList.size() == 0){
			qType = null;
			qLetter = null;
		}
		else{
			boolean typeBool = true;
			boolean letterBool = true;
			for (int i = 1; i < queuedList.size() ; i++){
				if (queuedList.get(i).getLetter() != queuedList.get(0).getLetter()){
					letterBool = false;
				}
				if (queuedList.get(i).getType() != queuedList.get(0).getType()){
					typeBool = false;
				}
			}
			
			if(typeBool)
				qType = queuedList.get(0).getType();
			else
				qType = null;
			
			if(letterBool)
				qLetter = queuedList.get(0).getLetter();
			else
				qLetter = null;
		}
	}

	/* public BufferedImage getLargeMenuIcon(int index)
	 * gets the large image icon for a particular index. If the index is out of the menu list's bounds
	 * it returns a the no data image
	 * 
	 * @param index The desired index to get a large image from
	 * @returns The large image icon for the desired index
	 */
	public BufferedImage getLargeMenuIcon(int index){
		if(index < menuList.size()){
			return menuList.get(index).getLargeMenuIcon();
		}else{
			return noData;
		}
	}
	
	public BufferedImage getLargeLetter(int index){
		if(index < menuList.size()){
			return menuList.get(index).getLargeLetter();
		}else{
			return null;
		}
	}
	public BufferedImage getDamageValue(int index){
		if(index < menuList.size()){
			return menuList.get(index).getDamageValue();
		}else{
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see Shared.SpriteInterface#paintRequest(java.awt.Graphics2D)
	 */
	@Override
	public void paintRequest(Graphics2D clone) {
		// TODO Auto-generated method stub
		for (int i = 0; i < menuList.size(); i++){
			if (!isSelectedList.get(i).booleanValue())
				if(queueCheck(i))
					clone.drawImage(menuList.get(i).getSmallMenuIcon(), (9+(16*(i%5))), 105+ 24*(i/5), null);
			else{
				/* Create an ARGB BufferedImage */
				BufferedImage img = menuList.get(i).getSmallMenuIcon();
				int w = img.getWidth(null);
				int h = img.getHeight(null);
				BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				Graphics g = bi.getGraphics();
				g.drawImage(img, 0, 0, null);

				/*
				 * Create a rescale filter op that makes the image
				 * 50% opaque.
				 */
				float[] scales = { 1f, 1f, 1f, 0.5f };
				float[] offsets = new float[4];
				RescaleOp rop = new RescaleOp(scales, offsets, null);

				/* Draw the image, applying the filter */
				clone.drawImage(bi, rop, (9+(16*(i%5))), 105+ 24*(i/5));
			}
//			/* Create an ARGB BufferedImage */
//			BufferedImage img = queuedList.get(i).getSmallMenuIcon();
//			int w = img.getWidth(null);
//			int h = img.getHeight(null);
//			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//			Graphics g = bi.getGraphics();
//			g.drawImage(img, 0, 0, null);
//
//			/*
//			 * Create a rescale filter op that makes the image
//			 * 50% opaque.
//			 */
//			float[] scales = { 1f, 1f, 1f, 0.5f };
//			float[] offsets = new float[4];
//			RescaleOp rop = new RescaleOp(scales, offsets, null);
//
//			/* Draw the image, applying the filter */
//			clone.drawImage(bi, rop, 97, 25+(16*i));


			clone.drawImage(menuList.get(i).getSmallLetter(), (9+4+(16*(i%5))), 105+16+ 24*(i/5), null );
		}
		
		for(int i = 0; i < queuedList.size(); i++){
			
			clone.drawImage(queuedList.get(i).getSmallMenuIcon(), 97, 25+(16*i), null);
		}

	}
}
