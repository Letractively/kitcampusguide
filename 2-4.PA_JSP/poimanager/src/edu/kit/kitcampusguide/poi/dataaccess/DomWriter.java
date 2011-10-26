package edu.kit.kitcampusguide.poi.dataaccess;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.kit.kitcampusguide.poi.model.POI;

public class DomWriter {

	private static final String CATEGORY_ELEMENT_NAME_NS = "kcg:category";
	protected static final String KCG_NS_PREFIX = "kcg";
	protected static final String KCG_NAMESPACE = "http://www.cm-tm.uni-karlsruhe.de/KCG";
	
	protected static final String KCG_POILIST_ELEMENT_NS = "kcg:poiList";
	protected static final String POILIST_ELEMENT_NAME = "poiList";
	protected static final String LONG_ELEMENT_NAME_NS = "kcg:longitude";
	protected static final String LAT_ELEMENT_NAME_NS = "kcg:latitude";
	protected static final String NAME_ELEMENT_NAME_NS = "kcg:name";
	protected static final String ID_ELEMENT_NAME_NS = "id";
	protected static final String POI_ELEMENT_NAME_NS = "kcg:poi";

	protected static final String XSI_SCHEMA_LOCATION = "http://www.cm-tm.uni-karlsruhe.de/KCG POIList.xsd";
	protected static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	protected static final String XSI_NS_PREFIX = "xmlns:xsi";
	protected static final String XSI_SCHEMA_LOCATION_ATTR = "xsi:schemaLocation";

	private Document createNewDocument() {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return document;
	}

	public boolean write(List<POI> poiList, String path) {
		System.out.println("\nDOM writer started - building document...");
		// create the root element
		Document document = createPoiListDocument(poiList);
		// output the XML
		return outputToFile(document, path);
	}

	private Document createPoiListDocument(List<POI> poiList) {
		Document document = createNewDocument();
		if (document != null) {
			document.createElement(POILIST_ELEMENT_NAME);
			// add the name space to the root element
			Element poilist = document.createElementNS(KCG_NAMESPACE,
					KCG_POILIST_ELEMENT_NS);
			// add the XMLSchema-instance name space
			poilist.setAttribute(XSI_NS_PREFIX, XSI_NAMESPACE);
			// add the location of the XML Schema for
			// the namespace http://www.cm-tm.uni-karlsruhe.de/KCG
			poilist.setAttribute(XSI_SCHEMA_LOCATION_ATTR, XSI_SCHEMA_LOCATION);
			// iterate through the list of POIs
			for (POI p : poiList) {
				Element poi = createPoiElement(document, p);
				poilist.appendChild(poi);
			}
			// append the root node to the document
			document.appendChild(poilist);
		}
		return document;
	}

	private Element createPoiElement(Document document, POI p) {
		// create a poi element
		Element poi = document.createElement(POI_ELEMENT_NAME_NS);
		// add an id to the poi element
		poi.setAttribute(ID_ELEMENT_NAME_NS, String.valueOf(p.getId()));
		// create a name element
		Element name = document.createElement(NAME_ELEMENT_NAME_NS);
		name.setTextContent(p.getName());
		poi.appendChild(name);
		// create a category element
        Element category= document.createElement(CATEGORY_ELEMENT_NAME_NS);
        category.setTextContent(p.getCategory());
        poi.appendChild(category);
		// create a latitude element
		Element latitude = document.createElement(LAT_ELEMENT_NAME_NS);
		latitude.setTextContent(String.valueOf(p.getLatitude()));
		// append it to the poi element
		poi.appendChild(latitude);
		// create a longitude element
		Element longitude = document.createElement(LONG_ELEMENT_NAME_NS);
		longitude.setTextContent(String.valueOf(p.getLongitude()));
		// append it to the poi element
		poi.appendChild(longitude);
		// append the poi to the list
		return poi;
	}

	private boolean outputToFile(Document document, String path) {
		if (document == null)
			return false;

		// set up a transformer
		Transformer transformer = createNewTransformer();
		if (transformer == null)
			return false;

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// create a stream to which the document should be written to after
		// transformation
		Result result = new StreamResult(new File(path));
		// a DOMSource wraps the document object before transformation
		DOMSource source = new DOMSource(document);
		// call the transformation, the resulting output will be written
		// automatically to the file
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
			return false;
		}
		// inform the user the document has been succesfully written
		System.out.println("Output finished - XML document written to:" + path);
		return true;

	}

	private Transformer createNewTransformer() {
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformer;
	}
}
