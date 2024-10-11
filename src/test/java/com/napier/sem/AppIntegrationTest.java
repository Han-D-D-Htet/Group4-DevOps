package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;
    static CountryData cd;
    static CityData ct;
    static CapitalData cap;
    static Connection con;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 20000);
        con = app.getCon();
        cd = new CountryData(con);
        ct = new CityData(con);
        cap = new CapitalData(con);
    }

    /**
     * integration tests for countries report
     */
    @Test
    void testGetAllCountriesInWorld() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInWorld());
        Country cty = countries.get(0);
        assertEquals("CHN", cty.getCode() );
        assertEquals("China", cty.getName());
        assertEquals("Asia", cty.getContinent() );
        assertEquals("Eastern Asia", cty.getRegion());
        assertEquals("1,277,558,000", cty.getPopulation());
        assertEquals("Peking", cty.getCapital());
    }

    @Test
    void testTopPopulatedCountriesInWorld() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInWorld(5));
        Country cty = countries.get(2);
        assertEquals("USA", cty.getCode());
        assertEquals("United States", cty.getName());
        assertEquals("North America", cty.getContinent());
        assertEquals("North America", cty.getRegion());
        assertEquals("278,357,000", cty.getPopulation());
        assertEquals("Washington", cty.getCapital());
    }

    @Test
    void testGetAllCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInContinent("Europe"));
        Country cty = countries.get(0);
        assertEquals("RUS", cty.getCode());
        assertEquals("Russian Federation", cty.getName());
        assertEquals("Europe", cty.getContinent());
        assertEquals("Eastern Europe", cty.getRegion());
        assertEquals("146,934,000", cty.getPopulation());
        assertEquals("Moscow", cty.getCapital());
    }

    @Test
    void testTopPopulatedCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInContinent(5,"Asia"));
        assertEquals(5, countries.size());
    }

    @Test
    void testGetAllCountriesInRegion() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInRegion("Southeast Asia"));
        int lastCountry = countries.size()-1;
        Country cty = countries.get(lastCountry);
        assertEquals("BRN", cty.getCode());
        assertEquals("Brunei", cty.getName());
        assertEquals("Asia", cty.getContinent());
        assertEquals("Southeast Asia", cty.getRegion());
        assertEquals("328,000", cty.getPopulation());
        assertEquals("Bandar Seri Begawan", cty.getCapital());
    }

    @Test
    void testTopPopulatedCountriesInRegion() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInRegion(5,"Southeast Asia"));
        Country cty = countries.get(1);
        assertEquals("VNM", cty.getCode());
        assertEquals("Vietnam", cty.getName());
        assertEquals("Asia", cty.getContinent());
        assertEquals("Southeast Asia", cty.getRegion());
        assertEquals("79,832,000", cty.getPopulation());
        assertEquals("Hanoi", cty.getCapital());
    }

    /**
     * integration tests for cities report
     */
    @Test
    void testGetAllCitiesInWorld()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInWorld());
        assertEquals(4079, cities.size());
    }

    @Test
    void testGetAllCitiesInContinent()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInContinent("Asia"));
        City city = cities.get(0);
        assertEquals("Mumbai (Bombay)", city.getCityName());
        assertEquals("India", city.getCountry());
        assertEquals("Maharashtra", city.getDistrict());
        assertEquals("10,500,000", city.getCityPopulation());
    }

    @Test
    void testGetAllCitiesInRegion()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInRegion("Southeast Asia"));
        City city = cities.get(0);
        assertEquals("Jakarta", city.getCityName());
        assertEquals("Indonesia", city.getCountry());
        assertEquals("9,604,900", city.getCityPopulation());
    }

    @Test
    void testGetAllCitiesInCountry()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInCountry("China"));
        assertEquals(363, cities.size());
        assertEquals("Wuhan", cities.get(4).getCityName());
    }

    @Test
    void testGetAllCitiesInDistrict()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInDistrict("Buenos Aires"));
        int lastCity = cities.size()-1;
        City city = cities.get(lastCity);
        assertEquals("Tandil", city.getCityName());
        assertEquals("Buenos Aires", city.getDistrict());
    }

    /**
     * integration tests for populated cities report
     */
    @Test
    void testGetTopPopulatedCitiesInWorld()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInWorld(5));
        assertEquals(5, cities.size());
    }
    @Test
    void testGetTopPopulatedCitiesInContinent()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInContinent(5,"Asia"));
        City city = cities.get(2);
        assertEquals("Shanghai", city.getCityName());
        assertEquals("China", city.getCountry());
        assertEquals("Shanghai", city.getDistrict());
        assertEquals("9,696,300", city.getCityPopulation());
    }

    @Test
    void testGetTopPopulatedCitiesInRegion()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInRegion(5,"Southeast Asia"));
        assertEquals(5, cities.size());
    }

    @Test
    void testGetTopPopulatedCitiesInCountry()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInCountry(10,"China"));
        City city = cities.get(8);
        assertEquals("Chengdu", city.getCityName());
        assertEquals("Sichuan", city.getDistrict());
    }

    @Test
    void testGetTopPopulatedCitiesInDistrict()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInDistrict(5,"Buenos Aires"));
        City city = cities.get(3);
        assertEquals("Almirante Brown", city.getCityName());
        assertEquals("Argentina", city.getCountry());
        assertEquals("Buenos Aires", city.getDistrict());
        assertEquals("538,918", city.getCityPopulation());
    }

    /**
     * integration tests for capital cities report
     */
    @Test
    void testGetAllCapitalCitiesInWorld()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInWorld());
        Capital cc = capitalCities.get(0);
        assertEquals("Seoul", cc.getCapitalName());
        assertEquals("South Korea", cc.getCountry());
    }

    @Test
    void testGetAllCapitalCitiesInContinent()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInContinent("Asia"));
        int lastCapital = capitalCities.size()-1;
        Capital cc = capitalCities.get(lastCapital);
        assertEquals("Bandar Seri Begawan", cc.getCapitalName());
        assertEquals("21,484", cc.getCapitalPopulation());
    }

    @Test
    void testGetAllCapitalCitiesInRegion()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInRegion("Southeast Asia"));
        Capital cc = capitalCities.get(3);
        assertEquals("Rangoon (Yangon)", cc.getCapitalName());
    }

    @Test
    void testGetTopPopulatedCapitalCitiesInWorld()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInWorld(5));
        assertEquals(5, capitalCities.size());
    }

    @Test
    void testGetTopPopulatedCapitalCitiesInContinent()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInContinent(5, "Asia"));
        assertEquals(5, capitalCities.size());
    }

    @Test
    void testGetTopPopulatedCapitalCitiesInRegion()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInRegion("Southeast Asia"));
        Capital cc = capitalCities.get(1);
        assertEquals("Bangkok", cc.getCapitalName());
    }

    /**
     * close database connection after all tests
     */
    @AfterAll
    static void closeConnection() {
        app.disconnect();
    }
}