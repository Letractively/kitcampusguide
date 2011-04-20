package edu.kit.cm.kitcampusguide.model;

/**
 * This class encapsulates a normal array but with generic type.
 * 
 * @author Tobias Zündorf
 *
 * @param <T> the type of the array
 */
public class GenericArray<T> {
	
	/** the array to save the elements */
	private Object[] array;
	
	/** 
	 * Constructs a new Array with given size.
	 * 
	 * @param size the size of the array
	 */
	public GenericArray(int size) {
		this.array = new Object[size];
	}
	
	/**
	 * Sets the value of the given index to the given element.
	 * 
	 * @param index the index to change
	 * @param element the element to store
	 */
	public void set(int index, T element) {
		this.array[index] = element;
	}
	
	/**
	 * Returns the element stored at the specified index.
	 * 
	 * @param index the index to load
	 * @return the element stored at the specified index
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) (this.array[index]);
	}

}
