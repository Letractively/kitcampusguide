package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class UserTest.
 */
public class UserTest {

	/** The user. */
	User user;

	/** The TES t_ role. */
	private static String TEST_ROLE = "student";

	/** The TES t_ email. */
	private static String TEST_EMAIL = "uaaaa@student.kit.edu";

	/** The TES t_ pw. */
	private static String TEST_PW = "pwWithMin8Chars";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	/**
	 * Test set role.
	 */
	@Test
	public void testSetRole() {
		HashSet<String> roles = new HashSet<String>();
		roles.add(TEST_ROLE);
		user.setRoles(roles);
		assertEquals(roles, user.getRoles());
	}

	/**
	 * Test set email.
	 */
	@Test
	public void testSetEmail() {
		user.setEmail(TEST_EMAIL);
		assertEquals(TEST_EMAIL, user.getEmail());
	}

	/**
	 * Test set password.
	 */
	@Test
	public void testSetPassword() {
		user.setPassword(TEST_PW);
		assertEquals(TEST_PW, user.getPassword());
	}

}
