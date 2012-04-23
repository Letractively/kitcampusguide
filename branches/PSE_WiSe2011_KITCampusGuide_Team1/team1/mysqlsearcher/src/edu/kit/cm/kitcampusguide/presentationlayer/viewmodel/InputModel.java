package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.List;

import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Manages the inputs in all text fields in the view.
 * @author Fred
 *
 */
public class InputModel {
	
	/** Content of the "Route from" field.*/
	private String routeFromField;
	/** Content of the "Route to" field.*/
	private String routeToField;
	/** The "Route From" field's proposal list to be displayed after a search with multiple results. 
	 * Can be <code>null</code>.*/
	private List<POI> routeFromProposalList;
	/** The "Route To" field's proposal list to be displayed after a search with multiple results. 
	 * Can be <code>null</code>.*/
	private List<POI> routeToProposalList;
	/** Describes whether the recent search triggered by the "Route from" field failed or not. */
	private boolean routeFromSearchFailed = false;		
	/** Describes whether the recent search triggered by the "Route to" field failed or not. */
	private boolean routeToSearchFailed = false;
	/** Describes whether the recent route calculation failed or not. */
	private boolean routeCalculationFailed = false;
		
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

	/**
	 * Returns the "Route From" field's proposal list.
	 * @return The "Route From" field's proposal list. Can be <code>null</code>.
	 */
	public List<POI> getRouteFromProposalList() {
		return routeFromProposalList;
	}
	
	/**
	 * Sets the "Route from" field's proposal list.
	 * @param routeFromProposalList The "Route from" field's proposal list. Can be <code>null</code>.
	 */
	public void setRouteFromProposalList(List<POI> routeFromProposalList) {
		this.routeFromProposalList = routeFromProposalList;
	}	
	
	/**
	 * Returns the "Route To" field's proposal list.
	 * @return The "Route To" field's proposal list. Can be <code>null</code>.
	 */
	public List<POI> getRouteToProposalList() {
		return routeToProposalList;
	}
	
	/**
	 * Sets the "Route to" field's proposal list.
	 * @param routeToProposalList The "Route to" field's proposal list. Can be <code>null</code>.
	 */
	public void setRouteToProposalList(List<POI> routeToProposalList) {
		this.routeToProposalList = routeToProposalList;
	}	

	/**
	 * Sets the routeFromSearchFailed-attribute.
	 * @param routeFromSearchFailed <code>true</code> if the recent search triggered by the "Route from" field
	 * failed, else <code>false</code>.
	 */
	public void setRouteFromSearchFailed(boolean routeFromSearchFailed) {
		this.routeFromSearchFailed = routeFromSearchFailed;
	}

	/**
	 * Returns <code>true</code> if the recent search triggered by the "Route from" field failed, else <code>false</code>.
	 * @return <code>true</code> if the recent search triggered by the "Route from" field failed, else <code>false</code>.
	 */
	public boolean isRouteFromSearchFailed() {
		return routeFromSearchFailed;
	}

	/**
	 * Sets the routeToSearchFailed-attribute.
	 * @param routeToSearchFailed <code>true</code> if the recent search triggered by the "Route to" field 
	 * failed, else <code>false</code>.
	 */
	public void setRouteToSearchFailed(boolean routeToSearchFailed) {
		this.routeToSearchFailed = routeToSearchFailed;
	}

	/**
	 * Returns <code>true</code> if the recent search triggered by the "Route to" field failed, else <code>false</code>.
	 * @return <code>true</code> if the recent search triggered by the "Route to" field failed, else <code>false</code>.
	 */
	public boolean isRouteToSearchFailed() {
		return routeToSearchFailed;
	}

	/**
	 * Sets the routeCalculationFailed-attribute.
	 * @param routeCalculationFailed <code>true</code> if the recent route calculation failed,
	 * else <code>false</code>.
	 */	
	public void setRouteCalculationFailed(boolean routeCalculationFailed) {
		this.routeCalculationFailed = routeCalculationFailed;
	}

	/**
	 * Returns <code>true</code> if the recent route calculation failed, else <code>false</code>.
	 * @return <code>true</code> if the recent route calculation failed, else <code>false</code>.
	 */
	public boolean isRouteCalculationFailed() {
		return routeCalculationFailed;
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
