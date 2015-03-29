package Game;

/**
 * Created by karagara on 23/03/15.
 */
public class FrameEvent {
    int frameIndex;
    Sprite sprite;
    int xDisplacement;
    int yDisplacement;
    String tileEffect = "";
    int damage;
    playerCondition playerState;
    Sprite playerSprite;

    static class Sprite{
        String spriteSrc;
        int spriteIndex;
    }

    public FrameEvent(){

    }
}
