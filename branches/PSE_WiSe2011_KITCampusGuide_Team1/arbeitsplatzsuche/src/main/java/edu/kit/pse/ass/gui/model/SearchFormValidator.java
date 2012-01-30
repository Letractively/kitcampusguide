package edu.kit.pse.ass.gui.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class SearchFormValidator.
 */
public class SearchFormValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return SearchFormModel.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		SearchFormModel sfm = (SearchFormModel) target;
		if (sfm.getStart() == null) {
			errors.reject("startDate.null");
			// set to standard value
			sfm.setStartToNow();
		}
		if (!sfm.isDurationValid() || sfm.getDuration().isEmpty()) {
			errors.reject("duration.invalid");
			// set to standard value
			sfm.setDuration("1:00");
		}

	}
}
