package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel;

import java.util.List;

import javax.faces.bean.ManagedBean;


public class TestTranslationModel implements TranslationModel {

	@Override
	public String tr(String key) {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public void setCurrentLanguage(String language) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCurrentLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

}
