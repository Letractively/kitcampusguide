package edu.kit.cm.kitcampusguide.view;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import edu.kit.cm.kitcampusguide.model.HeadlineModel;
import edu.kit.cm.kitcampusguide.model.InfoboxModel;
import edu.kit.cm.kitcampusguide.model.Settings;
import edu.kit.cm.kitcampusguide.model.SidebarModel;

@ManagedBean
@SessionScoped
public class CampusGuide {
	
	private HeadlineModel hlm;
	private SidebarModel sbm;
	private InfoboxModel ibm;
	private Settings settings;
	
	public CampusGuide() {
		this.hlm = new HeadlineModel();
		this.hlm.setSearch("test");
		this.sbm = new SidebarModel();
		this.ibm = new InfoboxModel();
		this.settings = null;
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
	
}
