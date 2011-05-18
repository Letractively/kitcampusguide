package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import static edu.kit.cm.kitcampusguide.testframework.Idgenerator.*;

/**
 * Tests {@link edu.kit.cm.kitcampusguide.standardtypes.Building}
 * @author Fabian
 *
 */
public class BuildingTest {

	private static Building building;
	private static int buildingID;
	private static POI poi;

	/**
	 * Sets up the building object used for testing.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(2, 2));
		List<Map> floors = Arrays.asList(	new Map(getFreeMapID(), "cellar", box, "null", 0 ,0),
											new Map(getFreeMapID(), "groundfloor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "first floor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "second floor", box, "null", 0 ,0));
		buildingID = getFreeBuildingID();
		poi = new POI(	getFreePOIID(), "buildingTestPOI", 
							"buildingTestPOI", new WorldPosition(0, 0), 
							new Map(getFreeMapID(), "worldmap", box, "null", 0 ,0), 
							buildingID, 
							Arrays.asList(new Category(getFreeCategoryID(), "buildingTestPOICategory")));
		building = new Building(buildingID, floors, 1, poi);
	}


	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#Building(int, java.util.List, int, edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBuildingIDTaken() {
		MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(2, 2));
		List<Map> floors = Arrays.asList(	new Map(getFreeMapID(), "cellar", box, "null", 0 ,0),
											new Map(getFreeMapID(), "groudfloor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "first floor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "second floor", box, "null", 0 ,0));
		int id = getFreeBuildingID();
		POI poi = new POI(	getFreePOIID(), "buildingTestPOI", 
							"buildingTestPOI", new WorldPosition(0, 0), 
							new Map(getFreeMapID(), "worldmap", box, "null", 0 ,0), 
							id, 
							Arrays.asList(new Category(getFreeCategoryID(), "buildingTestPOICategory")));
		new Building(buildingID, floors, 1, poi);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#Building(int, java.util.List, int, edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBuildingFloorIndexLesserThenNull() {
		MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(2, 2));
		List<Map> floors = Arrays.asList(	new Map(getFreeMapID(), "cellar", box, "null", 0 ,0),
											new Map(getFreeMapID(), "groudfloor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "first floor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "second floor", box, "null", 0 ,0));
		int id = getFreeBuildingID();
		POI poi = new POI(	getFreePOIID(), "buildingTestPOI", 
							"buildingTestPOI", new WorldPosition(0, 0), 
							new Map(getFreeMapID(), "worldmap", box, "null", 0 ,0), 
							id, 
							Arrays.asList(new Category(getFreeCategoryID(), "buildingTestPOICategory")));
		new Building(id, floors, -1, poi);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#Building(int, java.util.List, int, edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBuildingFloorIndexGreaterThenNumberOfFloors() {
		MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(2, 2));
		List<Map> floors = Arrays.asList(	new Map(getFreeMapID(), "cellar", box, "null", 0 ,0),
											new Map(getFreeMapID(), "groudfloor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "first floor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "second floor", box, "null", 0 ,0));
		int id = getFreeBuildingID();
		POI poi = new POI(	getFreePOIID(), "buildingTestPOI", 
							"buildingTestPOI", new WorldPosition(0, 0), 
							new Map(getFreeMapID(), "worldmap", box, "null", 0 ,0), 
							id, 
							Arrays.asList(new Category(getFreeCategoryID(), "buildingTestPOICategory")));
		new Building(id, floors, 6, poi);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#Building(int, java.util.List, int, edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testBuildingFloorsNull() {
		MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(2, 2));
		int id = getFreeBuildingID();
		POI poi = new POI(	getFreePOIID(), "buildingTestPOI", 
							"buildingTestPOI", new WorldPosition(0, 0), 
							new Map(getFreeMapID(), "worldmap", box, "null", 0 ,0), 
							id, 
							Arrays.asList(new Category(getFreeCategoryID(), "buildingTestPOICategory")));
		new Building(id, null, 1, poi);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#Building(int, java.util.List, int, edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testBuildingPOINull() {
		MapSection box = new MapSection(new WorldPosition(0, 0), new WorldPosition(2, 2));
		List<Map> floors = Arrays.asList(	new Map(getFreeMapID(), "cellar", box, "null", 0 ,0),
											new Map(getFreeMapID(), "groudfloor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "first floor", box, "null", 0 ,0),
											new Map(getFreeMapID(), "second floor", box, "null", 0 ,0));
		int id = getFreeBuildingID();
		new Building(id, floors, 1, null);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getID()}.
	 */
	@Test
	public void testGetID() {
		assertEquals(buildingID, building.getID());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getFloors()}.
	 */
	@Test
	public void testGetFloors() {
		List<Map> floors = building.getFloors();
		assertTrue(mapListContainsMapName("cellar", floors));
		assertTrue(mapListContainsMapName("groundfloor", floors));
		assertTrue(mapListContainsMapName("first floor", floors));
		assertTrue(mapListContainsMapName("second floor", floors));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getGroundFloorIndex()}.
	 */
	@Test
	public void testGetGroundFloorIndex() {
		assertEquals(1, building.getGroundFloorIndex());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getBuildingPOI()}.
	 */
	@Test
	public void testGetBuildingPOI() {
		assertSame(poi, building.getBuildingPOI());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getBuildingByID(int)}.
	 */
	@Test
	public void testGetBuildingByID() {
		assertSame(building, Building.getBuildingByID(buildingID));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getGroundFloor()}.
	 */
	@Test
	public void testGetGroundFloor() {
		assertEquals("groundfloor", building.getGroundFloor().getName());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Building#getAllBuildings()}.
	 */
	@Test
	public void testGetAllBuildings() {
		List<Building> buildings = Building.getAllBuildings();
		assertTrue(buildings.contains(building));
	}

	
	private static boolean mapListContainsMapName(String mapName, List<Map> maps) {
		for (Map m : maps) {
			if (m.getName().equals(mapName)) {
				return true;
			}
		}
		return false;
	}

}
