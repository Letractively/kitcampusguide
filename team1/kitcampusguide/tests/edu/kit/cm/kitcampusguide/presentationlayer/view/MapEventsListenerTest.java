package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import junit.framework.Test;
import junit.framework.TestSuite;
import static org.junit.Assert.*;

import org.apache.shale.test.base.AbstractJsfTestCase;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBTestFramework;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListener;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListener;
import edu.kit.cm.kitcampusguide.presentationlayer.controller.POIListenerImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

public class MapEventsListenerTest extends AbstractJsfTestCase {
	
	private MapEventsListener mapEventsListener;
	private MapModel mapModel;
	private InputModel inputModel;
	private CategoryModel categoryModel;
	private MapListener mapListener;
	private POIListener poiListener;
	private MapLocator testMapLocator;
	private MapPosition testMapPosition1;
	private MapPosition testMapPosition2;
	private String testPOIID1;
	private POI testPOI;
	private String testPOIID2;
	private POI testPOI2;
	private Map testFloor;
	private int testBuildingID;
	private Building testBuilding;
	private POI testBuildingPOI;
	private List<POI> testBuildingPOIList;
	private ValueChangeEvent[] testEvents;
	
	public MapEventsListenerTest(String name) {
		 super(name);
	}
	
	public static Test suite() {
	      return new TestSuite(MapEventsListenerTest.class);
	  }
	
	public void setUp() throws Exception {
	    super.setUp();
	    
	    POIDBTestFramework.constructPOIDB();
	    
	    mapModel = new MapModel();
	    inputModel = new InputModel();
	    categoryModel = new CategoryModel();
	    
	    MapListenerImpl mapListener2 = new MapListenerImpl();
	    mapListener2.setMapModel(mapModel);
	    mapListener2.setInputModel(inputModel);
	    POIListenerImpl poiListener2 = new POIListenerImpl();
	    poiListener2.setMapModel(mapModel);
	    poiListener2.setCategoryModel(categoryModel);
	    
	    File root = new File("WebContent");
	    servletContext.setDocumentRoot(root);
	    // simulate managed beans	    
	    facesContext.getExternalContext().getRequestMap().put("mapListener", mapListener2);	    
	    facesContext.getExternalContext().getRequestMap().put("poiListener", poiListener2);	    
	    
	    mapListener = mapListener2;
	    poiListener = poiListener2;	    
	    mapEventsListener = new MapEventsListener();
	    	    	    
	    testMapLocator = new MapLocator(new WorldPosition(0.0, 0.0));
		testMapPosition1 = new MapPosition(49.0179, 8.40232, Map.getMapByID(1));
		testMapPosition2 = new MapPosition(49.0, 8.5, Map.getMapByID(2));
		testPOIID1 = "1";
		testPOI = POISourceImpl.getInstance().getPOIByID("1");
		testPOIID2 = "3";
		testPOI2 = POISourceImpl.getInstance().getPOIByID("3");
		testFloor = testPOI2.getMap();
		testBuildingID = 1;  	    
		testBuilding = Building.getBuildingByID(1);
		testBuildingPOI = testBuilding.getBuildingPOI();
		testBuildingPOIList = new ArrayList<POI>(POISourceImpl.getInstance().getPOIsByBuilding(testBuilding, null));

	   	UIComponent testSource = new UICommand();
		
		String[] testTypes = {"mapLocatorChanged", "setRouteToByContextMenu", "setRouteFromByContextMenu",
			"clickOnPOI", "changeToBuildingMap", "showPOIsInBuilding", "listEntryClicked"};
		Object[] testData = {testMapLocator, testMapPosition1, testMapPosition2,
			testPOIID1, testBuildingID, testBuildingID, testPOIID2};
		testEvents = new ValueChangeEvent[9];
		for (int i = 0; i < 7; i++) {
			List<MapEvent> newValue = Arrays.asList(new MapEvent(testSource, testTypes[i], testData[i]));
			testEvents[i] = new ValueChangeEvent(testSource, null, newValue);
			testEvents[i].setPhaseId(PhaseId.INVOKE_APPLICATION);
		}	    
		testEvents[7] = new ValueChangeEvent(testSource, null, null);
		testEvents[7].setPhaseId(PhaseId.INVOKE_APPLICATION);
		testEvents[8] = new ValueChangeEvent(testSource, null, Arrays.asList(new MapEvent(testSource, testTypes[2], testData[2]),
				new MapEvent(testSource, testTypes[3], testData[3])));
		testEvents[8].setPhaseId(PhaseId.INVOKE_APPLICATION);
	}
	
