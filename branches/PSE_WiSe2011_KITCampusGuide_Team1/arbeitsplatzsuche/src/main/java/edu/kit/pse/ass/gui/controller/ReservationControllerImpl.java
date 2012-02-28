package edu.kit.pse.ass.gui.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * The implementation of the ReservationController.
 * 
 * @see edu.kit.pse.ass.gui.controller.ReservationControlle
 * 
 * @author Jannis Koch
 */
@Controller
public class ReservationControllerImpl extends MainController implements ReservationController {

	/** The booking management. */
	@Inject
	BookingManagement bookingManagement;

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.ReservationController#listReservations(org.springframework.ui.Model,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RequestMapping(value = "reservation/list")
	public String listReservations(Model model, HttpServletRequest request) {

		String userID = getUserID();

		ArrayList<ReservationModel> reservations = getCurrentReservations(userID);
		ArrayList<ReservationModel> pastReservations = getPastReservations(userID);

		model.addAttribute("reservations", reservations);
		model.addAttribute("pastReservations", pastReservations);
		model.addAttribute("deleteNotification", request.getParameter("deleteNotification"));

		return "reservation/list";
	}

	/**
	 * returns the current reservations of the user with the given id
	 * 
	 * @param userID
	 *            the user id
	 * @return the current reservations of the user with the given id
	 */
	private ArrayList<ReservationModel> getCurrentReservations(String userID) {

		// show reservations up until 1 year from now
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
		return reservationModels;
	}

	/**
	 * returns the past reservations of the user with the given id
	 * 
	 * @param userID
	 *            the user id
	 * @return the past reservations of the user with the given id
	 */
	private ArrayList<ReservationModel> getPastReservations(String userID) {

		// Show reservations starting from 6 months in the past
		Calendar asFrom = Calendar.getInstance();
		asFrom.add(Calendar.MONTH, -6);

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
		return pastReservationModels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.ReservationController#showReservationDetails(org.springframework.ui.Model,
	 * java.lang.String)
	 */
	@Override
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.GET)
	public String showReservationDetails(Model model, @PathVariable("reservationId") String reservationID) {

		ReservationModel resModel = getReservation(reservationID, getUserID(), model);

		model.addAttribute("reservation", resModel);
		model.addAttribute("updatedReservation", resModel);

		return "reservation/details";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.ReservationController#updateReservation(org.springframework.ui.Model,
	 * java.lang.String, edu.kit.pse.ass.gui.model.ReservationModel, org.springframework.validation.BindingResult)
	 */
	@Override
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.POST)
	public String updateReservation(Model model, @PathVariable("reservationId") String reservationID,
			@ModelAttribute("updatedReservation") ReservationModel updatedReservation,
			BindingResult updatedReservationResult) {
		String userID = getUserID();

		// get original reservation
		ReservationModel originalReservation = getReservation(reservationID, userID, model);

		if (originalReservation != null) {

			// validate update form
			validateUpdateForm(updatedReservation, updatedReservationResult, originalReservation);

			if (updatedReservationResult.hasErrors()) {
				model.addAttribute("formErrors", true);
			} else {
				updateReservation(originalReservation, updatedReservation, model);

				// Get new, updated reservation
				originalReservation = getReservation(reservationID, userID, model);

				if (originalReservation == null) {
					// iternal error: reservation has been found before, but not this time
					return handleIllegalRequest(new ReservationNotFoundException(""));
				}
			}

			model.addAttribute("reservation", originalReservation);
			model.addAttribute("updatedReservation", updatedReservation);
		}

		// show reservation details after update
		return "reservation/details";
	}

	/**
	 * validates the update form
	 * 
	 * @param updatedReservation
	 *            the updated reservation representing the form
	 * @param updatedReservationResult
	 *            the binding result of the form
	 * @param originalReservation
	 *            the original reservation
	 */
	private void validateUpdateForm(ReservationModel updatedReservation, BindingResult updatedReservationResult,
			ReservationModel originalReservation) {

		// reservations with a room / one workplace: set workplace count, so the Validator does not fail
		if (originalReservation.bookedFacilityIsRoom() || originalReservation.getWorkplaceCount() == 1) {
			updatedReservation.setWorkplaceCount(originalReservation.getWorkplaceCount());
		}

		// Validate update form
		ReservationValidator resValidator = new ReservationValidator();
		resValidator.validate(updatedReservation, originalReservation, updatedReservationResult);

	}

	/**
	 * returns the reservation with the given id
	 * 
	 * also performs checks on user. if errors occur, they are added to the model.
	 * 
	 * @param reservationID
	 *            the reservation id
	 * @param userID
	 *            the user id
	 * @param model
	 *            the model
	 * @return the reservation with the given id
	 */
	private ReservationModel getReservation(String reservationID, String userID, Model model) {

		ReservationModel reservationModel = null;
		try {
			Reservation reservation = bookingManagement.getReservation(reservationID);
			if (!reservation.getBookingUserId().equals(userID)) {
				// Reservation does not belong to this user!
				model.addAttribute("errorReservationNotFound", true);
				return null;
			} else {
				reservationModel = new ReservationModel(reservation, facilityManagement);
			}
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorReservationNotFound", true);
			return null;
		} catch (ReservationNotFoundException e) {
			model.addAttribute("errorReservationNotFound", true);
			return null;
		}
		return reservationModel;
	}

	/**
	 * updates the given reservation to the given updated reservation if errors occur, they are added to the model
	 * 
	 * @param originalReservation
	 *            the original reservation
	 * @param updatedReservation
	 *            the updated reservation
	 * @param model
	 *            the model
	 */
	private void updateReservation(ReservationModel originalReservation, ReservationModel updatedReservation,
			Model model) {
		// Remove workplaces from Reservation, if necessary
		boolean updateWorkplaceSuccess = updateWorkplaceCount(originalReservation,
				updatedReservation.getWorkplaceCount());

		if (!updateWorkplaceSuccess) {
			model.addAttribute("updateErrorWorkplaceCount", true);
		} else {
			// Update end time of reservation, if necessary
			boolean updateEndTimeSuccess = updateEndTime(originalReservation, updatedReservation.getEndTime());
			if (!updateEndTimeSuccess) {
				model.addAttribute("updateErrorFacilityOccupied", true);
			} else {
				model.addAttribute("updateSuccess", true);
			}
		}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.ReservationController#deleteReservations(org.springframework.ui.Model,
	 * java.security.Principal, java.lang.String)
	 */
	@Override
	@RequestMapping(value = "reservation/{reservationId}/delete.html")
	public String deleteReservations(Model model, @PathVariable("reservationId") String reservationID) {

		String userID = getUserID();

		try {
			// check if reservation exists
			Reservation reservation = bookingManagement.getReservation(reservationID);

			if (!reservation.getBookingUserId().equals(userID)) {
				// Reservation does not belong to this user!
				model.addAttribute("errorReservationNotFound", true);
				return "reservation/details";
			}
			// delete and set notification
			bookingManagement.deleteReservation(reservationID);
			model.addAttribute("deleteNotification", true);

		} catch (IllegalArgumentException e) {
			model.addAttribute("errorReservationNotFound", true);
			return "reservation/details";
		} catch (ReservationNotFoundException e) {
			model.addAttribute("errorReservationNotFound", true);
			return "reservation/details";
		}

		return "redirect:/reservation/list.html";
	}

	/**
	 * returns the user id of the currently logged in user
	 * 
	 * @return the user id
	 */
	private String getUserID() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
