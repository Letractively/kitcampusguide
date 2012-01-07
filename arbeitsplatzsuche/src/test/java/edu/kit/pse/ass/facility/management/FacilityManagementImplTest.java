package edu.kit.pse.ass.facility.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityManagementImplTest.
 *
 * @author Lennart
 */
public class FacilityManagementImplTest {

	/** The Constant FACILITYID. */
	private static final String FACILITYID = "ID###1";
	
	/** The Constant SEARCH_TEXT. */
	private static final String SEARCH_TEXT = "Informatik";
	
	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;
	
	/** The Constant FIND_PROPERTIES. */
	private static final Collection<Property> FIND_PROPERTIES = Arrays.asList(
			new Property("WLAN"), new Property("Steckdose"));

	/** The fm. */
	FacilityManagement fm = new FacilityManagementImpl();
	
	/** The FACILIT y_ query. */
	FacilityQuery FACILITY_QUERY = new RoomQuery(FIND_PROPERTIES, SEARCH_TEXT,
			NEEDED_WORKPLACES);

	/**
	 * Test get facility.
	 */
	@Test
	public void testGetFacility() {
		Facility result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", fm.getFacility(null));
			// TODO what's returned if nothing found?
			assertNull(fm.getFacility("ID9"));

			result = fm.getFacility(FACILITYID);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// a facility should be returned
		assertNotNull(result);
		// assert the correct facility was returned
		assertEquals(FACILITYID, result.getId());
		assertTrue(result.getProperties().contains(new Property("WLAN")));
		assertTrue(result.getContainedFacilities().size() == 3);
	}

	/**
	 * Test find matching facilities.
	 */
	@Test
	public void testFindMatchingFacilities() {
		Collection<? extends Facility> result = null;
		FacilityQuery testQuery = new RoomQuery(Arrays.asList(new Property(
				"Strom")), SEARCH_TEXT, NEEDED_WORKPLACES);
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					fm.findMatchingFacilities(null));
			// TODO what's returned if nothing found?
			assertNull(fm.findMatchingFacilities(testQuery));

			result = fm.findMatchingFacilities(FACILITY_QUERY);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// something should be returned
		assertNotNull(result);
		// assert all results are of the wanted type, have enough places and the
		// properties
		for (Facility fac : result) {
			assertEquals(fac.getClass(), Room.class);
			assertTrue(fac.getContainedFacilities().size() >= NEEDED_WORKPLACES);
			assertTrue(fac.getProperties().containsAll(FIND_PROPERTIES));
		}
	}

	/**
	 * Test get available propertis of.
	 */
	@Test
	public void testGetAvailablePropertisOf() {
		Collection<Property> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					fm.getAvailablePropertiesOf(null));

			result = fm.getAvailablePropertiesOf(Room.class);
			assertTrue(fm.getAvailablePropertiesOf(Building.class).contains(
					((List<Property>) FIND_PROPERTIES).get(0)));
			assertFalse(fm.getAvailablePropertiesOf(Building.class).contains(
					((List<Property>) FIND_PROPERTIES).get(1)));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// something should be returned
		assertNotNull(result);
		// assert the correct properties are returned
		assertTrue(result.containsAll(FIND_PROPERTIES));
	}
}
