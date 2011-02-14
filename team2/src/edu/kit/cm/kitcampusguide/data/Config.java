package edu.kit.cm.kitcampusguide.data;

/**
 * This utility-class saves important data to connect to the current used database.
 * Every class, which works with the database have to use this data to connect to the database.
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 *
 */
public final class Config {

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
	
}
