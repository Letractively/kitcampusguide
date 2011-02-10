package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.kit.cm.kitcampusguide.standardtypes.POI;

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
	private List<POI> routeFromProposalList;
	private List<POI> routeToProposalList;
	private boolean routeFromSearchFailed = false;		
	private boolean routeToSearchFailed = false;
	private boolean routeCalculationFailed = false;
	
	
	private boolean languageProposalListIsVisible = false;	
		
	/** The export link to be displayed. Can be <code>null</code>.*/
	private String exportLink;
	/** The html embedding code. Can be <code>null</code>.*/
	private String embeddingCode;
		
	/**
	 * Constructs a new input model.
	 */
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

	
	public List<POI> getRouteFromProposalList() {
		return routeFromProposalList;
	}
	
	public void setRouteFromProposalList(List<POI> routeFromProposalList) {
		this.routeFromProposalList = routeFromProposalList;
	}	
	
	public List<POI> getRouteToProposalList() {
		return routeToProposalList;
	}
	
	public void setRouteToProposalList(List<POI> routeToProposalList) {
		this.routeToProposalList = routeToProposalList;
	}	

	public void setRouteFromSearchFailed(boolean routeFromSearchFailed) {
		this.routeFromSearchFailed = routeFromSearchFailed;
	}

	public boolean isRouteFromSearchFailed() {
		return routeFromSearchFailed;
	}

	public void setRouteToSearchFailed(boolean routeToSearchFailed) {
		this.routeToSearchFailed = routeToSearchFailed;
	}

	public boolean isRouteToSearchFailed() {
		return routeToSearchFailed;
	}

	public void setRouteCalculationFailed(boolean routeCalculationFailed) {
		this.routeCalculationFailed = routeCalculationFailed;
	}

	public boolean isRouteCalculationFailed() {
		return routeCalculationFailed;
	}

	public void setLanguageProposalListIsVisible(
			boolean languageProposalListIsVisible) {
		this.languageProposalListIsVisible = languageProposalListIsVisible;
	}

	public boolean isLanguageProposalListIsVisible() {
		return languageProposalListIsVisible;
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
