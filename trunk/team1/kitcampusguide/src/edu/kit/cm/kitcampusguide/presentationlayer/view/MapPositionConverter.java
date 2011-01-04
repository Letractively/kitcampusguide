package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;

/**
 * Converts a {@link MapPosition} into a JSON formatted <code>String</code>. The
 * conversion of a <code>String</code> into a {@link MapPosition} is not
 * supported.
 * 
 * @author Stefan
 * @version 1.0
 * @see http://www.json.org
 */
public class MapPositionConverter implements Converter {

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
		if (!(arg2 instanceof MapPosition)) {
			throw new ConverterException("Can only convert Routess (class: "
					+ arg2.getClass().getName() + ", value: " + arg2 + ")");
		}
		return JSONConversionHelper.convertMapPosition((MapPosition) arg2)
				.toJSONString();
	}

}
