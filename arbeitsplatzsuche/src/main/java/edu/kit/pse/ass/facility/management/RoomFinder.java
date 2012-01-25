package edu.kit.pse.ass.facility.management;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
	public Collection<Room> execute(FacilityDAO facilityDAO) {
		// fetch facilities with matching properties
		Collection<Room> facilities = facilityDAO.getAllFacilities(Room.class);
		if (facilities == null || facilities.isEmpty()) {
			return new LinkedHashSet<Room>();
		}
		String[] searchText = null;
		if (roomQuery.getSearchText() != null) {
			searchText = roomQuery.getSearchText().toLowerCase().split("\\s+");
		}
		int minWorkpl = roomQuery.getMinimumWorkplaces();
		LinkedHashSet<Room> result = new LinkedHashSet<Room>(facilities.size() / 6);
		// check all facilities for the requested options
		Iterator<Room> iter = facilities.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			boolean add = (r.getContainedFacilities() != null) ? r.getContainedFacilities().size() >= minWorkpl
					: minWorkpl == 0;
			if (add) {
				if (!r.hasInheritedProperties(this.roomQuery.getProperties())) {
					LinkedList<Property> workplaceProps = new LinkedList<Property>();
					for (Property property : this.roomQuery.getProperties()) {
						if (!r.hasInheritedProperty(property)) {
							workplaceProps.add(property);
						}
					}
					int fittingPlaces = 0;
					for (Facility containedFac : r.getContainedFacilities()) {
						if (containedFac.getProperties().containsAll(workplaceProps)) {
							fittingPlaces++;
						}
					}
					if (fittingPlaces < minWorkpl) {
						add = false;
					}
				}
			}

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
			/*
			 * if (add && roomQuery.getWorkplaceProperties() != null) { // check properties of workplaces
			 * Collection<Property> props = roomQuery.getWorkplaceProperties(); int fittingPlaces = 0; for (Facility
			 * containedFac : r.getContainedFacilities()) { if (containedFac.getProperties().containsAll(props)) {
			 * fittingPlaces++; } } if (fittingPlaces < minWorkpl) { add = false; } }
			 */
			if (add) {
				result.add(r);
			}
		}
		return result;
	}
}
