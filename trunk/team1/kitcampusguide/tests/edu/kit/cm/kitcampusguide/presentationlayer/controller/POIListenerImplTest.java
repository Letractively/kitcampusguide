package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
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
	
	private POIListenerImpl poiListener;
	private MapModel mapModel;
	private CategoryModel categoryModel;
	private int testBuildingID;
	private Building testBuilding1;
	private POI testBuildingPOI;
	private List<POI> testBuildingPOIList;
	private Building testBuilding2;
	private List<POI> testBuildingPOIList2;
	private String testClickPOIID;
	private POI testClickPOI;
	private Map testFloor;
	private Map testMap;
	private POI testPOI;
	private MapLocator testMapLocator;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		POIDBTestFramework.constructPOIDB();						
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		testBuildingID = 1;
		testBuilding1 = Building.getBuildingByID(testBuildingID);
		testBuildingPOI = testBuilding1.getBuildingPOI();
		testBuildingPOIList = new ArrayList<POI>(POISourceImpl.getInstance().getPOIsByBuilding(testBuilding1, null));
		testBuilding2 = new Building(Idgenerator.getFreeBuildingID(), 
				Arrays.asList(Map.getMapByID(1), Map.getMapByID(2)), 
				1, POISourceImpl.getInstance().getPOIByID("2"));
		testBuildingPOIList2 = new ArrayList<POI>();
		testBuildingPOIList2.add(POISourceImpl.getInstance().getPOIByID("1"));
		testClickPOIID = "3";
		testClickPOI = (POISourceImpl.getInstance().getPOIByID(testClickPOIID));
		testFloor = testClickPOI.getMap();
		testMap = Map.getMapByID(1);
		testPOI = POISourceImpl.getInstance().getPOIByID("4");
		testMapLocator = new MapLocator(new WorldPosition(0.0, 0.0));
		
		poiListener = new POIListenerImpl();
		mapModel = new MapModel();	
		mapModel.setMap(testMap);
		categoryModel = new CategoryModel();
		poiListener.setMapModel(mapModel);
		poiListener.setCategoryModel(categoryModel);
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
	}
	
	@Test
	public void testChangeToBuildingMap_invalidID() {	
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
		assertEquals(testBuildingPOIList.size(), buildingPOIList.size());
		for (int i = 0; i < buildingPOIList.size(); i++) {
			assertEquals(testBuildingPOIList.get(i).getID(), buildingPOIList.get(i).getID());
		}
	}
	
	@Test
	public void testShowPOIsInBuilding_invalidID() {		
		mapModel.createBuildingPOIList(testPOI, testBuildingPOIList2);
		poiListener.changeToBuildingMap(Integer.MIN_VALUE);
		assertEquals(testPOI, mapModel.getBuildingPOI());
		List<POI> buildingPOIList = mapModel.getBuildingPOIList(); 	
		assertEquals(testBuildingPOIList2.size(), buildingPOIList.size());
		for (int i = 0; i < buildingPOIList.size(); i++) {
			assertEquals(testBuildingPOIList2.get(i).getID(), buildingPOIList.get(i).getID());
		}		
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl#listEntryClicked(java.lang.String)}.
	 */
	@Test
	public void testListEntryClicked() {	
		poiListener.showPOIsInBuilding(testBuildingID);	//ensure valid state		
		poiListener.listEntryClicked(testClickPOIID);
		assertEquals(testBuilding1, mapModel.getBuilding());
		assertEquals(testFloor, mapModel.getMap());
		assertEquals(testClickPOI.getID(), mapModel.getHighlightedPOI().getID());
		assertEquals(testClickPOI.getPosition(), mapModel.getMapLocator().getCenter());
	}
	
	@Test
	public void testEntryListClicked_invalidID() {
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
	}
	
	@Test
	public void testEntryListClicked_null() {
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
}
