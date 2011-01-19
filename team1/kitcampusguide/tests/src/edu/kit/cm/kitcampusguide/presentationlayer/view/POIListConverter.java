package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.beanutils.ConversionException;
import org.json.simple.JSONArray;

import edu.kit.cm.kitcampusguide.standardtypes.POI;

@SuppressWarnings("unchecked")
public class POIListConverter implements Converter {

	@Override
	public List<POI> getAsObject(FacesContext arg0, UIComponent arg1, String str)
			throws ConverterException {
		throw new ConverterException(new UnsupportedOperationException());
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
		if (!(arg2 instanceof Collection<?>)) {
			throw new ConversionException("Can only convert collections of POIs");
		}
		
		Collection<POI> pois = (Collection<POI>) arg2;
		JSONArray list = new JSONArray();
		for (POI p : pois) {
			list.add(JSONConversionHelper.convertPOI(p));
		}
		return list.toString();
	}
}
