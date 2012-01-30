package edu.kit.pse.ass.gui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.BookingNotAllowedException;
import edu.kit.pse.ass.booking.management.FacilityNotFreeException;
import edu.kit.pse.ass.booking.management.IllegalDateException;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.gui.layout.PropertyIconMap;
import edu.kit.pse.ass.gui.model.BookingFormModel;
import edu.kit.pse.ass.gui.model.CalendarParamModel;
import edu.kit.pse.ass.gui.model.Event;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;

/**
 * The Class RoomDetailController.
 */
@Controller
public class RoomDetailController extends MainController {

	/** The facility management. */
	@Inject
	private FacilityManagement facilityManagement;

	/** The booking management. */
	@Inject
	private BookingManagement bookingManagement;

	/**
	 * This method is called to display the RoomDetailsPage.
	 * 
	 * @param roomId
	 *            the room id to display details of
	 * @param model
	 *            the spring model
	 * @param searchFormModel
	 *            the SearchFormModel filled at SearchPage
	 * @param searchFilterModel
	 *            the SearchFilterModel filled at SearchPage
	 * @param bookingFormModel
	 *            the BookingFormModel if filled at RoomDetailsPage
	 * @return the view path
	 */
	@RequestMapping(value = "room/{roomId}/details.html", method = { RequestMethod.GET })
	public String setUpRoomDetails(@PathVariable("roomId") String roomId, Model model,
			@ModelAttribute SearchFormModel searchFormModel, @ModelAttribute SearchFilterModel searchFilterModel,
			@ModelAttribute BookingFormModel bookingFormModel) {
		String returnedView = setUpRoomDetailModel(model, roomId, searchFormModel, searchFilterModel);
		return returnedView;
	}

	/**
	 * This method is called when the BookingForm at the RoomDetailsPage was filled and submitted to the server.
	 * 
	 * @param roomId
	 *            the room id to display details of
	 * @param model
	 *            the spring model
	 * @param bookingFormModel
	 *            the BookingFormModel if filled at RoomDetailsPage
	 * @param bookingFormModelResult
	 *            result for error binding while booking
	 * @param searchFormModel
	 *            the SearchFormModel filled at AdvanceSearchPage
	 * @param searchFilterModel
	 *            the SearchFilterModel filled at AdvanceSearchPage
	 * @return the view path
	 * @throws FacilityNotFoundException
	 *             to be handled by general error page
	 * @throws IllegalStateException
	 *             to be handled by general error page
	 * @throws IllegalArgumentException
	 *             to be handled by general error page
	 */
	@RequestMapping(value = "room/{roomId}/details.html", method = { RequestMethod.POST })
	public String book(@PathVariable("roomId") String roomId, Model model,
			@ModelAttribute BookingFormModel bookingFormModel, BindingResult bookingFormModelResult,
			@ModelAttribute SearchFormModel searchFormModel, @ModelAttribute SearchFilterModel searchFilterModel)
			throws IllegalArgumentException, IllegalStateException, FacilityNotFoundException {

		String returnedView = setUpRoomDetailModel(model, roomId, searchFormModel, searchFilterModel);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName();

		ArrayList<String> facilityIDs = new ArrayList<String>();

		if (bookingFormModel.isWholeRoom()) {
			facilityIDs.add(roomId);
		} else {
			for (String id : bookingFormModel.getWorkplaces()) {
				if (id != null) {
					facilityIDs.add(id);
				}
			}
		}

		if (facilityIDs.size() != 0) {
			try {
				bookingManagement
						.book(userID, facilityIDs, bookingFormModel.getStart(), bookingFormModel.getEnd());
				returnedView = "redirect:/reservation/list.html";
			} catch (FacilityNotFreeException e) {
				if (bookingFormModel.isWholeRoom()) {
					bookingFormModelResult.reject("book.error.roomNotFree", e.getMessage());
				} else {
					bookingFormModelResult.reject("book.error.workplaceNotFree", e.getMessage());
				}
			} catch (BookingNotAllowedException e) {
				bookingFormModelResult.reject("book.error.hasBookingAtTime", e.getMessage());
			} catch (IllegalDateException e) {
				bookingFormModelResult.reject("book.error.illegalDate", e.getMessage());
			}
		} else {
			bookingFormModelResult.reject("book.error.noWorkplacesSelected", "No workplaces selected.");
		}

		return returnedView;

	}

