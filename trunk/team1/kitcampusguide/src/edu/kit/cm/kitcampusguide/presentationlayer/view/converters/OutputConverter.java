package edu.kit.cm.kitcampusguide.presentationlayer.view.converters;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapEvent;

/**
 * Converts a list of {@link MapEvent}s formatted as a JSON string into the
 * actual <code>MapEvent</code> objects. Only the conversion of a string into a
 * list of map events is supported. Exceptions will be thrown if the string does
 * not encode valid <code>MapEvent</code> objects. <br />
 * For each <code>MapEvent</code>, the event's data argument is parsed depending
 * on it's type given by the type argument. Until now, only the type Identifiers
 * "String", "Integer" and "MapPosition" are supported.
 * 
 * @author Stefan
 * 
 */
public class OutputConverter implements Converter {

	@Override
	public List<MapEvent> getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			if(value.isEmpty()) {
				return null;
			}
			JSONArray val = (JSONArray) JSONValue.parseWithException(value);
			List<MapEvent> result = new ArrayList<MapEvent>(val.size());
			for (Object eventObject: val) {
				JSONObject eventJSON = (JSONObject) eventObject;
				String dataType = (String) eventJSON.get("dataType");
				Object data = eventJSON.get("data");
				if ("String".equals(dataType)) {
					if (!(data instanceof String) && data != null) {
						throw new ConverterException("String expected");
					}
				} else if ("Integer".equals(dataType)) {
					if(!(data instanceof Long) && data != null) {
						throw new ConverterException("Integer expected");
					}
					// Cast to Integer
					data = (data == null) ? null : ((Long) data).intValue();
				} else if ("MapLocator".equals(dataType)) {
					data = JSONConversionHelper.getMapLocator((JSONObject) data);
				} else if ("MapPosition".equals(dataType)) {
					data = JSONConversionHelper.getMapPosition((JSONObject) data);
				} else {
					throw new ConverterException("Unknown Type: " + dataType);
				}
				result.add(new MapEvent(component, (String) eventJSON
						.get("type"), data));
			}
			return result;
		} catch (ParseException e) {
			throw new ConverterException("Error while parsing JSON data", e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return "";
	}
}
