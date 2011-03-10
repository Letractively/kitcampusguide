package edu.kit.cm.kitcampusguide.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This utility-class saves important data to connect to the current used database.
 * Every class, which works with the database have to use this data to connect to the database.
 * 
 * @author Michael Hauber
 *
 */
public final class Config {
	
	private Config() {
		assert false;
	}

	/**
	 * Saves the type of the current used database.
	 */
	public static String dbType = "postgresql";
	
	/**
	 * Saves the hostname of the current used database.
	 */
	public static String dbHost = "localhost";
	
	/**
	 * Saves the port of the current used database.
	 */
	public static String dbPort = "5432";
	
	/**
	 * Saves the database name of the current used database.
	 */
	public static String dbDatabase = "campusguide";
	
	/**
	 * Saves the username of the current used database.
	 */
	public static String dbUsername = "campusguide";
	
	/**
	 * Saves the password of the current used database.
	 */
	public static String dbPassword = "katja";
	
	
	public static Connection getPgSQLJDBCConnection() {
		try {
			Class.forName("org.postgresql.Driver");	

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return connection;
	}
	
	/**
	 * aaa
	 * @param connection a
	 * @param sqlQuery a
	 * @return a
	 * @throws SQLException a
	 */
	public static ResultSet executeSQLStatement(Connection connection, String sqlQuery) throws SQLException {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		
		Statement statement = null;
		ResultSet resultset = null;
		
	    statement = connection.createStatement();
	    resultset = statement.executeQuery(sqlQuery);     

		return resultset;
	}

	
}
