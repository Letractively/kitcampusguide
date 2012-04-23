package edu.kit.pse.ass.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.kit.pse.ass.gui.controller.LoginController;

/**
 * Test for the Login Controller
 * 
 * @author Jannis Koch
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@Transactional
public class LoginControllerTest {

	/** the login controller */
	@Autowired
	LoginController loginController;

	/**
	 * tests login()
	 */
	@Test
	public void testLogin() {

		Model model = new BindingAwareModelMap();

		String view = loginController.login(model);

		assertEquals("login", view);
	}
}
