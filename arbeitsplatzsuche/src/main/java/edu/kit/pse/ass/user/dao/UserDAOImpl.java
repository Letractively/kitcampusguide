package edu.kit.pse.ass.user.dao;

import javax.inject.Inject;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.User;

public class UserDAOImpl implements UserDAO {

	/** The jpa template. */
	@Inject
	private JpaTemplate jpaTemplate;

	@Override
	public User insertUser(String userID, String passwordHash) {
		User u = new User();
		u.setEmail(userID);
		u.setId(userID);
		u.setPassword(passwordHash);
		jpaTemplate.persist(u);
		return u;
	}

	@Override
	@Transactional
	public void deleteUser(String userID) {
		User u = jpaTemplate.find(User.class, userID);
		if (u != null) {
			jpaTemplate.remove(u);
		}
	}

	@Override
	public User getUser(String userID) {
		return jpaTemplate.find(User.class, userID);
	}

	@Override
	public void userFillWithDummies() {
		// TODO Auto-generated method stub

	}

}
