package edu.kit.cm.kitcampusguide.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.kit.cm.kitcampusguide.service.IPoiService;
import edu.kit.cm.kitcampusguide.service.poi.type.Select;

public class PoiOverviewController extends AbstractController {

	@Autowired
	private IPoiService poiService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Select selection = new Select();
		selection.setFindByNamesLike(new String[] { "%" });

		return new ModelAndView("poiOverview", "pois", this.poiService.select(selection));
	}

}
