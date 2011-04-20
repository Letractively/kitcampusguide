package edu.kit.cm.kitcampusguide.view.converter;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import edu.kit.cm.kitcampusguide.model.POICategory;

/**
 * This class represents a converter a list of {@link POICategory} due to the Converter technology of
 * the JavaServer Faces framework.
 * @author Haoqian Zheng
 *
 */
public class POICategoryListConverter implements Converter {
	
	/**
	 * {@inheritDoc}
	 * @throws ConverterException at call for its functionality is not supported.
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		throw new ConverterException(new UnsupportedOperationException());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if (arg0 == null || arg1 == null) {
			throw new NullPointerException();
		}
		if (arg2 == null) {
			return "";
		}
		if (!(arg2 instanceof Collection<?>)) {
			throw new ConverterException();
		}
		return JSONConverter.convertPOICategories((Collection<POICategory>) arg2).toJSONString();
	}

}
