package edu.kit.cm.kitcampusguide.ws.poi.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.tm.cm.kitcampusguide.poiservice.Poi;
import edu.kit.tm.cm.kitcampusguide.poiservice.PoiWithId;
import edu.kit.tm.cm.kitcampusguide.poiservice.Strings;

public class PoiConverter {

	public static POI convertToPojo(Poi poi) {
        POI convertion = new POI();

        convertion.setName(poi.getName());
        convertion.setDescription(poi.getDescription());
        convertion.setLatitude(poi.getLatitude());
        convertion.setLongitude(poi.getLongitude());
        convertion.setPublicly(poi.isPublicly());
        if (poi.getGroupIds() != null) {
            convertion.setGroupIds(poi.getGroupIds().getId());
        } else {
            convertion.setGroupIds(new HashSet<String>());
        }

        return convertion;
    }
	
	public static POI convertToPojo(PoiWithId pwi) {
        POI convertion = new POI();

        convertion.setId(pwi.getUid());
        convertion.setName(pwi.getName());
        convertion.setDescription(pwi.getDescription());
        convertion.setLatitude(pwi.getLatitude());
        convertion.setLongitude(pwi.getLongitude());
        convertion.setPublicly(pwi.isPublicly());
        if (pwi.getGroupIds() == null) {
            convertion.setGroupIds(new HashSet<String>());
        } else {
            convertion.setGroupIds(pwi.getGroupIds().getId());
        }

        return convertion;
    }
	
	public static PoiWithId createPoiWithId (POI poi){
		PoiWithId result = new PoiWithId();
		result.setUid(poi.getId());
		result.setName(poi.getName());
		result.setDescription(poi.getDescription());
        if (poi.getLatitude() == null) {
        	result.setLatitude(0d);
        } else {
        	result.setLatitude(poi.getLatitude());
        }
        if (poi.getLongitude() == null) {
        	result.setLongitude(0d);
        } else {
        	result.setLongitude(poi.getLongitude());
        }
        Strings groupIds = new Strings();
        groupIds.getId().addAll(poi.getGroupIds());
        result.setGroupIds(groupIds);
        result.setPublicly(poi.isPublicly());
        result.setCategoryName(poi.getCategoryName());
        return result;
	}
	
	public static List<PoiWithId> convertPoisForResponse(List<POI> foundPois) {
		List<PoiWithId> pwis = new ArrayList<PoiWithId>();
        for (POI poi : foundPois) {
            pwis.add(PoiConverter.createPoiWithId(poi));
        }
        return pwis;
    }

	public static Poi convertToPoi(PoiWithId obj) {
		Poi poi = new Poi();
		poi.setCategoryName(obj.getCategoryName());
		poi.setDescription(obj.getDescription());
		poi.setGroupIds(obj.getGroupIds());
		poi.setLatitude(obj.getLatitude());
		poi.setLongitude(poi.getLongitude());
		poi.setName(obj.getName());
		poi.setPublicly(obj.isPublicly());
		return poi;
	}
}
