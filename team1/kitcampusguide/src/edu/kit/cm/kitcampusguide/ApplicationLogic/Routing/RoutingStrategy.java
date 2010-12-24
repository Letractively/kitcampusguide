package edu.kit.cm.kitcampusguide.ApplicationLogic.Routing;
import edu.kit.cm.kitcampusguide.standardtypes.*;

public class RoutingStrategy {
	
	/**Stores the only instance of RoutingStrategy.*/ 
	static RoutingStrategy instance;
	
	DijkstraRouting routing;
	
	/**
	 * Private constructor.
	 */
	private RoutingStrategy() {
		routing = DijkstraRouting.getInstance();
	}
	
	/**
	 * Calculates a route from <code>from</code> to <code>to</code>.
	 * This class delegates the actual calculation.
	 * @param from {@link MapPosition Position} the route starts at.
	 * @param to {@link MapPosition Position} the route ends at.
	 * @return The {@link Route route} between <code>from</code> and <code>to</code>.
	 */
	public Route calculateRoute(MapPosition from, MapPosition to) {
		return routing.calculateRoute(from, to);
	}
	
	/**
	 * Returns the only instance of RoutingStrategy or creates it, if it doesn't exist and returns the new.
	 * @return The only RoutingStrategy.
	 */
	public static RoutingStrategy getInstance() {
		if (instance == null) {
			instance = new RoutingStrategy();
		}
		return instance;
	}
	
}
