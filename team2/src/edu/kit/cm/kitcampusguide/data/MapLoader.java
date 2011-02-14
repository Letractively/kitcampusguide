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
	public Graph getGraph();
	

	/**
	 * Loads a List containing all landmark Points stored in the database.
	 * The indices are conform to the indices in getLandmarkDistances().
	 * 
	 * @return a Point-Array with the coordinates of all landmarks.
	 */
	// TODO zum Entwurf hinzufügen
	public Point[] getLandmarks();

	/**
	 * Loads a table containing the distances between each pair of intersection and landmark.
	 * The first array index identifies an intersection node, the second array index identifies a 
	 * landmark. All indices are confirm to getGraph() and getLandmarks();
	 * 
	 * @return a 2-dimensional double-Array, the first array index identifies a intersection node,
	 * the second array index identifies a landmark.
	 */
	// TODO zum Entwurf hinzufügen
	public double[][] getLandmarkDistances();
	
	/**
	 * Adds a given landmark with all his distances to the database.
	 * the index of the double-array identifies an intersection node.
	 */
	// TODO zum Entwurf hinzufügen
	public void addLandmarkToDatabase(Point landmark, double[] distances);

	/**
	 * 
	 * @param fromId
	 * @param toId
	 * @param length
	 */
	// TODO zum Entwurf hinzufügen
	public void addStreetToDatabase(int fromId, int toId, double length);

}
