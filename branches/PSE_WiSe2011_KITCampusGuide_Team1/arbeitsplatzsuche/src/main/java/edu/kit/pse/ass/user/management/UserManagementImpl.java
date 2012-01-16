package edu.kit.pse.ass.user.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

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
	public String register(String userID, String password) {
		// TODO Auto-generated method stub
		if (passwordEncoder != null) {
			password = passwordEncoder.encodePassword(password, null);
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
	public User getUser(String userID) {
		return userDAO.getUser(userID);
	}

}
