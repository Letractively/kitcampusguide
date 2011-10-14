package edu.kit.cm.kitcampusguide.service.ws;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.cm.kitcampusguide.ws.poi.PoiFacade;
import edu.kit.cm.kitcampusguide.ws.poi.PoiService;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateResponseComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteResponseComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteFault;
import edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.ExecuteResponseComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadResponseComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectResponseComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateRequestComplexType;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateResponseComplexType;

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
        request.addRequest(specificRequest);
        return request;
    }

    @Override
    public ExecuteResponseComplexType execute(ExecuteRequestComplexType parameters) throws ExecuteFault {
        return this.poiService.execute(parameters);
    }

}
