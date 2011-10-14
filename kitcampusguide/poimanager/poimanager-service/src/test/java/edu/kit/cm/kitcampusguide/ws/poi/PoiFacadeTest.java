package edu.kit.cm.kitcampusguide.ws.poi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

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

import edu.kit.cm.kitcampusguide.dao.PoiCategoryDao;
import edu.kit.cm.kitcampusguide.dao.PoiDao;
import edu.kit.cm.kitcampusguide.dao.exception.PoiDaoException;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
import edu.kit.cm.kitcampusguide.ws.poi.util.PoiConverter;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.CreateResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.DeleteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteFault;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ExecuteResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.Names;
import edu.kit.tm.cm.kitcampusguide.poiservice.Poi;
import edu.kit.tm.cm.kitcampusguide.poiservice.PoiService;
import edu.kit.tm.cm.kitcampusguide.poiservice.PoiWithId;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.ReadResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectRequestComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.SelectResponseComplexType;
import edu.kit.tm.cm.kitcampusguide.poiservice.UpdateRequestComplexType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PoiFacadeTest {

    @Inject
    private final PoiService poiService = new PoiFacadeImpl();

    @Autowired
    private PoiDao poiDao;
    @Autowired
    private PoiCategoryDao poiCategoryDao;
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
    public void testFindByName() throws ExecuteFault {
        final SelectRequestComplexType request = createRequestToGetAllPois();
        final ExecuteRequestComplexType execRequest = new ExecuteRequestComplexType();
        execRequest.getCreateRequestsOrReadRequestsOrUpdateRequests().add(request);

        ExecuteResponseComplexType response = this.poiService.execute(execRequest);

        assertTrue(poiWithNameIsInList(this.poi.getName(), ((SelectResponseComplexType) response
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0)).getPoi()));
    }

    @Test
    public void testCreatingPoi() throws ExecuteFault, PoiDaoException {
        final CreateRequestComplexType request = new CreateRequestComplexType();
        final POI poiClone = PoiConverter.convertToPojo(this.requestPoi);
        request.setPoi(this.requestPoi);
        final ExecuteRequestComplexType execRequest = new ExecuteRequestComplexType();
        execRequest.getCreateRequestsOrReadRequestsOrUpdateRequests().add(request);

        ExecuteResponseComplexType response = this.poiService.execute(execRequest);

        assertEquals(poiClone, this.poiDao.findByUid(((CreateResponseComplexType) response
                .getCreateResponsesOrReadResponsesOrUpdateResponses().get(0)).getPoi().getUid()));
    }

    @Test
    public void testUpdatingPoi() throws ExecuteFault, PoiDaoException {
        this.poi2.setUid(this.poiCopyFromDb.getUid());
        final PoiWithId poiWithId = PoiConverter.createPoiWithId(this.poi2);
        final POI poiClone = PoiConverter.convertToPojo(poiWithId);
        final UpdateRequestComplexType request = new UpdateRequestComplexType();
        request.setPoi(poiWithId);
        final ExecuteRequestComplexType execRequest = new ExecuteRequestComplexType();
        execRequest.getCreateRequestsOrReadRequestsOrUpdateRequests().add(request);

        this.poiService.execute(execRequest);

        assertEquals(poiClone, this.poiDao.findByUid(this.poiCopyFromDb.getUid()));
    }

    @Test
    public void testReadingPoi() throws ExecuteFault {
        final ReadRequestComplexType request = new ReadRequestComplexType();
        request.setId(this.poiCopyFromDb.getUid());
        final ExecuteRequestComplexType execRequest = new ExecuteRequestComplexType();
        execRequest.getCreateRequestsOrReadRequestsOrUpdateRequests().add(request);

        final ExecuteResponseComplexType response = this.poiService.execute(execRequest);

        assertPoisWithIdEqual(PoiConverter.createPoiWithId(this.poiCopyFromDb),
                ((ReadResponseComplexType) response.getCreateResponsesOrReadResponsesOrUpdateResponses().get(0))
                        .getPoi());
    }

    @Test
    public void testDeletingPoi() throws ExecuteFault, PoiDaoException {
        final DeleteRequestComplexType request = new DeleteRequestComplexType();
        request.setId(this.poiCopyFromDb.getUid());
        final ExecuteRequestComplexType execRequest = new ExecuteRequestComplexType();
        execRequest.getCreateRequestsOrReadRequestsOrUpdateRequests().add(request);

        this.poiService.execute(execRequest);

        assertNull(this.poiDao.findByUid(this.poiCopyFromDb.getUid()));
    }

    private void assertPoisWithIdEqual(PoiWithId o, PoiWithId p) {
        assertEquals(o.getUid(), p.getUid());
        assertEquals(o.getName(), p.getName());
        assertEquals(o.getDescription(), p.getDescription());
        assertEquals(o.getLatitude(), p.getLatitude(), 0.005);
        assertEquals(o.getLongitude(), p.getLongitude(), 0.005);
        assertEquals(o.getCategoryName(), p.getCategoryName());
    }

    private SelectRequestComplexType createRequestToGetAllPois() {
        final SelectRequestComplexType request = new SelectRequestComplexType();
        Names names = new Names();
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
