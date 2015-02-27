package Server;
import java.sql.*;

public class DatabaseConnection{
	public static void databaseMapping(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE ACCOUNT " +
				"(ID 				INTEGER PRIMARY KEY     NOT NULL," +
				" USERNAME          TEXT    				NOT NULL, " + 
				" PASSWORD          TEXT     				NOT NULL, " + 
				" ROLE				TEXT					NOT NULL, " +
				" EMAIL        		TEXT, " +
				" NAME        		TEXT)"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public static Connection getConnection(){
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:mmbn.db");
			System.out.println("Opened database successfully");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		return c;
	}

}