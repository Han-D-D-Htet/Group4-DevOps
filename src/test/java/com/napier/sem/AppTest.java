package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;
    static CountryData cd;
    static CityData ct;
    static CapitalData cap;
    static PopulationData pd;
    static AdditionalPopulation ap;
    static LanguageData ld;
    static Connection con;

    /**
     * creating necessary objects for tests
     */
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
        ap = new AdditionalPopulation(con);
        ld = new LanguageData(con);
    }

    /**
     * test for returned query of countries report
     * unit test for all countries in the world
     */
    @Test
    void allCountriesInWorld() {
        String actual = cd.allCountriesInWorld();
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital " +
                "FROM country, city WHERE country.capital = city.ID ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated countries in the world
     */
    @Test
    void topPopulatedCountriesInWorld() {
        String actual = cd.topPopulatedCountriesInWorld(10);
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID "
                + "ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for all countries in a continent
     */
    @Test
    void allCountriesInContinent() {
        String actual = cd.allCountriesInContinent("Europe");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Europe' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated countries in a continent
     */
    @Test
    void topPopulatedCountriesInContinent() {
        String actual = cd.topPopulatedCountriesInContinent(15, "Europe");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Europe' ORDER BY Population DESC LIMIT 15";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for all countries in a region
     */
    @Test
    void allCountriesInRegion() {
        String actual = cd.allCountriesInRegion("Caribbean");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Caribbean' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated countries in a region
     */
    @Test
    void topPopulatedCountriesInRegion() {
        String actual = cd.topPopulatedCountriesInRegion(20, "Caribbean");
        String expected = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Caribbean' ORDER BY Population DESC LIMIT 20";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for null query as input for get all countries
     */
    @Test
    void getAllCountriesTestNull() {
        cd.getAllCountries(null);
    }

    /**
     * test for empty query as input for get all countries
     */
    @Test
    void getAllCountriesTestEmptyQuery() {
        cd.getAllCountries("");
    }

    /**
     * test for printing null
     */
    @Test
    void printCountriesTestNull() {
        cd.printCountries(null);
    }

    /**
     * test for printing empty list
     */
    @Test
    void printCountriesTestEmpty() {
        ArrayList<Country> countries = new ArrayList<>();
        cd.printCountries(countries);
    }

    /**
     * test for countries that contain null
     */
    @Test
    void printCountriesTestContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        cd.printCountries(countries);
    }

    /**
     * test for printing valid data
     */
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
     * test for returned query of cities report
     * unit test for all cities in the world
     */
    @Test
    void allCitiesInWorld() {
        String actual = ct.allCitiesInWorld();
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit tests for all cities in a continent
     */
    @Test
    void allCitiesInContinent() {
        String actual = ct.allCitiesInContinent("Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code AND country.Continent = 'Asia' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit tests for all cities in a region
     */
    @Test
    void allCitiesInRegion() {
        String actual = ct.allCitiesInRegion("Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code AND country.Region = 'Southeast Asia' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit tests for all cities in a country
     */
    @Test
    void allCitiesInCountry() {
        String actual = ct.allCitiesInCountry("Myanmar");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country WHERE city.CountryCode = country.Code AND country.Name = 'Myanmar' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit tests for all cities in a district
     */
    @Test
    void allCitiesInDistrict() {
        String actual = ct.allCitiesInDistrict("Mandalay");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city,country "
                + "WHERE city.CountryCode = country.Code AND city.District = 'Mandalay' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for null query as input for get all cities
     */
    @Test
    void getAllCitiesTestNullQuery() {
        ct.getAllCities(null);
    }

    /**
     * test for empty query as input for get all cities
     */
    @Test
    void getAllCitiesTestEmptyQuery() {
        ct.getAllCities("");
    }

    /**
     * test for printing null
     */
    @Test
    void printCitiesTestNull()
    {
        ct.printCities(null);
    }

    /**
     * test for printing empty list
     */
    @Test
    void printCitiesTestEmpty()
    {
        ArrayList<City> cities = new ArrayList<>();
        ct.printCities(cities);
    }

    /**
     * test for cities that contain null
     */
    @Test
    void printCitiesTestContainsNull()
    {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        ct.printCities(cities);
    }

    /**
     * test for printing valid data
     */
    @Test
    void printCities()
    {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setCityName("Kabul");
        city.setCountry("Afghanistan");
        city.setDistrict("Mandalay");
        city.setCityPopulation("1780000");
        cities.add(city);
        ct.printCities(cities);
    }

    /**
     * unit test for top populated cities in the world
     */
    @Test
    void topPopulatedCitiesInWorld() {
        String actual = ct.topPopulatedCitiesInWorld(10);
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city JOIN country ON city.CountryCode = country.Code ORDER BY city.Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated cities in a continent
     */
    @Test
    void topPopulatedCitiesInContinent() {
        String actual = ct.topPopulatedCitiesInContinent(10, "Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Continent = 'Asia' ORDER BY city.Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated cities in a region
     */
    @Test
    void topPopulatedCitiesInRegion() {
        String actual = ct.topPopulatedCitiesInRegion(10, "Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Region = 'Southeast Asia' ORDER BY city.Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated cities in a country
     */
    @Test
    void topPopulatedCitiesInCountry() {
        String actual = ct.topPopulatedCitiesInCountry(10, "Myanmar");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Name = 'Myanmar' ORDER BY city.Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated cities in a district
     */
    @Test
    void topPopulatedCitiesInDistrict() {
        String actual = ct.topPopulatedCitiesInDistrict(10, "Mandalay");
        String expected = "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city JOIN country ON city.CountryCode = country.Code WHERE city.District = 'Mandalay' ORDER BY city.Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for returned query of capital cities report
     * unit test for all capital cities in the world
     */
    @Test
    void allCapitalCitiesInWorld() {
        String actual = cap.allCapitalCitiesInWorld();
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.Capital = city.ID ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated capital cities in the world
     */
    @Test
    void topPopulatedCapitalCitiesInWorld() {
        String actual = cap.topPopulatedCapitalCitiesInWorld(10);
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.Capital = city.ID ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for all capital cities in a continent
     */
    @Test
    void allCapitalCitiesInContinent() {
        String actual = cap.allCapitalCitiesInContinent("Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Asia' ORDER BY Population DESC";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for top populated capital cities in a continent
     */
    @Test
    void topPopulatedCapitalCitiesInContinent() {
        String actual = cap.topPopulatedCapitalCitiesInContinent(10,"Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Asia' ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * unit test for all capital cities in a region
     */
    @Test
    void allCapitalCitiesInRegion() {
        String actual = cap.allCapitalCitiesInRegion("Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Southeast Asia' ORDER BY Population DESC";
        assertEquals(expected, actual);
    }

    /**
     * unit test for top populated capital cities in a region
     */
    @Test
    void topPopulatedCapitalCitiesInRegion() {
        String actual = cap.topPopulatedCapitalCitiesInRegion(10,"Southeast Asia");
        String expected = "SELECT city.Name, country.Name as Country, city.Population " +
                "FROM country, city WHERE country.capital = city.ID AND country.Region = 'Southeast Asia' ORDER BY Population DESC LIMIT 10";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for null query as input for get all capital cities
     */
    @Test
    void getAllCapitalCitiesTestNullQuery() {
        cap.getAllCapitalCities(null);
    }

    /**
     * test for empty query as input for get all capital cities
     */
    @Test
    void getAllCapitalCitiesTestEmptyQuery() {
        cap.getAllCapitalCities("");
    }

    /**
     * test for printing null
     */
    @Test
    void printCapitalCitiesTestNull() {
        cap.printCapitalCities(null);
    }

    /**
     * test for printing empty list
     */
    @Test
    void printCapitalCitiesTestEmpty() {
        ArrayList<Capital> capitalCities = new ArrayList<>();
        cap.printCapitalCities(capitalCities);
    }

    /**
     * test for capital cities that contain null
     */
    @Test
    void printCapitalCitiesTestContainsNull() {
        ArrayList<Capital> capitalCities = new ArrayList<>();
        capitalCities.add(null);
        cap.printCapitalCities(capitalCities);
    }

    /**
     * test for printing valid data
     */
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

    /**
     * test for query of total population in a continent
     */
    @Test
    void totalPopulationInContinent(){
        String actual = pd.totalPopulationInContinent("Asia");
        String expected = "SELECT country.Continent as popName, SUM(country.Population) as totalPopulation " +"FROM country, city WHERE country.capital = city.ID AND country.Continent = 'Asia'";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for the query of total population living in cities in a continent
     */
    @Test
    void totalPopulationLivingInCitiesInContinent(){
        String actual = pd.totalPopulationLivingInCitiesInContinent("Asia");
        String expected = "SELECT country.Continent, SUM(city.Population) as totalCityPopulation "
                + "FROM country, city "
                + "WHERE country.Code = city.CountryCode AND country.Continent = 'Asia'";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for the query of total population in a region
     */
    @Test
    void totalPopulationInRegion(){
        String actual = pd.totalPopulationInRegion("Asia");
        String expected = "SELECT country.Region as popName, SUM(country.Population) as totalPopulation "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Region = 'Asia'";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for the query of total population living in cities in a region
     */
    @Test
    void totalPopulationLivingInCitiesInRegion(){
        String actual = pd.totalPopulationLivingInCitiesInRegion("Southeast Asia");
        String expected = "SELECT country.Region, SUM(city.Population) as totalCityPopulation "
                + "FROM country, city "
                + "WHERE country.Code = city.CountryCode AND country.Region = 'Southeast Asia'";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for the query of total population in a country
     */
    @Test
    void totalPopulationInCountry(){
        String actual = pd.totalPopulationInCountry("China");
        String expected = "SELECT country.Name as popName, SUM(country.Population) as totalPopulation "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Name = 'China'";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for the query of total population living in cities in a country
     */
    @Test
    void totalPopulationLivingInCitiesInCountry(){
        String actual = pd.totalPopulationLivingInCitiesInCountry("China");
        String expected = "SELECT country.Name, SUM(city.Population) as totalCityPopulation "
                + "FROM country, city "
                + "WHERE country.Code = city.CountryCode AND country.Name = 'China'";
        Assertions.assertEquals(expected, actual);
    }

    /**
     * test for null query as input for get all population information
     */
    @Test
    void getAllPopulationInformationTestNull() { pd.getPopulationInformation((null),(null));}

    /**
     * test for empty query as input for get all population information
     */
    @Test
    void getAllPopulationInformationTestEmpty() {pd.getPopulationInformation("","");}

    /**
     * test for printing null for get all population information
     */
    @Test
    void printPopulationTestNull() {
        pd.printPopulation(null);
    }

    /**
     * test for printing valid data for population of people, living or not living in cites information in a country
     */
    @Test
    void printPopulationTestValid() {
        Population ppe = new Population();
        ppe.setPopName("China");
        ppe.setTotalPopulation("1,277,558,000");
        ppe.setTotalPopulationCities("175,953,614");
        ppe.setTotalPopulationNotCities("1,101,604,386");
        ppe.setPercentageCityPopulation("14%");
        ppe.setPercentageNotCityPopulation("86%");
        pd.printPopulation(ppe);
    }

    /**
     * test for printing empty for continent population
     */
    @Test
    void testContinentPopulationEmpty(){
        ap.continentPopulation("");
    }

    /**
     * test for printing null for region population
     */
    @Test
    void testRegionPopulationNull(){
        String result = ap.regionPopulation(null);
        assertNull(result, "Region is null.");
    }

    /**
     * test for printing null for country population
     */
    @Test
    void testCountryPopulationNull(){
        String result = ap.countryPopulation(null);
        assertNull(result, "Country is null.");
    }

    /**
     * test for printing null for district population
     */
    @Test
    void testDistrictPopulationEmpty(){
        ap.districtPopulation("");
    }

    /**
     * test for empty query as input for get language Information
     */
    @Test
    void getLanguageInformation() {
        ld.getLanguageInformation();
    }

    /**
     * test for printing empty list
     */
    @Test
    void printLanguageTestEmpty(){
        ArrayList<Language> languages = new ArrayList<>();
        ld.printLanguage(languages);
    }

    /**
     * test for printing null for language test
     */
    @Test
    void printLanguageTestNull(){
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(null);
        ld.printLanguage(languages);
    }

    /**
     * test for printing valid data
     */
    @Test
    void printLanguageTestValid(){
        ArrayList<Language> languages =  new ArrayList<>();
        Language lang = new Language();
        lang.setLanguage("English");
        lang.setTotalLanguageSpeaker("347,077,867");
        lang.setWorldPopPercentage("55%");
        languages.add(lang);
        ld.printLanguage(languages);
    }

    /**
     * close database connection after all tests
     */
    @AfterAll
    static void closeConnection() {
        app.disconnect();
    }

}
