package edu.kit.pse.ass.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

/**
 * Test for UserDAOImpl
 * 
 * @author Oliver Schneider
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOImplTest {

	@Autowired
	private UserDAO users;

	private static final String TEST_EMAIL1 = "uaaaa@student.kit.edu";
	private static final String TEST_PW1 = "pw11111111";

	@Test
	public void testInsertUser() {
		User user = users.insertUser(TEST_EMAIL1, TEST_PW1);
		assertNotNull(user);
		assertEquals(TEST_EMAIL1, user.getEmail());
		assertEquals(TEST_PW1, user.getPassword());

		User gettedUser = users.getUser(TEST_EMAIL1);
		assertNotNull(gettedUser);
		assertEquals(user, gettedUser);
	}

	@Test
	public void testGetUser() {
		User user = users.insertUser(TEST_EMAIL1, TEST_PW1);
		User gettedUser = users.getUser(TEST_EMAIL1);
		assertNotNull(gettedUser);
		assertEquals(user, gettedUser);
	}

	@Test
	public void testDeleteUser() {
		User user = users.insertUser(TEST_EMAIL1, TEST_PW1);
		assertNotNull(user);

		users.deleteUser(TEST_EMAIL1);

		User gettedUser = users.getUser(TEST_EMAIL1);
		assertNull(gettedUser);
	}

}
