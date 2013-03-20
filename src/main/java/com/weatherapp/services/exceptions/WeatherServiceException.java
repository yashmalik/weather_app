package com.weatherapp.services.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherServiceException extends Exception {
    public WeatherServiceException() {
    }

    public WeatherServiceException(String message) {
        super(message);
    }

    public WeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherServiceException(Throwable cause) {
        super(cause);
    }

    public WeatherServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
