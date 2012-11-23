package edu.kit.cm.kitcampusguide.model;

/**
 * This class contains the description of the infobox which contains the information about a POI.
 * 
 * @author Kateryna Yurchenko
 * 
 */

public class InfoboxModel {

	/* Is true if the further information is shown, otherwise false. */
	private boolean extendedView;
	
	/* The POI which is corresponding to the infobox. */
	private Poi dedicatedPOI;
	
	/**
	 * Creates a new infobox which contains the information about a POI.
	 */
	public InfoboxModel() {
		this.extendedView = false;
		this.dedicatedPOI = null;
	}
	
	/**
	 * This method returns true if the further information about a POI is shown,
	 * otherwise false.
	 * 
	 * @return true if the further information is shown, otherwise false.
	 */
	public boolean getExtendedView() {
		return this.extendedView;
	}
		
	/**
	 * This method changes the visibility of the further information about a POI.
	 * 
	 * @param extendedView the new visibility of the further information about a POI.
	 */
	public void setExtendedView(boolean extendedView) {
		this.extendedView = extendedView; 
	}
	
	/**
	 * This method returns a POI which is corresponding to the infobox.
	 * 
	 * @return a POI which is corresponding to the infobox.
	 */
	public Poi getDedicatedPOI() {
		return this.dedicatedPOI;
	}
		
	/**
	 * This method sets a POI which is corresponding to the infobox.
	 * 
	 * @param dedicatedPOI a POI which must be set as a POI which is corresponding to the infobox.
	 */
	public void setDedicatedPOI(Poi dedicatedPOI) {
		this.dedicatedPOI = dedicatedPOI; 
	}	
}
