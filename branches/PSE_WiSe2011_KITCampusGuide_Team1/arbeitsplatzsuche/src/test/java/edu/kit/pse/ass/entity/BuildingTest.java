package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BuildingTest {

	private Building building;
	private static String TEST_NUMBER = "20.24";

	@Before
	public void setUp() throws Exception {
		building = new Building();
	}

	@Test
	public void testSetNumber() {
		building.setNumber(TEST_NUMBER);
		assertEquals(TEST_NUMBER, building.getNumber());
	}

}
