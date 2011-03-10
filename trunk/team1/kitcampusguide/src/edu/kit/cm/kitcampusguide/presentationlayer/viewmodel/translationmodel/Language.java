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
	 * Constructs a new language object from the xml-file specified by filename.
	 * Needs to have a special format which will be specified later.
	 * @param inputStream The path to the file the language is defined in.
	 */
	Language(String name, HashMap<String, String> translateMap) {
		this.name = name;
		this.translateMap = (HashMap<String, String>) translateMap.clone();
	}

	/**
	 * Constructs this language from the xml-document <code>document</code>.
	 * @param document The document the language is to be constructed from.
	 */
	private void constructLanguage(Document document) {
		
	}
}
