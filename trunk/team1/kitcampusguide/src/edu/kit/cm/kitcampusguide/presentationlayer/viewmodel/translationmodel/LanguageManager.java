package edu.kit.cm.kitcampusguide.PresentationLayer.ViewModel.TranslationModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Manages all of the languages for the KITCampusGuide and gives methods to get these.
 * @author Fred
 */
class LanguageManager {
	/** Stores the only instance of the language manager.*/
	private static LanguageManager instance;
	/** Stores all available languages.*/
	private Collection<Language> allLanguages = new ArrayList<Language>();
	/** Stores the default language for the KITCampusGuide.*/
	private Language defaultLanguage;
	
	/**
	 * Constructs a new LanguageManager from the xml-file specified by <code>filename</code>. 
	 * @param filename The exact location of the configuration file.
	 */
	private LanguageManager(String filename) {
		try {
			Document document = new SAXBuilder().build(filename);
			ConfigureManager(document);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Configures the LanguageManager from the xml-document specified by <code>document</code>.
	 * @param document The document specifying the ConfigurationManager.
	 */
	private void ConfigureManager(Document document) {
		String folder = document.getRootElement().getChild("LanguageManagerConfiguration").getAttribute("folder").getValue();
		File f = new File(folder);
		File[] fileArray = f.listFiles();
		System.out.println(f.getAbsolutePath());
		for (File file : fileArray) {
			Language tmp = new Language(file.getAbsolutePath());
			allLanguages.add(tmp);
		}
		defaultLanguage = getLanguageByString(document.getRootElement().getChild("LanguageManagerConfiguration").getAttribute("defaultLanguage").getValue());
	}

	/**
	 * Returns the languages of the KITCampusGuide.
	 * @return A list of the available languages.
	 */
	List<String> getLanguagesAsString() {
		List<String> result = new ArrayList<String>();
		for (Language l : allLanguages) {
			result.add(l.getName());
		}
		return result;
	}
	
	/**
	 * Returns the language identified by <code>identifier</code>. If there are two languages defined by <code>identifier</code>, the returned language is undefined.
	 * @param identifier The name of the language to be returned.
	 * @return The language defined by identifier or <code>null</code>.
	 */
	Language getLanguageByString(String identifier) {
		Language result = null;
		for (Language language : allLanguages) {
			if (language.getName().equals(identifier)) {
				result = language;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Returns the default language.
	 * @return The default language.
	 */
	Language getDefaultLanguage() {
		return defaultLanguage;
	}
	
	/**
	 * Initializes the only instance of <code>LanguageManager</code> from the file defined by <code>filename</code>.
	 * @param filename The configuration file for the <code>LanguageManager</code>.
	 * @return The only <code>LanguageManager</code>-instance.
	 */
	static LanguageManager initializeLanguageManager(String filename) {
		if (instance == null) {
			instance = new LanguageManager(filename);
		}
		return instance;
	}
	
	/**
	 * Returns the instance of <code>LanguageManager</code>.
	 * @return The instance of <code>LanguageManager</code>.
	 */
	static LanguageManager getInstance() {
		return instance;
	}
}
