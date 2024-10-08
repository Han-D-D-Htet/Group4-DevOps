package com.napier.sem;

/**
 * represents a capital city
 */
public class Capital {
    // capital city Name
    private String capitalName;

    // country
    private String country;

    // capital city Population
    private String capitalPopulation;

    // getters
    public String getCapitalName() {
        return capitalName;
    }

    public String getCountry() {
        return country;
    }

    public String getCapitalPopulation() {
        return capitalPopulation;
    }

    // setters
    public void setCapitalName(String capitalName) {this.capitalName = capitalName;}

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCapitalPopulation(String capitalPopulation) {
        this.capitalPopulation = capitalPopulation;
    }

}
