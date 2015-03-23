package Game;

/**
 * Created by karagara on 22/03/15.
 */
public class GameStandalone {
    public static void main(String[] args) {
        Game g = new Game(new Connection("Test1"), new Connection("Test2"));
        Thread t = new Thread(g);
        t.start();
        try {
            t.join();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
