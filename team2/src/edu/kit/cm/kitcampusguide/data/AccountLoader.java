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
	public boolean verifyName(String name);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getPasswordHash(String name);
	
}
