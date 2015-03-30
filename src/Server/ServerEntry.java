package Server;

import java.io.File;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;
import Game.GameManager;

public class ServerEntry {

	 public static void main(String[] args) {	
        File dbFile = new File("mmbn.db");
        GameManager gm = new GameManager();        
        
        if (!dbFile.exists())
            DatabaseConnection.databaseMapping();

 
        externalStaticFileLocation("static");
        
        before("/auth/*", (request, response) -> {
            String user = request.session().attribute("user");
            if ( user == null ) {
                response.redirect("/login.html");
            }
        } );

        before("/admin/*", (request, response) -> {
            //method subject to change
            Session sess = request.session(true);
            if ( sess == null ) {
                response.redirect("/login.html");
            }
            else {
                String role = sess.attribute("role");
                if(role == null || role.compareTo("admin") != 0)
                    response.redirect("/auth/main");
            }
        } );
      
        AccountLogin.rigRoutes();
        AccountCreation.rigRoutes();
        AccountManager.rigRoutes();
        AdminPage.rigRoutes();
        rigRoutes(gm);
	}
	 
	 public static void rigRoutes(GameManager gm){
         get ("/auth/logout", (request, response) -> {
             request.session().removeAttribute("user");
            response.redirect("index.html");
             return  null;
         });

		get("/auth/requestGame", (request, response) -> {
			response.redirect(gm.newGame(request.session().attribute("user")));
			return "";
		});
		
		//used by players waiting in queue
		post("/game/checkForGame", (request, response) -> {
			if(gm.isInGame(request.session().attribute("user")))
				return "/testClient/mmbnClient.html";
			return "";
		});
		
		post("/game/sendAction", (request, response) -> {
			return gm.updateGame(request.session().attribute("user"), request.body());
		});
		
		post("/game/gameUpdate", (request, response) -> {
			return gm.getGameState(request.session().attribute("user"));
		});
		
		post("/game/playerLeft", (request, response) -> {
			gm.playerLeftGame(request.session().attribute("user"));
			return "";
		} );
		
	}
}