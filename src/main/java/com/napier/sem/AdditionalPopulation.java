package com.napier.sem;

import java.sql.*;
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
     * query for the sum of total population in world
     */
    public String worldPopulation() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the world
            String worldQuery = "SELECT SUM(Population) as worldPop FROM country";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(worldQuery);
            if (res.next()) {
                return nf.format(res.getDouble("worldPop"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");
        }
        return null;
    }

    /**
     * query for the sum of total population living in cities in a region
     *
     * @param continent The region for total population.
     */
    public String continentPopulation(String continent) {
        try {
            //testing continent is not empty
            if (continent.isEmpty()) {
                System.out.println("Continent is empty");
                return null;
            }
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String conQuery = "SELECT SUM(Population) as continentPop FROM country WHERE Continent = '" + continent + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(conQuery);
            if (res.next()) {
                return nf.format(res.getDouble("continentPop"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population");
        }
        return null;
    }

    /**
     * query for the sum of total population living in cities in a region
     *
     * @param region The region for total population.
     */
    public String regionPopulation(String region) {
        try {
            //testing region is not null
            if (region == null) {
                System.out.println("No region selected");
                return null;
            }
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String regQuery = "SELECT SUM(Population) as regPop FROM country WHERE Region = '" + region + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(regQuery);
            if (res.next()) {
                return nf.format(res.getDouble("regPop"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get region population");
        }
        return null;
    }

    /**
     * query for the sum of total population living in cities in a region
     * @param country The region for total population.
     */
    public String countryPopulation(String country){
        try
        {
            //testing country is not null
            if (country == null) {
                System.out.println("No country selected");
                return null;
            }
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String countryQuery = "SELECT SUM(Population) as countryPop FROM country WHERE Name = '" + country + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(countryQuery);
            if (res.next()) {
                return nf.format(res.getDouble("countryPop"));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population");
        }
        return null;
    }

    /**
     * query for the sum of total population living in cities in a region
     * @param district The region for total population.
     */
    public String districtPopulation(String district){
        try {
            //testing district is not null
            if (district.isEmpty()) {
                System.out.println("Cannot issue empty district");
            }
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String disQuery = "SELECT SUM(Population) as disPop FROM city WHERE District = '" + district + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(disQuery);
            if (res.next()) {
                return nf.format(res.getDouble("disPop"));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get district population");
        }
        return null;
    }

    /**
     * query for the sum of total population living in cities in a region
     * @param city The region for total population.
     */
    public String cityPopulation(String city){
        try
        {
            //testing city is not null
            if (city == null) {
                System.out.println("No city selected");
                return null;
            }
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the continent
            String citQuery = "SELECT SUM(Population) as citPop FROM city WHERE Name = '" + city + "'";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(citQuery);
            if (res.next()) {
                return nf.format(res.getDouble("citPop"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city population");
        }
        return null;
    }
}