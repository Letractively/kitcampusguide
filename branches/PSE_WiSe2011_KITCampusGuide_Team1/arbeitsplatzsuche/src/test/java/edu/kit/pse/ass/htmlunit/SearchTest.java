package edu.kit.pse.ass.htmlunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * 
 * @author Jannis Koch
 * 
 */
public class SearchTest {

	/** the constant base url */
	private static final String BASEURL = "http://localhost:8080/arbeitsplatzsuche/";

	/** the user */
	private static final String USERNAME = "ubbbb@student.kit.edu";

	/** the user's password */
	private static final String PASSWORD = "bbbbbbbb";

	/**
	 * logs the user in
	 * 
	 * @param webClient
	 *            the web client to use
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void login(WebClient webClient) throws IOException {

		HtmlPage loginPage = webClient.getPage(BASEURL);
		assertEquals("Login", loginPage.getTitleText());

		// Fill in login data
		HtmlForm form = loginPage.getFormByName("f");
		HtmlSubmitInput button = form.getInputByName("submit");
		form.getInputByName("j_username").setValueAttribute(USERNAME);
		form.getInputByName("j_password").setValueAttribute(PASSWORD);

		// submit login form
		final HtmlPage loginRedirectPage = button.click();
		assertEquals(loginRedirectPage.getTitleText(), "KIT Arbeitsplatz-Such-System");
	}

	/**
	 * logs the user out
	 * 
	 * @param webClient
	 *            the web client to use
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void logout(WebClient webClient) throws IOException {
		// Logout
		final HtmlPage logoutPage = webClient.getPage(BASEURL + "j_spring_security_logout");
		assertEquals("Login", logoutPage.getTitleText());
	}

	/**
	 * tests the search
	 * 
	 * @throws IOException
	 *             if an IO error occurs
	 */
	@Test
	public void searchTest() throws IOException {

		final WebClient webClient = new WebClient();

		login(webClient);

		// Make ajax work
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		// test simple search
		HtmlPage advancedSearchPage = testSimpleSearch(webClient);

		// test result table in advanced search
		testAdvancedSearchResultTable(advancedSearchPage);

		// test filters table in advanced search
		testAdvancedSearchFilters(advancedSearchPage);

		// click on first entry of results
		HtmlPage roomDetailsPage = clickFirstEntry(advancedSearchPage);

		// book the first room
		HtmlPage bookingOverviewPage = bookRoom(roomDetailsPage);

		logout(webClient);
	}

	/**
	 * tests the sorting on the advanced search
	 * 
	 * @throws IOException
	 *             if an IO error occurs
	 */
	@Test
	public void testSorting() throws IOException {
		final WebClient webClient = new WebClient();

		login(webClient);

		// Make ajax work
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		// test simple search
		HtmlPage advancedSearchPage = testSimpleSearch(webClient);

		// test sorting
		testSorting(advancedSearchPage);

		logout(webClient);

	}

	/**
	 * tests the sorting on the given advanced search page
	 * 
	 * @param advancedSearchPage
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void testSorting(HtmlPage advancedSearchPage) throws IOException {

		// sort table (should really click on the sorting field in the table, but it does not work)
		advancedSearchPage.executeJavaScript("resultTable.fnSort([[0,'asc']]);");

		// check if table is sorted in first column
		checkIfTableSorted(advancedSearchPage.getElementById("resultTable"), 0);
	}

	/**
	 * checks if the given result table is sorted in the column with the given index
	 * 
	 * @param resultTable
	 *            the table to be checked
	 */
	private void checkIfTableSorted(HtmlElement resultTable, int columnIndex) {
		// Check if there are elements in result table
		DomNodeList<HtmlElement> resultTableRows = resultTable.getElementsByTagName("tr");

		// Check if data is correct in each row
		String lastRoomName = "";
		boolean firstRow = true;
		for (HtmlElement row : resultTableRows) {
			if (firstRow) {
				// ignore first row
				firstRow = false;
			} else {
				DomNodeList<HtmlElement> cells = row.getElementsByTagName("td");
				String roomName = cells.get(columnIndex).asText();
				assertTrue(roomName.compareTo(lastRoomName) >= 0);

				lastRoomName = roomName;
			}
		}

	}

