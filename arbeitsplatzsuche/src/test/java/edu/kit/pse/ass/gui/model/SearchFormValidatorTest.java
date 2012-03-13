package edu.kit.pse.ass.gui.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.validation.BindException;

/**
 * The Class SearchFormValidatorTest.
 */
public class SearchFormValidatorTest {

	/**
	 * Test validate.
	 */
	@Test
	public void testValidate() {
		SearchFormModel model = new SearchFormModel();
		BindException errors = new BindException(model, "path");

		SearchFormValidator validator = new SearchFormValidator();

		model.setStartToNow();
		model.setDuration("2:00");
		model.setWorkplaceCount(2);

		validator.validate(model, errors);

		assertFalse(errors.hasErrors());
	}

	/**
	 * Test validate error start null.
	 */
	@Test
	public void testValidateErrorStartNull() {
		SearchFormModel model = new SearchFormModel();
		BindException errors = new BindException(model, "path");

		SearchFormValidator validator = new SearchFormValidator();

		model.setStart(null);
		model.setDuration("2:00");
		model.setWorkplaceCount(2);

		validator.validate(model, errors);

		assertTrue(errors.hasErrors());
	}

	/**
	 * Test validate error start too early.
	 */
	@Test
	public void testValidateErrorStartTooEarly() {
		SearchFormModel model = new SearchFormModel();
		BindException errors = new BindException(model, "path");

		SearchFormValidator validator = new SearchFormValidator();

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -10);
		model.setStart(now.getTime());
		model.setDuration("2:00");
		model.setWorkplaceCount(2);

		validator.validate(model, errors);

		assertTrue(errors.hasErrors());
	}

	/**
	 * Test validate error workplaces too small.
	 */
	@Test
	public void testValidateErrorWorkplacesTooSmall() {
		SearchFormModel model = new SearchFormModel();
		BindException errors = new BindException(model, "path");

		SearchFormValidator validator = new SearchFormValidator();

		model.setStartToNow();
		model.setDuration("2:00");
		model.setWorkplaceCount(0);

		validator.validate(model, errors);

		assertTrue(errors.hasErrors());
	}

	/**
	 * Test validate error invalid duration.
	 */
	@Test
	public void testValidateErrorInvalidDuration() {
		SearchFormModel model = new SearchFormModel();
		BindException errors = new BindException(model, "path");

		SearchFormValidator validator = new SearchFormValidator();

		model.setStartToNow();
		model.setDuration("ed");
		model.setWorkplaceCount(1);

		validator.validate(model, errors);

		assertTrue(errors.hasErrors());
	}

}
