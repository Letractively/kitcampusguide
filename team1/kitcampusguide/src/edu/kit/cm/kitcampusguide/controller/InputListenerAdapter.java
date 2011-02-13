package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Provides JSF-/view specific methods for reacting to user input.
 * Delegates controller specific tasks to the InputListener.
 *   
 * @author Team1
 *
 */
@ManagedBean (name="inputListenerAdapter")
@SessionScoped
public class InputListenerAdapter {
	
	private Logger logger = Logger.getLogger(InputListenerImpl.class);
	
	private ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	private InputModel inputModel = (InputModel) FacesContext.getCurrentInstance().getApplication()
    		.getELResolver().getValue(elContext, null, "inputModel");
	private TranslationModel translationModel = (TranslationModel) FacesContext.getCurrentInstance().getApplication()
    .getELResolver().getValue(elContext, null, "translationModel");
	private InputListenerImpl inputListener = (InputListenerImpl) FacesContext.getCurrentInstance().getApplication()
	.getELResolver().getValue(elContext, null, "inputListener");
	
	private final String ROUTE_FROM_FIELD_ID = "form:routeFromField";
	private final String ROUTE_TO_FIELD_ID = "form:routeToField";
	
	private POISource poiSource = POISourceImpl.getInstance();
	
	private String routeFromSelection;
	private String routeToSelection;
	
	private String languageSelection;
	
	public InputListenerAdapter() {
		
	}
	
	public String getSearchButtonLabel() {
		if ((inputModel.getRouteFromProposalList() != null 
				|| (inputModel.getRouteFromField() != null && !inputModel.getRouteFromField().isEmpty()) && 
			(inputModel.getRouteToProposalList() != null 
				|| (inputModel.getRouteToField() != null && !inputModel.getRouteToField().isEmpty())))) {
			return translationModel.tr("calculateRoute");
		} else {
			return translationModel.tr("search");
		}
	}
		
	
	/**
	 * Makes the error messages concerning problems with searching the term in the triggering input field 
	 * or calculating the route invisible again.
	 * @param ve Corresponding <code>ValueChangeEvent</code> that is triggered when something is typed into one of the input fields.
	 */
	/*public void refreshInputArea(ValueChangeEvent ve) {	
		String src = ((UIComponent)ve.getSource()).getClientId();
		if (src.equals("routeFromField")) {
			inputModel.setRouteFromSearchFailed(false);
		} else {		
			inputModel.setRouteToSearchFailed(false);
		}
		inputModel.setRouteCalculationFailed(false);
	}*/
		
