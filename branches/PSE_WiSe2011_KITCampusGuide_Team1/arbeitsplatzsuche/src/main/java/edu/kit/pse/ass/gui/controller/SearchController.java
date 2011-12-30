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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.pse.ass.booking.management.FreeFacilityResult;
import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.gui.model.DataTableParamModel;
import edu.kit.pse.ass.gui.model.SearchFilterModel;
import edu.kit.pse.ass.gui.model.SearchFormModel;

@Controller
public class SearchController extends MainController {

	@RequestMapping(value = "search/simple.html")
	public String setUpSimpleSearch(Model model) {
		prefillSearchForm(new SearchFormModel(), model);
		return "search/simple";
	}

	@RequestMapping(value = "search/advanced.html")
	public String setUpAdvancedSearch(@ModelAttribute SearchFormModel sfm,
			Model model) {

		prefillSearchForm(sfm, model);

		model.addAttribute("searchFilterModel", new SearchFilterModel());
		model.addAttribute("filterList", getFilterList());

		return "search/advanced";
	}

	private void prefillSearchForm(SearchFormModel sfm, Model model) {
		model.addAttribute("searchFormModel", sfm);
	}

	@RequestMapping(value = "search/results")
	public void listSearchResults(@ModelAttribute SearchFormModel sfm,
			@ModelAttribute SearchFilterModel searchFilterModel,
			HttpServletRequest request, HttpServletResponse response) {

		// Test
		if (searchFilterModel != null && searchFilterModel.getFilters() != null) {
			for (String s : searchFilterModel.getFilters()) {
				System.out.println(s);
			}
			System.out.println("");
		}
		// Get parameters for DataTable
		DataTableParamModel parameters = DataTablesParamUtility
				.getParameters(request);

		String sEcho = parameters.sEcho;

		// total number of entries (unfiltered)
		int iTotalRecords;

		// numbers of entries displayed
		int iTotalDisplayRecords;

		// data in JSON format
		JSONArray data = new JSONArray();

		// create dummy search results (temp!)
		List<FreeFacilityResult> searchResults = tempSearchResults();

		iTotalRecords = searchResults.size();

		// Filter for room and building columns - delete?
		List<FreeFacilityResult> result = new LinkedList<FreeFacilityResult>();
		for (FreeFacilityResult c : searchResults) {
			if (c.getFacility().getName().toLowerCase()
					.contains(parameters.sSearch.toLowerCase())
					|| c.getFacility().getParentFacility().getName()
							.toLowerCase()
							.contains(parameters.sSearch.toLowerCase())) {
				result.add(c); // Add a company that matches search criterion
			}
		}

		iTotalDisplayRecords = result.size();

		// Sort list
		final int sortColumnIndex = parameters.iSortColumnIndex;
		final int sortDirection = parameters.sSortDirection.equals("asc") ? -1
				: 1;

		Collections.sort(result, new Comparator<FreeFacilityResult>() {
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
				case 2: // Equipment
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

		// show requested part of results
		if (result.size() < parameters.iDisplayStart
				+ parameters.iDisplayLength)
			result = result.subList(parameters.iDisplayStart, result.size());
		else
			result = result.subList(parameters.iDisplayStart,
					parameters.iDisplayStart + parameters.iDisplayLength);

		// create JSON response
		try {
			JSONObject jsonResponse = new JSONObject();

			jsonResponse.put("sEcho", sEcho);
			jsonResponse.put("iTotalRecords", iTotalRecords);
			jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

			SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

			for (FreeFacilityResult c : result) {
				JSONArray row = new JSONArray();
				String equipment = "";
				for (Property p : c.getFacility().getProperties()) {
					equipment += p.getName() + " ";
				}
				row.put(c.getFacility().getName() + " ")
						.put(c.getFacility().getParentFacility().getName())
						.put(equipment).put(formatTime.format(c.getStart()));
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

	private Collection<String> getFilterList() {

		Collection<Property> availableProperties;

		// TEMP
		// availableProperties =
		// facilityManagement.getAvailablePropertiesOf(Room);
		availableProperties = tempAvailableProperties();

		List<String> filterList = new ArrayList<String>();

		for (Property p : availableProperties) {
			filterList.add(p.getName());
		}

		return filterList;
	}

	private Collection<Property> tempAvailableProperties() {
		ArrayList<Property> list = new ArrayList<Property>();

		list.add(new Property("WLAN"));
		list.add(new Property("LAN"));
		list.add(new Property("Strom"));
		list.add(new Property("Licht"));

		return list;
	}

	private LinkedList<FreeFacilityResult> tempSearchResults() {

		LinkedList<FreeFacilityResult> results = new LinkedList<FreeFacilityResult>();

		Building b1 = new Building();
		b1.setName("Info Hauptgebäude");
		Building b2 = new Building();
		b2.setName("Rechenzentrum");
		Building b3 = new Building();
		b3.setName("KIT Bibliothek");

		Room r = new Room();

		b2.addContainedFacilitiy(r);
		r.addProperty(new Property("WLAN"));
		r.addProperty(new Property("Strom"));
		r.addProperty(new Property("Licht"));
		r.setName("Foyer");
		results.add(new FreeFacilityResult(r, new Date(111, 11, 28, 15, 20)));

		r = new Room();
		r.addProperty(new Property("WLAN"));
		r.addProperty(new Property("Strom"));
		b3.addContainedFacilitiy(r);
		r.setName("Saal");
		results.add(new FreeFacilityResult(r, new Date(111, 11, 6, 15, 30)));

		for (int i = 0; i < 24; i++) {
			r = new Room();
			b1.addContainedFacilitiy(r);
			r.setName("Seminarraum " + i);
			r.setLevel(1);
			r.addProperty(new Property("WLAN"));
			r.addProperty(new Property("Barrierefrei"));
			results.add(new FreeFacilityResult(r, new Date(111, 11, 27, i,
					(int) (Math.random() * 59))));
		}
		return results;
	}
}
