package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;

/**
 * Initializes the translation model.
 * More specifically, all languages are constructed and the languageManager initialized.
 * @author Fred
 *
 */
public class TranslationInitializer {
	private Logger logger = Logger.getLogger(getClass());
	private static TranslationInitializer instance;
	
	/**
	 * Initializes the translation. 
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occured during initialization.
	 */
	private TranslationInitializer(InputStream inputStream) throws InitializationException {
			try {
				logger.info("Initializing languages from" + inputStream);
				Document document;
				document = new SAXBuilder().build(inputStream);
				initializeLanguages(document);
				logger.info("Languages initialized");
			} catch (JDOMException e) {
				throw new InitializationException("Translation initialization failed.", e);
			} catch (IOException e) {
				throw new InitializationException("Translation initialization failed.", e);
			}
	}
	
	/**
	 * Initializes the languages.
	 * @param document The document defining the languages.
	 */
	private void initializeLanguages(Document document) {
		List<Language> allLanguages = new ArrayList<Language>();
		String defaultLanguage;
		FacesContext context = FacesContext.getCurrentInstance();
		List<Element> listLanguages = document.getRootElement().getChild("LanguageManagerConfiguration").getChildren("Language");
		for (Element e : listLanguages) {
			Language tmp = constructLanguage(context.getExternalContext().getResourceAsStream(e.getAttributeValue("filepath")));
			allLanguages.add(tmp);
		}
		defaultLanguage = document.getRootElement().getChild("LanguageManagerConfiguration").getAttributeValue("defaultLanguage");
		LanguageManager.initializeLanguageManager(allLanguages, defaultLanguage);
	}

	private Language constructLanguage(InputStream resourceAsStream) {
		Language result = null;
		try {
			HashMap<String, String> translateMap = new HashMap<String, String>();
			Document document = new SAXBuilder().build(resourceAsStream);
			String name = document.getRootElement().getAttributeValue("name");
			List<Element> entries = document.getRootElement().getChildren("entry");
			for (Element entry : entries) {
				String key = entry.getAttributeValue("key");
				String value = entry.getValue();
				translateMap.put(key, value);
			}
			result = new Language(name, translateMap);
		} catch (IOException e) {
			logger.error("Language construction of failed.", e);
		} catch (JDOMException e) {
			logger.error("Language construction of failed.", e);
		}
		return result;
	}

	/**
	 * Initializes the translation. If such an initialization has already been done or no initialization of the file paths has been attempted before,
	 * the maps will not be initialized.
	 * No initialization is required before this one.
	 * @param inputStream The input stream to the configuration file.
	 * 
	 * @throws InitializationException If an error occured during initialization.
	 */
	public static void initialize(InputStream inputStream) throws InitializationException {
		if (instance == null) {
			instance = new TranslationInitializer(inputStream);
		}
	}
	
	
}
