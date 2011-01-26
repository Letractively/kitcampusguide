package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.util.List;

/**
 * This class manages the language and translation for a single user.
 * @author Fred
 */
public interface TranslationModel {
	/**
	 * Translates the {@link String} <code>key</code> in the current language.
	 * If there is no value for <code>key</code> in the current language the
	 * corresponding value from the default language will be used. If there is
	 * no translation defined in the default language either, <code>key</code>
	 * is returned.
	 * 
	 * @param key
	 *            The key identifying the text to be translated.
	 * @return The text corresponding to <code>key</code> in the current
	 *         language or the default language or <code>key</code>.
	 */
	public String tr(String key);

	/**
	 * Sets the current language to the language identified by
	 * <code>language</code>. Best used with
	 * {@link TranslationModel#getLanguages()}. Only changes the language if it
	 * is available on the system.
	 * 
	 * @param language
	 *            The <code>String</code> identifying the language.
	 */
	public void setCurrentLanguage(String language);

	/**
	 * Returns the name of the current language.
	 * 
	 * @return The name of the current language.
	 */
	public String getCurrentLanguage();

	/**
	 * Returns a list of the names of every available language.
	 * 
	 * @return A list of the names of every available language.
	 */
	public List<String> getLanguages();
}
