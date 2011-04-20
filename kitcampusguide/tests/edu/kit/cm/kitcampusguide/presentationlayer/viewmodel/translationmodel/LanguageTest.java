/**
 * 
 */
package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the language-class.
 * @author Frederik
 *
 */
public class LanguageTest {

	private static Language mainTestLanguage;
	private static String name = "TestSprache_ä\\u@€";
	private static int count = 20; 
	
	private static HashMap<String, String> testFullHashmap;
	
	/**
	 * Constructs the main language class for testing. Configures a logger.
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HashMap<String, String> translationMap = new HashMap<String, String>();
		for (int i = 0; i < count; i++) {
			String key = "testKey" + i;
			String value = "testValue" + i;
			translationMap.put(key, value);
		}
		translationMap.put("testKeyDouble", "testValue1");
		mainTestLanguage = new Language(name, translationMap);
		BasicConfigurator.configure();
		//these attributes are required for the tests for instantiation.
		testFullHashmap = new HashMap<String, String>();
		testFullHashmap.put("testKey1", "testValue1");
		testFullHashmap.put("testKey2", "testValue2");
		testFullHashmap.put("testKey3", "testValue3");
		testFullHashmap.put("testKey4", "testValue4");
		testFullHashmap.put("testKey5", "testValue5");
	}

	/**
	 * Tests the translaton method.
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.Language#tr(java.lang.String)}.
	 */
	@Test
	public void testTr() {
		//Tests all translations of the form "testValue" + i.
		for (int i = 0; i < count; i++) {
			String required = "testValue" + i;
			String trReturned = mainTestLanguage.tr("testKey" + i);
			assertEquals(required, trReturned);
		}
		//Tests the translation of testKeyDouble to testValue1, and thereby the possibility of using two
		//identical translations for different keys.
		assertEquals(mainTestLanguage.tr("testKeyDouble"), "testValue1");
		//Tests the translation method for its reaction to values of null.
		assertNull(mainTestLanguage.tr("notInsertedKey"));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.Language#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(name, mainTestLanguage.getName());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.Language#Language(java.lang.String, java.util.HashMap)}.
	 * Constructs several language-instances out of different combinations of hashMaps and names.
	 */
	@Test
	public void testLanguageConstructionSuccessful() {
		HashMap<String, String> testEmptyHashmap = new HashMap<String, String>();
		String nameEmpty = "";
		new Language(nameEmpty, testFullHashmap);
		new Language(name, testEmptyHashmap);
		new Language(name, testFullHashmap);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.Language#Language(java.lang.String, java.util.HashMap)}.
	 * Constructs several language-instances out of different combinations of hashMaps and names.
	 */
	@Test(expected=NullPointerException.class)
	public void testLanguageConstructionNullPointerNameNull() {
		String nameNull = null;
		new Language(nameNull, testFullHashmap);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.Language#Language(java.lang.String, java.util.HashMap)}.
	 * Constructs several language-instances out of different combinations of hashMaps and names.
	 */
	@Test(expected=NullPointerException.class)
	public void testLanguageConstructionNullPointerHashmapNull() {
		HashMap<String, String> testNullHashmap = null;
		new Language(name, testNullHashmap);

	}
}
