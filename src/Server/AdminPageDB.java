package Server;

import java.sql.*;

public class AdminPageDB {
	public static String getUsersInfo(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String info = "";

		String queryString = "SELECT * FROM account;";
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            rs = stmt.executeQuery( queryString );

            while(rs.next()) {
            	info += rs.getString("ID") + "\t" +
            			rs.getString("USERNAME") + "\t" + 
            			rs.getString("PASSWORD") + "\t" + 
            			rs.getString("ROLE") + "\t" +
            			rs.getString("EMAIL") + "\t" +
            			rs.getString("NAME") + "\n";
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
	
	public static void removeUser(String id){
		Connection conn = null;
		Statement stmt = null;

		String queryString = "DELETE FROM account WHERE id="+id+";";
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            stmt.executeUpdate( queryString );
			System.out.println("Deleted user with user id=" + id);
            //Log rs?

		} catch ( Exception e ) {
			printErrMsg(e);
		} finally {
			System.out.println("Closing DB resources");
		}
	}
	
	public static void editUser(String id, String userName, String password, String role, String email, String name){
		Connection conn = null;
		Statement stmt = null;
		String queryString = "UPDATE account " +
							"SET USERNAME='"+userName+"', PASSWORD='"+password+"', ROLE='"+role+"', EMAIL='"+email+"', NAME='"+name+"' "+
							"WHERE ID='"+id+"';";
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
            stmt.executeUpdate( queryString );
            System.out.println("Updated user with id=" + id);

		} catch ( Exception e ) {
			printErrMsg(e);
		} finally {
			System.out.println("Closing DB resources");
		}
	}
	
	
	
	
	public static void printErrMsg(Exception e){
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);	
	}
}