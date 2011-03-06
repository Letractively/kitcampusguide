package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

/**
 * Tests all methods of {@link MapSection}
 * @author Stefan
 *
 */
public class MapSectionTest {

	private static final Random random = new Random(1234);
	
	/**
	 * Test all methods implemented by {@link MapSection}. Random test data is
	 * generated and used to create new <code>MapSection</code> objects.
	 */
	@Test
	public void testMapSection() {
		for (int i = 0; i < 1000; i++) {
			WorldPosition pos1 = new WorldPosition(random.nextDouble(),
					random.nextDouble());
			WorldPosition pos2 = new WorldPosition(random.nextDouble(),
					random.nextDouble());
			double minLat = Math.min(pos1.getLatitude(), pos2.getLatitude());
			double maxLat = Math.max(pos1.getLatitude(), pos2.getLatitude());
			double minLon = Math.min(pos1.getLongitude(), pos2.getLongitude());
			double maxLon = Math.max(pos1.getLongitude(), pos2.getLongitude());
			
			MapSection mapSection = new MapSection(pos1, pos2);
			MapSection mapSection2 = new MapSection(pos1, pos2);
			MapSection mapSection3 = new MapSection(pos2, pos1);
			
			WorldPosition southEast = new WorldPosition(maxLat, maxLon);
			WorldPosition northWest = new WorldPosition(minLat, minLon);
			
			assertEquals(northWest, mapSection.getNorthWest());
			assertEquals(southEast, mapSection.getSouthEast());
			
			assertEquals(mapSection, mapSection3);
			assertEquals(mapSection, mapSection2);
			assertFalse(mapSection.equals(null));
			
		}
	}

}
