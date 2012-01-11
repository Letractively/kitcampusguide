package edu.kit.pse.ass.gui.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFormModel.
 * 
 * @author Oliver Schneider
 */
public class BookingFormModel extends BookingInfoModel {

	private final ArrayList<String> workplaces;

	/**
	 * Instantiates a new search form model.
	 */
	public BookingFormModel() {
		super();
		workplaces = new ArrayList<String>();
	}

	public ArrayList<String> getWorkplaces() {
		return workplaces;
	}

}
