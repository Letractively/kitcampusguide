package edu.kit.pse.ass.gui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.pse.ass.gui.model.ReservationModel;

/**
 * The ReservationController sets up the reservation page and handles updates and deletions of reservations.
 * 
 * @author Jannis Koch
 * 
 */
public interface ReservationController {

	/**
	 * List the reservations of a user.
	 * 
	 * @param model
	 *            the spring model
	 * @param request
	 *            the http request
	 * @return the view path
	 */
	@RequestMapping(value = "reservation/list")
	String listReservations(Model model, HttpServletRequest request);

	/**
	 * Shows a reservation details page.
	 * 
	 * @param model
	 *            the spring model
	 * @param reservationID
	 *            the reservation id of the reservation to show details of
	 * @return the view path
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.GET)
	String showReservationDetails(Model model, @PathVariable("reservationId") String reservationID);

	/**
	 * Handles the submit of an changed reservation.
	 * 
	 * @param model
	 *            the spring model
	 * @param reservationID
	 *            the reservation id of the reservation to update
	 * @param updatedReservation
	 *            the updated reservation
	 * @param updatedReservationResult
	 *            the result of the updated reservation
	 * @return the view path
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.POST)
	String updateReservation(Model model, @PathVariable("reservationId") String reservationID,
			@ModelAttribute("updatedReservation") ReservationModel updatedReservation,
			BindingResult updatedReservationResult);

	/**
	 * Handles a deletion of a reservations.
	 * 
	 * @param model
	 *            the spring model
	 * @param reservationID
	 *            the reservation id of the reservation to delete
	 * @return the view path
	 */
	@RequestMapping(value = "reservation/{reservationId}/delete.html")
	String deleteReservations(Model model, @PathVariable("reservationId") String reservationID);

}