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
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.RoomQuery;
import edu.kit.pse.ass.realdata.DataHelper;

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
	private static final String USER_ID = "ubbbb@student.kit.edu";

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

	/** a date (in future, after end date) */
	private Date dateAfterEndDate;

	@Autowired
	private DataHelper dataHelper;

	/** a building that could be booked */
	private Building infoBuilding;
	/** a room that could be booked */
	private Room room1;

	private Workplace workplace1;
	private Workplace workplace2;

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

		cal.add(Calendar.HOUR, 1);
		// dateAfterEndDate is date + 3
		dateAfterEndDate = cal.getTime();
	}

	/**
	 * Creates a simple building with one room
	 */
	private void initTestBuildingWithRoomsAndWorkplaces() {
		infoBuilding = dataHelper.createPersistedBuilding("50.20", "Informatik", new ArrayList<Property>());
		room1 = dataHelper.createPersistedRoom("Seminarraum", "-101", -1, Arrays.asList(new Property("WLAN")));

		workplace1 = dataHelper.createPersistedWorkplace("WP1",
				Arrays.asList(new Property("LAN"), new Property("Lampe")));
		workplace2 = dataHelper.createPersistedWorkplace("WP2", Arrays.asList(new Property("LAN")));

		room1.addContainedFacility(workplace1);
		room1.addContainedFacility(workplace2);

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
	@Test(expected = IllegalDateException.class)
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
		// start is current date - 2
		Date invalidStartDate = cal.getTime();
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), invalidStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an BookingNotAllowedException if user has a Reservation.
	 * 
	 * @throws Exception
	 *             should be an BookingNotAllowedException
	 */
	@Test(expected = BookingNotAllowedException.class)
	public void testBookNotAllowed() throws Exception {
		dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate, validEndDate);
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), validStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an IllegalArgumentException if a facility id is invalid.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBookIllegalFacilityID() throws Exception {
		bookingManagement.book(USER_ID, Arrays.asList((String) null), validStartDate, validEndDate);
	}

	/**
	 * Tests if book throws an FacilityNotFoundException if a facility id does not exist.
	 * 
	 * @throws Exception
	 *             should be an FacilityNotFoundException
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testBookFacilityIDDoesNotExist() throws Exception {
		bookingManagement
				.book(USER_ID, Arrays.asList("some id that does not exist"), validStartDate, validEndDate);
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
	 * Tests if book fails id workplace is not free.
	 * 
	 * @throws Exception
	 *             a FacilityNotFreeException should be thrown
	 */
	@Test(expected = FacilityNotFreeException.class)
	public void testBookNotFree() throws Exception {
		// create a reservation
		dataHelper.createPersistedReservation("aUser", Arrays.asList(room1.getId()), validStartDate, validEndDate);
		// book should fail..
		bookingManagement.book(USER_ID, Arrays.asList(room1.getId()), validStartDate, validEndDate);
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

	/**
	 * Tests if changeReservationEnd throws an IllegalArgumentException if reservationID is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeReservationEndWithNullID() throws Exception {
		bookingManagement.changeReservationEnd(null, validEndDate);
	}

	/**
	 * Tests if changeReservationEnd throws an IllegalArgumentException if newEndDate is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeReservationEndWithNullDate() throws Exception {
		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate,
				validEndDate);

		bookingManagement.changeReservationEnd(res1.getId(), null);
	}

	/**
	 * Tests if changeReservationEnd throws an FacilityNotFreeException when facility is not free for new EndDate.
	 * 
	 * @throws Exception
	 *             should be an FacilityNotFreeException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeReservationEndNotFreeForNewEndDate() throws Exception {
		ArrayList<String> facilityIDs = new ArrayList<String>();
		facilityIDs.add(room1.getId());

		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, facilityIDs, validStartDate,
				validEndDate);
		// make second reservation, so the booked facility is not free after first reservation
		dataHelper.createPersistedReservation(USER_ID, facilityIDs, validEndDate, dateAfterEndDate);

		bookingManagement.changeReservationEnd(res1.getId(), dateAfterEndDate);
	}

	/**
	 * Tests if changeReservationEnd throws an IllegalStateException if not existing ID is used.
	 * 
	 * @throws Exception
	 *             should be an IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testChangeReservationEndWithNotExistingID() throws Exception {
		bookingManagement.changeReservationEnd("This is not a valid ID", validEndDate);
	}

	/**
	 * Tests if changeReservationEnd throws an IllegalArgumentException if the EndDate is before the StartDate.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeReservationEndWithStartDateAfterEndDate() throws Exception {
		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate,
				validEndDate);
		bookingManagement.changeReservationEnd(res1.getId(), validStartDate);
	}

	/**
	 * Tests changeReservationEnd it reducing end works.
	 * 
	 * @throws Exception
	 *             no exception should be thrown
	 */
	@Test
	public void testChangeReservationEndReduceEnd() throws Exception {

		Calendar cal = Calendar.getInstance();
		Date start = cal.getTime();
		cal.add(Calendar.HOUR, 4);
		Date end = cal.getTime();
		cal.add(Calendar.HOUR, -1);
		Date newEnd = cal.getTime();

		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), start, end);

		bookingManagement.changeReservationEnd(res1.getId(), newEnd);

		res1 = bookingManagement.getReservation(res1.getId());
		assertNotNull(res1);
		// check if reservation is changed successfully
		assertEquals(newEnd, res1.getEndTime());
		assertEquals(start, res1.getStartTime());
	}

	/**
	 * Tests changeReservationEnd it extending end works.
	 * 
	 * @throws Exception
	 *             no exception should be thrown
	 */
	@Test
	public void testChangeReservationEndExtendEnd() throws Exception {

		Calendar cal = Calendar.getInstance();
		Date start = cal.getTime();
		cal.add(Calendar.HOUR, 4);
		Date end = cal.getTime();
		cal.add(Calendar.HOUR, +1);
		Date newEnd = cal.getTime();

		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), start, end);

		bookingManagement.changeReservationEnd(res1.getId(), newEnd);

		res1 = bookingManagement.getReservation(res1.getId());
		assertNotNull(res1);
		// check if reservation is changed successfully
		assertEquals(newEnd, res1.getEndTime());
		assertEquals(start, res1.getStartTime());
	}

	/**
	 * Tests if removeFacilityFromReservation throws an IllegalArgumentException if reservationID is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveFacilityFromReservationWithNullID() throws Exception {
		bookingManagement.removeFacilityFromReservation(null, room1.getId());
	}

	/**
	 * Tests if removeFacilityFromReservation throws an IllegalArgumentException if facilityID is null.
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveFacilityFromReservationWithNullFacilityID() throws Exception {
		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate,
				validEndDate);
		bookingManagement.removeFacilityFromReservation(res1.getId(), null);
	}

	/**
	 * Test if removeFacilityFromReservation removes the right facility.
	 * 
	 * @throws Exception
	 *             no exception expected
	 */
	@Test
	public void testRemoveFacilityFromReservation() throws Exception {
		Reservation res1 = dataHelper.createPersistedReservation(USER_ID,
				Arrays.asList(infoBuilding.getId(), room1.getId()), validStartDate, validEndDate);

		bookingManagement.removeFacilityFromReservation(res1.getId(), room1.getId());

		res1 = bookingManagement.getReservation(res1.getId());
		assertNotNull(res1);
		assertEquals(1, res1.getBookedFacilityIds().size());
		assertTrue(res1.getBookedFacilityIds().contains(infoBuilding.getId()));
	}

	/**
	 * Test if deleteReservation throws an IllegalArgumentException with null reservationID
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteReservationWithNullID() throws Exception {
		bookingManagement.deleteReservation(null);
	}

	/**
	 * Tests if deleteReservation deletes the right reservation.
	 * 
	 * @throws ReservationNotFoundException
	 *             should be thrown
	 * 
	 */
	@Test(expected = ReservationNotFoundException.class)
	public void testDeleteReservation() throws ReservationNotFoundException {
		// TODO: add a second res that sould be let untouched
		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate,
				validEndDate);
		assertNotNull(res1.getId());
		String id = res1.getId();
		bookingManagement.deleteReservation(res1.getId());

		res1 = bookingManagement.getReservation(id);
		assertTrue(res1 == null);
	}

	/**
	 * Test if getReservation throws an IllegalArgumentException with null reservationID
	 * 
	 * @throws Exception
	 *             should be an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetReservationWithNullID() throws Exception {
		bookingManagement.getReservation(null);
	}

	/**
	 * Tests if getReservation returns the right reservation.
	 * 
	 * @throws ReservationNotFoundException
	 *             should not be thrown
	 */
	@Test
	public void testGetReservation() throws ReservationNotFoundException {
		Reservation res1 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate,
				validEndDate);
		Reservation res2 = dataHelper.createPersistedReservation(USER_ID, new ArrayList<String>(), validStartDate,
				validEndDate);

		assertNotNull(res1.getId());
		assertNotNull(res2.getId());

		// Check if second reservation is returned
		Reservation res2Result = bookingManagement.getReservation(res2.getId());
		assertNotNull(res2Result);
		assertEquals(res2, res2Result);
	}

	/**
	 * Tests if findFreeFacility throws a IllegalArgumentException with null query.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindFreeFacilitiesWithNullQuery() {
		bookingManagement.findFreeFacilites(null, validStartDate, validEndDate, true);
	}

	/**
	 * Tests if findFreeFacility throws a IllegalArgumentException with null start date.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindFreeFacilitiesWithNullStart() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), SEARCH_TEXT, NEEDED_WORKPLACES);
		bookingManagement.findFreeFacilites(query, null, validEndDate, true);
	}

	/**
	 * Tests if findFreeFacility throws a IllegalArgumentException with null end date.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindFreeFacilitiesWithNullEnd() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), SEARCH_TEXT, NEEDED_WORKPLACES);
		bookingManagement.findFreeFacilites(query, validStartDate, null, true);
	}

	/**
	 * Tests if findFreeFacility throws a IllegalArgumentException with start date before end date.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindFreeFacilitiesWithStartAfterEnd() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), SEARCH_TEXT, NEEDED_WORKPLACES);
		bookingManagement.findFreeFacilites(query, validEndDate, validStartDate, true);
	}

	/**
	 * Tests if findFreeFacility throws a IllegalArgumentException with start date in the past.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindFreeFacilitiesWithIllegalStart() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), SEARCH_TEXT, NEEDED_WORKPLACES);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -2);
		// start is current date - 2
		Date invalidStartDate = cal.getTime();
		bookingManagement.findFreeFacilites(query, invalidStartDate, validEndDate, true);
	}

	/**
	 * Tests if findFreeFacility throws a IllegalArgumentException with end date in the past.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindFreeFacilitiesWithIllegalEnd() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), SEARCH_TEXT, NEEDED_WORKPLACES);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -2);
		// end is current date - 2
		Date invalidEndDate = cal.getTime();
		bookingManagement.findFreeFacilites(query, validStartDate, invalidEndDate, true);
	}

	/**
	 * Tests if findFreeFacility finds the 2 workplaces.
	 */
	@Test
	public void testFindFreeFacility2Workplaces() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), "", 2);
		Collection<FreeFacilityResult> result;
		result = bookingManagement.findFreeFacilites(query, validStartDate, validEndDate, false);

		// should find the one room with the two workplaces.
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(validStartDate, result.iterator().next().start);
	}

	/**
	 * Tests if findFreeFacility finds the 2 workplaces when room must be fully available.
	 */
	@Test
	public void testFindFreeFacility2WorkplacesFullyAvailible() {
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), "", 2);
		Collection<FreeFacilityResult> result;
		result = bookingManagement.findFreeFacilites(query, validStartDate, validEndDate, true);

		// should find the one room with the two workplaces.
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(validStartDate, result.iterator().next().start);
	}

	/**
	 * Tests if findFreeFacility finds the 2 workplaces if they are booked, but free in 15min.
	 */
	@Test
	public void testFindFreeFacility2WorkplacesNext15() {
		Calendar cal = Calendar.getInstance();

		// create a reservation for 15 miniutes for workplace 1 so it is
		// just bookable in 15 minitues
		cal.add(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 15);

		Date bookingStart = cal.getTime();

		cal.add(Calendar.MINUTE, 15);
		Date bookingEnd = cal.getTime();

		// book one workplace of the two:
		dataHelper.createPersistedReservation("aID", Arrays.asList(workplace1.getId()), bookingStart, bookingEnd);

		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), "", 2);
		Collection<FreeFacilityResult> result;
		result = bookingManagement.findFreeFacilites(query, bookingStart, bookingEnd, false);

		// should find the one room with the two workplaces.
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(bookingEnd, result.iterator().next().start);
	}

	/**
	 * Tests if findFreeFacility finds not the workplaces if they are not fully available.
	 */
	@Test
	public void testFindFreeFacility2WorkplacesNotFullyAvailible() {
		// get current date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		// start is current date + 1
		Date startDate = cal.getTime();

		cal.add(Calendar.HOUR, 24);
		Date reservationEndDate = cal.getTime();

		cal.add(Calendar.HOUR, -23);
		Date findEndDate = cal.getTime();

		dataHelper.createPersistedReservation("aID", Arrays.asList(workplace1.getId()), startDate,
				reservationEndDate);

		// search for 2 workplaces
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), "", 2);
		Collection<FreeFacilityResult> result;
		result = bookingManagement.findFreeFacilites(query, startDate, findEndDate, true);

		// should find no room because room1 is not fullyAvailible
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Tests if findFreeFacility finds not the workplaces if they are not available.
	 */
	@Test
	public void testFindFreeFacilityWorkplacesNotAvailible() {
		// get current date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		// start is current date + 1
		Date startDate = cal.getTime();

		cal.add(Calendar.HOUR, 24);
		Date reservationEndDate = cal.getTime();

		cal.add(Calendar.HOUR, -23);
		Date findEndDate = cal.getTime();

		dataHelper.createPersistedReservation("aID", Arrays.asList(workplace1.getId(), workplace2.getId()),
				startDate, reservationEndDate);

		// search for 1 workplace
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), "", 1);
		Collection<FreeFacilityResult> result;
		result = bookingManagement.findFreeFacilites(query, startDate, findEndDate, false);

		// should find no room because workplace1 and workplace 2 are not available
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Tests if findFreeFacility finds not the room if the room is not availible.
	 */
	@Test
	public void testFindFreeFacilityRoomNotAvailible() {
		// get current date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		// start is current date + 1
		Date startDate = cal.getTime();

		cal.add(Calendar.HOUR, 24);
		Date reservationEndDate = cal.getTime();

		cal.add(Calendar.HOUR, -23);
		Date findEndDate = cal.getTime();

		dataHelper.createPersistedReservation("aID", Arrays.asList(room1.getId()), startDate, reservationEndDate);

		// search for one workplace in room1
		RoomQuery query = new RoomQuery(Arrays.asList(new Property("WLAN")), "", 1);
		Collection<FreeFacilityResult> result;
		result = bookingManagement.findFreeFacilites(query, startDate, findEndDate, false);

		// should find no room because room is not available
		assertNotNull(result);
		assertEquals(0, result.size());
	}

}