	public void tearDown() throws Exception {
	    mapEventsListener = null;
		
	    super.tearDown();
	}
	
	public void testProcessValueChange() {
		//type "mapLocatorChanged"
		mapEventsListener.processValueChange(testEvents[0]); //nothing to test further
		
		//type "setRouteToByContextMenu"
		mapEventsListener.processValueChange(testEvents[1]);
		String expectedInputFieldContent = CoordinateManagerImpl.getInstance().coordinateToString(testMapPosition1);
		assertEquals(testMapPosition1, mapModel.getMarkerTo());
		assertEquals(expectedInputFieldContent, inputModel.getRouteToField());
		
		//type "setRouteFromByContextMenu"
		mapEventsListener.processValueChange(testEvents[2]);
		expectedInputFieldContent = CoordinateManagerImpl.getInstance().coordinateToString(testMapPosition2);
		assertEquals(testMapPosition2, mapModel.getMarkerFrom());
		assertEquals(expectedInputFieldContent, inputModel.getRouteFromField());
		
		//type "clickOnPOI"
		mapEventsListener.processValueChange(testEvents[3]);
		assertEquals(testPOIID1, mapModel.getHighlightedPOI().getID());
		assertEquals(testPOI.getPosition(), mapModel.getMapLocator().getCenter());
		
		mapModel.setMap(Map.getMapByID(1)); //make the mapModel consistent
		//type "changeToBuildingMap"
		mapEventsListener.processValueChange(testEvents[4]);
		assertEquals(testBuilding, mapModel.getBuilding());
		assertNull(mapModel.getHighlightedPOI());
		assertEquals(testBuilding.getGroundFloor(), mapModel.getMap());
		
		//type "showPOIsInBuilding"
		mapEventsListener.processValueChange(testEvents[5]);
		assertEquals(testBuildingPOI, mapModel.getBuildingPOI());
		//this test may fail once the POIs in the building are filtered, 
		//cf. the construction of "testBuildingPOIList"	
		List<POI> buildingPOIList = mapModel.getBuildingPOIList(); 		
		assertEquals(buildingPOIList.size(), testBuildingPOIList.size());
		for (int i = 0; i < buildingPOIList.size(); i++) {
			assertEquals(buildingPOIList.get(i).getID(), testBuildingPOIList.get(i).getID());
		}
		
		poiListener.showPOIsInBuilding(testBuildingID);	//make the mapModel consistent
		//type "listEntryClicked"
		mapEventsListener.processValueChange(testEvents[6]);			
		assertEquals(testBuilding, mapModel.getBuilding());
		assertEquals(testFloor, mapModel.getMap());
		assertEquals(testPOIID2, mapModel.getHighlightedPOI().getID());
		assertEquals(testPOI2.getPosition(), mapModel.getMapLocator().getCenter());	
		
		//newValue is null
		mapEventsListener.processValueChange(testEvents[7]); //nothing to test here
		
		//types "setRouteFromByContextMenu" and "clickOnPOI"
		mapEventsListener.processValueChange(testEvents[8]);
		expectedInputFieldContent = CoordinateManagerImpl.getInstance().coordinateToString(testMapPosition2);
		assertEquals(testMapPosition2, mapModel.getMarkerFrom());
		assertEquals(expectedInputFieldContent, inputModel.getRouteFromField());
		assertEquals(testPOIID1, mapModel.getHighlightedPOI().getID());
		assertEquals(testPOI.getPosition(), mapModel.getMapLocator().getCenter());
	}
		
}
	
