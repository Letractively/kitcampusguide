package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildingTest.
 */
public class BuildingTest {

	/** The building. */
	private Building building;
	
	/** The TES t_ number. */
	private static String TEST_NUMBER = "20.24";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		building = new Building();
	}

	/**
	 * Test set number.
	 */
	@Test
	public void testSetNumber() {
		building.setNumber(TEST_NUMBER);
		assertEquals(TEST_NUMBER, building.getNumber());
	}

}
