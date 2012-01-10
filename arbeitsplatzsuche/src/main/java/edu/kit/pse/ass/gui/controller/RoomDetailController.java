package edu.kit.pse.ass.gui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.booking.management.FreeFacilityResult;
import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.management.FacilityManagement;
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
			@ModelAttribute SearchFilterModel searchFilterModel) {
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
	 * Tmp get facility.
	 * 
	 * @param facilityId
	 *            the facility id
	 * @return the facility
	 */
	private Facility tmpGetFacility(String facilityId) {
		Room r = new Room();
		r.setName("Leetraum");
		r.setNumber("13.37");
		r.setLevel(3);
		r.setDescription("Ein Testraum, welcher nur zum testen da ist und sonst auch keinen anderen Sinn erfüllt. Nein diesen Raum gibt es nicht wirklich.");
		r.setId(facilityId);

		Building b = new Building();
		b.setName("Informatikgebäude");
		b.setNumber("08.15");
		r.setParentFacility(b);

		r.addProperty(new Property("WLAN"));
		r.addProperty(new Property("Steckdosen"));
		return r;
	}

	/**
	 * Tmp get containing facilities.
	 * 
	 * @return the collection
	 */
	private Collection<Facility> tmpGetContainingFacilities() {
		ArrayList<Facility> result = new ArrayList<Facility>();
		Workplace wp1 = new Workplace();
		wp1.setName("1");
		wp1.setId("someid1");
		wp1.addProperty(new Property("Lampe"));
		wp1.addProperty(new Property("Flachbildschirm"));
		Workplace wp2 = new Workplace();
		wp2.setName("2");
		wp2.setId("someid2");
		result.add(wp1);
		result.add(wp2);
		return result;
	}

	private Collection<Reservation> tmpListReservationsOfFacility(
			Facility facility, Date start, Date end) {

		ArrayList<Reservation> list = new ArrayList<Reservation>();
		Reservation r1 = new Reservation();
		r1.setId("aa");
		r1.setStartTime(start);
		r1.setEndTime(end);
		list.add(r1);

		return list;
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
		Collection<Facility> workplaces = tmpGetContainingFacilities();
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

		Facility f = tmpGetFacility(roomId);

		Collection<Reservation> reservations = tmpListReservationsOfFacility(f,
				cpm.getStart(), cpm.getEnd());

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
}
