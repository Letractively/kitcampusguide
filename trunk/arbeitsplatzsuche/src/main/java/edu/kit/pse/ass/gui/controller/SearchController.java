package edu.kit.pse.ass.gui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;
import edu.kit.pse.ass.gui.model.SearchResultsModel;

/**
 * The SearchController handles search requests and shows the search results.
 * 
 * @author Jannis Koch
 * 
 */
public interface SearchController {

	/**
	 * Sets up the SimpleSearchPage.
	 * 
	 * @param model
	 *            the spring model
	 * @return the view path
	 */
	@RequestMapping(value = "search/simple.html")
	String setUpSimpleSearch(Model model);

	/**
	 * Sets up the AdvancedSearchPage
	 * 
	 * @param searchFormModel
	 *            the SearchFormModel filled at the SimpleSearchPage or AdvancedSearchPage.
	 * @param model
	 *            the spring model
	 * @param searchFilterModel
	 *            the SearchFilterModel filled at the AdvancedSearchPage.
	 * @param sfmResult
	 *            the spring BindingResult for error handling
	 * @return the view path
	 */
	@RequestMapping(value = "search/advanced.html")
	String setUpAdvancedSearch(Model model, @ModelAttribute SearchFormModel searchFormModel,
			BindingResult sfmResult, @ModelAttribute SearchFilterModel searchFilterModel);

	/**
	 * JSON-Response for DataTable to list search results.
	 * 
	 * @param request
	 *            the spring HttpServletRequest
	 * @param searchFormModel
	 *            the SearchFormModel filled at the AdvacedSearchPage
	 * @param sfmResult
	 *            the spring BindingResult for error handling
	 * @param searchFilterModel
	 *            the SearchFilterModel filled at the AdvancedSearchPage
	 * @return JSON response containing parameters for DataTables, search results and error messages
	 */
	@RequestMapping(value = "search/results")
	@Transactional
	@ResponseBody
	SearchResultsModel listSearchResults(HttpServletRequest request,
			@ModelAttribute SearchFormModel searchFormModel, BindingResult sfmResult,
			@ModelAttribute SearchFilterModel searchFilterModel);

}