package edu.kit.cm.kitcampusguide.data;

import java.util.ArrayList;
import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Concrete Implementation of the POILoader, works on a PostgreSQL Database
 * and loads our data into Objects.
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 */
public class ConcretePOILoader implements POILoader {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public POI getPOI(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException();
		}
		
		POI result = null;
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_pois WHERE poi_id=" + id;
		
		ResultSet resultset = Config.executeSQLStatement(connection, sqlquery);
		
		try {
			result = savePOI(resultset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<POI> getPOIsByName(String name) {
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<POI> result = new ArrayList<POI>();

		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_pois WHERE poi_name LIKE '" + name + "'";
		
		ResultSet resultset = Config.executeSQLStatement(connection, sqlquery);
		
	    try {
			result = savePOIs(resultset);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<POI> getAllPOIs() {

		ArrayList<POI> result = new ArrayList<POI>();
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_pois";
		
		ResultSet resultset = Config.executeSQLStatement(connection, sqlquery);
		
		try {
			result = savePOIs(resultset);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public POICategory getPOICategory(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException();
		}
		
		POICategory result = null;
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {

	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_poicategory WHERE poicat_id=" + id);

	        result = savePOICategory(resultset);

	    } catch( Exception ex ) {
	        System.out.println( ex );
	    } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	    }
	    
	    result = addPOIsToCategory(result);
	      
	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<POICategory> getPOICategoryByName(String name) {
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<POICategory> result = new ArrayList<POICategory>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {

	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_poicategory WHERE poicat_name LIKE '" + name + "'");

	        result = savePOICategories(resultset);

	      } catch( Exception ex ) {
	        System.out.println( ex );
	      } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      for (POICategory poicat : result) {
	          poicat = addPOIsToCategory(poicat);
	      }
	      
	      return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<POICategory> getAllPOICategory() {	
		ArrayList<POICategory> result = new ArrayList<POICategory>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {

	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_poicategory");

	        result = savePOICategories(resultset);

	      } catch( Exception ex ) {
	        System.out.println( ex );
	      } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      for (POICategory poicat : result) {
	          poicat = addPOIsToCategory(poicat);
	      }
	      
	      return result;
	}
	
	
	
	private POI savePOI(ResultSet resultset) throws SQLException {
		POI poi = null;
		
		if (resultset != null) {
			int poiID = resultset.getInt(1);
			String poiName = resultset.getString(2);
			double poiX = resultset.getDouble(3);
			double poiY = resultset.getDouble(4);
			String poiIcon = resultset.getString(5);
			String poiDescription = resultset.getString(6);
			poi = new POI(poiName, poiID, poiIcon, poiDescription, poiX, poiY);
		}
		
		return poi;
		
	}
	
	private ArrayList<POI> savePOIs(ResultSet resultset) throws SQLException {
		/* delegate to savePOI */
		ArrayList<POI> result = new ArrayList<POI>();
		
        while(resultset.next()) {
			result.add(savePOI(resultset));	            	
        }
        
		return result;
		
	}
	

	private POICategory savePOICategory(ResultSet resultset) throws SQLException {
		POICategory poiCat = null;
		
		if (resultset != null) {
			int poiCatID = resultset.getInt(1);
			String poiCatName = resultset.getString(2);
			String poiCatIcon = resultset.getString(3);
			String poiCatDescription = resultset.getString(4);
			poiCat = new POICategory(poiCatName, poiCatID, poiCatIcon, poiCatDescription);
		}
		
		return poiCat;
		
	}
	
	private ArrayList<POICategory> savePOICategories(ResultSet resultset) throws SQLException {
		/* delegate to savePOI */
		ArrayList<POICategory> result = new ArrayList<POICategory>();
		
        while(resultset.next()) {
			result.add(savePOICategory(resultset));	            	
        }
        
		return result;
		
	}
	
	
	private POICategory addPOIsToCategory(POICategory poicat) {
		if (poicat == null) {
			throw new IllegalArgumentException("poicat cannot be null.");
		}
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try {

	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_poi-poicat WHERE category_id=" + poicat.getId());

	        
	        while(resultset.next()) {
				poicat.addPOI(this.getPOI(resultset.getInt("poi_id")));  	
	        }

	      } catch( Exception ex ) {
	        System.out.println( ex );
	      } finally {
	        try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	        try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	        try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }

		return poicat;
	}




}
