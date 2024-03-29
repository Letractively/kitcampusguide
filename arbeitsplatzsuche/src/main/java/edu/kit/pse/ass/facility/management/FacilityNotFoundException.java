package edu.kit.pse.ass.facility.management;

/**
 * The class FacilityNotFreeException.
 * 
 * @author Andreas Bosch
 */
public class FacilityNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3752325413375454423L;

	/**
	 * Instantiates a new facility not free exception.
	 */
	public FacilityNotFoundException() {
	}

	/**
	 * Instantiates a new facility not free exception with the given message
	 * 
	 * @param message
	 *            - the message
	 */
	public FacilityNotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new facility not free exception with the given cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public FacilityNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new facility not free exception with the given message and cause
	 * 
	 * @param message
	 *            - the message
	 * @param cause
	 *            - the cause
	 */
	public FacilityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
