package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;

/**
 * Converts a {@link MapPosition} into a JSON formatted <code>String</code>.
 * 
 * @author Stefan
 * @version 1.0
 * @see http://www.json.org
 */
public class MapLocatorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		if (arg0 == null || arg1 == null) {
			throw new NullPointerException();
		}
		if (arg2 == null || arg2.equals("")) {
			return null;
		}
		return JSONConversionHelper.getMapLocator((JSONObject) JSONValue
				.parse(arg2));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if (arg0 == null || arg1 == null) {
			throw new NullPointerException();
		}
		if (arg2 == null) {
			return "";
		}
		if (!(arg2 instanceof MapLocator)) {
			throw new ConverterException("Can only convert MapLocators (class: "
					+ arg2.getClass().getName() + ", value: " + arg2 + ")");
		}
		return JSONConversionHelper.convertMapLocator((MapLocator) arg2)
				.toJSONString();
	}

}
