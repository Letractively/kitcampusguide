package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.kit.cm.kitcampusguide.testframework.Idgenerator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests all methods implemented by {@link POIQuery}
 * 
 * @author Stefan
 * 
 */
public class POIQueryTest {

	/**
	 * Contains maps used for testing.
	 */
	private static List<Map> maps;
	
	/**
	 * Contains categories used for testing.
	 */
	private static List<Category> categories;

	/**
	 * Contains the map lists which are used for testing.
	 */
	private static List<Map>[] testMaps;
	
	/**
	 * Contains the category lists which are used for testing.
	 */
	private static List<Category>[] testCategories;
	
	/**
	 * Contains the map section values which are used for testing.
	 */
	private static MapSection[] testMapSections;

	/**
	 * Contains the {@link POIQuery} objects which are created and used for
	 * testing. The indices refer the the test data,
	 * <code>testQueries[i][j][k]</code> refers to a <code>POIQuery</code>
	 * created with <br>
	 * <code>new POIQuery(testMapSections[k], testMaps[i], testCategories[j]);</code>
	 * .
	 */
	private static POIQuery[][][] testQueries;

	/**
	 * Creates test data. All combinations of the created data are stored in a
	 * {@link POIQuery} object and tested later. The maps and categories lists
	 * are tested with a <code>null</code> value, with an empty list and with a
	 * list containing a few elements.
	 */
	@BeforeClass
	public static void createTestData() {
		categories = new ArrayList<Category>();
		for (int i = 0; i < 20; i++) {
			categories.add(new Category(Idgenerator.getFreeCategoryID(), ""));
		}
		maps = new ArrayList<Map>();
		MapSection boundingBox = new MapSection(new WorldPosition(1, 2),
				new WorldPosition(2, 4));

		for (int i = 0; i < 10; i++) {
			maps.add(new Map(Idgenerator.getFreeMapID(), "", boundingBox, "", 0, 0));
		}
		testMaps = new List[] {
				null,
				new ArrayList<Map>(),
				new ArrayList(Arrays.asList(maps.get(0), maps.get(1),
						maps.get(2))) };

		testCategories = new List[] {
				null,
				new ArrayList<Category>(),
				new ArrayList(Arrays.asList(categories.get(0),
						categories.get(1), categories.get(2))) };

		testMapSections = new MapSection[] {
				null,
				new MapSection(new WorldPosition(0, 1), new WorldPosition(1, 2)) };

		testQueries = new POIQuery[testMaps.length][testCategories.length][testMapSections.length];

		for (int i = 0; i < testMaps.length; i++) {
			for (int j = 0; j < testCategories.length; j++) {
				for (int k = 0; k < testMapSections.length; k++) {
					testQueries[i][j][k] = new POIQuery(testMapSections[k],
							testMaps[i], testCategories[j]);
				}
			}
		}
	}

	/**
	 * Tests {@link POIQuery#getSection()}
	 */
	@Test
	public void testGetSection() {
		for (int i = 0; i < testMaps.length; i++) {
			for (int j = 0; j < testCategories.length; j++) {
				for (int k = 0; k < testMapSections.length; k++) {
					assertEquals(testMapSections[k],
							testQueries[i][j][k].getSection());
				}
			}
		}
	}

	/**
	 * Tests {@link POIQuery#getCategories()}.
	 */
	@Test
	public void testGetCategories() {
		for (int i = 0; i < testMaps.length; i++) {
			for (int j = 0; j < testCategories.length; j++) {
				for (int k = 0; k < testMapSections.length; k++) {
					List<Category> curCategories = testCategories[j];
					POIQuery query = testQueries[i][j][k];

					assertTrue(collectionEquals(curCategories,
							query.getCategories()));

					// check if the query is unmodifiable
					if (curCategories != null) {
						assertNotSame(query.getCategories(), curCategories);
						try {
							query.getCategories().add(categories.get(0));
							assertFalse(curCategories.equals(query
									.getCategories()));
						} catch (UnsupportedOperationException e) {
							// This is allowed
						}

						curCategories.add(categories.get(1));
						assertFalse(curCategories.equals(query.getCategories()));
						curCategories.remove(curCategories.get(curCategories
								.size() - 1));
					}
				}
			}
		}
	}

	/**
	 * Tests {@link POIQuery#getMaps()}.
	 */
	@Test
	public void testGetMaps() {
		for (int i = 0; i < testMaps.length; i++) {
			for (int j = 0; j < testCategories.length; j++) {
				for (int k = 0; k < testMapSections.length; k++) {
					List<Map> curMaps = testMaps[i];
					POIQuery query = testQueries[i][j][k];

					assertTrue(collectionEquals(curMaps, query.getMaps()));

					// check if the query is unmodifiable
					if (curMaps != null) {
						assertNotSame(query.getMaps(), curMaps);
						try {
							query.getMaps().add(maps.get(0));
							assertFalse(curMaps.equals(query.getMaps()));
						} catch (UnsupportedOperationException e) {
							// This is allowed
						}

						curMaps.add(maps.get(1));
						assertFalse(curMaps.equals(query.getCategories()));
						curMaps.remove(curMaps.size() - 1);
					}
				}
			}
		}
	}

	/**
	 * Returns <code>true</code> if two collections contain the same elements.
	 * The order in which the elements are retrieved does not matter, if an
	 * element is contained multiple times it must be contained multiple times
	 * in the other collection as well.
	 * 
	 * @param <E>
	 *            an arbitrary type.
	 * @param c1
	 *            a collection
	 * @param c2
	 *            a collection
	 * @return if the given collections contain the same elements
	 */
	public static <E> boolean collectionEquals(Collection<E> c1,
			Collection<E> c2) {
		if (c1 == null && c2 == null) {
			return true;
		}
		if (c1 == null || c2 == null) {
			return false;
		}
		List<E> cpy = new ArrayList<E>(c2);

		for (E e : c1) {
			if (!cpy.remove(e)) {
				return false;
			}
		}
		return c1.size() == c2.size();
	}
}
