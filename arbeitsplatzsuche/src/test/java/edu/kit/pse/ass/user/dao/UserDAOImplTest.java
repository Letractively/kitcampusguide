package edu.kit.pse.ass.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.User;

// TODO: Auto-generated Javadoc
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
	private static User persistedUser;

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
	 * Test get user.
	 */
	@Test
	public void testGetUser() {
		User gettedUser = users.getUser(TEST_PERSISTEDMAIL);
		assertNotNull("User does not exist", gettedUser);
		assertEquals("User is not equal", persistedUser, gettedUser);
	}

	/**
	 * Test delete user.
	 */
	@Test
	public void testDeleteUser() {
		User user = users.getUser(TEST_PERSISTEDMAIL);
		assertNotNull("User did not exist", user);
		user = null;

		users.deleteUser(TEST_PERSISTEDMAIL);

		User gettedUser = users.getUser(TEST_PERSISTEDMAIL);
		assertNull("User was not deleted", gettedUser);
	}

}
