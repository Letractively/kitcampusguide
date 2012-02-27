package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
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

	/** a room */
	private Room room;

	/**
	 * creates the data for the tests
	 */
	@Before
	public void createTestData() {
		// create the properties
		Property propertyWLAN = new Property("WLAN");
		Property propertyLAN = new Property("LAN");
		Property propertySteckdose = new Property("Steckdose");

		// create a building with one room containing one work place
		Building building = dataHelper.createPersistedBuilding("50.20", "Informatik", new ArrayList<Property>());
		room = dataHelper.createPersistedRoom("Seminarraum", "-101", -1,
				Arrays.asList(propertyWLAN, propertySteckdose, propertyLAN));
		Workplace wp1 = dataHelper.createPersistedWorkplace("Workplace 1", Arrays.asList(propertyLAN));
		Workplace wp2 = dataHelper.createPersistedWorkplace("Workplace 2", Arrays.asList(propertyLAN));
		Workplace wp3 = dataHelper.createPersistedWorkplace("Workplace 3", Arrays.asList(propertyLAN));
		room.addContainedFacility(wp1);
		room.addContainedFacility(wp2);
		building.addContainedFacility(room);
	}

	/**
	 * tests the setUpRoomDetails() method
	 */
	@Test
	public void testSetUpRoomDetails() {

		// create models
		Model model = new BindingAwareModelMap();
		SearchFormModel formModel = new SearchFormModel();
		SearchFilterModel filterModel = new SearchFilterModel();
		BookingFormModel bookingFormModel = new BookingFormModel();

		formModel.setWorkplaceCount(2);

		String view = roomDetailController.setUpRoomDetails(room.getId(), model, formModel, filterModel,
				bookingFormModel);
		assertEquals("room/details", view);

		Map<String, Object> modelAttributes = model.asMap();

		checkRoom(modelAttributes);

		checkAttributes(modelAttributes);

		checkTitleArguments(modelAttributes);

		checkWorkplaceList(modelAttributes, formModel);
	}

	/**
	 * checks on the workplace list attributes for the setupRoomDetails() method
	 * 
	 * @param modelAttributes
	 *            the model attributes containing the workplace list
	 * @param formModel
	 *            the original SearchFormModel
	 */
	private void checkWorkplaceList(Map<String, Object> modelAttributes, SearchFormModel formModel) {

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

		// TODO ceck workplaces / workplaceProps
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

}
