package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PropertyTest {

	Property prop;
	private static String TEST_NAME = "WLAN";

	@Before
	public void setUp() throws Exception {
		prop = new Property(TEST_NAME);
	}

	@Test
	public void testGetName() {
		assertEquals(TEST_NAME, prop.getName());
	}

}
