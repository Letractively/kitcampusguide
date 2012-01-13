package edu.kit.pse.ass.facility.management;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.Workplace;
import edu.kit.pse.ass.facility.dao.FacilityDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class RoomFinder.
 */
public class RoomFinder extends FacilityFinder {

	/** The room query. */
	protected RoomQuery roomQuery;

	/**
	 * Instantiates a new room finder.
	 * 
	 * @param roomQuery
	 *            the room query
	 */
	public RoomFinder(RoomQuery roomQuery) {
		this.roomQuery = roomQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.kit.pse.ass.facility.management.FacilityFinder#execute()
	 */
	@Override
	@Transactional
	public Collection<Room> execute(FacilityDAO facilityDAO) {
		// fetch facilities with matching properties
		Collection<Facility> facilities = facilityDAO.getFacilities(roomQuery
				.getProperties());
		if (facilities == null || facilities.isEmpty()) {
			return new LinkedHashSet<Room>();
		}
		String[] searchText = roomQuery.getSearchText().toLowerCase()
				.split("\\s+");
		int minWorkpl = roomQuery.getMinimumWorkplaces();
		LinkedHashSet<Room> result = new LinkedHashSet<Room>(
				facilities.size() / 6);
		// check all facilities for the requested options
		Iterator<Facility> iter = facilities.iterator();
		while (iter.hasNext()) {
			Facility f = iter.next();
			// if a workplace with the correct properties was found, add its
			// room
			if (f instanceof Workplace) {
				f = f.getParentFacility();
			}
			// if we have a room and it is not added yet, check its options
			if (f instanceof Room && !result.contains(f)) {
				Room r = (Room) f;
				// check minimum workplaces
				boolean add = (r.getContainedFacilities() != null) ? r
						.getContainedFacilities().size() >= minWorkpl
						: minWorkpl == 0;
				if (add) {
					// check for search text
					String search = "";
					if (r.getParentFacility() instanceof Building) {
						Building b = (Building) r.getParentFacility();
						search = b.getName() + " " + b.getNumber();
					}
					search = (search + " " + r.getName() + " " + " "
							+ r.getNumber() + " " + r.getLevel()).toLowerCase();
					for (String s : searchText) {
						if (!search.contains(s)) {
							add = false;
							break;
						}
					}
				}
				if (add && roomQuery.getWorkplaceProperties() != null) {
					// check properties of workplaces
					Collection<Property> props = roomQuery
							.getWorkplaceProperties();
					int fittingPlaces = 0;
					for (Facility containedFac : r.getContainedFacilities()) {
						if (containedFac.getProperties().containsAll(props)) {
							fittingPlaces++;
						}
					}
					if (fittingPlaces < minWorkpl) {
						add = false;
					}
				}
				if (add) {
					result.add(r);
				}
			}
		}
		return result;
	}
}
