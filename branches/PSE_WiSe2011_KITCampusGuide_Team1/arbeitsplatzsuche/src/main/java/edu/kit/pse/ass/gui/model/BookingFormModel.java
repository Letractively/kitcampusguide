package edu.kit.pse.ass.gui.model;

import java.util.ArrayList;

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

	/**
	 * Get selected Workplaces
	 * 
	 * @return selected workplaces
	 */
	public ArrayList<String> getWorkplaces() {
		return workplaces;
	}

}
