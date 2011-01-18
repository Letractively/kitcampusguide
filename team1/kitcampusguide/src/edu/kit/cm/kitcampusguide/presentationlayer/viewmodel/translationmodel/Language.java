package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Represents a language.
 * @author Fred
 */
public class Language {
	/** Stores the keys and values for the translation.*/ 
	private HashMap<String, String> translateMap;
	
	/** Stores the name of the language.*/
	private String name;
	
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
	Language(InputStream inputStream) {
		translateMap = new HashMap<String, String>();
		try {
			Document document = new SAXBuilder().build(inputStream);
			constructLanguage(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Constructs this language from the xml-document <code>document</code>.
	 * @param document The document the language is to be constructed from.
	 */
	private void constructLanguage(Document document) {
		name = document.getRootElement().getAttributeValue("name");
		List<Element> entries = document.getRootElement().getChildren("entry");
		for (Element entry : entries) {
			String key = entry.getAttributeValue("key");
			String value = entry.getValue();
			translateMap.put(key, value);
		}
	}
}
