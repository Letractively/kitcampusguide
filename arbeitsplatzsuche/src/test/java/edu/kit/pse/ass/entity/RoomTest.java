package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoomTest {

	private Room room;
	private static String TEST_NUMBER = "-101";
	private static int TEST_LEVEL = 1;
	private static String TEST_DESCRIPTION = "Seminarraum";

	@Before
	public void setUp() throws Exception {
		room = new Room();
	}

	@Test
	public void testSetDescription() {
		room.setDescription(TEST_DESCRIPTION);
		assertEquals(TEST_DESCRIPTION, room.getDescription());
	}

	@Test
	public void testSetLevel() {
		room.setLevel(TEST_LEVEL);
		assertEquals(TEST_LEVEL, room.getLevel());
	}

	@Test
	public void testSetNumber() {
		room.setNumber(TEST_NUMBER);
		assertEquals(TEST_NUMBER, room.getNumber());
	}

}
