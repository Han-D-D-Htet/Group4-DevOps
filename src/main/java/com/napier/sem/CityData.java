package com.napier.sem;

import java.sql.*;

import java.text.NumberFormat;

import java.util.ArrayList;

public class CityData {

    private final Connection con;

    // Constructor
    public CityData(Connection con){
        this.con = con;
    }

    /**
     * sort cities in world by largest population to smallest
     */
    public String allCitiesInWorld() {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a continent by largest population to smallest
     * @param continent The continent to print.
     */
    public String allCitiesInContinent(String continent)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a region by largest population to smallest
     * @param region The region to print.
     */
    public String allCitiesInRegion(String region)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a country by largest population to smallest
     * @param country The country to print.
     */
    public String allCitiesInCountry(String country)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code AND country.Name = '" + country + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a district by largest population to smallest
     * @param district The district to print.
     */
    public String allCitiesInDistrict(String district)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city,country "
                + "WHERE city.CountryCode = country.Code AND city.District = '" + district + "' "
                + "ORDER BY Population DESC";
    }
    /**
     * Retrieves the top N populated cities in the world.
     * @param number The number of cities to retrieve.
     * @return SQL query string to get top N cities in the world.
     */
    public String topPopulatedCitiesInWorld(int number) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a continent.
     * @param number The number of cities to retrieve.
     * @param continent The continent to retrieve cities from.
     * @return SQL query string to get top N cities in a continent.
     */
    public String topPopulatedCitiesInContinent(int number, String continent) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Continent = '" + continent + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a region.
     * @param number The number of cities to retrieve.
     * @param region The region to retrieve cities from.
     * @return SQL query string to get top N cities in a region.
     */
    public String topPopulatedCitiesInRegion(int number, String region) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Region = '" + region + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a country.
     * @param number The number of cities to retrieve.
     * @param country The country to retrieve cities from.
     * @return SQL query string to get top N cities in a country.
     */
    public String topPopulatedCitiesInCountry(int number, String country) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Name = '" + country + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a district.
     * @param number The number of cities to retrieve.
     * @param district The district to retrieve cities from.
     * @return SQL query string to get top N cities in a district.
     */
    public String topPopulatedCitiesInDistrict(int number, String district) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE city.District = '" + district + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * get the cities information as an arraylist
     * @param query the SQL query to execute
     */
    public ArrayList<City> getAllCities(String query)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(query);

            // Extract cities information
            ArrayList<City> cities = new ArrayList<>();

            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();

            while (res.next())
            {
                // creating a city object
                City city = new City();
                city.setCityName(res.getString("Name"));
                city.setCountry(res.getString("Country"));
                city.setDistrict(res.getString("District"));
                // retrieve it as an integer
                int intPopulation = res.getInt("Population");
                // format with number format
                city.setCityPopulation(nf.format(intPopulation));
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    public void printCities(ArrayList<City> cities)
    {
        // Print header for cities information
        System.out.println(String.format("%-45s %-40s %-30s %-10s",
                "Name", "Country", "District", "Population"));
        System.out.println(String.format("%-45s %-40s %-30s %-10s",
                "----", "-------", "--------", "----------"));

        try
        {
            // Loop over all cities in the list
            for (City city : cities) {
                String city_string = String.format("%-45s %-40s %-30s %-10s",
                        city.getCityName(), city.getCountry(), city.getDistrict(), city.getCityPopulation());
                System.out.println(city_string);
            }
        }
        catch (NullPointerException ne)
        {
            System.out.println(ne.getMessage());
            System.out.println("The city list is empty. Something wrong!");
        }
    }
}
