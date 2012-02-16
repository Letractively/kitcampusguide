package edu.kit.pse.ass.gui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FreeFacilityResult;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.facility.management.RoomQuery;
import edu.kit.pse.ass.gui.model.DataTableParamModel;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;
import edu.kit.pse.ass.gui.model.SearchFormValidator;
import edu.kit.pse.ass.gui.model.SearchResultsModel;

/**
 * The Class SearchController.
 */
@Controller
public class SearchControllerImpl extends MainController implements SearchController {

	/** The booking management. */
	@Inject
	BookingManagement bookingManagement;

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	/** The message source. */
	@Inject
	MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.SearchController#setUpSimpleSearch(org.springframework.ui.Model)
	 */
	@Override
	@RequestMapping(value = "search/simple.html")
	public String setUpSimpleSearch(Model model) {
		prefillSearchForm(model, new SearchFormModel());
		return "search/simple";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.SearchController#setUpAdvancedSearch(org.springframework.ui.Model,
	 * edu.kit.pse.ass.gui.model.SearchFormModel, org.springframework.validation.BindingResult,
	 * edu.kit.pse.ass.gui.model.SearchFilterModel)
	 */
	@Override
	@RequestMapping(value = "search/advanced.html")
	public String setUpAdvancedSearch(Model model, @ModelAttribute SearchFormModel searchFormModel,
			BindingResult sfmResult, @ModelAttribute SearchFilterModel searchFilterModel) {

		// Validate form
		SearchFormValidator sfmValidator = new SearchFormValidator();
		sfmValidator.validate(searchFormModel, sfmResult);

		prefillSearchForm(model, searchFormModel);

		model.addAttribute("searchFilterModel", searchFilterModel);
		model.addAttribute("filterList", facilityManagement.getAvailablePropertiesOf(Room.class));

		// Additional CSS / JS files
		String[] cssFiles = { "/css/advancedSearch.css", "/libs/datatables/css/datatable.css",
				"/libs/datatables/css/datatable_jui.css", "/libs/datatables/themes/base/jquery-ui.css" };
		String[] jsFiles = { "/libs/datatables/jquery.dataTables.min.js", "/scripts/advancedSearch.js" };
		model.addAttribute("cssFiles", cssFiles);
		model.addAttribute("jsFiles", jsFiles);

		return "search/advanced";
	}

