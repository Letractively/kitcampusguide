/**
 * 
 */
package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import static edu.kit.cm.kitcampusguide.testframework.Idgenerator.*;

/**
 * @author Fabian
 *
 */
public class DijkstraRoutingCalculationTest {

	/**
	 * Creates an instance of {@link RoutingGraph} to run the tests.
	 * The Graph has the following edges (start -> end|weight):
	 * 0 -> 2|1;
	 * 1 -> 0|2;
	 * 1 -> 2|3;
	 * 1 -> 3|1.2;
	 * 1 -> 4|1.5;
	 * 2 -> 0|1;
	 * 2 -> 1|3;
	 * 2 -> 3|1;
	 * 3 -> 1|1.2;
	 * 3 -> 2|1;
	 * 4 -> 1|1.5;
	 * 4 -> 3|2.5;
	 * 
	 * This concludes the following arrays:
	 * vertices: {0, 1, 5, 8, 10, 12, 12}
	 * edges: {2, 0, 2, 3, 4, 0, 1, 3, 1, 2, 1, 3}
	 * weights: {1, 2, 3, 1.2, 1.5, 1, 3, 1, 1.2, 1, 1.5, 2.5}
	 * 
	 * MapPositions: { (1|0|1), (1|2|1), (0|0|1), (0|2|1), (1.5|2.5|1), (1.5|2.5|2) }
	 * (longitude | latitude | MapID)
	 * Note: There is no dependency between MapPositions and weights.
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		int[] verticesArray = {0, 1, 5, 8, 10, 12, 12};
		int[] edgeArray = {2, 0, 2, 3, 4, 0, 1, 3, 1, 2, 1, 3};
		double[] weightArray = {1, 2, 3, 1.2, 1.5, 1, 3, 1, 1.2, 1, 1.5, 2.5};
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = new WorldPosition(3, 3);
		MapSection boundingBox = new MapSection(pos1, pos2);
		Map rootmap;
		if (requestMapID(1)) {
			rootmap = new Map(1, "rootmap", boundingBox , "null", 0, 0);
		} else {
			rootmap = Map.getMapByID(1);
		}
		Map map2;
		if (requestMapID(2)) {
			map2 = new Map(2, "map", boundingBox , "null", 0, 0);
		} else {
			map2 = Map.getMapByID(2);
		}
		MapPosition[] positionArray = {	new MapPosition(1, 0, rootmap), 
										new MapPosition(1, 2, rootmap), 
										new MapPosition(0, 0, rootmap),
										new MapPosition(0, 2, rootmap),
										new MapPosition(1.5, 2.5, rootmap),
										new MapPosition(1.5, 2.5, map2)};
		RoutingGraph.initializeGraph(verticesArray, edgeArray, weightArray, positionArray);
		RoutingGraph.getInstance();
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.applicationlogic.routing.DijkstraRoutingCalculation#constructRoute(edu.kit.cm.kitcampusguide.standardtypes.MapPosition)}.
	 */
	@Test
	public void testConstructRoute() {
		BasicConfigurator.configure();
		MapPosition from1 = new MapPosition(1, 2, Map.getMapByID(1));
		DijkstraRoutingCalculation dijkstraRoutingCalc1 = new DijkstraRoutingCalculation(from1);
		MapPosition from2 = new MapPosition(1.1, 2.1, Map.getMapByID(1));
		DijkstraRoutingCalculation dijkstraRoutingCalc2 = new DijkstraRoutingCalculation(from2);
		Route routeToSelf = dijkstraRoutingCalc1.constructRoute(from2);
		assertTrue(routeEquals(routeToSelf, dijkstraRoutingCalc2.constructRoute(from1)));
		Route route1 = new Route(Arrays.asList(	new MapPosition(1, 2, Map.getMapByID(1)), 
												new MapPosition(0, 2, Map.getMapByID(1)), 
												new MapPosition(0, 0, Map.getMapByID(1))));
		MapPosition route1end = new MapPosition(0, 0, Map.getMapByID(1));
		assertTrue((route1.equals(dijkstraRoutingCalc2.constructRoute(route1end))));
	}
	
	
	/**
	 * Tests for no route possible.
	 */
	@Test
	public void testConstructRouteNotConnected() {
		MapPosition from1 = new MapPosition(1, 2, Map.getMapByID(1));
		DijkstraRoutingCalculation dijkstraRoutingCalc1 = new DijkstraRoutingCalculation(from1);
		MapPosition notConnected = new MapPosition(1, 2, Map.getMapByID(2));
		assertNull(dijkstraRoutingCalc1.constructRoute(notConnected));
	}
	
	private static boolean routeEquals(Route route1, Route route2) {
		List<MapPosition> waypoints1 = route1.getWaypoints();
		List<MapPosition> waypoints2 = route2.getWaypoints();
		if (waypoints1.size() != waypoints2.size()) {
			return false;
		} else {
			for (int i = 0; i < waypoints1.size(); i++) {
				if (!waypoints1.get(i).equals(waypoints2.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	
}

