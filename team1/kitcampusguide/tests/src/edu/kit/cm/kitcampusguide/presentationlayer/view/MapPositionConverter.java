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
public class MapPositionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		if (arg0 == null || arg1 == null) {
			throw new NullPointerException();
		}
		if (arg2 == null) {
			return null;
		}
		return JSONConversionHelper.getMapPosition((JSONObject) JSONValue
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
		if (!(arg2 instanceof MapPosition)) {
			throw new ConverterException("Can only convert MapPositions (class: "
					+ arg2.getClass().getName() + ", value: " + arg2 + ")");
		}
		return JSONConversionHelper.convertMapPosition((MapPosition) arg2)
				.toJSONString();
	}

}
