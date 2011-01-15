package edu.kit.cm.kitcampusguide.applicationlogic.routing;

/**
 * Constructs a addressable priority queue for a dijkstra algorithm.
 * 
 * @author Fred
 *
 */
public class DijkstraPriorityQueue {
	private QueueElement[] elements;
	private int elementCount;

	/**
	 * Constructs a new priority queue with the maximum capacity max.
	 * @param max
	 */
	public DijkstraPriorityQueue(int max) {
		elementCount = 0;
		elements = new QueueElement[max];
		for (int i = 0; i < max; i++) {
			elements[i] = null;
		}
	}
	
	/**
	 * Inserts the element <code>key</code> with the value <code>value</code>.
	 * Runs in O(logn).
	 * @param key The key of the element.
	 * @param value The value of the element.
	 */
	public void insert(int key, double value) {
		if (elementCount < elements.length - 1) {
			elementCount++;
			elements[elementCount - 1] = new QueueElement(key, value); 
			siftUp(elementCount - 1);
		}
		
	}
	
	/**
	 * Function to repair the heap property.
	 * Runs in O(logn).
	 * @param i
	 */
	private void siftUp(int i) {
		
		if ((i == 0) || (elements[(i-1)/2].getValue() < elements[i].getValue())) {
			
		} else {
			swap(i, (i-1)/2);
			siftUp((i-1)/2);
		}
	}

	/**
	 * Swaps the elements entries specified by <code>i</code> and <code>j</code>.
	 * Runs in O(1).
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		
		if ((i >= 0) && (i <= elementCount - 1) && (j >= 0) && (j <= elementCount - 1)) {
			QueueElement tmp = elements[i];
			elements[i] = elements[j];
			elements[j] = tmp;
		}
	}

	/**
	 * Deletes and returns the element with the minimal value.
	 * Runs in O(logn).
	 * @return The element with the minimal value.
	 */
	public int deleteMin() {
		int result = 0;
		if (elementCount > 0) {
			result = elements[0].getKey();
			elements[0] = elements[elementCount - 1];
			elementCount--;
			siftDown(0);
		}
		return result;
	}

	/**
	 * Function to repair the heap.
	 * Runs in O(logn).
	 * @param i
	 */
	private void siftDown(int i) {
		int tmp;
		if (2 * i < elementCount) {
			if ((2 * i + 1 > elementCount - 1) || (elements[2 * i].getKey() <= elements[2 * i + 1].getKey())) {
				tmp = 2 * i;
			} else {
				tmp = 2 * i + 1;
			}
			if (elements[i].getKey() > elements[tmp].getKey()) {
				swap(i, tmp);
				siftDown(tmp);
			}
		}
		
		
	}
	
	/**
	 * Returns true exactly if the elementCount in the data structure is 0.
	 * Runs in O(1).
	 * @return True if elementCount is zero.
	 */
	public boolean isEmpty() {
		boolean result = false;
		if (elementCount == 0) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Returns true exactly if there is a element stored with key <code>key</code>.
	 * Runs in O(n).
	 * @param key The key the element has to have.
	 * @return True if there is a element with <code>key</code>.
	 */
	public boolean contains(int key) {
		boolean result = false;
		for (int i = 0; i < elementCount; i++) {
			if (elements[i].getKey() == key) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Sets the value for the element specified by <code>key</code> to <code>value</code> and repairs the heap.
	 * Runs in O(n).
	 * @param key The key identifying the element.
	 * @param value The new value.
	 */
	public void decreaseKey(int key, double value) {
		for (int i = 0; i < elementCount; i++) {
			if (elements[i].getKey() == key) {
				elements[i].setValue(value);
				siftUp(i);
				siftDown(i);
			}
		}
	}


	private class QueueElement{
		private int key;
		private double value;
		public QueueElement(int key, double value) {
			this.key = key;
			this.value = value;
		}
		
		public int getKey() {
			return key;
		}
		
		public double getValue() {
			return value;
		}
		
		public void setValue(double value) {
			this.value = value;
		}
	}
}

