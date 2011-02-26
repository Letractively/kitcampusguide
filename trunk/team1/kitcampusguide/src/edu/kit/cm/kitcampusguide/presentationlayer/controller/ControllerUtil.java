package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Map;

/**
 * Contains some useful help functions used by all controller classes.
 * @author Stefan
 *
 */
public final class ControllerUtil {
	
	/**
	 * Used to retrieve POIs.
	 */
	private static final POISource poiSource = POISourceImpl.getInstance();

	/**
	 * Sets the map of a map model to a specific value. Additionally, the drawn
	 * POIs are set to show only POIs positioned on the new map.
	 * 
	 * @param mapModel
	 *            the <code>MapModel</code> which will be modified
	 * @param categoryModel
	 *            the category model currently used.
	 * @param map
	 *            the new <code>Map</code> which will be set
	 * @return <code>true</code> if the new map does not equals the old map and
	 *         was changed
	 * @throws NullPointerException
	 *             if one of the arguments is <code>null</code>
	 */
	public static boolean setMap(MapModel mapModel, CategoryModel categoryModel, Map map) {
		if (!mapModel.getMap().equals(map)) {
			mapModel.setMap(map);
			// Only set the POIs if really necessary, otherwise they would be
			// sent to the client for each map change.
			refreshPOIs(mapModel, categoryModel);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the currently drawn POIs to the current map's POIs filtered by the
	 * categories specified by the user.
	 * 
	 * @param mapModel
	 *            the currently used map model
	 * @param categoryModel
	 *            the currently used category model
	 */
	public static void refreshPOIs(MapModel mapModel, CategoryModel categoryModel) {
		// TODO: Apply category filter
		mapModel.setPOIs(poiSource.getPOIsBySection(null, mapModel.getMap(), null));
	}
}
