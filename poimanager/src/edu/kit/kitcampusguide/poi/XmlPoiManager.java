package edu.kit.kitcampusguide.poi;

import java.util.List;

import edu.kit.kitcampusguide.poi.dataaccess.DomWriter;
import edu.kit.kitcampusguide.poi.dataaccess.SaxReader;
import edu.kit.kitcampusguide.poi.model.POI;

public class XmlPoiManager {
	
	private SaxReader reader;
	private DomWriter writer;
	
	private SaxReader getReader() {
		if (reader == null){
			reader = new SaxReader();
		}
		return reader;
	}

	private DomWriter getWriter() {
		if (writer == null){
			writer = new DomWriter();
		}
		return writer;
	}	

	public List<POI> getAllPOIs(String url) {
		return getReader().readAllPOIs(url);
	}

	public boolean writeToDisk(List<POI> poiList, String path) {
		return getWriter().write(poiList, path);
	}
}
