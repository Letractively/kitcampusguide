package edu.kit.cm.kitcampusguide.ws.poi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.cm.kitcampusguide.dao.IPoiCategoryDao;
import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.CreateResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.DeleteRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.Names;
import edu.kit.cm.kitcampusguide.ws.poi.type.Poi;
import edu.kit.cm.kitcampusguide.ws.poi.type.PoiWithId;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.ReadResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectRequest;
import edu.kit.cm.kitcampusguide.ws.poi.type.SelectResponse;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateFault_Exception;
import edu.kit.cm.kitcampusguide.ws.poi.type.UpdateRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PoiFacadeTest {

	@Autowired
	private IPoiDao poiDao;
	@Autowired
	private IPoiCategoryDao poiCategoryDao;
	@Autowired
	private PoiService poiService;
	@Autowired
	private POI poi;
	private POI poiCopyFromDb;
	@Autowired
	private POI poi2;
	@Autowired
	private Poi requestPoi;
	@Autowired
	private POICategory category;

	@Before
	public void setUp() throws Exception {
		this.poiCategoryDao.save(category);
		this.poiDao.save(poi);
		this.poiCopyFromDb = this.poiDao.findByNameLike(poi.getName()).get(0);
	}

	@Test
	public void testFindByName() throws SelectFault_Exception {

		final SelectRequest request = createRequestToGetAllPois();

		SelectResponse response = this.poiService.select(request);

		assertTrue(poiWithNameIsInList(this.poi.getName(), response.getPoi()));
	}

	@Test
	public void testCreatingPoi() throws CreateFault_Exception {

		final CreateRequest request = new CreateRequest();
		final POI poiClone = this.requestPoi.convertToPojo();
		request.setPoi(this.requestPoi);

		CreateResponse response = this.poiService.create(request);

		assertEquals(poiClone, this.poiDao.findByUid(response.getPoi().getUid()));
	}

	@Test
	public void testUpdatingPoi() throws UpdateFault_Exception {

		final PoiWithId poiWithId = new PoiWithId(this.poi2, this.poiCopyFromDb.getCategoryName());
		poiWithId.setUid(this.poiCopyFromDb.getUid());
		final POI poiClone = poiWithId.convertToPojo();
		UpdateRequest request = new UpdateRequest();
		request.setPoi(poiWithId);

		this.poiService.update(request);

		assertEquals(poiClone, this.poiDao.findByUid(this.poiCopyFromDb.getUid()));
	}

	@Test
	public void testReadingPoi() throws ReadFault_Exception {

		ReadRequest request = new ReadRequest();
		request.setId(this.poiCopyFromDb.getUid());

		ReadResponse response = this.poiService.read(request);

		assertPoisWithIdEqual(new PoiWithId(this.poiCopyFromDb, this.poiCopyFromDb.getCategoryName()),
				response.getPoi());
	}

	@Test
	public void testDeletingPoi() throws DeleteFault_Exception {

		DeleteRequest request = new DeleteRequest();
		request.setId(this.poiCopyFromDb.getUid());

		this.poiService.delete(request);

		assertNull(this.poiDao.findByUid(this.poiCopyFromDb.getUid()));
	}

	private void assertPoisWithIdEqual(PoiWithId o, PoiWithId p) {
		assertEquals(o.getUid(), p.getUid());
		assertEquals(o.getName(), p.getName());
		assertEquals(o.getDescription(), p.getDescription());
		assertEquals(o.getLatitude(), p.getLatitude());
		assertEquals(o.getLongitude(), p.getLongitude());
		assertEquals(o.getCategoryName(), p.getCategoryName());
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
