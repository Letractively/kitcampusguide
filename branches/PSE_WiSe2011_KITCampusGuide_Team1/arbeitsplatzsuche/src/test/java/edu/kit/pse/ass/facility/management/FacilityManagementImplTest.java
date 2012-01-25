package edu.kit.pse.ass.facility.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.annotation.ExpectedException;
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
	private static final List<Property> FIND_PROPERTIES = Arrays.asList(new Property("WLAN"), new Property(
			"Steckdose"));

	/** all available properties of rooms */
	private static final Collection<Property> ALL_ROOM_PROPERTIES = Arrays.asList(new Property("WLAN"),
			new Property("Steckdose"), new Property("LAN"), new Property("Barrierefrei"), new Property("Licht"),
			new Property("PC"));

	/** The fm. */
	@Autowired
	private FacilityManagement facilityManagement;

	/** The test data. */
	@Autowired
	TestData testData;

	/** The FACILIT y_ query. */
	FacilityQuery facilityQuery = new RoomQuery(FIND_PROPERTIES, SEARCH_TEXT, NEEDED_WORKPLACES);

	/** Collection of the Collections of the test-dummy facilities */
	TestData.DummyFacilities dummyFacilities;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		dummyFacilities = testData.facilityFillWithDummies();
		for (Room r : dummyFacilities.rooms) {
			if (r.hasProperty(new Property("WLAN"))) {
				FACILITYID = r.getId();
				break;
			}
		}
	}

	/**
	 * Tests the method getFacility(String ID) with an ID which does not exist.
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @throws FacilityNotFoundException
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testGetNotExistingFacility() throws IllegalArgumentException, FacilityNotFoundException {
		// The id should not exist.
		facilityManagement.getFacility("ID9");
	}

	/**
	 * Tests the method getFacility(String ID) with null as parameter.
	 * 
	 * @throws IllegalArgumentException
	 * @throws FacilityNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithNullArgument() throws IllegalArgumentException, FacilityNotFoundException {
		facilityManagement.getFacility(null);
	}

	/**
	 * Test get facility.
	 * 
	 * @throws FacilityNotFoundException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testGetFacility() throws IllegalArgumentException, FacilityNotFoundException {
		// check for right input
		assertNotNull("No facility given", FACILITYID);
		assertFalse("No facility given", FACILITYID.isEmpty());
		// try to get facility
		Facility result = facilityManagement.getFacility(FACILITYID);
		// a facility should be returned
		assertNotNull(result);
		// assert the correct facility was returned
		assertEquals("Wrong facility ID", FACILITYID, result.getId());
		assertTrue("result has no wlan", result.hasInheritedProperty(new Property("WLAN")));
		assertTrue("result has not enough workplaces", result.getContainedFacilities().size() == 3);
	}

	/**
	 * Tests the method findMatchingFacilities(FacilityQuery facilityQuery) with null as parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindMatchingFacilitiesWithNullArgument() {
		facilityManagement.findMatchingFacilities(null);
	}

	/**
	 * Tests the result of the method findMatchingFacilities(FacilityQuery facilityQuery) if it is empty but not null
	 * when it should be so.
	 */
	public void testFindMatchingFacilitiesWithEmptyResult() {
		// the room query
		FacilityQuery testQuery = new RoomQuery(Arrays.asList(new Property("Strom")), SEARCH_TEXT,
				NEEDED_WORKPLACES);
		// no such facility should be found
		Collection<? extends Facility> result = facilityManagement.findMatchingFacilities(testQuery);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	/**
	 * Test find matching facilities.
	 */
	@Test
	public void testFindMatchingFacilities() {

		Collection<? extends Facility> result = facilityManagement.findMatchingFacilities(facilityQuery);
		assertNotNull("result is null", result);
		assertFalse("result ist empty", result.isEmpty());
		for (Facility fac : result) {
			assertEquals("wrong class", fac.getClass(), Room.class);
			assertTrue("not enough places", fac.getContainedFacilities().size() >= NEEDED_WORKPLACES);
			assertTrue("not all properties", fac.getProperties().containsAll(FIND_PROPERTIES));
		}
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void testGetAvailablePropertisOfWrongParameters() {
		facilityManagement.getAvailablePropertiesOf(null);
	}

	/**
	 * Test get available properties of.
	 */
	@Test
	public void testGetAvailablePropertiesOfBuilding() {
		Collection<Property> result = facilityManagement.getAvailablePropertiesOf(Building.class);
		assertNotNull("Result is null", result);
		assertTrue("Property missing", result.contains(FIND_PROPERTIES.get(0)));
		assertFalse("Wrong Property found", result.contains(FIND_PROPERTIES.get(1)));
	}

	@Test
	public void testGetAvailablePropertiesOfRoom() {
		Collection<Property> result = null;
		result = facilityManagement.getAvailablePropertiesOf(Room.class);
		// something should be returned
		assertNotNull("Result is null", result);
		// assert the correct properties are returned
		assertTrue("Missing properties or too many",
				result.size() == ALL_ROOM_PROPERTIES.size() && result.containsAll(ALL_ROOM_PROPERTIES));
	}
}
