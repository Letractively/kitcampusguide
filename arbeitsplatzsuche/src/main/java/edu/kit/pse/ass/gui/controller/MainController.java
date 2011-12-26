package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
public abstract class MainController {

	/**
	 * handles illegal requests, e.g. calling non-existent pages
	 */
	protected void handleIllegalRequest() {

	}

	/**
	 * sets up the page header
	 * 
	 * @param model
	 */
	protected void setUpHeader(Model model) {

	}

}
