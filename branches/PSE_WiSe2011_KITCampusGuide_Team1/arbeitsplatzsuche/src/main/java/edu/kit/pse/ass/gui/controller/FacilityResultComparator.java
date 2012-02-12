package edu.kit.pse.ass.gui.controller;

import java.util.Comparator;

import edu.kit.pse.ass.booking.management.FreeFacilityResult;
import edu.kit.pse.ass.entity.Room;

/**
 * Comparator for FreeFacilityResult
 * 
 * it compares two FreeFacilityResults according to the sort column index.
 * 
 * @author Jannis Koch
 * 
 */
public class FacilityResultComparator implements Comparator<FreeFacilityResult> {

	/** the index of the column to be sorted */
	private final int sortColumnIndex;

	/**
	 * constructs a new FacilityResultComparator setting the sort column index to the given value
	 * 
	 * @param sortColumnIndex
	 *            the index of the column to be sorted
	 */
	public FacilityResultComparator(int sortColumnIndex) {
		this.sortColumnIndex = sortColumnIndex;
	}

	/**
	 * compares the given FreeFacilityResults according to the sort column index of this Comparator
	 * 
	 * @param c1
	 *            the first FreeFacilityResult
	 * @param c2
	 *            the second FreeFacilityResult
	 * @return the comparison result
	 */
	@Override
	public int compare(FreeFacilityResult c1, FreeFacilityResult c2) {
		switch (sortColumnIndex) {

		case 0:
			return compareRoomName(c1, c2);

		case 1:
			return compareBuildingName(c1, c2);

		case 3:
			return compareStartDate(c1, c2);

		default:
			return 0;
		}
	}

	/**
	 * compares the given FreeFacilityResults according to their room names
	 * 
	 * @param c1
	 *            the first FreeFacilityResult
	 * @param c2
	 *            the second FreeFacilityResult
	 * @return the comparison result
	 */
	private int compareRoomName(FreeFacilityResult c1, FreeFacilityResult c2) {
		if (c1.getFacility() instanceof Room && c2.getFacility() instanceof Room) {
			// Both facilities are rooms - compare the formatted name for a room (this is the name shown in
			// DataTables!)
			Room r1 = (Room) c1.getFacility();
			Room r2 = (Room) c2.getFacility();
			return SearchController.formatRoomName(r1).compareTo(SearchController.formatRoomName(r2));
		} else {
			// Compare the normal name of the facility
			return c1.getFacility().getName().compareTo(c2.getFacility().getName());
		}
	}

	/**
	 * compares the given FreeFacilityResults according to their building names
	 * 
	 * @param c1
	 *            the first FreeFacilityResult
	 * @param c2
	 *            the second FreeFacilityResult
	 * @return the comparison result
	 */
	private int compareBuildingName(FreeFacilityResult c1, FreeFacilityResult c2) {
		return c1.getFacility().getParentFacility().getName()
				.compareTo(c2.getFacility().getParentFacility().getName());
	}

	/**
	 * compares the given FreeFacilityResults according to their start dates
	 * 
	 * @param c1
	 *            the first FreeFacilityResult
	 * @param c2
	 *            the second FreeFacilityResult
	 * @return the comparison result
	 */
	private int compareStartDate(FreeFacilityResult c1, FreeFacilityResult c2) {
		return c1.getStart().compareTo(c2.getStart());
	}

}
