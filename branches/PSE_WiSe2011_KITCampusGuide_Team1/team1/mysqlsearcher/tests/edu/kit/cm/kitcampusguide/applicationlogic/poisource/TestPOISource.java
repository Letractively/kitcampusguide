package edu.kit.cm.kitcampusguide.applicationlogic.poisource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

@Deprecated
public class TestPOISource implements POISource {
	
	private static Map map1 = new Map(42, "", new MapSection(new WorldPosition(0.0,0.0), new WorldPosition(0.0,0.0)), "", 0,0);
	private static List<Category> list = Collections.emptyList();
	private static POI poi1 = new POI("hallo1", "vorschlag1", "", new WorldPosition(0.0,0.0), 
			map1, new Integer(1), list);
	private static POI poi2 = new POI("hallo2", "vorschlag2", "", new WorldPosition(0.0,0.0), 
			map1, new Integer(1), list);
	private static POI poi3 = new POI("hallo3", "vorschlag3", "", new WorldPosition(0.0,0.0), 
			map1, new Integer(1), list);
	
	
	@Override
	public Collection<POI> getPOIsBySection(MapSection section, Map map,
			Collection<Category> categories) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<POI> getPOIsBySearch(String searchTerm) {
		List<POI> resultList = new ArrayList<POI>();	
		if (searchTerm.equals("testVorschlagsliste")) {		
			resultList.add(poi1);
			resultList.add(poi2);
			resultList.add(poi3);
			return resultList;
		} else if (searchTerm.equals("testVorschlagsliste2")) {
			resultList.add(poi1);
			resultList.add(poi3);
			return resultList;
		} else if (searchTerm.equals("vorschlag1")) {
			resultList.add(poi1);
			return resultList;
		} else if (searchTerm.equals("vorschlag2")) {
			resultList.add(poi2);
			return resultList;
		} else if (searchTerm.equals("vorschlag3")) {
			resultList.add(poi3);
			return resultList;
		} else return null;
	}

	@Override
	public POI getPOIByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<POI> getPOIsByBuilding(Building building,
			Collection<Category> categories) {
		// TODO Auto-generated method stub
		return null;
	}

}
