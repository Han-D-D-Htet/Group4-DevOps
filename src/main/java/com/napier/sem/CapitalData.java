package com.napier.sem;

import java.sql.*;

import java.text.NumberFormat;

import java.util.ArrayList;

public class CapitalData {

    private final Connection con;

    // Constructor
    public CapitalData(Connection con){
        this.con = con;
    }

    /**
     * sort capital cities in world by largest Population to smallest
     */
    public String allCapitalCitiesInWorld()
    {
        return "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated capital cities in the world
     * @param number The number of capital cities to print.
     */
    public String topPopulatedCapitalCitiesInWorld(int number)
    {
        return "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * sort capital cities in continent by largest Population to smallest
     */
    public String allCapitalCitiesInContinent(String continent)
    {
        return "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated capital cities in the continent
     * @param number The number of capital cities to print.
     */
    public String topPopulatedCapitalCitiesInContinent(int number, String continent)
    {
        return "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * sort capital cities in region by largest Population to smallest
     */
    public String allCapitalCitiesInRegion(String region)
    {
        return "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated capital cities in the region
     * @param number The number of capital cities to print.
     */
    public String topPopulatedCapitalCitiesInRegion(int number, String region)
    {
        return "SELECT city.Name, country.Name as Country, city.Population "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * get the capital cities information as an arraylist
     * @param query the SQL query to execute
     */
    public ArrayList<Capital> getAllCapitalCities(String query)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(query);

            // Extract capital city information
            ArrayList<Capital> capitalCities = new ArrayList<>();

            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();

            while (res.next())
            {
                // creating a capital city object
                Capital cc = new Capital();
                cc.setCapitalName(res.getString("Name"));
                cc.setCountry(res.getString("Country"));
                // retrieve it as an integer
                int intPopulation = res.getInt("Population");
                // format with number format
                cc.setCapitalPopulation(nf.format(intPopulation));
                capitalCities.add(cc);
            }
            return capitalCities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
            return null;
        }
    }

    /**
     * Prints a list of capital cities.
     * @param capitalCities The list of capital cities to print.
     */
    public void printCapitalCities(ArrayList<Capital> capitalCities)
    {
        // Print header for capital cities information
        System.out.println(String.format("%-40s %-50s %-20s",
                "Capital Name", "Country", "Population"));
        System.out.println(String.format("%-40s %-50s %-20s",
                "------------", "-------", "----------"));
        try
        {
            // Loop over all countries in the list
            for (Capital cc : capitalCities) {
                String cc_string =
                        String.format("%-40s %-50s %-20s",
                                cc.getCapitalName(), cc.getCountry(), cc.getCapitalPopulation());
                System.out.println(cc_string);
            }
        }
        catch (NullPointerException ne)
        {
            System.out.println(ne.getMessage());
            System.out.println("The capital cities list is empty. Something wrong!");
        }
    }
}
