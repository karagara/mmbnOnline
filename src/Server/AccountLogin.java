package Server;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.Request;
import spark.Response;
import spark.Session;

public class AccountLogin {

	public static void postResponse(Request request, Response response){
        String username = request.queryParams("user");
        String password = request.queryParams("password");

        if ( username == null || password == null ) {
            response.redirect("/login.html");
        }
        if ( AccountLoginDB.isLoginValid(username, password) ) {
            Session sess = request.session(true);
            if ( sess == null ) {
                response.redirect("/login.html");
            }
            sess.attribute("user", username);
        }
	}

	public static void rigRoutes(){
		get("/login", (request, response) -> {
			response.redirect("/login.html");
			return null;
		});

        post("/login", (request, response) -> {
        	//Post response checks if login is valid
        	AccountLogin.postResponse(request, response);
        	//If it is, then redirect to main landing page
            response.redirect("/auth/main.html");
            return null;
        });
	}
}