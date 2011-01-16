package edu.kit.cm.kitcampusguide.data;

/**
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 *
 */
public interface AccountLoader {
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean verifyName(String name);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	String getPasswordHash(String name);
	
}
