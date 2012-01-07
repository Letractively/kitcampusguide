package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This Controller provides basic functionality used in all GUI controllers.
 * 
 * All other GUI controllers use this functionality by subclassing from
 * MainController and calling its methods.
 * 
 * @author Jannis Koch
 * 
 */
@Controller
public class LoginController extends MainController {

	/**
	 * Login.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "login")
	public String login(Model model) {
		return "login";
	}
}
