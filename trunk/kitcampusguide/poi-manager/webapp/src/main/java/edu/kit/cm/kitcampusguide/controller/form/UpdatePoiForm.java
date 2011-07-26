package edu.kit.cm.kitcampusguide.controller.form;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kit.cm.kitcampusguide.validator.PoiValidator;
import edu.kit.cm.kitcampusguide.ws.poi.PoiService;
import edu.kit.cm.kitcampusguide.ws.poi.PoiServiceClient;
import edu.kit.cm.kitcampusguide.ws.poi.type.PoiWithId;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateRequest;

@Controller
@RequestMapping("poi/{uid}/update.htm")
@SessionAttributes(types = PoiWithId.class)
public class UpdatePoiForm {

	private static final Logger log = Logger.getLogger(UpdatePoiForm.class);

	@Autowired
	private PoiService poiService;

	public UpdatePoiForm() {
		// autowiring constructor
	}

	public UpdatePoiForm(PoiService poiService) {
		this.poiService = poiService;
	}

	@RequestMapping(method = RequestMethod.GET)
	protected String setUpForm(@PathVariable("uid") int uid, Model model) {
		String viewName;

		try {

			tryToLoadPoi(uid, model);
			viewName = "poi/form";
		} catch (ReadFault_Exception e) {

			handleReadExceptionAndSetErrorView(model, e);
			viewName = "redirect:/pois.htm";
		}

		return viewName;
	}

	private void tryToLoadPoi(int uid, Model model) throws ReadFault_Exception {
		ReadRequest readRequest = new ReadRequest();
		readRequest.setId(uid);
		model.addAttribute("poi", getServiceClient().read(readRequest).getPoi());
	}

	private void handleReadExceptionAndSetErrorView(Model model, ReadFault_Exception e) {
		log.error("Reading a poi failed.", e);
		model.addAttribute("errorMessage", "error.readPoi");
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String onSubmit(@ModelAttribute(value = "poi") PoiWithId poi, BindingResult result, Model model) {
		String viewName;
		new PoiValidator().validate(poi, result);
		if (result.hasErrors()) {

			viewName = "poi/form";
		} else {
			try {

				tryToUpdatePoiAndAddSuccessMessage(poi, model);
				viewName = "redirect:/pois.htm";
			} catch (UpdateFault_Exception e) {

				handleUpdateException(model, e);
				viewName = "poi/form";
			}
		}

		return viewName;
	}

	private void handleUpdateException(Model model, UpdateFault_Exception e) {
		log.error("Error while updating POI", e);
		model.addAttribute("faultMessage", "error.updatingPoi");
	}

	private void tryToUpdatePoiAndAddSuccessMessage(PoiWithId poi, Model model) throws UpdateFault_Exception {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.setPoi(poi);
		getServiceClient().update(updateRequest);
		model.addAttribute("successMessage", "success.updatingPoi");
	}

	private PoiService getServiceClient() {
		if (this.poiService != null) {
			return this.poiService;
		} else {
			return new PoiServiceClient().getPoiServiceSOAP();
		}
	}
}
