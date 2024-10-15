package com.napier.sem;

/**
 * represents a country
 */
public class Country {

    // country code
    private String code;

    // country name
    private String name;

    // country continent
    private String continent;

    // country region
    private String region;

    // country population
    private String population;

    // country capital
    private String capital;

    // getters for country variables
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public String getPopulation() {
        return population;
    }

    public String getCapital() {
        return capital;
    }

    // setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

}
