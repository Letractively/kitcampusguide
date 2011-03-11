package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.jdom.Document;

/**
 * Represents a language.
 * @author Fred
 */
public class Language {
	/** Stores the keys and values for the translation.*/ 
	private HashMap<String, String> translateMap;
	
	/** Stores the name of the language.*/
	private String name;
	
	/**The logger for this class*/
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Translates the <code>key</code> in the language represented by this class.
	 * Returns <code>null</code> if there is no translation for this key.
	 * @param key The key to be translated.
	 * @return The translation or <code>null</code>.
	 */
	String tr(String key) {
		return translateMap.get(key);
	}
	
	/**
	 * Returns the name of the language.
	 * @return The name of the language.
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Constructs a language with the specified name and the specified translations.
	 * @param name The name of the language. Required to be unique.
	 * @param translateMap The Hashmap defining the translation. Key is the key value which will
	 * be translated by the associated value-string.
	 */
	Language(String name, HashMap<String, String> translateMap) {
		if (name == null) {
			throw new NullPointerException("Language constructed with name null");
		}
		this.name = name;
		this.translateMap = (HashMap<String, String>) translateMap.clone();
		logger.debug("Created language " + name);
	}
}
