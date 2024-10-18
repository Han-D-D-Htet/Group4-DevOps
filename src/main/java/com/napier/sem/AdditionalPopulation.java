package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;


public class AdditionalPopulation {

    private final Connection con;

    // Constructor
    public AdditionalPopulation(Connection con) {
        this.con = con;
    }

    // number format to format numbers
    NumberFormat nf = NumberFormat.getNumberInstance();

    /**
     * query for the population of the world
     */

    public void worldPopulation() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the world
            String worldQuery = "SELECT SUM(Population) as worldPop FROM country";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(worldQuery);
            if (res.next()) {
                System.out.println("26. World Population: " + nf.format(res.getDouble("worldPop")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");

        }
    }

    /**
     * query for the population of a continent
     * @param continent The continent to print.
     */
    public void continentPopulation(String continent){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String conQuery = "SELECT SUM(Population) as continentPop FROM country WHERE Continent = '" + continent + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(conQuery);
            if (res.next())
            {
                System.out.println("27. " + continent + " Population: " + nf.format(res.getDouble("continentPop")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population");

        }
    }

    /**
     * query for the population of a region
     * @param region The region to print.
     */
    public void regionPopulation(String region){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String regQuery = "SELECT SUM(Population) as regPop FROM country WHERE Region = '" + region + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(regQuery);
            if (res.next())
            {
                System.out.println("28. " + region + " Population: " + nf.format(res.getDouble("regPop")));
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get region population");

        }
    }

    /**
     * query for the sum of total population living in cities in a region
     * @param country The region for total population.
     */
    public void countryPopulation(String country){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String countryQuery = "SELECT SUM(Population) as countryPop FROM country WHERE Name = '" + country + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(countryQuery);
            if (res.next())
            {
                System.out.println("29. " + country + " Population: " + nf.format(res.getDouble("countryPop")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population");

        }
    }

    /**
     * query for the sum of total population living in cities in a region
     * @param district The region for total population.
     */
    public void districtPopulation(String district){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String disQuery = "SELECT SUM(Population) as disPop FROM city WHERE District = '" + district + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(disQuery);
            if (res.next())
            {
                System.out.println("30. " + district + " Population: " + nf.format(res.getDouble("disPop")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get district population");

        }
    }

    /**
     * query for the sum of total population living in cities in a region
     * @param city The region for total population.
     */
    public void cityPopulation(String city){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String citQuery = "SELECT SUM(Population) as citPop FROM city WHERE Name = '" + city + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(citQuery);
            if (res.next())
            {
                System.out.println("31. " + city + " Population: " + nf.format(res.getDouble("citPop")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city population");

        }
    }

}
