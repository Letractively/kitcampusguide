package edu.kit.cm.kitcampusguide.presentationlayer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.component.UIOutput;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISource;
import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.translationmodel.TranslationModel;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Provides JSF-/view specific methods for reacting to user input.
 * Delegates controller specific tasks to the InputListener.
 *   
 * @author Team1
 *
 */
public class InputListenerAdapter {
	
	private InputModel inputModel;
	private TranslationModel translationModel;
	private InputListener inputListener;		
	private POISource poiSource = POISourceImpl.getInstance();
	
	//Holds the id of the POI that has been selected in the routeFromProposalList.
	private String routeFromSelection;
	//Holds the id of the POI that has been selected in the routeToProposalList.
	private String routeToSelection;	
	//Holds the language that has been selected in the languageProposalList.
	private String languageSelection;
	//Determines whether the languageProposalList shall be displayed in the view or not.
	private boolean languageProposalListIsVisible = false;	
	
	/**
	 * Default constructor.
	 */
	public InputListenerAdapter() {
		
	}
	
	/**
	 * Returns the actual appropriate search button label.
	 * @return Returns the actual appropriate search button label.
	 */
	public String getSearchButtonLabel() {
		if ((inputModel.getRouteFromProposalList() != null 
				|| (inputModel.getRouteFromField() != null && !inputModel.getRouteFromField().isEmpty())) && 
			(inputModel.getRouteToProposalList() != null 
				|| (inputModel.getRouteToField() != null && !inputModel.getRouteToField().isEmpty())))  {
			return translationModel.tr("calculateRoute");
		} else {
			return translationModel.tr("search");
		}
	}
		
	/**
	 * Triggers a search or route calculation after the search button has been pressed.
	 * Therefor processes the input in the input fields and/or the selections made with the proposal lists
	 * and delegates the resulting tasks to the InputListener.
	 * @param ae The corresponding ActionEvent.
	 */
	public void searchButtonPressed(ActionEvent ae) {
		if (inputModel.getRouteFromProposalList() != null) {
			POI routeFromPoi = poiSource.getPOIByID(routeFromSelection);
			inputModel.setRouteFromField(routeFromPoi.getName());
			if (inputModel.getRouteToProposalList() == null) {
				if (inputModel.getRouteToField() == null || inputModel.getRouteToField().isEmpty()) {						
					inputListener.searchTriggered(routeFromPoi);
				} else {
					MapPosition from = new MapPosition(routeFromPoi.getPosition().getLatitude(), routeFromPoi.getPosition().getLongitude(), routeFromPoi.getMap());
					inputListener.routeTriggered(from, inputModel.getRouteToField().trim());
				}
			} else {
				MapPosition from = new MapPosition(routeFromPoi.getPosition().getLatitude(), routeFromPoi.getPosition().getLongitude(), routeFromPoi.getMap());
				POI routeToPoi = poiSource.getPOIByID(routeToSelection);
				inputModel.setRouteToField(routeToPoi.getName());
				MapPosition to = new MapPosition(routeToPoi.getPosition().getLatitude(), routeToPoi.getPosition().getLongitude(), routeToPoi.getMap());
				inputListener.routeTriggered(from, to);
			}
		} else if (inputModel.getRouteToProposalList() != null) {
			POI routeToPoi = poiSource.getPOIByID(routeToSelection);
			inputModel.setRouteToField(routeToPoi.getName());
			if (inputModel.getRouteFromField() == null || inputModel.getRouteFromField().isEmpty()) {				
				inputListener.searchTriggered(routeToPoi);
			} else {
				MapPosition to = new MapPosition(routeToPoi.getPosition().getLatitude(), routeToPoi.getPosition().getLongitude(), routeToPoi.getMap());
				inputListener.routeTriggered(inputModel.getRouteFromField().trim(), to);
			}
		} else if (inputModel.getRouteFromField() != null && !inputModel.getRouteFromField().isEmpty()) {
			if (inputModel.getRouteToField() != null && !inputModel.getRouteToField().isEmpty()) {
				inputListener.routeTriggered(inputModel.getRouteFromField().trim(), inputModel.getRouteToField().trim());
			} else {
				inputListener.searchTriggered(inputModel.getRouteFromField().trim(), InputField.ROUTE_FROM);
			}
		} else if (inputModel.getRouteToField() != null && !inputModel.getRouteToField().isEmpty()) {
			inputListener.searchTriggered(inputModel.getRouteToField().trim(), InputField.ROUTE_TO);
		}			
	}
	
