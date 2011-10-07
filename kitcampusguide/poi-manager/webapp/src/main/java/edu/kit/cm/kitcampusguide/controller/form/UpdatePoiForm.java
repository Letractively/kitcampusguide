package edu.kit.cm.kitcampusguide.controller.form;

import java.util.Collection;

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

import edu.kit.cm.kitcampusguide.model.Group;
import edu.kit.cm.kitcampusguide.service.user.MemberService;
import edu.kit.cm.kitcampusguide.validator.PoiValidator;
import edu.kit.cm.kitcampusguide.ws.poi.PoiFacade;
import edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteFault;
import edu.kit.cm.kitcampusguide.ws.poi.type.PoiWithId;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateRequestComplexType;

/**
 * Controller for updating poi webpage.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
@Controller
@RequestMapping("poi/{uid}/update.htm")
@SessionAttributes(types = PoiWithId.class)
public class UpdatePoiForm {

    private static final Logger log = Logger.getLogger(UpdatePoiForm.class);

    @Autowired
    private PoiFacade poiFacade;

    @Autowired
    private MemberService memberService;

    public UpdatePoiForm() {
        super();
        // autowiring constructor
    }

    public UpdatePoiForm(PoiFacade poiFacade, MemberService memberService) {
        super();
        this.poiFacade = poiFacade;
        this.memberService = memberService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setUpForm(@PathVariable("uid") int uid, Model model) {
        log.debug("Setting up form for updating poi with id " + uid);
        String viewName;

        try {

            tryToLoadPoiAndSetToModel(uid, model);
            tryToLoadGroupsAndSetToModel(model);
            viewName = "poi/form";
        } catch (ExecuteFault e) {

            handleReadExceptionAndSetErrorView(model, e);
            viewName = "redirect:/pois.htm";
        }

        return viewName;
    }

    private void tryToLoadPoiAndSetToModel(int uid, Model model) throws ExecuteFault {
        ReadRequestComplexType readRequest = new ReadRequestComplexType();
        readRequest.setId(uid);
        PoiWithId poi = (poiFacade.read(readRequest).getPoi());
        model.addAttribute("poi", poi);
    }

    private void tryToLoadGroupsAndSetToModel(Model model) {
        Collection<Group> groups = this.memberService.getGroups();
        model.addAttribute("groups", groups);
    }

    private void handleReadExceptionAndSetErrorView(Model model, ExecuteFault e) {
        log.error("Reading a poi failed.", e);
        model.addAttribute("errorMessage", "error.readPoi");
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String onSubmit(@ModelAttribute(value = "poi") PoiWithId poi, BindingResult result, Model model) {
        log.debug("Update request for poi. data: " + poi);
        String viewName;
        new PoiValidator().validate(poi, result);
        if (result.hasErrors()) {

            viewName = "poi/form";
        } else {
            try {

                tryToUpdatePoiAndAddSuccessMessage(poi, model);
                viewName = "redirect:/pois.htm";
            } catch (ExecuteFault e) {

                handleUpdateException(model, e);
                viewName = "poi/form";
            }
        }

        return viewName;
    }

    private void handleUpdateException(Model model, ExecuteFault e) {
        log.error("Error while updating POI", e);
        model.addAttribute("faultMessage", "error.updatingPoi");
    }

    private void tryToUpdatePoiAndAddSuccessMessage(PoiWithId poi, Model model) throws ExecuteFault {
        UpdateRequestComplexType updateRequest = new UpdateRequestComplexType();
        updateRequest.setPoi(poi);
        poiFacade.update(updateRequest);
        model.addAttribute("successMessage", "success.updatingPoi");
    }
}
