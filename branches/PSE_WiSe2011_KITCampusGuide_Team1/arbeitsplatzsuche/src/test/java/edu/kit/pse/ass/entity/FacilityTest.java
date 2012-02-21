package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityTest.
 */
public class FacilityTest {

	/** The building. */
	private Building building;
	
	/** A second building. */
	private Building building2;

	/** The room. */
	private Room room;

	/** The workplace. */
	private Workplace workplace;

	/** The Constant NAME. */
	private static final String NAME = "Testname";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		building = new Building();
		room = new Room();
		workplace = new Workplace();
		building2 = new Building();
	}
	
	/**
	 * Test set ID.
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testSetId() {
		building2.setId(null);
	}
	
	/**
	 * Test set ID.
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testSetId2() {
		building2.setId("");
	}
	

	/**
	 * Test set name.
	 */
	@Test
	public void testSetName() {
		building.setName(NAME);
		assertEquals(NAME, building.getName());
		room.setName(NAME);
		assertEquals(NAME, room.getName());
		workplace.setName(NAME);
		assertEquals(NAME, workplace.getName());
	}
	
	/**
	 * Test set name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetName2() {
		building2.setName(null);
	}
	
	/**
	 * Test set name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetName3() {
		building2.setName("");
	}

	/**
	 * Test add contained facilities.
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void testAddContainedFacility() {
		// Collection of rooms to add to a bulding:
		Collection<Room> roomsToAdd = new ArrayList<Room>();
		building.setId("1asljcbaljf");
		// Add 5 rooms to the bulding:
		for (int i = 0; i < 5; i++) {
			Room r = new Room();
			r.setId("" + i);
			r.setName(NAME + i);
			roomsToAdd.add(r);
			building.addContainedFacility(r);
		}

		// Building should have 5 rooms
		assertEquals(5, building.getContainedFacilities().size());

		// Are all Rooms in Building?
		Collection<Facility> containedFacilities = building.getContainedFacilities();
		for (Facility r : roomsToAdd) {
			assertTrue(containedFacilities.contains(r));
			// check parent
			assertEquals(building, r.getParentFacility());
		}

	}
	
	/**
	 * Test add contained facility.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddContainedFacility2() {
		Facility tmp = new Facility();
		tmp.addContainedFacility(null);
	}
	
	/**
	 * Test add contained facility.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddContainedFacility3() {
		Room tmp = new Room();
		tmp.setParentFacility(building);
		building2.addContainedFacility(tmp);
	}
	
	/**
	 * Test add contained facilities.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddContainedFacilities() {
		building2.addContainedFacilities(null);
	}
	
	/**
	 * Test add contained facilities.
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testAddContainedFacilities2() {
		Room r = new Room();
		r.setId("blub");
		building2.addContainedFacility(r);
		Collection<Facility> rooms = new ArrayList<Facility>();
		Room r2 = new Room();
		r2.setId("blub2");
		rooms.add(r2);
		building2.addContainedFacilities(rooms);
	}
	
	/**
	 * Test add contained facilities.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddContainedFacilities3() {
		Collection<Facility> rooms = new ArrayList<Facility>();
		rooms.add(null);
		building2.addContainedFacilities(rooms);
	}
	
	/**
	 * Test add contained facilities.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddContainedFacilities4() {
		Collection<Facility> rooms = new ArrayList<Facility>();
		Room tmp = new Room();
		tmp.setParentFacility(building);
		rooms.add(tmp);
		building2.addContainedFacilities(rooms);
	}

	/**
	 * Removes the contained facility.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testRemoveContainedFacility() {
		Room r = new Room();
		r.setId("roomxyz1");
		building.addContainedFacility(r);
		// We added a room so size should be 1
		assertEquals("Facility is not added", 1, building
				.getContainedFacilities().size());
		building.removeContainedFacility(r);
		// We removed the room so size should be 0
		assertEquals("Facility is not removed", 0, building
				.getContainedFacilities().size());
	}
	
	/**
	 * Removes the contained facility.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveContainedFacility2() {
		Room r = new Room();
		r.removeContainedFacility(new Workplace());
	}
	
	/**
	 * Test set properties
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetProperties() {
		building2.setProperties(null);
	}
	
	/**
	 * Test set properties
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetProperties2() {
		Collection<Property> tmp = new ArrayList<Property>();
		tmp.add(new Property());
		building2.setProperties(tmp);
	}

	/**
	 * Test add properties.
	 */
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

	/**
	 * Test has property.
	 */
	@Test
	public void testHasProperty() {
		room.addProperty(new Property("WLAN"));
		assertTrue(room.hasProperty(new Property("WLAN")));
		// ''assertFalse'' == assertTrue(![something thats false])
		Room r = new Room();
		assertTrue(!(r.hasProperty(new Property("XYZ"))));
	}

	/**
	 * Removes the property.
	 */
	@Test
	public void removeProperty() {
		room.addProperty(new Property("WLAN"));
		room.removeProperty(new Property("WLAN"));
		assertEquals(0, room.getProperties().size());
	}

	/**
	 * Test add non room to building.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddNonRoomToBuilding() {
		building.addContainedFacility(workplace);
	}

	/**
	 * Test add non workplace to room.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddNonWorkplaceToRoom() {
		room.addContainedFacility(building);
	}

	/**
	 * Test add something to workplace.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddSomethingToWorkplace() {
		workplace.addContainedFacility(room);
	}

	/**
	 * Test set null name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNullName() {
		room.setName(null);
	}

}
