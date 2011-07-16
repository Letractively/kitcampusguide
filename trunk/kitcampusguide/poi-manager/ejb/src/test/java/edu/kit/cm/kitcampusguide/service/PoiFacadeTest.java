package edu.kit.cm.kitcampusguide.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
import edu.kit.cm.kitcampusguide.service.poi.type.PoiWithId;
import edu.kit.cm.kitcampusguide.service.poi.type.Select;
import edu.kit.cm.kitcampusguide.service.poi.type.SelectFault;
import edu.kit.cm.kitcampusguide.service.poi.type.SelectResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PoiFacadeTest {

	@Autowired
	private IPoiDao poiDao;
	@Autowired
	private IPoiService poiService;
	@Autowired
	private POI poi;
	@Autowired
	private POICategory category;

	@Before
	public void setUp() throws Exception {
		this.poiDao.save(category);
		this.poiDao.save(poi);
	}

	@Test
	public void testFindByName() throws SelectFault, RemoteException {

		final Select request = createRequestToGetAllPois();
		SelectResponse response = this.poiService.select(request);

		assertNotNull("No success message was responded", response.getSuccessMessage());
		assertTrue("Poi was not found.", poiWithNameIsInList(this.poi.getName(), response.getPois()));
	}

	private Select createRequestToGetAllPois() {
		final Select request = new Select();
		request.setFindByNamesLike(new String[] { "%" });
		return request;
	}

	private boolean poiWithNameIsInList(String name2find, List<PoiWithId> pois) {
		for (PoiWithId poi : pois) {
			if (poi.getName().equals(name2find)) {
				return true;
			}
		}
		return false;
	}

}
