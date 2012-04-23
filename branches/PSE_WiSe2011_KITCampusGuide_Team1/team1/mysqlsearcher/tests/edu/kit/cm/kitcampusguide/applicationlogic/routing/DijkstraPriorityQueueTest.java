package edu.kit.cm.kitcampusguide.applicationlogic.routing;

import static org.junit.Assert.*;

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
		assertFalse("Insert succeded when not expected", queue.contains(9));
		assertEquals("deleteMin failed wrong order", 4, queue.deleteMin());
	}
	
	@Test
	public void testInsertAdvanced() {
		assertEquals("deleteMin failed wrong order", 4, queue.deleteMin());
		queue.insert(10, Double.NEGATIVE_INFINITY);
		assertEquals("deleteMin failed wrong order", 10, queue.deleteMin());
		queue.insert(11, Double.NaN);
		assertEquals(11, queue.deleteMin());
	}

	@Test
	public void testDeleteMinBasic() {
		assertEquals(4, queue.deleteMin());
		assertEquals(1, queue.deleteMin());
		assertEquals(2, queue.deleteMin());
		assertEquals(3, queue.deleteMin());
		assertEquals(7, queue.deleteMin());
		assertEquals(6, queue.deleteMin());
		assertEquals(5, queue.deleteMin());
		assertEquals(8, queue.deleteMin());
		assertEquals(-1, queue.deleteMin());
	}
	
	@Test
	public void testDeleteMinAdvanced() {
		queue = new DijkstraPriorityQueue(8);
		queue.insert(9, -1);
		queue.insert(-9, -1);
		queue.insert(9, -1);
		assertEquals(9, queue.deleteMin());
		assertEquals(-9, queue.deleteMin());
		assertEquals(9, queue.deleteMin());
	}

	@Test
	public void testIsEmpty() {
		assertFalse(queue.isEmpty());
		for(int i = 0; i < 8; i++) {
			queue.deleteMin();
		}
		assertTrue(queue.isEmpty());
		queue.insert(1, 10);
		assertFalse(queue.isEmpty());
		queue = new DijkstraPriorityQueue(10);
		assertTrue(queue.isEmpty());
	}

	@Test
	public void testContains() {
		assertEquals(4, queue.deleteMin());
		assertTrue(queue.contains(2));
		assertEquals(1, queue.deleteMin());
		assertTrue(queue.contains(2));
		assertEquals(2, queue.deleteMin());
		assertFalse(queue.contains(2));
		queue.insert(10, -1);
		assertTrue(queue.contains(10));
		assertEquals(10, queue.deleteMin());
		assertFalse(queue.contains(10));
	}

	@Test
	public void testDecreaseKey() {
		queue.decreaseKey(4, 1000);
		assertEquals(1, queue.deleteMin());
		queue.insert(1, 0.5);
		queue.decreaseKey(4, -1000);
		assertEquals(4, queue.deleteMin());
	}

}
