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
	
	//TODO: Beanreferenzen als ManagedProperty
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
		resetView();
		MapPosition position = positionRepresentedBySearchTerm(searchTerm, inputField);
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void searchTriggered(POI soughtAfter) {
		resetView();
		highlightPOI(soughtAfter);
	}
	
	//Effects that the poi 'poi' is highlighted in the view.
	private void highlightPOI(POI poi) {
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
		MapPosition from = positionRepresentedBySearchTerm(routeFrom, InputFields.ROUTE_FROM);
		if (from == null) {
			POI poiFrom = performSearch(routeFrom, InputFields.ROUTE_FROM);
			if (poiFrom != null) {
				from = new MapPosition (poiFrom.getPosition().getLatitude(), poiFrom.getPosition().getLongitude(), poiFrom.getMap());
			}
		} 		
		MapPosition to = positionRepresentedBySearchTerm(routeTo, InputFields.ROUTE_TO);
		if (to == null) {
			POI poiTo = performSearch(routeTo, InputFields.ROUTE_TO);
			if (poiTo != null) {
				to = new MapPosition (poiTo.getPosition().getLatitude(), poiTo.getPosition().getLongitude(), poiTo.getMap());
			}
		} 
		resetView();
		if (from != null && to != null) {			
			calculateRoute(from, to);
		}		
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(String routeFrom, MapPosition to) {
		MapPosition from = positionRepresentedBySearchTerm(routeFrom, InputFields.ROUTE_FROM);
		if (from != null) {
			resetView();
			calculateRoute(from, to);
		} else {
			POI poi = performSearch(routeFrom, InputFields.ROUTE_FROM);
			if (poi != null) {
				from = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
				resetView();
				calculateRoute(from, to);
			}
		} 		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(MapPosition from, String routeTo) {
		MapPosition to = positionRepresentedBySearchTerm(routeTo, InputFields.ROUTE_TO);
		if (to != null) {
			resetView();
			calculateRoute(from, to);
		} else {
			POI poi = performSearch(routeTo, InputFields.ROUTE_TO);
			if (poi != null) {
				to = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
				resetView();
				calculateRoute(from, to);
			}
		} 	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void routeTriggered(MapPosition from, MapPosition to) {
		resetView();
		calculateRoute(from, to);
	}
	
	//Effects that route from 'from' to 'to' is calculated and displayed in the view.
	private void calculateRoute(MapPosition from, MapPosition to) {
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
				mapModel.setBuilding(null);
			}
		} else {
			inputModel.setRouteCalculationFailed(true);
		}
	}
	
	//Tries to convert the string 'searchTerm' into a MapPosition and returns it.
	//If the corresponding marker is set in the MapModel and equals the represented coordinates, 
	//the MapPosition of this marker will be returned.
	//If the conversion fails, null will be returned.
	private MapPosition positionRepresentedBySearchTerm (String searchTerm, InputFields inputField) {
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return null;
		} else {
			if (inputField.equals(InputFields.ROUTE_FROM)) {
				MapPosition markerFrom = mapModel.getMarkerFrom();
				if (markerFrom != null && equivalent(coordinate, markerFrom)) {	
					return markerFrom; 
				} else {
					return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), defaultModelValueClass.getDefaultMap());
				}
			} else {
				MapPosition markerTo = mapModel.getMarkerTo();
				if (markerTo != null && equivalent(coordinate, markerTo)) {		
					return markerTo;
				} else {
					return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), defaultModelValueClass.getDefaultMap());
				}
			}			
		}
	}
	
	private boolean equivalent(WorldPosition worldposition, MapPosition mapposition) {
		double eps = 1e-6;
		if (Math.abs(worldposition.getLatitude() - mapposition.getLatitude()) < eps 
				&& Math.abs(worldposition.getLongitude() - mapposition.getLongitude()) < eps) {
			return true;
		} else {
			return false;
		}
	}

	//Executes the search after 'searchTerm' and returns the poi that has been found if there's a unique search result.
	//If there is no or more than one search result, null will be returned and the inputModel will be given the corresponding information.
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
	
	//Resets the view which means that no highlighted pois, routes and so on will be displayed anymore.
	private void resetView() {
		mapModel.setHighlightedPOI(null);
		mapModel.setRoute(null);
		mapModel.setMarkerFrom(null);
		mapModel.setMarkerTo(null);
		inputModel.setRouteFromSearchFailed(false);
		inputModel.setRouteToSearchFailed(false);
		inputModel.setRouteCalculationFailed(false);
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
	public void changeFloorTriggered(Map floor) {
		logger.info("change floor to: " + floor.getName());
		mapModel.setMap(floor);
	}
		
}
