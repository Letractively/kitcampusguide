package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;
import java.util.Collections;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
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
	
	@Override
	public void changeToBuildingMap(int mapID) {
		mapModel.setMap(Map.getMapByID(mapID));
		mapModel.setPOIs(Collections.<POI>emptyList());
	}

	@Override
	public void showPOIsInBuilding(int buildingID) {
		System.out.println("ShowPOIsInBuilding");
		Building building = Building.getBuildingByID(buildingID);
		// TODO: Add category filter
		mapModel.createBuildingPOIList(
				building.getBuildingPOI(),
				 new ArrayList<POI>(source.getPOIsByBuilding(building, 
//						 categoryModel.getCategories()
						 null)));
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}
	
	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}
}
