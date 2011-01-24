package edu.kit.cm.kitcampusguide.mapAlgorithms;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This interface defines the possibility to calculate and get routes between points.
 * A class implementing this interface should use <code>RouteCalculatingUtility</code> for
 * its calculating.
 * 
 * @author Tobias Zündorf
 *
 */
public interface RouteCalculator {

	/**
	 * Calculates and Returns the shortest route between the two specified Points within
	 * the mapGraph. 
	 * 
	 * @param from	the start point of the route
	 * @param to the end point of the route
	 * @param mapGraph the Graph to search in
	 * @return the shortest route between the two specified Points
	 */
	// TODO parameter mapGraph im Entwurf einfügen
	public Route calculateRoute(Point from, Point to, Graph mapGraph);
	
}
