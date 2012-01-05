package edu.kit.pse.ass.gui.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
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

		Collection<Facility> workplaces = tmpGetContainingFacilities();
		model.addAttribute("workplaces", workplaces);
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

		r.addProperty(new Property("WLAN"));
		r.addProperty(new Property("Steckdosen"));
		return r;
	}

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

	private void prefillBookingForm(Model model) {

	}

	private void listWorkplaces() {

	}

	private void showBookableOccupancy(Model model) {

	}
}