	/**
	 * Helper Method to set the needed model data of the RoomDetailsPage.
	 * 
	 * @param model
	 *            the spring Model
	 * @param roomId
	 *            the rooms ID to display
	 * @param searchFormModel
	 *            the SearchFormModel filled at the SearchPage
	 */
	private String setUpRoomDetailModel(Model model, String roomId, SearchFormModel searchFormModel,
			SearchFilterModel searchFilterModel) {
		String returnedView = "room/details";
		try {
			Room room = facilityManagement.getFacility(Room.class, roomId);
			model.addAttribute("room", room);
			// Pass room and building name as arguments for page title
			model.addAttribute("titleArguments",
					new String[] { room.getName(), room.getParentFacility().getName() });
			setUpWorkplaceList(model, room, searchFormModel, searchFilterModel);

			// Additional CSS / JS files
			String[] cssFiles = { "/libs/datatables/css/datatable.css", "/libs/datatables/css/datatable_jui.css",
					"/libs/datatables/themes/base/jquery-ui.css" };
			String[] jsFiles = { "/libs/datatables/jquery.dataTables.min.js", "/scripts/roomDetails.js" };
			model.addAttribute("cssFiles", cssFiles);
			model.addAttribute("jsFiles", jsFiles);

			model.addAttribute("icons", new PropertyIconMap());
			model.addAttribute("filters", searchFilterModel.getFilters());

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			returnedView = handleIllegalRequest(e);
		} catch (FacilityNotFoundException e) {
			e.printStackTrace();
			returnedView = handleIllegalRequest(e);
		}
		return returnedView;
	}

	/**
	 * Set up the workplaces list.
	 * 
	 * @param model
	 *            the spring model
	 * @param room
	 *            the Room to list workplaces of
	 * @param searchFormModel
	 *            the SearchFormModel filled at the SearchPage
	 */
	@SuppressWarnings("unchecked")
	private void setUpWorkplaceList(Model model, Room room, SearchFormModel searchFormModel,
			SearchFilterModel searchFilterModel) {

		Collection<Facility> workplaces = room.getContainedFacilities();

		if (searchFilterModel.getFilters() == null) {
			searchFilterModel.setFilters(new ArrayList<Property>());
		}
		try {
			boolean[] checked = new boolean[workplaces.size()];
			Collection<Property>[] workplaceProps = new Collection[workplaces.size()];

			if (searchFormModel.isWholeRoom()) {
				for (int i = 0; i < checked.length; i++) {
					checked[i] = false;
				}
			} else {
				int workplacesToSelect = searchFormModel.getWorkplaceCount();

				Iterator<Facility> workplaceIter = workplaces.iterator();

				for (int i = 0; i < checked.length; i++) {
					Facility f = workplaceIter.next();

					workplaceProps[i] = getExtraProperties(room, (Workplace) f);

					if (workplacesToSelect > 0) {
						boolean isFree = bookingManagement.isFacilityFree(f.getId(), searchFormModel.getStart(),
								searchFormModel.getEnd());
						if (isFree && f.hasInheritedProperties(searchFilterModel.getFilters())) {
							checked[i] = true;
							workplacesToSelect--;
						}

					} else {
						checked[i] = false;
					}
				}
			}

			model.addAttribute("checked", checked);
			model.addAttribute("workplaces", workplaces);
			model.addAttribute("workplacesProps", workplaceProps);
		} catch (FacilityNotFoundException e) {
			e.printStackTrace();
		}

	}

	private Collection<Property> getExtraProperties(Room room, Workplace workplace) {

		Collection<Property> roomProperties = room.getInheritedProperties();
		Collection<Property> workplaceProperties = new ArrayList<Property>(workplace.getProperties());

		Iterator<Property> propIter = workplaceProperties.iterator();
		while (propIter.hasNext()) {
			if (roomProperties.contains(propIter.next())) {
				propIter.remove();
			}
		}
		return workplaceProperties;
	}

	/**
	 * JSON return for the occupancy of the room
	 * 
	 * @param roomId
	 *            the room id to show occupancy of
	 * @param calendarParamModel
	 *            the Parameters of the jQuery calendar
	 * @return JSON response: a list of events to display
	 */
	@RequestMapping(value = "room/{roomId}/calendar.html")
	public @ResponseBody
	Collection<Event> showBookableOccupancy(@PathVariable("roomId") String roomId,
			@ModelAttribute CalendarParamModel calendarParamModel) {

		ArrayList<Event> events = new ArrayList<Event>();

		Collection<Reservation> reservations = bookingManagement.listReservationsOfFacility(roomId,
				calendarParamModel.getStart(), calendarParamModel.getEnd());
		try {
			Room room = facilityManagement.getFacility(Room.class, roomId);

			for (Reservation r : reservations) {
				events.add(new Event(r.getId(), r.getStartTime(), r.getEndTime(), "Raum besetzt", true));
			}

			for (Facility fac : room.getContainedFacilities()) {
				String title = "teilw. besetzt";
				if (fac instanceof Workplace) {
					String name = ((Workplace) fac).getName();
					if (name != null && !name.isEmpty()) {
						title = name + " besetzt";
					}
				}
				reservations = bookingManagement.listReservationsOfFacility(fac.getId(),
						calendarParamModel.getStart(), calendarParamModel.getEnd());
				for (Reservation r : reservations) {
					events.add(new Event(r.getId() + fac.getId(), r.getStartTime(), r.getEndTime(), title, false));
				}
			}

		} catch (FacilityNotFoundException e) {
			e.printStackTrace();
		}

		return events;
	}
}
