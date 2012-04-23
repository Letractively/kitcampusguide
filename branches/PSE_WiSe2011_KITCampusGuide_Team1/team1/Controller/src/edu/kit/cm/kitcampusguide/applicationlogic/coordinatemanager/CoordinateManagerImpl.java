package edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

public class CoordinateManagerImpl implements CoordinateManager {
	
	private static final String numberPattern = "##0.000000";
	private static final String numberRegex = "[-]?[0-9]?[0-9]?[0-9][.][0-9][0-9][0-9][0-9][0-9][0-9]";
	private static final String separator = ", ";
	private static final String separatorRegex = "[,][ ]";
	
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
		if (position.matches(numberRegex + separatorRegex + numberRegex)) {
			String[] coordinates = position.split(separatorRegex);
			double latitude = Double.parseDouble(coordinates[0]);
			double longitude = Double.parseDouble(coordinates[1]);
			return new WorldPosition(latitude, longitude);
			//der Test, ob im String semantisch sinnvolle Zahlen übergeben wurden, wird
			//hierbei dem Konstruktor von WorldPosition überlassen
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String coordinateToString(WorldPosition position) {
		NumberFormat formatter = NumberFormat.getInstance(new Locale("en", "US"));
		if (formatter instanceof DecimalFormat) {
		     ((DecimalFormat) formatter).applyPattern(numberPattern);
		}
		String latitude = formatter.format(position.getLatitude());		
		String longitude = formatter.format(position.getLongitude());
		return (latitude + separator + longitude);
	}

}
