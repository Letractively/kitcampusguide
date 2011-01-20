package edu.kit.cm.kitcampusguide.data;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 *
 */
public class ConcretePOILoader implements POILoader {

	/**
	 * 
	 */
	@Override
	public POI getPOI(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<POI> getPOIsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<POI> getAllPOIs() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public POICategory getPOICategory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<POICategory> getPOICategoryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<POICategory> getAllPOICategory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] argv) {
		 
		System.out.println("-------- PostgreSQL " +
				"JDBC Connection Testing ------------");
 
		try {
 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? " +
					"Include in your library path!");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
				"jdbc:postgresql://127.0.0.1:5432/testdb","mkyong", "123456");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null){
			System.out.println("You made it, take control your database now!");
		}else{
			System.out.println("Failed to make connection!");
		}
	}


}
