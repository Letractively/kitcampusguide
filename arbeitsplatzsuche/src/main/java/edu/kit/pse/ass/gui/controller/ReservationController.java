package edu.kit.pse.ass.gui.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FacilityNotFreeException;
import edu.kit.pse.ass.booking.management.ReservationNotFoundException;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.gui.model.ReservationModel;
import edu.kit.pse.ass.gui.model.ReservationValidator;

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

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	/**
	 * List reservations.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the principal
	 * @param request
	 *            the http request
	 * @return the string
	 */
	@RequestMapping(value = "reservation/list")
	public String listReservations(Model model, Principal principal, HttpServletRequest request) {

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
		Collection<Reservation> reservations = bookingManagement.listReservationsOfUser(userID, new Date(),
				upTo.getTime());
		ArrayList<ReservationModel> reservationModels = new ArrayList<ReservationModel>();
		for (Reservation r : reservations) {
			reservationModels.add(new ReservationModel(r, facilityManagement));
		}
		Collections.sort(reservationModels);

		// get past reservations and create models
		Collection<Reservation> pastReservations = bookingManagement.listReservationsOfUser(userID,
				asFrom.getTime(), new Date());
		ArrayList<ReservationModel> pastReservationModels = new ArrayList<ReservationModel>();
		Date now = new Date();
		for (Reservation r : pastReservations) {
			if (r.getEndTime().before(now)) {
				// Only show reservation if it has ended (then it is shown in the current reservations list
				pastReservationModels.add(new ReservationModel(r, facilityManagement));
			}
		}
		Collections.sort(pastReservationModels);

		model.addAttribute("reservations", reservationModels);
		model.addAttribute("pastReservations", pastReservationModels);
		model.addAttribute("deleteNotification", request.getParameter("deleteNotification"));

		return "reservation/list";
	}

	/**
	 * Show reservation details.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the principal
	 * @param reservationID
	 *            the reservation id
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.GET)
	public String showReservationDetails(Model model, Principal principal,
			@PathVariable("reservationId") String reservationID) {

		// get name of logged in user
		String userID = "";
		if (principal != null) {
			userID = principal.getName();
		}

		// Get reservation
		Reservation reservation = null;
		try {
			reservation = bookingManagement.getReservation(reservationID);

			if (!reservation.getBookingUserId().equals(userID)) {
				// Reservation does not belong to this user!
				model.addAttribute("errorReservationNotFound", true);
			} else {
				// Create and add models
				ReservationModel resModel = new ReservationModel(reservation, facilityManagement);
				model.addAttribute("reservation", resModel);
				model.addAttribute("updatedReservation", resModel);
			}

		} catch (IllegalArgumentException e) {
			model.addAttribute("errorReservationNotFound", true);
		} catch (ReservationNotFoundException e) {
			model.addAttribute("errorReservationNotFound", true);
		}

		return "reservation/details";

	}

	/**
	 * Update reservations.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the principal
	 * @param reservationID
	 *            the reservation id
	 * @param updatedReservation
	 *            the updated reservation
	 * @param updatedReservationResult
	 *            the result of the updated reservation
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.POST)
	public String updateReservation(Model model, Principal principal,
			@PathVariable("reservationId") String reservationID,
			@ModelAttribute("updatedReservation") ReservationModel updatedReservation,
			BindingResult updatedReservationResult) {

		// get name of logged in user
		String userID = "";
		if (principal != null) {
			userID = principal.getName();
		}

		// Get reservation
		ReservationModel resModel = null;
		try {
			Reservation reservation = bookingManagement.getReservation(reservationID);
			if (!reservation.getBookingUserId().equals(userID)) {
				// Reservation does not belong to this user!
				model.addAttribute("errorReservationNotFound", true);
				return "reservation/details";
			} else {
				resModel = new ReservationModel(reservation, facilityManagement);
			}
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorReservationNotFound", true);
			return "reservation/details";
		} catch (ReservationNotFoundException e) {
			model.addAttribute("errorReservationNotFound", true);
			return "reservation/details";
		}

		if (resModel != null) {

			// WorkplaceCount update not yet implemented - set workplace count value so the Validator does not fail
			updatedReservation.setWorkplaceCount(resModel.getWorkplaceCount());

			// Set form workplace count value for Room reservations so the Validator does not fail
			if (resModel.bookedFacilityIsRoom()) {
				updatedReservation.setWorkplaceCount(resModel.getWorkplaceCount());
			}

			// Validate update form
			ReservationValidator resValidator = new ReservationValidator();
			resValidator.validate(updatedReservation, resModel, updatedReservationResult);

			if (updatedReservationResult.hasErrors()) {
				// form errors
				model.addAttribute("formErrors", true);
			} else {
				// update form is OK

				if (updatedReservation.getEndTime().equals(resModel.getEndTime())) {
					// no changes to the reservation - show success!
					model.addAttribute("updateSuccess", true);
				} else {
					// Change reservation end
					try {
						bookingManagement.changeReservationEnd(reservationID, updatedReservation.getEndTime());
						// success!
						model.addAttribute("updateSuccess", true);
					} catch (FacilityNotFreeException e) {
						// Facility is not free
						model.addAttribute("updateErrorFacilityOccupied", true);
					} catch (IllegalArgumentException e) {
						// arguments are wrong (should not happen as form is validated)
						model.addAttribute("formErrors", true);
					} catch (IllegalStateException e) {
						// database is inconsistent
						return handleIllegalRequest(e);
					}
				}

			}

			model.addAttribute("reservation", resModel);
			model.addAttribute("updatedReservation", updatedReservation);
		}

		// show reservation details after update
		return "reservation/details";
	}

	/**
	 * Delete reservations.
	 * 
	 * @param model
	 *            the model
	 * @param principal
	 *            the principal
	 * @param reservationID
	 *            the reservation id
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/delete.html")
	public String deleteReservations(Model model, Principal principal,
			@PathVariable("reservationId") String reservationID) {

		String returnedView;

		try {
			// check if reservation exists
			Reservation reservation = bookingManagement.getReservation(reservationID);

			bookingManagement.deleteReservation(reservationID);

			// show delete notification
			model.addAttribute("deleteNotification", true);

			// show reservation list after delete
			returnedView = "redirect:/reservation/list.html";

		} catch (IllegalArgumentException e) {
			model.addAttribute("errorReservationNotFound", true);
			returnedView = "reservation/details";
		} catch (ReservationNotFoundException e) {
			model.addAttribute("errorReservationNotFound", true);
			returnedView = "reservation/details";
		}

		return returnedView;
	}
}
