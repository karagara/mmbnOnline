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

        if ( username != null && password != null){
	        if ( AccountLoginDB.isLoginValid(username, password) ) {
	            System.out.println("test in accountLogin.java");
	            Session sess = request.session(true);
	            if ( sess != null ) {
	            	sess.attribute("user", username);
	            	response.redirect("/auth/main.html");
	            	return;
	            }
	        }
	            
		}
        response.redirect("/login.html");
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