package edu.kit.kitcampusguide.poi;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.kitcampusguide.poi.model.POI;

public class TestXmlPoiManager {

	private static final String query = "mensa";
	private static final String file = "./WebContent/data/POIList.xml";
	XmlPoiManager manager;
	
	@Before
	public void setUp() throws Exception {
		manager = new XmlPoiManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPoisByCategory() {
		List<POI> searchResult = manager.getPoisByCategory(query, file);
		assertNotNull(searchResult);
		assertFalse(searchResult.isEmpty());
		assertEquals(1, searchResult.size() );
	}

}
