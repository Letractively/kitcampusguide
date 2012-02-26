package edu.kit.pse.ass.gui.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public interface LoginController {

	/**
	 * Login.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "login")
	public abstract String login(Model model);

}