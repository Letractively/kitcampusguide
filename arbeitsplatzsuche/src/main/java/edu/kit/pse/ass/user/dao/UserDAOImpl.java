package edu.kit.pse.ass.user.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		// TODO Create testdata at the correct place
		// TODO This user is ubbbb@student.kit.edu with Password bbbbbbbb
		User u1 = new User();
		u1.setEmail("ubbbb@student.kit.edu");
		u1.setPassword("fb398cc690e15ddba43ee811b6c0d3ec190901ad3df377fec9a1f9004b919a06");
		HashSet<String> s = new HashSet<String>();
		s.add("ROLE_STUDENT");
		u1.setRoles(s);
		jpaTemplate.persist(u1);

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

	@Override
	@Transactional
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
			u.setEmail("u" + email.get(i) + "@student.kit.edu");
			u.setPassword(email.get(i) + email.get(i));
			jpaTemplate.persist(u);
		}
	}
}
