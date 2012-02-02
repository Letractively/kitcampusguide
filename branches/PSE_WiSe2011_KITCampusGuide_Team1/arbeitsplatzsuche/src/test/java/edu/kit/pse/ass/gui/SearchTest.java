package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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

		HtmlPage simpleSearchPage = webClient.getPage(BASEURL + "search/simple.html");
		assertEquals(simpleSearchPage.getTitleText(), "KIT Arbeitsplatz-Such-System");

		HtmlForm form = (HtmlForm) simpleSearchPage.getElementById("searchForm");
		HtmlSubmitInput submitButton = form.getInputByValue("Suche");
		HtmlPage advancedSearchPage = submitButton.click();

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

		logout(webClient);
	}
}
