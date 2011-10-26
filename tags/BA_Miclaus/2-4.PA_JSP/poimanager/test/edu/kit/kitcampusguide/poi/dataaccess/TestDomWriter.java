package edu.kit.kitcampusguide.poi.dataaccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.kitcampusguide.poi.model.POI;

import static org.junit.Assert.*;

public class TestDomWriter {
	
	private String file = "./WebContent/data/POIListTest.xml";
	private DomWriter writer;
	private List<POI> poiList;

	@Before
	public void setUp() throws Exception {
		writer = new DomWriter();
		
		POI mensa = new POI();
		mensa.setId(2323);
		mensa.setName("Mensa am Adenaurerring");
		mensa.setCategory("mensa");
		mensa.setLatitude(49.011924);
		mensa.setLongitude(8.41678);
		
		POI chemieBib = new POI();
		chemieBib.setId(12314);
		chemieBib.setCategory("bibliothek");
		chemieBib.setName("Chemie Bibliothek");
		chemieBib.setLatitude(49.011280);
		chemieBib.setLongitude(8.413655);
		
		POI infoBib = new POI();
		infoBib.setId(1235);
		infoBib.setCategory("bibliothek");
		infoBib.setName("Informatik Bibliothek");
		infoBib.setLatitude(49.013886);
		infoBib.setLongitude(8.419786);
		
		poiList = new ArrayList<POI>();
		poiList.add(mensa);
		poiList.add(chemieBib);
		poiList.add(infoBib);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() {
		assertTrue(writer.write(poiList, file));
	}

}
