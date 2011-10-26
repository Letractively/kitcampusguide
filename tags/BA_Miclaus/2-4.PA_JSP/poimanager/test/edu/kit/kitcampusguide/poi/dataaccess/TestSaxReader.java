package edu.kit.kitcampusguide.poi.dataaccess;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.kitcampusguide.poi.model.POI;

public class TestSaxReader {
	
	private static final double DELTA = 0.0001;

	private String file = "./WebContent/data/POIList.xml";
	
	private SaxReader reader;

	@Before
	public void setUp() throws Exception {
		reader = new SaxReader();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadAllPOIs() {
		List<POI> poiList = reader.readAllPOIs(file);
		
		assertNotNull(poiList);
		assertFalse(poiList.isEmpty());
		assertEquals(3, poiList.size());
		POI p = poiList.get(0);
		
		assertNotNull(p);
		assertEquals(2323,p.getId());
		assertEquals("Mensa am Adenaurerring", p.getName());
		assertEquals("mensa", p.getCategory());
		assertEquals(49.011924, p.getLatitude(), DELTA);
		assertEquals(8.41678, p.getLongitude(), DELTA);
	}

}
