package edu.kit.pse.ass.booking.management;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.facility.management.FacilityNotFoundException;
import edu.kit.pse.ass.facility.management.RoomQuery;

public class FreeRoomQuery extends RoomQuery implements FreeFacilityQuery {

	public FreeRoomQuery(Collection<Property> properties, String searchText, int minimumWorkplaces) {
		super(properties, searchText, minimumWorkplaces);
	}

	@Override
	public boolean isFacilityPartiallyFree(BookingManagement bookingManagement, Facility facility, Date startDate,
			Date endDate) {
		if (bookingManagement == null || facility == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException("at least one parameter is null.");
		}
		int freePlaces = 0;
		LinkedList<Property> workplaceProperties = new LinkedList<Property>(this.getProperties());
		workplaceProperties.removeAll(facility.getInheritedProperties());
		for (Facility f : facility.getContainedFacilities()) {
			if (workplaceProperties.isEmpty() || f.getProperties().containsAll(workplaceProperties)) {
				try {
					if (bookingManagement.isFacilityFree(f.getId(), startDate, endDate)) {
						freePlaces++;
						if (freePlaces >= this.minimumWorkplaces) {
							return true;
						}
					}
				} catch (FacilityNotFoundException e) {
				}
			}

		}

		return false;
	}
}
