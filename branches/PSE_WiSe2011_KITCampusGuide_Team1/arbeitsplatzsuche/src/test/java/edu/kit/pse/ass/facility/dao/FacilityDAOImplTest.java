package edu.kit.pse.ass.facility.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

	/** The workplaces of facility 1. */
	Collection<Facility> places1 = new ArrayList<Facility>();

	/** The workplaces of facility 2. */
	Collection<Facility> places2 = new ArrayList<Facility>();
	/** The workplaces of facility 3. */
	Collection<Facility> places3 = new ArrayList<Facility>();
	/** The workplaces of facility 4. */
	Collection<Facility> places4 = new ArrayList<Facility>();
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
		Facility facil1 = new Room();
		Facility facil2 = new Room();
		Facility facil3 = new Room();
		Facility facil4 = new Room();
		facil1.setId("ID###1");
		facil2.setId("ID###2");
		facil3.setId("ID###3");
		facil4.setId("ID###4");

		Facility place1 = new Workplace();
		Facility place2 = new Workplace();
		Facility place3 = new Workplace();
		Facility place4 = new Workplace();
		place1.setId("place1");
		place2.setId("place2");
		place3.setId("place3");
		place4.setId("place4");

		Facility place2_1 = new Workplace();
		Facility place3_1 = new Workplace();
		Facility place3_2 = new Workplace();
		Facility place3_3 = new Workplace();
		Facility place4_1 = new Workplace();
		Facility place4_2 = new Workplace();
		Facility place4_3 = new Workplace();
		Facility place4_4 = new Workplace();
		place2_1.setId("place2_1");
		place3_1.setId("place3_1");
		place3_2.setId("place3_2");
		place3_3.setId("place3_3");
		place4_1.setId("place4_1");
		place4_2.setId("place4_2");
		place4_3.setId("place4_3");
		place4_4.setId("place4_4");

		testData.facilityFillWithDummies();
		props.add(prop1);
		places1.add(place1);
		places1.add(place2);
		places1.add(place3);

		places2.add(place2_1);

		places3.add(place3_1);
		places3.add(place3_2);
		places3.add(place3_3);

		places4.add(place4_1);
		places4.add(place4_2);
		places4.add(place4_3);
		places4.add(place4_4);

		facs.add(facil1);
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
			result = dao.getFacility(FACILITYID);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// a facility should be returned
		assertNotNull(result);
		assertTrue(result instanceof Room);
		// ensure the right facility is returned
		assertEquals(FACILITYID, result.getId());
		assertTrue(result.getContainedFacilities().containsAll(places1));
		assertFalse(result.getContainedFacilities().contains(places4));
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
			System.out.println("Error: " + e.getMessage());
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
			System.out.println("Error: " + e.getMessage());
		}
		// properties should be returned
		assertNotNull(result);
		// ensure all properties are returned
		assertTrue(result.containsAll(expected));
	}
}
