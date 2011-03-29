package edu.kit.cm.kitcampusguide.data;

import edu.kit.cm.kitcampusguide.model.*;

/**
 * Interface of a MapLoader.
 * 
 * @author Michael Hauber
 * @author Tobias Zündorf
 *
 */
public interface MapLoader {
	
	/**
	 * Loads a Graph containing all intersections (as nodes) and streets (as edges) from the database.
	 * The node indices within the resulting Graph are conform to the node indices in getLandmarkDistances().
	 * 
	 * @return a Graph representing the map
	 */
	Graph getGraph();
	

	/**
	 * Loads a List containing all landmark Points stored in the database.
	 * The indices are conform to the indices in getLandmarkDistances().
	 * 
	 * @return a Point-Array with the coordinates of all landmarks.
	 */
	Point[] getLandmarks();

	/**
	 * Loads a table containing the distances between each pair of intersection and landmark.
	 * The first array index identifies a landmark, the second array index identifies an intersection node. All 
	 * indices are confirm to getGraph() and getLandmarks();
	 * 
	 * @return a 2-dimensional double-Array, the first array index identifies a landmark,
	 * the second array index identifies an intersection node.
	 */
	double[][] getLandmarkDistances();
	
	/**
	 * Adds a given landmark with all his distances to the database.
	 * the index of the double-array identifies an intersection node.
	 * 
	 * @param landmark given point to add as landmark
	 * @param distances distances to all other nodes as double-array.
	 */
	void addLandmarkToDatabase(Point landmark, double[] distances);

	/**
	 * Adds a given street to the saved graph in the database.
	 * It adds just the given street, not a street in two directions.
	 * 
	 * @param fromId streetnode id of the outgoing node.
	 * @param toId streetnode id of the incoming node.
	 * @param length length of the street.
	 */
	void addStreetToDatabase(int fromId, int toId, double length);

	/**
	 * Adds the given point to the saved graph in the database.
	 * Returns the ID of the new streetnode, returns -1 if the update failed.
	 * 
	 * @param longitude the longitude of the new node
	 * @param latitude the latitude of the new node
	 * @return the index of the inserted node
	 */
	public int addStreetNodeToDatabase(double latitude, double longitude);

}
