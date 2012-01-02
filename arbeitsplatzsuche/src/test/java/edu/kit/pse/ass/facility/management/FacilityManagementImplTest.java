package edu.kit.pse.ass.facility.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;

/**
 * @author Lennart
 * 
 */
public class FacilityManagementImplTest {

	private static final String FACILITYID = "ID###1";
	private static final String SEARCH_TEXT = "Informatik";
	private static final int NEEDED_WORKPLACES = 3;
	private static final Collection<Property> FIND_PROPERTIES = Arrays.asList(
			new Property("WLAN"), new Property("Steckdose"));

	FacilityManagement fm = new FacilityManagementImpl();
	FacilityQuery FACILITY_QUERY = new RoomQuery(FIND_PROPERTIES, SEARCH_TEXT,
			NEEDED_WORKPLACES);

	@Test
	public void testGetFacility() {
		Facility result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.", fm.getFacility(null));
			result = fm.getFacility(FACILITYID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// a facility should be returned
		assertNotNull(result);
		// assert the correct facility was returned
		assertEquals(FACILITYID, result.getId());
		assertTrue(result.getProperties().contains(new Property("WLAN")));
		assertTrue(result.getContainedFacilities().size() == 3);
	}

	@Test
	public void testFindMatchingFacilities() {
		Collection<? extends Facility> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					fm.findMatchingFacilities(null));

			result = fm.findMatchingFacilities(FACILITY_QUERY);
		} catch (Exception e) {
			System.out.println("Error: " + e);
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

	@Test
	public void testGetAvailablePropertisOf() {
		Collection<Property> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					fm.getAvailablePropertiesOf(null));

			result = fm.getAvailablePropertiesOf(Room.class);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// something should be returned
		assertNotNull(result);
		// assert the correct properties are returned
		assertTrue(result.containsAll(FIND_PROPERTIES));
	}
}
