package edu.kit.cm.kitcampusguide.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext/applicationContext-*.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class POICategoryTest {

	@Autowired
	private POI poi1;
	@Autowired
	private POI poi2;
	@Autowired
	private POI poi3;
	@Autowired
	private POICategory category1;
	@Autowired
	private POICategory category2;

	@Test
	public void poiIsAdded() {

		category1.add(poi1);

		assertTrue(category1.getPois().contains(poi1));
		assertSame(category1, poi1.getCategory());
	}

	@Test
	public void poiIsRemoved() {

		category1.add(poi1);
		category1.remove(poi1);

		assertFalse(category1.getPois().contains(poi1));
		assertNotSame(category1, poi1.getCategory());
	}

	@Test
	public void poiIsNotRemovedAfterAddingOtherPoi() {

		category1.add(poi1);
		category1.add(poi2);

		assertTrue(category1.getPois().contains(poi1));
		assertSame(category1, poi1.getCategory());
	}

	@Test
	public void poiIsNotRemovedAfterRemovingOtherPoi() {

		category1.add(poi2);
		category1.add(poi1);
		category1.remove(poi1);

		assertTrue(category1.getPois().contains(poi2));
		assertSame(category1, poi2.getCategory());
	}

	@Test
	public void poiCollectionIsSet() {

		Collection<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);

		category1.setPois(pois);

		assertTrue(category1.getPois().contains(poi1));
		assertTrue(category1.getPois().contains(poi2));
		assertSame(category1, poi1.getCategory());
		assertSame(category1, poi2.getCategory());
	}

	@Test
	public void poiCollectionIsRemoved() {
		Collection<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);

		category1.setPois(pois);
		category1.setPois(new ArrayList<POI>());

		assertFalse(category1.getPois().contains(poi1));
		assertFalse(category1.getPois().contains(poi2));
		assertNotSame(category1, poi1.getCategory());
		assertNotSame(category1, poi2.getCategory());
	}

	@Test
	public void poiCollectionIsReset() {
		Collection<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);

		category1.setPois(pois);
		category1.add(poi3);
		category2.setPois(pois);

		assertTrue(category2.getPois().contains(poi1));
		assertTrue(category2.getPois().contains(poi2));
		assertSame(category2, poi1.getCategory());
		assertSame(category2, poi2.getCategory());
	}

	@Test
	public void poiCollectionIsRemovedAfterReset() {
		Collection<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);

		category1.setPois(pois);
		category1.add(poi3);
		category2.setPois(pois);

		assertFalse(category1.getPois().contains(poi1));
		assertFalse(category1.getPois().contains(poi2));
		assertNotSame(category1, poi1.getCategory());
		assertNotSame(category1, poi2.getCategory());
	}

	@Test
	public void poiIsNotRemovedAfterRemovingCollection() {
		Collection<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);

		category1.setPois(pois);
		category1.add(poi3);
		category2.setPois(pois);

		assertTrue(category1.getPois().contains(poi3));
		assertSame(category1, poi3.getCategory());
	}
}
