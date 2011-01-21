package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.LinkedList;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.POI;

/**
 * 
 * @author Tobias Zündorf
 *
 */
// TODO Javadoc
public class ConcreteQueryCalculator implements QueryCalculator {
	
	/*
	 * Keeps the only instance of this class.
	 */
	private static ConcreteQueryCalculator singleton;
	
	/*
	 * The PoiLoader implementation used by this class
	 */
	private static final POILoader POI_LOADER = new ConcretePOILoader(); 
	
	/*
	 * Private constructor to ensure only one instantiation of this class.
	 */
	private ConcreteQueryCalculator() {
		assert singleton == null;
	}
	
	/**
	 * Returns the only instantiation of this class. If this is the first invocation
	 * of this method the class is instantiated at first. 
	 * 
	 * @return the only instantiation of this class
	 */
	public static ConcreteQueryCalculator getSingleton() {
		if (singleton == null) {
			singleton = new ConcreteQueryCalculator();
		}
		return singleton;
	}

	@Override
	public LinkedList<POI> getSuggestions(String name) {
		POI_LOADER.getAllPOIs();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POI searchPOI(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
