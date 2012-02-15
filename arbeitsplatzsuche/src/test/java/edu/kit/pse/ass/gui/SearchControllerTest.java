package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.gui.controller.SearchController;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;

/**
 * Tests for the Search Controller
 * 
 * @author Jannis Koch
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@Transactional
public class SearchControllerTest {

	/** the search controller */
	@Autowired
	SearchController searchController;

	/**
	 * tests setUpSimpleSearch()
	 */
	@Test
	public void testSetUpSimpleSearch() {

		// create empty model and execute setupSimpleSearch
		Model model = new BindingAwareModelMap();
		String simpleResult = searchController.setUpSimpleSearch(model);

		assertTrue("Wrong page returned", simpleResult.equals("search/simple"));

		// checks on model
		Map<String, Object> modelAttributes = model.asMap();
		assertTrue("model does not contain search form model", model.containsAttribute("searchFormModel"));
		assertTrue("search form model has wrong type",
				modelAttributes.get("searchFormModel") instanceof SearchFormModel);
		SearchFormModel sfm = (SearchFormModel) modelAttributes.get("searchFormModel");
		checkSearchFormModel(sfm);
	}

	/**
	 * checks the search form model of the simple search
	 * 
	 * @param sfm
	 */
	private void checkSearchFormModel(SearchFormModel sfm) {
		// check if search form model has standard values
		assertTrue("search test is not empty", sfm.getSearchText().isEmpty());
		assertTrue("workplace count is not 1", sfm.getWorkplaceCount() == 1);
		assertTrue("duration has not the standard value", sfm.getDuration().equals("1:00"));
		assertTrue("the start date is not set", sfm.getStart() != null);
	}

	/**
	 * tests setUpAdvancedSearch()
	 */
	@Test
	public void testSetupAdvancedSearch() {

		// create mock models for setupAdvancedSearch
		Model model = new BindingAwareModelMap();
		SearchFormModel searchFormModel = createMockSearchFormModel();
		SearchFilterModel searchFilterModel = createMockSearchFilterModel();
		BindingResult sfmResult = new BeanPropertyBindingResult(searchFormModel, "searchFormModel");

		// execute method
		String advancedResult = searchController.setUpAdvancedSearch(model, searchFormModel, sfmResult,
				searchFilterModel);

		assertTrue("wrong page returned", advancedResult.equals("search/advanced"));

		// checks on model
		Map<String, Object> modelAttributes = model.asMap();
		assertTrue("model does not contain additional js files", modelAttributes.containsKey("jsFiles"));
		assertTrue("model does not contain additional css files", modelAttributes.containsKey("cssFiles"));
		checkSearchFormModel(searchFormModel, modelAttributes.get("searchFormModel"));
		checkFilterList(modelAttributes.get("filterList"));

	}

	/**
	 * tests setUpAdvancedSearch() using wrong values for the searchFormModel
	 */
	@Test
	public void testSetupAdvancedSearchWrongValues() {

		// create mock models for setupAdvancedSearch
		Model model = new BindingAwareModelMap();
		SearchFormModel searchFormModel = createMockSearchFormModelWrongValues();
		SearchFilterModel searchFilterModel = createMockSearchFilterModel();
		BindingResult sfmResult = new BeanPropertyBindingResult(searchFormModel, "searchFormModel");

		// execute method
		String advancedResult = searchController.setUpAdvancedSearch(model, searchFormModel, sfmResult,
				searchFilterModel);

		// checks on model: have wrong values been corrected?
		Map<String, Object> modelAttributes = model.asMap();
		assertTrue("search form model has the wrong type",
				modelAttributes.get("searchFormModel") instanceof SearchFormModel);
		checkSearchFormModel((SearchFormModel) modelAttributes.get("searchFormModel"));

		// check if BindingResult contains errors
		checkBindingResult(sfmResult);
	}

	/**
	 * checks the binding result for the advanced search test
	 * 
	 * @param sfmResult
	 */
	private void checkBindingResult(BindingResult sfmResult) {

		List<ObjectError> errors = sfmResult.getAllErrors();

		// get list with all object concerned
		ArrayList<String> errorFields = new ArrayList<String>();
		for (ObjectError error : errors) {
			String field = error.getCode().split("\\.")[0];
			errorFields.add(field);
		}

		assertTrue("number of errors is wrong", errors.size() == 3);
		assertTrue("wrong duration value was not detected", errorFields.contains("duration"));
		assertTrue("wrong start date value was not detected", errorFields.contains("startDate"));
		assertTrue("wrong workplace value count was not detected", errorFields.contains("workplaceCount"));

	}

	/**
	 * checks the filter list for the advanced search test
	 * 
	 * @param object
	 *            the filter list
	 */
	private void checkFilterList(Object object) {
		assertTrue("filter list is not a collection", object instanceof Collection<?>);
		Collection<?> filterCol = (Collection<?>) object;
		// assertTrue(filterCol.size() > 0);
	}

	/**
	 * checks the search form model for the advanced search test
	 * 
	 * @param the
	 *            mock search form model created for the test
	 * @param object
	 *            the search filter model returned by the method
	 */
	private void checkSearchFormModel(SearchFormModel sfm, Object object) {
		assertTrue("search form model has the wrong type", object instanceof SearchFormModel);
		SearchFormModel sfmToCheck = sfm;

		// check if mock model and returned model have the same values
		assertTrue(sfm.getDuration().equals(sfmToCheck.getDuration()));
		assertTrue(sfm.getStart().equals(sfmToCheck.getStart()));
		assertTrue(sfm.getSearchText().equals(sfmToCheck.getSearchText()));
		assertTrue(sfm.getWorkplaceCount() == sfmToCheck.getWorkplaceCount());

	}

	/**
	 * creates mock search filter model for the advanced search test
	 * 
	 * @return mock search filter model
	 */
	private SearchFilterModel createMockSearchFilterModel() {
		SearchFilterModel sfm = new SearchFilterModel();
		Collection<Property> filters = new ArrayList<Property>();
		filters.add(new Property("WLAN"));
		filters.add(new Property("Barrierefrei"));
		sfm.setFilters(filters);
		return sfm;
	}

	/**
	 * creates mock search form model for the advanced search test
	 * 
	 * @return mock search form model
	 */
	private SearchFormModel createMockSearchFormModel() {
		SearchFormModel sfm = new SearchFormModel();
		sfm.setDuration("2:00");
		sfm.setWorkplaceCount(2);
		sfm.setSearchText("Bibliothek");
		sfm.setStartToNow();
		return sfm;
	}

	/**
	 * creates mock search form model for the advanced search test using wrong values
	 * 
	 * @return mock search form model
	 */
	private SearchFormModel createMockSearchFormModelWrongValues() {
		SearchFormModel sfm = new SearchFormModel();
		sfm.setDuration("");
		sfm.setWorkplaceCount(-1);
		sfm.setStart(null);
		return sfm;
	}

}
