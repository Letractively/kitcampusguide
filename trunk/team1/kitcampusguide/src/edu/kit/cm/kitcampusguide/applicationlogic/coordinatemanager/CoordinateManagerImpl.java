package edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Implements the interface {@link CoordinateManager}.
 * Implements a simple coordinate manager which will simply format a given
 * position by separating the position's coordinates with a comma.
 * 
 * @author Team1
 * 
 */
public class CoordinateManagerImpl implements CoordinateManager {
	
	private static final String numberPattern = "##0.000000";
	private static final String separator = ", ";
	private NumberFormat formatter = NumberFormat.getInstance(new Locale("en", "US"));
	private static Logger logger = Logger.getLogger(CoordinateManagerImpl.class);
	
	/**
	 * The singleton instance of <code>CoordinateManager</code>.
	 */
	private static CoordinateManager instance;
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private CoordinateManagerImpl() {
		
	}
	
	/**
	 * Returns the single instance of this <code>CoordinateManagerImpl</code>.
	 * 
	 * @return Returns the single instance of this 
	 * 		<code>CoordinateManagerImpl</code>.
	 */
	public static CoordinateManager getInstance() {
		if (instance == null) {
			logger.info("CoordinateManager initialized");
			instance = new CoordinateManagerImpl();
		}
		return instance;
	}
	
	/**
	 *
	 * @param position String that is meant to be converted into an instance of the 
	 * 		class {@link WorldPosition}.
	 * 		<code>position</code> has to declare the geographical latitude first,
	 * 		followed by the geographical longitude.
	 * 		Consider that the geographical latitude must range between -90 and 90 and the
	 * 		geographical longitude between -180 and 180.
	 * 		Both numbers must be valid FloatingPointLiterals as defined in §3.10.2 
	 * 		of the Java Language Specification and may be rounded to an appropriate exactness.
	 * 		A minus sign is allowed in front of each number to declare it negative.
	 * 		They must be separated by a comma.		
	 * @return Returns an instance of the class {@link WorldPosition} with the geographical
	 * 		coordinates represented by <code>position</code> or <code>null</code>,
	 * 		if <code>position</code> cannot be interpreted as geographical coordinates.
	 * @throws NullPointerException if <code>position</code> is null.	  
	 */
	@Override
	public WorldPosition stringToCoordinate(String position) throws NullPointerException {
		String[] coordinates = position.split("[,]");
		if (coordinates.length != 2) {
			return null;
		} else {
			coordinates[0] = coordinates[0].trim();
			coordinates[1] = coordinates[1].trim();
			double latitude;
			double longitude;
			try {
				latitude = round(Double.parseDouble(coordinates[0]));
				longitude = round(Double.parseDouble(coordinates[1]));				
			} catch (NumberFormatException e) {
				// The string doesn't encode a valid number, return null
				logger.debug("No valid coordinate: no valid numbers");
				return null;
			}
			if (WorldPosition.checkBounds(latitude, longitude)) {
				logger.debug("Coordinate decoded successfully");
				return new WorldPosition(latitude, longitude);
			} else {
				// The coordinates are valid double values but do
				// not describe a valid position
				logger.debug("No valid coordinate: values out of range");
				return null;
			}
		}
	}
	
	/**
	 * Rounds a given floating-point number according to the stated <code>numberPattern</code>
	 * and returns it.
	 * @param d Double that shall be rounded. Not null.
	 * @return Rounds the given double according to the stated <code>numberPattern</code>
	 * 		and returns it.
	 */
	private double round(double d) {
		if (formatter instanceof DecimalFormat) {
		     ((DecimalFormat) formatter).applyPattern(numberPattern);
		}
		return Double.valueOf(formatter.format(d));
	}

	/**
	 * @return Returns a string representation of the geographical coordinates of the 
	 * 		WorldPosition <code>position</code>. 
	 * 		The first number in this <code>String</code> will be the geographical latitude, the second
	 * 		the geographical longitude.
	 * @throws NullPointerException if <code>position</code> is null
	 */
	@Override
	public String coordinateToString(WorldPosition position) throws NullPointerException {
		if (formatter instanceof DecimalFormat) {
		     ((DecimalFormat) formatter).applyPattern(numberPattern);
		}
		String latitude = formatter.format(position.getLatitude());		
		String longitude = formatter.format(position.getLongitude());
		logger.debug("Coordinate translated successfully");
		return (latitude + separator + longitude);
	}

}
