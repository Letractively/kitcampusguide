package edu.kit.pse.ass.user.management;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

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
import edu.kit.pse.ass.realdata.DataHelper;

/**
 * The Class UserManagementImplTest.
 * 
 * @author Lennart
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserManagementImplTest {

	/** the autowired userMangement */
	@Autowired
	UserManagement userManagement;

	/** the dataHelper */
	@Autowired
	DataHelper dataHelper;

	/** The email of the user. */
	private static final String USER_ID = "ubobh@student.kit.edu";

	/** The password of the user. */
	private static final String USER_PW = "hydra";

	/** The roles of the user */
	private static HashSet<String> role = new HashSet<String>();

	/** The user */
	private static User user;

	/**
	 * sets the up
	 */
	@Before
	public void setUp() {
		role.add("Student");
	}

	/**
	 * Nulltest first register parameter
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterNull1() throws Exception {
		userManagement.register(null, USER_PW);
	}

	/**
	 * Nulltest second register parameter
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterNull2() throws Exception {
		userManagement.register(USER_ID, null);
	}

	/**
	 * test for register method
	 * 
	 * @throws Exception
	 *             no exception should be thrown
	 */
	@Test
	public void testRegister() throws Exception {
		String id = null;
		id = userManagement.register(USER_ID, USER_PW);
		assertNotNull("No id returned", id);
		assertFalse("Id was empty", id.isEmpty());
		assertTrue("The db id and the original email aren't equal", id.equals(USER_ID));
	}

	/**
	 * Nulltest getUser parameter
	 * 
	 * @throws Exception
	 *             - an IllegalArgumentException should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetUserNull() throws Exception {
		userManagement.getUser(null);
	}

	/**
	 * test get user
	 */
	@Test
	public void testGetUser() {
		User returned = null;
		user = dataHelper.createPersistedUser(USER_ID, USER_PW, role);
		returned = userManagement.getUser(user.getEmail());
		assertNotNull("No user returned", returned);
		assertTrue("Emails don't match", returned.getEmail().equals(user.getEmail()));
		returned = null;
		returned = userManagement.getUser(USER_ID);
		assertNotNull("No user returned", returned);
		assertTrue("Emails don't match", returned.getEmail().equals(USER_ID));
	}

	/**
	 * test UserAlreadyExistsException
	 * 
	 * @throws Exception
	 *             should be UserAlreadyExistsException
	 */
	@Test(expected = UserAlreadyExistsException.class)
	public void testUserAlreadyExistsException() throws Exception {
		user = dataHelper.createPersistedUser(USER_ID, USER_PW, role);
		userManagement.register(user.getEmail(), USER_PW);
	}
}
