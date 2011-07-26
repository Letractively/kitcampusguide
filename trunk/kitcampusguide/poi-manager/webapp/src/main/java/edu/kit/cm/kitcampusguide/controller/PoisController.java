package edu.kit.cm.kitcampusguide.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.kit.cm.kitcampusguide.controller.form.UpdatePoiForm;
import edu.kit.cm.kitcampusguide.ws.poi.PoiService;
import edu.kit.cm.kitcampusguide.ws.poi.PoiServiceClient;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.Names;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectRequest;

@Controller
public class PoisController {

	private static final Logger log = Logger.getLogger(UpdatePoiForm.class);

	@Autowired
	private PoiService poiService;

	public PoisController() {
		// autowiring constructor
	}

	public PoisController(PoiService poiService) {
		this.poiService = poiService;
	}

	@RequestMapping("pois.htm")
	public String setUpPoiList(Model model) throws Exception {
		PoiService serviceClient = getServiceClient();

		SelectRequest selection = new SelectRequest();
		Names names = new Names();
		names.setName(new ArrayList<String>());
		names.getName().add("%");
		selection.setFindByNamesLike(names);
		model.addAttribute("pois", serviceClient.select(selection).getPoi());

		return "poi/list";
	}

	@RequestMapping(value = "poi/{uid}/delete.htm")
	public ModelAndView deletePoi(@PathVariable Integer uid) {
		ModelAndView mv = new ModelAndView("redirect:/pois.htm");

		try {

			tryToDeletePoiWithId(uid, mv);
		} catch (DeleteFault_Exception ex) {

			handleErrorWhileDeletingPoi(mv, ex);
		}

		return mv;
	}

	private void handleErrorWhileDeletingPoi(ModelAndView mv, DeleteFault_Exception ex) {
		log.error("Poi could not be deleted", ex);
		mv.addObject("faultMessage", "error.deletingPoi");
	}

	private void tryToDeletePoiWithId(Integer uid, ModelAndView mv) throws DeleteFault_Exception {
		DeleteRequest deleteRequest = new DeleteRequest();
		deleteRequest.setId(uid);
		getServiceClient().delete(deleteRequest);
		mv.addObject("successMessage", "success.deletingPoi");
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
