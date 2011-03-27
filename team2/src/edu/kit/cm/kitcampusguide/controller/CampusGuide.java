package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;
import edu.kit.cm.kitcampusguide.model.HeadlineModel;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.model.SidebarModel;

/**
 * Objects of this class are associated with a session of an user of the KITCampusGuide through the
 * ManagedBean technology of JavaServer Faces storing the current state of the application. 
 * 
 * @author Haoqian Zheng
 */
@ManagedBean
@SessionScoped
public class CampusGuide {
	
	/**
	 * Pattern for a double number
	 */
	public static final String NUMBER = "-?\\d+(\\.\\d+)?";
	
	/**
	 * Pattern for String coordinates
	 */
	public static final String COORDINATES = ".*\\(" + NUMBER + "\\, " + NUMBER + "\\)";
	
	/**
	 * The {@link HeadlineModel} of the current session.
	 */
	private HeadlineModel hlm;
	
	/**
	 * The {@link SidebarModel} of the current session.
	 */
	private SidebarModel sbm;
	
	/**
	 * The {@link MapAlgorithms} of the current session.
	 */
	private MapAlgorithms ma;
	
	/**
	 * The Locale of the current session storing the current language.
	 */
	private Locale locale;
	
	/**
	 * The last {@link POI} that the map was centered on during the current session.
	 */
	private POI currentPOI;
	
	/**
	 * The last {@link Route} that has been calculated during the current session.
	 */
	private Route currentRoute;
	
	/**
	 * Creates a new CampusGuide object. 
	 * 
	 * Initializes the attributes of the new object with the default values.
	 */
	public CampusGuide() {
		this.hlm = new HeadlineModel();
		this.sbm = new SidebarModel();
		this.locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		this.setCurrentPOI(null);
		this.currentRoute = null;
		this.ma = new ConcreteMapAlgorithms();
	}
	
	/**
	 * Returns the current {@link SidebarModel}.
	 * @return the current SidebarModel.
	 */
	public SidebarModel getSbm() {
		return sbm;
	}

	/**
	 * Sets the current {@link SidebarModel}.
	 * @param sbm the new SidebarModel.
	 */
	public void setSbm(SidebarModel sbm) {
		this.sbm = sbm;
	}

	/**
	 * Returns the current {@link SidebarModel}.
	 * @return the current SidebarModel.
	 */
	public HeadlineModel getHlm() {
		return hlm;
	}
	
	/**
	 * Sets the current {@link HeadlineModel}.
	 * @param hlm the new HeadlineModel.
	 */
	public void setHlm(HeadlineModel hlm) {
		this.hlm = hlm;
	}
	
	/**
	 * Returns the current Locale.
	 * @return the current Locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the current Locale.
	 * @param locale the new Locale.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * Returns the current {@link POI}.
	 * @return the current POI.
	 */
	public POI getCurrentPOI() {
		return currentPOI;
	}
	
	/**
	 * Sets the current {@link POI}.
	 * @param currentPOI the new POI.
	 */
	public void setCurrentPOI(POI currentPOI) {
		this.currentPOI = currentPOI;
	}
	
	/**
	 * Returns the current {@see Route}.
	 * @return the current Route.
	 */
	public Route getCurrentRoute() {
		return currentRoute;
	}
	
	/**
	 * Sets the current {@link Route}.
	 * @param currentRoute the new Route.
	 */
	public void setCurrentRoute(Route currentRoute) {
		this.currentRoute = currentRoute;
	}
	
	/**
	 * Changes the current locale from German to English or vice versa.
	 */
	public void changeLocale() {
		if (this.locale.getLanguage().equals(Locale.GERMAN.getLanguage())) {
			this.locale = Locale.ENGLISH;
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		} else {
			this.locale = Locale.GERMAN;
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.GERMAN);
		}
	}
	
	/**
	 * This method updates the model components if the user of KITCampusGuide changes the input
	 * of the search field of the headline.
	 * 
	 * It stores the search string and searches for a proper {@link POI} storing it as current POI 
	 * and updates the suggestions concerning the search string.
	 * @param ev the ValueChangeEvent storing the new search string.
	 */
	public void searchChanged(ValueChangeEvent ev) {
		String newSearch = (String) ev.getNewValue();
		if (newSearch != null) {
			this.hlm.setSearch(newSearch);
			this.currentPOI = this.ma.searchPOI(this.hlm.getSearch());
			if (this.currentPOI == null) {
				this.hlm.setSuggestions(this.ma.getSuggestions(this.hlm.getSearch()));
			} else {
				this.hlm.setSuggestions(new ArrayList<POI>());
			}
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	/**
	 * This method updates the model components if the user of KITCampusGuide changes the value
	 * of the from-field of the sidebar.
	 * 
	 * It stores the value of the from-field and searches for a proper {@link POI} storing it in the 
	 * SidebarModel and updates the current route due the new value.
	 * @param ev the ValueChangeEvent storing the new value of the from-field.
	 */
	public void fromChanged(ValueChangeEvent ev) {
		String newFrom = (String) ev.getNewValue();
		POI newPOI = newFrom.matches(COORDINATES) ? generateMarker(newFrom) : this.ma.searchPOI(newFrom);
		this.sbm.setFrom(newPOI);
		this.updateRoute();
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	/**
	 * This method updates the model components if the user of KITCampusGuide changes the value
	 * of the to-field of the sidebar.
	 * 
	 * It stores the value of the to-field and searches for a proper {@link POI} storing it in the 
	 * SidebarModel and updates the current route due the new value.
	 * @param ev the ValueChangeEvent storing the new value of the to-field.
	 */
	public void toChanged(ValueChangeEvent ev) {
		String newTo = (String) ev.getNewValue();
		POI newPOI = newTo.matches(COORDINATES) ? generateMarker(newTo) : this.ma.searchPOI(newTo);
		this.sbm.setTo(newPOI);
		this.updateRoute();
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	/**
	 * Creates a new POI representing a custom marker out of the given text. 
	 * 
	 * @param text a String containing the coordinates of the Marker
	 * @return a POI representing the coordinates specified in text
	 */
	private POI generateMarker(String text) {
		if (!text.matches(COORDINATES)) {
			throw new IllegalArgumentException();
		}
		String[] coordinates = text.substring(text.indexOf('(') + 1, text.indexOf(')')).split("\\, ");
		return new POI(text, -1, null, "", new Double(coordinates[0]), new Double(coordinates[1]));
	}
	
	/**
	 * This method updates the current route due to the currently stored due to the current starting
	 * and destination point.
	 */
	public void updateRoute() {
		if (this.sbm.getFrom() != null && this.sbm.getTo() != null) {
			this.currentRoute = this.ma.calculateRoute(this.sbm.getFrom(), this.sbm.getTo());
		} else {
			this.currentRoute = null;
		}
	}
	
	/**
	 * This method resets the current route. 
	 */
	public void removeRoute() {
		this.currentRoute = null;
	}
}
