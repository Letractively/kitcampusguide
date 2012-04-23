package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Implements {@link POIListener}
 * @see POIListener
 * @author Fabian
 *
 */
public class POIListenerImpl implements POIListener {

	private MapModel mapModel;
	private POISource source = POISourceImpl.getInstance();
	private CategoryModel categoryModel;
	private static Logger logger = Logger.getLogger(POIListenerImpl.class);

	/**
	 * Changes the currently displayed map to the map belonging to the
	 * given building.
	 */
	@Override
	public void changeToBuildingMap(int buildingID) {
		logger.debug("changeToBuildingMap recieved, buildingID: " + buildingID);
		Building building = Building.getBuildingByID(buildingID);
		if (building != null) {
			mapModel.setBuilding(building);
			ControllerUtil.setMap(mapModel, categoryModel, building.getGroundFloor());
			mapModel.setHighlightedPOI(null);
		}
	}

	/**
	 * This implementation will create a building POI list which is shown
	 * afterwards. The list contains all POIs in the building specified by the
	 * given id.
	 */
	@Override
	public void showPOIsInBuilding(int buildingID) {
		logger.debug("showPOIsInBuilding recieved, buildingID: " + buildingID);
		Building building = Building.getBuildingByID(buildingID);
		mapModel.createBuildingPOIList(
				building.getBuildingPOI(),
				// TODO: Add category filter, maybe move this call into ControllerUtil
				 new ArrayList<POI>(source.getPOIsByBuilding(building,
						 null)));
	}

	/**
	 * Sets the new map model for this instance. This method is used by the jsf
	 * managed property injection mechanism.
	 * 
	 * @param mapModel
	 *            the new map model
	 */
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	/**
	 * Sets the new category model for this instance. This method is used by the
	 * jsf managed property injeciton mechanism.
	 * 
	 * @param categoryModel
	 *            the new category model
	 */
	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	/**
	 * This implementation will simply highlight the POI defined by the give id,
	 * change the map to the map where the POI is placed on and center the view
	 * on the given POI.
	 */
	@Override
	public void listEntryClicked(String poiID) {
		logger.debug("listEntryClicked recieved, poiID: " + poiID);
		POI poi = source.getPOIByID(poiID);
		if (poi != null) {
			mapModel.setBuilding(mapModel.getBuildingPOI().getBuilding());
			ControllerUtil.setMap(mapModel, categoryModel, poi.getMap());
			mapModel.setHighlightedPOI(poi);
			mapModel.setMapLocator(new MapLocator(poi.getPosition()));
		}
	}
}
