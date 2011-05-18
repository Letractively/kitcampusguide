package edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager;

import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * This class defines functions for conversion from <code>String</code>s representing geographical coordinates
 * into instances of the class {@link WorldPosition} and vice versa.
 *
 *@author Team1
 *
 */
public interface CoordinateManager {
	
	/**
	 * Interprets the given <code>String</code> as geographical coordinates,
	 * converts it into an instance of the class {@link WorldPosition} and returns the latter.
	 * 
	 * @param position 
	 * 				<code>String</code> that is meant to be converted. Not <code>null</code>.
	 * @return 
	 * 				Returns the instance of the class {@link WorldPosition} into which <code>position</code>
	 * 				has been converted.
	 * @throws 
	 * 				NullPointerException if <code>position</code> is <code>null</code>.
	 */
	public WorldPosition stringToCoordinate(String position) throws NullPointerException;
	
	
	/**
	 * Returns a string representation of the geographical coordinates of the WorldPosition
	 * <code>position</code>.
	 * 
	 * @param position 
	 * 				{@link WorldPosition} that is meant to be converted into a <code>String</code>.
	 * 				Not <code>null</code>.
	 * @return 
	 * 				Returns a string representation of the geographical coordinates of the 
	 * 				WorldPosition <code>position</code>. 
	 * @throws 
	 * 				NullPointerException if <code>position</code> is null
	 */
	public String coordinateToString(WorldPosition position) throws NullPointerException;
	
}
