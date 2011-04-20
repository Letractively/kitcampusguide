package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

import org.junit.Test;

/**
 * Tests the methods implemented by {@link POI}.
 * 
 * @author Stefan
 * 
 */
public class POITest {

	private static final Random random = new Random(1234);

	/**
	 * Tests the methods implemented by {@link POI}. The methods generates
	 * random test data, creates new POIs and checks if all properties were set
	 * correctly.
	 */
	@Test
	public void testPOI() {
		List<Map> maps = new ArrayList<Map>();
		MapSection boundingBox = new MapSection(new WorldPosition(1, 2),
				new WorldPosition(2, 4));

		for (int i = 0; i < 10; i++) {
			maps.add(new Map(Idgenerator.getFreeMapID(), "", boundingBox, "", 0, 0));
		}

		List<Category> categories = new ArrayList<Category>();
		for (int i = 0; i < 20; i++) {
			categories.add(new Category(Idgenerator.getFreeCategoryID(), ""));
		}
		POI theBuildingPOI = new POI("buildingPOI", "", null,
				new WorldPosition(1, 1), maps.get(0), null, categories);
		List<Building> buildings = new ArrayList<Building>();
		for (int i = 0; i < 20; i++) {
			buildings.add(new Building(Idgenerator.getFreeBuildingID(), maps, 0, theBuildingPOI));
		}

		for (int i = 0; i < 1000; i++) {
			String id = Idgenerator.getFreePOIID();
			String name = String.valueOf(random.nextInt());
			String description = random.nextDouble() > .8 ? null : String
					.valueOf(random.nextInt());
			WorldPosition position = new WorldPosition(random.nextDouble(),
					random.nextDouble());
			Map map = maps.get(random.nextInt(maps.size()));
			Integer buildingID = (random.nextDouble() > .8) ? buildings.get(
					random.nextInt(buildings.size())).getID() : null;
			List<Category> curCategories = POITest
					.randomSubset(categories);

			POI poi = new POI(id, name, description, position, map, buildingID,
					curCategories);

			assertEquals(id, poi.getID());
			assertEquals(name, poi.getName());
			assertEquals(description, poi.getDescription());
			assertEquals(position, poi.getPosition());
			assertEquals(map, poi.getMap());
			assertEquals(
					(buildingID == null) ? null
							: Building.getBuildingByID(buildingID),
					poi.getBuilding());
			assertEquals(curCategories, poi.getCategories());
			
			try {
				poi.getCategories().add(null);
				assertFalse(poi.getCategories().equals(curCategories));
			} catch (UnsupportedOperationException e) {
				// This should be the normal case
			}
			curCategories.add(categories.get(0));
			assertFalse(curCategories.equals(poi.getCategories()));
		}
	}

	/**
	 * Returns a sublist containing random elements of a given parent list of
	 * elements.
	 * 
	 * @param <E>
	 *            an arbitrary type
	 * @param elements
	 *            a list
	 * @return a sublist containing some elements of the given list
	 */
	public static <E> List<E> randomSubset(List<E> elements) {
		// TODO: Move this method to a better position, something like
		// "TestUtil"
		int size = random.nextInt(elements.size());
		List<E> copy = new ArrayList<E>(elements);
		Collections.shuffle(copy);
		return elements.subList(0, size);
	}
}
