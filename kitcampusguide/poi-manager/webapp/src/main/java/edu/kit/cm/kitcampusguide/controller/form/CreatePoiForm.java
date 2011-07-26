package edu.kit.cm.kitcampusguide.controller.form;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kit.cm.kitcampusguide.validator.PoiValidator;
import edu.kit.cm.kitcampusguide.ws.poi.PoiService;
import edu.kit.cm.kitcampusguide.ws.poi.PoiServiceClient;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.Poi;

@Controller
@RequestMapping(value = "poi/create.htm")
public class CreatePoiForm {

	private static final Logger log = Logger.getLogger(CreatePoiForm.class);

	@Autowired
	private PoiService poiService;

	public CreatePoiForm() {
		// autowiring constructor
	}

	public CreatePoiForm(PoiService poiService) {
		this.poiService = poiService;
	}

	@RequestMapping(method = RequestMethod.GET)
	protected String setUpForm(Model model) {

		Poi poi = new Poi();
		model.addAttribute(poi);

		return "poi/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String onSubmit(@ModelAttribute Poi poi, BindingResult result, Model model) {
		String viewName;
		new PoiValidator().validate(poi, result);
		if (result.hasErrors()) {

			viewName = "poi/form";
		} else {
			try {

				tryToCreatePoi(poi, model);
				viewName = "redirect:/pois.htm";
			} catch (CreateFault_Exception e) {

				handleCreateException(model, e);
				viewName = "poi/form";
			}
		}

		return viewName;
	}

	private void handleCreateException(Model model, CreateFault_Exception e) {
		log.error("Error while creating POI.", e);
		model.addAttribute("faultMessage", "error.creatingPoi");
	}

	private void tryToCreatePoi(Poi poi, Model model) throws CreateFault_Exception {
		CreateRequest createRequest = new CreateRequest();
		createRequest.setPoi(poi);
		getServiceClient().create(createRequest);
		model.addAttribute("successMessage", "success.creatingPoi");
	}

	private PoiService getServiceClient() {
		if (this.poiService != null) {
			return this.poiService;
		} else {
			return new PoiServiceClient().getPoiServiceSOAP();
		}
	}
}
