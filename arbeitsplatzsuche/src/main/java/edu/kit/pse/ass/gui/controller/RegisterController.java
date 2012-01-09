package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.pse.ass.entity.User;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterController.
 */
@Controller
public class RegisterController extends MainController {

	/**
	 * Sets the up register form.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "register.html", method = RequestMethod.GET)
	public String setUpRegisterForm(Model model) {
		model.addAttribute("registerUser", new User());
		return "register/register";
	}

	/**
	 * Register user.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	public String registerUser(Model model) {
		return "";
	}
}
