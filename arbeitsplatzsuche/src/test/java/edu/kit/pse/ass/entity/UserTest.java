package edu.kit.pse.ass.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	User user;
	private static String TEST_ID = "1";
	private static String TEST_ROLE = "student";
	private static String TEST_EMAIL = "abc@student.kit.edu";
	private static String TEST_PW = "pwWithMin8Chars";

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void testSetId() {
		user.setId(TEST_ID);
		assertEquals(TEST_ID, user.getId());
	}

	@Test
	public void testSetRole() {
		user.setRole(TEST_ROLE);
		assertEquals(TEST_ROLE, user.getRole());
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
