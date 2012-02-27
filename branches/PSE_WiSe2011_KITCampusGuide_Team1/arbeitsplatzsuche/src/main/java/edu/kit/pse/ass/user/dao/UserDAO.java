package edu.kit.pse.ass.user.dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.kit.pse.ass.entity.User;

/**
 * The Interface UserDAO, contains methods for user related database access .
 */
public interface UserDAO extends UserDetailsService {

	/**
	 * Insert a user with the given ID and password hash and returns the inserted User object.
	 * 
	 * @param userID
	 *            - the user's ID
	 * @param passwordHash
	 *            - the password hash
	 * @return the inserted User object
	 * 
	 * @throws IllegalArgumentException
	 *             if one parameter is null or empty
	 */
	User insertUser(String userID, String passwordHash);

	/**
	 * Deletes the user with the given ID.
	 * 
	 * @param userID
	 *            - the id of the user to be deleted
	 * 
	 * @throws IllegalArgumentException
	 *             if the userID is null or empty
	 */
	void deleteUser(String userID);

	/**
	 * Returns the User with the given ID.
	 * 
	 * @param userID
	 *            - the needed user's id
	 * @return the User with the given ID
	 * 
	 * @throws IllegalArgumentException
	 *             if userID is null or empty
	 */
	User getUser(String userID);

}
