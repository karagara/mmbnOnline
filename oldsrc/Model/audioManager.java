package Model;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/*This is the class that for the audio part of the game, the sound path will be pre defined.And the volume of the sound can be monitored.
 *Further development: more game sound should be added, and try to make the sound multi thread¡£
 *eg. the main back ground sound and the specific sound can be player together or one of them.
*/

public class audioManager{
	
	public static enum Volume{
		MUTE, LOW, MEDIUM, HIGH
	}
	public static final String BACKGROUND = "./src/audio/1.wav"; //soundID = 0
	//public static final String HIT = "";
	//public static final String DAMAGE = "";
	
    private Volume volume = Volume.LOW;
    private Clip clip;
    private File soundFile;
    private ArrayList<String> soundPath = new ArrayList<String>();
    private ArrayList<File> soundList = new ArrayList<File>();
    
    //Try to load all the sound when the audio manager initialized.
    public audioManager() {  
    	    soundPath.add(BACKGROUND);
    	    for(int i = 0;i < soundPath.size(); i++){
        	    soundFile = new File(soundPath.get(i));
        	    soundList.add(soundFile);
    	    }
	}
    
    //Play the sound with the sound ID. That is the index of the sound in the list.
    public void play(int soundID) {
    	try {
    		clip = AudioSystem.getClip();
    		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundList.get(soundID));
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	if( volume != Volume.MUTE){
        	if( soundID == 0){
        		clip.loop(Clip.LOOP_CONTINUOUSLY);
        	}
        	else{
        		if( clip.isRunning())
        			clip.stop();
        		clip.setFramePosition(0);
        	    clip.start();
        	}
    	}  	
     }
    
    public void stop(){
    	clip.stop();
    }
}

