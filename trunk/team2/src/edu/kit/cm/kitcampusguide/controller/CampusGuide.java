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
	public static final String COORDINATES = ".*\\("+ NUMBER + "\\, " + NUMBER + "\\)";
	
	private HeadlineModel hlm;
	private SidebarModel sbm;
	private MapAlgorithms ma;
	private Locale locale;
	private POI currentPOI;
	private Route currentRoute;
	
	public CampusGuide() {
		this.hlm = new HeadlineModel();
		this.sbm = new SidebarModel();
		this.locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		this.setCurrentPOI(null);
		this.currentRoute = null;
		this.ma = new ConcreteMapAlgorithms();
	}

	public SidebarModel getSbm() {
		return sbm;
	}

	public void setSbm(SidebarModel sbm) {
		this.sbm = sbm;
	}

	public HeadlineModel getHlm() {
		return hlm;
	}

	public void setHlm(HeadlineModel hlm) {
		this.hlm = hlm;
	}
	
	public String changeLocale() {
		if (this.locale.getLanguage().equals(Locale.GERMAN.getLanguage())) {
			this.locale = Locale.ENGLISH;
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		} else {
			this.locale = Locale.GERMAN;
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.GERMAN);
		}
		return "campuskarte";
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setCurrentPOI(POI currentPOI) {
		this.currentPOI = currentPOI;
	}

	public POI getCurrentPOI() {
		return currentPOI;
	}
	public void setCurrentRoute(Route currentRoute) {
		this.currentRoute = currentRoute;
	}
	
	public Route getCurrentRoute() {
		return currentRoute;
	}

	public void searchChanged(ValueChangeEvent ev) {
		String newSearch = (String) ev.getNewValue();
		if (newSearch != null) {
			this.hlm.setSearch(newSearch);
			this.currentPOI = this.ma.searchPOI(this.hlm.getSearch());
			if (this.currentPOI == null) {
				this.hlm.setSuggestions(this.ma.getSuggestions(this.hlm.getSearch()));
				//System.out.println(this.hlm.getSuggestions().size());
			} else {
				this.hlm.setSuggestions(new ArrayList<POI>());
			}
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void fromChanged(ValueChangeEvent ev) {
		String newFrom = (String) ev.getNewValue();
		POI newPOI = newFrom.matches(COORDINATES) ? generateMarker(newFrom) : this.ma.searchPOI(newFrom);
		this.sbm.setFrom(newPOI);
		this.updateRoute();
		FacesContext.getCurrentInstance().renderResponse();
	}
	
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
	
	public void updateRoute() {
		if (this.sbm.getFrom() != null && this.sbm.getTo() != null) {
			this.currentRoute = this.ma.calculateRoute(this.sbm.getFrom(), this.sbm.getTo());
		} else {
			this.currentRoute = null;
		}
	}
	
	public void removeRoute() {
		this.currentRoute = null;
	}

	public static void main(String[] args) {
		System.out.println(COORDINATES);
		System.out.println("Positionsmarker (8.41313, 49.01113)".matches(COORDINATES));
	}
	
}
