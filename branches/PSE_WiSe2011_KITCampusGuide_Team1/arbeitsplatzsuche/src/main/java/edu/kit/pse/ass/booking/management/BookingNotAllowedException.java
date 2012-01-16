package edu.kit.pse.ass.booking.management;

/**
 * The User is not allowed to book a Facility
 * 
 * @author Oliver Schneider
 */
public class BookingNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086893658106126623L;

	private final String userID;

	/**
	 * Creates a new BookingNotAllowedException.
	 * 
	 * @param userID
	 *            the user who is not allowed to book
	 * @param message
	 *            the message why
	 */
	public BookingNotAllowedException(String userID, String message) {
		super(message);
		this.userID = userID;
	}

	/**
	 * The user who is not allowed to book.
	 * 
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
}
