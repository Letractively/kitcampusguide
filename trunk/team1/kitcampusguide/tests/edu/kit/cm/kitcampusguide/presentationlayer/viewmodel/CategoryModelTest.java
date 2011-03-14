package edu.kit.cm.kitcampusguide.presentationlayer.viewmodel;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.testframework.Idgenerator;
import edu.kit.cm.kitcampusguide.standardtypes.Category;

/**
 * 
 * @author Corny
 * 
 **/

public class CategoryModelTest {

	
		private static CategoryModel testModel;
		private static final Integer MAX_CAT = 10;
		private static Set<Category> testingCategories;
		/**
		 * Creates a testing Category Model
		 */
		@BeforeClass
		public static void setUpTestData() {
			testModel = new CategoryModel();
			//Set up initial set of categories for testing
			testingCategories = new HashSet<Category>();
			//test with set of MAX_CAT categories
			for (int i = 0; i < MAX_CAT; i++) {
				testingCategories.add(new Category(Idgenerator.getFreeCategoryID(), "test" + i));
			}
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
			testModel.setCategories(testingCategories);
			assertEquals("Categories attribute is not set correctly", testingCategories, testModel.getCategories());
		}
		
		/**
		 * Tests if nullPointerException is thrown when setting current
		 * Categories attribute with null.
		 */
		@Test(expected = NullPointerException.class)
		public void setCurrentCategoriesNull() {
			testModel.setCurrentCategories(null);
		}
		
		/**
		 * Tests if a sequence of sets/gets of currentCategories attribute
		 * is correctly processed.
		 */
		@Test
		public void setCurrentCategories() {
			//Set current Categories with all existing categories
			testModel.setCategories(testingCategories);
			testModel.setCurrentCategories(testingCategories);
			assertEquals("CurrentCategories is not set correctly with the full set of categories", testingCategories, testModel.getCurrentCategories());
			//Creates a random selection of categories
			Set<Category> testCurrentCategories1 = new HashSet<Category>();
			Random idGen = new Random();
			for (int i = 0; i < 20; i++) {
				Integer generatedId = idGen.nextInt(MAX_CAT);
				if (Idgenerator.requestCategoryID(generatedId) == false) {
					testCurrentCategories1.add(Category.getCategoryByID(generatedId));
				}
			}
			testModel.setCurrentCategories(testCurrentCategories1);
			assertEquals("CurrentCategories is not set correctly with an random set of categories contained in categories attribute", testCurrentCategories1, testModel.getCurrentCategories());
			//Reset currentCategories to all available categories
			testModel.setCurrentCategories(testingCategories);
			assertEquals("CurrentCategories is not reset corretly to the full set of categories contained in categories attribute", testingCategories, testModel.getCurrentCategories());
		}
		
		/**
		 * Tests if CategoryModel correctly handles illegal "set"-operation of currentCategories 
		 * attribute (in this case an entire set of categories that is not contained in the categories 
		 * attribute).
		 */
		@Test(expected= IllegalArgumentException.class)
		public void setCurrentCategoriesIllegal0() {
			//Sets up a testing set of 20 categories not contained in the categories attribute
			Set<Category> testCurrentCategories = new HashSet<Category>();
			for (int i = 0; i < 20; i++) {
				Integer newId = Idgenerator.getFreeCategoryID();
				testCurrentCategories.add(new Category(newId, "test" + newId));
			}
			testModel.setCurrentCategories(testCurrentCategories);
		}
		
		/**
		 * Tests if CategoryModel correctly handles illegal "set"-operation of currentCategories 
		 * attribute (in this case a set of categories that is only partially contained in the categories 
		 * attribute). 
		 */
		@Test(expected= IllegalArgumentException.class)
		public void setCurrentCategoriesIllegal1() {
			//Sets up a testing set of categories partially contained in the categories attribute
			Set<Category> testCurrentCategories = new HashSet<Category>(testingCategories);
			for (int i = 0; i < 10; i++) {
				System.out.println(i);
				Integer newId = Idgenerator.getFreeCategoryID();
				testCurrentCategories.add(new Category(newId, "test" + newId));
			}
			testModel.setCurrentCategories(testCurrentCategories);
		}
		}
