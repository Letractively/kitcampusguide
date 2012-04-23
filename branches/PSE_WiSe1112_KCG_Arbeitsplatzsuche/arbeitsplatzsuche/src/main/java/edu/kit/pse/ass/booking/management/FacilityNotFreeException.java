/**
 * 
 */
package edu.kit.pse.ass.booking.management;

/**
 * The Class FacilityNotFreeException.
 * 
 * @author Andreas Bosch
 */
public class FacilityNotFreeException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3752325413375454423L;

	/**
	 * Instantiates a new facility not free exception.
	 * 
	 * @param message
	 *            the message
	 */
	public FacilityNotFreeException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new facility not free exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public FacilityNotFreeException(Throwable cause) {
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
	public FacilityNotFreeException(String message, Throwable cause) {
		super(message, cause);
	}

}
