package edu.kit.pse.ass.gui.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SearchFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SearchFormModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SearchFormModel sfm = (SearchFormModel) target;
		if (sfm.getStart() == null) {
			errors.reject("start.null", "Keine gültige Suchzeit angegeben.");
		}
		if (!sfm.isDurationValid()) {
			errors.reject("duration.invalid", "Keine gültige Dauer angegeben.");
		}

	}
}
