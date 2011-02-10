package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * This class manages the language for a single user and therefore uses the {@link LanguageManager} and {@link Language} classes.
 * @author Fred
 */
@ManagedBean (name="translationModel")
@SessionScoped
public class TranslationModelImpl implements TranslationModel {
		
	/** Stores the current language.*/
	private Language currentLanguage;
	
	/** Stores the {@link LanguageManager} this instance uses.*/
	private LanguageManager manager;	
	
	/**
	 * Constructs a new {@link TranslationModel}.
	 */
	public TranslationModelImpl() {
		manager = LanguageManager.getInstance();
		currentLanguage = manager.getDefaultLanguage();
	}	
		
	/**
	 * Translates the {@link String} <code>key</code> in the current language.
	 * If there is no value for <code>key</code> in the current language the corresponding value from
	 * the default language will be used. If there is no translation defined in the default language either, <code>key</code> is returned.
	 * @param key The key identifying the text to be translated.
	 * @return The text corresponding to <code>key</code> in the current language or the default language or <code>key</code>.
	 */
	public String tr(String key) {
		String result = currentLanguage.tr(key);
		if (result == null) {
			result = manager.getDefaultLanguage().tr(key);
			if (result == null) {
				result = key;
			}
		}
		return result;
	}
		
	/**
	 * Sets the current language to the language identified by <code>language</code>.
	 * Best used with {@link getLanguages()}. Only changes the language if it is available on the system.
	 * @param language The <code>String</code> identifying the language. 
	 */
	public void setCurrentLanguage(String language) {
		if (manager.getLanguageByString(language) != null) {
			currentLanguage = manager.getLanguageByString(language);
		}
	}
	
	/**
	 * Returns the name of the current language.
	 * @return The name of the current language.
	 */
	public String getCurrentLanguage() {
		return currentLanguage.getName();
	}
	
	/**
	 * Returns a list of the names of every available language.
	 * @return A list of the names of every available language.
	 */
	public List<String> getLanguages() {
		return manager.getLanguagesAsString();
	}
	
}
