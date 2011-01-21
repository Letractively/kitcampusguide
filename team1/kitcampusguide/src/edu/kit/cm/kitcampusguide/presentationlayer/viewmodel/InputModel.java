package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import edu.kit.cm.kitcampusguide.standardtypes.*;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlSelectOneMenu;
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
	@ManagedProperty (value ="")
	private String routeFromField;
	/** Content of the "Route to" field.*/
	@ManagedProperty (value ="")
	private String routeToField;
	/** The proposal list to be displayed after a search. Can be <code>null</code>.*/
	private List<SelectItem> routeFromProposalList = new ArrayList<SelectItem>();
	private List<SelectItem> routeToProposalList = new ArrayList<SelectItem>();

	/** The export link to be displayed. Can be <code>null</code>.*/
	private String exportLink;
	/** The html embedding code. Can be <code>null</code>.*/
	private String embeddingCode;
		
	public InputModel() {
		
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
	
	public List<SelectItem> getRouteToProposalList() {
		return routeToProposalList;
	}
	
	public void setRouteToProposalList(List<SelectItem> routeToProposalList) {
		this.routeToProposalList = routeToProposalList;
	}	

	/**
	 * Returns the proposal list.
	 * @return The proposal list. Can be <code>null</code>.
	 */
	/* List<POI> getProposalList() {
		return proposalList;
	}*/

	/**
	 * Sets the content of the proposal list.
	 * @param proposalList The content of the proposal list. Can be <code>null</code>.
	 */
	/*public void setProposalList(List<POI> proposalList) {
		this.proposalList = proposalList;					
	}*/

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
