package edu.kit.pse.ass.user.dao;

import org.springframework.security.core.userdetails.UserDetails;

import edu.kit.pse.ass.entity.User;

/**
 * The Interface UserDAO.
 */
public interface UserDAO {

	/**
	 * Inserts a user with the given ID and password hash and returns the inserted User object.
	 * 
	 * @param userID
	 *            the user's ID
	 * @param passwordHash
	 *            the password hash
	 * @return the inserted User object
	 */
	User insertUser(String userID, String passwordHash);

	/**
	 * Deletes the user with the given ID.
	 * 
	 * @param userID
	 *            the id of the user to be deleted
	 */
	void deleteUser(String userID);

	/**
	 * Returns the User with the given ID.
	 * 
	 * @param userID
	 *            the user's id
	 * @return the User with the given ID
	 */
	User getUser(String userID);

	UserDetails loadUserByUsername(String username);

}
