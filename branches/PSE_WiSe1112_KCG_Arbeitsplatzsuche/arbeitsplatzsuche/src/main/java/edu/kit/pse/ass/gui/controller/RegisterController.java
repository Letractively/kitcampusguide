package edu.kit.pse.ass.gui.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.gui.model.UserValidator;
import edu.kit.pse.ass.user.management.UserAlreadyExistsException;
import edu.kit.pse.ass.user.management.UserManagement;

/**
 * The RegisterController sets up the register page and registers new users.
 */
@Controller
public class RegisterController extends MainController {

	/** The user management. */
	@Inject
	UserManagement userManagement;

	/**
	 * Sets up the register form.
	 * 
	 * @param model
	 *            the spring model
	 * @return the view path
	 */
	@RequestMapping(value = "register.html", method = RequestMethod.GET)
	public String setUpRegisterForm(Model model) {
		model.addAttribute("registerUser", new User());
		return "register/register";
	}

	/**
	 * Is called if the register form is submitted. Checks the form for errors and registers the new user.
	 * 
	 * @param model
	 *            the spring model
	 * @param user
	 *            the user data of the register form
	 * @param userResult
	 *            binding result for errors
	 * @return the view path
	 */
	@RequestMapping(value = "register.html", method = RequestMethod.POST)
	public String registerUser(Model model, @ModelAttribute("registerUser") User user, BindingResult userResult) {

		String nextView = "register/register";

		// Validate form
		UserValidator userValidator = new UserValidator();
		userValidator.validate(user, userResult);

		if (userResult.hasErrors()) {
			nextView = "register/register";
			/*
			 * // temp: print error codes for (FieldError e : userResult.getFieldErrors()) {
			 * System.out.println("FieldError Code " + e.getCode() + ", Field " + e.getField() + "," + e.getClass() +
			 * ", Object " + e.getObjectName()); if (e.getCodes() != null) for (String code : e.getCodes())
			 * System.out.println(" + Code " + code); if (e.getArguments() != null) for (Object arg : e.getArguments())
			 * System.out.println(" + Argu " + arg);
			 * 
			 * }
			 */
		} else {
			String userID = "";
			try {
				userID = userManagement.register(user.getEmail(), user.getPassword());
			} catch (UserAlreadyExistsException e) {
				e.printStackTrace();
				model.addAttribute("userExists", true);
				return "register/register";
			}
			if (!userID.isEmpty()) {
				// Show success message in login form
				model.addAttribute("registerSuccess", true);
				nextView = "login";
			}
		}
		return nextView;
	}
}
