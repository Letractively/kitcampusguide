package edu.kit.cm.kitcampusguide.service;

import javax.ejb.Remote;

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

/**
 * Interface for poi access through cruds.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
@Remote
public interface IPoiService {
	public CreateResponse create(Create createRequest) throws java.rmi.RemoteException, CreateFault;

	public DeleteResponse delete(Delete deleteRequest) throws java.rmi.RemoteException, DeleteFault;

	public UpdateResponse update(Update updateRequest) throws java.rmi.RemoteException, UpdateFault;

	public ReadResponse read(Read readRequest) throws java.rmi.RemoteException, ReadFault;

	public SelectResponse select(Select selectRequest) throws java.rmi.RemoteException, SelectFault;
}
