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
import org.springframework.orm.jpa.JpaTemplate;
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

	@Autowired
	private JpaTemplate jpaTemplate;

	@Autowired
	private FacilityDAO facilityDAO;

	private Facility persistedRoom1;

	private Facility persistedRoom2;

	private Property propertyWLAN;

	private Collection<Facility> roomsWithWLAN;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		propertyWLAN = new Property("WLAN");

		// create 2 new rooms with WLAN and persist them in jpaTemplate
		persistedRoom1 = new Room();
		persistedRoom1.addProperty(propertyWLAN);
		jpaTemplate.persist(persistedRoom1);

		persistedRoom2 = new Room();
		persistedRoom2.addProperty(propertyWLAN);
		jpaTemplate.persist(persistedRoom2);

		// add this rooms to Collection roomsWithWLAN
		roomsWithWLAN = new ArrayList<Facility>();
		roomsWithWLAN.add(persistedRoom1);
		roomsWithWLAN.add(persistedRoom2);
	}

	/**
	 * Tests getFacility() with null parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithNullParameter() {
		facilityDAO.getFacility(null);
	}

	/**
	 * Tests getFacility() with empty parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithEmptyParameter() {
		facilityDAO.getFacility("");
	}

	/**
	 * Tests getFacility() with parameter that leads to a return equal null.
	 */
	@Test
	public void testGetFacilityWithNotExistingParameter() {
		assertNull("There should not be a facility with this ID. Really!", facilityDAO.getFacility("INVALID"));
	}

	/**
	 * Test getFacility() whether this method returns the right object.
	 */
	@Test
	public void testGetFacilityWithValidParameter() {
		// get a persisted room with getFacility() via his ID
		Facility result = facilityDAO.getFacility(persistedRoom1.getId());

		// at least any facility should be returned
		assertNotNull("No facility found despite it was added before.", result);

		// ensure the right facility is returned
		// check class of result
		assertTrue("facility is not a Room", result instanceof Room);

		// check ID of result
		assertEquals("ID of facility is wrong", persistedRoom1.getId(), result.getId());

		// check children of result
		assertEquals("containedFacilities are different", persistedRoom1.getContainedFacilities().size(), result
				.getContainedFacilities().size());
		assertTrue("containedFacilities are different",
				result.getContainedFacilities().containsAll(persistedRoom1.getContainedFacilities()));

		// check properties of result
		assertTrue("properties are different", result.getProperties().containsAll(persistedRoom1.getProperties()));

		// check if result is equal
		assertTrue("Wrong facility returned.", persistedRoom1.equals(result));
	}

	/**
	 * Tests getAvailablePropertiesOf() with null parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetAvailablePropertiesOfWithNullParameter() {
		facilityDAO.getAvailablePropertiesOf(null);
	}

	/**
	 * Tests getAvailablePropertiesOf() with parameter that leads to a return equal null.
	 */
	@Test
	public void testGetAvailablePropertiesOfNotExistingParameter() {
		assertNull("There should not be a workingplace, thus no properties neither",
				facilityDAO.getAvailablePropertiesOf(Workplace.class));
	}

	/**
	 * Test getAvailablePropertiesOf() with valid parameter.
	 */
	@Test
	public void testGetAvailablePropertiesOfWithValidParameter() {
		Collection<Property> result = facilityDAO.getAvailablePropertiesOf(Room.class);

		// properties should be returned
		assertFalse("The properties were not found.", result.isEmpty());

		// ensure the right properties are returned
		assertTrue("The 2 Collection<Property> should have same size.",
				persistedRoom1.getProperties().size() == result.size());
		assertTrue("More properties as intended were found.", persistedRoom1.getProperties().containsAll(result));
		assertTrue("Not all properties were found.", result.containsAll(persistedRoom1.getProperties()));
		assertTrue("The found properties do not match the existing ones.",
				result.equals(persistedRoom1.getProperties()));

	}

	/**
	 * Tests getFacilities with null parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilitiesWithNullParameter() {
		facilityDAO.getFacilities(null);
	}

	/**
	 * Tests getFacilities() with empty parameter.
	 */
	public void testGetFacilitiesWithEmptyParameter() {
		assertNull(facilityDAO.getFacilities(new ArrayList<Property>()));
	}

	/**
	 * Tests getFacilities() with property parameter that should lead to a result equal null.
	 */
	@Test
	public void testGetFacilitiesWithNotExistingProperty() {
		Property propertyToillet = new Property();
		propertyToillet.setName("Toillet");

		Collection<Facility> result = facilityDAO.getFacilities(Arrays.asList(propertyToillet));

		assertNotNull("getFacilities() should have returned an empty collection.", result);
		assertTrue("There should not be any facilities with this property", result.isEmpty());
	}

	/**
	 * Tests getFacilities() with valid parameter.
	 */
	@Test
	public void testGetFacilitiesWithValidParameter() {
		Collection<Facility> result = facilityDAO.getFacilities(Arrays.asList(propertyWLAN));

		// here you should only get rooms, because nothing else was added

		// at least any facilities/rooms should be returned
		assertNotNull("No facilities found.", result);
		assertFalse("Not one facility found.", result.isEmpty());

		// ensure the right facilities are returned
		assertTrue("Not all facilities were found.", result.containsAll(roomsWithWLAN));
		assertTrue("Too many facilities were returned.", roomsWithWLAN.containsAll(result));
		assertTrue("Wrong facilities were returned.", roomsWithWLAN.equals(result));
	}
}
