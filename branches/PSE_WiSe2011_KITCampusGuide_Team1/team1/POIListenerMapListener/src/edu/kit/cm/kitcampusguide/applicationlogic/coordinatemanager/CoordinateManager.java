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
	 * 		Both numbers must consist of exactly six decimal places and between 
	 * 		one and three integer parts. 
	 * 		They have to be separated by a comma followed by a space character.
	 * 		In short: <code>position</code> must be of the form "##0.000000, ##0.000000",
	 * 		in which a minus sign is allowed in front of each number to declare it negative.
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
	 * 		The string will be of the form "##0.000000, ##0.000000", in which a minus sign
	 * 		may be placed in front of each number to declare it negative.
	 */
	public String coordinateToString(WorldPosition position);
	
}
