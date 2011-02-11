package edu.kit.cm.kitcampusguide.standardtypes;

/**
 * Defines an exception thrown if any error occurs during initialization.
 * If this error is thrown, the program usually will be terminated because there is no guarantee for the program working correctly.
 * @author fred
 *
 */
public class InitializationException extends Exception {
	
	/**
     * {@inheritDoc}
     */
	public InitializationException() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public InitializationException(String message) {
		super(message);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public InitializationException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
