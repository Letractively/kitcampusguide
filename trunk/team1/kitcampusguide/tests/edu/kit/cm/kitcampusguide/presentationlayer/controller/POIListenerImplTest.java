package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBTestFramework;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;

/**
 * Test if {@link POIListenerImpl} works properly.
 * 
 * @author Isa
 *
 */
public class POIListenerImplTest {
	
	private static POIListenerImpl poiListener;
	private static MapModel mapModel;
	private static CategoryModel categoryModel;
	private static int testBuildingID = 1;
	private static Building testBuilding1;
	private static POI testBuildingPOI;
	private static List<POI> testBuildingPOIList;
	private static Building testBuilding2;
	private static List<POI> testBuildingPOIList2;
	private static String testClickPOIID = "3";
	private static POI testClickPOI;
	private static Map testFloor;
	private static Map testMap;
	private static POI testPOI;
	private static MapLocator testMapLocator;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		POIDBTestFramework.constructPOIDB();
		testBuilding1 = Building.getBuildingByID(testBuildingID);
		testBuildingPOI = testBuilding1.getBuildingPOI();
		testBuildingPOIList = new ArrayList<POI>(POISourceImpl.getInstance().getPOIsByBuilding(testBuilding1, null));
		testBuilding2 = new Building(Idgenerator.getFreeBuildingID(), 
				Arrays.asList(Map.getMapByID(1), Map.getMapByID(2)), 
				1, POISourceImpl.getInstance().getPOIByID("2"));
		testBuildingPOIList2 = new ArrayList<POI>();
		testBuildingPOIList2.add(POISourceImpl.getInstance().getPOIByID("1"));
		testClickPOI = (POISourceImpl.getInstance().getPOIByID(testClickPOIID));
		testFloor = testClickPOI.getMap();
		testMap = Map.getMapByID(1);
		testPOI = POISourceImpl.getInstance().getPOIByID("2");
		testMapLocator = new MapLocator(new WorldPosition(0.0, 0.0));
		
		poiListener = new POIListenerImpl();
		mapModel = new MapModel();		
		categoryModel = new CategoryModel();
		poiListener.setMapModel(mapModel);
		poiListener.setCategoryModel(categoryModel);				
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mapModel.setMap(testMap);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl#changeToBuildingMap(int)}.
	 */
	@Test
	public void testChangeToBuildingMap() {				
		poiListener.changeToBuildingMap(testBuildingID);
		assertEquals(testBuilding1, mapModel.getBuilding());
		assertNull(mapModel.getHighlightedPOI());
		assertEquals(testBuilding1.getGroundFloor(), mapModel.getMap());		
	
		mapModel.setBuilding(testBuilding2);
		mapModel.setHighlightedPOI(testPOI);
		mapModel.setMap(testMap);
		poiListener.changeToBuildingMap(Integer.MIN_VALUE);
		assertEquals(testBuilding2, mapModel.getBuilding());
		assertEquals(testPOI, mapModel.getHighlightedPOI());
		assertEquals(testMap, mapModel.getMap());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl#showPOIsInBuilding(int)}.
	 */
	@Test
	public void testShowPOIsInBuilding() {		
		poiListener.showPOIsInBuilding(testBuildingID);		
		assertEquals(testBuildingPOI, mapModel.getBuildingPOI());
		//this test may fail once the POIs in the building are filtered, 
		//cf. the construction of "testBuildingPOIList"	
		List<POI> buildingPOIList = mapModel.getBuildingPOIList(); 		
		assertEquals(buildingPOIList.size(), testBuildingPOIList.size());
		for (int i = 0; i < buildingPOIList.size(); i++) {
			assertEquals(buildingPOIList.get(i).getID(), testBuildingPOIList.get(i).getID());
		}
		
		mapModel.createBuildingPOIList(testPOI, testBuildingPOIList2);
		poiListener.changeToBuildingMap(Integer.MIN_VALUE);
		assertEquals(testPOI, mapModel.getBuildingPOI());
		buildingPOIList = mapModel.getBuildingPOIList(); 		
		assertEquals(buildingPOIList.size(), testBuildingPOIList2.size());
		for (int i = 0; i < buildingPOIList.size(); i++) {
			assertEquals(buildingPOIList.get(i).getID(), testBuildingPOIList2.get(i).getID());
		}		
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl#listEntryClicked(java.lang.String)}.
	 */
	@Test
	public void testListEntryClicked() {
	
		poiListener.showPOIsInBuilding(testBuildingID);			
		poiListener.listEntryClicked(testClickPOIID);
		assertEquals(testBuilding1, mapModel.getBuilding());
		assertEquals(testFloor, mapModel.getMap());
		assertEquals(testClickPOI.getID(), mapModel.getHighlightedPOI().getID());
		assertEquals(testClickPOI.getPosition(), mapModel.getMapLocator().getCenter());

		mapModel.setMap(testMap);
		mapModel.setBuilding(null);
		mapModel.setHighlightedPOI(testPOI);
		mapModel.setMapLocator(testMapLocator);
		poiListener.showPOIsInBuilding(testBuildingID);	
		poiListener.listEntryClicked("adgnaeöhtuithew");
		assertNull(mapModel.getBuilding());
		assertEquals(testMap, mapModel.getMap());
		assertEquals(testPOI, mapModel.getHighlightedPOI());
		assertEquals(testMapLocator, mapModel.getMapLocator());		
		
		mapModel.setMap(testMap);
		mapModel.setBuilding(null);
		mapModel.setHighlightedPOI(testPOI);
		mapModel.setMapLocator(testMapLocator);
		poiListener.showPOIsInBuilding(testBuildingID);	
		poiListener.listEntryClicked("null");
		assertNull(mapModel.getBuilding());
		assertEquals(testMap, mapModel.getMap());
		assertEquals(testPOI, mapModel.getHighlightedPOI());
		assertEquals(testMapLocator, mapModel.getMapLocator());	
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl#setMapModel(edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel)}.
	 */
	@Ignore("trivial setter-method")
	@Test
	public void testSetMapModel() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl#setCategoryModel(edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel)}.
	 */
	@Ignore("trivial setter-method")
	@Test
	public void testSetCategoryModel() {
		fail("Not yet implemented"); 
	}

	
}
