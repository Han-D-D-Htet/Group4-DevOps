package com.napier.sem;

import java.sql.*;

import java.text.NumberFormat;

import java.util.ArrayList;

public class CountryData {

    private final Connection con;

    // Constructor
    public CountryData(Connection con){
        this.con = con;
    }

    /**
     * sort countries in world by largest population to smallest
     */
    public String allCountriesInWorld()
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city "
                + "WHERE country.capital = city.ID "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated countries in the world
     * @param number The number of countries to print.
     */
    public String topPopulatedCountriesInWorld(int number)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city "
                + "WHERE country.capital = city.ID "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * sort countries in a continent by largest population to smallest
     * @param continent The continent to print.
     */
    public String allCountriesInContinent(String continent)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated countries in a continent
     * @param number the number of countries
     * @param continent the continent
     */
    public String topPopulatedCountriesInContinent(int number, String continent)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * sort countries in a region by largest population to smallest
     * @param region The region for the countries to print.
     */
    public String allCountriesInRegion(String region)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated countries in a region
     * @param number the number of countries
     * @param region the region
     */
    public String topPopulatedCountriesInRegion(int number, String region)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as capital "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * get the countries information as an arraylist
     * @param query the SQL query to execute
     */
    public ArrayList<Country> getAllCountries(String query)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(query);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();

            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();

            while (res.next())
            {
                // creating a country object
                Country cty = new Country();
                cty.setCode(res.getString("Code"));
                cty.setName(res.getString("Name"));
                cty.setContinent(res.getString("Continent"));
                cty.setRegion(res.getString("Region"));
                // retrieve it as an integer
                int intPopulation = res.getInt("Population");
                // format with number format
                cty.setPopulation(nf.format(intPopulation));
                cty.setCapital(res.getString("capital"));
                countries.add(cty);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Prints a list of countries.
     * @param countries The list of countries to print.
     */
    public void printCountries(ArrayList<Country> countries)
    {
        // Check countries is not null
        if (countries == null)
        {
            System.out.println("No countries");
            return;
        }
        // Print header for country information
        System.out.println(String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                "Code", "Name", "Continent", "Region", "Population", "capital"));
        System.out.println(String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                "----", "----", "---------", "------", "----------", "-------"));

        // Loop over all countries in the list
        for (Country cty : countries) {
            if (cty == null)
                continue;
            String cty_string =
                    String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                            cty.getCode(), cty.getName(), cty.getContinent(), cty.getRegion(), cty.getPopulation(), cty.getCapital());
            System.out.println(cty_string);
        }
    }


}
