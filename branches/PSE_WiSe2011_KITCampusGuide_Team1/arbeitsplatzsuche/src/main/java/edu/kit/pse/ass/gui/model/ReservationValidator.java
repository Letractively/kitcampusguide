package edu.kit.pse.ass.gui.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The class ReservationValidator validates ReservationsModels.
 * 
 * @author Jannis Koch
 * 
 */
public class ReservationValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ReservationModel.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		if (target instanceof ReservationModel) {
			ReservationModel resModel = (ReservationModel) target;

			if (!errors.hasFieldErrors("endTime") && resModel.getEndTime() == null) {
				errors.rejectValue("endTime", "endTime.null");
			}
			if (resModel.getWorkplaceCount() < 1) {
				errors.rejectValue("workplaceCount", "workplaceCount.invalid");
			}

		}
	}

	/**
	 * performs validations of a ReservationModel that represents an an update form and the respective ReservationModel
	 * of the original Reservation
	 * 
	 * @param updatedReservationModel
	 *            the ReservationModel representing the updated Reservation
	 * @param originalReservationModel
	 *            the ReservationModel representing the original Reservation
	 * @param errors
	 *            the errors object
	 */
	public void validate(ReservationModel updatedReservationModel, ReservationModel originalReservationModel,
			Errors errors) {
		validate(updatedReservationModel, errors);
		if (!errors.hasFieldErrors("endTime")
				&& !updatedReservationModel.getEndTime().after(originalReservationModel.getStartTime())) {
			errors.rejectValue("endTime", "endTime.beforeStart");
		}
		// check if form workplace count is not greater than reservation workplace count - adding workplaces is not
		// (yet) supported!
		if (updatedReservationModel.getWorkplaceCount() > originalReservationModel.getWorkplaceCount()) {
			errors.rejectValue("workplaceCount", "workplaceCount.invalid");
		}
	}
}
