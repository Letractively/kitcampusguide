package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategy;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategyImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Implements the interface <code>InputListener</code>.
 *  
 * @author Team1
 */
public class InputListenerImpl implements InputListener {	
	
	private Logger logger = Logger.getLogger(InputListenerImpl.class);
	
	private CoordinateManager cm = CoordinateManagerImpl.getInstance();	
	private POISource poiSource = POISourceImpl.getInstance();	
	private RoutingStrategy routing = RoutingStrategyImpl.getInstance();
		
	private ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	private InputModel inputModel = (InputModel) FacesContext.getCurrentInstance().getApplication()
    		.getELResolver().getValue(elContext, null, "inputModel");
	private TranslationModel translationModel = (TranslationModel) FacesContext.getCurrentInstance().getApplication()
			.getELResolver().getValue(elContext, null, "translationModel");	
	private MapModel mapModel;
	
	/**
	 * Default constructor.
	 */
	public InputListenerImpl() {
		
	}
		
	/**
	 * Sets the mapModel-attribute to <code>mapModel</code>.
	 * @param mapModel MapModel to which the mapModel-attribute shall be set.
	 *
	 */
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void searchTriggered(String searchTerm, InputFields inputField) {
		resetInputArea();
		MapPosition position = positionRepresentedBySearchTerm(searchTerm);
		if (position != null) {
			if (inputField == InputFields.ROUTE_FROM) {
				logger.info("set markerFrom to " + searchTerm);
				mapModel.setMarkerFrom(position);
			} else {
				logger.info("set markerTo to " + searchTerm);
				mapModel.setMarkerTo(position);
			}
		} else {
			POI poi = performSearch(searchTerm, inputField);
			if (poi != null) {
				logger.info("highlight poi: " + poi.getName());
				mapModel.setHighlightedPOI(poi);
				mapModel.setMapLocator(new MapLocator (new MapPosition(poi.getPosition().getLatitude(),
						poi.getPosition().getLongitude(), poi.getMap())));
				mapModel.setMap(poi.getMap());
			}			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(String routeFrom, String routeTo) {
		resetInputArea();
		MapPosition from = positionRepresentedBySearchTerm(routeFrom);
		if (from != null) {
			mapModel.setMarkerFrom(from);
		} else {
			POI poi = performSearch(routeFrom, InputFields.ROUTE_FROM);
			if (poi != null) {
				from = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		} 		
		MapPosition to = positionRepresentedBySearchTerm(routeTo);
		if (to != null) {
			mapModel.setMarkerTo(to);
		} else {
			POI poi = performSearch(routeTo, InputFields.ROUTE_TO);
			if (poi != null) {
				to = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		} 
		if (from != null && to != null) {
			Route route = routing.calculateRoute(from, to);
			if (route != null) {
				logger.info("Display route from: " + from + " to: " + to);
				mapModel.setRoute(route);
				mapModel.setMapLocator(new MapLocator (route.getBoundingBox()));
				if (from.getMap().getID() == to.getMap().getID()) {
					mapModel.setMap(from.getMap());
				} else {
					mapModel.setMap(Map.getMapByID(1));
				}
			} else {
				//TODO
				inputModel.setRouteCalculationFailed(true);
			}
		}		
	}
	
	private void resetInputArea() {
		mapModel.setHighlightedPOI(null);
		mapModel.setRoute(null);
		mapModel.setMarkerFrom(null);
		mapModel.setMarkerTo(null);
		inputModel.setRouteFromProposalListIsVisible(false);
		inputModel.setRouteFromSearchFailed(false);
		inputModel.setRouteToProposalListIsVisible(false);
		inputModel.setRouteToSearchFailed(false);
	}
	
	private MapPosition positionRepresentedBySearchTerm (String searchTerm) {
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return null;
		} else {
			return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), Map.getMapByID(1));
		}
	}

	private POI performSearch(String searchTerm, InputFields inputField) {
		List<POI> searchResults = poiSource.getPOIsBySearch(searchTerm);	
		if (searchResults == null || searchResults.size() == 0) {
			logger.info("no search results for " + searchTerm);
			if (inputField == InputFields.ROUTE_FROM) {
				inputModel.setRouteFromSearchFailed(true);
			} else {
				inputModel.setRouteToSearchFailed(true);
			}
			return null;
		} else if (searchResults.size() == 1) {
			logger.info("unique search result for " + searchTerm + " : " + searchResults.get(0).getName());
			return searchResults.get(0);
		} else {
			logger.info("multiple search results for " + searchTerm);	
			List<SelectItem> proposalList = createProposalList(searchResults);
			if (inputField == InputFields.ROUTE_FROM) {
				inputModel.setRouteFromField("");
				inputModel.setRouteFromProposalList(proposalList);
				inputModel.setRouteFromProposalListIsVisible(true);			
			} else {
				inputModel.setRouteToField("");
				inputModel.setRouteToProposalList(proposalList);
				inputModel.setRouteToProposalListIsVisible(true);			
			}
			return null;
		}
	}	
	
	private List<SelectItem> createProposalList(List<POI> searchResults) {
		List<SelectItem> proposalList = new ArrayList<SelectItem>();
		for (POI poi : searchResults) {
			SelectItem item = new SelectItem();
			item.setLabel(poi.getName());
			item.setValue(poi.getName());
			proposalList.add(item);	
		}
		return proposalList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void languageChangeTriggered(String language) {
		logger.info("change language to: " + language);
		translationModel.setCurrentLanguage(language);
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeToMapViewTriggered() {
		logger.info("change to map view");
		// TODO: Insert default map here
		mapModel.setMap(Map.getMapByID(1));
		mapModel.setBuilding(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void changeFloorTriggered(int floorNo) {
		Map floor = mapModel.getBuilding().getFloors().get(floorNo);
		logger.info("change floor to: " + floor.getName());
		mapModel.setMap(floor);
	}
		
}