	/**
	 * books the room of the given room details page
	 * 
	 * @param roomDetailsPage
	 *            the room details page
	 * @return the booking overview page
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private HtmlPage bookRoom(HtmlPage roomDetailsPage) throws IOException {

		HtmlForm bookingForm = (HtmlForm) roomDetailsPage.getElementById("searchForm");

		String formStart = bookingForm.getInputByName("start").getValueAttribute();
		String formDuration = bookingForm.getInputByName("duration").getValueAttribute();

		// submit booking form
		HtmlSubmitInput submitButton = bookingForm.getInputByValue("Reservieren");
		HtmlPage bookingOverviewPage = submitButton.click();

		assertEquals(bookingOverviewPage.getTitleText(), "Meine Reservierungen");

		// check if newly created reservation appears in booking overview
		boolean reservationFound = reservationExists(bookingOverviewPage, formDuration, formStart);
		assertTrue(reservationFound);

		return bookingOverviewPage;
	}

	/**
	 * checks if the reservation with the given properties exists on the given booking overview page
	 * 
	 * @param bookingOverviewPage
	 *            the booking overview page
	 * @param duration
	 *            the duration of the reservation
	 * @param startDate
	 *            the start date of the reservation
	 * @return true if the reservarion exists
	 */
	private boolean reservationExists(HtmlPage bookingOverviewPage, String duration, String startDateForm) {

		// date formats in booking form / booking overview
		SimpleDateFormat dfForm = new SimpleDateFormat("dd-M-yyyy HH:mm");
		SimpleDateFormat dfBookingOverview = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat dfBookingOverviewTime = new SimpleDateFormat("HH:mm");

		// parse the start date of the form to the format of the booking overview
		Date startDate = null;
		try {
			startDate = dfForm.parse(startDateForm);
		} catch (ElementNotFoundException e) {
			throw new IllegalArgumentException("start date has wrong format");
		} catch (ParseException e) {
			throw new IllegalArgumentException("start date has wrong format");
		}

		String dateBookingOverview = dfBookingOverview.format(startDate) + " um "
				+ dfBookingOverviewTime.format(startDate);

		// Check if there is a reservation on the page matching the properties
		boolean reservationFound = false;
		List<HtmlElement> divElements = bookingOverviewPage.getElementsByTagName("div");
		for (HtmlElement div : divElements) {
			if (div.getAttribute("class").equals("reservationDetails")) {
				String reservationDetails = div.asText();
				if (reservationDetails.indexOf(duration + " Stunden") == 0) {
					continue;
				}
				if (reservationDetails.indexOf(dateBookingOverview) == 0) {
					continue;
				}
				// All properties were found
				reservationFound = true;
				break;
			}
		}

		return reservationFound;
	}

	/**
	 * clicks on the first entry in the results table and returns the room details page
	 * 
	 * @param advancedSearchPage
	 *            the advanced search page
	 * @return the room details page
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private HtmlPage clickFirstEntry(HtmlPage advancedSearchPage) throws IOException {
		HtmlElement resultTable = advancedSearchPage.getElementById("resultTable");

		DomNodeList<HtmlElement> resultTableRows = resultTable.getElementsByTagName("tr");
		assertTrue(resultTableRows.size() > 0);

		HtmlElement firstRow = resultTableRows.get(1);

		DomNodeList<HtmlElement> cells = firstRow.getElementsByTagName("td");

		String roomName = cells.get(0).asText();
		String buildingName = cells.get(1).asText();
		assertTrue(!roomName.isEmpty());

		HtmlPage roomDetailsPage = cells.get(0).click();
		String roomDetailsTitle = roomDetailsPage.getTitleText();
		assertTrue(roomDetailsTitle.startsWith("Details für"));
		// check if title contains room and building name of the result table
		// assertTrue(roomDetailsTitle.indexOf(roomName) > 0);
		assertTrue(roomDetailsTitle.indexOf(buildingName) > 0);
		return roomDetailsPage;
	}

	/**
	 * tests the filters of the advanced search
	 * 
	 * @param advancedSearchPage
	 *            the advanced search page
	 */
	private void testAdvancedSearchFilters(HtmlPage advancedSearchPage) {
		// Get filters
		HtmlForm filterForm = (HtmlForm) advancedSearchPage.getElementById("filterForm");
		List<HtmlInput> filterCheckboxes = filterForm.getInputsByName("filters");

		// Check if there are filters
		assertTrue(filterCheckboxes.size() > 0);

		// Check if there are no duplicate filters / empty filter names
		List<String> duplicateCheckList = new ArrayList<String>();
		for (HtmlElement f : filterCheckboxes) {
			String filterName = f.getAttribute("value");
			assertTrue(!filterName.isEmpty());
			assertTrue(!duplicateCheckList.contains(filterName));
			duplicateCheckList.add(filterName);
		}

	}

	/**
	 * tests the result table of the advanced search
	 * 
	 * @param advancedSearchPage
	 *            the advanced search page
	 */
	private void testAdvancedSearchResultTable(HtmlPage advancedSearchPage) {
		HtmlElement resultTable = advancedSearchPage.getElementById("resultTable");

		// Check if there are elements in result table
		DomNodeList<HtmlElement> resultTableRows = resultTable.getElementsByTagName("tr");
		int resultSize = resultTableRows.size();
		assertTrue(resultSize > 0);

		// Check if data is correct in each row
		boolean firstRow = true;
		for (HtmlElement row : resultTableRows) {
			if (firstRow) {
				// ignore first row
				firstRow = false;
			} else {

				DomNodeList<HtmlElement> cells = row.getElementsByTagName("td");
				assertEquals(4, cells.size());

				String room = cells.get(0).asText();
				String building = cells.get(1).asText();
				String equipment = cells.get(2).asText();
				String freeFrom = cells.get(3).asText();

				assertTrue(!room.isEmpty());
				assertTrue(!building.isEmpty());
				assertTrue(freeFrom.matches("[0-9]*:?[0-9]*"));

			}
		}

	}

	/**
	 * tests the simple search
	 * 
	 * @param webClient
	 *            the web client
	 * @return the advanced search shown upon submitting the simple search
	 * @throws IOException
	 *             if IO error occurs
	 */
	private HtmlPage testSimpleSearch(WebClient webClient) throws IOException {
		HtmlPage simpleSearchPage = webClient.getPage(BASEURL + "search/simple.html");
		assertEquals(simpleSearchPage.getTitleText(), "KIT Arbeitsplatz-Such-System");

		HtmlForm form = (HtmlForm) simpleSearchPage.getElementById("searchForm");
		HtmlSubmitInput submitButton = form.getInputByValue("Suche");
		return submitButton.click();
	}
}
