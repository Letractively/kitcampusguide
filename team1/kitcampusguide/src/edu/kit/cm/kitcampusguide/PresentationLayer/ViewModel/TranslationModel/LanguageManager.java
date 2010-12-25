package edu.kit.cm.kitcampusguide.PresentationLayer.ViewModel.TranslationModel;

import java.util.Collections;
import java.util.List;

class LanguageManager {
	private static LanguageManager instance;
	private List<Language> allLanguages;
	private Language defaultLanguage;
	
	private LanguageManager() {
		//TODO Read and construct the language objects; Set the default language
	}
	
	List<String> getLanguagesAsString() {
		List<String> result = Collections.emptyList();
		for (Language l : allLanguages) {
			result.add(l.getName());
		}
		return result;
	}
	
	Language getLanguageByString(String identifier) {
		Language result = null;
		for (Language l : allLanguages) {
			if (l.getName().equals(identifier)) {
				result = l;
				break;
			}
		}
		return result;
	}
	
	Language getDefaultLanguage() {
		return defaultLanguage;
	}
	
	static LanguageManager getInstance() {
		if (instance == null) {
			instance = new LanguageManager();
		}
		return instance;
	}
}
