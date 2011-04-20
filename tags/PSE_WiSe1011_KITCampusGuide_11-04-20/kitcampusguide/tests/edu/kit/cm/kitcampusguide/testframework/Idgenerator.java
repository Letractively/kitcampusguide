package edu.kit.cm.kitcampusguide.testframework;

import java.util.HashSet;



/**
 * Utility class to provide a framework so Object-IDs used in testing are unique.
 * Please note that you always use this class to create an ID in order to minimize side effects
 * and that this class only provides unique IDs if you always use it to create the IDs.
 * Also this class does not use any methods, that are subject to testing.
 * 
 * @author Fabian
 *
 */
public final class Idgenerator {
	
	private static int freeMapID = 4; //XXX Auf 4 gesetzt, damit POIDBTestFramework funktioniert.
	private static int freeBuildingID = 2; //XXX Auf 2 gesetzt, damit POIDBTestFramework funktioniert.
	private static int freeCategoryID = 0;
	private static Integer freePOIID = Integer.MIN_VALUE;
	private static HashSet<Integer> takenMapIDs = new HashSet<Integer>();
	private static HashSet<Integer> takenBuildingIDs = new HashSet<Integer>();
	private static HashSet<Integer> takenCategoryIDs = new HashSet<Integer>();
	private static HashSet<String> takenPOIIds = new HashSet<String>();
	
	/**
	 * Prevents instantiation.
	 */
	private Idgenerator() {};

	/**
	 * Returns a free Map ID starting with <code>Integer.MIN_Value</code>.
	 * @return the free Map ID
	 */
	public static int getFreeMapID() {
		int result;
		do {
			result = freeMapID++;
		} while (takenMapIDs.contains(result));
		takenMapIDs.add(result);
		return result;
	}
	
	/**
	 * Returns a free Building ID starting with <code>Integer.MIN_Value</code>.
	 * @return the free Building ID
	 */
	public static int getFreeBuildingID() {
		int result;
		do {
			result = freeBuildingID++;
		} while (takenBuildingIDs.contains(result));
		takenBuildingIDs.add(result);
		return result;
	}
	
	/**
	 * Returns a free Category ID starting with <code>Integer.MIN_Value</code>.
	 * @return the free Category ID
	 */
	public static int getFreeCategoryID() {
		int result;
		do {
			result= freeCategoryID++;
		} while (takenCategoryIDs.contains(result));
		takenCategoryIDs.add(result);
		return result;
	}
	
	/**
	 * Returns a free POI ID starting with <code>Integer.MIN_Value</code> cast to a String.
	 * @return the free POI ID
	 */
	public static String getFreePOIID() {
		String result;
		do {
			result = (freePOIID++).toString();
		} while ((takenPOIIds.contains(result) || (result == "1") || (result == "2") 
				|| (result == "3") || (result == "4")));
		takenPOIIds.add(result);
		return result;
	}
	
	/**
	 * Checks if <code>id</code> is free or not. If the id is free, it will be marked as taken.
	 * So if the first call with a specific id returns <code>true</code> any subsequent call will be <code>false</code>.
	 * @param id the ID to be checked.
	 * @return True if the ID is free, false otherwise.
	 */
	public static boolean requestMapID(int id) {
		return takenMapIDs.add(id);
	}
	
	/**
	 * Checks if <code>id</code> is free or not. If the id is free, it will be marked as taken.
	 * So if the first call with a specific id returns <code>true</code> any subsequent call will be <code>false</code>.
	 * @param id the ID to be checked.
	 * @return True if the ID is free, false otherwise.
	 */
	public static boolean requestBuildingID(int id) {
		return takenBuildingIDs.add(id);
	}
	
	/**
	 * Checks if <code>id</code> is free or not. If the id is free, it will be marked as taken.
	 * So if the first call with a specific id returns <code>true</code> any subsequent call will be <code>false</code>.
	 * @param id the ID to be checked.
	 * @return True if the ID is free, false otherwise.
	 */
	public static boolean requestCategoryID(int id) {
		return takenCategoryIDs.add(id);
	}
	
	/**
	 * Checks if <code>id</code> is free or not. If the id is free, it will be marked as taken.
	 * So if the first call with a specific id returns <code>true</code> any subsequent call will be <code>false</code>.
	 * @param id the ID to be checked.
	 * @return True if the ID is free, false otherwise.
	 */
	public static boolean requestPOIID(String id) {
		return takenPOIIds.add(id);
	}
}
