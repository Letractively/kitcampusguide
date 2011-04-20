/**
 * 
 */
package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import static edu.kit.cm.kitcampusguide.testframework.Idgenerator.*;

/**
 * Tests {@link edu.kit.cm.kitcampusguide.standardtypes.Category}
 * @author Fabian
 *
 */
public class CategoryTest {

	private static int categoryID;
	private static Category category;

	/**
	 * Sets up the category to test.
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		categoryID = getFreeCategoryID();
		category = new Category(categoryID, "cat" + categoryID);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#Category(int, java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCategoryNameNull() {
		new Category(getFreeCategoryID(), null);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#Category(int, java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCategoryIDTaken() {
		new Category(categoryID, "failcat");
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#getID()}.
	 */
	@Test
	public void testGetID() {
		assertEquals(categoryID, category.getID());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("cat" + categoryID, category.getName());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#getAllCategories()}.
	 */
	@Test
	public void testGetAllCategories() {
		Collection<Category> categories = Category.getAllCategories();
		assertTrue(categoryCollectionContainsID(categoryID, categories));
	}
	
	private static boolean categoryCollectionContainsID(int id, Collection<Category> categories) {
		for (Category c : categories) {
			if (c.getID() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#getCategoriesByIDs(java.util.Collection)}.
	 */
	@Test
	public void testGetCategoriesByIDs() {
		assertTrue(Category.getCategoriesByIDs(Arrays.asList(categoryID)).contains(category));
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.standardtypes.Category#getCategoryByID(int)}.
	 */
	@Test
	public void testGetCategoryByID() {
		assertSame(category, Category.getCategoryByID(categoryID));
	}

}
