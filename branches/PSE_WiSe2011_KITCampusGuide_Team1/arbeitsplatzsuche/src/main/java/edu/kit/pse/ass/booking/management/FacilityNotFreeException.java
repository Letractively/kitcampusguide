/**
 * 
 */
package edu.kit.pse.ass.booking.management;

/**
 * @author Andreas Bosch
 * 
 */
public class FacilityNotFreeException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3752325413375454423L;

	/**
	 * Instantiates a new facility not free exception.
	 */
	public FacilityNotFreeException() {
	}

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

	/**
	 * Instantiates a new facility not free exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param enableSuppression
	 *            the enable suppression
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public FacilityNotFreeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
