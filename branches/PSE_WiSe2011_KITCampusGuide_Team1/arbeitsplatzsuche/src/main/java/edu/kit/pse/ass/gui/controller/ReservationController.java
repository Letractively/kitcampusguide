package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.booking.management.FacilityNotFreeException;

@Controller
public class ReservationController extends MainController {

	@RequestMapping(value = "reservation/list")
	public String listReservations(Model model) {
		return "reservation/list";
	}

	// better: room/book?
	@RequestMapping(value = "reservation/book")
	public String book(Model model) {
		return "";

	}

	@RequestMapping(value = "reservation/details")
	public String showReservationDetails(Model model) {
		return "reservation/details";

	}

	@RequestMapping(value = "reservation/update")
	public String updateReservations(Model model) {
		// show reservation details after update
		return "reservation/details";
	}

	@RequestMapping(value = "reservation/delete")
	public String deleteReservations(Model model) {
		// show reservation list after delete
		return "reservation/list";

	}

	private void handleFacilityNotFreeException(FacilityNotFreeException e) {

	}

	/*
	 * commented methods because exceptions do not yet exist
	 * 
	 * private void handleFacilityNotFoundException(FacilityNotFoundException e)
	 * {
	 * 
	 * }
	 * 
	 * private void handleReservationNotFoundException(
	 * ReservationNotFoundException e) { }
	 */
}
