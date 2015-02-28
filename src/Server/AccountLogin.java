package Server;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.Request;
import spark.Response;
import spark.Session;

public class AccountLogin {
    
	public static void postResponse(Request request, Response response){
        String username = request.queryParams("login");
        String password = request.queryParams("password");

        if ( username == null || password == null ) {
            response.redirect("/login.html");
        }
        if ( AccountLoginDB.isLoginValid(username, password) ) {
            Session sess = request.session(true);
            if ( sess == null ) {
                response.redirect("/login.html");
            }
            else{
                response.redirect("/auth/main.html");
            }
            sess.attribute("user", username);
        }
	}

	public static void rigRoutes(){
        get("/index", (request, response) ->{
            response.redirect("/index.html");
            return null;
        });
        
		get("/login", (request, response) -> {
			response.redirect("/login.html");
			return null;
		});

        post("/login", (request, response) -> {
        	//Post response checks if login is valid
        	AccountLogin.postResponse(request, response);
            return null;
        });
	}
}