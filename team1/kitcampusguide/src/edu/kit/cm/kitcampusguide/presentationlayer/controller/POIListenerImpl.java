package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.util.ArrayList;

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
	
	@Override
	public void changeToBuildingMap(int buildingID) {
		System.out.println("ChangeToBuildingMap: " + buildingID);
		Building building = Building.getBuildingByID(buildingID);
		mapModel.setBuilding(building);
		ControllerUtil.setMap(mapModel, null, building.getGroundFloor());
		mapModel.setHighlightedPOI(null);
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

	@Override
	public void listEntryClicked(String poiID) {
		// TODO Auto-generated method stub
		POI poi = source.getPOIByID(poiID);
		mapModel.setBuilding(mapModel.getBuildingPOI().getBuilding());
		ControllerUtil.setMap(mapModel, categoryModel, poi.getMap());
		mapModel.setHighlightedPOI(poi);
		mapModel.setMapLocator(new MapLocator(poi.getPosition()));
	}
}
