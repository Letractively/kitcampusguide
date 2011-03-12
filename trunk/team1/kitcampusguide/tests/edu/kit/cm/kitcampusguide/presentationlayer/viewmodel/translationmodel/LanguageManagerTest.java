/**
 * 
 */
package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Frederik
 *
 */
public class LanguageManagerTest {
	
	static List<Language> allLanguages;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		allLanguages= new ArrayList<Language>();
		HashMap<String, String> emptyHashmap = new HashMap<String, String>();
		allLanguages.add(new Language("testLanguage0", emptyHashmap));
		allLanguages.add(new Language("testLanguage1", emptyHashmap));
		allLanguages.add(new Language("testLanguage2", emptyHashmap));
		allLanguages.add(new Language("testLanguage3", emptyHashmap));
		allLanguages.add(new Language("testLanguage4", emptyHashmap));
		allLanguages.add(new Language("defaultLanguage", emptyHashmap));
		LanguageManager.initializeLanguageManager(allLanguages, "defaultLanguage");
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager#getLanguagesAsString()}.
	 */
	@Test
	public void testGetLanguagesAsString() {
		List<String> languageNames = LanguageManager.getInstance().getLanguagesAsString();
		assertEquals(6, languageNames.size());
		assertTrue(languageNames.contains("testLanguage0"));
		assertTrue(languageNames.contains("testLanguage1"));
		assertTrue(languageNames.contains("testLanguage2"));
		assertTrue(languageNames.contains("testLanguage3"));
		assertTrue(languageNames.contains("testLanguage4"));
		assertTrue(languageNames.contains("defaultLanguage"));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager#getLanguageByString(java.lang.String)}.
	 */
	@Test
	public void testGetLanguageByString() {
		LanguageManager manager = LanguageManager.getInstance();
		assertEquals(allLanguages.get(0), manager.getLanguageByString("testLanguage0"));
		assertEquals(allLanguages.get(1), manager.getLanguageByString("testLanguage1"));
		assertEquals(allLanguages.get(2), manager.getLanguageByString("testLanguage2"));
		assertEquals(allLanguages.get(3), manager.getLanguageByString("testLanguage3"));
		assertEquals(allLanguages.get(4), manager.getLanguageByString("testLanguage4"));
		assertEquals(allLanguages.get(5), manager.getLanguageByString("defaultLanguage"));
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager#getLanguageByString(java.lang.String)}.
	 */
	@Test
	public void testGetLanguageByStringFail() {
		LanguageManager manager = LanguageManager.getInstance();
		assertNull(manager.getLanguageByString("noSuchLanguage"));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager#getDefaultLanguage()}.
	 */
	@Test
	public void testGetDefaultLanguage() {
		assertEquals("defaultLanguage", LanguageManager.getInstance().getDefaultLanguage().getName());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.LanguageManager#initializeLanguageManager(java.util.List, java.lang.String)}.
	 */
	@Test
	public void testInitializeLanguageManager() {
		LanguageManager.initializeLanguageManager(new ArrayList<Language>(), "noSuchLanguage");
		assertEquals("defaultLanguage", LanguageManager.getInstance().getDefaultLanguage().getName());
	}
}
