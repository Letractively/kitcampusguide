package edu.kit.pse.ass.facility.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
	private static final Collection<Property> FIND_PROPERTY_NAMES = Arrays
			.asList(new Property("WLAN"), new Property("Steckdose"));

	FacilityManagement fm = new FacilityManagementImpl();
	FacilityQuery FACILITY_QUERY = new RoomQuery(FIND_PROPERTY_NAMES,
			SEARCH_TEXT, NEEDED_WORKPLACES);

	@Test
	public void getFacility() {
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
		assertEquals(FACILITYID, result.getName());

	}

	@Test
	public void findMatchingFacilities() {
		fm.findMatchingFacilities(FACILITY_QUERY);
	}

	@Test
	public void getAvailablePropertiesOf() {
		fm.getAvailablePropertiesOf(Room.class);
	}
}
