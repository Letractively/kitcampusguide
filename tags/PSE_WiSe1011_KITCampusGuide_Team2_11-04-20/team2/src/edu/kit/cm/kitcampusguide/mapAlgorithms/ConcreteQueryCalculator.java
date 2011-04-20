package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.POI;

/**
 * This class is the concrete implementation of the QueryCalculator interface.
 * 
 * @author Tobias Zündorf
 *
 */
public class ConcreteQueryCalculator implements QueryCalculator {
	
	/*
	 * Minimal similarity value that represents a significant correspondence 
	 */
	private static final double SIGNIFICANT_SIMILARITY = 1;
	
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
	public List<POI> getSuggestions(String name) {
		List<POI> poiList = POI_LOADER.getAllPOIs();
		final Map<POI, Double> similarity = new HashMap<POI, Double>();
		for (POI poi : poiList) {
			similarity.put(poi, getSimilarity(name, new String[] {poi.getName(), poi.getDescription()}));
		}
		PriorityQueue<POI> suggestions = new PriorityQueue<POI>(poiList.size(), new Comparator<POI>() {

			@Override
			public int compare(POI poi1, POI poi2) {
				return (int) Math.signum(similarity.get(poi1) - similarity.get(poi2));
			}
			
		});
		for (POI poi : poiList) {
			if (similarity.get(poi) >= SIGNIFICANT_SIMILARITY) {
				suggestions.add(poi);
			}
		}
		return new LinkedList<POI>(suggestions);
	}

	@Override
	public List<String> getSuggestionsNames(String name) {
		List<POI> pois = getSuggestions(name);
		List<String> result = new LinkedList<String>();
		for (POI poi : pois) {
			result.add(poi.getName());
		}
		return result;
	}

	@Override
	public POI searchPOI(String name) {
		List<POI> poiList = POI_LOADER.getAllPOIs();
		POI reply = null;
		double similarity = -Double.MAX_VALUE;
		for (POI poi : poiList) {
			double poiSimilarity = getSimilarity(name, new String[] {poi.getName(), poi.getDescription()});
			if (poiSimilarity > similarity) {
				reply = poi;
				similarity = poiSimilarity;
			}
		}
		return (similarity >= SIGNIFICANT_SIMILARITY) ? reply : null;
	}
	
	/*
	 * Calculates the similarity between the specified request and an the Sequence of texts.
	 * The Returned value is a not negative number, a higher value represents better similarity.
	 * 
	 * @param request the string to search
	 * @param texts the texts to search in
	 * @return the similarity represented by a number between 0 an Double.MAX_VALUE
	 */
	private double getSimilarity(String request, String... texts) {
		double similarity = 0;
		request = request.toLowerCase();
		String[] requests = "x ".concat(request.trim()).split("\\s+");
		requests[0] = request;
		for (String regex : requests) {
			Pattern pattern = Pattern.compile(regex);
			for (String text : texts) {
				Matcher matcher = pattern.matcher(text.toLowerCase());
				while (matcher.find()) {
					similarity += 1;
				}
			}
		}
		return similarity / requests.length;
	}

}
