package edu.kit.cm.kitcampusguide.view;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.model.HeadlineModel;
import edu.kit.cm.kitcampusguide.model.InfoboxModel;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.model.Settings;
import edu.kit.cm.kitcampusguide.model.SidebarModel;

@ManagedBean
@SessionScoped
public class CampusGuide {
	
	private HeadlineModel hlm;
	private SidebarModel sbm;
	private InfoboxModel ibm;
	private Settings settings;
	private Locale locale;
	private POI currentPOI;
	
	public CampusGuide() {
		this.hlm = new HeadlineModel();
		this.sbm = new SidebarModel();
		this.ibm = new InfoboxModel();
		this.settings = null;
		this.locale = Locale.GERMAN;
		this.setCurrentPOI(null);
	}

	public SidebarModel getSbm() {
		return sbm;
	}

	public void setSbm(SidebarModel sbm) {
		this.sbm = sbm;
	}

	public InfoboxModel getIbm() {
		return ibm;
	}

	public void setIbm(InfoboxModel ibm) {
		this.ibm = ibm;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
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
	
	public void submitSearch() {
		List<POI> pl = new ConcretePOILoader().getPOIsByName(this.hlm.getSearch());
		if (!pl.isEmpty()) {			
			this.currentPOI = pl.get(0);
		}
	}
	
	public void searchChanged(ValueChangeEvent ev) {
		String newSearch = (String) ev.getNewValue();
		System.out.println(newSearch);
		if (newSearch != null) {
			this.hlm.setSearch(newSearch);
			this.submitSearch();
		}
		FacesContext.getCurrentInstance().renderResponse();
	}
}
