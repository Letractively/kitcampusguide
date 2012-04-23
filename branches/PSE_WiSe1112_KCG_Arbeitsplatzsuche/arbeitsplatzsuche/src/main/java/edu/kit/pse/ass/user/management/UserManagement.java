package edu.kit.pse.ass.user.management;

import edu.kit.pse.ass.entity.User;

/**
 * The Interface UserManagement encapsulates the userDAO methods.
 */
public interface UserManagement {

	/**
	 * Registers a user with the given userID and password and returns the userID on success.
	 * 
	 * @param userID
	 *            - the new user's id
	 * @param password
	 *            - the user's password
	 * @return the userID
	 * @throws UserAlreadyExistsException
	 *             if user already exists
	 * @throws IllegalArgumentException
	 *             when userID is empty or null.
	 */
	String register(String userID, String password) throws UserAlreadyExistsException, IllegalArgumentException;

	/**
	 * Returns the user with the given ID.
	 * 
	 * @param userID
	 *            - the user's id
	 * @return the user with the given ID
	 * @throws IllegalArgumentException
	 *             when userID is empty or null.
	 */
	User getUser(String userID) throws IllegalArgumentException;
}
