package edu.kit.cm.kitcampusguide.controller;

import java.util.List;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * 
 * @author Fabian
 *
 */
public class POIListenerImpl implements POIListener {

	private MapModel mapModel = null; //TODO: Change so the MapModel of the current session is invoked.
	private POISource source = POISourceImpl.getInstance();
	private CategoryModel categoryModel = null; //TODO: Change so the CategoryModel of the current session is invoked.
	
	@Override
	public void changeToBuildingMap(int mapID) {
		mapModel.setMap(Map.getMapByID(mapID));
	}

	@Override
	public void showPOIsInBuilding(int buildingID) {
		Building building = Building.getBuildingByID(buildingID);
		mapModel.setBuildingPOI(
				building.getBuildingPOI(),
				(List<POI>) source.getPOIsByBuilding(building, categoryModel.getCategories())); //TODO: Is this cast valid?
	}

}
