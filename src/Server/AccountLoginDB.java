package Server;

import java.sql.*;

public class AccountLoginDB {
    static String changedUser ="";
	public static boolean isLoginValid(String username, String password){
        boolean isValid = false;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String queryString = "SELECT username,password FROM account WHERE username='" + username+ "';";
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            rs = stmt.executeQuery( queryString );
            if ( rs.next() ) {
                String dbPassword  = rs.getString("password");
            	if ( dbPassword.equals(password) ){
            		isValid = true;            	
            	}
            }
            rs.close();
            stmt.close();
            conn.close();
		} catch ( Exception e ) {
			printErrMsg(e);
		} finally {
			System.out.println("Closing DB resources");
			try{ if (rs == null) rs.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (stmt == null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn == null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
		}

		return isValid;
	}
    
    public static String checkUser(String username){
        
        String resultMsg = "";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String link = "";
        boolean flag = false;
        String queryString = "SELECT username FROM account WHERE username='" + username+ "';";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
            System.out.println("Opened database successfully");
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery( queryString );
            if ( rs.next() ) {
                String dbUsername  = rs.getString("username");
                if ( dbUsername.equals(username) ){
                    resultMsg = "<p class='resetmsg'>Hello " + username + "</p>";
                    link = "<a href='newpass.html' class='resetlink'>Change Password</a>";
                    flag = true;
                    changedUser = username;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            printErrMsg(e);
        } finally {
            System.out.println("Closing DB resources");
            try{ if (rs == null) rs.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (stmt == null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn == null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
        }
        if( flag == false){
            resultMsg = "<p class='resetmsg'> The username does not exist</p>";
            link = "<a href='reset.html' class='resetlink'>Go Back</a>";
        }
        String staticPage = "<html><head><title>Reset Password</title><link rel='stylesheet' type='text/css' href='main.css'></head>"+"<body><img id='main' src='main1.jpg' width='600' style='position:absolute;left:560px;'>" + resultMsg +  link + "</body></html>";
        return staticPage;
    }
    
    public static String resetPass(String password){
        Connection conn = null;
        Statement stmt = null;
        String resultMsg = "";
        String link = "";
        String updateString = "UPDATE account SET password='" + password +"'"+ "WHERE username='" +changedUser+ "';";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
            System.out.println("Opened database successfully");
            
            stmt = conn.createStatement();
            stmt.executeUpdate( updateString );

            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            printErrMsg(e);
        } finally {
            System.out.println("Closing DB resources");
            try{ if (stmt == null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn == null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
        }

        resultMsg = "<p class='resetmsg'> Password Update Successfully</p>";
        link = "<a href='login.html' class='resetlink'>Login</a>";
 
        String staticPage = "<html><head><title>Password update</title><link rel='stylesheet' type='text/css' href='main.css'></head>"+"<body><img id='main' src='main1.jpg' width='600' style='position:absolute;left:560px;'>" + resultMsg +  link + "</body></html>";
        return staticPage;
    }
    

	public static void printErrMsg(Exception e){
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);	
	}
}