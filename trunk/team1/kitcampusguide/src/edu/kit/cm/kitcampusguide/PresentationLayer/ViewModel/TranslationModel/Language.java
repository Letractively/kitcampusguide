package edu.kit.cm.kitcampusguide.PresentationLayer.ViewModel.TranslationModel;

import java.util.Map;

class Language {
	private Map<String, String> translateMap;
	
	private String name;
	
	String tr(String key) {
		return translateMap.get(key);
	}
	
	String getName() {
		return name;
	}
	
	Language(String filename) {
		//TODO Implement extraction from file.
	}
}
