package edu.kit.cm.kitcampusguide.data;

import java.util.ArrayList;
import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
		// TODO SQL Statement lernen ;-)
		
		ArrayList<POI> result = new ArrayList<POI>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {
			// Select fitting database driver and connect:
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("select * from cg_pois");
	        // Get meta data:
	        ResultSetMetaData resultmd = resultset.getMetaData();
	        int n = resultmd.getColumnCount();
	        
	        while(resultset.next()) {
	            int poiID = resultset.getInt(1);
	            String poiName = resultset.getString(2);
	            double poiX = resultset.getDouble(3);
	            double poiY = resultset.getDouble(4);
	            String poiIcon = resultset.getString(5);
	            String poiDescription = resultset.getString(6);
	            result.add(new POI(poiName, poiID, poiIcon, poiDescription, poiX, poiY));	            	
	        }

	      } catch( Exception ex ) {
	        System.out.println( ex );
	      } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      
	      return result;
	}

	/**
	 * 
	 */
	@Override
	public List<POI> getPOIsByName(String name) {
		
		// TODO SQL Statement lernen ;-)
		
		ArrayList<POI> result = new ArrayList<POI>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {
			// Select fitting database driver and connect:
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("select * from cg_pois");
	        // Get meta data:
	        ResultSetMetaData resultmd = resultset.getMetaData();
	        int n = resultmd.getColumnCount();
	        
	        while(resultset.next()) {
	            int poiID = resultset.getInt(1);
	            String poiName = resultset.getString(2);
	            double poiX = resultset.getDouble(3);
	            double poiY = resultset.getDouble(4);
	            String poiIcon = resultset.getString(5);
	            String poiDescription = resultset.getString(6);
	            result.add(new POI(poiName, poiID, poiIcon, poiDescription, poiX, poiY));	            	
	        }

	      } catch( Exception ex ) {
	        System.out.println( ex );
	      } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      
	      return result;
	}

	/**
	 * 
	 */
	@Override
	public List<POI> getAllPOIs() {

		ArrayList<POI> result = new ArrayList<POI>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {
			// Select fitting database driver and connect:
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("select * from cg_pois");
	        // Get meta data:
	        ResultSetMetaData resultmd = resultset.getMetaData();
	        int n = resultmd.getColumnCount();
	        
	        while(resultset.next()) {
	            int poiID = resultset.getInt(1);
	            String poiName = resultset.getString(2);
	            double poiX = resultset.getDouble(3);
	            double poiY = resultset.getDouble(4);
	            String poiIcon = resultset.getString(5);
	            String poiDescription = resultset.getString(6);
	            result.add(new POI(poiName, poiID, poiIcon, poiDescription, poiX, poiY));	            	
	        }

	      } catch( Exception ex ) {
	        System.out.println( ex );
	      } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      
	      return result;
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



}
