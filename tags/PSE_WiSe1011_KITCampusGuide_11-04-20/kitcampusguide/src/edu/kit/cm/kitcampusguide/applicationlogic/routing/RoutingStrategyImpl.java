package edu.kit.cm.kitcampusguide.applicationlogic.routing;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * A simple routing strategy that uses the {@link PreCalculatedRouting} class
 * for calculations. If necessary, other strategies for finding a route can be
 * easily added in the future.
 * 
 * @author Stefan
 * 
 */
public class RoutingStrategyImpl implements RoutingStrategy {
	
	/** Stores the singleton instance of <code>RoutingStrategyImpl</code>. */
	private static RoutingStrategyImpl instance;

	/**
	 * Until now all calculations are executed with this strategy.
	 */
	private PreCalculatedRouting routing;
	
	/**
	 * Private constructor.
	 */
	private RoutingStrategyImpl() {
		routing = PreCalculatedRouting.getInstance();
	}

	@Override
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
