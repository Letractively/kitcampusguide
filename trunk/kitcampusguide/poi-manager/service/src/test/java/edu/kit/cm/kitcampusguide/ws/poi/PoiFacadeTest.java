package edu.kit.cm.kitcampusguide.ws.poi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import edu.kit.cm.kitcampusguide.ws.poi.PoiService;
import edu.kit.cm.kitcampusguide.ws.poi.type.Names;
import edu.kit.cm.kitcampusguide.ws.poi.type.PoiWithId;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PoiFacadeTest {

	@Autowired
	private IPoiDao poiDao;
	@Autowired
	private PoiService poiService;
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
	public void testFindByName() throws SelectFault_Exception {

		final SelectRequest request = createRequestToGetAllPois();
		SelectResponse response = this.poiService.select(request);

		assertNotNull("No success message was responded", response.getSuccessMessage());
		assertTrue("Poi was not found.", poiWithNameIsInList(this.poi.getName(), response.getPoi()));
	}

	private SelectRequest createRequestToGetAllPois() {
		final SelectRequest request = new SelectRequest();
		Names names = new Names();
		names.setName(new ArrayList<String>());
		names.getName().add("%");
		request.setFindByNamesLike(names);
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
