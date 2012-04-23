package edu.kit.cm.kitcampusguide.presentationlayer.view.converters;

import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertBuilding;
import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertMap;
import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertMapLocator;
import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertMapPosition;
import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertPOI;
import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertPOIList;
import static edu.kit.cm.kitcampusguide.presentationlayer.view.converters.JSONConversionHelper.convertRoute;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel.MapProperty;

/**
 * Converter which converts a {@link MapModel} into a JSON formatted string.
 * Only properties which are marked as "changed" in the given <code>MapModel</code>
 * will be converted. The conversion of a <code>String</code> into a <code>MapModel</code>
 * is not supported.<br />
 * Notice that the <code>changeProperties</code> property is not converted.
 * @author Stefan
 *
 */
public class MapModelConverter implements Converter {

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
		
		MapModel m = (MapModel) arg2;
		JSONObject result = new JSONObject();
		for (MapProperty prop: m.getChangedProperties()) {
			switch (prop) {
			case building:
				result.put(prop.toString(), convertBuilding(m.getBuilding()));
				break;
			case map:
				result.put(prop.toString(), convertMap(m.getMap()));
				break;
			case mapLocator:
				result.put(prop.toString(), convertMapLocator(m.getMapLocator()));
				break;
			case POIs:
				result.put(prop.toString(), convertPOIList(m.getPOIs()));
				break;
			case highlightedPOI:
				result.put(prop.toString(), convertPOI(m.getHighlightedPOI()));
				break;
			case markerFrom:
				result.put(prop.toString(), convertMapPosition(m.getMarkerFrom()));
				break;
			case markerTo:
				result.put(prop.toString(), convertMapPosition(m.getMarkerTo()));
				break;
			case route:
				result.put(prop.toString(), convertRoute(m.getRoute()));
				break;
			case buildingPOI:
				result.put(prop.toString(), convertPOI(m.getBuildingPOI()));
				break;
			case buildingPOIList:
				result.put(prop.toString(), convertPOIList(m.getBuildingPOIList()));
				break;
			}
		}
		return result.toJSONString();
	}
}
