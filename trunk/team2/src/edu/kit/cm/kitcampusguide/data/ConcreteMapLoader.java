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
	public Graph getGraph() {
		ArrayList<Integer> streetnodeId = new ArrayList<Integer>();
		ArrayList<Double> streetnodeX = new ArrayList<Double>();
		ArrayList<Double> streetnodeY = new ArrayList<Double>();
		
		ArrayList<Integer> streetFrom = new ArrayList<Integer>();
		ArrayList<Integer> streetTo = new ArrayList<Integer>();
		ArrayList<Double> streetLength = new ArrayList<Double>();
		
		String dbURL = "jdbc:" + Config.dbType + "://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbDatabase;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
	        Class.forName("org.postgresql.Driver" );
	        connection = DriverManager.getConnection(dbURL, Config.dbUsername, Config.dbPassword);

	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_street");     
	        
	        while(resultset.next()) {
	            streetnodeId.add(resultset.getInt(1));
	            streetnodeX.add(resultset.getDouble(2));
	            streetnodeY.add(resultset.getDouble(3));
			}
	        
	        
	        statement = connection.createStatement();
	        resultset = statement.executeQuery("SELECT * FROM cg_streetnode");     
	        
	        while(resultset.next()) {
	        	streetFrom.add(resultset.getInt(1));
	        	streetTo.add(resultset.getInt(2));
	        	streetLength.add(resultset.getDouble(3));
			}

	        
	      } catch( Exception ex ) {
	          System.out.println( ex );
	      } finally {
	          try { if( null != resultset ) resultset.close(); } catch( Exception ex ) {}
	          try { if( null != statement ) statement.close(); } catch( Exception ex ) {}
	          try { if( null != connection ) connection.close(); } catch( Exception ex ) {}
	      }
	      
	      
	      // process to Graph data structure.
	      
	      ArrayList<Point> resPoints = new ArrayList<Point>();
	      
	      for (int i = 0; i <= streetnodeId.size(); i++) {
	    	  resPoints.set(streetnodeId.get(i), new Point(streetnodeX.get(i), streetnodeY.get(i))); 
	      }
	      
	      
	      Integer vertices[] = new Integer[streetnodeId.size() + 1];
	      Integer edges[] = new Integer[streetFrom.size() + 1];
	      Double lengths[] = new Double[streetFrom.size() + 1];
	      vertices[0] = 1;
	     
	      // calculate amount of edges of a node.
		  for (Integer from : streetFrom) {
		      vertices[from]++;
		  }
		  
		  // calculate offsets.
		  for (int i = 1; i <= vertices.length; i++) {
			  vertices[i] += vertices[i - 1];
		  }
	      
		  for (int i = 0; i <= streetFrom.size(); i++) {
			  edges[--vertices[streetFrom.get(i)]] = streetTo.get(i);
			  lengths[--vertices[streetFrom.get(i)]] = streetLength.get(i);
			  
		  }
		  
		 
		   ArrayList<Integer> resVertices = new ArrayList<Integer>();
		   for (int i = 0; i < vertices.length; i++) {
			   resVertices.add(vertices[i]);
		   }
		   
		   ArrayList<Integer> resEdges = new ArrayList<Integer>();
		   ArrayList<Double> resLengths = new ArrayList<Double>();
		   for (int i = 0; i < edges.length; i++) {
			   resEdges.add(edges[i]);
			   resLengths.add(lengths[i]);
		   }

		  Graph graph;
		  graph = new Graph(resPoints, resLengths, resVertices, resEdges);
		  
	      return graph;
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
	      
	      // unsch�n? ;)
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
	      
	      // unsch�n? ;)
	      for (int i = 0; i < streetnodelist.size(); i++) {
	          result[streetnodelist.get(i)][landmarklist.get(i)] = lengthlist.get(i);
	      }

	      return result;
	}

}