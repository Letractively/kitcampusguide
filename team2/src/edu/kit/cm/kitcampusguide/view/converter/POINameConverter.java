package edu.kit.cm.kitcampusguide.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.POI;

public class POINameConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		if (arg0 == null || arg1 == null) {
			throw new NullPointerException();
		}
		if (arg2 == null || arg2.isEmpty()) {
			return null;
		}
		POILoader pl = new ConcretePOILoader();
		for (POI p : pl.getAllPOIs()) {
			if (p.getName().equals(arg2)) {
				return p;
			}
		}
		return null;
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
		if (!(arg2 instanceof POI)) {
			throw new ConverterException();
		}
		return ((POI) arg2).getName();
	}

}
