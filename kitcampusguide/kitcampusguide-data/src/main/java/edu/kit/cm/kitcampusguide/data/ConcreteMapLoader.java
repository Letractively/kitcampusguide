package edu.kit.cm.kitcampusguide.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * Concrete Implementation of the MapLoader, uses a PostgreSQL database.
 * 
 * @author Michael Hauber
 */
public class ConcreteMapLoader implements MapLoader {

	/**
	 * {@inheritDoc}
	 */
	public Graph getGraph() {
		if (ConstantData.getGraph() == null) {
			ArrayList<Integer> streetnodeId = new ArrayList<Integer>();
			ArrayList<Double> streetnodeX = new ArrayList<Double>();
			ArrayList<Double> streetnodeY = new ArrayList<Double>();
			
			ArrayList<Integer> streetFrom = new ArrayList<Integer>();
			ArrayList<Integer> streetTo = new ArrayList<Integer>();
			ArrayList<Double> streetLength = new ArrayList<Double>();
			
			String dbURL = "jdbc:" + Config.DB_TYPE + "://" + Config.DB_HOST + ":" + Config.DB_PORT + "/" + Config.DB_DATABASE;
			
			Connection connection = null;
			Statement statement = null;
			ResultSet resultset = null;
			
			try {
		        Class.forName("org.postgresql.Driver");
		        connection = DriverManager.getConnection(dbURL, Config.DB_USERNAME, Config.DB_PASSWORD);
	
		        statement = connection.createStatement();
		        resultset = statement.executeQuery("SELECT * FROM cg_streetnode");     
		        
		        while (resultset.next()) {
		            streetnodeId.add(resultset.getInt(1));
		            streetnodeX.add(resultset.getDouble(2));
		            streetnodeY.add(resultset.getDouble(3));
				}
		        
		        
		        statement = connection.createStatement();
		        resultset = statement.executeQuery("SELECT * FROM cg_street");     
		        
		        while (resultset.next()) {
		        	streetFrom.add(resultset.getInt(1));
		        	streetTo.add(resultset.getInt(2));
		        	streetLength.add(resultset.getDouble(3));
				}
	
		        
			} catch (Exception ex) {
				ex.printStackTrace();
		    } finally {
		        try { if (null != resultset) resultset.close(); } catch (Exception ex) { ex.printStackTrace(); }
		        try { if (null != statement) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
		        try { if (null != connection) connection.close(); } catch (Exception ex) { ex.printStackTrace(); }
		    }
		      
		    Point[] points = new Point[streetnodeId.size()];
		      
		    for (int i = 0; i < streetnodeId.size(); i++) {
		    	points[streetnodeId.get(i)] = new Point(streetnodeX.get(i), streetnodeY.get(i)); 
		    }
		      
		      
		    Integer[] vertices = new Integer[streetnodeId.size() + 1];
		    Integer[] edges = new Integer[streetFrom.size()];
		    Double[] lengths = new Double[streetFrom.size()];
		    vertices[0] = 0;
		     
		    for (int i = 1; i < vertices.length; i++) {
		    	vertices[i] = 0;
		    }
		      
		    // calculate amount of edges of a node.
			for (Integer from : streetFrom) {
				vertices[from]++;
			}
			  
			// calculate offsets.
			for (int i = 1; i < vertices.length; i++) {
				vertices[i] += vertices[i - 1];
			}
		      
			for (int i = 0; i < streetFrom.size(); i++) {
				edges[--vertices[streetFrom.get(i)]] = streetTo.get(i);
				lengths[vertices[streetFrom.get(i)]] = streetLength.get(i);
			}
			  
			 
			ConstantData.setGraph(new Graph(points, lengths, vertices, edges));
		}
	    return ConstantData.getGraph();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Point[] getLandmarks() {
		ArrayList<Point> pointlist = new ArrayList<Point>();
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlquery = "SELECT * FROM cg_landmark";
		
		ResultSet resultset = null;
		
		try {
			resultset = Config.executeSQLStatement(connection, sqlquery);
	        while (resultset.next()) {
	        	double latitude = resultset.getDouble(2);
	        	double longitude = resultset.getDouble(3);
	        	pointlist.add(new Point(latitude, longitude));
			}
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }
		
	    
	    Point[] landmarks = new Point[pointlist.size()];
	    landmarks = pointlist.toArray(landmarks);
	    return landmarks;
	}

	/**
	 * {@inheritDoc}
	 */
	public double[][] getLandmarkDistances() {
		ArrayList<Integer> streetnodelist = new ArrayList<Integer>();
		ArrayList<Integer> landmarklist = new ArrayList<Integer>();
		ArrayList<Double> lengthlist = new ArrayList<Double>();
		int count = 0;
		
		Connection connection = Config.getPgSQLJDBCConnection();
		String sqlqueryDistance = "SELECT * FROM cg_distance";
		String sqlqueryLandmarks = "SELECT * FROM cg_landmark";
		
		ResultSet resultset = null;
		
		try {
			resultset = Config.executeSQLStatement(connection, sqlqueryDistance);
	        while (resultset.next()) {
	        	streetnodelist.add(resultset.getInt(1));
	        	landmarklist.add(resultset.getInt(2));
	        	lengthlist.add(resultset.getDouble(3));
			}
	        
	        
	        resultset = Config.executeSQLStatement(connection, sqlqueryLandmarks);
	        while (resultset.next()) {
	        	count++;
			}
	        
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }
	    double[][] result = new double[count][streetnodelist.size()];
	    
	    for (int i = 0; i < streetnodelist.size(); i++) {
	        result[landmarklist.get(i)][streetnodelist.get(i)] = lengthlist.get(i);
	    }

	    return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addLandmarkToDatabase(Point landmark, double[] distances) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;

		try { 
	        connection = Config.getPgSQLJDBCConnection();

	        statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO cg_landmark (latitude, longitude) " 
            		+ "VALUES ('" + landmark.getLongitude() + "', '" + landmark.getLatitude() + "')");
	        
            resultset = statement.executeQuery("SELECT id FROM cg_landmark WHERE latitude = '" + landmark.getLongitude() 
            		+ "' AND longitude = '" + landmark.getLatitude() + "'");
            resultset.next();
            int landmarkid = resultset.getInt("id");
            
	        for (int i = 0; i < distances.length; i++) {
	        	statement.executeUpdate("INSERT INTO cg_distance (streetnode_id, landmark_id, length) "
	        		+ "VALUES ('" + i + "', '" + landmarkid + "', '" + distances[i] + "')");
	        }

        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
            System.err.println(e.getStackTrace());
        } finally {
            try { if (null != statement) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (null != connection) connection.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}
