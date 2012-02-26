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
 * The Reservation Controller
 * 
 * @author Jannis Koch
 * 
 */
public interface ReservationController {

	/**
	 * List reservations.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the http request
	 * @return the string
	 */
	@RequestMapping(value = "reservation/list")
	String listReservations(Model model, HttpServletRequest request);

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
	String showReservationDetails(Model model, @PathVariable("reservationId") String reservationID);

	/**
	 * Update reservations.
	 * 
	 * @param model
	 *            the model
	 * @param reservationID
	 *            the reservation id
	 * @param updatedReservation
	 *            the updated reservation
	 * @param updatedReservationResult
	 *            the result of the updated reservation
	 * @return the string
	 */
	@RequestMapping(value = "reservation/{reservationId}/details.html", method = RequestMethod.POST)
	String updateReservation(Model model, @PathVariable("reservationId") String reservationID,
			@ModelAttribute("updatedReservation") ReservationModel updatedReservation,
			BindingResult updatedReservationResult);

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
	String deleteReservations(Model model, @PathVariable("reservationId") String reservationID);

}