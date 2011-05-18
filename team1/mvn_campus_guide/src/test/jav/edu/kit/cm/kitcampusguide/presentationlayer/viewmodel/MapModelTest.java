package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel.MapProperty;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

/**
 * Tests methods implemented by {@link MapModel}.
 * @author Cornelius
 *
 */

public class MapModelTest {

	/**
	 * Contains some testing maps.
	 */
	private static List<Map> testMaps;
	/**
	 * Constant indicating the number of testing maps used.
	 */
	private static final int NUM_MAPS = 30;
	/**
	 * Contains some testing POIs.
	 */
	private static List<POI> testPOIs;

	/**
	 * Constant indicating the number of testing POIs used.
	 */
	private static final int NUM_POIS = 50;	
	/**
	 * Contains some testing POI collections
	 */
	private static List<Collection<POI>> testPOICollections;
	/**
	 * Constant indicating the number of testing POI Collections used.
	 */
	private static final int NUM_COLLECTIONS = 20;
	/**
	 * Contains some testing categories.
	 */
	private static Collection<Category> testCategories;
	/**
	 * Contains some testing buildings.
	 */
	private static List<Building> testBuildings;
	/**
	 * Constant indicating the number of testing buildings used.
	 */
	private static final int NUM_BUILDINGS = 10;
	/**
	 * Contains some testing routes.
	 */
	private static List<Route> testRoutes;
	/**
	 * Constant indicating the number of testing routes used.
	 */
	private static final int NUM_ROUTES = 10;
	/**
	 * Instance of the MapModel for testing.
	 */
	private static MapModel testModel;
	
	/**
	 * Creates all necessary test data
	 * @param map
	 */
	@BeforeClass
	public static void createData() {
		testModel = new MapModel();
		testMaps = new LinkedList<Map>();
		for (int i = 0; i < NUM_MAPS; i++) {
			int generatedId = Idgenerator.getFreeMapID();
			testMaps.add(new Map(generatedId, "Test Map" + generatedId, new MapSection(new WorldPosition(0,0), new WorldPosition(0,0)),"testURL" + generatedId, generatedId - 1, generatedId + 1));
		}
		testCategories = new HashSet<Category>();
		for (int i = 0; i < 10; i++) {
			testCategories.add(new Category(Idgenerator.getFreeCategoryID(), "Test Kategorie" + i));
		}
		
		testPOIs = new LinkedList<POI>();
		for (int i = 0; i < NUM_POIS; i++) {
			String generatedId = Idgenerator.getFreePOIID();
			testPOIs.add(new POI(generatedId, "TestPOI" + generatedId, "TestDescription" + generatedId, new WorldPosition(0,0), testMaps.get(i % 30), null, testCategories));
		}
		testPOICollections = new LinkedList<Collection<POI>>();
		for (int i = 0; i < NUM_COLLECTIONS; i++) {
			Random randGen = new Random();
			int randNum = randGen.nextInt(NUM_POIS);
			HashSet<POI> newPOICollection = new HashSet<POI>();
			for (int j = 0; j < randNum; j++) {
				newPOICollection.add(testPOIs.get(randGen.nextInt(NUM_POIS)));
			}
			testPOICollections.add(newPOICollection);
		}
		testBuildings = new LinkedList<Building>();
		for (int i = 0; i < NUM_BUILDINGS; i++) {
			testBuildings.add(new Building(Idgenerator.getFreeBuildingID(), testMaps, 0, testPOIs.get(i % NUM_POIS)));
		}
		testRoutes = new LinkedList<Route>();
		for (int i = 0; i < NUM_ROUTES; i++) {
			List<MapPosition> waypoints = new LinkedList<MapPosition>();
			for (int j = 0; j < 100; j++) {
				waypoints.add(new MapPosition(0,0, testMaps.get(j % NUM_MAPS)));
			}
			Route newRoute = new Route(waypoints);
			testRoutes.add(newRoute);
		}
	}
	
	/**
	 * Tests if NullPointerException is thrown when setting map attribute to null.
	 */
	@Test(expected = NullPointerException.class)
	public void testSetMapNull() {
		testModel.setMap(null);
	}
	
