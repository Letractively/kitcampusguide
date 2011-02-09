package edu.kit.cm.kitcampusguide.controller;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.InputModel;

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
	private InputListener inputListener = (InputListener) FacesContext.getCurrentInstance().getApplication()
	.getELResolver().getValue(elContext, null, "inputListener");
	
	private final String ROUTE_FROM_FIELD_ID = "form:routeFromField";
	private final String ROUTE_TO_FIELD_ID = "form:routeToField";
	
	public InputListenerAdapter() {
		
	}
	
	/**
	 * Determines whether the search button shall be labeled with "Search" or with "Route" (or rather the corresponding translations).
	 * @return Returns <code>true</code> if the button shall be labeled with "Route" and false else.
	 */
	public boolean isCalculateRoute() {
		UIInput routeFromFieldComponent = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(ROUTE_FROM_FIELD_ID);
		String routeFromField = (String) routeFromFieldComponent.getValue();
		if (routeFromField == null) {
			routeFromField = "";
			routeFromFieldComponent.setValue("");
			//this workaround is done because the context parameter "INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL" is set true at the moment,
			//for which reason the ValueChangedEvent wasn't triggered exactly else
		}
		routeFromField = routeFromField.trim();
		UIInput routeToFieldComponent = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(ROUTE_TO_FIELD_ID);
		String routeToField = (String) routeToFieldComponent.getValue();
		if (routeToField == null) {
			routeToField = "";
			routeToFieldComponent.setValue("");
			//see above
		}
		routeToField = routeToField.trim();
		if ((inputModel.isRouteFromProposalListIsVisible() || !routeFromField.equals("")) 
				&& (inputModel.isRouteToProposalListIsVisible() || !routeToField.equals(""))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Makes the error messages concerning problems with searching the term in the triggering input field 
	 * or calculating the route invisible again.
	 * @param ve Corresponding <code>ValueChangeEvent</code> that is triggered when something is typed into one of the input fields.
	 */
	public void refreshInputArea(ValueChangeEvent ve) {	
		String src = ((UIComponent)ve.getSource()).getClientId();
		if (src.equals("routeFromField")) {
			inputModel.setRouteFromSearchFailed(false);
		} else {		
			inputModel.setRouteToSearchFailed(false);
		}
		inputModel.setRouteCalculationFailed(false);
	}
	
	public void searchButtonPressed(ActionEvent ae) {
		if (inputModel.isRouteFromProposalListIsVisible()) {
			inputModel.setRouteFromField(inputModel.getRouteFromSelection());
		} 
		String routeFromField = inputModel.getRouteFromField();
		if (routeFromField == null) {
				routeFromField = "";
		}
		routeFromField.trim();
		if (inputModel.isRouteToProposalListIsVisible()) {
			inputModel.setRouteToField(inputModel.getRouteToSelection());
		} 
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
	
	public void resetRouteFromProposalList(ActionEvent ae) {
		inputModel.setRouteFromField("");
		inputModel.setRouteFromProposalListIsVisible(false);
	}
	
	public void resetRouteToProposalList(ActionEvent ae) {
		inputModel.setRouteToField("");
		inputModel.setRouteToProposalListIsVisible(false);
	} 
	
	public void changeLanguageToEnglish(ActionEvent ae) {
		logger.info("change language to english");
		inputListener.languageChangeTriggered("English");
	}
	
	public void changeLanguageToGerman(ActionEvent ae) {
		logger.info("change language to german");
		inputListener.languageChangeTriggered("Deutsch");
	}
	
	public void languageChangeLinkPressed(ActionEvent ae) {
		inputModel.setLanguageProposalListIsVisible(true);
	}
	
	public void languageChangeCancelled(ActionEvent ae) {
		inputModel.setLanguageProposalListIsVisible(false);
	}
		
	public void languageChangeTriggered(ActionEvent ae) {
		String language = inputModel.getLanguageSelection();
		logger.info("change language to " + language);
		inputListener.languageChangeTriggered(language);
		inputModel.setLanguageProposalListIsVisible(false);
	}

	public void changeToMapViewTriggered(ActionEvent ae) {
		inputListener.changeToMapViewTriggered();		
	}	
	
	public void changeFloorTriggered(ActionEvent ae) {
		String src = ((UIComponent) ae.getSource()).getClientId();
		String[] flooridx = src.split(":");
		Integer floorNo = Integer.parseInt(flooridx[2]);
		inputListener.changeFloorTriggered(floorNo);
	}
	
}
