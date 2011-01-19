package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
			Language tmp = new Language(context.getExternalContext().getResourceAsStream(e.getAttributeValue("filepath")));
			allLanguages.add(tmp);
		}
		defaultLanguage = document.getRootElement().getChild("LanguageManagerConfiguration").getAttributeValue("defaultLanguage");
		LanguageManager.initializeLanguageManager(allLanguages, defaultLanguage);
	}

	/**
	 * Initializes the translation. 
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
