package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This class is the concrete implementation of the MapAlgorithms interface.
 * 
 * @author Tobias Zündorf
 * 
 */
public class ConcreteMapAlgorithms implements MapAlgorithms {

	/*
	 * The QueryCalculator used by this implementation.
	 */
	private QueryCalculator queryCalculator = ConcreteQueryCalculator
			.getSingleton();

	/*
	 * The RouteCalculator used by this implementation.
	 */
	private RouteCalculator routeCalculator = AStar.getSingleton();

	@Override
	public List<POI> getSuggestions(String name) {
		return queryCalculator.getSuggestions(name);
	}

	@Override
	public List<String> getSuggestionsNames(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POI searchPOI(String name) {
		return queryCalculator.searchPOI(name);
	}

	@Override
	public Route calculateRoute(Point from, Point to) {
		return routeCalculator.calculateRoute(
				from,
				to,
				RouteCalculatingUtility.calculateStreetGraph(new Point[] {
						from, to }));
	}

}
