package edu.kit.pse.ass.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.kit.pse.ass.entity.User;

/**
 * The Class UserDAOImpl.
 */
public class UserDAOImpl implements UserDAO, UserDetailsService {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.user.dao.UserDAO#insertUser(java.lang.String, java.lang.String)
	 */
	@Override
	public User insertUser(String userID, String passwordHash) throws IllegalArgumentException {
		if (userID == null || userID.isEmpty()) {
			throw new IllegalArgumentException("Illegal userID specified.");
		}
		if (passwordHash == null || passwordHash.isEmpty()) {
			throw new IllegalArgumentException("Illegal password hash");
		}
		User u = new User();
		u.setEmail(userID);
		u.setPassword(passwordHash);
		jpaTemplate.persist(u);
		return u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.user.dao.UserDAO#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String userID) throws IllegalArgumentException {
		if (userID == null || userID.isEmpty()) {
			throw new IllegalArgumentException("Illegal userID specified.");
		}
		User u = jpaTemplate.find(User.class, userID);
		if (u != null) {
			jpaTemplate.remove(u);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.user.dao.UserDAO#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String userID) throws IllegalArgumentException {
		if (userID == null || userID.isEmpty()) {
			throw new IllegalArgumentException("Illegal userID specified.");
		}
		return jpaTemplate.find(User.class, userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService# loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User u = null;
		try {
			u = jpaTemplate.find(User.class, username);
		} catch (DataRetrievalFailureException e) {
			throw new UsernameNotFoundException("Username not found.");
		}
		if (u == null) {
			throw new UsernameNotFoundException("Username not found.");
		}
		return u;
	}

}
