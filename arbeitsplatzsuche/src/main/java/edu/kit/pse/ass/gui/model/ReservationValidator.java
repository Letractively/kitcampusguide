package edu.kit.pse.ass.gui.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReservationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ReservationModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if (target instanceof ReservationModel) {
			ReservationModel resModel = (ReservationModel) target;

			if (!errors.hasFieldErrors("endTime")
					&& resModel.getEndTime() == null) {
				errors.rejectValue("endTime", "endTime.null");
			}
			if (resModel.getWorkplaceCount() < 1) {
				errors.rejectValue("workplaceCount", "workplaceCount.invalid");
			}

		}
	}
}
