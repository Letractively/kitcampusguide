package edu.kit.cm.kitcampusguide.model;

/**
 * This class implements a addressable, monotonically increasing heap with integer keys. The implementation is a so 
 * called RadixHeap. This class guarantees amortized O(1) running time for each of its methods.
 * 
 * @author Tobias Zündorf
 *
 * @param <T> the type of elements to store in this heap
 */
public class AddressableRadixHeap<T> {
	
	/** stores the maximal difference between the keys of two pairwise different elements within the heap */
	private int maxKeyOffset;

	/** stores the different buckets of the heap */
	private GenericArray<Handle> buckets;

	/** stores the number of buckets used by the heap  */
	private int numberOfBuckets;
	
	/** stores the number of elements actually stored within the heap */
	private int size = 0;
	
	/**
	 * Creates a new RadixHeap with specified maximal key difference.
	 * 
	 * @param maxKeyOffset the maximal key difference for all elements stored in the heap
	 */
	public AddressableRadixHeap(int maxKeyOffset) {
		if (maxKeyOffset <= 0) {
			throw new IllegalArgumentException("maxKeyOffset has to be a positiv number.");
		}
		this.maxKeyOffset = maxKeyOffset;
		this.numberOfBuckets = mostSignificantDifference(maxKeyOffset, 0) + 2;
		this.buckets = new GenericArray<Handle>(numberOfBuckets);
		for (int i = 0; i < numberOfBuckets; i++) {
			this.buckets.set(i, new Handle());
		}
	}
	
	/**
	 * Inserts the given element with specified key into the heap. Returns a Handle, representing the element, which 
	 * can be used to address the element within the heap. 
	 * 
	 * @param element the element to store
	 * @param key the key for the specified element. Key has to be in range of [min, min + maxKeyOffset]
	 * @return a handle representing the inserted element
	 */
	public Handle insert(T element, int key) {
		Handle h = new Handle(element, key);
		this.insert(h);
		this.size++;
		return h;
	}
	
	/**
	 * Returns the handle with minimal key within the heap.
	 * 
	 * @return the handle with minimal key within the heap
	 */
	public Handle min() {
		return this.isEmpty() ? null : this.buckets.get(0).next.getMin();
	}
	
	/**
	 * Deletes and returns the handle with minimal key within the heap. The heap has to contain at least 1 element for 
	 * an successful invocation of this method.
	 * 
	 * @return the handle with minimal key within the heap
	 */
	public Handle deleteMin() {
		if (this.isEmpty()) {
			throw new UnsupportedOperationException("Cannot delete from an Empty list");
		}
		
		Handle min = this.buckets.get(0).detach();
		this.size--;
		if (!this.buckets.get(0).holdsElements() && !this.isEmpty()) {
			int index = 1;
			while (!this.buckets.get(index).holdsElements()) {
				index++;
			}
			this.buckets.get(0).attach(this.buckets.get(index).detachMin());
			while (this.buckets.get(index).holdsElements()) {
				this.insert(this.buckets.get(index).detach());
			}
		}
		
		return min;
	}
	
	/**
	 * Changes the key of the given handle to the specified value.
	 * 
	 * @param h the handle to change its key
	 * @param newKey the new key value for the handle
	 */
	public void decreaseKey(Handle h, int newKey) {
		h.key = newKey;
		this.insert(h.prev.detach());
	}
	
	/**
	 * Returns the number of elements currently saved within the heap.
	 * 
	 * @return the number of elements currently saved within the heap
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns false if this heaps contains at least one element, true otherwise.
	 * 
	 * @return false if this heaps contains at least one element, true otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Inserts the specified handle into the heap.
	 * 
	 * @param h the handle to insert
	 */
	private void insert(Handle h) {
		if (!this.isEmpty() && h.key < this.buckets.get(0).next.getMin().key) {
			throw new IllegalArgumentException("This heap is monotonically increasing, key has to be greater then min");
		}
		if (!this.isEmpty() && h.key - this.buckets.get(0).next.getMin().key > this.maxKeyOffset) {
			throw new IllegalArgumentException("key has to be less then (min + maxKeyOffset)");
		}		
		
		int index = this.isEmpty() ? 0 : Math.min(mostSignificantDifference(h.key, this.buckets.get(0).next.key), this.numberOfBuckets - 1);
		this.buckets.get(index).attach(h);		
	}
	
	/**
	 * Returns the highest index where the bit at this index in number a is different to that one in number b. 
	 * The least significant bit of a number is indexed with 1.
	 * 
	 * @param a the first number
	 * @param b the second number
	 * @return the most significant bit with different values in a and b
	 */
	private static int mostSignificantDifference(int a, int b) {
		return 32 - Integer.numberOfLeadingZeros(a ^ b);
	}
	
	/**
	 * This class represents a pair of element and key within the heap. It is used to address elements within the heap.
	 * 
	 * @author Tobias Zündorf
	 *
	 */
	public class Handle {
		
		/** the key of the represented element */
		private int key;
		
		/** the element to address */
		private T element;
		
		/** the next handle within the same bucket */
		private Handle next;
		
		/** the previous handle within the same bucket */
		private Handle prev;
		
		/** 
		 * creates a new empty dummy handle 
		 */
		private Handle() {
			this(null, -1);
		}
		
		/** 
		 * creates a new handle representing the specified element and key.
		 * 
		 * @param element the element to represent
		 * @param key the key of the represented element
		 */
		private Handle(T element, int key) {
			this.element = element;
			this.key = key;
			this.prev = this;
			this.next = this;
		}
		
		/**
		 * Returns the key of the represented element.
		 * 
		 * @return the key of the represented element
		 */
		public int getKey() {
			return key;
		}

		/**
		 * Returns the represented element.
		 * 
		 * @return the represented element
		 */
		public T getElement() {
			return element;
		}

		/**
		 * Changes the element represented by this handle.
		 * 
		 * @param element the new element
		 */
		public void setElement(T element) {
			this.element = element;
		}
		
		/**
		 * Returns true if at least one element is stored within the bucket of this handle.
		 * 
		 * @return0 true if at least one element is stored within the bucket of this handle
		 */
		private boolean holdsElements() {
			return this.next != this;
		}
		
		/**
		 * Attaches the specified handle to the bucket of this handle.
		 * 
		 * @param h the handle to attach to this handles bucket
		 */
		private void attach(Handle h) {
			h.next = this.next;
			h.prev = this;
			h.prev.next = h;
			h.next.prev = h;
		}
		
		/**
		 * Detaches one Handle of the bucket within this handle.
		 * 
		 * @return a handle of the bucket within this handle
		 */
		private Handle detach() {
			Handle h = this.next;
			this.next = h.next;
			this.next.prev = this;
			return h;
		}
		
		/**
		 * Detaches and returns the handle with minimal key within the bucket of this handle.
		 * 
		 * @return the handle with minimal key within the bucket of this handle
		 */
		private Handle detachMin() {
			return this.next.getMin().prev.detach();
		}
		
		/**
		 * Returns the handle with minimal key within the bucket of this handle.
		 * 
		 * @return the handle with minimal key within the bucket of this handle
		 */
		private Handle getMin() {
			if (this.next.key == -1) {
				return this;
			} else {
				Handle h = this.next.getMin();
				return (h.key < this.key) ? h : this;
			}
		}
		
	}
		
}
