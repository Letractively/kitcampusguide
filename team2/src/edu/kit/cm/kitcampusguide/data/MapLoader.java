package edu.kit.cm.kitcampusguide.data;

import edu.kit.cm.kitcampusguide.model.*;

/**
 * 
 * @author Michael Hauber
 * @author Tobias Zündorf
 *
 */
public interface MapLoader {
	
	/**
	 * 
	 * @return
	 */
	public Graph getGraph();
	
	/**
	 * Creates a Graph containing intersections, which are stored in the database, but not the streets 
	 * connecting them. Additional the Graph contains some special nodes, named landmarks, with degree zero. 
	 * For each pair of intersection-node and landmark-node the Graph contains an edge with their distance.
	 * 
	 * @return A Graph representing the map with additional landmarks
	 */
	// TODO zum Entwurf hinzufügen
	public Graph getLandmarks();

}
