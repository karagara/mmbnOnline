package Server;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.Request;
import spark.Response;
import spark.Session;

public class AccountCreation {

	public static void postResponse(Request request, Response response){
		//on any failure, reroute back to account creation page
		//check if username and pass aren't empty
		String username = request.queryParams("username");
        String password = request.queryParams("password");
        String passwordCheck = request.queryParams("passwordCheck");

        if ( username == null || password == null || passwordCheck == null) {
        	System.out.println("Null field(s)");
            response.redirect("/create-account.html");
        } else if ( username.equals("") || password.equals("") || passwordCheck.equals("") ) {
        	System.out.println("Empty field(s)");
            response.redirect("/create-account.html");
        } else if ( !password.equals(passwordCheck) ){
        	System.out.println("Unequal Passwords");
        	response.redirect("/create-account.html");
        } else if ( AccountCreationDB.isUsernameFree(username) ){
			//if free, create db entry
        	AccountCreationDB.createUser(username,password);
        	System.out.println("Created user "+username);

        	//log user in immediately
        	Session sess = request.session(true);
            if ( sess == null ) {
                response.redirect("/login.html");
            } else {
            	sess.attribute("user", username);
            	response.redirect("/auth/main.html");
            }
        } else {
        	response.redirect("/create-account.html");
        }
	}

	public static void rigRoutes(){
		post("/account", (request, response) -> {
			AccountCreation.postResponse(request, response);
			return null;
		});
	}
}