	public void searchButtonPressed(ActionEvent ae) {
		if (inputModel.getRouteFromProposalList() != null) {
			POI poi = poiSource.getPOIByID(routeFromSelection);
			inputModel.setRouteFromField(poi.getName());
			if (inputModel.getRouteToProposalList() == null) {
				if (inputModel.getRouteToField() == null || inputModel.getRouteToField().equals("") ) {						
					inputListener.searchTriggered(poi);
				} else {
					MapPosition from = new MapPosition(poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
					inputListener.routeTriggered(from,inputModel.getRouteToField().trim());
				}
			} else {
				POI start = poiSource.getPOIByID(routeFromSelection);
				MapPosition from = new MapPosition(start.getPosition().getLatitude(), start.getPosition().getLongitude(), start.getMap());
				inputModel.setRouteFromField(start.getName());
				POI end = poiSource.getPOIByID(routeToSelection);
				MapPosition to = new MapPosition(end.getPosition().getLatitude(), end.getPosition().getLongitude(), end.getMap());
				inputModel.setRouteToField(end.getName());
				inputListener.routeTriggered(from, to);
			}
		} else if (inputModel.getRouteToProposalList() != null) {
			POI poi = poiSource.getPOIByID(routeToSelection);
			inputModel.setRouteToField(poi.getName());
			if (inputModel.getRouteFromField() == null || inputModel.getRouteFromField().equals("") ) {				
				inputListener.searchTriggered(poi);
			} else {
				MapPosition to = new MapPosition(poi.getPosition().getLatitude(), poi.getPosition().getLongitude(), poi.getMap());
				inputListener.routeTriggered(inputModel.getRouteFromField().trim(), to);
			}
		} else {
			String routeFromField = inputModel.getRouteFromField();
			if (routeFromField == null) {
					routeFromField = "";
			}
			routeFromField.trim();
			String routeToField = inputModel.getRouteToField();
			if (routeToField == null) {
				routeToField = "";
			}
			routeToField.trim();
			if (!(routeFromField.equals("")) && !( routeToField.equals(""))) {
				logger.info("calculate route from " + routeFromField + " to " + routeToField);
				inputListener.routeTriggered(routeFromField, routeToField);
			} else if (!routeFromField.equals("")) {
				logger.info("search for " + routeFromField);
				inputListener.searchTriggered(routeFromField, InputFields.ROUTE_FROM);
			} else if (!routeToField.equals("")) {
				logger.info("search for " + routeToField);
				inputListener.searchTriggered(routeToField, InputFields.ROUTE_TO);
			} else {
				logger.info("search button has been pressed but the input fields are empty");
			}
		}
	}
	
	/**
	 * Returns the routeFromProposalList in a format that can be easily displayed by the view
	 * @return Returns the routeFromProposalList in a format that can be easily displayed by the view
	 */
	public List<SelectItem> getRouteFromProposalList() {
		List<POI> routeFromProposalList = inputModel.getRouteFromProposalList();
		List<SelectItem> proposalList = new ArrayList<SelectItem>();
		for (POI poi : routeFromProposalList) {
			SelectItem item = new SelectItem();
			item.setLabel(poi.getName());
			item.setValue(poi.getID());
			proposalList.add(item);	
		}
		return proposalList;
	}
	
	/**
	 * Returns the routeToProposalList in a format that can be easily displayed by the view
	 * @return Returns the routeToProposalList in a format that can be easily displayed by the view
	 */
	public List<SelectItem> getRouteToProposalList() {
		List<POI> routeToProposalList = inputModel.getRouteToProposalList();
		List<SelectItem> proposalList = new ArrayList<SelectItem>();
		for (POI poi : routeToProposalList) {
			SelectItem item = new SelectItem();
			item.setLabel(poi.getName());
			item.setValue(poi.getID());
			proposalList.add(item);	
		}
		return proposalList;
	}
	
	/**
	 * Resets the routeFromProposalList to null, which also means that it won't be displayed anymore.
	 * @param ae The corresponding ActionEvent.
	 */
	public void resetRouteFromProposalList(ActionEvent ae) {
		inputModel.setRouteFromField("");
		inputModel.setRouteFromProposalList(null);
	}
	
	/**
	 * Resets the routeToProposalList to null, which also means that it won't be displayed anymore.
	 * @param ae The corresponding ActionEvent.
	 */
	public void resetRouteToProposalList(ActionEvent ae) {
		inputModel.setRouteToField("");
		inputModel.setRouteToProposalList(null);
	} 
	
	/**
	 * Returns a list with all available languages in a format that can be easily displayed by the view
	 * @return Returns a list with all available languages in a format that can be easily displayed by the view
	 */
	public List<SelectItem> getLanguageProposalList() {
		List<String> languages = translationModel.getLanguages();
		List<SelectItem> proposalList = new ArrayList<SelectItem>();
		for (String language : languages) {
			SelectItem item = new SelectItem();
			item.setLabel(language);
			item.setValue(language);
			proposalList.add(item);	
		}
		return proposalList;
	}
	
	/**
	 * EL-Function that can be used in the JSF-site.
	 * Uses the function "tr()" of the recent TranslationModel to translate labels into the current language.
	 * @param key The key identifying the text to be translated.
	 * @return The translated text.
	 */
	public static String translate(String key) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();	
		TranslationModel translationModel = (TranslationModel) FacesContext.getCurrentInstance().getApplication()
		        .getELResolver().getValue(elContext, null, "translationModel");
		return translationModel.tr(key);
	}
	
	/**
	 * Changes the current language to English.
	 * @param ae The corresponding ActionEvent.
	 */
	public void changeLanguageToEnglish(ActionEvent ae) {
		inputListener.languageChangeTriggered("English");
	}	
	
	/**
	 * Changes the current language to German.
	 * @param ae The corresponding ActionEvent.
	 */
	public void changeLanguageToGerman(ActionEvent ae) {
		inputListener.languageChangeTriggered("Deutsch");
	}
	
	//TODO
	public void languageChangeLinkPressed(ActionEvent ae) {
		inputModel.setLanguageProposalListIsVisible(true);
	}
	
	public void languageChangeCancelled(ActionEvent ae) {
		inputModel.setLanguageProposalListIsVisible(false);
	}
	
	public void languageChangeTriggered(ActionEvent ae) {
		inputListener.languageChangeTriggered(languageSelection);
		inputModel.setLanguageProposalListIsVisible(false);
	}

	/**
	 * Changes to map view.
	 * @param ae The corresponding ActionEvent.
	 */
	public void changeToMapViewTriggered(ActionEvent ae) {
		inputListener.changeToMapViewTriggered();		
	}	
	
	/**
	 * Makes the view display the floor that has been selected through a click on the corresponding link.
	 * @param ae The corresponding ActionEvent.
	 */
	public void changeFloorTriggered(ActionEvent ae) {
		String src = ((UIComponent) ae.getSource()).getClientId();
		String[] splitSrc = src.split(":");
		Integer floorNo = Integer.parseInt(splitSrc[2]);
		inputListener.changeFloorTriggered(floorNo);
	}

	public void setRouteFromSelection(String routeFromSelection) {
		this.routeFromSelection = routeFromSelection;
	}

	public String getRouteFromSelection() {
		return routeFromSelection;
	}

	public void setRouteToSelection(String routeToSelection) {
		this.routeToSelection = routeToSelection;
	}

	public String getRouteToSelection() {
		return routeToSelection;
	}	

	public void setLanguageSelection(String languageSelection) {
		this.languageSelection = languageSelection;
	}

	public String getLanguageSelection() {
		return languageSelection;
	}
	
}
