package edu.kit.cm.kitcampusguide.data;

import java.util.ArrayList;
import java.util.List;

import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Poi;
import edu.kit.cm.kitcampusguide.model.PoiCategory;

/**
 * An instance of this class is created at the start of the containing server due to the 
 * ApplicationScoped ManagedBean technology of JavaServer Faces. 
 * 
 * It stores data that needs to be loaded once from the database.
 * 
 * @author Haoqian Zheng
 */
public class ConstantData {
	
	/**
	 * The list of the names of all POI from the database.
	 */
	private List<String> allPOIName;
	
	/**
	 * The list of all POI from the database.
	 */
	private List<Poi> allPOI;
	
	/**
	 * The list of the names of all POI categories from the database.
	 */
	private List<String> categoriesName;
	
	/**
	 * The list of all POI categories from the database.
	 */
	private List<PoiCategory> categories;
	
	/**
	 * The street graph from the database.
	 */
	private static Graph graph;

	/**
	 * Creates a new ConstantData object.
	 * 
	 * Accesses the database via the data package loading the data from the database.
	 */
	public ConstantData() {
		POILoader pLoader = new ConcretePOILoader();
		this.categories = pLoader.getAllPOICategory();
		this.categoriesName = new ArrayList<String>();
		for (PoiCategory p : categories) {
			this.categoriesName.add(p.getName());
		}		
		this.allPOI = pLoader.getAllPOIs();
		this.allPOIName = new ArrayList<String>();
		for (Poi p : allPOI) {
			this.allPOIName.add(p.getName());
		}
	}

	/**
	 * Returns the street graph.
	 * @return the street graph.
	 */
	public static Graph getGraph() {
		return graph;
	}
	
	/**
	 * Sets the street graph.
	 * @param graph the street graph.
	 */
	public static void setGraph(Graph graph) {
		ConstantData.graph = graph;
	}

	/**
	 * Returns a list of all POI.
	 * @return a list of all POI.
	 */
	public List<Poi> getAllPOI() {
		return allPOI;
	}
	
	/**
	 * Sets the attribute allPOI to a new value.
	 * @param allPOI the new list of all POI.
	 */
	public void setAllPOI(List<Poi> allPOI) {
		this.allPOI = allPOI;
	}
	
	/**
	 * Returns a list of the names of all POI categories.
	 * @return a list of the names of all POI categories.
	 */
	public List<String> getCategoriesName() {
		return categoriesName;
	}

	/**
	 * Sets the attribute categorieName to a new value.
	 * @param categoriesName the new list of the names of all POI categories.
	 */
	public void setCategoriesName(List<String> categoriesName) {
		this.categoriesName = categoriesName;
	}
	
	/**
	 * Returns a list of the names of all POI.
	 * @return a list of the names of all POI.
	 */
	public List<String> getAllPOIName() {
		return allPOIName;
	}

	/**
	 * Sets the attribute allPOIName.
	 * @param allPOIName the new list of the names of all POI.
	 */
	public void setAllPOIName(List<String> allPOIName) {
		this.allPOIName = allPOIName;
	}
	
	
	/**
	 * Sets the attribute categories.
	 * @param categories the new list of all POI categories.
	 */
	public void setCategories(List<PoiCategory> categories) {
		this.categories = categories;
	}
	
	/**
	 * Returns a list of all POI categories.
	 * @return a list of all POI categories.
	 */
	public List<PoiCategory> getCategories() {
		return categories;
	}	
}
