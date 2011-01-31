package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DijkstraPriorityQueueTest {

	
	private DijkstraPriorityQueue queue;

	@Before
	public void setUpPriorityQueue() {
		queue = new DijkstraPriorityQueue(8);
		queue.insert(1, 0.5);
		queue.insert(2, 7);
		queue.insert(3, 8.3);
		queue.insert(4, 0.0001);
		queue.insert(5, 110);
		queue.insert(6, 27.83);
		queue.insert(7, 12.1);
		queue.insert(8, 1300.0000001);
	}
	
	
	@Test
	public void testInsertBasic() {
		queue.insert(9, -1);
		Assert.assertFalse("Insert succeded when not expected", queue.contains(9));
		Assert.assertEquals("deleteMin failed wrong order", 4, queue.deleteMin());
	}
	
	@Test
	public void testInsertAdvanced() {
		Assert.assertEquals("deleteMin failed wrong order", 4, queue.deleteMin());
		queue.insert(10, Double.NEGATIVE_INFINITY);
		Assert.assertEquals("deleteMin failed wrong order", 10, queue.deleteMin());
		queue.insert(11, Double.NaN);
		Assert.assertEquals(11, queue.deleteMin());
	}

	@Test
	public void testDeleteMinBasic() {
		Assert.assertEquals(4, queue.deleteMin());
		Assert.assertEquals(1, queue.deleteMin());
		Assert.assertEquals(2, queue.deleteMin());
		Assert.assertEquals(3, queue.deleteMin());
		Assert.assertEquals(7, queue.deleteMin());
		Assert.assertEquals(6, queue.deleteMin());
		Assert.assertEquals(5, queue.deleteMin());
		Assert.assertEquals(8, queue.deleteMin());
		Assert.assertEquals(-1, queue.deleteMin());
	}
	
	@Test
	public void testDeleteMinAdvanced() {
		queue = new DijkstraPriorityQueue(8);
		queue.insert(9, -1);
		queue.insert(-9, -1);
		queue.insert(9, -1);
		Assert.assertEquals(9, queue.deleteMin());
		Assert.assertEquals(-9, queue.deleteMin());
		Assert.assertEquals(9, queue.deleteMin());
	}

	@Test
	public void testIsEmpty() {
		Assert.assertFalse(queue.isEmpty());
		for(int i = 0; i < 8; i++) {
			queue.deleteMin();
		}
		Assert.assertTrue(queue.isEmpty());
		queue.insert(1, 10);
		Assert.assertFalse(queue.isEmpty());
		queue = new DijkstraPriorityQueue(10);
		Assert.assertTrue(queue.isEmpty());
	}

	@Test
	public void testContains() {
		Assert.assertEquals(4, queue.deleteMin());
		Assert.assertTrue(queue.contains(2));
		Assert.assertEquals(1, queue.deleteMin());
		Assert.assertTrue(queue.contains(2));
		Assert.assertEquals(2, queue.deleteMin());
		Assert.assertFalse(queue.contains(2));
		queue.insert(10, -1);
		Assert.assertTrue(queue.contains(10));
		Assert.assertEquals(10, queue.deleteMin());
		Assert.assertFalse(queue.contains(10));
	}

	@Test
	public void testDecreaseKey() {
		queue.decreaseKey(4, 1000);
		Assert.assertEquals(1, queue.deleteMin());
		queue.insert(1, 0.5);
		queue.decreaseKey(4, -1000);
		Assert.assertEquals(4, queue.deleteMin());
	}

}
