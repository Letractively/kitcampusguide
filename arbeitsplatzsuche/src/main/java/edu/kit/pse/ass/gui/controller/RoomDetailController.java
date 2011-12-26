package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoomDetailController extends MainController {

	@RequestMapping(value = "room/details")
	public String setUpRoomDetails(Model model) {
		return "room/details";
	}

	private void prefillBookingForm(Model model) {

	}

	private void listWorkplaces() {

	}

	private void showBookableOccupancy(Model model) {

	}
}
