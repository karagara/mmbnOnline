/**
* 	AccountManagement.java
*	Handles the responses corresponding to the account management page
*
*	Creator: Colten Normore
*/
 
package Server;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.Request;
import spark.Response;
import spark.Session;

import java.lang.System;

public class AccountManager{
	//TODO: replace these with a database connection
	private String acctName = "karagararadio"; 
	private String name = "Colten Normore";
	private String email = "test@example.com";
	private String password = "abccba";

	private static String STATIC_SECTION =
        "<html>\n" + 
        "<body>\n" +
        "<form action='/account' method='POST'>\n" +
        "<p>Account Name: %s\n</p>"+
        "<p>Name: %s\n</p>"+
        "<label>Change Name: <input type='text' name='name'></label>\n" +
        "<p>Email: %s\n</p>"+
        "<label>Change Email: <input type='text' name='email'></label>\n" +
        "<p>Change Password:\n</p>"+
        "<label>Old Password: <input type='password' name='oldPassword'></label><br>\n" +
        "<label>New Password: <input type='password' name='newPassword'></label><br>\n" +
        "<label>Verify Password: <input type='password' name='verifyPassword'></label><br>\n" +
        "<input type='submit'>\n" +
        "</form>\n" +
        "</body>\n" + 
        "</html>\n";

    public static void rigRoutes(){
        get("/test", (request,response) -> {
            return AccountManager.getResponse(request,response);
        });
    }

    public static String getResponse(Request request, Response response){
    	//get account name
        String acctName = "karagararadio"; 
        String name = "Colten Normore";
        String email = "test@example.com";    	
        return String.format(STATIC_SECTION,acctName,name,email);
    }

    public static String postResponse(Request request, Response response){

    	// if (request.queryParams("name") != "")
    	// 	this.name = request.queryParams("name");

    	// if (request.queryParams("email") != "")
    	// 	this.email = request.queryParams("email");

        response.redirect("/account");
        return null;
    }
}

