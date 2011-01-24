package edu.kit.cm.kitcampusguide;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.POI;

@ManagedBean
@ApplicationScoped
public class ConstantData {
	private String[] allPOI;
	private Graph graph;	

	public ConstantData() {
		POILoader pl = new ConcretePOILoader();
		List<POI> poiList = pl.getAllPOIs();
		this.allPOI = new String[poiList.size()];
		for (int i = 0; i < this.allPOI.length; i++) {
			this.allPOI[i] = poiList.get(i).getName();
		}
	}

	public String[] getAllPOI() {
		return allPOI;
	}

	public void setAllPOI(String[] allPOI) {
		this.allPOI = allPOI;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	//TODO: Testmethode, wieder entfernen
	public static void main(String[] args) {
		ConstantData cd = new ConstantData();
		String[] list = cd.getAllPOI();
		for (String ss : list) {
			System.out.println(ss);
		}
	}
}
