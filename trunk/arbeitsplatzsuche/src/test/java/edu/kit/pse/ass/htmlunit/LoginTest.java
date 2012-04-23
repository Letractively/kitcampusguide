package edu.kit.pse.ass.htmlunit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * Test for the login.
 * 
 * @author Jannis Koch
 * 
 */
public class LoginTest {

	/** the constant base url */
	private static final String BASEURL = "http://localhost:8080/arbeitsplatzsuche/";

	/** the user */
	private static final String USERNAME = "ubbbb@student.kit.edu";

	/** the user's password */
	private static final String PASSWORD = "bbbbbbbb";

	/**
	 * Tests the login form.
	 * 
	 * Test submits wrong login data, followed by correct login data, and then logs out.
	 * 
	 * @throws IOException
	 *             if an IO error occurs
	 */
	@Test
	public void login() throws IOException {

		final WebClient webClient = new WebClient();

		HtmlPage loginPage = webClient.getPage(BASEURL);
		assertEquals("Login", loginPage.getTitleText());

		// Fill in wrong login data
		HtmlForm form = loginPage.getFormByName("f");
		HtmlSubmitInput button = form.getInputByName("submit");
		form.getInputByName("j_username").setValueAttribute("abc");
		form.getInputByName("j_password").setValueAttribute("def");

		// submit login form
		final HtmlPage loginRedirectPage = button.click();

		String redirectPageUrl = loginRedirectPage.getWebResponse().getRequestSettings().getUrl().toString();
		assertEquals(redirectPageUrl, BASEURL + "login.html?login_error=1");

		// Fill in correct login data
		form = loginRedirectPage.getFormByName("f");
		button = form.getInputByName("submit");
		form.getInputByName("j_username").setValueAttribute(USERNAME);
		form.getInputByName("j_password").setValueAttribute(PASSWORD);

		// submit login form
		final HtmlPage login2RedirectPage = button.click();
		assertEquals(login2RedirectPage.getTitleText(), "KIT Arbeitsplatz-Such-System");

		// Logout
		final HtmlPage logoutPage = webClient.getPage(BASEURL + "j_spring_security_logout");
		assertEquals("Login", logoutPage.getTitleText());

		webClient.closeAllWindows();
	}
}
