package edu.kit.cm.kitcampusguide.PresentationLayer.ViewModel.TranslationModel;

import java.util.List;

public class TranslationModel {
	private Language currentLanguage;
	private LanguageManager manager;
	
	public TranslationModel() {
		manager = LanguageManager.getInstance();
		currentLanguage = manager.getDefaultLanguage();
	}
	
	public String tr(String key) {
		return currentLanguage.tr(key);
	}
	
	public void setCurrentLanguage(String language) {
		if (manager.getLanguageByString(language) != null) {
			currentLanguage = manager.getLanguageByString(language);
		}
	}
	
	public String getCurrentLanguage() {
		return currentLanguage.getName();
	}
	
	public List<String> getLanguages() {
		return manager.getLanguagesAsString();
	}
}
