package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategy;
import edu.kit.cm.kitcampusguide.applicationlogic.routing.RoutingStrategyImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.CategoryModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Implements the interface {@link InputListener}.
 *  
 * @author Team1
 */
public class InputListenerImpl implements InputListener {	
	
	private Logger logger = Logger.getLogger(InputListenerImpl.class);
	
	private CoordinateManager cm = CoordinateManagerImpl.getInstance();	
	private POISource poiSource = POISourceImpl.getInstance();	
	private RoutingStrategy routing = RoutingStrategyImpl.getInstance();
	
	// Managed properties, see faces-config.xml
	private MapModel mapModel;
	private InputModel inputModel;
	private TranslationModel translationModel;
	private DefaultModelValues defaultModelValueClass;
	private CategoryModel categoryModel;	
	
	/**
	 * Default constructor.
	 */
	public InputListenerImpl() {
		
	}
			
	/**
	 * If <code>searchTerm</code> can be interpreted as geographical coordinates, the view will be provoked to
	 * set an appropriate marker at the corresponding position. 
	 * Else a search after an appropriate <code>POI</code> will be triggered. 
	 * If this results in a unique search result, the <code>POI</code> which was found will be highlighted. 
	 * If there is no search result, an error message will be displayed.
	 * If there are multiple search results, the view will be provoked to display the corresponding proposal list.
	 */
	@Override
	public void searchTriggered(String searchTerm, InputField inputField) throws NullPointerException {
		if (searchTerm == null || inputField == null) {
			throw new NullPointerException();
		}
		resetView();
		MapPosition position = positionRepresentedBySearchTerm(searchTerm, inputField);
		if (position != null) {
			if (inputField == InputField.ROUTE_FROM) {
				logger.debug("set markerFrom to " + searchTerm);
				mapModel.setMarkerFrom(position);
			} else {
				logger.debug("set markerTo to " + searchTerm);
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
	 * Provokes the view to highlight the <code>POI</code> <code>soughtAfter</code>.
	 */
	@Override
	public void searchTriggered(POI soughtAfter) throws NullPointerException {
		if (soughtAfter == null) {
			throw new NullPointerException();
		}
		resetView();
		highlightPOI(soughtAfter);
	}
		
	/**
	 * Tries to determine the positions represented by the given <code>String</code>s at first. 
	 * If the position of <code>routeFrom</code> or <code>routeTo</code> can't be determined,
	 * an appropriate error message will be displayed.
	 * If there are several possible positions of <code>routeFrom</code> or <code>routeTo</code>,
	 * the view will be provoked to display the corresponding proposal list.
	 * If both can be determined definitely, the calculation of a route in between is triggered.
	 * If the route can be calculated successfully, it is displayed, else an error message is displayed.
	 */
	@Override
	public void routeTriggered(String routeFrom, String routeTo) throws NullPointerException {
		if (routeFrom == null || routeTo == null) {
			throw new NullPointerException();
		}
		resetView();
		MapPosition from = determinePosition(routeFrom, InputField.ROUTE_FROM);	
		MapPosition to = determinePosition(routeTo, InputField.ROUTE_TO);		
		if (from != null && to != null) {			
			calculateRoute(from, to);
		}		
	}	
	
	/**
	 * Tries to determine the position represented by <code>routeFrom</code> at first. 
	 * If the position of <code>routeFrom</code> can't be determined, an error message will be displayed.
	 * If there are several possible positions of <code>routeFrom</code>,
	 * the view will be provoked to display the corresponding proposal list.
	 * If the position can be determined definitely, the calculation of a route to <code>to</code> is triggered.
	 * If the route can be calculated successfully, it is displayed, else an error message is displayed.
	 */
	@Override
	public void routeTriggered(String routeFrom, MapPosition to) throws NullPointerException {
		if (routeFrom == null || to == null) {
			throw new NullPointerException();
		}
		resetView();
		MapPosition from = determinePosition(routeFrom, InputField.ROUTE_FROM);		
		if (from != null) {			
			calculateRoute(from, to);
		} 		
	}
	
	/**
	 * Tries to determine the position represented by <code>routeTo</code> at first.
	 * If the position of <code>routeTo</code> can't be determined, an error message will be displayed.
	 * If there are several possible positions of <code>routeTo</code>,
	 * the view will be provoked to display the corresponding proposal list.
	 * If the position can be determined definitely, the calculation of a route from <code>from</code> is triggered.
	 * If the route can be calculated successfully, it is displayed, else an error message is displayed.
	 */
	@Override
	public void routeTriggered(MapPosition from, String routeTo) throws NullPointerException {
		if (from == null || routeTo == null) {
			throw new NullPointerException();
		}
		resetView();
		MapPosition to = determinePosition(routeTo, InputField.ROUTE_TO);		
		if (to != null) {			
			calculateRoute(from, to);
		} 	
	}
	
	/**
	 * Triggers the calculation of a route from <code>from</code> to <code>to</code>.
	 * If the route can be calculated successfully, it is displayed, else an error message is displayed.
	 */
	@Override
	public void routeTriggered(MapPosition from, MapPosition to) throws NullPointerException {
		if (from == null || to == null) {
			throw new NullPointerException();
		}
		resetView();
		calculateRoute(from, to);
	}
	
	// Effects that the POI 'poi' is highlighted in the view. 'poi' must not be null.
	private void highlightPOI(POI poi) {
		assert poi != null;
		logger.debug("highlight poi: " + poi.getName());
		mapModel.setMarkerFrom(defaultModelValueClass.getDefaultMarkerFrom());
		mapModel.setMarkerTo(defaultModelValueClass.getDefaultMarkerTo());
		mapModel.setHighlightedPOI(poi);
		mapModel.setMapLocator(new MapLocator (poi.getPosition()));
		ControllerUtil.setMap(mapModel, categoryModel, poi.getMap());
		if (!poi.getMap().equals(defaultModelValueClass.getDefaultMap())) {
			mapModel.setBuilding(poi.getMap().getBuilding());
		} else {
			mapModel.setBuilding(defaultModelValueClass.getDefaultBuilding());
		}
	}	
	
	// Effects that the route from the MapPosition 'from' to the MapPosition 'to' is calculated and displayed
	// in the view.
	// If the route can't be calculated, an error message will be displayed.
	// 'from' and 'to' must not be null.
	private void calculateRoute(MapPosition from, MapPosition to) {
		assert from != null;
		assert to != null;
		Route route = routing.calculateRoute(from, to);
		if (route != null) {
			logger.debug("Display route");
			if (!inputWasSetViaContextMenu(inputModel.getRouteFromField(), InputField.ROUTE_FROM)) {
				mapModel.setMarkerFrom(route.getStart());
			}
			if (!inputWasSetViaContextMenu(inputModel.getRouteToField(), InputField.ROUTE_TO)) {
				mapModel.setMarkerTo(route.getEnd());
			}
			mapModel.setRoute(route);
			mapModel.setMapLocator(new MapLocator (route.getBoundingBox()));
			if (from.getMap().getID() != to.getMap().getID()
					|| from.getMap().getID() == defaultModelValueClass
							.getDefaultMap().getID()) {
				ControllerUtil.setMap(mapModel,
						categoryModel, defaultModelValueClass.getDefaultMap());
				mapModel.setBuilding(defaultModelValueClass.getDefaultBuilding());
			} else {
				ControllerUtil.setMap(mapModel, categoryModel, from.getMap());
			}
		} else {
			inputModel.setRouteCalculationFailed(true);
		}
	}
	
	/**
	 * Provokes the view to display the proposal list <code>proposalList</code>.
	 */
	@Override
	public void choiceProposalTriggered(List<POI> proposalList, InputField inputField) throws NullPointerException {
		if (proposalList == null || inputField == null) {
			throw new NullPointerException();
		}
		if (inputField == InputField.ROUTE_FROM) {
			inputModel.setRouteFromField("");
			inputModel.setRouteFromProposalList(proposalList);			
		} else {
			inputModel.setRouteToField("");
			inputModel.setRouteToProposalList(proposalList);		
		}
	}
	
	//Tries to determine the position represented by the String 'searchTerm', which was typed into the
	//InputField 'inputField', and returns it. 'searchTerm' and 'inputField' mustn't be null.
	//Tries to interpret 'searchTerm' as coordinates at first. If the conversion fails, a search after an 
	//appropriate POI will be triggered and its position will be returned.
	//If the position can't be determined, null will be returned.
	private MapPosition determinePosition(String searchTerm, InputField inputField) {
		assert searchTerm != null;
		assert inputField != null;
		MapPosition position = positionRepresentedBySearchTerm(searchTerm, inputField);
		if (position == null) {
			POI poi = performSearch(searchTerm, inputField);
			if (poi != null) {
				position = new MapPosition (poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
			}
		}
		return position;
	}
	
	//Executes the search after 'searchTerm' and returns the POI that has been found 
	//if there's a unique search result. 'searchTerm' and 'inputField' mustn't be null.
	//If there is no search result, an error message will be displayed and null will be returned.
	//If there are multiple search results, the view will be provoked to display the corresponding proposal list.
	private POI performSearch(String searchTerm, InputField inputField) {
		assert searchTerm != null;
		assert inputField != null;
		List<POI> searchResults = poiSource.getPOIsBySearch(searchTerm);	
		if (searchResults == null || searchResults.size() == 0) {
			logger.debug("no search results for " + searchTerm);
			if (inputField == InputField.ROUTE_FROM) {
				inputModel.setRouteFromSearchFailed(true);
			} else {
				inputModel.setRouteToSearchFailed(true);
			}
			return null;
		} else if (searchResults.size() == 1) {
			logger.debug("unique search result for " + searchTerm + " : " + searchResults.get(0).getName());
			return searchResults.get(0);
		} else {
			logger.debug("multiple search results for " + searchTerm);	
			choiceProposalTriggered(searchResults, inputField);
			return null;
		}
	}
	
	//Tries to interpret the String 'searchTerm', which was typed into the InputField 'inputField',
	//as coordinates and returns the corresponding MapPosition. 'searchTerm' and 'inputField' mustn't be null.
	//If the corresponding marker and thus the content of the input field has been set via the context menu, 
	//the MapPosition of this marker will be returned.
	//If the conversion fails, null will be returned.
	private MapPosition positionRepresentedBySearchTerm (String searchTerm, InputField inputField) {
		assert searchTerm != null;
		assert inputField != null;
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return null;
		} else if (inputWasSetViaContextMenu(searchTerm, inputField)) {
			if (inputField.equals(InputField.ROUTE_FROM)) {
				return mapModel.getMarkerFrom(); 
			} else {
				return mapModel.getMarkerTo();
			} 
		} else {
			return new MapPosition(coordinate.getLatitude(), coordinate.getLongitude(), defaultModelValueClass.getDefaultMap());		
		}
	}
	
	//Determines, whether the String 'searchTerm', which was typed into the InputField 'inputField', 
	//has been set by the context menu.
	//This is the case if 'searchTerm' can be interpreted as coordinates and the corresponding marker 
	//is set at the same position (not necessary on the same Map).
	//'searchTerm' and 'inputField' mustn't be null;
	private boolean inputWasSetViaContextMenu (String searchTerm, InputField inputField) {
		assert searchTerm != null;
		assert inputField != null;
		WorldPosition coordinate = cm.stringToCoordinate(searchTerm);
		if (coordinate == null) {
			return false;
		} else {
			if (inputField.equals(InputField.ROUTE_FROM)) {
				MapPosition markerFrom = mapModel.getMarkerFrom();
				return (markerFrom != null && equivalent(coordinate, markerFrom));
			} else {
				MapPosition markerTo = mapModel.getMarkerTo();
				return (markerTo != null && equivalent(coordinate, markerTo));
			}
		}
	}
	
	//Returns true if the WorldPosition 'worldposition' and the MapPosition 'mapposition' are at the same 
	//position, else false. 
	//'worldposition' and 'mapposition' mustn't be null.
	private boolean equivalent(WorldPosition worldposition, MapPosition mapposition) {
		assert worldposition != null;
		assert mapposition != null;
		double eps = 1e-6;
		return (Math.abs(worldposition.getLatitude() - mapposition.getLatitude()) < eps 
				&& Math.abs(worldposition.getLongitude() - mapposition.getLongitude()) < eps);
	}		
	
	//Resets the view to its default state, which means that no highlighted POIs, routes, error messages
	//and so on will be displayed anymore.
	private void resetView() {
		mapModel.setHighlightedPOI(defaultModelValueClass.getDefaultHighlightedPOI());
		mapModel.setRoute(defaultModelValueClass.getDefaultRoute());
		inputModel.setRouteFromProposalList(null);
		inputModel.setRouteToProposalList(null);
		inputModel.setRouteFromSearchFailed(false);
		inputModel.setRouteToSearchFailed(false);
		inputModel.setRouteCalculationFailed(false);
	}
	
	/**
	 * Sets the current language to <code>language</code>.
	 * Only changes the language if it is available on the system.
	 */
	@Override
	public void languageChangeTriggered(String language) {
		logger.debug("change language to: " + language);
		translationModel.setCurrentLanguage(language);
	}	

	/**
	 * Provokes the view to change to map view.
	 */
	@Override
	public void changeToMapViewTriggered() {
		logger.debug("change to map view");
		ControllerUtil.setMap(mapModel, categoryModel, defaultModelValueClass.getDefaultMap());
		mapModel.setBuilding(defaultModelValueClass.getDefaultBuilding());
	}
	
	/**
	 * Changes the floor being displayed to <code>floor</code>. 
	 */
	@Override
	public void changeFloorTriggered(Map floor) throws NullPointerException {
		if (floor == null) {
			throw new NullPointerException();
		}
		logger.debug("change floor to: " + floor.getName());
		ControllerUtil.setMap(mapModel, categoryModel, floor);
	}
	
	/**
	 * Sets the current categories to <code>enabledCategories</code>.
	 */
	@Override
	public void changeCategoryFilterTriggered(Set<Category> enabledCategories) throws NullPointerException {
		categoryModel.setCurrentCategories(enabledCategories);
		ControllerUtil.refreshPOIs(mapModel, categoryModel);
	}
	
	/**
	 * Sets the map model for this instance. This method is necessary for the
	 * jsf managed property injection mechanism.
	 * 
	 * @param mapModel
	 *            the new map model
	 */
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}
	
	/**
	 * Sets the input model for this instance. This method is necessary for the
	 * jsf managed property injection mechanism.
	 * 
	 * @param inputModel
	 *            the new input model
	 */
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
	
	/**
	 * Sets the translation model for this instance. This method is necessary for the
	 * jsf managed property injection mechanism.
	 * 
	 * @param translationModel
	 *            the new translation model
	 */
	public void setTranslationModel(TranslationModel translationModel) {
		this.translationModel = translationModel;
	}
	
	/**
	 * Sets the default model value class for this instance. This method is necessary for the
	 * jsf managed property injection mechanism.
	 * 
	 * @param defaultModelValueClass
	 *            the new default model value class
	 */
	public void setDefaultModelValueClass(DefaultModelValues defaultModelValueClass) {
		this.defaultModelValueClass = defaultModelValueClass;
	}		
	
	/**
	 * Sets the category model for this instance. This method is necessary for the
	 * jsf managed property injection mechanism.
	 * 
	 * @param categoryModel
	 *            the new category model
	 */
	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	
}
