package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import edu.kit.cm.kitcampusguide.standardtypes.Route;

/**
 * Converts a {@link Route} into a JSON formatted <code>String</code>. The convertion
 * of an appropriate String into a <code>Route</code> is not supported.
 * @author Stefan
 * @version 1.0
 * @see http://www.json.org
 */
public class RouteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

}
