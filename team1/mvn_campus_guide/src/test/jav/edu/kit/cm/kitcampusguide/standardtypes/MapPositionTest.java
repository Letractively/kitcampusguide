package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests all methods implemented by {@link MapPosition}.
 * 
 * @author Stefan
 * 
 */
public class MapPositionTest {

	private static final Random random = new Random(1234);

	private static final double EPSILON = 1E-12;
	
	/**
	 * Stores a list of maps used for testing.
	 */
	private static List<Map> maps;

	/**
	 * Creates some test maps.
	 */
	@BeforeClass
	public static void createMaps() {
		maps = new ArrayList<Map>();
		for (int i = 0; i < 20; i++) {
			maps.add(new Map(Idgenerator.getFreeMapID(), "", new MapSection(
					new WorldPosition(0, 0), new WorldPosition(1, 1)), "", 0, 0));
		}
	}

	/**
	 * Tests if {@link MapPosition#MapPosition(double, double, Map)} creates a
	 * valid new {@link MapPosition}.
	 */
	@Test
	public void testMapPosition() {

		for (int i = 0; i < 1000; i++) {
			Map map = maps.get(random.nextInt(maps.size()));
			double longitude = random.nextDouble();
			double latitude = random.nextDouble();
			MapPosition mapPosition = new MapPosition(latitude, longitude, map);

			assertEquals(map, mapPosition.getMap());
			assertEquals(longitude, mapPosition.getLongitude(), 0);
			assertEquals(latitude, mapPosition.getLatitude(), 0);
		}
	}

	/**
	 * Tests {@link MapPosition#calculateDistance(MapPosition, MapPosition)} by
	 * generating random test positions and checking the distance with
	 * {@link WorldPosition#calculateDistance(WorldPosition, WorldPosition)}.
	 * <code>WorldPosition.calculateDistance</code> must be working correctly.
	 */
	@Test
	public void testCalculateDistanceMapPositionMapPosition() {
		for (int i = 0; i < 1000; i++) {
			Map map1 = maps.get(random.nextInt(maps.size()));
			Map map2 = (random.nextDouble() > .8) ? maps.get(random
					.nextInt(maps.size())) : map1;
			MapPosition pos1 = new MapPosition(random.nextDouble(),
					random.nextDouble(), map1);
			MapPosition pos2 = new MapPosition(random.nextDouble(),
					random.nextDouble(), map2);
			
			if (map1.equals(map2)) {
				assertEquals(WorldPosition.calculateDistance(pos1, pos2),
						MapPosition.calculateDistance(pos1, pos2), EPSILON);
			} else {
				assertTrue(Double.POSITIVE_INFINITY == MapPosition
						.calculateDistance(pos1, pos2));
			}
		}
	}

}
