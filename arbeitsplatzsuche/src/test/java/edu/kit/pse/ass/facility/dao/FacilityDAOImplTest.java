package edu.kit.pse.ass.facility.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;

public class FacilityDAOImplTest {
	private static final String FACILITYID = "#SOME_FACILITY_ID#";

	@Test
	public void testGetFacility() {
		FacilityDAO dao = new FacilityDAOImpl();
		dao.getFacility(FACILITYID);
	}

	@Test
	public void testGetFacilities() {
		FacilityDAO dao = new FacilityDAOImpl();
		Collection<Property> properties = new ArrayList<Property>();
		properties.add(new Property("WLAN"));
		dao.getFacilities(properties);
	}

	@Test
	public void testGetAvailablePropertiesOf() {
		FacilityDAO dao = new FacilityDAOImpl();
		dao.getAvailablePropertiesOf(Room.class);
	}

}
