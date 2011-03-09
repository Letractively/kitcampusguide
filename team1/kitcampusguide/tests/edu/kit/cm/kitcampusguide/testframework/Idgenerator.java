package edu.kit.cm.kitcampusguide.testframework;


/**
 * Utility class to provide a framework so Object-IDs used in testing are unique.
 * Please note that you always use this class to create an ID in order to minimize side effects
 * and that this class only provides unique IDs if you always use it to create the IDs.
 * Also this class does not use any methods, that are subject to testing.
 * 
 * @author Fabian
 *
 */
public class Idgenerator {
	
	private static int freeMapID = Integer.MIN_VALUE;
	private static int freeBuildingID = Integer.MIN_VALUE;
	private static int freeCategoryID = Integer.MIN_VALUE;
	private static int freePOIID = Integer.MIN_VALUE;
	
	private Idgenerator() {};

	/**
	 * Returns a free Map ID starting with <code>Integer.MIN_Value</code>.
	 * @return the free Map ID
	 */
	public static int getFreeMapID() {
		int result = freeMapID++;
		return result;
	}
	
	/**
	 * Returns a free Building ID starting with <code>Integer.MIN_Value</code>.
	 * @return the free Building ID
	 */
	public static int getFreeBuildingID() {
		int result = freeBuildingID++;
		return result;
	}
	
	/**
	 * Returns a free Category ID starting with <code>Integer.MIN_Value</code>.
	 * @return the free Category ID
	 */
	public static int getFreeCategoryID() {
		int result = freeCategoryID++;
		return result;
	}
	
	/**
	 * Returns a free POI ID starting with <code>Integer.MIN_Value</code> cast to a String.
	 * @return the free POI ID
	 */
	public static String getFreePOIID() {
		Integer result = freePOIID++;
		return result.toString();
	}
}
