package com.napier.sem;

import java.sql.*;

import java.text.NumberFormat;
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
                System.out.println("Failed to connect to database attempt " + i);
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

    /**
     * sort countries in world by largest population to smallest
     */
    public String allCountriesInWorld()
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital "
                            + "FROM country, city "
                            + "WHERE country.Capital = city.ID "
                            + "ORDER BY Population DESC";
    }

    /**
     * top N populated countries in the world
     * @param number The number of countries to print.
     */
    public String topPopulatedCountriesInWorld(int number)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * sort countries in a continent by largest population to smallest
     * @param continent The continent to print.
     */
    public String allCountriesInContinent(String continent)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated countries in a continent
     * @param number the number of countries
     * @param continent the continent
     */
    public String topPopulatedCountriesInContinent(int number, String continent)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC"
                + " LIMIT " + number;
    }

    /**
     * sort countries in a region by largest population to smallest
     * @param region The region for the countries to print.
     */
    public String allCountriesInRegion(String region)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * top N populated countries in a region
     * @param number the number of countries
     * @param region the region
     */
    public String topPopulatedCountriesInRegion(int number, String region)
    {
        return "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital "
                + "FROM country, city "
                + "WHERE country.Capital = city.ID AND country.Region = '" + region + "' "
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
            ResultSet rset = stmt.executeQuery(query);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();

            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();

            while (rset.next())
            {
                // creating a country object
                Country cty = new Country();
                cty.Code = rset.getString("Code");
                cty.Name = rset.getString("Name");
                cty.Continent = rset.getString("Continent");
                cty.Region = rset.getString("Region");
                // retrieve it as an integer
                int intPopulation = rset.getInt("Population");
                // format with number format
                cty.Population = nf.format(intPopulation);
                cty.Capital = rset.getString("Capital");
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
        String line = "=".repeat(130);

        // title underline
        String titleLine = "-".repeat(70);

        ArrayList<Country> countriesInWorld = a.getAllCountries(a.allCountriesInWorld());
        System.out.println("All countries in the world by largest population to smallest");
        System.out.println(titleLine);
        a.printCountries(countriesInWorld);
        System.out.println(line);

        ArrayList<Country> countriesInContinent = a.getAllCountries(a.allCountriesInContinent("Asia"));
        System.out.println("All countries in the continent by largest population to smallest");
        System.out.println(titleLine);
        a.printCountries(countriesInContinent);
        System.out.println(line);

        ArrayList<Country> countriesInRegion = a.getAllCountries(a.allCountriesInRegion("Southeast Asia"));
        System.out.println("All countries in the region by largest population to smallest");
        System.out.println(titleLine);
        a.printCountries(countriesInRegion);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInWorld = a.getAllCountries(a.topPopulatedCountriesInWorld(5));
        System.out.println("Top N populated countries in the world");
        System.out.println(titleLine);
        a.printCountries(populatedCountriesInWorld);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInContinent = a.getAllCountries(a.topPopulatedCountriesInContinent(5, "Europe"));
        System.out.println("Top N populated countries in the continent");
        System.out.println(titleLine);
        a.printCountries(populatedCountriesInContinent);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInRegion = a.getAllCountries(a.topPopulatedCountriesInRegion(5,"Caribbean"));
        System.out.println("Top N populated countries in the region");
        System.out.println(titleLine);
        a.printCountries(populatedCountriesInRegion);
        System.out.println(line);

        // Disconnect from database
        a.disconnect();
    }
}