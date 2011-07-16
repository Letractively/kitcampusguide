package edu.kit.cm.kitcampusguide.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.service.poi.type.Create;
import edu.kit.cm.kitcampusguide.service.poi.type.CreateFault;
import edu.kit.cm.kitcampusguide.service.poi.type.CreateResponse;
import edu.kit.cm.kitcampusguide.service.poi.type.Delete;
import edu.kit.cm.kitcampusguide.service.poi.type.DeleteFault;
import edu.kit.cm.kitcampusguide.service.poi.type.DeleteResponse;
import edu.kit.cm.kitcampusguide.service.poi.type.PoiWithId;
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
 * Facade for cruds access to database through service request.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
@Repository
public class PoiFacade implements IPoiService {

	@Autowired
	private IPoiDao poiDao;

	@Override
	public CreateResponse create(Create createRequest) throws RemoteException, CreateFault {
		POI poiFromRequest = createRequest.getPoi().convertToPojo();
		String categoryFromRequest = createRequest.getPoi().getCategoryName();

		this.poiDao.save(poiFromRequest);

		CreateResponse response = new CreateResponse();
		response.setPoi(new PoiWithId(poiFromRequest, categoryFromRequest));
		response.setSuccessMessage("The point of interest was successfully added.");

		return response;
	}

	@Override
	public DeleteResponse delete(Delete deleteRequest) throws RemoteException, DeleteFault {
		this.poiDao.remove(this.poiDao.findByUid(deleteRequest.getId()));

		DeleteResponse response = new DeleteResponse();
		response.setSuccessMessage("The point of interest was successfully removed.");

		return response;
	}

	@Override
	public UpdateResponse update(Update updateRequest) throws RemoteException, UpdateFault {
		POI poiFromRequest = updateRequest.getPoi().convertToPojo();

		this.poiDao.refresh(poiFromRequest);

		UpdateResponse response = new UpdateResponse();
		response.setSuccessMessage("The changes were successfully saved.");

		return response;
	}

	@Override
	public ReadResponse read(Read readRequest) throws RemoteException, ReadFault {
		int idToRead = readRequest.getId();

		POI foundPoi = (POI) this.poiDao.findByUid(idToRead);

		ReadResponse response = new ReadResponse();
		response.setPoi(new PoiWithId(foundPoi, foundPoi.getCategory().getName()));
		response.setSuccessMessage("Successfully retrieved the point of interest.");

		return response;
	}

	@Override
	public SelectResponse select(Select selectRequest) throws RemoteException, SelectFault {
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

	private Collection<? extends POI> findPoisByNameSuffixes(String[] suffixes) {
		List<POI> pois = new ArrayList<POI>();

		if (suffixes != null) {
			for (String suffix : suffixes) {
				pois.addAll(this.poiDao.findByNameWithSuffix(suffix));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByNamesLike(String[] likePatterns) {
		List<POI> pois = new ArrayList<POI>();

		if (likePatterns != null) {
			for (String likePattern : likePatterns) {
				pois.addAll(this.poiDao.findByNameLike(likePattern));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByNames(String[] names) {
		List<POI> pois = new ArrayList<POI>();

		if (names != null) {
			for (String name : names) {
				pois.addAll(this.poiDao.findByNameLike(name));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByNamePrefixes(String[] prefixes) {
		List<POI> pois = new ArrayList<POI>();

		if (prefixes != null) {
			for (String prefix : prefixes) {
				pois.add((POI) this.poiDao.findByNameWithPrefix(prefix));
			}
		}

		return pois;
	}

	private Collection<? extends POI> findPoisByIds(int[] ids) {
		List<POI> pois = new ArrayList<POI>();

		if (ids != null) {
			for (int uid : ids) {
				pois.add((POI) this.poiDao.findByUid(uid));
			}
		}

		return pois;
	}

}
