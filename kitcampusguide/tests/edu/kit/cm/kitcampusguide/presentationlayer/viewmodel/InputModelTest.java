package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

/**
 * Tests all methods implemented by {@link InputModel}.
 * @author Cornelius
 *
 */
public class InputModelTest {
	
	/**
	 * Contains an instance of the model being tested
	 */
	private static InputModel testModel;
	/**
	 * Contains some testing maps.
	 */
	private static List<Map> testMaps;
	/**
	 * Constant indicating the number of testing maps used.
	 */
	private static final int NUM_MAPS = 30;
	/**
	 * Contains some Testing POIs.
	 */
	private static List<POI> testPOIs;
	/**
	 * Constant indicating the number of testing pois used. 
	 */
	private static final int NUM_POIS = 50;
	/**
	 * Contains some testing categories.
	 */
	private static Collection<Category> testCategories;
	
	/**
	 * Creates the necessary test data
	 */
	@BeforeClass
	public static void createData() {
		testModel = new InputModel();
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

	}
	
	/**
	 * Tests if the routeFromField attribute is set properly.
	 */
	@Test
	public void testSetRouteFromField() {
		testModel.setRouteFromField(null);
		assertEquals("RouteFromField is not set properly!", testModel.getRouteFromField(), null);
		testModel.setRouteFromField("TestRoute1");
		assertEquals("RouteFromField is not set properly!", testModel.getRouteFromField(), "TestRoute1");
		testModel.setRouteFromField(null);
		assertEquals("RouteFromField is not set properly!", testModel.getRouteFromField(), "TestRoute1");
		testModel.setRouteFromField("TestRoute2");
		assertEquals("RouteFromField is not set properly!", testModel.getRouteFromField(), "TestRoute2");
	}
	
	/**
	 * Tests if the routeToField attribute is set properly.
	 */
	@Test
	public void testSetRouteToField() {
		testModel.setRouteToField(null);
		assertEquals("RouteToField is not set properly!", testModel.getRouteToField(), null);
		testModel.setRouteToField("TestRoute1");
		assertEquals("RouteToField is not set properly!", testModel.getRouteToField(), "TestRoute1");
		testModel.setRouteToField(null);
		assertEquals("RouteToField is not set properly!", testModel.getRouteToField(), "TestRoute1");
		testModel.setRouteToField("TestRoute2");
		assertEquals("RouteToField is not set properly!", testModel.getRouteToField(), "TestRoute2");
	}
	
	/**
	 * Tests if the routeFromProposalList attribute is set properly.
	 */
	@Test
	public void testSetRouteFromProposalList() {
		testModel.setRouteFromProposalList(null);
		assertEquals("RouteFromProposalList is not set properly!", testModel.getRouteFromProposalList(), null);
		testModel.setRouteFromProposalList(testPOIs);
		assertEquals("RouteFromProposalList ist not set properly!", testModel.getRouteFromProposalList(), testPOIs);
		testModel.setRouteFromProposalList(null);
		assertEquals("RouteFromProposalList is not set properly!", testModel.getRouteFromProposalList(), null);
	}
	
	/**
	 * Tests if the routeToProposalList attribute is set properly.
	 */
	@Test
	public void testSetRouteToProposalList() {
		testModel.setRouteToProposalList(null);
		assertEquals("RouteToProposalList is not set properly!", testModel.getRouteToProposalList(), null);
		testModel.setRouteToProposalList(testPOIs);
		assertEquals("RouteToProposalList ist not set properly!", testModel.getRouteToProposalList(), testPOIs);
		testModel.setRouteToProposalList(null);
		assertEquals("RouteToProposalList is not set properly!", testModel.getRouteToProposalList(), null);
	}
	
	/**
	 * Tests if the routeFromSearchFailed attribute is set properly.
	 */
	@Test
	public void testSetRouteFromSearchFailed() {
		assertEquals("routeFromSearchFailed was not initialized properly!", testModel.isRouteFromSearchFailed(), false);
		testModel.setRouteFromSearchFailed(true);
		assertEquals("routeFromSearchFailed was not set properly!", testModel.isRouteFromSearchFailed(), true);
		testModel.setRouteFromSearchFailed(false);
		assertEquals("routeFromSearchFailed was not set properly!", testModel.isRouteFromSearchFailed(), false);
	}
	
	/**
	 * Tests if the routeToSearchFailed attribute is set properly.
	 */
	@Test
	public void testSetRouteToSearchFailed() {
		assertEquals("routeFromSearchFailed was not initialized properly!", testModel.isRouteToSearchFailed(), false);
		testModel.setRouteToSearchFailed(true);
		assertEquals("routeToSearchFailed was not set properly!", testModel.isRouteToSearchFailed(), true);
		testModel.setRouteToSearchFailed(false);
		assertEquals("routeToSearchFailed was not set properly!", testModel.isRouteToSearchFailed(), false);
	}
	
	/**
	 * Tests if the routeCalculationFailed attribute is set properly.
	 */
	@Test
	public void testSetRouteCalculationFailed() {
		assertEquals("routeCalculationFailed was not initialized properly!", testModel.isRouteCalculationFailed(), false);
		testModel.setRouteCalculationFailed(true);
		assertEquals("routeToSearchFailed was not set properly!", testModel.isRouteCalculationFailed(), true);
		testModel.setRouteCalculationFailed(false);
		assertEquals("routeToSearchFailed was not set properly!", testModel.isRouteCalculationFailed(), false);
	}
	
	/**
	 * Tests if the setExportLink attribute is set properly.
	 */
	@Test
	public void testSetExportLink() {
		assertEquals("exportLink attribute was not initialized correctly", testModel.getExportLink(), null);
		testModel.setExportLink("TestLink1");
		assertEquals("exportLink attribute was not set correctly", testModel.getExportLink(), "TestLink1");
		testModel.setExportLink("TestLink2");
		assertEquals("exportLink attribute was not set correctly", testModel.getExportLink(), "TestLink2");
		testModel.setExportLink(null);
		assertEquals("exportLink attribute was not reset correctly", testModel.getExportLink(), null);
	}
	
	/**
	 * Tests if the embeddingCode attribute is set properly.
	 */
	@Test
	public void testSetEmbeddingCode() {
		assertEquals("embeddingCode attribute was not initialized correctly", testModel.getEmbeddingCode(), null);
		testModel.setEmbeddingCode("TestLink1");
		assertEquals("embeddingCode attribute was not set correctly", testModel.getEmbeddingCode(), "TestLink1");
		testModel.setEmbeddingCode("TestLink2");
		assertEquals("embeddingCode attribute was not set correctly", testModel.getEmbeddingCode(), "TestLink2");
		testModel.setEmbeddingCode(null);
		assertEquals("embeddingCode attribute was not reset correctly", testModel.getEmbeddingCode(), null);
	}
}
