package com.napier.sem;

/**
 * represents a city
 */
public class City {

    // city name
    private String cityName;

    // country name
    private String country;

    // district name
    private String district;

    // city population
    private String cityPopulation;

    // getters for city variables
    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public String getDistrict() {
        return district;
    }

    public String getCityPopulation() {
        return cityPopulation;
    }

    // setters
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCityPopulation(String cityPopulation) {
        this.cityPopulation = cityPopulation;
    }
}

