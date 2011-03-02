package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Defines an exception thrown if any error occurs during initialization. If
 * this error is thrown, the program usually will be terminated because there is
 * no guarantee for the program working correctly.
 * 
 * @author fred
 * 
 */
public class InitializationException extends Exception {

	/**
	 * Creates a new {@link InitializationException} without any description.
	 * 
	 * @see Exception#Exception()
	 */
	public InitializationException() {
		super();
	}

	/**
	 * Creates a new {@link InitializationException} with a given error message.
	 * 
	 * @param message
	 *            an error message
	 * @see Exception#Exception(String)
	 */
	public InitializationException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link InitializationException} with a given cause.
	 * 
	 * @param cause
	 *            a <code>Throwable</code> which caused this exception.
	 * @see Exception#Exception(Throwable)
	 */
	public InitializationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new {@link InitializationException} with a given error message
	 * and a given cause.
	 * 
	 * @param message
	 *            an error message
	 * @param cause
	 *            a <code>Throwable</code> which caused this exception
	 * @see Exception#Exception(String, Throwable)
	 */
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
