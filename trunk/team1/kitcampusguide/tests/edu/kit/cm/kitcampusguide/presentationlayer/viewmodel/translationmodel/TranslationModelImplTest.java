/**
 * 
 */
package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Frederik
 *
 */
public class TranslationModelImplTest {

	static TranslationModel model;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<Language> allLanguages = new ArrayList<Language>();
		allLanguages.add(new Language("testLanguage0", new HashMap<String, String>()));
		allLanguages.add(new Language("testLanguage1", new HashMap<String, String>()));
		allLanguages.add(new Language("testLanguage2", new HashMap<String, String>()));
		HashMap<String, String> defaultTranslation = new HashMap<String, String>();
		defaultTranslation.put("testKey0", "testValue0");
		defaultTranslation.put("testKey1", "testValue1");
		defaultTranslation.put("testKey2", "testValue2");
		allLanguages.add(new Language("defaultLanguage", defaultTranslation));
		LanguageManager.initializeLanguageManager(allLanguages, "defaultLanguage");
	}
	
	@Before
	public void setUpBeforeTest() throws Exception {
		model = new TranslationModelImpl();
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl#TranslationModelImpl()}.
	 */
	@Test
	public void testTranslationModelImpl() {
		new TranslationModelImpl();
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl#tr(java.lang.String)}.
	 */
	@Test
	public void testTr() {
		assertEquals("testValue0", model.tr("testKey0"));
		assertEquals("testValue1", model.tr("testKey1"));
		assertEquals("testValue2", model.tr("testKey2"));
		model.setCurrentLanguage("testLanguage0");
		assertEquals("testValue0", model.tr("testKey0"));
		assertEquals("noSuchKey", model.tr("noSuchKey"));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl#setCurrentLanguage(java.lang.String)}.
	 */
	@Test
	public void testSetCurrentLanguage() {
		model.setCurrentLanguage("testLanguage");
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl#setCurrentLanguage(java.lang.String)}.
	 */
	@Test
	public void testSetCurrentLanguageFail() {
		model.setCurrentLanguage("noSuchLanguage");
		assertEquals("defaultLanguage", model.getCurrentLanguage());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl#getCurrentLanguage()}.
	 */
	@Test
	public void testGetCurrentLanguage() {
		assertEquals("defaultLanguage", model.getCurrentLanguage());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModelImpl#getLanguages()}.
	 */
	@Test
	public void testGetLanguages() {
		List<String> languages = model.getLanguages();
		assertEquals(languages.size(), 4);
		assertTrue(languages.contains("testLanguage0"));
		assertTrue(languages.contains("testLanguage1"));
		assertTrue(languages.contains("testLanguage2"));
		assertTrue(languages.contains("defaultLanguage"));
	}

}
