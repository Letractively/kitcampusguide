package edu.kit.pse.ass.gui.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Provides the method that sets up the LoginForm.
 * 
 * @author Jannis Koch
 * 
 */
public interface LoginController {

	/**
	 * Sets up the LoginForm.
	 * 
	 * @param model
	 *            the spring model
	 * @return the login form view
	 */
	@RequestMapping(value = "login")
	String login(Model model);

}