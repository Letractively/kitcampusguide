package edu.kit.cm.kitcampusguide.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.openfaces.component.panel.FoldingPanel;

import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;
import edu.kit.cm.kitcampusguide.model.HeadlineModel;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.model.SidebarModel;

@ManagedBean
@SessionScoped
public class CampusGuide {
	
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
	
	public void changeLocale() {
		if (this.locale.getLanguage().equals(Locale.GERMAN.getLanguage())) {
			this.locale = Locale.ENGLISH;
		} else {
			this.locale = Locale.GERMAN;
		}
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
				System.out.println(this.hlm.getSuggestions().size());
			} else {
				this.hlm.setSuggestions(new ArrayList<POI>());
			}
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void fromChanged(ValueChangeEvent ev) {
		String newFrom = (String) ev.getNewValue();
		this.sbm.setFrom(this.ma.searchPOI(newFrom));
		this.updateRoute();
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void toChanged(ValueChangeEvent ev) {
		String newTo = (String) ev.getNewValue();
		this.sbm.setTo(this.ma.searchPOI(newTo));
		this.updateRoute();
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void updateRoute() {
		if (this.sbm.getFrom() != null && this.sbm.getTo() != null) {
			this.currentRoute = this.ma.calculateRoute(this.sbm.getFrom(), 
									this.sbm.getTo());
		} else {
			this.currentRoute = null;
		}
	}
	
	public void removeRoute() {
		this.currentRoute = null;
	}

}
