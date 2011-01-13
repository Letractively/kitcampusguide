package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Converts a {@link Route} into a JSON formatted <code>String</code>. The
 * conversion of an appropriate String into a <code>Route</code> is not
 * supported.
 * 
 * @author Stefan
 * @version 1.0
 * @see http://www.json.org
 */
public class RouteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		throw new ConverterException(new UnsupportedOperationException());
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if (arg0 == null || arg1 == null) {
			throw new NullPointerException();
		}
		if (arg2 == null) {
			return "";
		}
		if (!(arg2 instanceof Route)) 
		{
			throw new ConverterException("Can only convert Routes (class: " + arg2.getClass().getName()
					+ ", value: " + arg2 + ")");
		}
		JSONObject result = new JSONObject();
		JSONArray waypoints = new JSONArray();
		result.put("waypoints", waypoints);
		for (MapPosition pos: ((Route)arg2).getWaypoints()) {
			waypoints.add(JSONConversionHelper.convertMapPosition(pos));
		}
		return result.toJSONString();
	}

}
