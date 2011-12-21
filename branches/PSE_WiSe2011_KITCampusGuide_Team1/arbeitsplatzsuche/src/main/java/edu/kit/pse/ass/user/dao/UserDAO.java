package edu.kit.pse.ass.user.dao;

import edu.kit.pse.ass.entity.User;

public interface UserDAO {

	/**
	 * inserts a user with the given ID and password hash and returns the
	 * inserted User object
	 * 
	 * @param userID
	 *            the user's ID
	 * @param passwordHash
	 *            the password hash
	 * @return the inserted User object
	 */
	public User insertUser(String userID, String passwordHash);

	/**
	 * deletes the user with the given ID
	 * 
	 * @param userID
	 *            the id of the user to be deleted
	 */
	public void deleteUser(String userID);

	/**
	 * returns the User with the given ID
	 * 
	 * @param userID
	 *            the user's id
	 * @return the User with the given ID
	 */
	public User getUser(String userID);
}
