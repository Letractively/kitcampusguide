package edu.kit.pse.ass.booking.management;

import java.util.Date;

import edu.kit.pse.ass.entity.Facility;

interface FreeFacilityQuery {

	/**
	 * Checks if is facility partially free, so that the query requirements can
	 * be fulfilled.
	 * 
	 * @param bookingManagement
	 *            the booking management
	 * @param facility
	 *            the facility
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return true, if is facility partially free
	 */
	public boolean isFacilityPartiallyFree(BookingManagement bookingManagement,
			Facility facility, Date startDate, Date endDate);

}
