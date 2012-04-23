/**
 * 
 */
package edu.kit.pse.ass.booking.management;

/**
 * The Class FacilityNotFreeException.
 * 
 * @author Andreas Bosch
 */
public class ReservationNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3752325413375454424L;

	/**
	 * Instantiates a new facility not free exception.
	 * 
	 * @param message
	 *            the message
	 */
	public ReservationNotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new facility not free exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ReservationNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new facility not free exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ReservationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
