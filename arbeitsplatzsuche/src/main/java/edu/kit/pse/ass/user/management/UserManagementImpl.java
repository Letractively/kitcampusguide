package edu.kit.pse.ass.user.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.user.dao.UserDAO;

public class UserManagementImpl implements UserManagement {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String register(String userID, String password) {
		// TODO Auto-generated method stub
		if (passwordEncoder != null) {
			password = passwordEncoder.encodePassword(password, null);
		}
		User u = userDAO.insertUser(userID, password);
		if (u == null)
			return null;
		return u.getEmail();
	}

	@Override
	public User getUser(String userID) {
		return userDAO.getUser(userID);
	}

}
