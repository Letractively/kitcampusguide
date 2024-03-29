package edu.kit.pse.ass.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.User;

/**
 * Test for UserDAOImpl.
 * 
 * @author Oliver Schneider
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOImplTest {

	/** The users. */
	@Autowired
	private UserDAO users;

	/** The Constant persistedUser. */
	private User persistedUser;

	/** The Constant TEST_PERSISTEDMAIL. */
	private static final String TEST_PERSISTEDMAIL = "upers@student.kit.edu";

	/** The Constant TEST_EMAIL1. */
	private static final String TEST_EMAIL1 = "uaaaa@student.kit.edu";

	/** The Constant TEST_PW1. */
	private static final String TEST_PW1 = "pw11111111";

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		persistedUser = users.insertUser(TEST_PERSISTEDMAIL, "pw2453565");
	}

	/**
	 * Test insert with first parameter null
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInsertNull1() throws Exception {
		users.insertUser(null, TEST_PW1);
	}

	/**
	 * Test insert with second parameter null
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInsertNull2() throws Exception {
		users.insertUser(TEST_EMAIL1, null);
	}

	/**
	 * Test insert with first parameter empty
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInsertEmpty1() throws Exception {
		users.insertUser("", TEST_PW1);
	}

	/**
	 * Test insert with second parameter empty
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInsertEmpty2() throws Exception {
		users.insertUser(TEST_EMAIL1, "");
	}

	/**
	 * Test insert user.
	 */
	@Test
	public void testInsertUser() {
		User user = users.insertUser(TEST_EMAIL1, TEST_PW1);
		assertNotNull(user);
		assertEquals("E-Mail is wrong", TEST_EMAIL1, user.getEmail());
		assertEquals("Passwordhash is wrong", TEST_PW1, user.getPassword());

		User gettedUser = users.getUser(TEST_EMAIL1);
		assertNotNull("User was not saved", gettedUser);
		assertEquals("Stored user is not equal", user, gettedUser);
	}

	/**
	 * Test getUser with parameter null
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetNull() throws Exception {
		users.getUser(null);
	}

	/**
	 * Test getUser with parameter null
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetEmpty() throws Exception {
		users.getUser("");
	}

	/**
	 * Test get user.
	 */
	@Test
	public void testGetUser() {
		User gettedUser = users.getUser(TEST_PERSISTEDMAIL);
		assertNotNull("User does not exist", gettedUser);
		assertEquals("User is not equal", persistedUser, gettedUser);
	}

	/**
	 * Test deleteUser with parameter null
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNull() throws Exception {
		users.deleteUser(null);
	}

	/**
	 * Test deleteUser with parameter null
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteEmpty() throws Exception {
		users.deleteUser("");
	}

	/**
	 * Test delete user.
	 */
	@Test
	public void testDeleteUser() {
		User user = null;
		user = users.getUser(TEST_PERSISTEDMAIL);
		assertNotNull("User did not exist", user);

		users.deleteUser(TEST_PERSISTEDMAIL);

		User gettedUser = users.getUser(TEST_PERSISTEDMAIL);
		assertNull("User was not deleted", gettedUser);
	}

	/**
	 * Test remove a removed user
	 */
	@Test
	public void testRemoveRemoved() {
		User user = null;
		user = users.getUser(TEST_PERSISTEDMAIL);
		assertNotNull("User did not exist", user);

		users.deleteUser(TEST_PERSISTEDMAIL);
		users.deleteUser(TEST_PERSISTEDMAIL);
	}

	/**
	 * Test UsernameNotFoundException
	 * 
	 * @throws Exception
	 *             should be UsernameNotFoundException
	 */
	@Test(expected = UsernameNotFoundException.class)
	public void testUsernameNotFoundException() throws Exception {
		users.loadUserByUsername("Peter");
	}

	/**
	 * Test load users by username
	 */

	@Test
	public void testLoadUserByUsername() {
		UserDetails userDets = null;
		userDets = users.loadUserByUsername(TEST_PERSISTEDMAIL);
		assertTrue(userDets.getPassword().equals("pw2453565"));
	}

}
