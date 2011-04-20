package model;

import junit.framework.Assert;

import org.junit.Test;

import edu.kit.cm.kitcampusguide.model.AddressableRadixHeap;

/**
 * 
 * @author Monica
 * 
 *         This class tests the AddressableRadixHeap.
 */
public class AddressableRadixHeapTest {

	/*
	 * @BeforeClass public void beforeClass() {
	 * 
	 * }
	 * 
	 * @Before public void before() {
	 * 
	 * }
	 */

	/**
	 * This tests the construction and insertion in the radix heap.
	 */
	@Test
	public void constructAndInsertRadixHeap() {
		AddressableRadixHeap<Integer> radixHeap = new AddressableRadixHeap<Integer>(
				40);
		radixHeap.insert(2, 0);
		Assert.assertEquals(1, radixHeap.size());
		Assert.assertTrue(radixHeap.min().getElement() == 2);

		radixHeap.insert(1, 1);
		Assert.assertEquals(2, radixHeap.size());

		radixHeap.insert(1, 2);
		Assert.assertEquals(3, radixHeap.size());
		Assert.assertTrue(radixHeap.min().getElement() == 2);

		boolean exceptionThrown = false;

		try {
			radixHeap.insert(3, 42);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

	/**
	 * This tests the construction of the radix heap with an negative offset.
	 */
	@Test
	public void constructNegativeOffsetRadixHeap() {
		boolean exceptionThrown = false;
		try {
			new AddressableRadixHeap<Integer>(-1);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

	/**
	 * This tests other methods like min(), deleteMin(), size() and empty() of the 
	 * RadixHeap.
	 */
	@Test
	public void otherOperationsOnRadixHeap() {
		AddressableRadixHeap<Integer> radixHeap = new AddressableRadixHeap<Integer>(
				40);

		radixHeap.insert(2, 0);
		AddressableRadixHeap<Integer>.Handle ha = radixHeap.insert(3, 4);
		radixHeap.insert(1, 2);

		Assert.assertEquals(0, radixHeap.min().getKey());
		Assert.assertEquals(3, radixHeap.size());
		Assert.assertEquals(0, radixHeap.deleteMin().getKey());

		radixHeap.decreaseKey(ha, 2);
		Assert.assertEquals(2, radixHeap.min().getKey());
		radixHeap.deleteMin();
		Assert.assertEquals(2, radixHeap.min().getKey());
		Assert.assertFalse(radixHeap.isEmpty());
		radixHeap.deleteMin();
		Assert.assertTrue(radixHeap.isEmpty());

		boolean exceptionThrown = false;
		try {
			radixHeap.deleteMin();
		} catch (UnsupportedOperationException e) {
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
		radixHeap.insert(3, 4);

		boolean exceptionThrown2 = false;
		try {

			radixHeap.insert(1, 2);
		} catch (IllegalArgumentException e) {
			exceptionThrown2 = true;
		}
		Assert.assertTrue(exceptionThrown2);
	}

	/*
	 * 
	 * 
	 * 
	 * @AfterClass public void afterClass() {
	 * 
	 * }
	 */
}
