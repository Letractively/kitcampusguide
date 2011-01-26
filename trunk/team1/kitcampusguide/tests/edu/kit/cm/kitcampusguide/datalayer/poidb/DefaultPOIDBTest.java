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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POIQuery;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Tests the the methods {@link DefaultPOIDB#getPOIsByQuery(POIQuery)} and
 * {@link DefaultPOIDB#getPOIsByQuery(POIQuery)}
 * 
 * {@link DefaultPOIDB#getPOIsBySearch(String)} will not be tested within this
 * class as its results entirely depend on how the interface
 * 
 * {@link POIDBSearcher} is implemented.
 * 
 * @author Stefan, Fabian
 */
public class DefaultPOIDBTest {

	private static final String dbURL = "jdbc:sqlite:defaultpoidbtestNew.db";
	private static POIDB db;

	/**
	 * Sets up the database for testing, if not already set up.
	 * 
	 * This is actually not a desired behavior the database should always be set
	 * up by this class. So the tests are not affected by any other class
	 * because possible side effects when the database changes in an unexpected
	 * way.
	 */
	@BeforeClass
	public static void setUpTestDB() {
		//FIXME: Always set up a new database on running this testclass.
		try {
			db = DefaultPOIDB.getInstance();
		} catch (IllegalStateException e) {
			createTestDB(dbURL, true);
			db = DefaultPOIDB.getInstance();
		}
	}

	/**
	 * Cleans up the system afterwards.
	 */
	@AfterClass
	public static void cleanUp() {
		System.gc();
	}

	/**
	 * Test with <code>null</code> as all three query parameters, so all POI
	 * should match.
	 */
	@Test
	public void getPOIsByQueryNull() {
		POIQuery query = new POIQuery(null, null, null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertTrue(containsName(pois, "hello2"));
		assertTrue(containsName(pois, "hello3"));
		assertTrue(containsName(pois, "hello4"));
	}

	/**
	 * Test method for {@link DefaultPOIDB#getPOIsByQuery(POIQuery)} Tests with
	 * some categories selected, so that a partial match of POIs is expected.
	 * section and map on query parameters set to <code>null</code>
	 */

	@Test
	public void getPOIsByQuerySomeCategoriesSelected() {

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

	}

	/**
	 * Test with no categories selected, so no POI should match. This is
	 * performed with an empty List of Categories. section and map on query
	 * parameters set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryNoCategoriesSelected() {
		POIQuery query = new POIQuery(null, null,
				Category.getCategoriesByIDs(new ArrayList<Integer>()));
		List<POI> pois = db.getPOIsByQuery(query);
		assertFalse(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Test with all categories selected, so all POIs should match. section and
	 * map on query parameters set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryAllCategoriesSelected() {
		POIQuery query = new POIQuery(null, null, Category.getAllCategories());
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertTrue(containsName(pois, "hello2"));
		assertTrue(containsName(pois, "hello3"));
		assertTrue(containsName(pois, "hello4"));
	}

	/**
	 * Test performed with Map 1 selected. hello1 and hello4 are expected.
	 * section and categories are set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryMap1Selected() {
		POIQuery query = new POIQuery(null, Arrays.asList(Map.getMapByID(1)),
				null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertTrue(containsName(pois, "hello4"));
	}

	/**
	 * Test performed with no Map selected. So no POIs are expected. section and
	 * categories are set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryNoMapSelected() {
		POIQuery query = new POIQuery(null, new ArrayList<Map>(), null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(pois.equals(new ArrayList<POI>()));
	}

	/**
	 * Test performed with all Maps selected, so all POIs are expected. section
	 * and categories are set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryAllMapsSelected() {
		POIQuery query = new POIQuery(null, Arrays.asList(Map.getMapByID(1),
				Map.getMapByID(2), Map.getMapByID(3)), null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertTrue(containsName(pois, "hello2"));
		assertTrue(containsName(pois, "hello3"));
		assertTrue(containsName(pois, "hello4"));
	}

	/**
	 * Test performed with the section (49.012000|8.410000) and
	 * (49.014000|8.420000). hello1 and hello2 are expected section and
	 * categories are set to <code>null</code>
	 */
	@Test
	public void getPOIsByQuerySomeBySection() {
		WorldPosition pos1 = new WorldPosition(49.012, 8.41);
		WorldPosition pos2 = new WorldPosition(49.014, 8.42);
		POIQuery query = new POIQuery(new MapSection(pos1, pos2), null, null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertTrue(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Test performed with section (49.012743|8.415631) and (49.012743|8.415631)
	 * so the section is a point exactly hits hello1. section and categories are
	 * set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryExactHitBySection() {
		WorldPosition pos1 = new WorldPosition(49.012743, 8.415631);
		WorldPosition pos2 = pos1;
		POIQuery query = new POIQuery(new MapSection(pos1, pos2), null, null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Test performed with section (49.012743|8.415632) and (49.012743|8.415632)
	 * so the section misses hello1 by 1 on the last digit of the longitude.
	 * section and categories are set to <code>null</code>
	 */
	@Test
	public void getPOIsByQuerySlightlyOffBySection() {
		WorldPosition pos1 = new WorldPosition(49.012743, 8.415632);
		WorldPosition pos2 = pos1;
		POIQuery query = new POIQuery(new MapSection(pos1, pos2), null, null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertFalse(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Tests if Categories and MapSection correctly intersect. Section (49.011,
	 * 8.41) and (49.014, 8.418) which gets hallo1 and hallo3. Category with Id
	 * 1 which gets hello1 and hello 2. So only hello1 is expected. Map is set
	 * to <code>null</code>
	 */
	@Test
	public void getPOIsByQuerySectionAndCategory() {
		WorldPosition pos1 = new WorldPosition(49.011, 8.41);
		WorldPosition pos2 = new WorldPosition(49.014, 8.418);
		POIQuery query = new POIQuery(new MapSection(pos1, pos2), null,
				Category.getCategoriesByIDs(Arrays.asList(1)));
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Tests if Map and MapSection correctly intersect. Section (49.011, 8.41)
	 * and (49.014, 8.418) which gets hallo1 and hallo3. Map with Id 1 which
	 * gets hello1 and hello 4. So only hello1 is expected. Category is set to
	 * <code>null</code>
	 */
	@Test
	public void getPOIsByQuerySectionAndMap() {
		WorldPosition pos1 = new WorldPosition(49.011, 8.41);
		WorldPosition pos2 = new WorldPosition(49.014, 8.418);
		POIQuery query = new POIQuery(new MapSection(pos1, pos2),
				Arrays.asList(Map.getMapByID(1)), null);
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Tests if Map and Category correctly intersect. Category is set to ID 1
	 * which gets hello 1 and hello 2. Map is set to ID 1 which gets hello1 and
	 * hello4. So hello1 is expected. MapSection is set to <code>null</code>
	 */
	@Test
	public void getPOIsByQueryMapAndCategory() {
		WorldPosition pos1 = new WorldPosition(49.011, 8.41);
		WorldPosition pos2 = new WorldPosition(49.014, 8.418);
		POIQuery query = new POIQuery(new MapSection(pos1, pos2),
				Arrays.asList(Map.getMapByID(1)),
				Category.getCategoriesByIDs(Arrays.asList(1)));
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Tests if MapSection, Map and Category correctly intersect. Section is set
	 * to (49.011, 8.41) and (49.014, 8.418) which gets hallo1 and hallo3.
	 * Category is set to ID 1 which gets hello 1 and hello 2. Map is set to ID
	 * 1 which gets hello1 and hello4. So hello1 is expected.
	 */
	@Test
	public void getPOIsByQueryTestIntersectAll() {

		POIQuery query = new POIQuery(null, Arrays.asList(Map.getMapByID(1)),
				Category.getCategoriesByIDs(Arrays.asList(1)));
		List<POI> pois = db.getPOIsByQuery(query);
		assertTrue(containsName(pois, "hello1"));
		assertFalse(containsName(pois, "hello2"));
		assertFalse(containsName(pois, "hello3"));
		assertFalse(containsName(pois, "hello4"));
	}

	/**
	 * Test for {@link DefaultPOIDB#getPOIByID(String)} Expects to find the ID
	 * in the database.
	 */
	@Test
	public void getPOIsByIDMatch() {
		POI poi = db.getPOIByID("1");
		assertNotNull(poi);
		assertEquals("1", poi.getID());
		poi = db.getPOIByID("2");
		assertNotNull(poi);
		assertEquals("2", poi.getID());
	}

	/**
	 * Expects not to find the ID in the database.
	 */
	@Test
	public void getPOIsByIDNoMatch() {
		assertTrue(db.getPOIByID("lalablub") == null);
		assertNull(db.getPOIByID("123412"));
	}

	static void createTestDB(String dbURL, boolean create) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			fail("SQLLite Driver could not be established");
		}

		try {
			BasicConfigurator.configure();
			DefaultPOIDB.init(dbURL, new SimpleSearch(), true);
			DefaultPOIDB db = (DefaultPOIDB) DefaultPOIDB.getInstance();

			// Set up a map section
			MapSection box = new MapSection(
					new WorldPosition(49.0199, 8.40232), new WorldPosition(
							49.0078, 8.42622));

			// set up some maps
			new Map(1, "campus", box,
					"./resources/tiles/campus/${z}/${x}/${y}.png", 14, 18);
			new Map(2, "asd", box, "blablub", 2, 3);
			new Map(3, "asd3", box, "blablub", 3, 4);

			// Creat categories
			Category cat1 = new Category(1, "streets");
			Category cat2 = new Category(2, "buildings");
			Category cat4 = new Category(4, "something");
			Category cat3 = new Category(3, "events");

			List<Category> categories = new ArrayList<Category>();

			categories.add(cat1);
			categories.add(cat2);
			categories.add(cat4);

			/*
			 * Set up POI with: name: hello1 description: world position:
			 * 49.012743 & 8.415631 mapid: 1 building: null categories: 1, 2, 4
			 */
			db.addPOI("hello1", "world", new MapPosition(49.012743, 8.415631,
					Map.getMapByID(1)), null, categories);
			/*
			 * Set up POI with: name: hello2 description: world position:
			 * 49.013897 & 8.419729 mapid: 2 building: null categories: 1, 2, 4
			 */
			db.addPOI("hello2", "world", new MapPosition(49.013897, 8.419729,
					Map.getMapByID(2)), null, categories);

			categories.clear();
			categories.add(cat2);

			/*
			 * Set up POI with: name: hello2 description: world position:
			 * 49.011011 & 8.416382 mapid: 3 building: null categories: 2
			 */
			db.addPOI("hello3", "world", new MapPosition(49.011011, 8.416382,
					Map.getMapByID(3)), null, categories);

			categories.add(cat3);

			/*
			 * Set up POI with: name: hello1 description: world position:
			 * 49.013728 & 8.404537 mapid: 1 building: null categories: 2, 3
			 */
			db.addPOI("hello4", "world", new MapPosition(49.013728, 8.404537,
					Map.getMapByID(1)), null, categories);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Simple Test if a POI is contained in the the list.
	 * 
	 * @param pois
	 * @param name
	 * @return
	 */
	private boolean containsName(List<POI> pois, String name) {
		for (POI poi : pois) {
			if (poi.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
