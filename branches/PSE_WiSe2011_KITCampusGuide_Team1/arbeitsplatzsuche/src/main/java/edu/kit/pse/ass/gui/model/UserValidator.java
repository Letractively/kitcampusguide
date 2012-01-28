package edu.kit.pse.ass.gui.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.kit.pse.ass.entity.User;

/**
 * 
 * TODO: author?
 * 
 * @author ?
 * 
 */
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if (target instanceof User) {
			User user = (User) target;

			if (user.getPassword().length() < 8) {
				errors.rejectValue("password", "password.tooShort");
			}
			// TODO validate E-Mail
		}
	}
}
