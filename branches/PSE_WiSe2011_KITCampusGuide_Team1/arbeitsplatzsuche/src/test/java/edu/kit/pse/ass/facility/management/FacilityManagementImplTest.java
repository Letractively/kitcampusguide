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
import edu.kit.pse.ass.entity.Workplace;

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

		Building building = new Building();
		building.setName("Informatik-Hauptgebäude");
		building.setNumber("50.34");
		building.addProperty(propertyBarrierefrei);
		jpaTemplate.persist(building);

		// define which properties shall be found
		propertiesToFind = new ArrayList<Property>();
		propertiesToFind.add(propertyWLAN);
		propertiesToFind.add(propertySteckdose);

		// create 2 new rooms with WLAN and persist them in jpaTemplate
		persistedRoom1 = new Room();
		persistedRoom1.addProperty(propertyWLAN);
		jpaTemplate.persist(persistedRoom1);
		building.addContainedFacility(persistedRoom1);

		Workplace workplace = new Workplace();
		workplace.addProperty(propertySteckdose);
		jpaTemplate.persist(workplace);
		persistedRoom1.addContainedFacility(workplace);

		Workplace workplace2 = new Workplace();
		workplace2.addProperty(propertySteckdose);
		jpaTemplate.persist(workplace2);
		persistedRoom1.addContainedFacility(workplace2);

		Workplace workplace3 = new Workplace();
		workplace3.addProperty(propertySteckdose);
		jpaTemplate.persist(workplace3);
		persistedRoom1.addContainedFacility(workplace3);

		persistedRoom2 = new Room();
		persistedRoom2.addProperty(propertyWLAN);
		persistedRoom2.addProperty(propertyLicht);
		jpaTemplate.persist(persistedRoom2);
		building.addContainedFacility(persistedRoom2);

		// define the search
		facilityQuery = new RoomQuery(propertiesToFind, SEARCH_TEXT, NEEDED_WORKPLACES);
		facilityID = persistedRoom1.getId();
		assertFalse("Error on creating testdata", facilityID == null || facilityID.isEmpty());
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
	 *             This exception will never occur!
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithNullParameter() throws FacilityNotFoundException {
		facilityManagement.getFacility(null);
	}

	/**
	 * Test getFacility(String ID) with empty parameter.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception will never occur!
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityWithEmptyParameter() throws FacilityNotFoundException {
		facilityManagement.getFacility("");
	}

	/**
	 * Tests the method getFacility(String ID) with an existing id.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception will never occur!
	 */
	@Test
	public void testGetFacility() throws FacilityNotFoundException {

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
	 * Test get facility with wrong type but existing id.
	 * 
	 * @throws FacilityNotFoundException
	 *             the facility not found exception
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testGetFacilityWithWrongType() throws FacilityNotFoundException {
		facilityManagement.getFacility(Building.class, facilityID);
	}

	/**
	 * Tests getFacility(Class<T> type,String ID) with null parameter.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception will never occur!
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityTypeWithNullParameter() throws FacilityNotFoundException {
		facilityManagement.getFacility(Building.class, null);
	}

	/**
	 * Test getFacility(Class<T> type,String ID) with empty parameter.
	 * 
	 * @throws FacilityNotFoundException
	 *             This exception will never occur!
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFacilityTypeWithEmptyParameter() throws FacilityNotFoundException {
		facilityManagement.getFacility(Building.class, "");
	}

	/**
	 * Test get facility with type Room.
	 * 
	 * @throws FacilityNotFoundException
	 *             musst never occur
	 */
	@Test
	public void testGetFacilityWithTypeRoom() throws FacilityNotFoundException {
		Room r = facilityManagement.getFacility(Room.class, facilityID);
		assertEquals("Wrong facility returned.", facilityID, r.getId());
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
		Collection<FacilityResult> result = facilityManagement.findMatchingFacilities(testQuery);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	/**
	 * Test find matching facilities.
	 */
	@Test
	public void testFindMatchingFacilities() {

		Collection<FacilityResult> result = facilityManagement.findMatchingFacilities(facilityQuery);
		assertNotNull("result is null", result);
		assertFalse("result ist empty", result.isEmpty());
		for (FacilityResult fac : result) {
			assertEquals("wrong class", Room.class, fac.getFacility().getClass());
			assertTrue("not enough places", fac.getFacility().getContainedFacilities().size() >= NEEDED_WORKPLACES);
			assertTrue("not enough places", fac.getMatchingChildFacilities().size() >= NEEDED_WORKPLACES);
			assertTrue("not all properties", fac.getFacility().hasInheritedProperties(propertiesToFind)
					|| allChildrenMatchProps(fac.getMatchingChildFacilities(), propertiesToFind));
		}
	}

	private boolean allChildrenMatchProps(Collection<Facility> matchingChildFacilities,
			Collection<Property> properties) {
		for (Facility fac : matchingChildFacilities) {
			if (!fac.hasInheritedProperties(properties)) {
				return false;
			}
		}
		return true;
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
		assertTrue("Property missing", result.contains(propertyBarrierefrei));
		assertTrue("Wrong Property found", result.size() == 1);
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
				result.size() == 2 && result.contains(propertyWLAN) && result.contains(propertyLicht));
	}
}
