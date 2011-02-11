package edu.kit.cm.kitcampusguide.controller;

import java.util.List;
import org.apache.log4j.Logger;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

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
	
	ELContext el = FacesContext.getCurrentInstance().getELContext();
	DefaultModelValues defaultModelValueClass = (DefaultModelValues) el.getELResolver().getValue(el, null,
	    "defaultModelValues");
	
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
				highlightPOI(poi);
			}			
		}
	}
	
	public void searchTriggered(POI soughtAfter) {
		highlightPOI(soughtAfter);
	}
	
	private void highlightPOI(POI poi) {
		resetInputArea();
		logger.info("highlight poi: " + poi.getName());
		mapModel.setHighlightedPOI(poi);
		mapModel.setMapLocator(new MapLocator (new MapPosition(poi.getPosition().getLatitude(),
				poi.getPosition().getLongitude(), poi.getMap())));
		mapModel.setMap(poi.getMap());
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(String routeFrom, String routeTo) {
		MapPosition from = positionRepresentedBySearchTerm(routeFrom);
		if (from == null) {
			POI poi = performSearch(routeFrom, InputFields.ROUTE_FROM);
			if (poi != null) {
				from = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		} 		
		MapPosition to = positionRepresentedBySearchTerm(routeTo);
		if (to == null) {
			POI poi = performSearch(routeTo, InputFields.ROUTE_TO);
			if (poi != null) {
				to = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		} 
		if (from != null && to != null) {			
			calculateRoute(from, to);
		}		
	}
	
	public void routeTriggered(MapPosition from, MapPosition to) {
		calculateRoute(from, to);
	}
	
	public void routeTriggered(String routeFrom, MapPosition to) {
		MapPosition from = positionRepresentedBySearchTerm(routeFrom);
		if (from != null) {
			calculateRoute(from, to);
		} else {
			POI poi = performSearch(routeFrom, InputFields.ROUTE_FROM);
			if (poi != null) {
				from = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
				calculateRoute(from, to);
			}
		} 		
	}
	
	public void routeTriggered(MapPosition from, String routeTo) {
		MapPosition to = positionRepresentedBySearchTerm(routeTo);
		if (to != null) {
			calculateRoute(from, to);
		} else {
			POI poi = performSearch(routeTo, InputFields.ROUTE_TO);
			if (poi != null) {
				to = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
				calculateRoute(from, to);
			}
		} 	
	}
	
	private void calculateRoute(MapPosition from, MapPosition to) {
		resetInputArea();
		Route route = routing.calculateRoute(from, to);
		if (route != null) {
			logger.info("Display route");
			mapModel.setMarkerFrom(from);
			mapModel.setMarkerTo(to);
			mapModel.setRoute(route);
			mapModel.setMapLocator(new MapLocator (route.getBoundingBox()));
			if (from.getMap().getID() == to.getMap().getID()) {
				mapModel.setMap(from.getMap());
			} else {
				mapModel.setMap(defaultModelValueClass.getDefaultMap());
			}
		} else {
			inputModel.setRouteCalculationFailed(true);
		}
	}
	
	private void resetInputArea() {
		mapModel.setHighlightedPOI(null);
		mapModel.setRoute(null);
		mapModel.setMarkerFrom(null);
		mapModel.setMarkerTo(null);
		inputModel.setRouteFromProposalList(null);
		inputModel.setRouteFromSearchFailed(false);
		inputModel.setRouteToProposalList(null);
		inputModel.setRouteToSearchFailed(false);
	}
	
	private MapPosition positionRepresentedBySearchTerm (String searchTerm) {
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return null;
		} else {
			return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), defaultModelValueClass.getDefaultMap());
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
			if (inputField == InputFields.ROUTE_FROM) {
				inputModel.setRouteFromField("");
				inputModel.setRouteFromProposalList(searchResults);			
			} else {
				inputModel.setRouteToField("");
				inputModel.setRouteToProposalList(searchResults);		
			}
			return null;
		}
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
		mapModel.setMap(defaultModelValueClass.getDefaultMap());
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
