package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.gui.controller.ReservationController;
import edu.kit.pse.ass.gui.model.ReservationModel;
import edu.kit.pse.ass.realdata.DataHelper;

/**
 * Tests for the Reservation Controller
 * 
 * @author Jannis Koch
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@Transactional
public class ReservationControllerTest {

	/** the reservation controller */
	@Autowired
	ReservationController reservationController;

	/** data helper for creating test data */
	@Autowired
	private DataHelper dataHelper;

	/** the user id of user 1 */
	private static final String USERID1 = "ubbbb@student.kit.edu";

	/** the user id of user 2 */
	private static final String USERID2 = "ucccc@student.kit.edu";

	/** the booked room */
	private Room bookedRoom;

	/** a reservation of user 1 */
	private Reservation resUser1;

	/** a reservation of user 2 */
	private Reservation resUser2;

	/**
	 * creates the data for the tests
	 */
	@Before
	public void createTestData() {
		createUser();
		createFacilities();
		createReservations();

		// set the user in the security context
		setSecurityContext();

	}

	/**
	 * creates a persisted user for the tests
	 */
	private void createUser() {
		dataHelper.createPersistedUser(USERID1, "bbbbbb", new HashSet<String>());
	}

	/**
	 * creates persisted facilities for the tests
	 */
	private void createFacilities() {

		// create the properties
		Property propertyWLAN = new Property("WLAN");
		Property propertyLAN = new Property("LAN");
		Property propertySteckdose = new Property("Steckdose");

		// create a building with one room containing one work place
		Building building = dataHelper.createPersistedBuilding("50.20", "Informatik", new ArrayList<Property>());
		bookedRoom = dataHelper.createPersistedRoom("Seminarraum", "-101", -1,
				Arrays.asList(propertyWLAN, propertySteckdose, propertyLAN));
		Workplace wp = dataHelper.createPersistedWorkplace("Workplace 1", Arrays.asList(propertyLAN));
		bookedRoom.addContainedFacility(wp);
		building.addContainedFacility(bookedRoom);

	}

	/**
	 * creates persisted reservations for the tests
	 */
	private void createReservations() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, 1);
		Date start = calendar.getTime();
		calendar.add(Calendar.HOUR, 1);
		Date end = calendar.getTime();

		ArrayList<String> facilities = new ArrayList<String>();
		facilities.add(bookedRoom.getId());

		resUser1 = dataHelper.createPersistedReservation(USERID1, facilities, start, end);

		resUser2 = dataHelper.createPersistedReservation(USERID2, facilities, start, end);

		calendar.add(Calendar.MONTH, -2);
		start = calendar.getTime();
		calendar.add(Calendar.HOUR, 1);
		end = calendar.getTime();

		dataHelper.createPersistedReservation(USERID1, facilities, start, end);
	}

	/**
	 * sets the username in the security context
	 */
	private void setSecurityContext() {

		Authentication auth = new UsernamePasswordAuthenticationToken(USERID1, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * tests the listReservations() method
	 */
	@Test
	public void testListReservations() {

		// Create mock model and request for the test
		MockHttpServletRequest request = new MockHttpServletRequest();
		Model model = new BindingAwareModelMap();

		String view = reservationController.listReservations(model, request);

		assertEquals("reservation/list", view);

		Map<String, Object> modelAttributes = model.asMap();
		checkReservationCollection(modelAttributes, "reservations");
		checkReservationCollection(modelAttributes, "pastReservations");

		// delete notification was not set in the request
		assertTrue(modelAttributes.get("deleteNotification") == null);
	}

	/**
	 * Tests the listReservations() method with delete notification
	 */
	@Test
	public void testListReservationsDeleteNotification() {

		// Create mock model and request for the test
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("deleteNotification", "true");
		Model model = new BindingAwareModelMap();

		String view = reservationController.listReservations(model, request);

		assertEquals("reservation/list", view);

		Map<String, Object> modelAttributes = model.asMap();
		checkReservationCollection(modelAttributes, "reservations");
		checkReservationCollection(modelAttributes, "pastReservations");

		// delete notification was set in the request
		assertTrue(modelAttributes.get("deleteNotification") instanceof String);
		String deleteNotification = (String) modelAttributes.get("deleteNotification");
		assertEquals("true", deleteNotification);
	}

	/**
	 * tests the showReservationDetails() method
	 */
	@Test
	public void testShowReservationDetails() {

		// Create mock model for the test
		Model model = new BindingAwareModelMap();

		String view = reservationController.showReservationDetails(model, resUser1.getId());

		assertEquals("reservation/details", view);

		Map<String, Object> modelAttributes = model.asMap();
		assertTrue(modelAttributes.get("reservation") instanceof ReservationModel);
		ReservationModel resModel = (ReservationModel) modelAttributes.get("reservation");

		assertEquals(resModel.getId(), resUser1.getId());
		assertEquals(resModel.getRoomName(), bookedRoom.getName());
		assertEquals(resModel.getBuildingName(), bookedRoom.getParentFacility().getName());
		assertEquals(resModel.getRoom().getId(), bookedRoom.getId());
		assertEquals(resModel.getStartTime(), resUser1.getStartTime());
		assertEquals(resModel.getWorkplaceCount(), 1);

	}

	/**
	 * tests the showReservationDetails() method using an invalid reservation id
	 */
	@Test
	public void testShowReservationDetailsInvalidReservation() {

		// Create mock model for the test
		Model model = new BindingAwareModelMap();
		String wrongID = "xxx";
		String view = reservationController.showReservationDetails(model, wrongID);

		assertEquals("reservation/details", view);
		assertTrue(model.containsAttribute("errorReservationNotFound"));

	}

	/**
	 * tests the showReservationDetails() method using an empty reservation id
	 */
	@Test
	public void testShowReservationDetailsEmptyID() {

		// Create mock model for the test
		Model model = new BindingAwareModelMap();
		String emptyID = "";
		String view = reservationController.showReservationDetails(model, emptyID);

		assertEquals("reservation/details", view);
		assertTrue(model.containsAttribute("errorReservationNotFound"));

	}

	/**
	 * tests the showReservationDetails() method using an reservation id belonging to another user's reservation
	 */
	@Test
	public void testShowReservationDetailsWrongUser() {

		// Create mock model for the test
		Model model = new BindingAwareModelMap();
		String view = reservationController.showReservationDetails(model, resUser2.getId());

		assertEquals("reservation/details", view);
		assertTrue(model.containsAttribute("errorReservationNotFound"));
	}

	/**
	 * checks the reservation collection with the given attribute name in the model attributes
	 * 
	 * @param modelAttributes
	 *            the model attributes containing the collection
	 * @param attributeName
	 *            the attribute name of the collection to check
	 */
	private void checkReservationCollection(Map<String, Object> modelAttributes, String attributeName) {
		assertTrue("model does not contain " + attributeName, modelAttributes.containsKey(attributeName));
		assertTrue(attributeName + " is not a collection",
				modelAttributes.get(attributeName) instanceof Collection<?>);
		Collection<?> reservations = (Collection<?>) modelAttributes.get(attributeName);
		for (Object o : reservations) {
			assertTrue(attributeName + " contains wrong type", o instanceof ReservationModel);
		}
		assertEquals(1, reservations.size());
	}
}
