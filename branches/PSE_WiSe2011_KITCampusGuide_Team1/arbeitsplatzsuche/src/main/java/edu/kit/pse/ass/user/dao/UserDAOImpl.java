package edu.kit.pse.ass.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.kit.pse.ass.entity.User;

public class UserDAOImpl implements UserDAO, UserDetailsService {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	@Override
	public User insertUser(String userID, String passwordHash) {
		User u = new User();
		u.setEmail(userID);
		u.setPassword(passwordHash);
		jpaTemplate.persist(u);
		return u;
	}

	@Override
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
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		try {
			User u = jpaTemplate.find(User.class, username);
			if (u == null) {
				throw new UsernameNotFoundException("Username not found.");
			}
			return u;
		} catch (DataRetrievalFailureException e) {
			throw new UsernameNotFoundException("Username not found.");
		}
	}

}
