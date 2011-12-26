package edu.kit.pse.ass.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.gui.model.SearchFormModel;

@Controller
public class SearchController extends MainController {

	@RequestMapping(value = "search/simple.html")
	public String setUpSimpleSearch(Model model) {
		prefillSearchForm(model);
		return "search/simple";
	}

	@RequestMapping(value = "search/advanced.html")
	public String setUpAdvancedSearch(Model model) {
		prefillSearchForm(model);
		return "search/advanced";
	}

	private void prefillSearchForm(Model model) {
		model.addAttribute("searchFormModel", new SearchFormModel());
	}

	public String listSearchResults(Model model) {
		return "json";
	}

}
