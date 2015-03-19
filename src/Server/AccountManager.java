/**
* 	AccountManagement.java
*	Handles the responses corresponding to the account management page
*
*	Creator: Colten Normore
*/
 
package Server;

import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.halt;

import spark.Request;
import spark.Response;
import spark.Session;

import java.lang.System;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;


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
        get("/api/user", (request, response) -> {
            CurrentUser user = new CurrentUser();
            user.username = request.session().attribute("user");

            Gson gson = new Gson();
            String json = gson.toJson(user);
            System.out.println(json);

            response.type("application/json");
            return json;
        });

        get("/api/account/:user", (request,response) -> {
            GetUser user = getUser(request.params(":user"));

            Gson gson = new Gson();
            String json = gson.toJson(user);

            response.type("application/json");
            return json;
        });

        put("/api/account/:user", (request,response) -> {
            try {
                Gson gson = new Gson();

                String body = request.body();
                System.out.println(body);
                // attempt to convert JSON to DayNote
                PutUser user = gson.fromJson(body, PutUser.class);
                System.out.println(user.name);

                //ToDo: check old password first

                //insert note into database
                putUser(user);
                return "Success!";
            }
            catch ( JsonParseException ex ) {
                System.out.println("post /calendar/:year/:month/:day : malformed values");
                halt(400, "malformed values");
            }

            return null;
        });
    }

    public static void putUser(PutUser user){
        String queryString = "UPDATE account SET name='"+user.name+"',email='"+user.email+"',password='"+user.newPassword+"' " +
                "WHERE username='"+user.username+"';";
        System.out.println(queryString);
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
            System.out.println("Opened database successfully");

            stmt = conn.createStatement();
            stmt.executeUpdate( queryString );

            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            printErrMsg(e);
        } finally {
            System.out.println("Closing DB resources");
            try{ if (stmt == null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn == null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
        }
    }

    public static GetUser getUser(String username){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        GetUser user = new GetUser();

        String queryString = "SELECT * FROM account WHERE username='"+username+"';";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
            System.out.println("Opened database successfully");

            stmt = conn.createStatement();
            rs = stmt.executeQuery( queryString );

            if(rs.next()) {
                user.username = username;
                user.email = rs.getString("email");
                user.name = rs.getString("name");
            }

        } catch ( Exception e ) {
            printErrMsg(e);
        } finally {
            System.out.println("Closing DB resources");
            try{ if (rs == null) rs.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (stmt == null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn == null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
        }

        return user;
    }

    public static void printErrMsg(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
    }
}

class CurrentUser {
    public String username;
    CurrentUser(){

    }
}

class GetUser {
    public String username;
    public String name;
    public String email;
    public GetUser(){

    }
}

class PutUser {
    public String username;
    public String name;
    public String email;
    public String oldPassword;
    public String newPassword;
    public PutUser(){

    }
}

