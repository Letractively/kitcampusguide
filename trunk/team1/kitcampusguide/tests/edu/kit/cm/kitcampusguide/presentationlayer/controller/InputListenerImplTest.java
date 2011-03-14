/**
 * 
 */
package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.PreCalculatedRoutingTest;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategy;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategyImpl;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBTestFramework;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManagerTest;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;
import edu.kit.cm.kitcampusguide.testframework.TestInitializer;

/**
 * @author Frederik
 *
 */
public class InputListenerImplTest {

	private static InputListenerImpl listener;
	private static MapModel mapModel;
	private static InputModel inputModel = new InputModel();
	private static DefaultModelValues defaultModelValues;
	private static TranslationModelImpl translationModel;
	private static CategoryModel categoryModel;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (Idgenerator.requestCategoryID(1)) {
			new Category(1, "test1");
		}
		if (Idgenerator.requestCategoryID(2)) {
			new Category(2, "test2");
		}
		if (Idgenerator.requestCategoryID(3)) {
			new Category(3, "test3");
		}
		if (Idgenerator.requestCategoryID(4)) {
			new Category(4, "test4");
		}
		if (Idgenerator.requestCategoryID(5)) {
			new Category(5, "test5");
		}
		if (Idgenerator.requestCategoryID(6)) {
			new Category(6, "test6");
		}
		BasicConfigurator.configure();
		POIDBTestFramework.constructPOIDB();
		PreCalculatedRoutingTest.setUpBeforeClass();
		defaultModelValues = new DefaultModelValues();
		DefaultModelValues.initialize(new FileInputStream(new File("WebContent/resources/config/DefaultValues.xml")));
		LanguageManagerTest.setUpBeforeClass();
		
	}
	
	@Before
	public void setUp() {
		translationModel = new TranslationModelImpl();
		listener = new InputListenerImpl();
		mapModel = new MapModel();
		categoryModel = new CategoryModel();
		listener.setMapModel(mapModel);
		categoryModel.setCategories(defaultModelValues.getDefaultCategories());
		categoryModel.setCurrentCategories(defaultModelValues.getDefaultCurrentCategories());
		listener.setCategoryModel(categoryModel);
		listener.setDefaultModelValueClass(new DefaultModelValues());
		listener.setInputModel(inputModel);
		listener.setTranslationModel(translationModel);
		mapModel.setMap(Map.getMapByID(1));
	}

	
	private String toCoordinates(MapPosition pos) {
		return pos.getLatitude() + ", " + pos.getLongitude();
	}
	
	private String toCoordinates(WorldPosition pos) {
		return pos.getLatitude() + ", " + pos.getLongitude();
	}
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(java.lang.String, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test
	public void testSearchTriggeredCoordinatesRouteFrom() {
		MapPosition pos = new MapPosition(49.012743, 8.415631, Map.getMapByID(1));
		listener.searchTriggered(pos.getLatitude() + ", " + pos.getLongitude(), InputField.ROUTE_FROM);
		assertEquals(pos, mapModel.getMarkerFrom());
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(java.lang.String, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test
	public void testSearchTriggeredCoordinatesRouteTo() {
		//TODO DefaultModelValue-Error, therefore only compares latitude and longitude
		MapPosition pos = new MapPosition(49.012743, 8.415631, Map.getMapByID(1));
		listener.searchTriggered(toCoordinates(pos), InputField.ROUTE_TO);
		assertEquals(pos, mapModel.getMarkerTo());
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(java.lang.String, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test
	public void testSearchTriggeredSearchSuccessful() {
		POI testPOI = POISourceImpl.getInstance().getPOIByID("1");
		testPOI.getName();
		listener.searchTriggered(testPOI.getName(), InputField.ROUTE_FROM);
		assertEquals(testPOI.getID(), mapModel.getHighlightedPOI().getID());
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(java.lang.String, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test
	public void testSearchTriggeredSearchUnsuccessful() {
		listener.searchTriggered("noSuchPOIName", InputField.ROUTE_FROM);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(java.lang.String, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testSearchTriggeredNull() {
		listener.searchTriggered(null, InputField.ROUTE_FROM);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test
	public void testSearchTriggeredPOI() {
		POI testPOI = POISourceImpl.getInstance().getPOIByID("1");
		listener.searchTriggered(testPOI);
		assertEquals(testPOI, mapModel.getHighlightedPOI());
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#searchTriggered(edu.kit.cm.kitcampusguide.standardtypes.POI)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testSearchTriggeredPOINull() {
		POI testPOI = null;
		listener.searchTriggered(testPOI);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#routeTriggered(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRouteTriggeredStringString() {
		POI testPOI1 = POISourceImpl.getInstance().getPOIByID("1");
		MapPosition pos1 = new MapPosition(testPOI1.getPosition().getLatitude(), testPOI1.getPosition().getLongitude(), Map.getMapByID(1));
		POI testPOI2 = POISourceImpl.getInstance().getPOIByID("2");
		MapPosition pos2 = new MapPosition(testPOI2.getPosition().getLatitude(), testPOI2.getPosition().getLongitude(), Map.getMapByID(1));
		RoutingStrategyImpl.getInstance().calculateRoute(pos1, pos2);
		inputModel.setRouteFromField(toCoordinates(pos1));
		inputModel.setRouteToField(toCoordinates(pos2));
		listener.routeTriggered(toCoordinates(pos1), toCoordinates(pos2));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#routeTriggered(java.lang.String, edu.kit.cm.kitcampusguide.standardtypes.MapPosition)}.
	 */
	@Test
	public void testRouteTriggeredStringMapPosition() {
		POI testPOI1 = POISourceImpl.getInstance().getPOIByID("1");
		MapPosition pos1 = new MapPosition(testPOI1.getPosition().getLatitude(), testPOI1.getPosition().getLongitude(), Map.getMapByID(1));
		POI testPOI2 = POISourceImpl.getInstance().getPOIByID("2");
		MapPosition pos2 = new MapPosition(testPOI2.getPosition().getLatitude(), testPOI2.getPosition().getLongitude(), Map.getMapByID(1));
		RoutingStrategyImpl.getInstance().calculateRoute(pos1, pos2);
		listener.routeTriggered(toCoordinates(pos1), pos2);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#routeTriggered(edu.kit.cm.kitcampusguide.standardtypes.MapPosition, java.lang.String)}.
	 */
	@Test
	public void testRouteTriggeredMapPositionString() {
		POI testPOI1 = POISourceImpl.getInstance().getPOIByID("1");
		MapPosition pos1 = new MapPosition(testPOI1.getPosition().getLatitude(), testPOI1.getPosition().getLongitude(), Map.getMapByID(1));
		POI testPOI2 = POISourceImpl.getInstance().getPOIByID("2");
		MapPosition pos2 = new MapPosition(testPOI2.getPosition().getLatitude(), testPOI2.getPosition().getLongitude(), Map.getMapByID(1));
		RoutingStrategyImpl.getInstance().calculateRoute(pos1, pos2);
		listener.routeTriggered(pos1, toCoordinates(pos2));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#routeTriggered(edu.kit.cm.kitcampusguide.standardtypes.MapPosition, edu.kit.cm.kitcampusguide.standardtypes.MapPosition)}.
	 */
	@Test
	public void testRouteTriggeredMapPositionMapPosition() {
		POI testPOI1 = POISourceImpl.getInstance().getPOIByID("1");
		MapPosition pos1 = new MapPosition(testPOI1.getPosition().getLatitude(), testPOI1.getPosition().getLongitude(), Map.getMapByID(1));
		POI testPOI2 = POISourceImpl.getInstance().getPOIByID("2");
		MapPosition pos2 = new MapPosition(testPOI2.getPosition().getLatitude(), testPOI2.getPosition().getLongitude(), Map.getMapByID(1));
		RoutingStrategyImpl.getInstance().calculateRoute(pos1, pos2);
		listener.routeTriggered(pos1, pos2);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#choiceProposalTriggered(java.util.List, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test
	public void testChoiceProposalTriggeredRoutFrom() {
		List<POI> proposalList = new ArrayList<POI>();
		proposalList.add(POISourceImpl.getInstance().getPOIByID("1"));
		proposalList.add(POISourceImpl.getInstance().getPOIByID("2"));
		listener.choiceProposalTriggered(proposalList, InputField.ROUTE_FROM);
		assertEquals(proposalList, inputModel.getRouteFromProposalList());
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#choiceProposalTriggered(java.util.List, edu.kit.cm.kitcampusguide.presentationlayer.controller.InputField)}.
	 */
	@Test
	public void testChoiceProposalTriggeredRoutTo() {
		List<POI> proposalList = new ArrayList<POI>();
		proposalList.add(POISourceImpl.getInstance().getPOIByID("1"));
		proposalList.add(POISourceImpl.getInstance().getPOIByID("2"));
		listener.choiceProposalTriggered(proposalList, InputField.ROUTE_TO);
		assertEquals(proposalList, inputModel.getRouteToProposalList());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#languageChangeTriggered(java.lang.String)}.
	 */
	@Test
	public void testLanguageChangeTriggered() {
		listener.languageChangeTriggered("testLanguage0");
		assertEquals("testLanguage0", translationModel.getCurrentLanguage());
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#languageChangeTriggered(java.lang.String)}.
	 */
	@Test
	public void testLanguageChangeTriggeredFails() {
		listener.languageChangeTriggered("noSuchLanguage");
		assertEquals("defaultLanguage", translationModel.getCurrentLanguage());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#changeToMapViewTriggered()}.
	 */
	@Test
	public void testChangeToMapViewTriggered() {
		listener.changeToMapViewTriggered();
		assertEquals(defaultModelValues.getDefaultMap(), mapModel.getMap());
		assertEquals(defaultModelValues.getDefaultBuilding(), mapModel.getBuilding());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#changeFloorTriggered(edu.kit.cm.kitcampusguide.standardtypes.Map)}.
	 */
	@Test
	public void testChangeFloorTriggered() {
		listener.changeFloorTriggered(Map.getMapByID(2));
		assertEquals(Map.getMapByID(2), mapModel.getMap());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.controller.InputListenerImpl#changeCategoryFilterTriggered(java.util.Set)}.
	 */
	@Test
	public void testChangeCategoryFilterTriggered() {
		System.out.println("Test ChangeCategoryFilterTriggered");
		Set<Category> enabledCategories = new HashSet<Category>();
		enabledCategories.add(Category.getCategoryByID(1));
		listener.changeCategoryFilterTriggered(enabledCategories);
		System.out.println(categoryModel.getCategories().size());
		for (Category c : categoryModel.getCurrentCategories()) {
			System.out.println(c.getID() + " " + c.getName());
			assertTrue(enabledCategories.contains(c));
		}
		assertEquals(enabledCategories.size(), categoryModel.getCurrentCategories().size());
	}
}
