package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.*;

public class FacilityTest {
	private Building building;
	private Room room;
	private Workplace workplace;

	private static final String NAME = "Testname";

	@Before
	public void setUp() throws Exception {
		building = new Building();
		room = new Room();
		workplace = new Workplace();
	}

	@Test
	public void testSetName() {
		building.setName(NAME);
		assertEquals(NAME, building.getName());
		room.setName(NAME);
		assertEquals(NAME, room.getName());
		workplace.setName(NAME);
		assertEquals(NAME, workplace.getName());
	}

	@Test
	public void testAddContainedFacilities() {
		// Collection of rooms to add to a bulding:
		Collection<Room> roomsToAdd = new ArrayList<Room>();

		// Add 5 rooms to the bulding:
		for (int i = 0; i < 5; i++) {
			Room r = new Room();
			r.setName(NAME + i);
			roomsToAdd.add(r);
			building.addContainedFacilitiy(r);
		}

		// Building should have 5 rooms
		assertEquals(5, building.getContainedFacilities().size());

		// Are all Rooms in Building?
		for (Room r : roomsToAdd) {
			assertTrue(building.getContainedFacilities().contains(r));
			// check parent
			assertEquals(building, room.getParentFacility());
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNonRoomToBuilding() {
		building.addContainedFacilitiy(workplace);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNonWorkplaceToRoom() {
		room.addContainedFacilitiy(building);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddSomethingToWorkplace() {
		workplace.addContainedFacilitiy(room);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullName() {
		room.setName(null);
	}

}
