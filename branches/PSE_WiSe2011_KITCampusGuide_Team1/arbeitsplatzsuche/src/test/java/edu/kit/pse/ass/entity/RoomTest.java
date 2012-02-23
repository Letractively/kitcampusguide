package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class RoomTest.
 */
public class RoomTest {

	/** The room. */
	private Room room;

	/** The TES t_ number. */
	private static final String TEST_NUMBER = "-101";

	/** The TES t_ level. */
	private static final int TEST_LEVEL = 1;

	/** The TES t_ description. */
	private static final String TEST_DESCRIPTION = "Seminarraum";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		room = new Room();
	}

	/**
	 * Test set parent facility
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetParentFacility() {
		room.setParentFacility(null);
	}
	
	/**
	 * Test set parent facility
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetParentFacility2() {
		room.setParentFacility(new Workplace());
	}
	
	/**
	 * Test set parent facility
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetParentFacility3() {
		room.setParentFacility(new Room());
	}
	
	/**
	 * Test set description.
	 */
	@Test
	public void testSetDescription() {
		room.setDescription(TEST_DESCRIPTION);
		assertEquals(TEST_DESCRIPTION, room.getDescription());
	}
	
	/**
	 * Test set description.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetDescription2() {
		room.setDescription(null);
	}

	/**
	 * Test set level.
	 */
	@Test
	public void testSetLevel() {
		room.setLevel(TEST_LEVEL);
		assertEquals(TEST_LEVEL, room.getLevel());
	}

	/**
	 * Test set number.
	 */
	@Test
	public void testSetNumber() {
		room.setNumber(TEST_NUMBER);
		assertEquals(TEST_NUMBER, room.getNumber());
	}
	
	/**
	 * Test set number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNumber2() {
		room.setNumber(null);
	}

}
