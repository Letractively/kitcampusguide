package edu.kit.pse.ass.gui.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.validation.BindException;

import edu.kit.pse.ass.entity.User;

/**
 * The Class UserValidatorTest.
 */
public class UserValidatorTest {

	/**
	 * Test validate.
	 */
	@Test
	public void testValidate() {
		User u = new User();
		BindException errors = new BindException(u, "path");

		UserValidator validator = new UserValidator();

		u.setPassword("12345678");

		validator.validate(u, errors);

		assertFalse(errors.hasErrors());
	}

	/**
	 * Test validate password too short.
	 */
	@Test
	public void testValidatePasswordTooShort() {
		User u = new User();
		BindException errors = new BindException(u, "path");

		UserValidator validator = new UserValidator();

		u.setPassword("12345");

		validator.validate(u, errors);

		assertTrue(errors.hasErrors());
	}

}
