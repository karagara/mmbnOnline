package Server;

import java.io.File;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;

public class ServerEntry {

	 public static void main(String[] args) {
		
		
        File dbFile = new File("mmbn.db");
        if (!dbFile.exists())
            DatabaseConnection.databaseMapping();


        externalStaticFileLocation("static");
        
        before("/auth/*", (request, response) -> {
            String user = request.session().attribute("user");
            if ( user == null ) {
                response.redirect("/login.html");
            }
        } );

        AccountLogin.rigRoutes();
        AccountCreation.rigRoutes();
        AccountManager.rigRoutes();
	}
}