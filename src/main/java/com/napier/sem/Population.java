package com.napier.sem;

/**
 * represents population information
 */
public class Population {

    // Name of continent, region or country
    private String popName;

    // total population
    private String totalPopulation;

    // total population living in cities
    private String totalPopulationCities;

    // percentage of total population living in cities
    private String percentageCityPopulation;

    // total population not living in cities
    private String totalPopulationNotCities;

    // percentage of total population not living in cities
    private String percentageNotCityPopulation;

    // getters
    public String getPopName() {
        return popName;
    }

    public String getTotalPopulation() {
        return totalPopulation;
    }

    public String getTotalPopulationCities() {
        return totalPopulationCities;
    }

    public String getPercentageCityPopulation() {
        return percentageCityPopulation;
    }

    public String getTotalPopulationNotCities() {
        return totalPopulationNotCities;
    }

    public String getPercentageNotCityPopulation() {
        return percentageNotCityPopulation;
    }

    // setters
    public void setPopName(String popName) {
        this.popName = popName;
    }

    public void setTotalPopulation(String totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public void setTotalPopulationCities(String totalPopulationCities) {
        this.totalPopulationCities = totalPopulationCities;
    }

    public void setPercentageCityPopulation(String percentageCityPopulation) {
        this.percentageCityPopulation = percentageCityPopulation;
    }

    public void setTotalPopulationNotCities(String TotalPopulationNotCities) {
        this.totalPopulationNotCities = TotalPopulationNotCities;
    }

    public void setPercentageNotCityPopulation(String percentageNotCityPopulation) {
        this.percentageNotCityPopulation = percentageNotCityPopulation;
    }
}
