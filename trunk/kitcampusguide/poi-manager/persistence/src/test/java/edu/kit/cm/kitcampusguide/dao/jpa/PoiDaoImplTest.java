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

import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PoiDaoImplTest {

	@Autowired
	private POI poi1;
	private POI poi1Clone;
	@Autowired
	private POI poi2;
	private POI poi2Clone;
	@Autowired
	private POI poi3;

	@Autowired
	private IPoiDao poiDao;

	@Test
	public void findByNameLike() {
		List<POI> pois = poiDao.findByNameLike(poi1Clone.getName());

		assertFalse(pois.isEmpty());
		assertTrue("Poi was not found by name.", poiWithNameIsInList(poi1Clone.getName(), pois));
	}

	@Test
	@Rollback
	public void uidAssignedAfterCreation() {

		assertNull(poi3.getUid());
		poiDao.save(poi3);
		assertNotNull(poi3.getUid());
	}

	@Test
	public void findByUid() {

		POI poi2FromDb = (POI) poiDao.findByUid(poi2.getUid());
		assertEquals(poi2Clone, poi2FromDb);
	}

	private boolean poiWithNameIsInList(String name2, List<POI> pois) {
		for (POI poi : pois) {
			if (poi.getName().equals(name2)) {
				return true;
			}
		}
		return false;
	}

	@Before
	public void before() throws CloneNotSupportedException {

		poi1Clone = poi1.clone();
		poi2Clone = poi2.clone();

		poiDao.save(poi1);
		poiDao.save(poi2);
	}
}