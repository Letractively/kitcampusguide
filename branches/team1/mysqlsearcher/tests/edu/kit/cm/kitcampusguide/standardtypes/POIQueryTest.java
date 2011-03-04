package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * Tests all methods implemented by {@link POIQuery}
 * @author Stefan
 *
 */
public class POIQueryTest {

	private static final Random rnd = new Random();
	
	/**
	 * Tests all methods by generating random test data.
	 */
	@Test
	public void testPOIQuery() {
		
		List<Map> maps = new ArrayList<Map>();
		MapSection boundingBox = new MapSection(new WorldPosition(1,2), new WorldPosition(2, 4));
		
		for (int i = 0; i < 10; i++) {
			maps.add(new Map(i + 23200, "", boundingBox, "", 0, 0));
		}
		
		List<Category> categories = new ArrayList<Category>();
		for (int i = 0; i < 20; i++) {
			categories.add(new Category(i + 150, ""));
		}
		
		for (int i = 0; i < 1000; i++) {
			MapSection curSection = rnd.nextBoolean() ? boundingBox : null;
			List<Map> curMaps = rnd.nextDouble() > .9 ? null
					: randomSubset(maps);
			List<Category> curCategories = rnd.nextDouble() > .9 ? null
					: randomSubset(categories);

			POIQuery query = new POIQuery(curSection, curMaps, curCategories);
			
			assertTrue((curCategories == null) ? query.getCategories() == null
					: new ArrayList(query.getCategories())
							.equals(curCategories));
			assertTrue((curMaps == null) ? query.getMaps() == null
					: new ArrayList(query.getMaps()).equals(curMaps));

			assertEquals(query.getSection(), curSection);
			
			// check if the query is unmodifiable
			if (curCategories != null) {
				assertNotSame(query.getCategories(), curCategories);
				try {
					query.getCategories().add(categories.get(0));
					assertFalse(curCategories.equals(query.getCategories()));
				} catch (UnsupportedOperationException e) {
					// This is allowed
				}
				
				curCategories.add(categories.get(0));
				assertFalse(curCategories.equals(query.getCategories()));
			}

			if (curMaps != null) {
				assertNotSame(query.getMaps(), curMaps);
				try {
					query.getMaps().add(maps.get(0));
					assertFalse(curMaps.equals(query.getMaps()));
				} catch (UnsupportedOperationException e) {
					// This is allowed
				}
				curMaps.add(maps.get(0));
				assertFalse(curMaps.equals(query.getMaps()));
			}
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
	private <E> List<E> randomSubset(List<E> elements) {
		int size = rnd.nextInt(elements.size());
		List<E> copy = new ArrayList<E>(elements);
		Collections.shuffle(copy);
		return elements.subList(0, size);
	}
}
