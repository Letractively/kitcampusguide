package edu.kit.cm.kitcampusguide.data;

import java.util.ArrayList;
import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Concrete Implementation of the POILoader, works on a PostgreSQL Database
 * and loads our data into Objects.
 * 
 * @author Michael Hauber
 */
public class ConcretePOILoader implements POILoader {

	/**
	 * {@inheritDoc}
	 */
	public POI getPOI(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException();
		}
		
		POI result = null;
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_pois WHERE poi_id=" + id;
		
		ResultSet resultset = null;
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
			if (!resultset.next()) {
				resultset = null;
			}
			result = savePOI(resultset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<POI> getPOIsByName(String name) {
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<POI> result = new ArrayList<POI>();

		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_pois WHERE poi_name LIKE '%" + name + "%'";
		
		ResultSet resultset = null;
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
			result = savePOIs(resultset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result.size() == 0) {
			result = null;
		}

	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<POI> getAllPOIs() {

		ArrayList<POI> result = new ArrayList<POI>();
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_pois";
		
		ResultSet resultset = null;
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
			result = savePOIs(resultset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result.size() == 0) {
			result = null;
		}

	    return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public POICategory getPOICategory(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException();
		}
		
		POICategory result = null;
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_poicategory WHERE poicat_id='" + id + "'";
		
		ResultSet resultset = null;
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
			if (resultset.next()) {
		    result = savePOICategory(resultset);
		    }
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<POICategory> getPOICategoryByName(String name) {
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<POICategory> result = new ArrayList<POICategory>();
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_poicategory WHERE poicat_name LIKE '%" + name + "%'";
		
		ResultSet resultset = null;
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
			result = savePOICategories(resultset);
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }
		if (result.size() == 0) {
			result = null;
		}
		
        return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<POICategory> getAllPOICategory() {	
		ArrayList<POICategory> result = new ArrayList<POICategory>();
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_poicategory";
		
		ResultSet resultset = null;
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
			result = savePOICategories(resultset);
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }
		if (result.size() == 0) {
			result = null;
		}
	    
        return result;
	}
	
	/*
	 * Supporting-method to save a POI of the database
	 * in a POI object by using a given ResultSet.
	 */
	private POI savePOI(ResultSet resultset) throws SQLException {
		POI poi = null;
		
		if (resultset != null) {
			int poiID = resultset.getInt("poi_id");
			String poiName = resultset.getString("poi_name");
			double poiX = resultset.getDouble("poi_latitude");
			double poiY = resultset.getDouble("poi_longitude");
			String poiIcon = resultset.getString("poi_icon");
			String poiDescription = resultset.getString("poi_description");
			poi = new POI(poiName, poiID, poiIcon, poiDescription, poiX, poiY);
		}
		
		return poi;
	}
	
	/*
	 * Supporting-method to save a list of POIs of the database into a
	 * List of POIs.
	 */
	private ArrayList<POI> savePOIs(ResultSet resultset) throws SQLException {
		ArrayList<POI> result = new ArrayList<POI>();
		
        while (resultset.next()) {
			result.add(savePOI(resultset));	            	
        }
        
		return result;
	}
	
	/*
	 * Supporting-method to save a POICategory of the database
	 * in a POICategory object.
	 */
	private POICategory savePOICategory(ResultSet resultset) throws SQLException {
		POICategory poiCat = null;
 
		if (resultset != null) {
			int poiCatID = resultset.getInt(1);
			String poiCatName = resultset.getString(2);
			String poiCatIcon = resultset.getString(3);
			String poiCatDescription = resultset.getString(4);
			poiCat = new POICategory(poiCatName, poiCatID, poiCatIcon, poiCatDescription);
		}
		addPOIsToCategory(poiCat);
		return poiCat;
		
	}
	
	/*
	 * Supporting-method to save a list of POICategory of the database
	 * in a list of POICategory objects.
	 */
	private ArrayList<POICategory> savePOICategories(ResultSet resultset) throws SQLException {
		ArrayList<POICategory> result = new ArrayList<POICategory>();
        while (resultset.next()) {
			result.add(savePOICategory(resultset));	            	
        }
		return result;
	}
	
	/*
	 * Supporting-method to add all found POIs to a given POICategory.
	 */
	private void addPOIsToCategory(POICategory poicat) {
		if (poicat == null) {
			throw new IllegalArgumentException("poicat cannot be null.");
		}
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_poi_poicat WHERE category_id = " + poicat.getUid();
		
		ResultSet resultset = null;

		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
	        while (resultset.next()) {
				int poiid = resultset.getInt("poi_id");
	        	poicat.add(getPOI(poiid));  	
	        }
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }
	}

}
