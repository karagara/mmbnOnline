package Server;

import java.sql.*;

public class AccountCreationDB {
	public static Boolean isUsernameFree(String username){
		Boolean isFree = false;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String queryString = "SELECT username FROM account WHERE username='" + username+ "';";
        System.out.println(queryString);
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            rs = stmt.executeQuery( queryString );

            if ( !rs.next() ) {
            	isFree = true;
            }
		} catch ( Exception e ) {
			printErrMsg(e);
		} finally {
			System.out.println("Closing DB resources");
			try{ if (rs != null) rs.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (stmt != null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn != null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
		}

		return isFree;
	}

	public static void createUser(String username, String password){
		Connection conn = null;
		Statement stmt = null;

		String queryString = 	"INSERT INTO account (username,password,role)" +
								"values('"+username+"', '"+password+"', 'admin');";
        System.out.println(queryString);
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            stmt.executeUpdate( queryString );

		} catch ( Exception e ) {
			printErrMsg(e);
		} finally {
			System.out.println("Closing DB resources");
            try{ if (stmt != null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn != null) {conn.close();} } catch ( Exception e ) { printErrMsg(e); };
		}
	}

	public static void printErrMsg(Exception e){
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);	
	}
}