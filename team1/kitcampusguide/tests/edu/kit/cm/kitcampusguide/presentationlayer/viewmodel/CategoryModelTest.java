package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.standardtypes.Category;

/**
 * 
 * @author Corny
 * 
 **/

public class CategoryModelTest {

	
		private static CategoryModel testModel;
		/**
		 * Creates a testing Category Model
		 */
		@BeforeClass
		public static void setUpTestData() {
			testModel = new CategoryModel();
		}
		
		/**
		 * Tests if nullPointerException is thrown when setting 
		 * categories attribute with null.
		 */
		@Test(expected= NullPointerException.class)
		public void setCategoriesNull() {
			Set<Category> testCategories = null;
			testModel.setCategories(testCategories);
		}
		
		/**
		 * Tests if categories attribute is set and returned correctly
		 * not null category attribute.
		 */
		@Test
		public void setCategories() {
			Set<Category> testCategories = new HashSet<Category>();
			//test with set of 100 categories
			for (int i = 0; i < 100; i++) {
				testCategories.add(new Category(i, "test" + i));
			}
			testModel.setCategories(testCategories);
			assertEquals("Categories attribute is not set correctly", testCategories, testModel.getCategories());
		}
		
		/**
		 * Tests if nullPointerException is thrown when setting current
		 * Categories attribute with null.
		 */
		@Test(expected = NullPointerException.class)
		public void setCurrentCategoriesNull() {
			testModel.setCategories(null);
		}
		
		/**
		 * Tests if a sequence of sets/gets of currentCategories attribute
		 * is correctly processed.
		 */
		@Test
		public void setCurrentCategories() {
			//Test with category set containing 100 categories
			Set<Category> testCurrentCategories1 = new HashSet<Category>();
			for (int i = 0; i < 100; i++) {
				testCurrentCategories1.add(new Category(i, "test" + i));
			}
			testModel.setCurrentCategories(testCurrentCategories1);
			assertEquals("CurrentCategories attribute is not set correctly", testCurrentCategories1, testModel.getCurrentCategories());
			//Test with category set containing no categories
			Set<Category> testCurrentCategories2 = new HashSet<Category>();
			testModel.setCurrentCategories(testCurrentCategories2);
			assertEquals("CurrentCategories attribute is not set correctly", testCurrentCategories2, testModel.getCurrentCategories());
			//Test with another category set containing 5 categories
			Set<Category> testCurrentCategories3 = new HashSet<Category>();
			for (int i = 0; i < 5; i++) {
				testCurrentCategories1.add(new Category(i, "test" + i));
			}
			testModel.setCurrentCategories(testCurrentCategories3);
			assertEquals("CurrentCategories attribute is not set correctly", testCurrentCategories3, testModel.getCurrentCategories());
		}
}
