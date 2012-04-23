package edu.kit.pse.ass.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class UserTest.
 */
public class UserTest {

	/** The user. */
	User user;

	/** The test role. */
	private static final String TEST_ROLE = "student";

	/** The test email. */
	private static final String TEST_EMAIL = "uaaaa@student.kit.edu";

	/** The test pw. */
	private static final String TEST_PW = "pwWithMin8Chars";

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
	@Test(expected = IllegalArgumentException.class)
	public void testSetEmail() {
		user.setEmail(null);
	}

	/**
	 * Test set email.
	 */
	@Test
	public void testSetEmail2() {
		user.setEmail(TEST_EMAIL);
		assertEquals(TEST_EMAIL, user.getEmail());
	}
	
	/**
	 * Test set email.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetEmail3() {
		user.setEmail("NOT@ALLOW.ED");
	}
	
	/**
	 * Test set password.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPassword() {
		user.setPassword(null);
	}
	
	/**
	 * Test set password.
	 */
	@Test
	public void testSetPassword2() {
		user.setPassword(TEST_PW);
		assertEquals(TEST_PW, user.getPassword());
	}
	
	/**
	 * Test get authorities.
	 */
	@Test
	public void testGetAuthorities() {
		User u1 = new User();
		u1.setRoles(null);
		assertEquals(u1.getAuthorities(), null);
	}
	
	/**
	 * Test get authorities.
	 */
	@Test
	public void testGetAuthorities2() {
		User u1 = new User();
		HashSet<String> roles = new HashSet<String>();
		roles.add("TEST_ROLE");
		u1.setRoles(roles);
		assertEquals(u1.getAuthorities().size(), 1);
	}
	
	/**
	 * Test get username.
	 */
	@Test
	public void testGetUsername() {
		user.setEmail(TEST_EMAIL);
		assertEquals(user.getUsername(), TEST_EMAIL);
	}
	
	/**
	 * Test is account non expired 
	 */
	@Test
	public void testIsAccountNonExpired() {
		assertTrue(user.isAccountNonExpired());
	}
	
	/**
	 * Test is account non locked 
	 */
	@Test
	public void testIsAccountNonLocked() {
		assertTrue(user.isAccountNonLocked());
	}
	
	/**
	 * Test is credentials non expired 
	 */
	@Test
	public void testIsCredentialsNonExpired() {
		assertTrue(user.isCredentialsNonExpired());
	}
	
	/**
	 * Test is enabled 
	 */
	@Test
	public void testIsEnabled() {
		assertTrue(user.isEnabled());
	}
}
