package edu.kit.pse.ass.facility.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
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

/**
 * The Class FacilityManagementImplTest.
 * 
 * @author Fabian
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class FacilityManagementImplTest {

	private static final String SEARCH_TEXT = "Informatik 50.34";

	private static final int NEEDED_WORKPLACES = 3;

	@Autowired
	private JpaTemplate jpaTemplate;

	@Autowired
	private FacilityManagement facilityManagement;

	private Property propertyWLAN;
	private Property propertyLAN;
	private Property propertySteckdose;
	private Property propertyBarrierefrei;
	private Property propertyLicht;
	private Property propertyPC;

	private Collection<Property> propertiesToFind;

	private Facility persistedRoom1;
	private Facility persistedRoom2;

	private FacilityQuery facilityQuery;

	private String facilityID;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		// create the properties
		propertyWLAN = new Property("WLAN");
		propertyLAN = new Property("LAN");
		propertySteckdose = new Property("Steckdose");
		propertyBarrierefrei = new Property("Barrierefrei");
		propertyLicht = new Property("Licht");
		propertyPC = new Property("PC");

		// define which properties shall be found
		propertiesToFind = new ArrayList<Property>();
		propertiesToFind.add(propertyWLAN);
		propertiesToFind.add(propertySteckdose);

		// create 2 new rooms with WLAN and persist them in jpaTemplate
		persistedRoom1 = new Room();
		persistedRoom1.addProperty(propertyWLAN);
		jpaTemplate.persist(persistedRoom1);

		persistedRoom2 = new Room();
		persistedRoom2.addProperty(propertyWLAN);
		jpaTemplate.persist(persistedRoom2);

		// define the search
		facilityQuery = new RoomQuery(propertiesToFind, SEARCH_TEXT, NEEDED_WORKPLACES);
	}

	/**
	 * Tests the method getFacility(String ID) with an not existing ID.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception is expected!
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testGetFacilityWithNotExistingParameter() throws FacilityNotFoundException {
		facilityManagement.getFacility("ThisIsNotAnID");
	}

	/**
	 * Tests the method getFacility(String ID) with a null parameter.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception will never occure!
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithNullParameter() throws FacilityNotFoundException {
		facilityManagement.getFacility(null);
	}

	/**
	 * Tests the method getFacility(String ID) with a null parameter.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception will never occure!
	 */
	@Test
	public void testGetFacility() throws FacilityNotFoundException {

		// check for right input
		assertNotNull("No facility given", facilityID);
		assertFalse("No facility given", facilityID.isEmpty());
		// try to get facility
		Facility result = facilityManagement.getFacility(facilityID);
		// a facility should be returned
		assertNotNull(result);
		// assert the correct facility was returned
		assertEquals("Wrong facility ID", facilityID, result.getId());
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

	/**
	 * 
	 */
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

	/**
	 * 
	 */
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
