package com.weatherapp;

import com.weatherapp.validators.ZipCodeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan("com.weatherapp.*")
public class WeatherAppConfiguration {

    @Bean
    public ZipCodeValidator zipCodeValidator() {
        return new ZipCodeValidator();
    }
}
