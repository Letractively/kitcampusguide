package edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

public class CoordinateManagerImpl implements CoordinateManager {
	
	private static final String numberPattern = "##0.000000";
	private static final String separator = ", ";
	private NumberFormat formatter = NumberFormat.getInstance(new Locale("en", "US"));
	
	/**
	 * The singleton instance of <code>CoordinateManager</code>.
	 */
	private static CoordinateManager instance;
	
	/**
	 * Private constructor to prevent instantiation
	 */
	private CoordinateManagerImpl() {
		
	}
	
	/**
	 * Returns the single instance of this <code>CoordinateManagerImplementation</code>.
	 * 
	 * @return Returns the single instance of this 
	 * 		<code>CoordinateManagerImplementation</code>.
	 */
	public static CoordinateManager getInstance() {
		if (instance == null) {
			instance = new CoordinateManagerImpl();
		}
		return instance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public WorldPosition stringToCoordinate(String position) {
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
				System.out.println(e.getMessage());
				return null;
			}
			try {
				return new WorldPosition(latitude, longitude);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}
	
	/**
	 * Rounds a given floating-point number according to the stated <code>numberPattern</code>
	 * and returns it.
	 * @param d Double that shall be rounded.
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
	 * {@inheritDoc}
	 */
	public String coordinateToString(WorldPosition position) {
		if (formatter instanceof DecimalFormat) {
		     ((DecimalFormat) formatter).applyPattern(numberPattern);
		}
		String latitude = formatter.format(position.getLatitude());		
		String longitude = formatter.format(position.getLongitude());
		return (latitude + separator + longitude);
	}

}
