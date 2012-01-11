package edu.kit.pse.ass.facility.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.testdata.TestData;

// TODO: Auto-generated Javadoc
/**
 * The Class FacilityManagementImplTest.
 * 
 * @author Lennart
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class FacilityManagementImplTest {

	/** The Constant FACILITYID. */
	private static String FACILITYID = null;

	/** The Constant SEARCH_TEXT. */
	private static final String SEARCH_TEXT = "Informatik";

	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;

	/** The Constant FIND_PROPERTIES. */
	private static final Collection<Property> FIND_PROPERTIES = Arrays.asList(
			new Property("WLAN"), new Property("Steckdose"));

	/** The fm. */
	@Autowired
	FacilityManagement facilityManagement;

	/** The FACILIT y_ query. */
	FacilityQuery FACILITY_QUERY = new RoomQuery(FIND_PROPERTIES, SEARCH_TEXT,
			NEEDED_WORKPLACES);

	/** Collection of the Collections of the test-dummy facilities */
	Collection<Collection<Facility>> dummyFacilities = null;

	/** The test data. */
	@Autowired
	TestData testData;

	@Before
	void setUp() {
		dummyFacilities = testData.facilityFillWithDummies();
		FACILITYID = ((List<Facility>) ((List<Collection<Facility>>) dummyFacilities)
				.get(1)).get(0).getId();
	}

	/**
	 * Test get facility.
	 */
	@Test
	public void testGetFacility() {
		Facility result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					facilityManagement.getFacility(null));
			// TODO what's returned if nothing found?
			assertNull(facilityManagement.getFacility("ID9"));

			result = facilityManagement.getFacility(FACILITYID);
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
					facilityManagement.findMatchingFacilities(null));
			// TODO what's returned if nothing found? Therefore no property
			// "Strom" in testdata
			assertNull(facilityManagement.findMatchingFacilities(testQuery));

			result = facilityManagement.findMatchingFacilities(FACILITY_QUERY);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		assertNotNull("something should be returned", result);
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
					facilityManagement.getAvailablePropertiesOf(null));

			result = facilityManagement.getAvailablePropertiesOf(Room.class);
			assertTrue(facilityManagement.getAvailablePropertiesOf(
					Building.class).contains(
					((List<Property>) FIND_PROPERTIES).get(0)));
			assertFalse(facilityManagement.getAvailablePropertiesOf(
					Building.class).contains(
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
