package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Creates the JSON representation of a {@link POI}. The conversion from a JSON
 * formatted <code>String</code> to a {@link POI} object is not supported.
 * 
 * @author Stefan
 * @version 1.0
 * @see http://www.json.org
 */
public class POIConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		throw new ConverterException(new UnsupportedOperationException());
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if (arg2 == null) {
			return null;
		}
		if (!(arg2 instanceof POI)) 
		{
			throw new ConverterException("Can only convert POIs (class: " + arg2.getClass().getName()
					+ ", value: " + arg2 + ")");
		}
		return convertPOI((POI) arg2).toString();
	}

	/**
	 * Converts a POI to a JSONObject.
	 * @param p a POI
	 * @return a JSONObject representing the POI
	 */
	@SuppressWarnings("unchecked")
	JSONObject convertPOI(POI p) {
		JSONObject map = new JSONObject();
		map.put("id", p.getMap().getID());
		map.put("name", p.getMap().getName());
		JSONArray categories = new JSONArray();
		for (Category c : p.getCategories()) {
			JSONObject category = new JSONObject();
			category.put("id", c.getID());
			categories.add(category);
		}
		JSONObject position = new JSONObject();
		position.put("longitude", p.getPosition().getLongitude());
		position.put("latitude", p.getPosition().getLatitude());
		JSONObject result = new JSONObject();
		result.put("id", p.getID());
		result.put("name", p.getName());
		result.put("description", p.getDescription());
		result.put("map", map);
		result.put("categories", categories);
		result.put("position", position);
		result.put("building", null); // TODO
		return result;
	}
}