	/**
	 * Returns the InputModels routeFromProposalList in a format that can be easily displayed by the view.
	 * @return Returns the InputModels routeFromProposalList in a format that can be easily displayed by the view.
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
	 * Returns the InputModels routeToProposalList in a format that can be easily displayed by the view.
	 * @return Returns the InputModels routeToProposalList in a format that can be easily displayed by the view.
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
	 * Returns a list with all available languages in a format that can be easily displayed by the view.
	 * @return Returns a list with all available languages in a format that can be easily displayed by the view.
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
	
	/**
	 * Makes the view display the languageProposalList.
	 * @param ae The corresponding ActionEvent.
	 */
	public void languageChangeLinkPressed(ActionEvent ae) {
		setLanguageProposalListIsVisible(true);
	}
	
	/**
	 * Unmakes the view display the languageProposalList.
	 * @param ae The corresponding ActionEvent.
	 */
	public void languageChangeCancelled(ActionEvent ae) {
		setLanguageProposalListIsVisible(false);
	}
	
	/**
	 * Changes the current language to the one that has been selected in the languageProposalList.
	 * @param ae The corresponding ActionEvent.
	 */
	public void languageChangeTriggered(ActionEvent ae) {
		inputListener.languageChangeTriggered(languageSelection);
		setLanguageProposalListIsVisible(false);
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
		Integer floorID =  (Integer) ((UIOutput) ae.getComponent().getChildren().get(0)).getValue();
		inputListener.changeFloorTriggered(Map.getMapByID(floorID));
	}
	
	/**
	 * Sets the id of the POI that has been selected in the routeFromProposalList.
	 * @param routeFromSelection The id of the POI that has been selected in the routeFromProposalList.
	 */
	public void setRouteFromSelection(String routeFromSelection) {
		this.routeFromSelection = routeFromSelection;
	}
	
	/**
	 * Returns the id of the POI that has been selected in the routeFromProposalList.
	 * @return The id of the POI that has been selected in the routeFromProposalList.
	 */
	public String getRouteFromSelection() {
		return routeFromSelection;
	}

	/**
	 * Sets the id of the POI that has been selected in the routeToProposalList.
	 * @param routeToSelection The id of the POI that has been selected in the routeToProposalList.
	 */
	public void setRouteToSelection(String routeToSelection) {
		this.routeToSelection = routeToSelection;
	}

	/**
	 * Returns the id of the POI that has been selected in the routeToProposalList.
	 * @return The id of the POI that has been selected in the routeToProposalList.
	 */
	public String getRouteToSelection() {
		return routeToSelection;
	}	

	/**
	 * Sets the language that has been selected in the languageProposalList.
	 * @param languageSelection The language that has been selected in the languageProposalList.
	 */
	public void setLanguageSelection(String languageSelection) {
		this.languageSelection = languageSelection;
	}

	/**
	 * Returns the language that has been selected in the languageProposalList.
	 * @return The language that has been selected in the languageProposalList.
	 */
	public String getLanguageSelection() {
		return languageSelection;
	}
	
	/**
	 * Sets the languageProposalListIsVisible-attribute.
	 * @param languageProposalListIsVisible <code>true</code> if the languageProposalList shall be displayed
	 * in the view, else <code>false</code>.
	 */
	public void setLanguageProposalListIsVisible(
			boolean languageProposalListIsVisible) {
		this.languageProposalListIsVisible = languageProposalListIsVisible;
	}

	/**
	 * Returns <code>true</code> if the languageProposalList shall be displayed in the view, 
	 * else <code>false</code>.
	 * @return <code>true</code> if the languageProposalList shall be displayed in the view, 
	 * else <code>false</code>.
	 */
	public boolean isLanguageProposalListIsVisible() {
		return languageProposalListIsVisible;
	}
	
	/**
	 * Sets the InputModel-property.
	 * @param inputModel Not null.
	 */
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
	
	/**tionModel-property.
	 * @param translationModel Not null.
	 */
	public void setTranslationModel(TranslationModel translationModel) {
		this.translationModel = translationModel;
	}
	
	/**
	 * Sets the InputListener-property.
	 * @param inputListener Not null.
	 */
	public void setInputListener(InputListener inputListener) {
		this.inputListener = inputListener;
	}	
}
