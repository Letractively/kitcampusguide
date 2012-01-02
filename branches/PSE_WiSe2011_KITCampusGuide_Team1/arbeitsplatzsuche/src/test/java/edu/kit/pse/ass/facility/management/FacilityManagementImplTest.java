package edu.kit.pse.ass.facility.management;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;

/**
 * @author Lennart
 * 
 */
public class FacilityManagementImplTest {

	private static final String FACILITYID = "#SOME_FACILITY_ID#";
	private static final String SEARCH_TEXT = "Informatik";
	private static final int NEEDED_WORKPLACES = 3;
	private static final Collection<Property> FIND_PROPERTY_NAMES = Arrays
			.asList(new Property("WLAN"), new Property("Steckdose"));

	FacilityManagement fm = new FacilityManagementImpl();
	FacilityQuery FACILITY_QUERY = new RoomQuery(FIND_PROPERTY_NAMES,
			SEARCH_TEXT, NEEDED_WORKPLACES);

	@Test
	public void getFacility() {
		fm.getFacility(FACILITYID);
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
