package Game;
import java.lang.Boolean;

public interface Action {
	public void update();
	public Boolean isEventComplete();
    public String getChipSequence();
}