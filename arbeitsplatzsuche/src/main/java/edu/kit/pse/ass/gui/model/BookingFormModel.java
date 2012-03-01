package edu.kit.pse.ass.gui.model;

import java.util.ArrayList;

/**
 * The BookingFormModel contains information of a booking request.
 * 
 * @author Oliver Schneider
 */
public class BookingFormModel extends BookingInfoModel {

	/**
	 * The ids of the workplaces that should be booked.
	 */
	private final ArrayList<String> workplaces;

	/**
	 * Instantiates a new search form model.
	 */
	public BookingFormModel() {
		super();
		workplaces = new ArrayList<String>();
	}

	/**
	 * Get the id of the workplaces that should be booked.
	 * 
	 * @return selected workplaces in booking form
	 */
	public ArrayList<String> getWorkplaces() {
		return workplaces;
	}

	/**
	 * adds the given workplace id to the workplaces list
	 * 
	 * @param workplaceID
	 *            the workplace id to add
	 */
	public void addWorkplace(String workplaceID) {
		workplaces.add(workplaceID);
	}

}
