package edu.kit.cm.kitcampusguide.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This utility class provides several methods to edit the data stored in the database to simplify 
 * adding a street node to the database.
 * @author Hao
 */
public final class DatabaseUtility {
	
	/* private constructor to prevent instantiations */
	private DatabaseUtility() {
		assert false;
	}
	
	/**
	 * Adds a given street to the saved graph in the database.
	 * It adds just the given street, not a street in two directions.
	 * 
	 * @param fromId streetnode id of the outgoing node.
	 * @param toId streetnode id of the incoming node.
	 * @param length length of the street.
	 */
	public static void addStreetToDatabase(int fromId, int toId, double length) {
		if (fromId <= 0 || toId <= 0 || length <= 0) {
			throw new IllegalArgumentException();
		}
		
		Connection connection = null;
		Statement statement = null;

		try { 
	        connection = Config.getPgSQLJDBCConnection();

	        statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO cg_street (from_id, to_id, length) "
            		+ "VALUES ('" + fromId + "', '" + toId + "', '" + length + "')");
            
        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
            System.err.println(e.getStackTrace());
        } finally {
            try { if (null != statement) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (null != connection) connection.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }
	}
	

	/**
	 * Adds the given point to the saved graph in the database.
	 * Returns the ID of the new streetnode, returns -1 if the update failed.
	 * 
	 * @param longitude the longitude of the new node
	 * @param latitude the latitude of the new node
	 * @return the index of the inserted node
	 */
	public static int addStreetNodeToDatabase(double latitude, double longitude) {	
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		int nodeId = -1;

		try { 
	        connection = Config.getPgSQLJDBCConnection();

	        statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO cg_streetnode (id, latitude, longitude) " 
            		+ "VALUES (nextval('cg_streetnode_id_seq'), '" + latitude + "', '" + longitude + "')");
            
            resultset = statement.executeQuery("SELECT id FROM cg_streetnode WHERE id = currval('cg_streetnode_id_seq')");
            resultset.next();
            nodeId = resultset.getInt("id");
            
        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
            System.err.println(e.getStackTrace());
        } finally {
            try { if (null != statement) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (null != connection) connection.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }

		return nodeId;
	}
}
