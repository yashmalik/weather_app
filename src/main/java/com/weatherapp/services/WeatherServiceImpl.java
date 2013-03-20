package com.weatherapp.services;

import com.google.gson.Gson;
import com.weatherapp.WeatherAppConfiguration;
import com.weatherapp.models.Weather;
import com.weatherapp.services.exceptions.WeatherServiceException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.weatherapp.services.exceptions.ZipErrorMessages.ZIP_NOT_FOUND;
import static com.weatherapp.utils.Constants.ERROR_KEY;
import static com.weatherapp.utils.MapUtils.get;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
@ImportResource("classpath:/properties-config.xml")
public class WeatherServiceImpl implements WeatherService {

    public static final String CURRENT_OBSERVATION = "current_observation";
    public static final String DISPLAY_LOCATION = "display_location";

    @Autowired
    private WeatherAppConfiguration weatherAppConfiguration;

    @Value("#{apiProperties['api.key']}")
    private String apiKey;

    @Value("#{apiProperties['url.format']}")
    private String urlFormat;
    @Override
    public void validateZip(int zip) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public Weather getWeatherData(int zip) throws WeatherServiceException {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(new URI(format(urlFormat, apiKey, zip)));
            HttpResponse response = httpClient.execute(httpGet);
            Map<String, Object> respData = new Gson().fromJson(EntityUtils.toString(response.getEntity()), HashMap.class);

            if (((Map) respData.get("response")).containsKey(ERROR_KEY)) {
                if ("querynotfound".equalsIgnoreCase(get(respData, "response", ERROR_KEY, "type").toString())) {
                    throw new WeatherServiceException(ZIP_NOT_FOUND);
                } else throw new WeatherServiceException("Generic Exception: " + respData.get(ERROR_KEY).toString());
            }

            int returnedZip = parseInt(get(respData, CURRENT_OBSERVATION, DISPLAY_LOCATION, "zip").toString());
            String city = get(respData, CURRENT_OBSERVATION, DISPLAY_LOCATION, "city");
            String state = get(respData, CURRENT_OBSERVATION, DISPLAY_LOCATION, "state_name");
            double tempF = get(respData, CURRENT_OBSERVATION, "temp_f");
            return new Weather(returnedZip, city, state, tempF);
        } catch (Exception e) {
            throw new WeatherServiceException(e.getMessage(), e);
        }
    }
}
