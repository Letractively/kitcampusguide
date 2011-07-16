package edu.kit.cm.kitcampusguide.dao.jpa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PoiDaoImplTest {

	private static final String NAME = "Nicest Place to be";

	private static final Double LONGITUDE = 0.987654;

	private static final Double LATITUDE = 0.123456;

	private static final String ICON = "SomeIcon";

	private static final String DESCRIPTION = "This is the description of the nicest place on earth";

	@Autowired
	private IPoiDao poiDao;

	@Before
	public void before() {
		POI poi = new POI();
		poi.setIcon(ICON);
		poi.setDescription(DESCRIPTION);
		poi.setName(NAME);
		poi.setLatitude(LATITUDE);
		poi.setLongitude(LONGITUDE);

		poiDao.save(poi);
	}

	@Test
	public void findByUserName() {
		List<POI> pois = poiDao.findByNameLike(NAME);

		assertFalse(pois.isEmpty());
		assertTrue("Poi was not found by name.", poiWithNameIsInList(NAME, pois));
	}

	private boolean poiWithNameIsInList(String name2, List<POI> pois) {
		for (POI poi : pois) {
			if (poi.getName().equals(name2)) {
				return true;
			}
		}
		return false;
	}
}