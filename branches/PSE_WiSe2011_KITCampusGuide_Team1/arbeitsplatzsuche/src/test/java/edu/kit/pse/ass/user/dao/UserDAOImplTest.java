package edu.kit.pse.ass.user.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.ass.entity.User;

/**
 * Test for UserDAOImpl
 * 
 * @author Oliver Schneider
 */
public class UserDAOImplTest {

	// TODO autowire!
	private UserDAO users;

	private static final String TEST_EMAIL1 = "uaaaa@student.kit.edu";
	private static final String TEST_PW1 = "pw11111111";

	@Before
	public void setUp() throws Exception {
		users = new UserDAOImpl();
	}

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
	public void testDeleteUser() {
		User user = users.insertUser(TEST_EMAIL1, TEST_PW1);
		assertNotNull(user);

		users.deleteUser(TEST_EMAIL1);

		User gettedUser = users.getUser(TEST_EMAIL1);
		assertNull(gettedUser);
	}

}
