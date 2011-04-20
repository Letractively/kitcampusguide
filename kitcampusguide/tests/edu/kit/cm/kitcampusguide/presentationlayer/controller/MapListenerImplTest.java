package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBTestFramework;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Test if {@link MapListenerImpl} works properly.
 * 
 * @author Isa
 *
 */
public class MapListenerImplTest {

	private MapListenerImpl mapListener;
	private MapModel mapModel;
	private InputModel inputModel;
	private String testPOIID;
	private POI testPOI;
	private POI testPOI2;
	private MapLocator testMapLocator;
	private MapPosition testMapPosition;
	
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
		testPOIID = "1";
		testPOI = POISourceImpl.getInstance().getPOIByID(testPOIID);
		testPOI2 = POISourceImpl.getInstance().getPOIByID("2");
		testMapLocator = new MapLocator(new WorldPosition(0.0, 0.0));
		testMapPosition = new MapPosition(0.0, 0.0, Map.getMapByID(1));
		
		mapListener = new MapListenerImpl();
		mapModel = new MapModel();
		inputModel = new InputModel();
		mapListener.setMapModel(mapModel);
		mapListener.setInputModel(inputModel);	
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#mapLocatorChanged(edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator)}.
	 */
	@Test
	public void testMapLocatorChanged() {
		mapListener.mapLocatorChanged(testMapLocator);
		//nothing to test here
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#clickOnPOI(java.lang.String)}.
	 */
	@Test
	public void testClickOnPOI() {
		mapListener.clickOnPOI(testPOIID);
		assertEquals(testPOI.getID(), mapModel.getHighlightedPOI().getID());
		assertEquals(testPOI.getPosition(), mapModel.getMapLocator().getCenter());
	}
	
	@Test
	public void testClickOnPOI_invalidID() {
		mapModel.setHighlightedPOI(testPOI2);
		mapModel.setMapLocator(testMapLocator);
		mapListener.clickOnPOI("ajkfhkhgleiu");
		assertEquals(testPOI2.getID(), mapModel.getHighlightedPOI().getID());		
		assertEquals(testMapLocator, mapModel.getMapLocator());
	}
	
	@Test
	public void testClickOnPOI_null() {
		mapModel.setHighlightedPOI(testPOI2);
		mapModel.setMapLocator(testMapLocator);
		mapListener.clickOnPOI(null);
		assertNull(mapModel.getHighlightedPOI());
		assertEquals(testMapLocator, mapModel.getMapLocator());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#setRouteFromByContextMenu(edu.kit.cm.kitcampusguide.standardtypes.MapPosition)}.
	 */
	@Test
	public void testSetRouteFromByContextMenu() {
		mapListener.setRouteFromByContextMenu(testMapPosition);
		String expectedInputFieldContent = CoordinateManagerImpl.getInstance().coordinateToString(testMapPosition);
		assertEquals(testMapPosition, mapModel.getMarkerFrom());
		assertEquals(expectedInputFieldContent, inputModel.getRouteFromField());					
	}
	
	@Test (expected=NullPointerException.class)
	public void testSetRouteFromByContextMenu_null() {
		mapListener.setRouteFromByContextMenu(null);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#setRouteToByContextMenu(edu.kit.cm.kitcampusguide.standardtypes.MapPosition)}.
	 */
	@Test
	public void testSetRouteToByContextMenu() {
		mapListener.setRouteToByContextMenu(testMapPosition);
		String expectedInputFieldContent = CoordinateManagerImpl.getInstance().coordinateToString(testMapPosition);
		assertEquals(testMapPosition, mapModel.getMarkerTo());
		assertEquals(expectedInputFieldContent, inputModel.getRouteToField());	
	}
	
	@Test (expected=NullPointerException.class)
	public void testSetRouteToByContextMenu_null() {
		mapListener.setRouteToByContextMenu(null);	
	}
}
