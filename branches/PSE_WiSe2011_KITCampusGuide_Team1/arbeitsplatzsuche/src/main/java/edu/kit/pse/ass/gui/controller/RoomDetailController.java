package edu.kit.pse.ass.gui.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityManagement;

@Controller
public class RoomDetailController extends MainController {

	@Inject
	FacilityManagement facilityManagement;

	@RequestMapping(value = "room/{roomId}/details.html")
	public String setUpRoomDetails(@PathVariable("roomId") String roomId,
			Model model) {
		Facility f = tmpGetFacility(roomId);// facilityManagement.getFacility(roomId);

		if (!(f instanceof Room)) {
			// TODO error!
		}

		Room room = (Room) f;
		model.addAttribute("room", room);
		return "room/details";
	}

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

		b.addProperty(new Property("WLAN"));
		b.addProperty(new Property("Steckdosen"));
		return r;
	}

	private void prefillBookingForm(Model model) {

	}

	private void listWorkplaces() {

	}

	private void showBookableOccupancy(Model model) {

	}
}
