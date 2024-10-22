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
    static LanguageData ld;
    static AdditionalPopulation ap;
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
        ld = new LanguageData(con);
        ap = new AdditionalPopulation(con);
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
        assertEquals("China", cty.getName(), "country name should be China.");
        assertEquals("Eastern Asia", cty.getRegion(), "Region should be Eastern Asia.");
        assertEquals("1,277,558,000", cty.getPopulation(), "Population should be 1,277,558,000.");
    }

    /**
     * test for top populated countries in the world
     */
    @Test
    void testTopPopulatedCountriesInWorld() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInWorld(5));
        Country cty = countries.get(2);
        assertEquals("USA", cty.getCode(), "Country code should be USA.");
        assertEquals("Washington", cty.getCapital(), "Capital should be Washington.");
    }

    /**
     * test for all countries in a continent
     */
    @Test
    void testGetAllCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInContinent("Europe"));
        Country cty = countries.get(0);
        assertEquals("Russian Federation", cty.getName(), "Country name should be Russian Federation.");
        assertEquals("Moscow", cty.getCapital(), "The capital should be Moscow.");
    }

    /**
     * test for top populated countries in a continent
     */
    @Test
    void testTopPopulatedCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInContinent(5,"Asia"));
        assertEquals(5, countries.size(), "There should be 5 countries in continent.");
    }

    /**
     * test for all countries in a region
     */
    @Test
    void testGetAllCountriesInRegion() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInRegion("Southeast Asia"));
        int lastCountry = countries.size()-1;
        Country cty = countries.get(lastCountry);
        assertEquals("Brunei", cty.getName(), "The last country should be Brunei.");
        assertEquals("Asia", cty.getContinent(), "Continent should be Asia.");
    }

    /**
     * tests for all top populated countries in a region
     */
    @Test
    void testTopPopulatedCountriesInRegion() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInRegion(5,"Southeast Asia"));
        Country cty = countries.get(1);
        assertEquals("VNM", cty.getCode(), "Country code should be VNM.");
        assertEquals("Vietnam", cty.getName(), "Country name should be Vietnam.");
        assertEquals("Hanoi", cty.getCapital(), "Capital city should be Hanoi.");
    }

    /**
     * test for cities report
     * integration test for all cities in the world
     */
    @Test
    void testGetAllCitiesInWorld()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInWorld());
        assertEquals(4079, cities.size(), "There should be 4079 cities.");
    }

    /**
     * integration test for all cities in a continent
     */
    @Test
    void testGetAllCitiesInContinent()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInContinent("Asia"));
        City city = cities.get(0);
        assertEquals("Mumbai (Bombay)", city.getCityName(), "City name should be Mumbai (Bombay).");
        assertEquals("India", city.getCountry(), "Country name should be India.");
    }

    /**
     * integration test for all cities in a region
     */
    @Test
    void testGetAllCitiesInRegion()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInRegion("Southeast Asia"));
        City city = cities.get(0);
        assertEquals("Jakarta", city.getCityName(), "City name should be Jakarta.");
    }

    /**
     * integration test for all cities in a country
     */
    @Test
    void testGetAllCitiesInCountry()
    {
        ArrayList<City> cities = ct.getAllCities(ct.allCitiesInCountry("China"));
        assertEquals(363, cities.size(), "There are 363 cities in China.");
        assertEquals("Wuhan", cities.get(4).getCityName(), "Wuhan is the 4th largest population city in China.");
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
        assertEquals("Tandil", city.getCityName(), "City name is Tandil.");
        assertEquals("Buenos Aires", city.getDistrict(), "The district of the city should be Buenos Aires.");
    }

    /**
     * integration test for top populated cities in the world
     */
    @Test
    void testGetTopPopulatedCitiesInWorld()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInWorld(5));
        assertEquals(5, cities.size(), "There should be 5 cities.");
    }

    /**
     * integration test for top populated cities in a continent
     */
    @Test
    void testGetTopPopulatedCitiesInContinent()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInContinent(5,"Asia"));
        City city = cities.get(2);
        assertEquals("Shanghai", city.getCityName(), "The most populated city in Asia is Shanghai.");
        assertEquals("9,696,300", city.getCityPopulation(), "Population of the city is 9,696,300.");
    }

    /**
     * integration test for top populated cities in a region
     */
    @Test
    void testGetTopPopulatedCitiesInRegion()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInRegion(5,"Southeast Asia"));
        assertEquals(5, cities.size(), "There should be 5 cities.");
    }

    /**
     * integration test for top populated cities in a country
     */
    @Test
    void testGetTopPopulatedCitiesInCountry()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInCountry(10,"China"));
        City city = cities.get(8);
        assertEquals("Chengdu", city.getCityName(), "Populated city in China is Chengdu.");
        assertEquals("Sichuan", city.getDistrict(), "The district should be Sichuan.");
    }

    /**
     * integration test for top populated cities in a district
     */
    @Test
    void testGetTopPopulatedCitiesInDistrict()
    {
        ArrayList<City> cities = ct.getAllCities(ct.topPopulatedCitiesInDistrict(5,"Buenos Aires"));
        City city = cities.get(3);
        assertEquals("Almirante Brown", city.getCityName(), "City name should be Almirante Brown.");
        assertEquals("538,918", city.getCityPopulation(), "Population of the city is 538,918.");
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
        assertEquals("Seoul", cc.getCapitalName(), "Capital city should be Seoul.");
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
        assertEquals("Bandar Seri Begawan", cc.getCapitalName(), "Capital city is Bandar Seri Begawan.");
        assertEquals("21,484", cc.getCapitalPopulation(), "Population of the city is 21,484.");
    }

    /**
     * integration test for all capital cities in a region
     */
    @Test
    void testGetAllCapitalCitiesInRegion()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.allCapitalCitiesInRegion("Southeast Asia"));
        Capital cc = capitalCities.get(3);
        assertEquals("Rangoon (Yangon)", cc.getCapitalName(), "Capital city should be Rangoon(Yangon).");
    }

    /**
     * integration test for top populated cities in the world
     */
    @Test
    void testGetTopPopulatedCapitalCitiesInWorld()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInWorld(5));
        assertEquals(5, capitalCities.size(),"There should be 5 capital cities.");
    }

    /**
     * integration test for top populated cities in a continent
     */
    @Test
    void testGetTopPopulatedCapitalCitiesInContinent()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInContinent(5, "Asia"));
        assertEquals(5, capitalCities.size(), "Expected and Actual result should be the same.");
    }

    /**
     * integration test for top populated cities in a region
     */
    @Test
    void testGetTopPopulatedCapitalCitiesInRegion()
    {
        ArrayList<Capital> capitalCities = cap.getAllCapitalCities(cap.topPopulatedCapitalCitiesInRegion(5,"Southeast Asia"));
        Capital cc = capitalCities.get(1);
        assertEquals("Bangkok", cc.getCapitalName(), "Capital cities should be Bangkok.");
        assertEquals("Thailand", cc.getCountry(), "Country name should be Thailand.");
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
     * integration test for return result of the world population
     */
    @Test
    void testReturnOfWorldPopulation(){

        String worldPopulationResults = ap.worldPopulation();
        assertEquals("6,078,749,450",worldPopulationResults, "This is the population of the world.");
    }

    /**
     * integration test for return result of the continent population
     */
    @Test
    void testReturnOfContinentPopulation(){

        String continentPopulationResults = ap.continentPopulation("Asia");
        assertEquals("3,705,025,700",continentPopulationResults, "This is the population of Asia.");
    }

    /**
     * integration test for return result of the continent population
     */
    @Test
    void testReturnOfRegionPopulation(){

        String regionPopulationResults = ap.regionPopulation("Southeast Asia");
        assertEquals("518,541,000",regionPopulationResults, "This is the population of Southeast Asia.");
    }

    /**
     * integration test for return result of the country population
     */
    @Test
    void testReturnOfCountryPopulation(){

        String countryPopulationResults = ap.countryPopulation("China");
        assertEquals("1,277,558,000",countryPopulationResults, "This is the population of China.");
    }

    /**
     * integration test for return result of the district population
     */
    @Test
    void testReturnOfDistrictPopulation(){

        String districtPopulationResults = ap.districtPopulation("Buenos Aires");
        assertEquals("10,530,136",districtPopulationResults, "This is the population of Buenos Aires.");
    }

    /**
     * integration test for Chinese language information
     */
    @Test
    void testGetChineseLanguageInformation(){
        ArrayList<Language> languages = ld.getLanguageInformation();
        Language lan = languages.get(0);
        assertEquals("Chinese", lan.getLanguage(), "Language should be Chinese.");
        assertEquals(5, languages.size(), "There should be 5 languages.");
    }

    /**
     * integration test for Spanish language information
     */
    @Test
    void testGetSpanishLanguageInformation(){
        ArrayList<Language> languages = ld.getLanguageInformation();
        Language lan = languages.get(2);
        assertEquals("Spanish", lan.getLanguage(), "Language should be Spanish.");
        assertEquals("6%", lan.getWorldPopPercentage(), "This percentage is based on world population.");
    }

    /**
     * integration test for Arabic language information
     */
    @Test
    void testGetArabicLanguageInformation(){
        ArrayList<Language> languages = ld.getLanguageInformation();
        int lastLan = languages.size()-1;
        Language lan = languages.get(lastLan);
        assertEquals("233,839,239", lan.getTotalLanguageSpeaker(), "233,839,239 people speak Arabic.");
    }

    /**
     * close database connection after all tests
     */
    @AfterAll
    static void closeConnection() {
        app.disconnect();
    }
}