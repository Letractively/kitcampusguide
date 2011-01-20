package edu.kit.cm.kitcampusguide.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.kit.cm.kitcampusguide.standardtypes.*;

/**
 * Initializes and manages the default values.
 * @author Fred
 *
 */
public class DefaultModelValues {
	/**The default map.*/
	private static Map defaultMap;
	
	/**
	 * Constructs a new DefaultModelValues class. Note that all DefaultModelValues classes are identical in their functions.
	 */
	public DefaultModelValues() {
		
	}
	
	/**
	 * Sets the default values of all DefaultModelValues classes to those defined in the document given by inputStream.
	 * @param inputStream The xml-document defining the default values.
	 * @throws InitializationException If an error occurred during initialization.
	 */
	public static void setDefaultValues(InputStream inputStream) throws InitializationException {
		try {
			Document document;
			document = new SAXBuilder().build(inputStream);
			defaultMap = Map.getMapByID(Integer.getInteger(document.getRootElement().getChild("defaultMap").getAttributeValue("ID")));
		} catch (JDOMException e) {
			throw new InitializationException("Initialization of default values failed.", e);
		} catch (IOException e) {
			throw new InitializationException("Initialization of default values failed.", e);
		}
		
	}
	
	/**
	 * Returns the default map for the view.
	 * @return The default map for the view.
	 */
	public Map getDefaultMap() {
		return defaultMap;
	}
}
