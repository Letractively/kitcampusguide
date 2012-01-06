package edu.kit.pse.ass.testdata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.User;

public class TestData {

	@Autowired
	private JpaTemplate jpaTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void userFillWithDummies() {
		// TODO Auto-generated method stub
		// 4 letter email part, used as id
		List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe",
				"bbbf", "bbbg", "bbbh", "bbbi", "bbbj", "bbbk", "bbbl", "bbbm");
		if (null != jpaTemplate.find(User.class, "u" + email.get(0)
				+ "@student.kit.edu")) {
			return;
		}
		for (int i = 0; i < email.size(); i++) {
			User u = new User();
			HashSet<String> s = new HashSet<String>();
			s.add("ROLE_STUDENT");
			// u.setRoles(s);
			u.setEmail("u" + email.get(i) + "@student.kit.edu");
			u.setPassword(passwordEncoder.encodePassword(
					email.get(i) + email.get(i), null));
			jpaTemplate.persist(u);
		}
	}
}
