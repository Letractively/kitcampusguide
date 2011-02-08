package edu.kit.cm.kitcampusguide.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
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
	@Override
	/* MISSING */
	public Graph getGraph() {
		ArrayList<Integer> streetnodelist = new ArrayList<Integer>();
		ArrayList<Integer> landmarklist = new ArrayList<Integer>();
		ArrayList<Double> lengthlist = new ArrayList<Double>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);

	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_");     
	        
	        while(resultset.next()) {
	        	// TODO
			}
	        
	      } catch( Exception ex ) {
	          System.out.println( ex );
	      } finally {
	          try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	          try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	          try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      
	      
	      return null;
		
	     /*   Graph graph
		      graph = new Graph();
		
		      return null;
		 */
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point[] getLandmarks() {
		ArrayList<Point> pointlist = new ArrayList<Point>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);
	        
	        
	        
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_landmark");     
	        
	        while(resultset.next()) {
	        	double latitude = resultset.getDouble(2);
	        	double longitude = resultset.getDouble(3);
	        	pointlist.add(new Point(latitude, longitude));
			}
	        
	      } catch( Exception ex ) {
	          System.out.println( ex );
	      } finally {
	          try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	          try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	          try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      // unschön? ;)
	      Point[] landmarks = new Point[pointlist.size()];
	      landmarks = pointlist.toArray(landmarks);
	      return landmarks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[][] getLandmarkDistances() {
		ArrayList<Integer> streetnodelist = new ArrayList<Integer>();
		ArrayList<Integer> landmarklist = new ArrayList<Integer>();
		ArrayList<Double> lengthlist = new ArrayList<Double>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);

	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_distance");     
	        
	        while(resultset.next()) {
	        	streetnodelist.add(resultset.getInt(1));
	        	landmarklist.add(resultset.getInt(2));
	        	lengthlist.add(resultset.getDouble(3));
			}
	        
	      } catch( Exception ex ) {
	          System.out.println( ex );
	      } finally {
	          try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	          try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	          try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      double[][] result = new double[streetnodelist.size()][streetnodelist.size()];
	      
	      // unschön? ;)
	      for (int i = 0; i < streetnodelist.size(); i++) {
	          result[streetnodelist.get(i)][landmarklist.get(i)] = lengthlist.get(i);
	      }

	      return result;
	}

}
