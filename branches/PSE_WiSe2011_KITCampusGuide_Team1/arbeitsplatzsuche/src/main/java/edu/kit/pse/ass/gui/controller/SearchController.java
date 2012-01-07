package edu.kit.pse.ass.gui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.booking.management.BookingManagement;
import edu.kit.pse.ass.booking.management.FreeFacilityResult;
import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityManagement;
import edu.kit.pse.ass.gui.model.DataTableParamModel;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;
import edu.kit.pse.ass.gui.model.SearchFormValidator;

// TODO: Auto-generated Javadoc
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
	 * Sets the up simple search.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "search/simple.html")
	public String setUpSimpleSearch(Model model) {
		prefillSearchForm(new SearchFormModel(), model);
		return "search/simple";
	}

	/**
	 * Sets the up advanced search.
	 * 
	 * @param sfm
	 *            the sfm
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "search/advanced.html")
	public String setUpAdvancedSearch(@ModelAttribute SearchFormModel sfm,
			Model model) {

		prefillSearchForm(sfm, model);

		model.addAttribute("searchFilterModel", new SearchFilterModel());
		model.addAttribute("filterList", tempAvailableProperties());

		return "search/advanced";
	}

	/**
	 * Prefill search form.
	 * 
	 * @param sfm
	 *            the sfm
	 * @param model
	 *            the model
	 */
	private void prefillSearchForm(SearchFormModel sfm, Model model) {
		model.addAttribute("searchFormModel", sfm);
	}

	/**
	 * List search results.
	 * 
	 * @param sfm
	 *            the sfm
	 * @param sfmResult
	 *            the sfm result
	 * @param searchFilterModel
	 *            the search filter model
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "search/results")
	public void listSearchResults(@ModelAttribute SearchFormModel sfm,
			BindingResult sfmResult,
			@ModelAttribute SearchFilterModel searchFilterModel,
			HttpServletRequest request, HttpServletResponse response) {

		SearchFormValidator sfmValidator = new SearchFormValidator();
		sfmValidator.validate(sfm, sfmResult);

		// DataTable Parameters
		DataTableParamModel parameters;
		// total number of entries (unfiltered)
		int iTotalRecords;
		// numbers of entries displayed
		int iTotalDisplayRecords;
		// the search results
		List<FreeFacilityResult> searchResults;
		// output data in JSON format
		JSONArray data = new JSONArray();

		// Get parameters for DataTable
		parameters = DataTablesParamUtility.getParameters(request);

		// find free rooms
		/*
		 * RoomQuery roomQuery = new RoomQuery(searchFilterModel.getFilters(),
		 * sfm.getSearchText(), sfm.getWorkplaceCount());
		 * Collection<FreeFacilityResult> searchResultsCollection =
		 * bookingManagement .findFreeFacilites(roomQuery, sfm.getStart(),
		 * sfm.getEnd(), sfm.isWholeRoom());
		 * 
		 * if (searchResultsCollection instanceof List) { searchResults = (List)
		 * searchResultsCollection; } else { searchResults = new
		 * ArrayList(searchResultsCollection); }
		 */

		// create dummy search results (temp!)
		searchResults = tempSearchResults();

		iTotalRecords = searchResults.size();
		iTotalDisplayRecords = iTotalRecords;

		// Sort results
		final int sortColumnIndex = parameters.iSortColumnIndex;
		final int sortDirection = parameters.sSortDirection.equals("asc") ? 1
				: -1;
		sortResults(searchResults, sortColumnIndex, sortDirection);

		// show requested part of results
		if (searchResults.size() < parameters.iDisplayStart
				+ parameters.iDisplayLength)
			searchResults = searchResults.subList(parameters.iDisplayStart,
					searchResults.size());
		else
			searchResults = searchResults.subList(parameters.iDisplayStart,
					parameters.iDisplayStart + parameters.iDisplayLength);

		// create JSON response
		try {
			JSONObject jsonResponse = new JSONObject();

			jsonResponse.put("sEcho", parameters.sEcho);
			jsonResponse.put("iTotalRecords", iTotalRecords);
			jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

			// add errors if any
			if (sfmResult.hasErrors()) {
				JSONArray asErrors = new JSONArray();
				for (ObjectError error : sfmResult.getAllErrors()) {
					asErrors.put(error.getDefaultMessage());
				}
				jsonResponse.put("asErrors", asErrors);
			}

			SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

			for (FreeFacilityResult c : searchResults) {
				JSONArray row = new JSONArray();
				String equipment = "";
				for (Property p : c.getFacility().getProperties()) {
					equipment += p.getName() + " ";
				}
				row.put(c.getFacility().getName() + " ")
						.put(c.getFacility().getParentFacility().getName())
						.put(equipment).put(formatTime.format(c.getStart()))
						.put(c.getFacility().getId());
				data.put(row);
			}
			jsonResponse.put("aaData", data);

			response.setContentType("application/json");
			response.getWriter().print(jsonResponse.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setContentType("text/html");
			// response.getWriter().print(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * sorts the specified search results according to the given column index
	 * and sortDirection.
	 * 
	 * @param results
	 *            the results to sort
	 * @param sortColumnIndex
	 *            the index of the column to sort
	 * @param sortDirection
	 *            the sort direction, must be 1 or -1
	 */
	private void sortResults(List<FreeFacilityResult> results,
			final int sortColumnIndex, final int sortDirection) {
		Collections.sort(results, new Comparator<FreeFacilityResult>() {
			@Override
			public int compare(FreeFacilityResult c1, FreeFacilityResult c2) {
				switch (sortColumnIndex) {
				case 0: // Room name
					return c1.getFacility().getName()
							.compareTo(c2.getFacility().getName())
							* sortDirection;
				case 1: // Building name
					return c1
							.getFacility()
							.getParentFacility()
							.getName()
							.compareTo(
									c2.getFacility().getParentFacility()
											.getName())
							* sortDirection;
				case 2: // Equipment (DataTables does not allow sorting, this
						// implementation would sort the number of Properties
					return (c1.getFacility().getProperties().size() - c2
							.getFacility().getProperties().size())
							* sortDirection;
				case 3: // start date
					return c1.getStart().compareTo(c2.getStart())
							* sortDirection;
				}
				return 0;
			}
		});

	}

	/*
	 * dummy!
	 */
	/**
	 * Temp available properties.
	 * 
	 * @return the collection
	 */
	private Collection<Property> tempAvailableProperties() {
		ArrayList<Property> list = new ArrayList<Property>();

		list.add(new Property("WLAN"));
		list.add(new Property("LAN"));
		list.add(new Property("Strom"));
		list.add(new Property("Licht"));

		return list;
	}

	/*
	 * dummy!
	 */
	/**
	 * Temp search results.
	 * 
	 * @return the linked list
	 */
	private LinkedList<FreeFacilityResult> tempSearchResults() {

		LinkedList<FreeFacilityResult> results = new LinkedList<FreeFacilityResult>();

		Building b1 = new Building();
		b1.setName("Info Hauptgebäude");
		Building b2 = new Building();
		b2.setName("Rechenzentrum");
		Building b3 = new Building();
		b3.setName("KIT Bibliothek");

		Room r = new Room();

		b2.addContainedFacility(r);
		r.addProperty(new Property("WLAN"));
		r.addProperty(new Property("Strom"));
		r.addProperty(new Property("Licht"));
		r.setName("Foyer");
		results.add(new FreeFacilityResult(r, new Date(111, 11, 27, 15, 20)));

		r = new Room();
		r.addProperty(new Property("WLAN"));
		r.addProperty(new Property("Strom"));
		b3.addContainedFacility(r);
		r.setName("Saal");
		results.add(new FreeFacilityResult(r, new Date(111, 11, 27, 15, 30)));

		for (int i = 0; i < 100; i++) {
			r = new Room();
			b1.addContainedFacility(r);
			r.setName("Seminarraum " + i);
			r.setLevel(1);
			r.addProperty(new Property("WLAN"));
			r.addProperty(new Property("Barrierefrei"));
			results.add(new FreeFacilityResult(r, new Date(111, 11, 27,
					(i * 23 / 99), (int) (Math.random() * 59))));
		}
		return results;
	}
}
