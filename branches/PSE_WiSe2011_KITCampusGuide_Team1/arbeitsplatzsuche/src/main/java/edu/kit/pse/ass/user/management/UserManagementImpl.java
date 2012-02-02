package edu.kit.pse.ass.user.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.user.dao.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class UserManagementImpl.
 */
public class UserManagementImpl implements UserManagement {

	/** The user dao. */
	@Autowired
	private UserDAO userDAO;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.user.management.UserManagement#register(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String register(String userID, String password) throws UserAlreadyExistsException, IllegalArgumentException {
		if (userID == null || userID.isEmpty()) {
			throw new IllegalArgumentException("Illegal userID specified.");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Illegal password specified");
		}
		if (passwordEncoder != null) {
			password = passwordEncoder.encodePassword(password, null);
		}
		if (userDAO.getUser(userID) != null) {
			throw new UserAlreadyExistsException();
		}
		User u = userDAO.insertUser(userID, password);
		if (u == null) {
			return null;
		}
		return u.getEmail();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.user.management.UserManagement#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String userID) throws IllegalArgumentException {
		if (userID == null || userID.isEmpty()) {
			throw new IllegalArgumentException("Illegal userID specified.");
		}
		return userDAO.getUser(userID);
	}

}
