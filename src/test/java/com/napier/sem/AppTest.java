package com.napier.sem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

public class AppTest {
    static App app;
    static CountryData cd;
    static Connection con;

    @BeforeAll
    static void init()
    {
        app = new App();
        con = app.connect();
        cd = new CountryData(con);
    }

    @Test
    void allCountriesInWorld() {
        String actual = cd.allCountriesInWorld();
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital " +
                "FROM country, city WHERE country.capital = city.ID ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void topPopulatedCountriesInWorld() {
        String actual = cd.topPopulatedCountriesInWorld(10);
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID "
                + "ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCountriesInContinent() {
        String actual = cd.allCountriesInContinent("Europe");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Europe' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void topPopulatedCountriesInContinent() {
        String actual = cd.topPopulatedCountriesInContinent(15, "Europe");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Europe' ORDER BY Population DESC LIMIT 15";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCountriesInRegion() {
        String actual = cd.allCountriesInRegion("Caribbean");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Caribbean' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void topPopulatedCountriesInRegion() {
        String actual = cd.topPopulatedCountriesInRegion(20, "Caribbean");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Caribbean' ORDER BY Population DESC LIMIT 20";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCountriesTestNull() {
        cd.getAllCountries(null);
    }

    @Test
    void getAllCountriesTestEmptyQuery() {
        cd.getAllCountries("");
    }

    @Test
    void printCountriesTestNull() {
        cd.printCountries(null);
    }

    @Test
    void printCountriesTestEmpty() {
        ArrayList<Country> countries = new ArrayList<>();
        cd.printCountries(countries);
    }

    @Test
    void printCountriesTestContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        cd.printCountries(countries);
    }

    @Test
    void printCountriesTestValid() {
        ArrayList<Country> countries = new ArrayList<>();
        Country cty = new Country();
        cty.setCode("MMR");
        cty.setName("Myanmar");
        cty.setContinent("Asia");
        cty.setRegion("Southeast Asia");
        cty.setPopulation("45,611,000");
        cty.setCapital("Nay Pyi Taw");
        countries.add(cty);
        cd.printCountries(countries);
    }
}
