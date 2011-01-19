package edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager;

import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * This class facilitates conversion from strings representing geographical coordinates
 * into instances of the class {@link WordPosition} and vice versa.
 *
 */
public interface CoordinateManager {

	/**
	 * Interprets the given string <code>position</code> as geographical coordinates,
	 * converts it into an instance of the class {@link WordPosition} and returns the latter.
	 * If <code>position</code> cannot be interpreted as geographical coordinates,
	 * <code>null</code> will be returned.
	 * 
	 * @param position String that is meant to be converted into an instance of the 
	 * 		class {@link WordPosition}.
	 * 		<code>position</code> has to declare the geographical latitude first,
	 * 		followed by the geographical longitude.
	 * 		Consider that the geographical latitude must range between -90 and 90 and the
	 * 		geographical longitude between -180 and 180.
	 * 		Both numbers must be valid FloatingPointLiterals as defined in §3.10.2 
	 * 		of the Java Language Specification and may be rounded to an appropriate exactness.
	 * 		A minus sign is allowed in front of each number to declare it negative.
	 * 		They must be separated by a comma.		
	 * @return Returns an instance of the class {@link WordPosition} with the geographical
	 * 		coordinates represented by <code>position</code> or <code>null</code>,
	 * 		if <code>position</code> cannot be interpreted as geographical coordinates.
	 */
	public WorldPosition stringToCoordinate(String position);
	
	
	/**
	 * Returns a string representation of the geographical coordinates of the WorldPosition
	 * <code>position</code>.
	 * 
	 * @param position WorldPosition that is meant to be converted into a string.
	 * @return Returns a string representation of the geographical coordinates of the 
	 * 		WorldPosition <code>position</code>. 
	 * 		The first number in this string will be the geographical latitude, the second
	 * 		the geographical longitude.
	 */
	public String coordinateToString(WorldPosition position);
	
}
