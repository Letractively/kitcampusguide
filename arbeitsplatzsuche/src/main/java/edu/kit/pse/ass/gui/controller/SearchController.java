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
public class SearchController extends MainController {

	/** The booking management. */
	@Inject
	BookingManagement bookingManagement;

	/** The facility management. */
	@Inject
	FacilityManagement facilityManagement;

	/** The message source. */
	@Inject
	MessageSource messageSource;

	/**
	 * Sets up the SimpleSearchPage.
	 * 
	 * @param model
	 *            the spring model
	 * @return the view path
	 */
	@RequestMapping(value = "search/simple.html")
	public String setUpSimpleSearch(Model model) {
		prefillSearchForm(model, new SearchFormModel());
		return "search/simple";
	}

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
	public @ResponseBody
	SearchResultsModel listSearchResults(HttpServletRequest request,
			@ModelAttribute SearchFormModel searchFormModel, BindingResult sfmResult,
			@ModelAttribute SearchFilterModel searchFilterModel) {

		// Validate form (Validator sets invalid values to standard values, so search can be executed in any case.)
		SearchFormValidator sfmValidator = new SearchFormValidator();
		sfmValidator.validate(searchFormModel, sfmResult);

		// Get DataTable Parameters
		DataTableParamModel parameters = new DataTableParamModel(request);

		// Parameters for search
		Date searchStart = searchFormModel.getStart();
		Date searchEnd = searchFormModel.getEnd();
		String searchText = searchFormModel.getSearchText();
		int workplaceCount = searchFormModel.getWorkplaceCount();
		boolean wholeRoom = searchFormModel.isWholeRoom();
		Collection<Property> properties = searchFilterModel.getFilters();

		// Execute search
		RoomQuery roomQuery = new RoomQuery(properties, searchText, workplaceCount);
		Collection<FreeFacilityResult> searchResultsCollection = bookingManagement.findFreeFacilites(roomQuery,
				searchStart, searchEnd, wholeRoom);

		List<FreeFacilityResult> searchResults;
		if (searchResultsCollection instanceof List) {
			searchResults = (List<FreeFacilityResult>) searchResultsCollection;
		} else {
			searchResults = new ArrayList<FreeFacilityResult>(searchResultsCollection);
		}

		// Sort results
		final int sortColumnIndex = parameters.getiSortColumnIndex();
		final int sortDirection = parameters.getsSortDirection().equals("asc") ? 1 : -1;
		sortResults(searchResults, sortColumnIndex, sortDirection);

		// show requested part of results
		if (searchResults.size() < parameters.getiDisplayStart() + parameters.getiDisplayLength()) {
			searchResults = searchResults.subList(parameters.getiDisplayStart(), searchResults.size());
		} else {
			searchResults = searchResults.subList(parameters.getiDisplayStart(), parameters.getiDisplayStart()
					+ parameters.getiDisplayLength());
		}

		// Format search results for output
		Collection<Collection<String>> searchResultsData = createResultList(searchResults);

		// Create model for search results
		SearchResultsModel searchResultsModel = new SearchResultsModel(parameters.getsEcho(),
				searchResults.size(), searchResults.size(), searchResultsData);

		// in case of errors: add error messages to model
		if (sfmResult.hasErrors()) {
			Collection<String> asErrors = new ArrayList<String>();
			for (ObjectError error : sfmResult.getAllErrors()) {
				asErrors.add(messageSource.getMessage(error.getCode(), null, null));
			}
			searchResultsModel.setAsErrors(asErrors);
		}

		return searchResultsModel;
	}

	/**
	 * sorts the specified search results according to the given column index and sortDirection.
	 * 
	 * @param results
	 *            the results to sort
	 * @param sortColumnIndex
	 *            the index of the column to sort
	 * @param sortDirection
	 *            the sort direction, must be 1 or -1
	 */
	private void sortResults(List<FreeFacilityResult> results, final int sortColumnIndex, final int sortDirection) {
		Collections.sort(results, new Comparator<FreeFacilityResult>() {
			@Override
			public int compare(FreeFacilityResult c1, FreeFacilityResult c2) {
				switch (sortColumnIndex) {
				case 0: // Room name
					if (c1.getFacility() instanceof Room && c2.getFacility() instanceof Room) {
						// Both facilities are rooms - compare the formatted name for a room (this is the name shown in
						// DataTables!)
						Room r1 = (Room) c1.getFacility();
						Room r2 = (Room) c2.getFacility();
						return formatRoomName(r1).compareTo(formatRoomName(r2));
					} else {
						// Compare the normal name of the facility
						return c1.getFacility().getName().compareTo(c2.getFacility().getName()) * sortDirection;
					}
				case 1: // Building name
					return c1.getFacility().getParentFacility().getName()
							.compareTo(c2.getFacility().getParentFacility().getName())
							* sortDirection;
				case 2: // Equipment (DataTables does not allow sorting, this
						// implementation would sort the number of Properties
					return (c1.getFacility().getProperties().size() - c2.getFacility().getProperties().size())
							* sortDirection;
				case 3: // start date
					return c1.getStart().compareTo(c2.getStart()) * sortDirection;
				}
				return 0;
			}
		});

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
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

		for (FreeFacilityResult c : searchResults) {

			Collection<String> facilityResult = new ArrayList<String>();
			if (c.getFacility() instanceof Room) {

				Room room = (Room) c.getFacility();

				// output for this row
				String roomName = "", buildingName = "", equipment = "", startTime = "", roomID = "";

				if (room != null) {
					roomName = formatRoomName(room);
					roomID = room.getId();
					if (room.getParentFacility() != null) {
						buildingName = room.getParentFacility().getName();
					}

					// TODO replace text with icons
					boolean equipmentListStart = true;
					for (Property p : room.getProperties()) {
						if (equipmentListStart) {
							equipmentListStart = false;
						} else {
							equipment += ", ";
						}
						equipment += p.getName();
					}
				}
				if (c.getStart() != null) {
					startTime = formatTime.format(c.getStart());
				}
				facilityResult.add(roomName);
				facilityResult.add(buildingName);
				facilityResult.add(equipment);
				facilityResult.add(startTime);
				facilityResult.add(roomID);

				results.add(facilityResult);
			}
		}
		return results;
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
	private static String formatRoomName(Room room) {
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
