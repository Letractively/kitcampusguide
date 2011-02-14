package edu.kit.cm.kitcampusguide;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.mapAlgorithms.RouteCalculatingUtility;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

@ManagedBean
@ApplicationScoped
public class ConstantData {
	private List<String> allPOIName;
	private List<POI> allPOI;

	private List<String> categoriesName;
	private List<POICategory> categories;
	
	private static Graph graph;

	public ConstantData() {
		POILoader pl = new ConcretePOILoader();
		this.categories = pl.getAllPOICategory();
		this.categoriesName = new ArrayList<String>();
		for (POICategory p : categories) {
			this.categoriesName.add(p.getName());
		}		
		this.allPOI = pl.getAllPOIs();
		this.allPOIName = new ArrayList<String>();
		for (POI p : allPOI) {
			this.allPOIName.add(p.getName());
		}
	}

	public List<POI> getAllPOI() {
		return allPOI;
	}
	
	public void setAllPOI(List<POI> allPOI) {
		this.allPOI = allPOI;
	}
	
	public List<String> getCategoriesName() {
		return categoriesName;
	}

	public void setCategoriesName(List<String> categoriesName) {
		this.categoriesName = categoriesName;
	}

	public List<String> getAllPOIName() {
		return allPOIName;
	}

	public void setAllPOIName(List<String> allPOIName) {
		this.allPOIName = allPOIName;
	}

	public static Graph getGraph() {
		if (graph == null) {
			MapLoader ml = new ConcreteMapLoader();
			graph = ml.getGraph();
		}
		return graph;
	}

	public void setCategories(List<POICategory> categories) {
		this.categories = categories;
	}

	public List<POICategory> getCategories() {
		return categories;
	}
	//TODO: entfernen
	public static void main(String[] args) {
		/* ConstantData c = new ConstantData();
		for (POICategory p : c.getCategories()) {
			System.out.println(p.getAllPOI().size());
		}
		System.out.println(c.allPOI.size());
		
		POILoader pl = new ConcretePOILoader();
		POI poi = pl.getPOI(1);
		System.out.println("1 " + poi.getName());
		poi = pl.getPOI(2);
		System.out.println("2 " + poi.getName());
		poi = pl.getPOI(3);
		System.out.println("3 " + poi.getName());
		poi = pl.getPOI(4);
		System.out.println("4 " + poi.getName());
		
		List<POI> poilist = new ArrayList<POI>();
	    poilist = pl.getPOIsByName("saal");
	    System.out.println(poilist.size());
		for (POI p : poilist) {
			System.out.println("POIbyName: " + p.getName());
		} 
		
		List<POICategory> poicatlist = new ArrayList<POICategory>();
	    poicatlist = pl.getPOICategoryByName("e");
	    System.out.println(poicatlist.size());
		for (POICategory p : poicatlist) {
			System.out.println("CatbyName: " + p.getName());
		} 
		
		POICategory poicat = pl.getPOICategory(1);
		System.out.println("1 Cat " + poicat.getName());
		poicat = pl.getPOICategory(2);
		System.out.println("2 Cat " + poicat.getName()); */
		
		MapLoader ml = new ConcreteMapLoader();
		Graph graph = ml.getGraph();
		RouteCalculatingUtility.generateLandmark(graph.getNode(0));
	}
	
}
