package edu.kit.cm.kitcampusguide.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import edu.kit.cm.kitcampusguide.dao.exception.PoiDaoException;
import edu.kit.cm.kitcampusguide.model.POICategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PoiCategoryDaoImplTest {

    @Autowired
    private POICategory category1;
    private POICategory category1Clone;
    @Autowired
    private POICategory category2;

    @Autowired
    private PoiCategoryDao poiCategoryDao;

    @Test
    public void findByNameLike() {
        List<POICategory> pois = poiCategoryDao.findByNameLike(category1Clone.getName());

        assertFalse(pois.isEmpty());
        assertTrue(categoryWithNameIsInList(category1Clone.getName(), pois));
    }

    @Test
    @Rollback
    public void uidAssignedAfterCreation() throws PoiDaoException {

        assertNull(category2.getUid());
        poiCategoryDao.save(category2);
        assertNotNull(category2.getUid());
    }

    @Test
    public void findByUid() throws PoiDaoException {

        POICategory category1FromDb = (POICategory) poiCategoryDao.findByUid(category1.getUid());
        assertEquals(category1Clone, category1FromDb);
    }

    private boolean categoryWithNameIsInList(String name2, List<POICategory> categories) {
        for (POICategory category : categories) {
            if (category.getName().equals(name2)) {
                return true;
            }
        }
        return false;
    }

    @Before
    public void before() throws PoiDaoException {

        category1Clone = category1.clone();

        poiCategoryDao.save(category1);
    }
}