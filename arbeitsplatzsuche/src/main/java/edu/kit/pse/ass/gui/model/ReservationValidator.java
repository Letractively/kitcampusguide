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
			if (resModel.getEndTime() == null) {
				errors.rejectValue("endTime", "errors.invalid",
						"Keine gültige Endzeit angegeben.");
			}
			if (resModel.getWorkplaceCount() < 1) {
				errors.rejectValue("workplaceCount", "errors.invalid",
						"Ungültige Anzahl.");
			}

		}
	}
}
