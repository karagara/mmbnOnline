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

        before("/admin/*", (request, response) -> {
            //method subject to change
            Session sess = request.session(true);
            if ( sess == null ) {
                response.redirect("/login.html");
            }
            else {
                String role = sess.attribute("role");
                if(role == null || role.compareTo("admin") != 0)
                    response.redirect("/test");
            }
        } );
        
         
        AccountLogin.rigRoutes();
        AccountCreation.rigRoutes();
        AccountManager.rigRoutes();
        AdminPage.rigRoutes();
	}
}