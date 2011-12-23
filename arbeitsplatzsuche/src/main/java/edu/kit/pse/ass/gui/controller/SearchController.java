package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

	@RequestMapping(value = "search/simple")
	public String setUpSimpleSearch(Model model) {
		preFillSearchForm(model);
		return "search/simple";
	}

	@RequestMapping(value = "search/advanced")
	public String setUpAdvancedSearch(Model model) {
		preFillSearchForm(model);
		return "search/advanced";
	}

	private void preFillSearchForm(Model model) {
		// TODO: Implement
	}

	public String listSearchResults(Model model) {
		return "json";
	}

}
