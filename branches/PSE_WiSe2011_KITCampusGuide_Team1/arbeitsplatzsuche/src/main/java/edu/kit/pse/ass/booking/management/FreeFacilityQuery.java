package edu.kit.pse.ass.booking.management;

import java.util.Date;

import edu.kit.pse.ass.entity.Facility;

/**
 * The interface FreeFacilityQuery
 */
interface FreeFacilityQuery {

	/**
	 * Checks if the facility is partially free, so that the query requirements can be fulfilled.
	 * 
	 * @param bookingManagement
	 *            the booking management
	 * @param facility
	 *            the facility
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return true, if the facility is partially free
	 */
	boolean isFacilityPartiallyFree(BookingManagement bookingManagement, Facility facility, Date startDate,
			Date endDate);

}
