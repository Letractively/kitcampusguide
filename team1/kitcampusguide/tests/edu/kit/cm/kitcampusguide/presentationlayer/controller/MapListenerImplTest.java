package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBTestFramework;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

/**
 * Test if {@link MapListenerImpl} works properly.
 * 
 * @author Isa
 *
 */
public class MapListenerImplTest {

	private static MapListenerImpl mapListener;
	private static MapModel mapModel;
	private static InputModel inputModel;
	private static String testPOIID = "1";
	private static POI testPOI;
	private static POI testPOI2;
	private static MapLocator testMapLocator;
	private static MapPosition testMapPosition;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		POIDBTestFramework.constructPOIDB();		
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
	@Ignore("in this implementation this method does nothing") 
	@Test
	public void testMapLocatorChanged() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#clickOnPOI(java.lang.String)}.
	 */
	@Test
	public void testClickOnPOI() {
		mapListener.clickOnPOI(testPOIID);
		assertEquals(testPOI.getID(), mapModel.getHighlightedPOI().getID());
		assertEquals(testPOI.getPosition(), mapModel.getMapLocator().getCenter());
		
		mapModel.setHighlightedPOI(testPOI2);
		mapModel.setMapLocator(testMapLocator);
		mapListener.clickOnPOI("ajkfhkhgleiu");
		assertEquals(testPOI2.getID(), mapModel.getHighlightedPOI().getID());		
		assertEquals(testMapLocator, mapModel.getMapLocator());
		
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
	public void testSetRouteFromByContextMenu_WithNullParameter() {
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
	public void testSetRouteToByContextMenu_WithNullParameter() {
		mapListener.setRouteToByContextMenu(null);	
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#setMapModel(edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel)}.
	 */
	@Ignore("trivial setter-method")
	@Test
	public void testSetMapModel() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.MapListenerImpl#setInputModel(edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel)}.
	 */
	@Ignore("trivial setter-method")
	@Test
	public void testSetInputModel() {
		fail("Not yet implemented");
	}

}
