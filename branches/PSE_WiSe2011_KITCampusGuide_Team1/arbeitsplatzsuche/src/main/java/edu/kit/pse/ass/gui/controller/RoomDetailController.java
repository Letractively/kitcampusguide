package edu.kit.pse.ass.gui.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FacilityNotFreeException;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityManagement;
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
	 */
	@RequestMapping(value = "room/{roomId}/details.html")
	public String setUpRoomDetails(@PathVariable("roomId") String roomId,
			Model model, @ModelAttribute SearchFormModel sfm,
			@ModelAttribute SearchFilterModel searchFilterModel,
			@ModelAttribute BookingFormModel bfm) {
		Facility f = facilityManagement.getFacility(roomId);

		if (!(f instanceof Room)) {
			// TODO error!
		}

		Room room = (Room) f;
		model.addAttribute("room", room);

		listWorkplaces(model, room);
		return "room/details";
	}

	/**
	 * List workplaces.
	 * 
	 * @param model
	 *            the model
	 * @param room
	 *            the room
	 */
	private void listWorkplaces(Model model, Room room) {
		Collection<Facility> workplaces = room.getContainedFacilities();
		model.addAttribute("workplaces", workplaces);
	}

	/**
	 * Show bookable occupancy.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "room/{roomId}/calendar.html")
	public void showBookableOccupancy(@PathVariable("roomId") String roomId,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute CalendarParamModel cpm) {

		Collection<Reservation> reservations = bookingManagement
				.listReservationsOfFacility(roomId, cpm.getStart(),
						cpm.getEnd());

		// create JSON response
		try {
			JSONArray events = new JSONArray();

			for (Reservation r : reservations) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());
				o.put("start", r.getStartTime().getTime());
				o.put("end", r.getEndTime().getTime());
				o.put("title", "besetzt");
				events.put(o);
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

	// better: room/book?
	/**
	 * Book.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "room/{roomId}/book.html")
	public String book(@PathVariable("roomId") String roomId, Model model,
			@ModelAttribute BookingFormModel bfm, Principal principal) {

		String userID = principal.getName();

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

		try {
			bookingManagement.book(userID, facilityIDs, bfm.getStart(),
					bfm.getEnd());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacilityNotFreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "room/details";

	}
}
