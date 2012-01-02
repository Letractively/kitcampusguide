package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

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
			building.addContainedFacility(r);
		}

		// Building should have 5 rooms
		assertEquals(5, building.getContainedFacilities().size());

		// Are all Rooms in Building?
		for (Room r : roomsToAdd) {
			assertTrue(building.getContainedFacilities().contains(r));
			// check parent
			assertEquals(building, r.getParentFacility());
		}

	}

	@Test
	public void removeContainedFacility() {
		Room r = new Room();
		building.addContainedFacility(r);
		// We added a room so size should be 1
		assertEquals(1, building.getContainedFacilities().size());
		building.removeContainedFacility(r);
		// We removed the room so size should be 0
		assertEquals(0, building.getContainedFacilities().size());
	}

	@Test
	public void testAddProperties() {
		Collection<Property> props = new ArrayList<Property>();
		props.add(new Property("WLAN"));
		props.add(new Property("LAN"));
		props.add(new Property("Steckdosen"));

		assertEquals(0, room.getProperties().size());

		for (Property prop : props) {
			room.addProperty(prop);
		}
		assertEquals(props.size(), room.getProperties().size());

		for (Property prop : props) {
			assertTrue(room.hasProperty(prop));
		}
	}

	@Test
	public void testHasProperty() {
		room.addProperty(new Property("WLAN"));
		assertTrue(room.hasProperty(new Property("WLAN")));
	}

	@Test
	public void removeProperty() {
		room.addProperty(new Property("WLAN"));
		room.removeProperty(new Property("WLAN"));
		assertEquals(0, room.getProperties().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNonRoomToBuilding() {
		building.addContainedFacility(workplace);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNonWorkplaceToRoom() {
		room.addContainedFacility(building);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddSomethingToWorkplace() {
		workplace.addContainedFacility(room);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullName() {
		room.setName(null);
	}

}
