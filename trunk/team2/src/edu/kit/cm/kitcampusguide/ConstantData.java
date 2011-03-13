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
import edu.kit.cm.kitcampusguide.model.Point;

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
}
