package edu.kit.pse.ass.gui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FacilityNotFreeException;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.gui.model.BookingFormModel;
import edu.kit.pse.ass.gui.model.CalendarParamModel;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RoomDetailController.
 */
@Controller
public class RoomDetailController extends MainController {

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	@Inject
	BookingManagement bookingManagement;

	/**
	 * Sets the up room details.
	 * 
	 * @param roomId
	 *            the room id
	 * @param model
	 *            the model
	 * @param sfm
	 *            the sfm
	 * @param searchFilterModel
	 *            the search filter model
	 * @return the string
	 * @throws FacilityNotFoundException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "room/{roomId}/details.html", method = { RequestMethod.GET })
	public String setUpRoomDetails(@PathVariable("roomId") String roomId,
			Model model, @ModelAttribute SearchFormModel sfm,
			@ModelAttribute SearchFilterModel searchFilterModel,
			@ModelAttribute BookingFormModel bfm)
			throws IllegalArgumentException, FacilityNotFoundException {
		setUpRoomDetailModel(model, roomId, sfm);
		return "room/details";
	}

	private void setUpRoomDetailModel(Model model, String roomId,
			SearchFormModel sfm) throws IllegalArgumentException,
			FacilityNotFoundException {
		Facility f = facilityManagement.getFacility(roomId);

		if (!(f instanceof Room)) {
			// TODO error!
		}

		Room room = (Room) f;
		model.addAttribute("room", room);

		listWorkplaces(model, room, sfm);
	}

	/**
	 * Book.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 * @throws FacilityNotFoundException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "room/{roomId}/details.html", method = { RequestMethod.POST })
	public String book(@PathVariable("roomId") String roomId, Model model,
			@ModelAttribute BookingFormModel bfm)
			throws IllegalArgumentException, FacilityNotFoundException {

		String returnedView = "room/details";

		setUpRoomDetailModel(model, roomId, new SearchFormModel());

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String userID = auth.getName();

		ArrayList<String> facilityIDs = new ArrayList<String>();

		if (bfm.isWholeRoom()) {
			facilityIDs.add(roomId);
		} else {
			for (String id : bfm.getWorkplaces()) {
				if (id != null) {
					facilityIDs.add(id);
				}
			}
		}

		if (facilityIDs.size() != 0) {
			try {
				bookingManagement.book(userID, facilityIDs, bfm.getStart(),
						bfm.getEnd());

				returnedView = "redirect:/reservation/list.html";
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FacilityNotFreeException e) {
				model.addAttribute("notFree", true);
			}
		} else {
			model.addAttribute("noFacilities", true);
		}

		return returnedView;

	}

	/**
	 * List workplaces.
	 * 
	 * @param model
	 *            the model
	 * @param room
	 *            the room
	 */
	private void listWorkplaces(Model model, Room room, SearchFormModel sfm) {
		Collection<Facility> workplaces = room.getContainedFacilities();

		boolean[] checked = new boolean[workplaces.size()];

		if (sfm.isWholeRoom()) {
			for (int i = 0; i < checked.length; i++) {
				checked[i] = false;
			}
		} else {
			int workplacesToSelect = sfm.getWorkplaceCount();
			for (int i = 0; i < checked.length; i++) {
				if (workplacesToSelect > 0) {
					// TODO: isFree, hasProps
					checked[i] = true;
					workplacesToSelect--;
				} else {
					checked[i] = false;
				}

			}
		}

		model.addAttribute("checked", checked);
		model.addAttribute("workplaces", workplaces);
	}

	/**
	 * Show bookable occupancy.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws FacilityNotFoundException
	 */
	@RequestMapping(value = "room/{roomId}/calendar.html")
	public void showBookableOccupancy(@PathVariable("roomId") String roomId,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute CalendarParamModel cpm)
			throws FacilityNotFoundException {

		Collection<Reservation> reservations = bookingManagement
				.listReservationsOfFacility(roomId, cpm.getStart(),
						cpm.getEnd());
		Room room = facilityManagement.getFacility(Room.class, roomId);

		// create JSON response
		try {
			JSONArray events = new JSONArray();

			for (Reservation r : reservations) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());
				o.put("start", r.getStartTime().getTime());
				o.put("end", r.getEndTime().getTime());
				o.put("title", "Raum besetzt");
				events.put(o);
			}

			for (Facility fac : room.getContainedFacilities()) {
				String title = "teilw. besetzt";
				if (fac instanceof Workplace) {
					String name = ((Workplace) fac).getName();
					if (name != null && !name.isEmpty()) {
						title = name + " besetzt";
					}
				}
				reservations = bookingManagement.listReservationsOfFacility(
						fac.getId(), cpm.getStart(), cpm.getEnd());
				for (Reservation r : reservations) {
					JSONObject o = new JSONObject();
					o.put("id", r.getId());
					o.put("start", r.getStartTime().getTime());
					o.put("end", r.getEndTime().getTime());
					o.put("title", title);
					o.put("color", "yellow");
					events.put(o);
				}
			}

			JSONObject jsonResponse = new JSONObject();

			jsonResponse.put("events", events);

			response.setContentType("application/json");
			response.getWriter().print(jsonResponse.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setContentType("text/html");
			// response.getWriter().print(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
