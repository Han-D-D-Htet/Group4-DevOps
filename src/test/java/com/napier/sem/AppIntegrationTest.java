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
    static PopulationData pd;
    static Connection con;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 10000);
        con = app.getCon();
        cd = new CountryData(con);
        ct = new CityData(con);
        cap = new CapitalData(con);
        pd = new PopulationData(con);
    }

    /**
     * test for countries report
     * test for all countries in the world
     */
    @Test
    void testGetAllCountriesInWorld() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInWorld());
        Country cty = countries.get(0);
        assertNotNull(countries, "Countries should not be null");
        assertEquals("CHN", cty.getCode() );
        assertEquals("China", cty.getName());
        assertEquals("Asia", cty.getContinent() );
        assertEquals("Eastern Asia", cty.getRegion());
        assertEquals("1,277,558,000", cty.getPopulation());
        assertEquals("Peking", cty.getCapital());
    }

    /**
     * test for top populated countries in the world
     */
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

    /**
     * test for all countries in a continent
     */
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

    /**
     * test for top populated countries in a continent
     */
    @Test
    void testTopPopulatedCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInContinent(5,"Asia"));
        assertEquals(5, countries.size());
    }

    /**
     * test for all countries in a region
     */
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

    /**
     * tests for all top populated countries in a region
     */
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
     * test for cities report
     * integration test for all cities in the world
     */
    @Test
    void testGetAllCitiesInWorld()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInWorld());
        assertEquals(4079, cities.size());
    }

    /**
     * integration test for all cities in a continent
     */
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

    /**
     * integration test for all cities in a region
     */
    @Test
    void testGetAllCitiesInRegion()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInRegion("Southeast Asia"));
        City city = cities.get(0);
        assertEquals("Jakarta", city.getCityName());
        assertEquals("Indonesia", city.getCountry());
        assertEquals("9,604,900", city.getCityPopulation());
    }

    /**
     * integration test for all cities in a country
     */
    @Test
    void testGetAllCitiesInCountry()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInCountry("China"));
        assertEquals(363, cities.size());
        assertEquals("Wuhan", cities.get(4).getCityName());
    }

    /**
     * integration test for all cities in a district
     */
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
     * integration test for top populated cities in the world
     */
    @Test
    void testGetTopPopulatedCitiesInWorld()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInWorld(5));
        assertEquals(5, cities.size());
    }

    /**
     * integration test for top populated cities in a continent
     */
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

    /**
     * integration test for top populated cities in a region
     */
    @Test
    void testGetTopPopulatedCitiesInRegion()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInRegion(5,"Southeast Asia"));
        assertEquals(5, cities.size());
    }

    /**
     * integration test for top populated cities in a country
     */
    @Test
    void testGetTopPopulatedCitiesInCountry()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInCountry(10,"China"));
        City city = cities.get(8);
        assertEquals("Chengdu", city.getCityName());
        assertEquals("Sichuan", city.getDistrict());
    }

    /**
     * integration test for top populated cities in a district
     */
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
     * test for capital cities report
     * integration test for all capital cities in the world
     */
    @Test
    void testGetAllCapitalCitiesInWorld()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInWorld());
        Capital cc = capitalCities.get(0);
        assertEquals("Seoul", cc.getCapitalName());
        assertEquals("South Korea", cc.getCountry());
    }

    /**
     * integration test for all capital cities in a continent
     */
    @Test
    void testGetAllCapitalCitiesInContinent()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInContinent("Asia"));
        int lastCapital = capitalCities.size()-1;
        Capital cc = capitalCities.get(lastCapital);
        assertEquals("Bandar Seri Begawan", cc.getCapitalName());
        assertEquals("21,484", cc.getCapitalPopulation());
    }

    /**
     * integration test for all capital cities in a region
     */
    @Test
    void testGetAllCapitalCitiesInRegion()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInRegion("Southeast Asia"));
        Capital cc = capitalCities.get(3);
        assertEquals("Rangoon (Yangon)", cc.getCapitalName());
    }

    /**
     * integration test for top populated cities in the world
     */
    @Test
    void testGetTopPopulatedCapitalCitiesInWorld()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInWorld(5));
        assertEquals(5, capitalCities.size());
    }

    /**
     * integration test for top populated cities in a continent
     */
    @Test
    void testGetTopPopulatedCapitalCitiesInContinent()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInContinent(5, "Asia"));
        assertEquals(5, capitalCities.size());
    }

    /**
     * integration test for top populated cities in a region
     */
    @Test
    void testGetTopPopulatedCapitalCitiesInRegion()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInRegion("Southeast Asia"));
        Capital cc = capitalCities.get(1);
        assertEquals("Bangkok", cc.getCapitalName());
    }

    /**
     * integration test for population of people, population of people, people living in cities, and people not living in cities in each continent
     */
    @Test
    void testGetPopulationInformationInContinent(){
        Population ppe = pd.getPopulationInformation(pd.totalPopulationInContinent("Asia"),pd.totalPopulationLivingInCitiesInContinent("Asia"));
        assertEquals("3,705,025,700", ppe.getTotalPopulation(), "Expected population and Actual population of people living in Asia should be the same.");
        assertEquals("19%", ppe.getPercentageCityPopulation(), "In Asia, 19% of people live in cities.");
    }

    /**
     * integration test for population of people, population of people, people living in cities, and people not living in cities in each region
     */
    @Test
    void testGetPopulationInformationInRegion(){
        Population ppe = pd.getPopulationInformation(pd.totalPopulationInRegion("Southeast Asia"),pd.totalPopulationLivingInCitiesInRegion("Southeast Asia"));
        assertEquals("102,067,225", ppe.getTotalPopulationCities(), "In Southeast Asia, 102,067,225 people live in cities.");
        assertEquals("20%", ppe.getPercentageCityPopulation(), "Percentage of people who live in cities of Southeast Asia is 20%.");
    }

    /**
     * integration test for population of people, population of people, people living in cities, and people not living in cities in each country
     */
    @Test
    void testGetPopulationInformationInCountry(){
        Population ppe = pd.getPopulationInformation(pd.totalPopulationInCountry("China"),pd.totalPopulationLivingInCitiesInCountry("China"));
        assertEquals("China", ppe.getPopName(), "Country should be China.");
        assertEquals("1,101,604,386", ppe.getTotalPopulationNotCities(), "In China, 101,604,386 people do not live in cities.");
        assertEquals("86%", ppe.getPercentageNotCityPopulation(), "Percentage of people who do not live in cities of China is 86%.");
    }


    /**
     * close database connection after all tests
     */
    @AfterAll
    static void closeConnection() {
        app.disconnect();
    }
}