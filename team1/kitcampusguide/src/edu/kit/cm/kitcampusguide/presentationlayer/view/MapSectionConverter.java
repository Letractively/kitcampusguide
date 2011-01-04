package edu.kit.cm.kitcampusguide.presentationlayer.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.beanutils.ConversionException;

import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

public class MapSectionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String str)
			throws ConverterException {
		if (str == null) {
			return null;
		}

		// left, bottom, right, top
		String[] split = str.split(",");
		if (split.length != 4) {
			throw new ConversionException("Exactly 4 numbers needed");
		}
		double[] coords = new double[4];
		try {
			for (int i = 0; i < 4; i++) {
				coords[i] = Double.parseDouble(split[i]);
			}
		} catch (NumberFormatException e) {
			throw new ConversionException(e);
		}
		WorldPosition nw = new WorldPosition(coords[0], coords[3]);
		WorldPosition se = new WorldPosition(coords[2], coords[1]);
		return new MapSection(nw, se);
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
		MapSection section = (MapSection) arg2;
		// left, bottom, right, top
		WorldPosition nw = section.getNorthWest();
		WorldPosition se = section.getSouthEast();
		return nw.getLongitude() + "," + se.getLatitude() + ","
				+ se.getLongitude() + "," + nw.getLatitude();
	}
}
