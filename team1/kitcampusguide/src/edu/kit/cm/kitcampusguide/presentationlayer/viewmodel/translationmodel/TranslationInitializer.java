package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.InitializationException;

public class TranslationInitializer {
	private Logger logger = Logger.getLogger(getClass());
	private static TranslationInitializer instance;
	
	private TranslationInitializer(String filename) throws InitializationException {
			try {
				logger.info("Initializing languages from" + filename);
				Document document;
				document = new SAXBuilder().build(filename);
				initializeLanguages(document);
				logger.info("Languages initialized");
			} catch (JDOMException e) {
				throw new InitializationException("Translation initialization failed. " + e.getMessage());
			} catch (IOException e) {
				throw new InitializationException("Translation initialization failed. " + e.getMessage());
			}
	}
	
	private void initializeLanguages(Document document) {
		List<Language> allLanguages = new ArrayList<Language>();
		String defaultLanguage;
		String folder = document.getRootElement().getChild("LanguageManagerConfiguration").getAttribute("folder").getValue();
		File f = new File(folder);
		File[] fileArray = f.listFiles();
		for (File file : fileArray) {
			Language tmp = new Language(file.getAbsolutePath());
			allLanguages.add(tmp);
		}
		defaultLanguage = document.getRootElement().getChild("LanguageManagerConfiguration").getAttribute("defaultLanguage").getValue();
		LanguageManager.initializeLanguageManager(allLanguages, defaultLanguage);
	}

	public static void initialize(String filename) throws InitializationException {
		if (instance == null) {
			instance = new TranslationInitializer(filename);
		}
	}
}
