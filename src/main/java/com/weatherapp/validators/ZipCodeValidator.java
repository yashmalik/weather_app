package com.weatherapp.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.weatherapp.services.exceptions.ZipErrorMessages.*;
import static java.lang.Integer.parseInt;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZipCodeValidator implements Validator {

    public static final String ZIP_CODE = "zipCode";

    @Override
    public boolean supports(Class<?> aClass) {
        return String.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String zipCode = o.toString();
        if (zipCode == null || zipCode.length() == 0 || zipCode.trim().length() == 0) {
            errors.reject(ZIP_CODE, BLANK_ZIP);
        } else if (zipCode.length() != 5) {
            errors.reject(ZIP_CODE, INVALID_ZIP);
        } else try {
            int zip = parseInt(zipCode);
            if (zip < 0) {
                errors.reject(ZIP_CODE, INVALID_ZIP);
            }
        } catch (NumberFormatException e) {
            errors.reject(ZIP_CODE, INVALID_ZIP_FORMAT);
        }
    }
}
