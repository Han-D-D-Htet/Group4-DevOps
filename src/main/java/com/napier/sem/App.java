package com.napier.sem;

import java.sql.*;

import java.util.ArrayList;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public String allCountriesInWorld()
    {
        String query = "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Population DESC";
        return query;

    }

    public String topPopulatedCountriesInWorld(int number)
    {
        String query = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
        return query;

    }

    /**
     * sort countries in a continent by largest population to smallest
     * @param continent The continent to print.
     */
    public String allCountriesInContinent(String continent)
    {
        String query =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "WHERE Continent = " + continent + " "
                            + "ORDER BY Population DESC";
        return query;
    }

    public String allCountriesInRegion(String region)
    {
        String query =
                "SELECT Code, Name, Continent, Region, Population, Capital "
                        + "FROM country "
                        + "WHERE Continent = " + region;
        return query;
    }

    public ArrayList<Country> getAllCountries(String query)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = query;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();

            while (rset.next())
            {
                // creating a country object
                Country cty = new Country();
                cty.Code = rset.getString("Code");
                cty.Name = rset.getString("Name");
                cty.Continent = rset.getString("Continent");
                cty.Region = rset.getString("Region");
                cty.Population = rset.getInt("Population");
                cty.Capital = rset.getInt("Capital");
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
        // Print header for country information
        System.out.println(String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                                    "Code", "Name", "Continent", "Region", "Population", "Capital"));

        // Loop over all countries in the list
        for (Country cty : countries)
        {
            String cty_string =
                    String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                            cty.Code, cty.Name, cty.Continent, cty.Region, cty.Population, cty.Capital);
            System.out.println(cty_string);
        }
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // division line between prints
        String line = "=".repeat(125);

        // Extract country information
        ArrayList<Country> countriesInWorld = a.getAllCountries(a.allCountriesInWorld());
        System.out.println("All countries in the world by largest population to smallest");
        a.printCountries(countriesInWorld);
        System.out.println(line);

//        ArrayList<Country> countriesInContinent = a.getAllCountries(a.allCountriesInContinent("Asia"));
//        a.printCountries(countriesInContinent);
//        System.out.println(line);

//        ArrayList<Country> countriesInRegion = a.getAllCountries(a.allCountriesInRegion("Southeast Asia"));
//        a.printCountries(countriesInRegion);
//        System.out.println(line);

        ArrayList<Country> populatedCountriesInWorld = a.getAllCountries(a.topPopulatedCountriesInWorld(5));
        System.out.println("Top N populated countries in the world");
        a.printCountries(populatedCountriesInWorld);
        System.out.println(line);


        // Disconnect from database
        a.disconnect();
    }
}