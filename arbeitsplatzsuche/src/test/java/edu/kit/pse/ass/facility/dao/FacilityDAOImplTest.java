package edu.kit.pse.ass.facility.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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

	/** The persisted facility. */
	private Facility persistedFacility;

	/** The dao. */
	@Autowired
	FacilityDAO facilityDAO;

	/** The property wlan. */
	Property propWlan;

	/** The facscilites with Wlan */
	Collection<Facility> facsWithWlan = new ArrayList<Facility>();

	/** The test data. */
	@Autowired
	private TestData testData;

	private TestData.DummyFacilities dummyFacilities;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		dummyFacilities = testData.facilityFillWithDummies();
		propWlan = new Property("WLAN");

		for (Facility f : dummyFacilities.rooms) {
			if (f.hasProperty(propWlan)) {
				facsWithWlan.add(f);
			}
		}
		persistedFacility = dummyFacilities.rooms.get(0);
	}

	/**
	 * Test get facility.
	 */
	@Test
	public void testGetFacility() {
		Facility result = null;
		try {
			// throw error or return null if parameter is null
			assertNull(facilityDAO.getFacility(null));
		} catch (IllegalArgumentException e) {
		}

		result = facilityDAO.getFacility(persistedFacility.getId());
		// a facility should be returned
		assertNotNull("no facility found", result);
		// ensure the right facility is returned
		assertTrue("facility is not a Room", result instanceof Room);
		assertEquals("ID of facility is wrong", persistedFacility.getId(), result.getId());

		assertEquals("containedFacilities are different", persistedFacility.getContainedFacilities().size(),
				result.getContainedFacilities().size());
		assertTrue("containedFacilities are different",
				result.getContainedFacilities().containsAll(persistedFacility.getContainedFacilities()));
		assertTrue("properties are different",
				result.getProperties().containsAll(persistedFacility.getProperties()));
	}

	/**
	 * Test get facilities.
	 */
	@Test
	public void testGetFacilities() {
		Collection<Facility> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull(facilityDAO.getFacilities(null));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		result = facilityDAO.getFacilities(Arrays.asList(propWlan));
		// facilities should be returned
		assertNotNull("no facilities found (NULL)", result);
		// facilities should be returned
		assertFalse("no facilities found (EMPTY)", result.isEmpty());
		// ensure the right facilities are returned
		assertTrue("not all facilities found", result.containsAll(facsWithWlan));
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
			assertNull(facilityDAO.getAvailablePropertiesOf(null));

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		result = facilityDAO.getAvailablePropertiesOf(Room.class);
		// properties should be returned
		assertNotNull("no properties", result);
		// ensure all properties are returned
		assertTrue("not all properties", result.containsAll(expected));
	}
}
