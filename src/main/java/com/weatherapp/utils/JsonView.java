package com.weatherapp.utils;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonView {

    public static ModelAndView render(Object model, HttpServletResponse response) {
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        try {
            jsonConverter.write(model, jsonMimeType, new ServletServerHttpResponse(response));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
