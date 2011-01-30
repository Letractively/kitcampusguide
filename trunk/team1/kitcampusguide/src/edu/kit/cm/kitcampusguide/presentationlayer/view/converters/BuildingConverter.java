package edu.kit.cm.kitcampusguide.presentationlayer.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import edu.kit.cm.kitcampusguide.standardtypes.Building;

// TODO 
public class BuildingConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
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
		if (!(arg2 instanceof Building)) {
			throw new ConverterException("Can only convert buildings (class: "
					+ arg2.getClass().getName() + ", value: " + arg2 + ")");
		}
		return JSONConversionHelper.convertBuilding((Building) arg2)
				.toJSONString();
	}

}
