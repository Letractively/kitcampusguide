package edu.kit.pse.ass.gui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FreeFacilityResult;
import edu.kit.pse.ass.booking.management.FreeRoomQuery;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.gui.model.DataTableParamModel;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;
import edu.kit.pse.ass.gui.model.SearchFormValidator;

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
	 * @return the view path
	 */
	@RequestMapping(value = "search/advanced.html")
	public String setUpAdvancedSearch(Model model, @ModelAttribute SearchFormModel searchFormModel) {

		prefillSearchForm(model, searchFormModel);

		model.addAttribute("searchFilterModel", new SearchFilterModel());
		model.addAttribute("filterList", facilityManagement.getAvailablePropertiesOf(Room.class));

		// Additional CSS / JS files
		String[] cssFiles = { "/css/advancedSearch.css", "/libs/datatables/css/datatable.css",
				"/libs/datatables/css/datatable_jui.css", "/libs/datatables/themes/base/jquery-ui.css" };
		String[] jsFiles = { "/libs/datatables/jquery.dataTables.min.js", "/scripts/advancedSearch.js" };
		model.addAttribute("cssFiles", cssFiles);
		model.addAttribute("jsFiles", jsFiles);

		// TODO workplace properties

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
	 * @param response
	 *            the spring HttpServletResponse
	 * @param searchFormModel
	 *            the SearchFormModel filled at the AdvacedSearchPage
	 * @param sfmResult
	 *            the spring BindingResult for error handling
	 * @param searchFilterModel
	 *            the SearchFilterModel filled at the AdvancedSearchPage
	 */
	@RequestMapping(value = "search/results")
	@Transactional
	public void listSearchResults(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute SearchFormModel searchFormModel, BindingResult sfmResult,
			@ModelAttribute SearchFilterModel searchFilterModel) {

		// Validate form
		SearchFormValidator sfmValidator = new SearchFormValidator();
		sfmValidator.validate(searchFormModel, sfmResult);

		// Get DataTable Parameters
		DataTableParamModel parameters = new DataTableParamModel(request);

		// Find free rooms
		if (searchFilterModel.getFilters() == null) {
			searchFilterModel.setFilters(new ArrayList<Property>());
		}

		FreeRoomQuery roomQuery = new FreeRoomQuery(searchFilterModel.getFilters(),
				searchFormModel.getSearchText(), searchFormModel.getWorkplaceCount());
		Collection<FreeFacilityResult> searchResultsCollection = bookingManagement.findFreeFacilites(roomQuery,
				searchFormModel.getStart(), searchFormModel.getEnd(), searchFormModel.isWholeRoom());

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

		// create JSON result List
		JSONArray resultListData = createJSONResultList(searchResults);

		// Create JSON response object and send response
		JSONObject jsonResponse = new JSONObject();
		try {
			// request sequence number sent by DataTable
			jsonResponse.put("sEcho", parameters.getsEcho());

			// total records and displayed records are the same (filtering is disabled)
			jsonResponse.put("iTotalRecords", searchResults.size());
			jsonResponse.put("iTotalDisplayRecords", searchResults.size());

			jsonResponse.put("aaData", resultListData);

			// add errors if any
			if (sfmResult.hasErrors()) {
				JSONArray asErrors = new JSONArray();
				for (ObjectError error : sfmResult.getAllErrors()) {
					asErrors.put(error.getDefaultMessage());
				}
				jsonResponse.put("asErrors", asErrors);
			}

			// send response
			response.setContentType("application/json");
			response.getWriter().print(jsonResponse.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					return c1.getFacility().getName().compareTo(c2.getFacility().getName()) * sortDirection;
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
	 * creates a JSONArray containing the search results for DataTables
	 * 
	 * @param searchResults
	 *            the search results for the output
	 * @return JSONArray containing the search results for DataTables
	 */
	private JSONArray createJSONResultList(List<FreeFacilityResult> searchResults) {

		JSONArray resultListData = new JSONArray();
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

		for (FreeFacilityResult c : searchResults) {

			if (c.getFacility() instanceof Room) {

				Room room = (Room) c.getFacility();

				// output for this row
				String roomName = "", buildingName = "", equipment = "", startTime = "", roomID = "";

				if (room != null) {

					if (room.getName().isEmpty()) {
						roomName = "Raum";
					} else {
						roomName = room.getName();
					}
					if (!room.getNumber().isEmpty()) {
						roomName += " " + room.getNumber();
					}
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

				JSONArray row = new JSONArray();
				row.put(roomName);
				row.put(buildingName);
				row.put(equipment);
				row.put(startTime);
				row.put(roomID);

				resultListData.put(row);
			}
		}
		return resultListData;
	}
}
