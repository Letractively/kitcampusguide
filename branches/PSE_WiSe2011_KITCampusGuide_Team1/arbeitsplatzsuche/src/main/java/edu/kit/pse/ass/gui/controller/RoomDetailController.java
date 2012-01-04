package edu.kit.pse.ass.gui.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityManagement;

@Controller
public class RoomDetailController extends MainController {

	@Inject
	FacilityManagement facilityManagement;

	@RequestMapping(value = "room/{roomId}/details.html")
	public String setUpRoomDetails(@PathVariable("roomId") String roomId,
			Model model) {
		Facility f = facilityManagement.getFacility(roomId);

		if (!(f instanceof Room)) {
			// TODO error!
		}

		Room room = (Room) f;
		model.addAttribute("room", room);
		return "room/details";
	}

	private void prefillBookingForm(Model model) {

	}

	private void listWorkplaces() {

	}

	private void showBookableOccupancy(Model model) {

	}
}
