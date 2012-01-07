package edu.kit.pse.ass.gui.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FacilityNotFreeException;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.gui.model.ReservationModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservationController.
 * 
 * @author Jannis Koch
 */
@Controller
public class ReservationController extends MainController {

	/** The booking management. */
	@Inject
	BookingManagement bookingManagement;

	/**
	 * List reservations.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the principal
	 * @return the string
	 */
	@RequestMapping(value = "reservation/list")
	public String listReservations(Model model, Principal principal,
			HttpServletRequest request) {

		// get name of logged in user
		String userID = "";
		if (principal != null) {
			userID = principal.getName();
		}

		// Show reservations starting from 6 months in the past...
		Calendar asFrom = Calendar.getInstance();
		asFrom.add(Calendar.MONTH, -6);

		// ... up until 1 year from now
		Calendar upTo = Calendar.getInstance();
		upTo.add(Calendar.YEAR, 1);

		// get current reservations and create models
		Collection<Reservation> reservations = bookingManagement
				.listReservationsOfUser(userID, new Date(), upTo.getTime());

		reservations = tempReservationList(); // TEMP

		Collection<ReservationModel> reservationModels = new ArrayList<ReservationModel>();
		for (Reservation r : reservations) {
			reservationModels.add(new ReservationModel(r));
		}

		// get past reservations and create models
		Collection<Reservation> pastReservations = bookingManagement
				.listReservationsOfUser(userID, asFrom.getTime(), new Date());
		pastReservations = tempReservationList(); // TEMP
		Collection<ReservationModel> pastReservationModels = new ArrayList<ReservationModel>();
		for (Reservation r : pastReservations) {
			pastReservationModels.add(new ReservationModel(r));
		}

		model.addAttribute("reservations", reservationModels);
		model.addAttribute("pastReservations", pastReservationModels);

		model.addAttribute("deleteNotification",
				request.getParameter("deleteNotification"));

		return "reservation/list";
	}

	/**
	 * Temp reservation list.
	 * 
	 * @return the collection
	 */
	private Collection<Reservation> tempReservationList() {

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();

		Reservation res1 = new Reservation(new Date(111, 11, 27, 15, 30),
				new Date(111, 11, 27, 16, 00), "ubbbb@student.kit.edu");
		Reservation res2 = new Reservation(new Date(111, 11, 29, 11, 00),
				new Date(111, 11, 29, 13, 15), "ubbbb@student.kit.edu");
		Reservation res3 = new Reservation(new Date(112, 12, 29, 11, 00),
				new Date(112, 12, 29, 13, 15), "ubbbb@student.kit.edu");

		res1.addBookedFacilityId("wpid1");
		res1.addBookedFacilityId("wpid2");
		res2.addBookedFacilityId("roomid3");
		res3.addBookedFacilityId("wpid1");

		res1.setId("resid1");
		res2.setId("resid2");
		res3.setId("resid3");

		reservations.add(res1);
		reservations.add(res2);
		reservations.add(res3);

		return reservations;
	}

	// better: room/book?
	/**
	 * Book.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "reservation/book")
	public String book(Model model) {
		return "";

	}

	/**
	 * Show reservation details.
	 * 
	 * @param model
	 *            the model
	 * @param reservationID
	 *            the reservation id
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.GET)
	public String showReservationDetails(Model model,
			@PathVariable("reservationId") String reservationID) {

		Reservation reservation = bookingManagement
				.getReservation(reservationID);
		reservation = tempGetReservation(reservationID);

		model.addAttribute("reservation", new ReservationModel(reservation));

		return "reservation/details";

	}

	/**
	 * Temp get reservation.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @return the reservation
	 */
	private Reservation tempGetReservation(String reservationID) {
		Object[] reservations = tempReservationList().toArray();
		if (reservationID.equals("resid1")) {
			return (Reservation) reservations[0];
		} else if (reservationID.equals("resid2")) {
			return (Reservation) reservations[1];
		} else if (reservationID.equals("resid3")) {
			return (Reservation) reservations[2];
		}
		return (Reservation) reservations[0];
	}

	/**
	 * Update reservations.
	 * 
	 * @param model
	 *            the model
	 * @param reservationID
	 *            the reservation id
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.POST)
	public String updateReservations(Model model,
			@PathVariable("reservationId") String reservationID) {
		// show reservation details after update
		return "reservation/details";
	}

	/**
	 * Delete reservations.
	 * 
	 * @param model
	 *            the model
	 * @param reservationID
	 *            the reservation id
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/delete.html")
	public String deleteReservations(Model model, Principal principal,
			@PathVariable("reservationId") String reservationID) {

		// TODO bookingManagement.deleteReservation(reservationID);

		// show delete notification
		model.addAttribute("deleteNotification", true);

		// show reservation list after delete
		return "redirect:/reservation/list.html";

	}

	/**
	 * Handle facility not free exception.
	 * 
	 * @param e
	 *            the e
	 */
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
