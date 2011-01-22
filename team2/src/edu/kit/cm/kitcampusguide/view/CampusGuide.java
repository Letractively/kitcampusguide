package edu.kit.cm.kitcampusguide.view;

import javax.faces.bean.ManagedBean;

import edu.kit.cm.kitcampusguide.model.HeadlineModel;

@ManagedBean
public class CampusGuide {
	
	HeadlineModel hlm;
	
	public CampusGuide() {
		this.hlm = new HeadlineModel();
		this.hlm.setSearch("test");
	}

	public HeadlineModel getHlm() {
		return hlm;
	}

	public void setHlm(HeadlineModel hlm) {
		this.hlm = hlm;
	}
}
