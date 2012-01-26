/**
 * 
 */
package edu.kit.pse.ass.booking.management;

/**
 * @author Sebastian
 * 
 */
public class IllegalDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086893658106126623L;

	/**
	 * 
	 */
	public IllegalDateException() {
		super();
	}

	/**
	 * @param message
	 *            the message to throw
	 */
	public IllegalDateException(String message) {
		super(message);
	}

}
