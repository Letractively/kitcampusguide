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
		ReservationModel originalReservation = null;
		try {
			Reservation reservation = bookingManagement.getReservation(reservationID);
			if (!reservation.getBookingUserId().equals(userID)) {
				// Reservation does not belong to this user!
				model.addAttribute("errorReservationNotFound", true);
				return "reservation/details";
			} else {
				originalReservation = new ReservationModel(reservation, facilityManagement);
			}
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorReservationNotFound", true);
			return "reservation/details";
		} catch (ReservationNotFoundException e) {
			model.addAttribute("errorReservationNotFound", true);
			return "reservation/details";
		}

		if (originalReservation != null) {

			// Set form workplace count value for Room reservations / reservations with one workplace, so the Validator
			// does not fail
			if (originalReservation.bookedFacilityIsRoom() || originalReservation.getWorkplaceCount() == 1) {
				updatedReservation.setWorkplaceCount(originalReservation.getWorkplaceCount());
			}

			// Validate update form
			ReservationValidator resValidator = new ReservationValidator();
			resValidator.validate(updatedReservation, originalReservation, updatedReservationResult);

			if (updatedReservationResult.hasErrors()) {
				// form errors
				model.addAttribute("formErrors", true);
			} else {
				// form ok

				// Remove workplaces from Reservation, if necessary
				boolean updateWorkplaceSuccess = updateWorkplaceCount(originalReservation,
						updatedReservation.getWorkplaceCount());

				if (!updateWorkplaceSuccess) {
					model.addAttribute("updateErrorWorkplaceCount", true);
				} else {
					// Update end time of reservation, if necessary
					boolean updateEndTimeSuccess = updateEndTime(originalReservation,
							updatedReservation.getEndTime());
					if (!updateEndTimeSuccess) {
						model.addAttribute("updateErrorFacilityOccupied", true);
					} else {
						model.addAttribute("updateSuccess", true);
					}
				}

				// Get new, updated reservation
				try {
					originalReservation = new ReservationModel(bookingManagement.getReservation(reservationID),
							facilityManagement);
				} catch (IllegalArgumentException e) {
					// Internal error
					return handleIllegalRequest(e);
				} catch (ReservationNotFoundException e) {
					// Internal error
					return handleIllegalRequest(e);
				}

			}

			model.addAttribute("reservation", originalReservation);
			model.addAttribute("updatedReservation", updatedReservation);
		}

		// show reservation details after update
		return "reservation/details";
	}

	/**
	 * updates the workplace count of the given reservation to the given integer value and return true on success
	 * 
	 * @param reservation
	 *            the reservation to update
	 * @param newWorkplaceCount
	 *            the new workplace count
	 * @return true if update was successful
	 */
	private boolean updateWorkplaceCount(ReservationModel reservation, int newWorkplaceCount) {
		boolean updateError = false;
		if (newWorkplaceCount < reservation.getWorkplaceCount()) {
			int removeWorkplacesCount = reservation.getWorkplaceCount() - newWorkplaceCount;
			Collection<String> bookedFacilities = reservation.getBookedFacilityIds();
			int removedWorkplaces = 0;
			for (String workplaceID : bookedFacilities) {
				if (removedWorkplaces < removeWorkplacesCount) {
					try {
						bookingManagement.removeFacilityFromReservation(reservation.getId(), workplaceID);
						removedWorkplaces++;
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			if (removedWorkplaces != removeWorkplacesCount) {
				updateError = true;
			}
		}
		return !updateError;
	}

	/**
	 * updates the end time of the given reservation to the given end time and return true on success
	 * 
	 * @param reservation
	 *            the reservation to update
	 * @param newEndTime
	 *            the new end time
	 * @return true if update was successful
	 */
	private boolean updateEndTime(ReservationModel reservation, Date newEndTime) {
		boolean updateError = false;
		if (!reservation.getEndTime().equals(newEndTime)) {
			try {
				bookingManagement.changeReservationEnd(reservation.getId(), newEndTime);
			} catch (FacilityNotFreeException e) {
				updateError = true;
			} catch (IllegalArgumentException e) {
				updateError = true;
			}
		}
		return !updateError;
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
			bookingManagement.getReservation(reservationID);

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