	/**
	 * Tests if a sequence of get/set operations on the map attribute is processed
	 * correctly.
	 */
	@Test
	public void testSetMap(){
		Random sequenceGen = new Random();
		for (int i = 0; i < 20; i++) {
			int randomId = sequenceGen.nextInt(NUM_MAPS);
			testModel.setMap(testMaps.get(randomId));
			assertEquals("Sequence of get/set Operations on map attribute failed", testMaps.get(randomId), testModel.getMap());
		}
	}
	
	/**
	 * Tests if NullPointerException is thrown when setting pois attribute to null.
	 */
	@Test(expected = NullPointerException.class)
	public void testSetPoisNull () {
		testModel.setPOIs(null);
	}
	
	/**
	 * Tests if NullPointerException is thrown when setting mapLocator attribute to null.
	 */
	@Test(expected = NullPointerException.class)
	public void testSetMapLocatorNull() {
		testModel.setMapLocator(null);
	}
	
	
	/**
	 * Tests if IllegalArgumentException is thrown when passing invalid parameters to
	 * createBuildingPOIList in this case listpoi == null &&  list != null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBuildingPOIListInvalid0() {
		testModel.createBuildingPOIList(null, testPOIs);
	}
	
	/**
	 * Tests if IllegalArgumentException is thrown when passing invalid parameters to
	 * createBuildingPOIList in this case listpoi == null && list != null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBuildingPOIListInvalid1() {
		testModel.createBuildingPOIList(testPOIs.get(0), null);
	}
	
	/**
	 * Tests if changedProperties is set properly on a random sequence of 
	 * set operations on attributes.
	 */
	@Test
	public void testChangedProperties() {
		testModel.resetChangedProperties();
		for (int i = 1; i < 20; i++) {
			Random numGen = new Random();
			int a = 1;
			while (a < 6) {
				//generate Random sequence of length i
				int[] randomSeq = new int[i];
				//fill sequence
				for (int j = 0; j < i; j++) {
					randomSeq[j] = numGen.nextInt(10);
				}
				//construct correct changedProperties attribute for sequence
				//and process sequence
				Set<MapModel.MapProperty> testProperty = new HashSet<MapModel.MapProperty>();
				for (int j = 0; j < i; j++) {
					switch(randomSeq[j]) {
						case 0: Map newMap = testMaps.get(numGen.nextInt(NUM_MAPS));
								if (testModel.getMap().equals(newMap)) {
									testModel.setMap(newMap);
								} else {
									testModel.setMap(newMap);
									testProperty.add(MapProperty.map);
								}
								break;
						case 1: testModel.setPOIs(testPOICollections.get(numGen.nextInt(NUM_COLLECTIONS)));	
								testProperty.add(MapProperty.POIs);
								break;
						case 2: testModel.setBuilding(testBuildings.get(numGen.nextInt(NUM_BUILDINGS)));	
								testProperty.add(MapProperty.building);
								break;
						case 3: testModel.setMapLocator(new MapLocator(new MapSection(new WorldPosition(0,0), new WorldPosition(0,0))));	
								testProperty.add(MapProperty.mapLocator);
								break;
						case 4: testModel.setHighlightedPOI(testPOIs.get(numGen.nextInt(NUM_POIS)));	
								testProperty.add(MapProperty.highlightedPOI);
								break;
						case 5: testModel.setMarkerFrom(null);	
								testProperty.add(MapProperty.markerFrom);
								break;
						case 6: testModel.setMarkerTo(null);	
								testProperty.add(MapProperty.markerTo);
								break;
						case 7: testModel.setRoute(testRoutes.get(numGen.nextInt(NUM_ROUTES)));	
								testProperty.add(MapProperty.route);
								break;
						case 8: testModel.createBuildingPOIList(testPOIs.get(numGen.nextInt(NUM_POIS)), testPOIs);	
								testProperty.add(MapProperty.buildingPOIList);
								testProperty.add(MapProperty.buildingPOI);
								break;
						case 9: testModel.setCurrentFloorIndex(0);	
								testProperty.add(MapProperty.currentFloorIndex);
								break;
					}
				}
				assertEquals("Changed Properties were not set properly! Sequence length:" + i, testProperty, testModel.getChangedProperties());
				testModel.resetChangedProperties();
				a++;
			}	
		}
	}
}
