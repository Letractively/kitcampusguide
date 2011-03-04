package edu.kit.cm.kitcampusguide.datalayer.poidb;

import static junit.framework.Assert.*;


import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POIQuery;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Tests the the methods {@link DefaultPOIDB#getPOIsByQuery(POIQuery)} and
 * {@link DefaultPOIDB#getPOIsByQuery(POIQuery)}
 * 
 * {@link DefaultPOIDB#getPOIsBySearch(String)} will not be tested within this
 * class as the methods results entirely depend on how the interface
 * {@link POIDBSearcher} is implemented.
 * 
 * IMPORTANT NOTE: Relies on having the correct XML file 
 * defined by <code>_dbFile</code> in the directory stated by <code>_testdir</code>.
 * The content of this XML file is append in the regular comments at the end of this file.
 * 
 * @author Stefan, Fabian
 */
public class DefaultPOIDBTest {

	private static final String dbURL = "jdbc:mysql://localhost:3306/testdb?user=root&password=pwd";
	private static POIDB db;
	private static final String _testDir        = "tests/testDB";
    private static final String _dbFile         = "dbDefaultPOIDBTest.xml";
    private static final String _driverClass    = "com.mysql.jdbc.Driver";
    private static File file = new File(_testDir, _dbFile);

	/**
	 * Sets up the database for testing and necessary Objects referred by the database.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws DatabaseUnitException 
	 * @throws MalformedURLException 
	 * @throws SQLException 
	 */
	@BeforeClass
	public static void setUpTestDB() throws MalformedURLException, DatabaseUnitException, ClassNotFoundException, SQLException {

		BasicConfigurator.configure();
		IDatabaseConnection connection = getConnection();
		DefaultPOIDB.init(dbURL, new SimpleSearch(), true);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        builder.setDtdMetadata(true);
        IDataSet dataSet = builder.build(file);
        
        //Initialize Maps
        ITable poidbtable = dataSet.getTable("POIDB");
        MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(10, 10));
        for (int i = 0; i < poidbtable.getRowCount(); i++) {
        	String s = (String) poidbtable.getValue(i, "mapid");
        	Integer k =  Integer.parseInt(s);
        	if (Map.getMapByID(k) == null) {
        		new Map(k, "map" + k.toString(), box, "null", 1, 2);
        	}
        }
        
        //Initialize Categories
        ITable categoriesTable = dataSet.getTable("CATEGORY");
        for (int i = 0; i < categoriesTable.getRowCount(); i++) {
        	String s = (String) categoriesTable.getValue(i, "categoryid");
        	Integer k =  Integer.parseInt(s);
        	if (Category.getCategoriesByIDs(Arrays.asList(k)).isEmpty()) {
        		new Category(k, "category" + k.toString());
        	}
        }

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		db = DefaultPOIDB.getInstance();
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
		assertFalse("pois is empty", pois.isEmpty());
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

	/**
     * @return IDatabaseConnection
     * @throws ClassNotFoundException
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    public static IDatabaseConnection getConnection() throws  ClassNotFoundException, 
                                                                DatabaseUnitException, 
                                                                SQLException {
        // database connection
        Class driverClass = Class.forName(_driverClass);
        Connection jdbcConnection = DriverManager.getConnection(dbURL);
        return new DatabaseConnection(jdbcConnection);
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

/* The content of the XML file used to setup the database.
/*
<?xml version='1.0' encoding='UTF-8'?>
<dataset>
  <POIDB id="1" name="hello1" description="world" lon="8.415631" lat="49.012743" mapid="1" buildingid="0"/>
  <POIDB id="2" name="hello2" description="world" lon="8.419729" lat="49.013897" mapid="2" buildingid="0"/>
  <POIDB id="3" name="hello3" description="world" lon="8.416382" lat="49.011011" mapid="3" buildingid="0"/>
  <POIDB id="4" name="hello4" description="world" lon="8.404537" lat="49.013728" mapid="1" buildingid="0"/>
  <CATEGORY id="1" poiid="1" categoryid="1"/>
  <CATEGORY id="2" poiid="1" categoryid="2"/>
  <CATEGORY id="3" poiid="1" categoryid="4"/>
  <CATEGORY id="4" poiid="2" categoryid="1"/>
  <CATEGORY id="5" poiid="2" categoryid="2"/>
  <CATEGORY id="6" poiid="2" categoryid="4"/>
  <CATEGORY id="7" poiid="3" categoryid="2"/>
  <CATEGORY id="8" poiid="4" categoryid="2"/>
  <CATEGORY id="9" poiid="4" categoryid="3"/>
</dataset>
*/
}
