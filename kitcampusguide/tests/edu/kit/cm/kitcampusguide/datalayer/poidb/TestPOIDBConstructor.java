package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * This is a test class for easier construction of a test database of pois.
 * @author Fred
 *
 */
public class TestPOIDBConstructor {
	
	private static final Logger logger = Logger
			.getLogger("TestPOIDBConstructor");

	/**
	 * Constructs the maps, categories and POIs.
	 * @param args -
	 * @throws JDOMException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws JDOMException, IOException, ClassNotFoundException, SQLException {
		BasicConfigurator.configure();
		logger.info("Beginning");
		String filename = "./WebContent/resources/testPOIsForDB.xml";
		String filenameMaps = "./WebContent/resources/config/Maps.xml";
		String filenameCategories = "./WebContent/resources/config/Categories.xml";
		
		Document document = new SAXBuilder().build(filename);
		constructMaps(filenameMaps);
		constructCategories(filenameCategories);
		constructPOIDB(document);
		logger.info("Ending");
	}

	/**
	 * Constructs the categories from the file specified by filenameCategories.
	 * @param filenameCategories
	 * @throws JDOMException
	 * @throws IOException
	 */
	private static void constructCategories(String filenameCategories) throws JDOMException, IOException {
		List<Element> listOfCategories = new SAXBuilder().build(filenameCategories).getRootElement().getChildren("category");
		for (Element e : listOfCategories) {
			int id = Integer.parseInt(e.getAttributeValue("id"));
			String name = e.getAttributeValue("name");
			new Category(id, name);
		}
	}

	/**
	 * Constructs the maps from the file specified by filenameMaps.
	 * @param filenameMaps
	 * @throws JDOMException
	 * @throws IOException
	 */
	private static void constructMaps(String filenameMaps) throws JDOMException, IOException {
		List<Element> listOfMaps = new SAXBuilder().build(filenameMaps).getRootElement().getChildren("map");
		for (Element e : listOfMaps) {
			int id = Integer.parseInt(e.getAttributeValue("id"));
			String name = e.getAttributeValue("name");
			WorldPosition NW = new WorldPosition(Double.parseDouble(e.getAttributeValue("boundingNorth")), Double.parseDouble(e.getAttributeValue("boundingWest")));
			WorldPosition SE = new WorldPosition(Double.parseDouble(e.getAttributeValue("boundingSouth")), Double.parseDouble(e.getAttributeValue("boundingEast")));
			MapSection boundingBox = new MapSection(NW, SE);
			String mapFilename = e.getAttributeValue("filename");
			int minZoom = Integer.parseInt(e.getAttributeValue("minZoom"));
			int maxZoom = Integer.parseInt(e.getAttributeValue("maxZoom"));
			new Map(id, name, boundingBox, mapFilename, minZoom, maxZoom);
		}
	}

	/**
	 * Constructs the POIs from the document document.
	 * @param document
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void constructPOIDB(Document document) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		DefaultPOIDB.init("jdbc:sqlite:defaultpoidbtest.db", new SimpleSearch(), true);
		DefaultPOIDB DB = (DefaultPOIDB) DefaultPOIDB.getInstance();
		List<Element> listOfPOIElements = document.getRootElement().getChildren("POI");
		for (Element p : listOfPOIElements) {
			String name = p.getAttributeValue("name");
			String description = null;
			Element descElement = p.getChild("description");
			if (descElement != null) {
				XMLOutputter out = new XMLOutputter(Format.getRawFormat());
				description = out.outputString(descElement.getContent());
			}
			Element pos = p.getChild("position");
			Double latitude = Double.parseDouble(pos.getAttributeValue("latitude"));
			Double longitude = Double.parseDouble(pos.getAttributeValue("longitude"));
			Map map = Map.getMapByID(Integer.parseInt(pos.getAttributeValue("mapID")));
			MapPosition position = new MapPosition(latitude, longitude, map);
			Integer buildingID = null;
			try {
				buildingID = Integer.parseInt(pos.getAttributeValue("buildingID"));
			} catch(Exception e) {
				
			}
			
			Collection<Category> categories = new ArrayList<Category>();
			List<Element> categoryElements = p.getChild("categories").getChildren("category");
			List<Integer> categoryIDs = new ArrayList<Integer>();
			for (Element c : categoryElements) {
				Integer id = Integer.parseInt(c.getAttributeValue("ID"));
				categoryIDs.add(id);
			}
			categories = Category.getCategoriesByIDs(categoryIDs);
			DB.addPOI(name, description, position, buildingID, categories);
		}
	}
}
