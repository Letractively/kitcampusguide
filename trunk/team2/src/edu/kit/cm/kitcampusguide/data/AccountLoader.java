package edu.kit.cm.kitcampusguide.data;

/**
 * Interface of a AccountLoader.
 * 
 * @author Michael Hauber
 */
public interface AccountLoader {
	
	/**
	 * Verifies a Name by a given String name, returns true if the given
	 * Name was found in the database, returns false if not.
	 * 
	 * @param name given name to verify.
	 * @return true if the name was found in the database, false if not.
	 */
	public boolean verifyName(String name);
	
	/**
	 * Returns a Password hash of a given name,
	 * returns null if none hash was found.
	 * 
	 * @param name given name to search for hash.
	 * @return password hash of the found name, null if none was found.
	 */
	public String getPasswordHash(String name);
	
}
