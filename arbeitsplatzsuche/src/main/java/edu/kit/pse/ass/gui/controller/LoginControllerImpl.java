package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Login Controller
 * 
 * @author Jannis Koch
 * 
 */
@Controller
public class LoginControllerImpl extends MainController implements LoginController {

	/* (non-Javadoc)
	 * @see edu.kit.pse.ass.gui.controller.LoginController#login(org.springframework.ui.Model)
	 */
	@Override
	@RequestMapping(value = "login")
	public String login(Model model) {
		return "login";
	}
}
