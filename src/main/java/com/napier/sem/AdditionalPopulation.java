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
            //testing continent is not null
            if (continent == null) {
                System.out.println("No continent selected");
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
}