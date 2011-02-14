package edu.kit.cm.kitcampusguide.view;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import edu.kit.cm.kitcampusguide.ConstantData;
import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.mapAlgorithms.ConcreteMapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.Dijkstra;
import edu.kit.cm.kitcampusguide.mapAlgorithms.MapAlgorithms;
import edu.kit.cm.kitcampusguide.mapAlgorithms.RouteCalculator;
import edu.kit.cm.kitcampusguide.model.HeadlineModel;
import edu.kit.cm.kitcampusguide.model.InfoboxModel;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.model.Settings;
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
	
	public void submitSearch() {
		List<POI> pl = new ConcretePOILoader().getPOIsByName(this.hlm.getSearch());
		if (!pl.isEmpty()) {			
			this.currentPOI = pl.get(0);
		}
	}
	
	public void searchChanged(ValueChangeEvent ev) {
		String newSearch = (String) ev.getNewValue();
		if (newSearch != null) {
			this.hlm.setSearch(newSearch);
			this.currentPOI = this.ma.searchPOI(this.hlm.getSearch());
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void fromChanged(ValueChangeEvent ev) {
		String newFrom = (String) ev.getNewValue();
		this.sbm.setFrom(this.ma.searchPOI(newFrom));
		if (this.sbm.getTo() != null) {
//			this.currentRoute = this.ma.calculateRoute(this.sbm.getFrom(), 
//									this.sbm.getTo());
			List<Point> route = new ArrayList<Point>();
			route.add(this.sbm.getFrom());
			route.add(this.sbm.getTo());
			this.currentRoute = new Route(route);
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
	
	public void toChanged(ValueChangeEvent ev) {
		String newTo = (String) ev.getNewValue();
		this.sbm.setTo(this.ma.searchPOI(newTo));
		if (this.sbm.getFrom() != null) {
//			this.currentRoute = this.ma.calculateRoute(this.sbm.getFrom(), 
//									this.sbm.getTo());
			List<Point> route = new ArrayList<Point>();
			route.add(this.sbm.getFrom());
			route.add(this.sbm.getTo());
			this.currentRoute = new Route(route);
		}
		FacesContext.getCurrentInstance().renderResponse();
	}

}
