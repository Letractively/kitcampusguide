package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import edu.kit.cm.kitcampusguide.standardtypes.MapSection;

public class MapSectionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		if (arg0 == null || arg1 == null || arg2 == null) {
			throw new NullPointerException();
		}
		if (arg2 == "") {
			return null;
		}
		return JSONConversionHelper.getMapSection((JSONObject) JSONValue
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
		if (!(arg2 instanceof MapSection)) {
			throw new ConverterException("Can only convert MapSection (class: "
					+ arg2.getClass().getName() + ", value: " + arg2 + ")");
		}
		return JSONConversionHelper.convertMapSection((MapSection) arg2)
				.toJSONString();
	}
}
