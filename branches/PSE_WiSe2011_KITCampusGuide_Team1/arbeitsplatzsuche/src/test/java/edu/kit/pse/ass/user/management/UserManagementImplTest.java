package edu.kit.pse.ass.user.management;

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

	/**
	 * the autowired userMangement
	 */
	@Autowired
	UserManagement userManagement;

	/**
	 * sets the up
	 */
	@Before
	public void setUp() {

	}

	/**
	 * test for registers method
	 */
	@Test
	public void testRegister() {
		// TODO
	}

	/**
	 * test get user
	 */
	@Test
	public void testGetUser() {
		// TODO
	}

	/**
	 * test the exception
	 */
	@Test(expected = UserAlreadyExistsException.class)
	public void testUserAlreadyExistsException() {
		// TODO add a user to check already exists exception
	}
}
