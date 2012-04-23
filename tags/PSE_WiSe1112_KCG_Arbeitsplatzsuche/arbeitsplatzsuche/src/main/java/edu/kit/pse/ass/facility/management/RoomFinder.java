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

/**
 * The Class RoomFinder, extends the abstract class FacilityFinder. It is adjusted for the need to find matching rooms
 */
public class RoomFinder extends FacilityFinder {

	/** The room query, containing the needed properties, a search text and the minimum workplaces */
	protected RoomQuery roomQuery;

	/**
	 * Instantiates a new room finder.
	 * 
	 * @param roomQuery
	 *            - the room query
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
		// return empty list if nothing found
		if (facilities == null || facilities.isEmpty()) {
			return new ArrayList<FacilityResult>();
		}
		int minWorkpl = roomQuery.getMinimumWorkplaces();
		ArrayList<FacilityResult> result = new ArrayList<FacilityResult>();
		// create search words array
		String[] searchText = null;
		if (roomQuery.getSearchText() != null) {
			searchText = roomQuery.getSearchText().toLowerCase().split("\\s+");
		}
		// check all facilities for the requested options
		Iterator<Room> iter = facilities.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			ArrayList<Facility> matchingWorkplaces = null;
			boolean enoughWorkplaces = (r.getContainedFacilities() != null) 
					? r.getContainedFacilities().size() >= minWorkpl : minWorkpl == 0;
			if (enoughWorkplaces) {
				matchingWorkplaces = chooseMatchingWorkplaces(r);
				enoughWorkplaces = matchingWorkplaces.size() >= minWorkpl;
			}

			// if we have matching workplaces and searchtext is found, add
			if (enoughWorkplaces && hasSearchText(r, searchText)) {
				result.add(new FacilityResult(r, matchingWorkplaces));
			}
		}
		return result;
	}

	/**
	 * Checks if the room contains all search words in building name, building number, room name, room number
	 * 
	 * @param r
	 *            the room, must not be null
	 * @param searchText
	 *            the array of search words, must be in lower case
	 * @return true, if all words found or searchText is null
	 */
	private boolean hasSearchText(Room r, String[] searchText) {
		assert r != null;
		// check for search text
		if (searchText == null || searchText.length == 0) {
			return true;
		}
		String toSearch = "";
		// get number and name of building
		if (r.getParentFacility() instanceof Building) {
			Building b = (Building) r.getParentFacility();
			toSearch = b.getName() + " " + b.getNumber();
		}
		// get name and number of room
		toSearch += toSearch + " " + r.getName() + " " + " " + r.getNumber() + " " + r.getLevel();
		// case insensitive search
		toSearch = toSearch.toLowerCase();
		// if a word is not found, abort
		for (String s : searchText) {
			if (!toSearch.contains(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Choose matching workplaces from a room.
	 * 
	 * @param r
	 *            the room to search in, must not be null
	 * @return the new array list with all matching workplaces
	 */
	private ArrayList<Facility> chooseMatchingWorkplaces(Room r) {
		assert r != null;
		ArrayList<Facility> matchingWorkplaces;
		// if room has not all properties, lokk for workplaces with the missing ones
		if (!r.hasInheritedProperties(this.roomQuery.getProperties())) {
			// first find all properties the Room is lacking
			LinkedList<Property> workplaceProps = new LinkedList<Property>();
			for (Property property : this.roomQuery.getProperties()) {
				if (!r.hasInheritedProperty(property)) {
					workplaceProps.add(property);
				}
			}
			// find all workplaces with the missing properties
			matchingWorkplaces = new ArrayList<Facility>();
			for (Facility containedFac : r.getContainedFacilities()) {
				if (containedFac.getProperties().containsAll(workplaceProps)) {
					matchingWorkplaces.add(containedFac);
				}
			}
		} else {
			// room has all required properties. All workplaces match
			matchingWorkplaces = new ArrayList<Facility>(r.getContainedFacilities());
		}
		return matchingWorkplaces;
	}
}
