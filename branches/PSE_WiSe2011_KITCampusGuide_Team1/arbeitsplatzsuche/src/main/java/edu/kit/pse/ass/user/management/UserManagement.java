package edu.kit.pse.ass.user.management;

import edu.kit.pse.ass.entity.User;

/**
 * The Interface UserManagement.
 */
public interface UserManagement {

	/**
	 * registers a user with the given userID and password and returns the userID on success.
	 * 
	 * @param userID
	 *            the user's id
	 * @param password
	 *            the user's password
	 * @return the user id
	 */
	String register(String userID, String password);

	/**
	 * returns the user with the given ID.
	 * 
	 * @param userID
	 *            the user's id
	 * @return the user with the given ID
	 */
	User getUser(String userID);
}
