package edu.kit.pse.ass.facility.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;

/**
 * @author Lennart
 * 
 */
public class FacilityDAOImplTest {
	private static final String FACILITYID = "ID###1";
	FacilityDAO dao = new FacilityDAOImpl();
	Collection<Property> props = null;
	Collection<Facility> places = null;
	Collection<Facility> facs = null;

	@Before
	public void setUp() {
		Property prop1 = new Property("WLAN");
		Facility place1 = new Workplace();
		place1.setName("place1");
		Facility place2 = new Workplace();
		place2.setName("place2");
		Facility place3 = new Workplace();
		place3.setName("place3");
		Facility place4 = new Workplace();
		place4.setName("place4");
		Facility facil1 = new Room();
		Facility facil2 = new Room();
		Facility facil3 = new Room();
		Facility facil4 = new Room();
		facil1.setName("ID###1");
		facil2.setName("ID###2");
		facil3.setName("ID###3");
		facil4.setName("ID###4");

		dao.facilityFillWithDummies();
		props.add(prop1);
		places.add(place1);
		places.add(place2);
		places.add(place3);
		places.add(place4);
		facs.add(facil1);
		facs.add(facil2);
		facs.add(facil3);
		facs.add(facil4);
	}

	@Test
	public void testGetFacility() {
		Facility result = null;
		try {
			assertNull(dao.getFacility(null));
			result = dao.getFacility(FACILITYID);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull(result);
		assertEquals(FACILITYID, result.getId());
		assertTrue(result.getContainedFacilities().containsAll(places));
		assertTrue(result.getProperties().containsAll(props));
	}

	@Test
	public void testGetFacilities() {
		Collection<Facility> result = null;
		try {
			assertNull(dao.getFacilities(null));
			result = dao.getFacilities(props);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull(result);
		assertTrue(result.containsAll(facs));
		for (Facility fac : result) {
			assertTrue(fac.getProperties().containsAll(props));
		}

	}

	@Test
	public void testGetAvailablePropertiesOf() {
		Collection<Property> result = null;
		Collection<Property> expected = new ArrayList<Property>();
		expected.add(new Property("WLAN"));
		expected.add(new Property("Steckdose"));
		try {
			assertNull(dao.getAvailablePropertiesOf(null));
			result = dao.getAvailablePropertiesOf(Room.class);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		assertNotNull(result);
		assertTrue(result.containsAll(expected));
		assertEquals(result.size(), expected.size());
	}
}
