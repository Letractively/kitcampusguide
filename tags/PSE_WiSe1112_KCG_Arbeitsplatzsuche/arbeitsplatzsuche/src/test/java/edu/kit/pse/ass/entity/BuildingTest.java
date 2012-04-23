package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class BuildingTest.
 */
public class BuildingTest {

	/** The building. */
	private Building building;

	/** The TES t_ number. */
	private static final String TEST_NUMBER = "20.24";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
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
	
	/**
	 * Test set number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNumber2() {
		building.setNumber("");
	}
	
	/**
	 * Test set number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNumber3() {
		building.setNumber(null);
	}
	
	/**
	 * Test set parent facility.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetParentFacility() {
		building.setParentFacility(new Room());
	}
	
	/**
	 * Test set parent facility
	 */
	@Test
	public void testSetParentFacility2() {
		building.setParentFacility(null);
	}

}
