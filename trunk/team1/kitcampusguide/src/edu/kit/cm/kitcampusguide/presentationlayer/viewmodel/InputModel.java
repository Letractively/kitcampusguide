package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * Manages the inputs in all text fields in the view.
 * @author Fred
 *
 */
@ManagedBean (name="inputModel")
@SessionScoped
public class InputModel {
	/** Content of the "Route from" field.*/
	private String routeFromField;
	/** Content of the "Route to" field.*/
	private String routeToField;
	/** The proposal list to be displayed after a search. Can be <code>null</code>.*/
	private List<SelectItem> routeFromProposalList = new ArrayList<SelectItem>();
	private String routeFromSelection;
	private boolean routeFromProposalListIsVisible = false;
	private List<SelectItem> routeToProposalList = new ArrayList<SelectItem>();
	private String routeToSelection;
	private boolean routeToProposalListIsVisible = false;
	@ManagedProperty(value="")
	private String routeFromInformation;
	private boolean routeFromInformationIsVisible = false;		
	@ManagedProperty(value="")
	private String routeToInformation;
	private boolean routeToInformationIsVisible = false;
	
	/** The export link to be displayed. Can be <code>null</code>.*/
	private String exportLink;
	/** The html embedding code. Can be <code>null</code>.*/
	private String embeddingCode;
		
	public InputModel() {
		
	}
	
	/**
	 * Returns the actual appropriate searchButtonLabel
	 * @return
	 */
	public String getSearchButtonLabel() {
		if (routeFromField != null) {
			routeFromField = routeFromField.trim();
		}	
		if (routeToField != null) {
			routeToField = routeToField.trim();
		}
		if ((routeFromProposalListIsVisible || (routeFromField != null && !routeFromField.equals(""))) 
				&& (routeToProposalListIsVisible || (routeToField != null && !routeToField.equals("")))) {
			return "calculateRoute";			
		} else {
			return "search";
		}		
	}
		
	/**
	 * Returns the content of the "Route from" text field.
	 * @return The content of the "Route from" text field.
	 */
	public String getRouteFromField() {
		return routeFromField;
	}

	/**
	 * Sets the content of the "Route from" text field.
	 * @param routeFromField The content of the "Route from" text field. If <code>null</code>, no changes are made.
	 */
	public void setRouteFromField(String routeFromField) {
		if (routeFromField != null) {
			this.routeFromField = routeFromField;			
		}
	}

	/**
	 * Returns the content of the "Route to" text field.
	 * @return The content of the "Route to" text field.
	 */
	public String getRouteToField() {
		return routeToField;
	}

	/**
	 * Sets the content of the "Route to" text field.
	 * @param routeToField The content of the "Route to" text field. If <code>null</code>, no changes are made.
	 */
	public void setRouteToField(String routeToField) {
		if (routeToField != null) {
			this.routeToField = routeToField;
		}
	}

	public List<SelectItem> getRouteFromProposalList() {
		return routeFromProposalList;
	}
	
	public void setRouteFromProposalList(List<SelectItem> routeFromProposalList) {
		this.routeFromProposalList = routeFromProposalList;
	}	
	
	public void setRouteFromSelection(String routeFromSelection) {
		this.routeFromSelection = routeFromSelection;
	}

	public String getRouteFromSelection() {
		return routeFromSelection;
	}

	public void setRouteFromProposalListIsVisible(
			boolean routeFromProposalListIsVisible) {
		this.routeFromProposalListIsVisible = routeFromProposalListIsVisible;
	}

	public boolean isRouteFromProposalListIsVisible() {
		return routeFromProposalListIsVisible;
	}

	public List<SelectItem> getRouteToProposalList() {
		return routeToProposalList;
	}
	
	public void setRouteToProposalList(List<SelectItem> routeToProposalList) {
		this.routeToProposalList = routeToProposalList;
	}	

	public void setRouteToSelection(String routeToSelection) {
		this.routeToSelection = routeToSelection;
	}

	public String getRouteToSelection() {
		return routeToSelection;
	}

	public void setRouteToProposalListIsVisible(boolean routeToProposalListIsVisible) {
		this.routeToProposalListIsVisible = routeToProposalListIsVisible;
	}

	public boolean isRouteToProposalListIsVisible() {
		return routeToProposalListIsVisible;
	}

	public void setRouteFromInformation(String routeFromInformation) {
		this.routeFromInformation = routeFromInformation;
	}

	public String getRouteFromInformation() {
		return routeFromInformation;
	}

	public void setRouteFromInformationIsVisible(
			boolean routeFromInformationIsVisible) {
		this.routeFromInformationIsVisible = routeFromInformationIsVisible;
	}

	public boolean isRouteFromInformationIsVisible() {
		return routeFromInformationIsVisible;
	}

	public void setRouteToInformation(String routeToInformation) {
		this.routeToInformation = routeToInformation;
	}

	public String getRouteToInformation() {
		return routeToInformation;
	}

	public void setRouteToInformationIsVisible(boolean routeToInformationIsVisible) {
		this.routeToInformationIsVisible = routeToInformationIsVisible;
	}

	public boolean isRouteToInformationIsVisible() {
		return routeToInformationIsVisible;
	}

	/**
	 * Returns the export link string.
	 * @return The export link. Can be <code>null</code>.
	 */
	public String getExportLink() {
		return exportLink;
	}

	/**
	 * Sets the export link.
	 * @param exportLink The export link. Can be <code>null</code>.
	 */
	public void setExportLink(String exportLink) {
		this.exportLink = exportLink;
	}

	/**
	 * Gets the embedding code.
	 * @return The embedding code. Can be <code>null</code>. 
	 */
	public String getEmbeddingCode() {
		return embeddingCode;
	}

	/**
	 * Sets the embedding code.
	 * @param embeddingCode The embedding code. Can be <code>null</code>.
	 */
	public void setEmbeddingCode(String embeddingCode) {
		this.embeddingCode = embeddingCode;
	}
}
