package com.weatherapp.services;

import com.weatherapp.models.Weather;
import com.weatherapp.services.exceptions.WeatherServiceException;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public interface WeatherService {

    public void validateZip(int zip);

    public Weather getWeatherData(int zip) throws WeatherServiceException, WeatherServiceException;

}
