package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;


/**
 * Tests the {@link Route} class.
 * 
 * @author Stefan
 * 
 */
public class RouteTest {

	/**
	 * Maximal amount of waypoints tested.
	 */
	private static final int MAX_SIZE = 500;

	private static final Random rnd = new Random(1234);

	/**
	 * Tests all functions implemented by {@link Route}.
	 */
	@Test
	public void testRoute() {
		ArrayList<MapPosition> positions = new ArrayList<MapPosition>(MAX_SIZE);
		ArrayList<MapPosition> positionsCopy = new ArrayList<MapPosition>(
				MAX_SIZE);
		Map map = new Map(0, "", new MapSection(new WorldPosition(1, 2),
				new WorldPosition(3, 4)), "", 0, 0);

		for (int i = 0; i < 1000; i++) {
			int size = rnd.nextInt(MAX_SIZE - 2) + 2;

			double minLat, minLon, maxLat, maxLon;
			minLat = minLon = Double.MAX_VALUE;
			maxLat = maxLon = Double.MIN_VALUE;
			
			for (int j = 0; j < size; j++) {
				MapPosition pos = new MapPosition(rnd.nextDouble(),
						rnd.nextDouble(), map);
				minLon = Math.min(minLon, pos.getLongitude());
				maxLon = Math.max(maxLon, pos.getLongitude());
				minLat = Math.min(minLat, pos.getLatitude());
				maxLat = Math.max(maxLat, pos.getLatitude());
				positions.add(pos);
				positionsCopy.add(pos);
			}


			Route route = new Route(positions);
			assertEquals(positions, positionsCopy);
			assertNotSame(positions, route.getWaypoints());
			
			// Check if the route is immutable
			positions.set(0, new MapPosition(-1, -1, map));
			assertFalse(positions.equals(route.getWaypoints()));
			
			assertEquals(route.getWaypoints().get(0), route.getStart());
			assertEquals(route.getWaypoints().get(size - 1), route.getEnd());
			
			try {
				route.getWaypoints().set(0, null);
				assertNotNull(route.getStart());
			} catch (UnsupportedOperationException e) {
				// This is allowed
			}
			
			MapSection box = new MapSection(new WorldPosition(minLat, minLon),
					new WorldPosition(maxLat, maxLon));
			assertEquals(box, route.getBoundingBox());
			
			positions.clear();
			positionsCopy.clear();
		}
	}

}
