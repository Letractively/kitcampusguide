package edu.kit.cm.kitcampusguide.ws.poi;

import org.springframework.security.access.prepost.PreAuthorize;

import edu.kit.tm.cm.kitcampusguide.poiservice.CreateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteFault;
import edu.kit.tm.cm.kitcampusguide.poiservice.PoiService;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateResponseComplexType;

/**
 * Facade for cruds access to database through service request.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface PoiFacade extends PoiService {

	@PreAuthorize("isAuthenticated()")
    CreateResponseComplexType create(CreateRequestComplexType createRequest) throws ExecuteFault;

    @PreAuthorize("isAuthenticated()")
    DeleteResponseComplexType delete(DeleteRequestComplexType deleteRequest) throws ExecuteFault;

    @PreAuthorize("hasPermission(#poi, 'RIGHT_UPDATE_POI')")
    UpdateResponseComplexType update(UpdateRequestComplexType updateRequest) throws ExecuteFault;

    @PreAuthorize("isAuthenticated()")
    ReadResponseComplexType read(ReadRequestComplexType readRequest) throws ExecuteFault;

    @PreAuthorize("isAuthenticated()")
    SelectResponseComplexType select(SelectRequestComplexType selectRequest) throws ExecuteFault;

}