package edu.kit.cm.kitcampusguide.applicationlogic.poisource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.kit.cm.kitcampusguide.datalayer.poidb.DefaultPOIDB;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDB;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POIQuery;

/**
 * Realizes the POISource-component. a {@link DefaultPOIDB} is used to retrieve
 * POIs.
 * 
 */
public class POISourceImpl implements POISource {

	/**
	 * The singleton instance of <code>POISource</code>.
	 */
	private static POISource instance;

	/**
	 * Holds a reference to the used POIDB.
	 */
	private POIDB db;

	/**
	 * Returns the single instance of this <code>POISourceImplementation</code>.
	 * 
	 * @return a {@link POISource}
	 */
	public static POISource getInstance() {
		return (instance == null ? instance = new POISourceImpl() : instance);
	}

	@Override
	public Collection<POI> getPOIsBySection(MapSection section, Map map,
			Collection<Category> categories) {
		POIQuery query = new POIQuery(section, (map == null) ? null
				: Collections.singleton(map), categories);
		return db.getPOIsByQuery(query);
	}

	@Override
	public List<POI> getPOIsBySearch(String searchTerm) {
		return db.getPOIsBySearch(searchTerm);
	}

	@Override
	public POI getPOIByID(String id) {
		return db.getPOIByID(id);
	}

	@Override
	public Collection<POI> getPOIsByBuilding(Building building,
			Collection<Category> categories) {
		POIQuery query = new POIQuery(null, building.getFloors(), categories);
		return db.getPOIsByQuery(query);
	}

	/**
	 * Prevent instantiation
	 */
	private POISourceImpl() {
		this.db = DefaultPOIDB.getInstance();
	}

}
