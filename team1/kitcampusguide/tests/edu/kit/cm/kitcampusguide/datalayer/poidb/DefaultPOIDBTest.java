/**
 * 
 */
package edu.kit.cm.kitcampusguide.datalayer.poidb;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POIQuery;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * @author Stefan
 * 
 */
public class DefaultPOIDBTest {

	public static final String dbURL = "jdbc:sqlite:defaultpoidbtestNew.db";

	/**
	 * Test method for
	 * {@link edu.kit.cm.kitcampusguide.datalayer.poidb.DefaultPOIDB#getPOIsByQuery(POIQuery)
	 * 
	 */

	@Test
	public void testGetPOIsByQuery() {

		createTestDB(dbURL, true);
		
		// Test category filter
		POIDB db = DefaultPOIDB.getInstance();
		POIQuery query = new POIQuery(null, null,
				Category.getCategoriesByIDs(Arrays.asList(1, 4)));
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertTrue(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));

		query = new POIQuery(null, null, Category.getCategoriesByIDs(Arrays
				.asList(3)));
		pois = db.getPOIsByQuery(query);
		assertFalse(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertTrue(containsName(pois, "hello4"));

		// Test id filter
		POI poi = db.getPOIByID("1");
		assertNotNull(poi);
		assertEquals("1", poi.getID());
		poi = db.getPOIByID("2");
		assertNotNull(poi);
		assertEquals("2", poi.getID());
		assertTrue(db.getPOIByID("lalablub") == null);
		assertNull(db.getPOIByID("123412"));

		// TODO: Test section filter

	}

	static void createTestDB(String dbURL, boolean create) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			fail("SQLLite Driver could not be established");
		}

		try {
			BasicConfigurator.configure();
			DefaultPOIDB.init(dbURL,
					new SimpleSearch(), true);
			DefaultPOIDB db = (DefaultPOIDB) DefaultPOIDB.getInstance();
			MapSection box = new MapSection(
					new WorldPosition(49.0199, 8.40232), new WorldPosition(
							49.0078, 8.42622));
			new Map(1, "campus", box,
					"./resources/tiles/campus/${z}/${x}/${y}.png", 14, 18);
			new Map(2, "asd", box, "blablub", 2, 3);
			new Map(3, "asd3", box, "blablub", 3, 4);
			Category cat1 = new Category(1, "streets");
			Category cat2 = new Category(2, "buildings");
			Category cat4 = new Category(4, "something");
			Category cat3 = new Category(3, "events");

			List<Category> categories = new ArrayList<Category>();

			categories.add(cat1);
			categories.add(cat2);
			categories.add(cat4);
			db.addPOI("hello1", "world", new MapPosition(49.012743, 8.415631,
					Map.getMapByID(1)), null, categories);
			db.addPOI("hello2", "world", new MapPosition(49.013897, 8.419729,
					Map.getMapByID(2)), null, categories);
			categories.clear();
			categories.add(cat2);
			db.addPOI("hello3", "world", new MapPosition(49.011011, 8.416382,
					Map.getMapByID(3)), null, categories);
			categories.add(cat3);
			db.addPOI("hello4", "world", new MapPosition(49.013728, 8.404537,
					Map.getMapByID(1)), null, categories);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	private boolean containsName(List<POI> pois, String name) {
		for (POI poi : pois) {
			if (poi.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
