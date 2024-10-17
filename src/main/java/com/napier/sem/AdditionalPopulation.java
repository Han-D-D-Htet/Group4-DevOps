package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;


public class AdditionalPopulation {

    private final Connection con;

    // Constructor
    public AdditionalPopulation(Connection con){
        this.con = con;
    }

    // number format to format numbers
    NumberFormat nf = NumberFormat.getNumberInstance();

    /**
     * query for the population of the world
     */

    public void worldPopulation(){
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for the population of the world
            String worldQuery = "SELECT SUM(Population) as worldPop FROM country";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(worldQuery);
            if (res.next())
            {
                System.out.println("World Population: " + nf.format(res.getDouble("worldPop")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");

        }
    }



}