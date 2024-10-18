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

}