	/**
	 * Prefill search form.
	 * 
	 * @param searchFormModel
	 *            the SearchFormModel
	 * @param model
	 *            the spring model
	 */
	private void prefillSearchForm(Model model, SearchFormModel searchFormModel) {
		model.addAttribute("searchFormModel", searchFormModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.gui.controller.SearchController#listSearchResults(javax.servlet.http.HttpServletRequest,
	 * edu.kit.pse.ass.gui.model.SearchFormModel, org.springframework.validation.BindingResult,
	 * edu.kit.pse.ass.gui.model.SearchFilterModel)
	 */
	@Override
	@RequestMapping(value = "search/results")
	@Transactional
	public @ResponseBody
	SearchResultsModel listSearchResults(HttpServletRequest request,
			@ModelAttribute SearchFormModel searchFormModel, BindingResult sfmResult,
			@ModelAttribute SearchFilterModel searchFilterModel) {

		// Validate form and correct to standard values, if input is invalid
		SearchFormValidator sfmValidator = new SearchFormValidator();
		sfmValidator.validate(searchFormModel, sfmResult);

		// Get DataTable Parameters
		DataTableParamModel parameters = new DataTableParamModel(request);

		// Execute search
		List<FreeFacilityResult> searchResults = executeSearch(searchFormModel, searchFilterModel);

		// Sort results
		sortResults(searchResults, parameters);

		// show requested part of results
		clipRequestedResults(searchResults, parameters);

		// Format search results for output
		Collection<Collection<String>> searchResultsData = createResultList(searchResults);

		// Create search results model
		SearchResultsModel searchResultsModel = new SearchResultsModel(parameters.getsEcho(),
				searchResults.size(), searchResults.size(), searchResultsData);

		// add possible error messages to model
		addErrors(searchResultsModel, sfmResult);

		return searchResultsModel;
	}

	/**
	 * adds form errors of the specified search form model to the specified search results model.
	 * 
	 * @param searchResultsModel
	 *            the search results model
	 * @param sfmResult
	 *            the search form model result
	 */
	private void addErrors(SearchResultsModel searchResultsModel, BindingResult sfmResult) {
		Collection<String> errors = new ArrayList<String>();
		for (ObjectError error : sfmResult.getAllErrors()) {
			String errorMessage = "";

			try {
				errorMessage = messageSource.getMessage(error.getCode(), null, LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException e) {
				errorMessage = "Ein Fehler ist aufgetreten.";
			}

			errors.add(errorMessage);
		}
		searchResultsModel.setAsErrors(errors);
	}

	/**
	 * removes search results from the given list that are not to be shown by DataTables. The given DataTables
	 * parameters give the information which results are to be shown.
	 * 
	 * @param searchResults
	 *            the search results
	 * @param parameters
	 *            the DataTables parameters
	 */
	private void clipRequestedResults(List<FreeFacilityResult> searchResults, DataTableParamModel parameters) {
		if (searchResults.size() < parameters.getiDisplayStart() + parameters.getiDisplayLength()) {
			searchResults = searchResults.subList(parameters.getiDisplayStart(), searchResults.size());
		} else {
			searchResults = searchResults.subList(parameters.getiDisplayStart(), parameters.getiDisplayStart()
					+ parameters.getiDisplayLength());
		}
	}

	/**
	 * executes the search using the data from the given form models
	 * 
	 * @param searchFormModel
	 *            the search form model
	 * @param searchFilterModel
	 *            the filter form model
	 * @return the search results
	 */
	private List<FreeFacilityResult> executeSearch(SearchFormModel searchFormModel,
			SearchFilterModel searchFilterModel) {

		// Parameters for search
		Date searchStart = searchFormModel.getStart();
		Date searchEnd = searchFormModel.getEnd();
		String searchText = searchFormModel.getSearchText();
		int workplaceCount = searchFormModel.getWorkplaceCount();
		boolean wholeRoom = searchFormModel.isWholeRoom();
		Collection<Property> properties = searchFilterModel.getFilters();

		// get search results collection
		RoomQuery roomQuery = new RoomQuery(properties, searchText, workplaceCount);
		Collection<FreeFacilityResult> searchResultsCollection = bookingManagement.findFreeFacilites(roomQuery,
				searchStart, searchEnd, wholeRoom);

		// convert collection to list
		List<FreeFacilityResult> searchResults;
		if (searchResultsCollection instanceof List) {
			searchResults = (List<FreeFacilityResult>) searchResultsCollection;
		} else {
			searchResults = new ArrayList<FreeFacilityResult>(searchResultsCollection);
		}

		return searchResults;
	}

	/**
	 * sorts the specified search results according to the given DataTables parameters
	 * 
	 * @param results
	 *            the results to sort
	 * @param parameters
	 *            the DataTable parameters
	 */
	private void sortResults(List<FreeFacilityResult> results, DataTableParamModel parameters) {

		int sortColumnIndex = parameters.getiSortColumnIndex();

		// Get comparator for the column index
		Comparator<FreeFacilityResult> comparator = new FacilityResultComparator(sortColumnIndex);

		// revert comparator if necessary
		if (!parameters.getsSortDirection().equals("asc")) {
			comparator = Collections.reverseOrder(comparator);
		}

		Collections.sort(results, comparator);
	}

	/**
	 * creates a String collection containing the search results for DataTables
	 * 
	 * @param searchResults
	 *            the search results for the output
	 * @return String collection containing the search results for DataTables
	 */
	private Collection<Collection<String>> createResultList(List<FreeFacilityResult> searchResults) {

		Collection<Collection<String>> results = new ArrayList<Collection<String>>();

		for (FreeFacilityResult c : searchResults) {
			if (c.getFacility() instanceof Room) {
				Room room = (Room) c.getFacility();
				Collection<String> rowData = createResultListRow(room, c.getStart());
				results.add(rowData);
			}
		}
		return results;
	}

	/**
	 * creates a String Collection containing the data of one row in the search results for DataTables.
	 * 
	 * This row contains the data of the given room and start date
	 * 
	 * @param room
	 *            the room
	 * @param startDate
	 *            the start date
	 * @return the row with the data
	 */
	private Collection<String> createResultListRow(Room room, Date startDate) {

		Collection<String> rowData = new ArrayList<String>();
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

		// output for this row
		String roomName = "", buildingName = "", equipment = "", startTime = "", roomID = "";

		if (room != null) {
			roomName = formatRoomName(room);
			roomID = room.getId();
			if (room.getParentFacility() != null) {
				buildingName = room.getParentFacility().getName();
			}
			equipment = searchResultsFormatEquipment(room);
		}
		if (startDate != null) {
			startTime = formatTime.format(startDate);
		}

		rowData.add(roomName);
		rowData.add(buildingName);
		rowData.add(equipment);
		rowData.add(startTime);
		rowData.add(roomID);

		return rowData;
	}

	/**
	 * formats the output for the equipment of the given Room for the search Results
	 * 
	 * @param room
	 * @return
	 */
	private String searchResultsFormatEquipment(Room room) {
		String output = "";

		// TODO replace text with icons
		boolean equipmentListStart = true;
		for (Property p : room.getProperties()) {
			if (equipmentListStart) {
				equipmentListStart = false;
			} else {
				output += ", ";
			}
			output += p.getName();
		}
		return output;
	}

	/**
	 * returns a formatted room name of the given Room.
	 * 
	 * if the given Room is null, an empty String is returned.
	 * 
	 * @param room
	 *            the room
	 * @return the formatted room name of the given Room
	 */
	public static String formatRoomName(Room room) {
		String roomName = "";
		if (room != null) {
			if (room.getName().isEmpty()) {
				roomName = "Raum";
			} else {
				roomName = room.getName();
			}
			if (!room.getNumber().isEmpty()) {
				roomName += " " + room.getNumber();
			}
		}
		return roomName;

	}
}
