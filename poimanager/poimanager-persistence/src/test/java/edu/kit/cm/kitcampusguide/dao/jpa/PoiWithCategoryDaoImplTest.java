package edu.kit.cm.kitcampusguide.dao.jpa;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.cm.kitcampusguide.dao.PoiCategoryDao;
import edu.kit.cm.kitcampusguide.dao.PoiDao;
import edu.kit.cm.kitcampusguide.dao.exception.PoiDaoException;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PoiWithCategoryDaoImplTest {

    @Autowired
    private POI poi1;
    private POI poi1Clone;
    @Autowired
    private POI poi2;
    private POI poi2Clone;
    @Autowired
    private POICategory category1;
    private POICategory category1Clone;
    @Autowired
    private POICategory category2;
    private POICategory category2Clone;

    @Autowired
    private PoiDao poiDao;
    @Autowired
    private PoiCategoryDao poiCategoryDao;

    @Test
    @Rollback
    public void categoryChangesAfterReassignment() throws PoiDaoException {

        poi1.setCategory(category2);
        poiCategoryDao.save(category2);
        poiDao.save(poi1);

        POI poi1FromDb = (POI) poiDao.findByUid(poi1.getId());
        assertEquals(category2Clone, poi1FromDb.getCategory());
    }

    @Before
    public void before() throws CloneNotSupportedException, PoiDaoException {
        poi1Clone = poi1.clone();
        poi2Clone = poi2.clone();
        category1Clone = category1.clone();
        category2Clone = category2.clone();

        poi1.setCategory(category1);
        poi2.setCategory(category1);
        poi1Clone.setCategory(category1Clone);
        poi2Clone.setCategory(category1Clone);

        poiCategoryDao.save(category1);
        poiDao.save(poi1);
        poiDao.save(poi2);
    }
}