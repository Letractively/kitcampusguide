package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.RoomQuery;

/**
 * @author ???
 * This class represents a room query.
 */
public class FreeRoomQuery extends RoomQuery implements FreeFacilityQuery {

	/**
	 * @param properties the properties the workplaces should have.
	 * @param searchText the search text to search for.
	 * @param minimumWorkplaces the amount of places to search for.
	 */
	public FreeRoomQuery(Collection<Property> properties, String searchText, int minimumWorkplaces) {
		super(properties, searchText, minimumWorkplaces);
	}

	@Override
	public boolean isFacilityPartiallyFree(BookingManagement bookingManagement, Facility facility, Date startDate,
			Date endDate) {
		if (bookingManagement == null || facility == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException("at least one parameter is null.");
		}
		if (!(facility instanceof Room)) {
			throw new IllegalArgumentException("The facility must be a room.");
		}

		// check if enough fitting workplaces are free
		Collection<Property> missingProperties = findMissingProperties(facility);
		int freePlaces = 0;
		for (Facility f : facility.getContainedFacilities()) {
			if (f.getProperties().containsAll(missingProperties)) {
				try {
					if (bookingManagement.isFacilityFree(f.getId(), startDate, endDate)) {
						freePlaces++;
						if (freePlaces >= this.minimumWorkplaces) {
							return true;
						}
					}
				} catch (FacilityNotFoundException e) {
					//TODO
					
				}
			}

		}
		return false;
	}

	private Collection<Property> findMissingProperties(Facility facility) {
		if (this.getProperties() == null) {
			return new LinkedList<Property>();
		}
		LinkedList<Property> missingProperties = new LinkedList<Property>(this.getProperties());
		missingProperties.removeAll(facility.getInheritedProperties());
		return missingProperties;
	}

}
