package edu.kit.cm.kitcampusguide.PresentationLayer.ViewModel.TranslationModel;
import java.io.IOException;
import java.util.HashMap;
import org.jdom.*;
import org.jdom.input.SAXBuilder;

class Language {
	/** Stores the keys and values for the translation.*/ 
	private HashMap<String, String> translateMap;
	
	/** Stores the name of the language.*/
	private String name;
	
	/**
	 * Translates the <code>key</code> in the language represented by this class.
	 * Returns <code>null</code> if there ist no translation for this key.
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
	 * @param filename The path to the file the language is defined in.
	 */
	Language(String filename) {
		translateMap = new HashMap<String, String>();
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(filename);
			Element root = doc.getRootElement();
			constructLanguage(root);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void constructLanguage(Element root) {
		Element child = (Element) root.getChildren().get(0);
		name = child.getValue();
		for (int i = 1; i < root.getChildren().size(); i++) {
			child = (Element) root.getChildren().get(i);
			translateMap.put(child.getName(), child.getValue());	
		}
	}
}
