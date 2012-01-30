package edu.kit.pse.ass.facility.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
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
	public Collection<FacilityResult> execute(FacilityDAO facilityDAO) {
		// fetch facilities with matching properties
		Collection<Room> facilities = facilityDAO.getAllFacilities(Room.class);
		if (facilities == null || facilities.isEmpty()) {
			return new ArrayList<FacilityResult>();
		}
		String[] searchText = null;
		if (roomQuery.getSearchText() != null) {
			searchText = roomQuery.getSearchText().toLowerCase().split("\\s+");
		}
		int minWorkpl = roomQuery.getMinimumWorkplaces();
		ArrayList<FacilityResult> result = new ArrayList<FacilityResult>();
		// check all facilities for the requested options
		Iterator<Room> iter = facilities.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			ArrayList<Facility> matchingWorkplaces = null;
			boolean add = (r.getContainedFacilities() != null) ? r.getContainedFacilities().size() >= minWorkpl
					: minWorkpl == 0;
			if (add) {
				// if room has not all properties, lokk for workplaces with the missing ones
				if (!r.hasInheritedProperties(this.roomQuery.getProperties())) {
					LinkedList<Property> workplaceProps = new LinkedList<Property>();
					for (Property property : this.roomQuery.getProperties()) {
						if (!r.hasInheritedProperty(property)) {
							workplaceProps.add(property);
						}
					}
					matchingWorkplaces = new ArrayList<Facility>();
					int fittingPlaces = 0;
					for (Facility containedFac : r.getContainedFacilities()) {
						if (containedFac.getProperties().containsAll(workplaceProps)) {
							fittingPlaces++;
							matchingWorkplaces.add(containedFac);
						}
					}
					if (fittingPlaces < minWorkpl) {
						add = false;
					}
				} else {
					matchingWorkplaces = new ArrayList<Facility>(r.getContainedFacilities());
				}
			}

			// if we have a search text, try to find it
			if (add && searchText != null) {
				// check for search text
				String search = "";
				if (r.getParentFacility() instanceof Building) {
					Building b = (Building) r.getParentFacility();
					search = b.getName() + " " + b.getNumber();
				}
				search = (search + " " + r.getName() + " " + " " + r.getNumber() + " " + r.getLevel())
						.toLowerCase();
				for (String s : searchText) {
					if (!search.contains(s)) {
						add = false;
						break;
					}
				}
			}

			// if all tests succeeded, we have a result
			if (add) {
				result.add(new FacilityResult(r, matchingWorkplaces));
			}
		}
		return result;
	}
}
