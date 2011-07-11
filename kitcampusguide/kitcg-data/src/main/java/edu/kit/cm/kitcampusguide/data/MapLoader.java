package edu.kit.cm.kitcampusguide.data;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

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

}
