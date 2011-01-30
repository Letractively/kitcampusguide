package edu.kit.cm.kitcampusguide.applicationlogic.routing;

/**
 * Constructs a addressable priority queue for a dijkstra algorithm.
 * 
 * @author Fred
 *
 */
class DijkstraPriorityQueue {
	/** The priority queue*/
	private QueueElement[] elements;
	/** The number of elements contained at the moment*/
	private int elementCount;
	
	private int maxElements;

	/**
	 * Constructs a new priority queue with the maximum capacity max.
	 * @param max
	 */
	public DijkstraPriorityQueue(int max) {
		elementCount = 0;
		maxElements = max;
		elements = new QueueElement[max + 1];
		for (int i = 0; i < max + 1; i++) {
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
		if (elementCount < maxElements) {
			elementCount++;
			elements[elementCount] = new QueueElement(key, value); 
			siftUp(elementCount);
		}
		
	}
	
	/**
	 * Function to repair the heap property.
	 * Runs in O(logn).
	 * @param i
	 */
	private void siftUp(int i) {
		
		if ((i == 1) || (elements[i/2].getValue() < elements[i].getValue())) {
			
		} else {
			swap(i, i/2);
			siftUp(i/2);
		}
	}

	/**
	 * Swaps the elements entries specified by <code>i</code> and <code>j</code>.
	 * Runs in O(1).
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		
		if ((i >= 1) && (i <= elementCount) && (j >= 1) && (j <= elementCount)) {
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
		int result = -1;
		if (elementCount > 0) {
			result = elements[1].getKey();
			elements[1] = elements[elementCount];
			elementCount--;
			siftDown(1);
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
		if (2 * i <= elementCount) {
			if (((2 * i) + 1 > elementCount) || (elements[2 * i].getValue() <= elements[2 * i + 1].getValue())) {
				tmp = 2 * i;
			} else {
				tmp = 2 * i + 1;
			}
			if (elements[i].getValue() > elements[tmp].getValue()) {
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
		for (int i = 1; i < elementCount + 1; i++) {
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
		for (int i = 1; i < elementCount + 1; i++) {
			if (elements[i].getKey() == key) {
				elements[i].setValue(value);
				siftUp(i);
				siftDown(i);
			}
		}
	}


	/**
	 * Defines the elements of a DijkstraPriorityQueue. Consists of a key and a value.
	 * @author Fred
	 *
	 */
	private class QueueElement{
		/**The key of this element*/
		private int key;
		/**The value of this element*/
		private double value;
		
		/**
		 * Constructs a new QueueElement.
		 * @param key The key identifying this element.
		 * @param value The value defining this element.
		 */
		public QueueElement(int key, double value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Returns the key of this element.
		 * @return The key of this element.
		 */
		public int getKey() {
			return key;
		}
		
		/**
		 * Returns the value of this element.
		 * @return The value of this element.
		 */
		public double getValue() {
			return value;
		}
		
		/**
		 * Sets the value of this element.
		 * @param value The value of this element.
		 */
		public void setValue(double value) {
			this.value = value;
		}
	}
}

