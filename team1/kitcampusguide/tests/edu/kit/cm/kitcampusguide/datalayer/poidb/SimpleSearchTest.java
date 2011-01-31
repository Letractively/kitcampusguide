package edu.kit.cm.kitcampusguide.datalayer.poidb;

import org.junit.*;
import static org.junit.Assert.*;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleSearchTest {

	public static final String dbURL = "jdbc:sqlite:simplesearchtest.db";
	private static POIDB db;
	
	@BeforeClass
	public static void getDB() {
		try {
			db = DefaultPOIDB.getInstance();
		} catch (IllegalStateException e) {
			DefaultPOIDBTest.createTestDB(dbURL, true);
			db = DefaultPOIDB.getInstance();
		}
	}
	
	@Ignore
	@Test
	public void testGetIDs() {
		
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
