package edu.kit.cm.kitcampusguide.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.kit.cm.kitcampusguide.controller.form.CreatePoiForm;
import edu.kit.cm.kitcampusguide.ws.poi.type.Poi;
import edu.kit.cm.kitcampusguide.ws.poi.type.PoiWithId;

public class PoiValidator implements Validator {

    private static final Logger log = Logger.getLogger(CreatePoiForm.class);

    private final Double DEFAULT_MAX_LONGITUDE = 180d;
    private final Double DEFAULT_MIN_LONGITUDE = -180d;
    private final Double maxLongitude = DEFAULT_MAX_LONGITUDE;
    private final Double minLongitude = DEFAULT_MIN_LONGITUDE;
    private final Double DEFAULT_MAX_LATITUDE = 180d;
    private final Double DEFAULT_MIN_LATITUDE = -180d;
    private final Double maxLatitude = DEFAULT_MAX_LATITUDE;
    private final Double minLatitude = DEFAULT_MIN_LATITUDE;

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.equals(PoiWithId.class) || arg0.equals(Poi.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        log.debug("Processing object(" + obj.toString() + ") in poi validator.");
        if (obj instanceof Poi) {

            validate((Poi) obj, errors);
        } else if (obj instanceof PoiWithId) {

            validate(Poi.convertToPoi((PoiWithId) obj), errors);
        } else {

            errors.reject("error.classNotSupported");
        }
    }

    public void validate(Poi poi, Errors errors) {
        if (poi == null) {

            errors.reject("error.poiNotSpecified");
        } else {

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.nameNotSpecified", "Name required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.descriptionNotSpecified",
                    "Description required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "longitude", "error.longitudeNotSpecified",
                    "Longitude required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "latitude", "error.latitudeNotSpecified",
                    "Latitude required");

            rejectIfLatitudeOutOfRange(errors, poi);
            rejectIfLongitudeOutOfRange(errors, poi);
        }
    }

    private void rejectIfLatitudeOutOfRange(Errors errors, Poi poi) {
        if (poi.getLatitude() > maxLatitude) {

            errors.rejectValue("latitude", "errors.latitudeTooHigh", new Object[] { new Double(maxLatitude) },
                    "Latitude too high");
        } else if (poi.getLatitude() < minLatitude) {

            errors.rejectValue("latitude", "errors.latitudeTooLow", new Object[] { new Double(minLatitude) },
                    "Latitude too low");
        }
    }

    private void rejectIfLongitudeOutOfRange(Errors errors, Poi poi) {
        if (poi.getLongitude() > maxLongitude) {

            errors.rejectValue("longitude", "errors.longitudeTooHigh", new Object[] { new Double(maxLongitude) },
                    "Longitude too high");
        } else if (poi.getLongitude() < minLongitude) {

            errors.rejectValue("longitude", "errors.longitudeTooLow", new Object[] { new Double(minLongitude) },
                    "Longitude too low");
        }
    }
}
