package edu.kit.pse.ass.booking.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.FacilityQuery;
import edu.kit.pse.ass.realdata.DataHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingManagementImplTest.
 * 
 * @author Lennart
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingManagementImplTest {

	/** The email of the user. */
	private static final String USER_ID = "ubbbb@student.kit.edu";;

	/** The Constant FIND_PROPERTY_NAMES. */
	private static final String[] FIND_PROPERTY_NAMES = { "WLAN", "Steckdose" };

	/** The Constant SEARCH_TEXT. */
	private static final String SEARCH_TEXT = "Informatik";

	/** The Constant NEEDED_WORKPLACES. */
	private static final int NEEDED_WORKPLACES = 3;

	/** The booking management. */
	@Autowired
	private BookingManagement bookingManagement;

	/** a valid start date (in future) */
	private Date validStartDate;

	/** a valid end date (in future, after start date) */
	private Date validEndDate;

	@Autowired
	private DataHelper dataHelper;

	/** a building that could be booked */
	private Building infoBuilding;
	/** a room that could be booked */
	private Room room1;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		initTestBuildingWithRoomsAndWorkplaces();

		// get current date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		// start is current date + 1
		validStartDate = cal.getTime();

		cal.add(Calendar.HOUR, 1);
		// end is current date + 2
		validEndDate = cal.getTime();
	}

	/**
	 * Creates a simple building with one room
	 */
	private void initTestBuildingWithRoomsAndWorkplaces() {
		infoBuilding = dataHelper.createPersistedBuilding("50.20", "Informatik", new ArrayList<Property>());
		room1 = dataHelper.createPersistedRoom("Seminarraum", "-101", -1, Arrays.asList(new Property("WLAN")));

		infoBuilding.addContainedFacility(room1);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if userID is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookWithNullUserID() throws Exception {
		bookingManagement.book(null, Arrays.asList(room1.getId()), validStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if userID is an empty string.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookWithEmptyUserID() throws Exception {
		bookingManagement.book("", Arrays.asList(room1.getId()), validStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if facilityIDs list is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookWithNullFacilities() throws Exception {
		bookingManagement.book(USER_ID, null, validStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if facilityID list is empty.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookWithEmptyFacilities() throws Exception {
		bookingManagement.book(USER_ID, new ArrayList<String>(), validStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if startDate is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookWithNullStart() throws Exception {
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), null, validEndDate);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if endDate is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookWithNullEnd() throws Exception {
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), validStartDate, null);
	}

	/**
	 * Tests if book throws an IllegalArgumenExcpetion if endDate before startDate.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookStartDateAfterEndDate() throws Exception {
		// switched end and start
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), validEndDate, validStartDate);
	}

	/**
	 * Tests if book throws an IllegalDateExcpetion if startDate is in the past.
	 * 
	 * @throws Exception
	 *             should be an IllegalDateException
	 */
	@Test(expected = IllegalDateException.class)
	public void testBookStartDateInPast() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -2);
		// start is current date + 1
		Date invalidStartDate = cal.getTime();
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), invalidStartDate, validEndDate);
	}

	/**
	 * Tests if booking of an single room works.
	 * 
	 * @throws Exception
	 *             no exception should be thrown
	 */
	@Test
	public void testBook() throws Exception {
		String createdReservationID = bookingManagement.book(USER_ID, Arrays.asList(room1.getId()),
				validStartDate, validEndDate);
		// a reservation must be returned
		assertNotNull("Reservation id is null", createdReservationID);
		assertFalse("Reservation id is emtpy", createdReservationID.isEmpty());
		// check if returned reservation is correct
		Reservation createdReservation = bookingManagement.getReservation(createdReservationID);
		assertNotNull("Reservation of returned id was not found", createdReservation);

		assertEquals(USER_ID, createdReservation.getBookingUserId());

		assertEquals(1, createdReservation.getBookedFacilityIds().size());
		assertTrue(createdReservation.getBookedFacilityIds().contains(room1.getId()));
		assertEquals(validStartDate, createdReservation.getStartTime());
		assertEquals(validEndDate, createdReservation.getEndTime());
	}

	/**
	 * Tests if listReservationsOfUser throws an IllegalArgumentException if userID is null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListReservationsOfUserWithNullUserID() {
		bookingManagement.listReservationsOfUser(null, validStartDate, validEndDate);
	}

	/**
	 * Tests if listReservationsOfUser throws an IllegalArgumentException if startDate is null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListReservationsOfUserWithNullStartDate() {
		bookingManagement.listReservationsOfUser(USER_ID, null, validEndDate);
	}

	/**
	 * Tests if listReservationsOfUser throws an IllegalArgumentException if endDate is null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListReservationsOfUserWithNullEndDate() {
		bookingManagement.listReservationsOfUser(USER_ID, validStartDate, null);
	}

	/**
	 * Tests if listReservationsOfUser not lists reservations of other users.
	 */
	@Test
	public void testListReservationsOfUserNoMatch() {
		// create a reservation of another user
		dataHelper.createPersistedReservation("someID", new ArrayList<String>(), validStartDate, validEndDate);

		// should not find above reservation
		Collection<Reservation> reservationsOfUser;
		reservationsOfUser = bookingManagement.listReservationsOfUser(USER_ID, validStartDate, validEndDate);
		assertNotNull(reservationsOfUser);
		assertTrue(reservationsOfUser.isEmpty());
	}

	/**
	 * Tests if listReservationsOfUser lists reservations that start after list start date and end before list end date.
	 */
	@Test
	public void testListReservationsOfUserInInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		cal.add(Calendar.HOUR, -23);

		// first reservation is from now +1 to now +3
		Date reservation1StartDate = cal.getTime();
		cal.add(Calendar.HOUR, +2);
		Date reservation1EndDate = cal.getTime();
		Reservation reservation1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(),
				reservation1StartDate, reservation1EndDate);

		// second reservation is from now +4 to now +5
		cal.add(Calendar.HOUR, 1);
		Date reservation2StartDate = cal.getTime();

		cal.add(Calendar.HOUR, 1);
		Date reservation2EndDate = cal.getTime();

		Reservation reservation2 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(),
				reservation2StartDate, reservation2EndDate);

		// should find both above reservation
		Collection<Reservation> reservationsOfUser;
		reservationsOfUser = bookingManagement.listReservationsOfUser(USER_ID, listStart, listEnd);

		assertNotNull(reservationsOfUser);
		assertEquals(2, reservationsOfUser.size());
		assertTrue(reservationsOfUser.contains(reservation1));
		assertTrue(reservationsOfUser.contains(reservation2));
	}

	/**
	 * Tests if listReservationsOfUser lists reservations that start and end at given list dates
	 */
	@Test
	public void testListReservationsOfUserEqualInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		Reservation reservation1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(),
				listStart, listEnd);

		// should find above reservation
		Collection<Reservation> reservationsOfUser;
		reservationsOfUser = bookingManagement.listReservationsOfUser(USER_ID, listStart, listEnd);

		assertNotNull(reservationsOfUser);
		assertEquals(1, reservationsOfUser.size());
		assertTrue(reservationsOfUser.contains(reservation1));
	}

	/**
	 * Tests if listReservationsOfUser not lists reservations after given list interval
	 */
	@Test
	public void testListReservationsOfUserAfterInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		// first reservation is 1 second after list end
		cal.add(Calendar.SECOND, 1);
		Date reservation1StartDate = cal.getTime();
		cal.add(Calendar.HOUR, +2);
		Date reservation1EndDate = cal.getTime();
		dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), reservation1StartDate,
				reservation1EndDate);

		// should not find above reservation
		Collection<Reservation> reservationsOfUser;
		reservationsOfUser = bookingManagement.listReservationsOfUser(USER_ID, listStart, listEnd);

		assertNotNull(reservationsOfUser);
		assertTrue(reservationsOfUser.isEmpty());
	}

	/**
	 * Tests if listReservationsOfUser not lists reservations before given list interval.
	 */
	@Test
	public void testListReservationsOfUserBeforeInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		// first reservation start date is 2 hour before list start
		cal.add(Calendar.HOUR, -26);
		Date reservation1StartDate = cal.getTime();
		cal.add(Calendar.HOUR, +1);
		Date reservation1EndDate = cal.getTime();
		dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), reservation1StartDate,
				reservation1EndDate);

		// should not find above reservation
		Collection<Reservation> reservationsOfUser;
		reservationsOfUser = bookingManagement.listReservationsOfUser(USER_ID, listStart, listEnd);

		assertNotNull(reservationsOfUser);
		assertTrue(reservationsOfUser.isEmpty());
	}

	/*
	 * Test ListReservationsOfFacility
	 */
	/**
	 * Tests if listReservationsOfFacility throws an IllegalArgumentException if facilityID is null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListReservationsOfFacilityWithNullFacilityID() {
		bookingManagement.listReservationsOfFacility(null, validStartDate, validEndDate);
	}

	/**
	 * Tests if listReservationsOfFacility throws an IllegalArgumentException if startDate is null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListReservationsOfFacilityWithNullStartDate() {
		bookingManagement.listReservationsOfFacility(room1.getId(), null, validEndDate);
	}

	/**
	 * Tests if listReservationsOfFacility throws an IllegalArgumentException if endDate is null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListReservationsOfFacilityWithNullEndDate() {
		bookingManagement.listReservationsOfFacility(room1.getId(), validStartDate, null);
	}

	/**
	 * Tests if listReservationsOfFacility not lists reservations of other facilities.
	 */
	@Test
	public void testListReservationsOfFacilityNoMatch() {
		// create a reservation of another user
		dataHelper.createPersistedReservation(USER_ID, Arrays.asList(room1.getId()), validStartDate, validEndDate);

		// should not find above reservation
		Collection<Reservation> reservationsOfFacility;
		reservationsOfFacility = bookingManagement.listReservationsOfFacility(infoBuilding.getId(),
				validStartDate, validEndDate);
		assertNotNull(reservationsOfFacility);
		assertTrue(reservationsOfFacility.isEmpty());
	}

	/**
	 * Tests if listReservationsOfFacility lists reservations that start after list start date and end before list end
	 * date.
	 */
	@Test
	public void testListReservationsOfFacilityInInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		cal.add(Calendar.HOUR, -23);

		// first reservation is from now +1 to now +3
		Date reservation1StartDate = cal.getTime();
		cal.add(Calendar.HOUR, +2);
		Date reservation1EndDate = cal.getTime();
		Reservation reservation1 = dataHelper.createPersistedReservation(USER_ID, Arrays.asList(room1.getId()),
				reservation1StartDate, reservation1EndDate);

		// second reservation is from now +4 to now +5
		cal.add(Calendar.HOUR, 1);
		Date reservation2StartDate = cal.getTime();

		cal.add(Calendar.HOUR, 1);
		Date reservation2EndDate = cal.getTime();

		Reservation reservation2 = dataHelper.createPersistedReservation(USER_ID,
				Arrays.asList(room1.getId(), infoBuilding.getId()), reservation2StartDate, reservation2EndDate);

		// should find both above reservation
		Collection<Reservation> reservationsOfFacility;
		reservationsOfFacility = bookingManagement.listReservationsOfFacility(room1.getId(), listStart, listEnd);

		assertNotNull(reservationsOfFacility);
		assertEquals(2, reservationsOfFacility.size());
		assertTrue(reservationsOfFacility.contains(reservation1));
		assertTrue(reservationsOfFacility.contains(reservation2));
	}

	/**
	 * Tests if listReservationsOfFacility lists reservations that start and end at given list dates
	 */
	@Test
	public void testListReservationsOfFacilityEqualInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		Reservation reservation1 = dataHelper.createPersistedReservation(USER_ID, Arrays.asList(room1.getId()),
				listStart, listEnd);

		// should find above reservation
		Collection<Reservation> reservationsOfFacility;
		reservationsOfFacility = bookingManagement.listReservationsOfFacility(room1.getId(), listStart, listEnd);

		assertNotNull(reservationsOfFacility);
		assertEquals(1, reservationsOfFacility.size());
		assertTrue(reservationsOfFacility.contains(reservation1));
	}

	/**
	 * Tests if listReservationsOfFacility not lists reservations after given list interval
	 */
	@Test
	public void testListReservationsOfFacilityAfterInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		// first reservation is 1 second after list end
		cal.add(Calendar.SECOND, 1);
		Date reservation1StartDate = cal.getTime();
		cal.add(Calendar.HOUR, +2);
		Date reservation1EndDate = cal.getTime();
		dataHelper.createPersistedReservation(USER_ID, Arrays.asList(room1.getId()), reservation1StartDate,
				reservation1EndDate);

		// should not find above reservation
		Collection<Reservation> reservationsOfFacility;
		reservationsOfFacility = bookingManagement.listReservationsOfFacility(room1.getId(), listStart, listEnd);

		assertNotNull(reservationsOfFacility);
		assertTrue(reservationsOfFacility.isEmpty());
	}

	/**
	 * Tests if listReservationsOfUser not lists reservations before given list interval.
	 */
	@Test
	public void testListReservationsOfFacilityBeforeInterval() {
		// create a reservation
		Calendar cal = Calendar.getInstance();

		// list start is now
		Date listStart = cal.getTime();
		cal.add(Calendar.HOUR, 24);
		// list end is now + 24h
		Date listEnd = cal.getTime();

		// first reservation start date is 1 hour before list start
		cal.add(Calendar.HOUR, -26);
		Date reservation1StartDate = cal.getTime();
		cal.add(Calendar.HOUR, +1);
		Date reservation1EndDate = cal.getTime();
		dataHelper.createPersistedReservation(USER_ID, Arrays.asList(room1.getId()), reservation1StartDate,
				reservation1EndDate);

		// should not find above reservation
		Collection<Reservation> reservationsOfFacility;
		reservationsOfFacility = bookingManagement.listReservationsOfFacility(room1.getId(), listStart, listEnd);

		assertNotNull(reservationsOfFacility);
		assertTrue(reservationsOfFacility.isEmpty());
	}

	// /**
	// * Test change reservation end.
	// *
	// * @throws ReservationNotFoundException
	// * @throws IllegalArgumentException
	// * @throws FacilityNotFreeException
	// */
	// @Test
	// public void testChangeReservationEnd() throws IllegalArgumentException, ReservationNotFoundException,
	// FacilityNotFreeException {
	// Date newEnd = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
	// try {
	// // TODO throw error if parameter is null
	// bookingManagement.changeReservationEnd(reservationID, null);
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	// try {
	// bookingManagement.changeReservationEnd(null, newEnd);
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	//
	// bookingManagement.changeReservationEnd(reservationID, newEnd);
	// // check if reservation is changed successfully
	// assertEquals(newEnd, bookingManagement.getReservation(reservationID).getEndTime());
	// assertEquals(start, bookingManagement.getReservation(reservationID).getStartTime());
	// assertEquals(userID, bookingManagement.getReservation(reservationID).getBookingUserId());
	// }
	//
	// /**
	// * Test remove facility from reservation.
	// *
	// * @throws ReservationNotFoundException
	// * @throws IllegalArgumentException
	// */
	// @Test
	// public void testRemoveFacilityFromReservation() throws IllegalArgumentException, ReservationNotFoundException {
	// assertTrue(place.size() == bookingManagement.getReservation(reservationID).getBookedFacilityIds().size());
	// try {
	// // TODO throw error if parameter is null
	// bookingManagement.removeFacilityFromReservation(null, place.get(0));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	// try {
	// bookingManagement.removeFacilityFromReservation(reservationID, null);
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	//
	// bookingManagement.removeFacilityFromReservation(reservationID, place.get(0));
	// // check if the right facility was removed
	// assertTrue("No facility removed", place.size() > bookingManagement.getReservation(reservationID)
	// .getBookedFacilityIds().size());
	// assertFalse("Facility still in bookedFacilityIds, but a facility was removed", bookingManagement
	// .getReservation(reservationID).getBookedFacilityIds().contains(place.get(0)));
	// }
	//
	// /**
	// * Test delete reservation.
	// *
	// * @throws ReservationNotFoundException
	// * @throws IllegalArgumentException
	// */
	// @Test
	// public void testDeleteReservation() throws IllegalArgumentException, ReservationNotFoundException {
	// assertNotNull(bookingManagement.getReservation(reservationID));
	// try {
	// // TODO throw error if parameter is null
	// bookingManagement.deleteReservation(null);
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	//
	// bookingManagement.deleteReservation(reservationID);
	// // TODO getRes must return null if res not found
	// try {
	// bookingManagement.getReservation(reservationID);
	// fail("No ReservationNotFoundException");
	// } catch (ReservationNotFoundException e) {
	// }
	// // check if other reservation untouched
	// assertNotNull(bookingManagement.getReservation(reservationID2));
	// }
	//
	// /**
	// * Test get reservation.
	// *
	// * @throws ReservationNotFoundException
	// */
	// @Test
	// public void testGetReservation() throws ReservationNotFoundException {
	// Reservation resv = null;
	// try {
	// // throw error or return null if parameter is null
	// assertNull(bookingManagement.getReservation(null));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	//
	// resv = bookingManagement.getReservation(reservationID);
	//
	// // a reservation must be returned
	// assertNotNull("No reservation returned", resv);
	// // check if the right reservation was returned
	// assertEquals(reservationID, resv.getId());
	// assertEquals(userID, resv.getBookingUserId());
	// assertTrue(resv.getBookedFacilityIds().containsAll(place));
	// assertEquals(start, resv.getStartTime());
	// assertEquals(end, resv.getEndTime());
	// }
	//
	// /**
	// * Test find free facilites.
	// */
	// @Test
	// public void testFindFreeFacilites() {
	// // TODO findFreeFacilites needs a facility database to work
	// ArrayList<Property> propertiesList = new ArrayList<Property>();
	// for (String property : FIND_PROPERTY_NAMES) {
	// propertiesList.add(new Property(property));
	// }
	// FacilityQuery query = new FreeRoomQuery(propertiesList, SEARCH_TEXT, NEEDED_WORKPLACES);
	// Collection<FreeFacilityResult> result = null;
	// try {
	// // TODO throw error or return null if parameter is null
	// assertNull(bookingManagement.findFreeFacilites(null, start, end, false));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	// try {
	// assertNull(bookingManagement.findFreeFacilites(query, null, end, false));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	// try {
	// assertNull(bookingManagement.findFreeFacilites(query, start, null, false));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	//
	// result = bookingManagement.findFreeFacilites(query, start, end, false);
	//
	// // free facilities should be returned
	// assertNotNull("Result is null", result);
	// assertTrue("Resultlist is empty", result.size() > 0);
	// // check if right facilities are returned
	//
	// System.out.println("##FREE FACS");
	// for (FreeFacilityResult freeFacility : result) {
	// System.out.println(freeFacility.getFacility().getId());
	// }
	//
	// for (FreeFacilityResult freeFacility : result) {
	// // TODO search text unused in the facility construction, what needed
	// // for?
	// assertTrue("invalid facility returned", dummyFacilities.rooms.contains(freeFacility.getFacility()));
	// assertFalse("facility is in booking 1", place.contains(freeFacility.getFacility().getId())
	// && freeFacility.getStart().before(end));
	// assertFalse("facility is in booking 2", place3.contains(freeFacility.getFacility().getId())
	// && freeFacility.getStart().before(end));
	// // assertTrue(FACILITIES2.contains(freeFacility.getFacility().getId()));
	// }
	//
	// }
	//
	// /**
	// * Test is facility free.
	// *
	// * @throws FacilityNotFoundException
	// */
	// @Test
	// public void testIsFacilityFree() throws FacilityNotFoundException {
	// Date startUnused = new GregorianCalendar(2012, 0, 3, 9, 0).getTime();
	// Date endUnused = new GregorianCalendar(2012, 0, 3, 10, 0).getTime();
	// Date startUsedHalf = new GregorianCalendar(2012, 0, 2, 8, 0).getTime();
	// Date endUsedHalf = new GregorianCalendar(2012, 0, 2, 11, 0).getTime();
	// try {
	// // throw error or return null if parameter is null
	// assertNull(bookingManagement.isFacilityFree(null, start, end));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	// try {
	// assertNull(bookingManagement.isFacilityFree(place.get(0), null, end));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	// try {
	// assertNull(bookingManagement.isFacilityFree(place.get(0), start, null));
	// fail("Accepted wrong parameters.");
	// } catch (IllegalArgumentException e) {
	// System.out.println("Error: " + e);
	// }
	//
	// // different dates used
	// assertFalse("assert 1", bookingManagement.isFacilityFree(place.get(0), start, end));
	// assertFalse("assert 2", bookingManagement.isFacilityFree(place.get(1), start, end));
	// assertTrue("assert 3", bookingManagement.isFacilityFree(place.get(0), startUnused, endUnused));
	// assertTrue("assert 4", bookingManagement.isFacilityFree(place2.get(0), startUnused, endUnused));
	// assertTrue("assert 5", bookingManagement.isFacilityFree(place2.get(0), start, end));
	// assertFalse("assert 6", bookingManagement.isFacilityFree(place.get(0), start, endUnused));
	// assertTrue("assert 7", bookingManagement.isFacilityFree(place.get(0), end, startUnused));
	// assertFalse("assert 8", bookingManagement.isFacilityFree(place.get(0), startUsedHalf, endUsedHalf));
	// assertFalse("assert 9", bookingManagement.isFacilityFree(place.get(0), startUsedHalf, end));
	// assertFalse("assert 10", bookingManagement.isFacilityFree(place.get(0), start, endUsedHalf));
	// assertFalse("assert 11", bookingManagement.isFacilityFree(place.get(0), startUsedHalf, endUnused));
	// assertTrue("assert 12", bookingManagement.isFacilityFree(place.get(0), startUsedHalf, start));
	// assertTrue("assert 13", bookingManagement.isFacilityFree(place.get(0), end, endUsedHalf));
	// }
}
