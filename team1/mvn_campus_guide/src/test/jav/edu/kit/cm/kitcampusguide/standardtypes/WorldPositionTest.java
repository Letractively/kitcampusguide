package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

/**
 * Tests the basic features of a WorldPosition.
 * 
 * @author Stefan
 * 
 */
public class WorldPositionTest {

	private static final double EPSILON = 1E-14;
	
	private static final Random random = new Random(314159265);
	
	/**
	 * Creates 1000 WorldPositions with random positions and checks whether they
	 * are created correctly. Tests {@link WorldPosition#getLongitude()},
	 * {@link WorldPosition#getLatitude()} and {@link WorldPosition#equals} as
	 * well.
	 */
	@Test
	public void testWorldPosition() {
		for (int i = 0; i < 1000; i++) {
			double lon = random.nextDouble();
			double lat = random.nextDouble();
			WorldPosition wp = new WorldPosition(lat, lon);
			WorldPosition wp2 = new WorldPosition(lat, lon);
			assertTrue(wp.equals(wp2));
			assertEquals(lon, wp.getLongitude(), EPSILON);
			assertEquals(lat, wp.getLatitude(), EPSILON);
		}
	}

	/**
	 * Tests if the check bounds method works properly by passing some values
	 * close to the allowed boundaries.
	 */
	@Test
	public void testCheckBounds() {
		assertFalse(WorldPosition.checkBounds(-90.00001, 0));
		assertFalse(WorldPosition.checkBounds(90.000001, 5));
		assertFalse(WorldPosition.checkBounds(0, 180.000001));
		assertFalse(WorldPosition.checkBounds(-3, -180.00001));

		assertTrue(WorldPosition.checkBounds(20, 40));
		assertTrue(WorldPosition.checkBounds(4, 2));
		assertTrue(WorldPosition.checkBounds(89, 179));
		assertTrue(WorldPosition.checkBounds(-89.9999, 40));
		assertTrue(WorldPosition.checkBounds(89.9999, -179.9999));
	}

	/**
	 * Tests different distance calculation. The allowed error depends on the
	 * size of the test distance.<br />
	 * The test distances were calculated with some web tools.
	 */
	@Test
	public void testCalculateDistance() {
		final double d = 750;

		// Karlsruhe to Munic
		WorldPosition pos1 = new WorldPosition(49.0080848, 8.4037563);
		WorldPosition pos2 = new WorldPosition(48.1391265, 11.5801863);
		double distance = 253.144E3;
		assertEquals(distance, WorldPosition.calculateDistance(pos1, pos2),
				distance / d);

		// New York to Moskau
		pos1 = new WorldPosition(40.7143528, -74.0059731);
		pos2 = new WorldPosition(55.7557860, 37.6176330);
		distance = 7518.551E3;
		assertEquals(distance, WorldPosition.calculateDistance(pos1, pos2),
				distance / d);

		// New York to Hong Kong
		pos2 = new WorldPosition(22.3964280, 114.1094970);
		distance = 12961.959E3;
		assertEquals(distance, WorldPosition.calculateDistance(pos1, pos2),
				distance / d);
		
		// Kronenplatz to Marktplatz
		pos1 = new WorldPosition(49.0088800,8.4088900);
		pos2 = new WorldPosition(49.0088889,8.4038889);
		distance = 0.365E3;
		assertEquals(distance, WorldPosition.calculateDistance(pos1, pos2),
				distance / d);
		
		// Kaiserstrasse 11 to Kaiserstrassse 13
		pos1 = new WorldPosition(49.5498000,7.8530000);
		pos2 = new WorldPosition(49.5498991,7.8532524);
		distance = 21.27351;
		assertEquals(distance, WorldPosition.calculateDistance(pos1, pos2),
				distance / d);
	}

}
