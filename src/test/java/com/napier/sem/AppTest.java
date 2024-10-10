package com.napier.sem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    static App app;
    static CountryData cd;
    static CityData ct;
    static CapitalData cap;
    static Connection con;

    @BeforeAll
    static void init()
    {
        app = new App();
        con = app.connect();
        cd = new CountryData(con);
        ct = new CityData(con);
        cap = new CapitalData(con);
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

    /**
     * unit tests for cities report
     */
    @Test
    void allCitiesInWorld() {
        String actual = ct.allCitiesInWorld();
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCitiesInContinent() {
        String actual = ct.allCitiesInContinent("Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code AND country.Continent = 'Asia' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void allCitiesInRegion() {
        String actual = ct.allCitiesInRegion("Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code AND country.Region = 'Southeast Asia' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCitiesInCountry() {
        String actual = ct.allCitiesInCountry("Myanmar");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code AND country.Name = 'Myanmar' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCitiesInDistrict() {
        String actual = ct.allCitiesInDistrict("Mandalay");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city,country "
                + "WHERE city.CountryCode = country.Code AND city.District = 'Mandalay' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCitiesTestNullQuery() {
        ct.getAllCities(null);
    }

    @Test
    void getAllCitiesTestEmptyQuery() {
        ct.getAllCities("");
    }

    @Test
    void printCitiesTestNull()
    {
        ct.printCities(null);
    }

    @Test
    void printCitiesTestEmpty()
    {
        ArrayList<City> cities = new ArrayList<>();
        ct.printCities(cities);
    }

    @Test
    void printCitiesTestContainsNull()
    {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        ct.printCities(cities);
    }

    @Test
    void printCities()
    {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setCityName("Kabul");
        city.setCountry("Afghanistan");
        city.setCityPopulation("1780000");
        cities.add(city);
        ct.printCities(cities);
    }


    /**
     * unit tests for capital cities report
     */
    @Test
    void allCapitalCitiesInWorld() {
        String actual = cap.allCapitalCitiesInWorld();
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.Capital = city.ID ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void topPopulatedCapitalCitiesInWorld() {
        String actual = cap.topPopulatedCapitalCitiesInWorld(10);
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.Capital = city.ID ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCapitalCitiesInContinent() {
        String actual = cap.allCapitalCitiesInContinent("Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Asia' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void topPopulatedCapitalCitiesInContinent() {
        String actual = cap.topPopulatedCapitalCitiesInContinent(10,"Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Asia' ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void allCapitalCitiesInRegion() {
        String actual = cap.allCapitalCitiesInRegion("Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Southeast Asia' ORDER BY Population DESC";
        assertEquals(expected, actual);
    }

    @Test
    void topPopulatedCapitalCitiesInRegion() {
        String actual = cap.topPopulatedCapitalCitiesInRegion(10,"Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Southeast Asia' ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCapitalCitiesTestNullQuery() {
        cap.getAllCapitalCities(null);
    }

    @Test
    void getAllCapitalCitiesTestEmptyQuery() {
        cap.getAllCapitalCities("");
    }

    @Test
    void printCapitalCitiesTestNull() {
        cap.printCapitalCities(null);
    }

    @Test
    void printCapitalCitiesTestEmpty() {
        ArrayList<Capital> capitalCities = new ArrayList<>();
        cap.printCapitalCities(capitalCities);
    }

    @Test
    void printCapitalCitiesTestContainsNull() {
        ArrayList<Capital> capitalCities = new ArrayList<>();
        capitalCities.add(null);
        cap.printCapitalCities(capitalCities);
    }

    @Test
    void printCapitalCities() {
        ArrayList<Capital> capitalCities = new ArrayList<>();
        Capital cc = new Capital();
        cc.setCapitalName("Kabul");
        cc.setCountry("Afghanistan");
        cc.setCapitalPopulation("1780000");
        capitalCities.add(cc);
        cap.printCapitalCities(capitalCities);
    }
}
