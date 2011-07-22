package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.kit.cm.kitcampusguide.ws.poi.PoiService;
import edu.kit.cm.kitcampusguide.ws.poi.PoiServiceClient;
import edu.kit.cm.kitcampusguide.ws.poi.type.Names;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectRequest;

public class PoiOverviewController extends AbstractController {

	@Autowired
	private PoiService poiService;

	public PoiOverviewController() {
		// autowiring constructor
	}

	public PoiOverviewController(PoiService poiService) {
		this.poiService = poiService;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PoiService serviceClient = getServiceClient();

		SelectRequest selection = new SelectRequest();
		Names names = new Names();
		names.setName(new ArrayList<String>());
		names.getName().add("%");
		selection.setFindByNamesLike(names);

		return new ModelAndView("poiOverview", "pois", serviceClient.select(selection).getPoi());
	}

	private PoiService getServiceClient() {
		if (this.poiService != null) {
			return this.poiService;
		} else {
			return new PoiServiceClient().getPoiServiceSOAP();
		}
	}

	public void setPoiService(PoiService poiService) {
		this.poiService = poiService;
	}
}
