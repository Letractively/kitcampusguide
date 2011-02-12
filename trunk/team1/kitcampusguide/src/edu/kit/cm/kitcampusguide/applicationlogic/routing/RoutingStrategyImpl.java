package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import edu.kit.cm.kitcampusguide.standardtypes.*;

public class RoutingStrategyImpl implements RoutingStrategy {
	
	/**Stores the only instance of RoutingStrategy.*/ 
	private static RoutingStrategyImpl instance;
	
	private PreCalculatedRouting routing;
	
	/**
	 * Private constructor.
	 */
	private RoutingStrategyImpl() {
		routing = PreCalculatedRouting.getInstance();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Route calculateRoute(MapPosition from, MapPosition to) {
		return routing.calculateRoute(from, to);
	}
	
	/**
	 * Returns the only instance of RoutingStrategy or creates it, if it doesn't exist and returns the new.
	 * @return The only RoutingStrategy.
	 */
	public static RoutingStrategyImpl getInstance() {
		if (instance == null) {
			instance = new RoutingStrategyImpl();
		}
		return instance;
	}
	
}
