package Server;

import java.sql.*;

public class AccountLoginDB {
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

	public static void printErrMsg(Exception e){
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);	
	}
}