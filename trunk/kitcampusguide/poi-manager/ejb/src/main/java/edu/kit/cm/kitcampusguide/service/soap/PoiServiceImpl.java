package edu.kit.cm.kitcampusguide.service.soap;

import java.rmi.RemoteException;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.cm.kitcampusguide.service.IPoiService;
import edu.kit.cm.kitcampusguide.service.poi.type.Create;
import edu.kit.cm.kitcampusguide.service.poi.type.CreateFault;
import edu.kit.cm.kitcampusguide.service.poi.type.CreateResponse;
import edu.kit.cm.kitcampusguide.service.poi.type.Delete;
import edu.kit.cm.kitcampusguide.service.poi.type.DeleteFault;
import edu.kit.cm.kitcampusguide.service.poi.type.DeleteResponse;
import edu.kit.cm.kitcampusguide.service.poi.type.Read;
import edu.kit.cm.kitcampusguide.service.poi.type.ReadFault;
import edu.kit.cm.kitcampusguide.service.poi.type.ReadResponse;
import edu.kit.cm.kitcampusguide.service.poi.type.Select;
import edu.kit.cm.kitcampusguide.service.poi.type.SelectFault;
import edu.kit.cm.kitcampusguide.service.poi.type.SelectResponse;
import edu.kit.cm.kitcampusguide.service.poi.type.Update;
import edu.kit.cm.kitcampusguide.service.poi.type.UpdateFault;
import edu.kit.cm.kitcampusguide.service.poi.type.UpdateResponse;

@WebService(name = "PoiService", serviceName = "PoiService", targetNamespace = "urn:http://cm.tm.kit.edu/kitcampusguide/poiservice/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Stateless(name = "PoiService")
public class PoiServiceImpl implements IPoiService {

	@Autowired
	private IPoiService facade;

	@WebMethod
	public CreateResponse create(Create createRequest) throws RemoteException, CreateFault {

		return facade.create(createRequest);
	}

	@WebMethod
	public DeleteResponse delete(Delete deleteRequest) throws RemoteException, DeleteFault {

		return facade.delete(deleteRequest);
	}

	@WebMethod
	public UpdateResponse update(Update updateRequest) throws RemoteException, UpdateFault {

		return facade.update(updateRequest);
	}

	@WebMethod
	public ReadResponse read(Read readRequest) throws RemoteException, ReadFault {

		return facade.read(readRequest);
	}

	@WebMethod
	public SelectResponse select(Select selectRequest) throws RemoteException, SelectFault {

		return facade.select(selectRequest);
	}

}
