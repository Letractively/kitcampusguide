package edu.kit.pse.ass.user.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.User;

public class UserDAOImpl implements UserDAO, UserDetailsService {

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
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return jpaTemplate.find(User.class, username);
	}

	@Override
	public void userFillWithDummies() {
		// TODO Auto-generated method stub
		// 4 letter email part, used as id
		List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe",
				"bbbf", "bbbg", "bbbh", "bbbi", "bbbj", "bbbk", "bbbl", "bbbm");
		for (int i = 0; i < email.size(); i++) {
			User u = new User();
			HashSet<String> s = new HashSet<String>();
			s.add("student");
			u.setRoles(s);
			u.setId("u" + email.get(i) + "@student.kit.edu");
			u.setEmail("u" + email.get(i) + "@student.kit.edu");
			u.setPassword(email.get(i) + email.get(i));
			jpaTemplate.persist(u);
		}
	}
}
