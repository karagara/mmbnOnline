package Server;

import java.sql.*;

public class AdminPageDB {
	public static String getUsersInfo(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String info = "";

		String queryString = "SELECT username, password, role FROM account;";
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            rs = stmt.executeQuery( queryString );

            while(rs.next()) {
            	info += rs.getString("username") + " " + rs.getString("password") + " " + rs.getString("role") + "\n";
            }

		} catch ( Exception e ) {
			printErrMsg(e);
		} finally {
			System.out.println("Closing DB resources");
			try{ if (rs == null) rs.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (stmt == null) stmt.close(); } catch ( Exception e ) { printErrMsg(e); };
            try{ if (conn == null) conn.close(); } catch ( Exception e ) { printErrMsg(e); };
		}
	
		return info;
	}
	
	public static void printErrMsg(Exception e){
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);	
	}
}