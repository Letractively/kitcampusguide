package edu.kit.pse.ass.facility.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.testdata.TestData;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityDAOImplTest.
 * 
 * @author Lennart
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class FacilityDAOImplTest {

	/** The Constant FACILITYID. */
	private static final String FACILITYID = "ID###1";

	/** The dao. */
	@Autowired
	FacilityDAO dao;

	/** The props. */
	Collection<Property> props = new ArrayList<Property>();

	/** The places. */
	Collection<Facility> places = new ArrayList<Facility>();

	/** The facs. */
	Collection<Facility> facs = new ArrayList<Facility>();

	/** The test data. */
	@Autowired
	private TestData testData;

	/**
	 * Sets the up.
	 */
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		Property prop1 = new Property("WLAN");
		Facility place1 = new Workplace();
		place1.setId("place1");
		Facility place2 = new Workplace();
		place2.setId("place2");
		Facility place3 = new Workplace();
		place3.setId("place3");
		Facility place4 = new Workplace();
		place4.setId("place4");
		Facility facil1 = new Room();
		Facility facil2 = new Room();
		Facility facil3 = new Room();
		Facility facil4 = new Room();
		facil1.setId("ID###1");
		facil2.setId("ID###2");
		facil3.setId("ID###3");
		facil4.setId("ID###4");

		testData.facilityFillWithDummies();
		props.add(prop1);
		places.add(place1);
		places.add(place2);
		places.add(place3);
		facs.add(facil1);
		facs.add(facil2);
		facs.add(facil3);
		facs.add(facil4);
	}

	/**
	 * Test get facility.
	 */
	@Test
	public void testGetFacility() {
		Facility result = null;
		try {
			// throw error or return null if parameter is null
			assertNull(dao.getFacility(null));
		} catch (Exception e) {
		}

		result = dao.getFacility(FACILITYID);
		// a facility should be returned
		assertNotNull(result);
		assertTrue(result instanceof Room);
		// ensure the right facility is returned
		assertEquals(FACILITYID, result.getId());
		assertTrue(result.getContainedFacilities().containsAll(places));
		assertTrue(result.getProperties().containsAll(props));
	}

	/**
	 * Test get facilities.
	 */
	@Test
	public void testGetFacilities() {
		Collection<Facility> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull(dao.getFacilities(null));

			result = dao.getFacilities(props);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// facilities should be returned
		assertNotNull(result);
		// ensure the right facilities are returned
		assertTrue(result.containsAll(facs));
	}

	/**
	 * Test get available properties of.
	 */
	@Test
	public void testGetAvailablePropertiesOf() {
		Collection<Property> result = null;
		Collection<Property> expected = new ArrayList<Property>();
		expected.add(new Property("WLAN"));
		expected.add(new Property("Steckdose"));
		try {
			// throw error or return null if parameter is null
			assertNull(dao.getAvailablePropertiesOf(null));

			result = dao.getAvailablePropertiesOf(Room.class);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		// properties should be returned
		assertNotNull(result);
		// ensure all properties are returned
		assertTrue(result.containsAll(expected));
	}
}
