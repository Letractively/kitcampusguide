package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	User user;
	private static String TEST_ID = "1";
	private static String TEST_ROLE = "student";
	private static String TEST_EMAIL = "uaaaa@student.kit.edu";
	private static String TEST_PW = "pwWithMin8Chars";

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void testSetRole() {
		HashSet<String> roles = new HashSet<String>();
		roles.add(TEST_ROLE);
		user.setRoles(roles);
		assertEquals(roles, user.getRoles());
	}

	@Test
	public void testSetEmail() {
		user.setEmail(TEST_EMAIL);
		assertEquals(TEST_EMAIL, user.getEmail());
	}

	@Test
	public void testSetPassword() {
		user.setPassword(TEST_PW);
		assertEquals(TEST_PW, user.getPassword());
	}

}
