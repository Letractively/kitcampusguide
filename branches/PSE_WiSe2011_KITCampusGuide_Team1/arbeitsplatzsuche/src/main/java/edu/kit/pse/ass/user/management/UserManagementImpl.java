package edu.kit.pse.ass.user.management;

import javax.inject.Inject;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.user.dao.UserDAO;

public class UserManagementImpl implements UserManagement {

	@Inject
	UserDAO userDAO;

	@Inject
	PasswordEncoder passwordEncoder;

	@Override
	public String register(String userID, String password) {
		// TODO Auto-generated method stub
		if (passwordEncoder != null) {
			password = passwordEncoder.encodePassword(password, null);
		}
		User u = userDAO.insertUser(userID, password);
		if (u == null)
			return null;
		return u.getId();
	}

	@Override
	public User getUser(String userID) {
		return userDAO.getUser(userID);
	}

}
