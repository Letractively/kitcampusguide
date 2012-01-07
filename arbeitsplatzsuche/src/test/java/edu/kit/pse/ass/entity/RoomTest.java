package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class RoomTest.
 */
public class RoomTest {

	/** The room. */
	private Room room;
	
	/** The TES t_ number. */
	private static String TEST_NUMBER = "-101";
	
	/** The TES t_ level. */
	private static int TEST_LEVEL = 1;
	
	/** The TES t_ description. */
	private static String TEST_DESCRIPTION = "Seminarraum";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		room = new Room();
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

}
