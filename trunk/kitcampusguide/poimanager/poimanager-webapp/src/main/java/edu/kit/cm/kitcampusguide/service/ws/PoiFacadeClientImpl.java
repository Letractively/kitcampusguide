package edu.kit.cm.kitcampusguide.service.ws;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.cm.kitcampusguide.ws.poi.PoiFacade;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteFault;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.PoiService;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateResponseComplexType;

/**
 * Handles access to pois through webservice on client side.
 * 
 * @author roland
 */
public class PoiFacadeClientImpl implements PoiFacade, PoiService {

    @Autowired
    private PoiService poiService;

    public PoiFacadeClientImpl() {
        super();
    }

    public PoiFacadeClientImpl(PoiService poiService) {
        super();
        this.poiService = poiService;
    }

    @Override
    public CreateResponseComplexType create(CreateRequestComplexType createRequest) throws ExecuteFault {
        return (CreateResponseComplexType) this.poiService.execute(createExecuteRequestWith(createRequest))
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0);
    }

    @Override
    public DeleteResponseComplexType delete(DeleteRequestComplexType deleteRequest) throws ExecuteFault {
        return (DeleteResponseComplexType) this.poiService.execute(createExecuteRequestWith(deleteRequest))
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0);
    }

    @Override
    public UpdateResponseComplexType update(UpdateRequestComplexType updateRequest) throws ExecuteFault {
        return (UpdateResponseComplexType) this.poiService.execute(createExecuteRequestWith(updateRequest))
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0);
    }

    @Override
    public ReadResponseComplexType read(ReadRequestComplexType readRequest) throws ExecuteFault {
        return (ReadResponseComplexType) this.poiService.execute(createExecuteRequestWith(readRequest))
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0);
    }

    @Override
    public SelectResponseComplexType select(SelectRequestComplexType selectRequest) throws ExecuteFault {
        return (SelectResponseComplexType) this.poiService.execute(createExecuteRequestWith(selectRequest))
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0);
    }

    private ExecuteRequestComplexType createExecuteRequestWith(Object specificRequest) {
        ExecuteRequestComplexType request = new ExecuteRequestComplexType();
        request.getCreateRequestsOrReadRequestsOrUpdateRequests().add(specificRequest);
        return request;
    }

    @Override
    public ExecuteResponseComplexType execute(ExecuteRequestComplexType parameters) throws ExecuteFault {
        return this.poiService.execute(parameters);
    }

}
