package com.weatherapp.controllers;

import com.weatherapp.services.WeatherService;
import com.weatherapp.services.exceptions.WeatherServiceException;
import com.weatherapp.validators.ZipCodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.weatherapp.utils.Constants.*;
import static com.weatherapp.utils.MapUtils.serialize;
import static com.weatherapp.utils.Pair.pair;
import static java.lang.Integer.parseInt;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 9:07 AM
 */
@Controller
@RequestMapping("/service")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ZipCodeValidator zipCodeValidator;

    @RequestMapping(method = RequestMethod.POST)
    public String fetchWeather(@ModelAttribute(ZIP_PARAM_NAME) String zipCode, HttpServletResponse response, BindingResult result) throws IOException {
        if (!zipCodeValidator.supports(zipCode.getClass())) {
            throw new IllegalArgumentException("No validators found");
        } else {
            zipCodeValidator.validate(zipCode, result);
        }

        if (result.hasErrors()) {
            response.getWriter().write(serialize(pair(ERROR_KEY, result.getAllErrors().get(0).getDefaultMessage())));
            return null;
        }
        try {
            response.getWriter().write(serialize(pair(DATA_KEY, weatherService.getWeatherData(parseInt(zipCode)))));
        } catch (WeatherServiceException e) {
            response.getWriter().write(serialize(pair(ERROR_KEY, e.getMessage())));
        }
        return null;
    }
}
