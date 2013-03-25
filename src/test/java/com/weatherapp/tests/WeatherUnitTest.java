package com.weatherapp.tests;

import com.google.gson.Gson;
import com.weatherapp.controllers.WeatherController;
import com.weatherapp.models.Weather;
import com.weatherapp.services.WeatherService;
import com.weatherapp.services.exceptions.WeatherServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import static com.weatherapp.services.exceptions.ZipErrorMessages.*;
import static com.weatherapp.utils.Constants.DATA_KEY;
import static com.weatherapp.utils.Constants.ERROR_KEY;
import static junit.framework.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/WeatherUnitTest-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class WeatherUnitTest {

    @Autowired
    private WeatherController weatherController;

    @Autowired
    private WeatherService weatherService;

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetch(String zipCode) {
        try {
            String url = weatherController.getClass().getAnnotation(RequestMapping.class).value()[0];
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.weatherController).build();
            ResultActions rs = mockMvc.perform(post(url).param("zip", zipCode));
            return new Gson().fromJson(rs.andReturn().getResponse().getContentAsString(), HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBlankZip() {
        Map<String, Object> map = fetch("");
        assertTrue(map.containsKey(ERROR_KEY));
        assertTrue(!map.containsKey(DATA_KEY));
        assertEquals(map.get(ERROR_KEY), BLANK_ZIP);
    }

    @Test
    public void testInvalidFormatZip() {
        Map<String, Object> map = fetch("Test1");
        assertTrue(map.containsKey(ERROR_KEY));
        assertTrue(!map.containsKey(DATA_KEY));
        assertEquals(map.get(ERROR_KEY), INVALID_ZIP_FORMAT);
    }

    @Test
    public void testIncompleteZip() {
        Map<String, Object> map = fetch("1234");
        assertTrue(map.containsKey(ERROR_KEY));
        assertTrue(!map.containsKey(DATA_KEY));
        assertEquals(map.get(ERROR_KEY), INVALID_ZIP);
    }

    @Test
    public void testNegativeZip() {
        Map<String, Object> map = fetch("-1234");
        assertTrue(map.containsKey(ERROR_KEY));
        assertTrue(!map.containsKey(DATA_KEY));
        assertEquals(map.get(ERROR_KEY), INVALID_ZIP);
    }

    @Test
    public void testZipNotFound() {
        Map<String, Object> map = fetch("00000");
        assertTrue(map.containsKey(ERROR_KEY));
        assertTrue(!map.containsKey(DATA_KEY));
        assertEquals(map.get(ERROR_KEY), ZIP_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testValidZip() {
        Map<String, Object> map = fetch("94538");
        assertTrue(!map.containsKey(ERROR_KEY));
        assertTrue(map.containsKey(DATA_KEY));
        Map<String, Object> data = (Map<String, Object>) map.get(DATA_KEY);
        assertEquals(data.get("city"), "Fremont");
        assertEquals(((Number) data.get("zip")).intValue(), 94538);
        assertTrue(((Number) data.get("temperatureFahrenheit")).doubleValue() > 0);
    }

    @Test
    public void testWeatherServiceReturnsValidZip() {
        try {
            Weather w = weatherService.getWeatherData(94538);
            assertEquals(w.getZip(), 94538);
            assertEquals(w.getCity(), "Fremont");
            assertEquals(w.getState(), "California");
            assertTrue(w.getTemperatureFahrenheit() > 0);
        } catch (WeatherServiceException e) {
            throw new AssertionError("Test Failed");
        }
    }

    @Test
    public void testWeatherServiceFailsForInvalidZip() {
        try {
            weatherService.getWeatherData(-94538);
            throw new AssertionError("Test Failed. Code should never reach here.");
        } catch (WeatherServiceException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testWeatherServiceReturnsZipNotFound() {
        try {
            weatherService.getWeatherData(0);
        } catch (WeatherServiceException e) {
            assertEquals(e.getMessage(), ZIP_NOT_FOUND);
        }
    }
}
