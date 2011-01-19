package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	 * Creates a new language manager with the languages defined by <code>allLanguages</code> and the default language defined by <code>defaultLanguage</code>.
	 * @param allLanguages
	 * @param defaultLanguage
	 */
	private LanguageManager(List<Language> allLanguages, String defaultLanguage) {
		this.allLanguages = allLanguages;
		this.defaultLanguage = getLanguageByString(defaultLanguage);
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
	public Language getLanguageByString(String identifier) {
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
	 * Initializes the only instance of <code>LanguageManager</code> from the collection of languages with the default language defined by defaultLanguage.
	 * If a instance of language manager already exists, no new is created.
	 * @param allLanguages All available languages.
	 * @param defaultLanguage The name of the default language.
	 */
	static void initializeLanguageManager(List<Language> allLanguages, String defaultLanguage) {
		if (instance == null) {
			instance = new LanguageManager(allLanguages, defaultLanguage);
		}
	}
	
	/**
	 * Returns the instance of <code>LanguageManager</code>.
	 * @return The instance of <code>LanguageManager</code>.
	 */
	public static LanguageManager getInstance() {
		return instance;
	}
}
