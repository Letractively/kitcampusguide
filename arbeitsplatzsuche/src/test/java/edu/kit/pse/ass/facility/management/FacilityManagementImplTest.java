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
	private static final String SEARCH_TEXT = "Informatik 50.34";

	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;

	/** The Constant FIND_PROPERTIES. */
	private static final Collection<Property> FIND_PROPERTIES = Arrays.asList(
			new Property("WLAN"), new Property("Steckdose"));

	/** all available properties of rooms */
	private static final Collection<Property> ALL_ROOM_PROPERTIES = Arrays
			.asList(new Property("WLAN"), new Property("Steckdose"),
					new Property("LAN"), new Property("Barrierefrei"),
					new Property("Licht"), new Property("PC"));

	/** The fm. */
	@Autowired
	private FacilityManagement facilityManagement;

	/** The test data. */
	@Autowired
	TestData testData;

	/** The FACILIT y_ query. */
	FacilityQuery FACILITY_QUERY = new RoomQuery(FIND_PROPERTIES, SEARCH_TEXT,
			NEEDED_WORKPLACES);

	/** Collection of the Collections of the test-dummy facilities */
	TestData.DummyFacilities dummyFacilities = null;

	@Before
	public void setUp() {
		dummyFacilities = testData.facilityFillWithDummies();
		FACILITYID = dummyFacilities.rooms.iterator().next().getId();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNotExistingFacility() {
		// The id should not exist.
		facilityManagement.getFacility("ID9");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithNullArgument() {
		facilityManagement.getFacility(null);
	}

	/**
	 * Test get facility.
	 */
	@Test
	public void testGetFacility() {
		// check for right input
		assertNotNull(FACILITYID);
		assertFalse(FACILITYID.isEmpty());
		// try to get facility
		Facility result = facilityManagement.getFacility(FACILITYID);
		// a facility should be returned
		assertNotNull(result);
		// assert the correct facility was returned
		assertEquals(FACILITYID, result.getId());
		assertTrue(result.getProperties().contains(new Property("WLAN")));
		assertTrue(result.getContainedFacilities().size() == 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindMatchingFacilitiesWithNullArgument() {
		facilityManagement.findMatchingFacilities(null);
	}

	public void testFindMatchingFacilitiesWithEmptyResult() {
		// the room query
		FacilityQuery testQuery = new RoomQuery(Arrays.asList(new Property(
				"Strom")), SEARCH_TEXT, NEEDED_WORKPLACES);
		// no such facility should be found
		Collection<? extends Facility> result = facilityManagement
				.findMatchingFacilities(testQuery);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	/**
	 * Test find matching facilities.
	 */
	@Test
	public void testFindMatchingFacilities() {

		Collection<? extends Facility> result = facilityManagement
				.findMatchingFacilities(FACILITY_QUERY);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		for (Facility fac : result) {
			assertEquals(fac.getClass(), Room.class);
			assertTrue(fac.getContainedFacilities().size() >= NEEDED_WORKPLACES);
			assertTrue(fac.getProperties().containsAll(FIND_PROPERTIES));
		}
	}

	/**
	 * Test get available properties of.
	 */
	@Test
	public void testGetAvailablePropertisOf() {
		Collection<Property> result = null;
		try {
			// throw error or return null if parameter is null
			assertNull("Accepted wrong parameters.",
					facilityManagement.getAvailablePropertiesOf(null));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		// buildings only have wlan
		result = facilityManagement.getAvailablePropertiesOf(Building.class);
		assertNotNull("Result is null", result);
		assertTrue(result.contains(((List<Property>) FIND_PROPERTIES).get(0)));
		assertFalse(facilityManagement.getAvailablePropertiesOf(Building.class)
				.contains(((List<Property>) FIND_PROPERTIES).get(1)));
		result = null;
		result = facilityManagement.getAvailablePropertiesOf(Room.class);
		// something should be returned
		assertNotNull("Result is null", result);
		// assert the correct properties are returned
		assertTrue(result.containsAll(ALL_ROOM_PROPERTIES));
	}
}
