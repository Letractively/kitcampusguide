package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.gui.controller.SearchController;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;
import edu.kit.pse.ass.gui.model.SearchResultsModel;
import edu.kit.pse.ass.realdata.DataHelper;

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

	/** data helper for creating test data */
	@Autowired
	private DataHelper dataHelper;

	/**
	 * creates the data for the tests
	 */
	@Before
	public void createTestData() {

		// create the properties
		Property propertyWLAN = new Property("WLAN");
		Property propertyLAN = new Property("LAN");
		Property propertySteckdose = new Property("Steckdose");

		// create a building with one room containing one work place
		Building building = dataHelper.createPersistedBuilding("50.20", "Informatik", new ArrayList<Property>());
		Room room = dataHelper.createPersistedRoom("Seminarraum", "-101", -1,
				Arrays.asList(propertyWLAN, propertySteckdose, propertyLAN));
		Workplace wp = dataHelper.createPersistedWorkplace("Workplace 1", Arrays.asList(propertyLAN));
		room.addContainedFacility(wp);
		building.addContainedFacility(room);
	}

	/**
	 * creates additional buildings needed for the second listSearchResults test
	 */
	private void createSearchResultsData() {

		Building newBuilding = dataHelper
				.createPersistedBuilding("50.20", "Bibliothek", new ArrayList<Property>());
		for (int i = 0; i < 50; i++) {
			Room room = dataHelper.createPersistedRoom("Seminarraum", "-10" + i, -1, new ArrayList<Property>());
			Workplace wp = dataHelper.createPersistedWorkplace("Arbeitsplatz " + i, new ArrayList<Property>());
			room.addContainedFacility(wp);
			newBuilding.addContainedFacility(room);
		}
	}

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

		assertTrue("wrong page returned", advancedResult.equals("search/advanced"));

		// checks on model: have wrong values been corrected?
		Map<String, Object> modelAttributes = model.asMap();
		assertTrue("search form model has the wrong type",
				modelAttributes.get("searchFormModel") instanceof SearchFormModel);
		checkSearchFormModel((SearchFormModel) modelAttributes.get("searchFormModel"));

		// check if BindingResult contains errors
		checkBindingResult(sfmResult);
	}

	/**
	 * tests listSearchResults()
	 */
	@Test
	public void testListSearchResults() {

		// create mock values for listSearchResults
		SearchFormModel searchFormModel = createMockSearchFormModelListSearchResults();
		SearchFilterModel searchFilterModel = new SearchFilterModel();
		BindingResult sfmResult = new BeanPropertyBindingResult(searchFormModel, "searchFormModel");

		MockHttpServletRequest request = new MockHttpServletRequest();
		setDataTablesParams(request);

		SearchResultsModel resultModel = searchController.listSearchResults(request, searchFormModel, sfmResult,
				searchFilterModel);

		// check result model
		assertTrue("wrong number of search results", resultModel.getiTotalRecords() == 1);
		assertTrue("there are error messages", resultModel.getAsErrors().size() == 0);
		checkResultModel(resultModel, request);
	}

	/**
	 * checks the result model for listSearchResults test
	 * 
	 * @param resultModel
	 *            the result model
	 * @param request
	 *            the request mock
	 */
	private void checkResultModel(SearchResultsModel resultModel, MockHttpServletRequest request) {
		assertTrue("inconsistent number of results", resultModel.getiTotalRecords() == resultModel.getAaData()
				.size());
		assertEquals("sEcho does not match", request.getParameter("sEcho"), resultModel.getsEcho());
		assertEquals("value of total records and displayed records should be the same",
				resultModel.getiTotalDisplayRecords(), resultModel.getiTotalRecords());

		checkAaData(resultModel.getAaData());
	}

	/**
	 * checks the aaData of the SearchResultsModel
	 * 
	 * @param aaData
	 */
	private void checkAaData(Collection<Collection<String>> aaData) {
		// check if cells are empty
		for (Collection<String> row : aaData) {
			assertTrue(row.size() == 5);
			int i = 0;
			for (String cell : row) {
				if (i != 2) {
					// third cell, the equipment, can be empty
					assertTrue("value in search results is empty", !cell.isEmpty());
				}
				i++;
			}
		}
	}

	/**
	 * tests listSearchResults()
	 */
	@Test
	public void testListSearchResultsWrongValues() {

		createSearchResultsData();

		// create mock values for listSearchResults
		SearchFormModel searchFormModel = createMockSearchFormModelWrongValues();
		SearchFilterModel searchFilterModel = new SearchFilterModel();
		BindingResult sfmResult = new BeanPropertyBindingResult(searchFormModel, "searchFormModel");
		MockHttpServletRequest request = new MockHttpServletRequest();
		setDataTablesParams(request);

		// sort room name in descending order
		request.setParameter("sSortDir_0", "desc");
		request.setParameter("iSortCol_0", "0");

		SearchResultsModel resultModel = searchController.listSearchResults(request, searchFormModel, sfmResult,
				searchFilterModel);

		// check result model
		assertTrue("wrong number of search results", resultModel.getiTotalRecords() == 51);
		checkResultModel(resultModel, request);
		checkErrorMessages(resultModel.getAsErrors());
		checkSorting(resultModel.getAaData());

	}

	/**
	 * checks the sorting for the listSearchResults test
	 * 
	 * @param aaData
	 *            the search results
	 */
	private void checkSorting(Collection<Collection<String>> aaData) {

		String lastValue = null;
		for (Collection<String> row : aaData) {
			String roomName = row.iterator().next();
			if (lastValue != null) {
				// check if sorted descendingly
				assertTrue("search results are not sorted", roomName.compareTo(lastValue) <= 0);
			}
			lastValue = roomName;
		}
	}

	/**
	 * checks the error messages of the result model in testListSearchResult()
	 * 
	 * @param asErrors
	 *            the error messages
	 */
	private void checkErrorMessages(Collection<String> asErrors) {
		assertTrue("wrong number of error messages", asErrors.size() == 3);
		for (String msg : asErrors) {
			assertFalse("error message is empty", msg.isEmpty());
		}
	}

	/**
	 * sets parameters for DataTables in the given request object
	 * 
	 * @param request
	 *            the request object
	 */
	private void setDataTablesParams(MockHttpServletRequest request) {
		request.setParameter("sEcho", "1");
		request.setParameter("iColumns", "5");
		request.setParameter("iDisplayStart", "0");
		request.setParameter("iDisplayLength", "40");
		request.setParameter("iSortingCols", "1");
		request.setParameter("iSortColumnIndex", "3");
		request.setParameter("sSearch", "");
		request.setParameter("sSortDirection", "asc");
		request.setParameter("sSortDir_0", "asc");
		request.setParameter("sColumns", "1");
		request.setParameter("iSortCol_0", "3");
	}

	/**
	 * checks the binding result for the advanced search test
	 * 
	 * @param sfmResult
	 */
	private void checkBindingResult(BindingResult sfmResult) {

		List<ObjectError> errors = sfmResult.getAllErrors();

		// get list with all error fields concerned
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
		assertTrue(filterCol.size() == 3);
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
	 * creates mock search form model for the advanced search test
	 * 
	 * @return mock search form model
	 */
	private SearchFormModel createMockSearchFormModelListSearchResults() {
		SearchFormModel sfm = new SearchFormModel();
		sfm.setDuration("1:00");
		sfm.setWorkplaceCount(1);
		sfm.setSearchText("");
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
