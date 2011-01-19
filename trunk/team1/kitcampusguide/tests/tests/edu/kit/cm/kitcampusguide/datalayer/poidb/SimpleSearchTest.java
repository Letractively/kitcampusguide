package edu.kit.cm.kitcampusguide.datalayer.poidb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class SimpleSearchTest {

	public static final String dbURL = "jdbc:sqlite:simplesearchtest.db";

	@Test
	public void testGetIDs() {
		DefaultPOIDBTest.createTestDB(dbURL, true);
		SimpleSearch searcher = new SimpleSearch();
		
		testSearch(searcher);
	}

	private void testSearch(POIDBSearcher searcher) {
		List<Integer> result = searcher.getIDs("hello1", dbURL);
		assertTrue(result.contains(1));
		assertEquals(1, result.size());
		
		result = searcher.getIDs("hello4", dbURL);
		assertTrue(result.contains(4));
		assertTrue(result.contains(5));
		assertEquals(2, result.size());
	}

}
