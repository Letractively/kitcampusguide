package edu.kit.cm.kitcampusguide.ws.poi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.Ids;
import edu.kit.cm.kitcampusguide.ws.poi.type.Names;
import edu.kit.cm.kitcampusguide.ws.poi.type.PoiWithId;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateResponse;

/**
 * Facade for cruds access to database through service request.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
@Repository
@Transactional
public class PoiFacade implements PoiService {

	private static final Logger log = Logger.getLogger(PoiFacade.class);

	@Autowired
	private IPoiDao dao;

	public PoiFacade() {
		// constructor for auto wiring
	}

	public PoiFacade(IPoiDao dao) {
		this.dao = dao;
	}

	public CreateResponse create(CreateRequest createRequest) throws CreateFault_Exception {

		POI poiFromRequest = createRequest.getPoi().convertToPojo();
		String categoryFromRequest = createRequest.getPoi().getCategoryName();

		this.dao.save(poiFromRequest);

		CreateResponse response = new CreateResponse();
		response.setPoi(new PoiWithId(poiFromRequest, categoryFromRequest));
		response.setSuccessMessage("The point of interest was successfully added.");

		return response;
	}

	public DeleteResponse delete(DeleteRequest deleteRequest) throws DeleteFault_Exception {
		this.dao.remove(this.dao.findByUid(deleteRequest.getId()));

		DeleteResponse response = new DeleteResponse();
		response.setSuccessMessage("The point of interest was successfully removed.");

		return response;
	}

	public UpdateResponse update(UpdateRequest updateRequest) throws UpdateFault_Exception {
		POI poiFromRequest = updateRequest.getPoi().convertToPojo();

		this.dao.merge(poiFromRequest);

		UpdateResponse response = new UpdateResponse();
		response.setSuccessMessage("The changes were successfully saved.");

		return response;
	}

	public ReadResponse read(ReadRequest readRequest) throws ReadFault_Exception {
		int idToRead = readRequest.getId();

		POI foundPoi = (POI) this.dao.findByUid(idToRead);

		ReadResponse response = new ReadResponse();
		if (foundPoi != null) {

			response.setPoi(new PoiWithId(foundPoi, foundPoi.getCategoryName()));
			response.setSuccessMessage("Successfully retrieved the point of interest.");
		} else {
			throw new ReadFault_Exception("Could not find poi with uid " + idToRead + ".", new ReadFault(readRequest));
		}

		return response;
	}

	public SelectResponse select(SelectRequest selectRequest) throws SelectFault_Exception {
		List<POI> foundPois = new ArrayList<POI>();

		foundPois.addAll(findPoisByIds(selectRequest.getFindByIds()));

		foundPois.addAll(findPoisByNamePrefixes(selectRequest.getFindByNamePrefixes()));

		foundPois.addAll(findPoisByNames(selectRequest.getFindByNames()));

		foundPois.addAll(findPoisByNamesLike(selectRequest.getFindByNamesLike()));

		foundPois.addAll(findPoisByNameSuffixes(selectRequest.getFindByNameSuffixes()));

		SelectResponse response = new SelectResponse();
		response.convertPoisForResponse(foundPois);
		response.setSuccessMessage("Successfully retrieved points of interest.");

		return response;
	}

	private Collection<? extends POI> findPoisByNameSuffixes(Names suffixes) {
		List<POI> pois = new ArrayList<POI>();

		if (suffixes != null) {
			for (String suffix : suffixes.getName()) {
				pois.addAll(this.dao.findByNameWithSuffix(suffix));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByNamesLike(Names likePatterns) {
		List<POI> pois = new ArrayList<POI>();

		if (likePatterns != null) {
			for (String likePattern : likePatterns.getName()) {
				pois.addAll(this.dao.findByNameLike(likePattern));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByNames(Names names) {
		List<POI> pois = new ArrayList<POI>();

		if (names != null) {
			for (String name : names.getName()) {
				pois.addAll(this.dao.findByNameLike(name));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByNamePrefixes(Names prefixes) {
		List<POI> pois = new ArrayList<POI>();

		if (prefixes != null) {
			for (String prefix : prefixes.getName()) {
				pois.addAll(this.dao.findByNameWithPrefix(prefix));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByIds(Ids ids) {
		List<POI> pois = new ArrayList<POI>();

		if (ids != null) {
			for (int uid : ids.getId()) {
				pois.add((POI) this.dao.findByUid(uid));
			}
		}

		return pois;
	}

}
