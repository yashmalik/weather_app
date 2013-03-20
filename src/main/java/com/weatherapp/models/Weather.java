package com.weatherapp.models;

/**
 * Created with IntelliJ IDEA.
 * User: Yash Malik
 * Date: 3/19/13
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Weather {

    private int zip;
    private String city;
    private String state;
    private double temperatureFahrenheit;

    public Weather(int zip) {
        this.zip = zip;
    }

    public Weather(int zip, String city, String state, double temperatureFahrenheit) {
        this.zip = zip;
        this.city = city;
        this.state = state;
        this.temperatureFahrenheit = temperatureFahrenheit;
    }

    public int getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public double getTemperatureFahrenheit() {
        return temperatureFahrenheit;
    }
}
