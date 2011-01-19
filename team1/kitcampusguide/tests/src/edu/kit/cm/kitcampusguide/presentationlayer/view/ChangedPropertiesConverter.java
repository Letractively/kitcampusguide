package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapModel.MapProperty;

/**
 * Converts a set of {@link MapProperty} objects into a JSON formatted
 * <code>String</code>. The conversion of a <code>String</code> into an
 * appropriate set is not supported.
 * 
 * @author Stefan
 * @version 1.0
 * @see http://www.json.org
 */
public class ChangedPropertiesConverter implements Converter {

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
		return JSONConversionHelper.convertChangedProperties((Set<MapProperty>) arg2)
				.toJSONString();
	}

}
