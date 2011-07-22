package edu.kit.cm.kitcampusguide.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
public class POITest {

	@Autowired
	private POI poi1;
	@Autowired
	private POI poi2;
	@Autowired
	private POICategory category1;
	@Autowired
	private POICategory category2;

	@Test
	public void categoryIsAssigned() {

		poi2.setCategory(category2);

		assertEquals(poi2.getCategory(), category2);
		assertTrue(category2.getPois().contains(poi2));
	}

	@Test
	public void categoryIsReassigned() {

		poi1.setCategory(category1);
		poi1.setCategory(category2);

		assertSame(category2, poi1.getCategory());
		assertTrue(category2.getPois().contains(poi1));
	}

	@Test
	public void categoryIsRemovedAfterReassign() {

		poi1.setCategory(category1);
		poi1.setCategory(category2);

		assertNotSame(category1, poi1.getCategory());
		assertFalse(category1.getPois().contains(poi1));
	}

	@Test
	public void settingCategoryTwiceWorks() {

		poi1.setCategory(category1);
		poi2.setCategory(category1);

		assertTrue(category1.getPois().contains(poi1));
		assertTrue(category1.getPois().contains(poi2));
		assertSame(category1, poi1.getCategory());
		assertSame(category1, poi2.getCategory());
	}

	@Test
	public void onlySelectedPoiIsRemovedAfterReassignment() {

		poi1.setCategory(category1);
		poi2.setCategory(category1);
		poi1.setCategory(category2);

		assertFalse(category1.getPois().contains(poi1));
		assertTrue(category1.getPois().contains(poi2));
		assertSame(category1, poi2.getCategory());
	}

}
