package edu.kit.kitcampusguide.poi.dataaccess;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.kit.kitcampusguide.poi.model.POI;

import static edu.kit.kitcampusguide.poi.dataaccess.DomWriter.*;

public class SaxReader {

	private SAXParserFactory factory;

	private SAXParser parser;

	private List<POI> poiList;

	private POI myPoi;

	private State state;

	private enum State {
		WAIT, NAME, CAT, LAT, LONG;
	}

	public List<POI> readAllPOIs(String url) {
		try {
			// initialize parser
			factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser();
			parser.parse(url, new PoiListHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return poiList;
	}

	private class PoiListHandler extends DefaultHandler {

		@Override
		public void startDocument() throws SAXException {
			System.out.println("SAX parser started - reading document...");
			state = State.WAIT;
			poiList = new LinkedList<POI>();
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			System.out.println(">Element " + qName + " found");
			if (qName.equals(POI_ELEMENT_NAME_NS)) {
				System.out.println("\t Creating new POI...");
				myPoi = new POI();
				myPoi.setId(Integer.parseInt(attributes.getValue("id")));
			}
			if (qName.equals(NAME_ELEMENT_NAME_NS)) {
				state = State.NAME;
			}
			if (qName.equals("kcg:category")) {
                state = State.CAT;
            }
			if (qName.equals(LAT_ELEMENT_NAME_NS)) {
				state = State.LAT;
			}
			if (qName.equals(LONG_ELEMENT_NAME_NS)) {
				state = State.LONG;
			}
			super.startElement(uri, localName, qName, attributes);
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			System.out.println(">>The State is " + state.toString());
			// according to the state we know which characters are read and we
			// can save them accordingly
			switch (state) {
			case NAME:
				myPoi.setName(new String(ch, start, length));
				System.out.println("\t Processing name...");
				break;
			case CAT:
                myPoi.setCategory(new String(ch, start, length));
                System.out.println("\t Processing category...");
                break;
			case LAT:
				myPoi.setLatitude(Float
						.parseFloat(new String(ch, start, length)));
				System.out.println("\t Processing latitude...");
				break;
			case LONG:
				myPoi.setLongitude(Float.parseFloat(new String(ch, start,
						length)));
				System.out.println("\t Processing longitude...");
				break;
			case WAIT:
				// do nothing
				break;
			}
			state = State.WAIT;
			super.characters(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (qName.equals(POI_ELEMENT_NAME_NS)) {
				poiList.add(myPoi);
			}
			super.endElement(uri, localName, qName);
		}

		@Override
		public void endDocument() throws SAXException {
			System.out.println(">Document ended!");
			super.endDocument();
		}
	}
}
