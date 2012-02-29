package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.gui.controller.RoomDetailController;
import edu.kit.pse.ass.gui.model.BookingFormModel;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;
import edu.kit.pse.ass.realdata.DataHelper;

/**
 * Tests for the Room Detail Controller
 * 
 * @author Jannis Koch
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@Transactional
public class RoomDetailControllerTest {

	/** the Room Detail controller */
	@Autowired
	private RoomDetailController roomDetailController;

	/** data helper for creating test data */
	@Autowired
	private DataHelper dataHelper;

	/** the user id of user 1 */
	private static final String USERID1 = "ubbbb@student.kit.edu";

	/** the user id of user 2 */
	private static final String USERID2 = "ucccc@student.kit.edu";

	/** a room */
	private Room room;

	/** three workplaces in that room */
	private Workplace wp1, wp2, wp3;

	/**
	 * creates the data for the tests
	 */
	@Before
	public void createTestData() {
		createFacilities();
		createUser();
		setSecurityContext();
	}

	/**
	 * creates the facilities for the tests
	 */
	private void createFacilities() {

		// create the properties
		Property propertyWLAN = new Property("WLAN");
		Property propertyLAN = new Property("LAN");
		Property propertySteckdose = new Property("Steckdose");
		Property propertyBarrierefrei = new Property("Barrierefrei");

		// create a building with one room containing one work place
		Building building = dataHelper.createPersistedBuilding("50.20", "Informatik", new ArrayList<Property>());
		room = dataHelper.createPersistedRoom("Seminarraum", "-101", -1,
				Arrays.asList(propertyWLAN, propertySteckdose));
		wp1 = dataHelper.createPersistedWorkplace("Workplace 1", Arrays.asList(propertyBarrierefrei));
		wp2 = dataHelper.createPersistedWorkplace("Workplace 2", Arrays.asList(propertyLAN));
		wp3 = dataHelper.createPersistedWorkplace("Workplace 3", Arrays.asList(propertyLAN));
		room.addContainedFacility(wp1);
		room.addContainedFacility(wp2);
		room.addContainedFacility(wp3);
		building.addContainedFacility(room);
	}

	/**
	 * creates a persisted user for the tests
	 */
	private void createUser() {
		dataHelper.createPersistedUser(USERID1, "bbbbbb", new HashSet<String>());
	}

	/**
	 * sets the username in the security context
	 */
	private void setSecurityContext() {

		Authentication auth = new UsernamePasswordAuthenticationToken(USERID1, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * tests the setUpRoomDetails() method with a search query for 2 workplaces
	 */
	@Test
	public void testSetUpRoomDetails() {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();

		formModel.setWorkplaceCount(2);
		filterModel.setFilters(new ArrayList<Property>());

		String view = roomDetailController.setUpRoomDetails(room.getId(), model, formModel, filterModel,
				bookingFormModel);
		assertEquals("room/details", view);

		Map<String, Object> modelAttributes = model.asMap();

		checkRoom(modelAttributes);

		checkAttributes(modelAttributes);

		checkTitleArguments(modelAttributes);

		checkWorkplaceList(modelAttributes, formModel, filterModel);
	}

	/**
	 * tests the setUpRoomDetails() method with a search query for a whole room
	 */
	@Test
	public void testSetUpRoomDetailsWholeRoom() {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();

		formModel.setWorkplaceCount(2);
		formModel.setWholeRoom(true);

		String view = roomDetailController.setUpRoomDetails(room.getId(), model, formModel, filterModel,
				bookingFormModel);
		assertEquals("room/details", view);

		Map<String, Object> modelAttributes = model.asMap();

		checkRoom(modelAttributes);

		checkAttributes(modelAttributes);

		checkTitleArguments(modelAttributes);

		checkWorkplaceListWholeRoom(modelAttributes, formModel, filterModel);
	}

	/**
	 * tests the setUpRoomDetails() method with a room id that does not exist
	 */
	@Test
	public void testSetUpRoomDetailsWrongRoomId() {
		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();

		String view;

		// test wrong id
		view = roomDetailController.setUpRoomDetails("wrongid", model, formModel, filterModel, bookingFormModel);
		assertEquals("error", view);

		// test empty id
		view = roomDetailController.setUpRoomDetails("", model, formModel, filterModel, bookingFormModel);
		assertEquals("error", view);

		// test workplace id
		Facility wp = room.getContainedFacilities().iterator().next();
		view = roomDetailController.setUpRoomDetails(wp.getId(), model, formModel, filterModel, bookingFormModel);
		assertEquals("error", view);

		// test building id
		Facility building = room.getParentFacility();
		view = roomDetailController.setUpRoomDetails(building.getId(), model, formModel, filterModel,
				bookingFormModel);
		assertEquals("error", view);
	}

	/**
	 * checks on the workplace list attributes for the setupRoomDetails() method
	 * 
	 * @param modelAttributes
	 *            the model attributes containing the workplace list
	 * @param formModel
	 *            the original SearchFormModel
	 * @param filterModel
	 *            the original SearchFilterModel
	 */
	private void checkWorkplaceList(Map<String, Object> modelAttributes, SearchFormModel formModel,
			SearchFilterModel filterModel) {

		// check worklace list
		assertTrue(modelAttributes.get("checked") instanceof boolean[]);
		boolean[] checked = (boolean[]) modelAttributes.get("checked");
		assertTrue(modelAttributes.get("workplaces") instanceof Collection<?>);
		Collection<?> workplaces = (Collection<?>) modelAttributes.get("workplaces");
		assertTrue(modelAttributes.get("workplacesProps") instanceof Collection<?>[]);
		Collection<?>[] workplacesProps = (Collection<?>[]) modelAttributes.get("workplacesProps");

		assertEquals(room.getContainedFacilities().size(), checked.length);

		// check if the right number of workplaces is checked
		int totalChecked = 0;
		for (boolean b : checked) {
			if (b) {
				totalChecked++;
			}
		}
		assertEquals(formModel.getWorkplaceCount(), totalChecked);

		// check workplaces and its properties
		int i = 0;
		for (Object o : workplaces) {
			assertTrue(o instanceof Workplace);
			Workplace wp = (Workplace) o;
			if (checked[i]) {
				// this wp is selected - check if workplace contains all filter properties
				wp.getInheritedProperties().containsAll(filterModel.getFilters());
			}
			// check if every workplace has one additional property, as has the test data
			assertEquals(1, workplacesProps[i].size());
			i++;
		}

	}

	/**
	 * checks on the workplace list attributes for the setupRoomDetails() method
	 * 
	 * @param modelAttributes
	 *            the model attributes containing the workplace list
	 * @param formModel
	 *            the original SearchFormModel
	 * @param filterModel
	 *            the original SearchFilterModel
	 */
	private void checkWorkplaceListWholeRoom(Map<String, Object> modelAttributes, SearchFormModel formModel,
			SearchFilterModel filterModel) {

		// check worklace list
		assertTrue(modelAttributes.get("checked") instanceof boolean[]);
		boolean[] checked = (boolean[]) modelAttributes.get("checked");
		assertTrue(modelAttributes.get("workplaces") instanceof Collection<?>);
		Collection<?> workplaces = (Collection<?>) modelAttributes.get("workplaces");
		assertTrue(modelAttributes.get("workplacesProps") instanceof Collection<?>[]);

		assertEquals(room.getContainedFacilities().size(), checked.length);

		// check if no workplace is checked
		for (boolean b : checked) {
			assertFalse(b);
		}

		// check if workplaces collections contains workplaces only
		for (Object o : workplaces) {
			assertTrue(o instanceof Workplace);
		}
	}

	/**
	 * checks the title arguments in the given model attributes
	 * 
	 * @param modelAttributes
	 *            the model attributes
	 */
	private void checkTitleArguments(Map<String, Object> modelAttributes) {

		assertTrue(modelAttributes.get("titleArguments") instanceof String[]);
		String[] titleArguments = (String[]) modelAttributes.get("titleArguments");
		assertEquals(2, titleArguments.length);
		assertEquals(room.getName(), titleArguments[0]);
		assertEquals(room.getParentFacility().getName(), titleArguments[1]);
	}

	/**
	 * checks on the given model attributes
	 * 
	 * @param modelAttributes
	 *            the model attributes to check
	 */
	private void checkAttributes(Map<String, Object> modelAttributes) {
		assertTrue("model does not contain additional js files", modelAttributes.containsKey("jsFiles"));
		assertTrue("model does not contain additional css files", modelAttributes.containsKey("cssFiles"));
		assertTrue("no icons are set", modelAttributes.containsKey("icons"));
		assertTrue("no filters are set", modelAttributes.containsKey("filters"));

	}

	/**
	 * checks the room object of the given model attributes
	 * 
	 * @param modelAttributes
	 *            the model attributes
	 */
	private void checkRoom(Map<String, Object> modelAttributes) {
		assertTrue(modelAttributes.get("room") instanceof Room);
		Room detailsRoom = (Room) modelAttributes.get("room");
		assertEquals(room.getDescription(), detailsRoom.getDescription());
		assertEquals(room.getId(), detailsRoom.getId());
		assertEquals(room.getLevel(), detailsRoom.getLevel());
		assertEquals(room.getName(), detailsRoom.getName());
		assertEquals(room.getNumber(), detailsRoom.getNumber());
		assertEquals(room.getParentFacility().getId(), detailsRoom.getParentFacility().getId());

	}

	/**
	 * tests the book method() booking workplaces
	 * 
	 * @throws FacilityNotFoundException
	 *             if the facility was not found
	 */
	@Test
	public void testBookWorkplaces() throws FacilityNotFoundException {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		// Select two workplaces in the booking form model
		bookingFormModel.addWorkplace(wp1.getId());
		bookingFormModel.addWorkplace(wp2.getId());

		// Book
		bookAndExpectSuccess(room.getId(), model, bookingFormModel, bookingFormModelResult, formModel, filterModel);

		// Try to book wp3 and expect "user has booking at that time" error
		bookingFormModel = new BookingFormModel();
		bookingFormModel.addWorkplace(wp3.getId());
		bookAndExpectErrorCode("book.error.hasBookingAtTime", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);

		// Try to book room and expect "user has booking at that time" error
		bookingFormModel.setWholeRoom(true);
		bookingFormModel = new BookingFormModel();
		bookAndExpectErrorCode("book.error.hasBookingAtTime", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);
	}

	/**
	 * tests the book method() booking a whole room
	 * 
	 * @throws FacilityNotFoundException
	 *             if the facility was not found
	 */
	@Test
	public void testBook() throws FacilityNotFoundException {

		// use another day with no bookings!
		Date anotherDay = addDaysToCurrentDate(1);

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		// Book
		bookingFormModel.setWholeRoom(true);
		bookingFormModel.setStart(anotherDay);
		formModel.setStart(anotherDay);
		bookAndExpectSuccess(room.getId(), model, bookingFormModel, bookingFormModelResult, formModel, filterModel);

		// Try to book wp3 and expect "user has booking at that time" error
		bookingFormModel = new BookingFormModel();
		bookingFormModel.setStart(anotherDay);
		bookingFormModel.addWorkplace(wp3.getId());
		bookAndExpectErrorCode("book.error.hasBookingAtTime", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);

		// Try to book room and expect "user has booking at that time" error
		bookingFormModel.setWholeRoom(true);
		bookingFormModel = new BookingFormModel();
		bookAndExpectErrorCode("book.error.hasBookingAtTime", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);

	}

	/**
	 * tests the book method() trying to book occupied facilities
	 * 
	 * @throws FacilityNotFoundException
	 *             if the facility was not found
	 */
	@Test
	public void testBookOccupiedFacilities() throws FacilityNotFoundException {

		// use another day with no bookings!
		Date anotherDay = addDaysToCurrentDate(2);

		createReservationsOfAnotherUser(anotherDay);

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		// Book room expect not free error
		bookingFormModel.setWholeRoom(true);
		bookingFormModel.setStart(anotherDay);
		formModel.setStart(anotherDay);
		bookAndExpectErrorCode("book.error.roomNotFree", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);

		// Book workplace expect not free error
		bookingFormModel = new BookingFormModel();
		bookingFormModel.addWorkplace(wp1.getId());
		bookingFormModel.setStart(anotherDay);
		bookAndExpectErrorCode("book.error.roomNotFree", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);

	}

	/**
	 * tests the book method() using a wrong date
	 * 
	 * @throws FacilityNotFoundException
	 *             if the facility was not found
	 */
	@Test
	public void testBookWrongDate() throws FacilityNotFoundException {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		// Book in the past and expect error
		Date dateInThePast = addDaysToCurrentDate(-10);
		formModel.setStart(dateInThePast);
		bookingFormModel.setStart(dateInThePast);
		bookingFormModel.setWholeRoom(true);
		bookAndExpectErrorCode("book.error.illegalDate", room.getId(), model, bookingFormModel,
				bookingFormModelResult, formModel, filterModel);

	}

	/**
	 * create reservations for user2 for testBookOccupiedFacilities()
	 * 
	 * @param date
	 */
	private void createReservationsOfAnotherUser(Date start) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.HOUR, 2);
		Date end = calendar.getTime();

		ArrayList<String> facilities = new ArrayList<String>();
		for (Facility f : room.getContainedFacilities()) {
			facilities.add(f.getId());
		}

		dataHelper.createPersistedReservation(USERID2, facilities, start, end);

	}

	/**
	 * tries to make a booking with for the given room id using the given models and expects the given error code
	 * 
	 * @param expectedCode
	 *            the expected error code
	 * @param roomId
	 *            the id of the room to book
	 * @param model
	 *            the model
	 * @param bookingFormModel
	 *            the booking form model
	 * @param bookingFormModelResult
	 *            the result for the booking form model
	 * @param formModel
	 *            the search form model
	 * @param filterModel
	 *            the search filter model
	 * @throws FacilityNotFoundException
	 *             if the room with the given id was not found
	 */
	private void bookAndExpectErrorCode(String expectedCode, String roomId, Model model,
			BookingFormModel bookingFormModel, BindingResult bookingFormModelResult, SearchFormModel formModel,
			SearchFilterModel filterModel) throws FacilityNotFoundException {
		String view = roomDetailController.book(roomId, model, bookingFormModel, bookingFormModelResult,
				formModel, filterModel);
		// booking unsuccessful - expect to stay on room detail page
		assertEquals("room/details", view);

		// get list with all error fields concerned
		ArrayList<String> errorCodes = new ArrayList<String>();
		for (ObjectError error : bookingFormModelResult.getAllErrors()) {
			String code = error.getCode();
			errorCodes.add(code);
		}
		assertTrue(errorCodes.contains(expectedCode));
	}

	/**
	 * tries to make a booking with for the given room id using the given models and expects success
	 * 
	 * @param roomId
	 *            the id of the room to book
	 * @param model
	 *            the model
	 * @param bookingFormModel
	 *            the booking form model
	 * @param bookingFormModelResult
	 *            the result for the booking form model
	 * @param formModel
	 *            the search form model
	 * @param filterModel
	 *            the search filter model
	 * @throws FacilityNotFoundException
	 *             if the room with the given id was not found
	 */
	private void bookAndExpectSuccess(String roomId, Model model, BookingFormModel bookingFormModel,
			BindingResult bookingFormModelResult, SearchFormModel formModel, SearchFilterModel filterModel)
			throws FacilityNotFoundException {
		String view = roomDetailController.book(roomId, model, bookingFormModel, bookingFormModelResult,
				formModel, filterModel);
		// booking successful - expect to be redirected to reservation list
		assertEquals("redirect:/reservation/list.html", view);
	}

	// TODO book whole room, room not free, workplace not free, user has booking, illegal date, no workplaces selected

	/**
	 * tests the book method() using an empty room id
	 * 
	 * @throws FacilityNotFoundException
	 *             if a facility was not found
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testBookEmptyRoomId() throws FacilityNotFoundException {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		roomDetailController.book("", model, bookingFormModel, bookingFormModelResult, formModel, filterModel);

	}

	/**
	 * tests the book method() using a wrong room id
	 * 
	 * @throws FacilityNotFoundException
	 *             if a facility was not found
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testBookWrongRoomId() throws FacilityNotFoundException {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		roomDetailController.book("wrongid", model, bookingFormModel, bookingFormModelResult, formModel,
				filterModel);

	}

	/**
	 * tests the book method() using a workplace instead of a room id
	 * 
	 * @throws FacilityNotFoundException
	 *             if a facility was not found
	 */
	@Test(expected = FacilityNotFoundException.class)
	public void testBookWorkplaceId() throws FacilityNotFoundException {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();
		BindingResult bookingFormModelResult = new BeanPropertyBindingResult(bookingFormModel, "bookingFormModel");

		roomDetailController.book(wp1.getId(), model, bookingFormModel, bookingFormModelResult, formModel,
				filterModel);

	}

	/**
	 * adds the given number of days to the date of today and returns the date object
	 * 
	 * @param days
	 *            the number of days to add
	 * @return the date object of the date
	 */
	private Date addDaysToCurrentDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

}
