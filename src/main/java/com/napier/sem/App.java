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
            catch (SQLException e)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(e.getMessage());
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

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // division line between prints
        String line = "=".repeat(130);

        // variables
        int count = 5;
        String inputContinent = "Asia";
        String inputRegion = "Southeast Asia";
        String inputCountry = "China";
        String inputDistrict = "Buenos Aires";

        CountryData cd = new CountryData(a.con);
        ArrayList<Country> countriesInWorld = cd.getAllCountries(cd.allCountriesInWorld());
        System.out.println("<<< All countries in the world by largest population to smallest >>>");
        cd.printCountries(countriesInWorld);
        System.out.println(line);

        ArrayList<Country> countriesInContinent = cd.getAllCountries(cd.allCountriesInContinent(inputContinent));
        System.out.println("<<< All countries in the " + inputContinent + " continent by largest population to smallest >>>");
        cd.printCountries(countriesInContinent);
        System.out.println(line);

        ArrayList<Country> countriesInRegion = cd.getAllCountries(cd.allCountriesInRegion(inputRegion));
        System.out.println("<<< All countries in the " + inputRegion + " region by largest population to smallest >>>");
        cd.printCountries(countriesInRegion);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInWorld = cd.getAllCountries(cd.topPopulatedCountriesInWorld(count));
        System.out.println("<<< Top " + count + " populated countries in the world >>>");
        cd.printCountries(populatedCountriesInWorld);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInContinent = cd.getAllCountries(cd.topPopulatedCountriesInContinent(count, inputContinent));
        System.out.println("<<< Top " + count + " populated countries in the " + inputContinent + " continent >>>");
        cd.printCountries(populatedCountriesInContinent);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInRegion = cd.getAllCountries(cd.topPopulatedCountriesInRegion(count, inputRegion));
        System.out.println("<<< Top " + count + " populated countries in the " + inputRegion + " region >>>");
        cd.printCountries(populatedCountriesInRegion);
        System.out.println(line);

        CityData cid = new CityData(a.con);
//        ArrayList<City> citiesInWorld = cid.getAllCities(cid.allCitiesInWorld());
//        System.out.println("<<< All cities in the world by largest population to smallest >>>");
//        cid.printCities(citiesInWorld);
//        System.out.println(line);

        ArrayList<City> citiesInContinent = cid.getAllCities(cid.allCitiesInContinent(inputContinent));
        System.out.println("<<< All cities in the " + inputContinent + " continent by largest population to smallest >>>");
        cid.printCities(citiesInContinent);
        System.out.println(line);

        ArrayList<City> citiesInRegion = cid.getAllCities(cid.allCitiesInRegion(inputRegion));
        System.out.println("<<< All cities in the " + inputRegion + " region by largest population to smallest >>>");
        cid.printCities(citiesInRegion);
        System.out.println(line);

        ArrayList<City> citiesInCountry = cid.getAllCities(cid.allCitiesInCountry(inputCountry));
        System.out.println("<<< All cities in the " + inputCountry + " country by largest population to smallest >>>");
        cid.printCities(citiesInCountry);
        System.out.println(line);

        ArrayList<City> citiesInDistrict = cid.getAllCities(cid.allCitiesInDistrict(inputDistrict));
        System.out.println("<<< All cities in the " + inputDistrict + " by largest population to smallest >>>");
        cid.printCities(citiesInDistrict);
        System.out.println(line);

        // top N populated cities in the world
        ArrayList<City> topCitiesInWorld = cid.getAllCities(cid.topPopulatedCitiesInWorld(count)); // Change the number as needed
        System.out.println("<<< Top " + count + " populated cities in the world >>>");
        cid.printCities(topCitiesInWorld);
        System.out.println(line);

        // top N populated cities in a specific continent (e.g., Asia)
        ArrayList<City> topCitiesInContinent = cid.getAllCities(cid.topPopulatedCitiesInContinent(count, inputContinent)); // Change the number as needed
        System.out.println("<<< Top " + count + " populated cities in the " + inputContinent + " continent>>>");
        cid.printCities(topCitiesInContinent);
        System.out.println(line);

        //top N populated cities in a specific region (e.g., Southeast Asia)
        ArrayList<City> topCitiesInRegion = cid.getAllCities(cid.topPopulatedCitiesInRegion(count, inputRegion)); // Change the number as needed
        System.out.println("<<< Top " + count + " populated cities in the " + inputRegion + " region >>>");
        cid.printCities(topCitiesInRegion);
        System.out.println(line);

        // top N populated cities in a specific country (e.g., "United States")
        ArrayList<City> topPopulatedCitiesInCountry = cid.getAllCities(cid.topPopulatedCitiesInCountry(count, inputCountry)); // Change the number as needed
        System.out.println("<<< Top " + count + " populated cities in the " + inputCountry + " country >>>");
        cid.printCities(topPopulatedCitiesInCountry);
        System.out.println(line);

        // top N populated cities in a specific district (e.g., "Buenos Aires")
        ArrayList<City> topCitiesInDistrict = cid.getAllCities(cid.topPopulatedCitiesInDistrict(count, inputDistrict)); // Change the number as needed
        System.out.println("<<< Top " + count + " populated cities in the " + inputDistrict + " district >>>");
        cid.printCities(topCitiesInDistrict);
        System.out.println(line);

        CapitalData cpd = new CapitalData(a.con);
        ArrayList<Capital> capitalCitiesInWorld = cpd.getAllCapitalCities(cpd.allCapitalCitiesInWorld());
        System.out.println("<<< All capital cities in the world by largest Population to smallest >>>");
        cpd.printCapitalCities(capitalCitiesInWorld);
        System.out.println(line);

        ArrayList<Capital> capitalCitiesInContinent = cpd.getAllCapitalCities(cpd.allCapitalCitiesInContinent(inputContinent));
        System.out.println("<<< All capital cities in the " + inputContinent + " continent by largest Population to smallest >>>");
        cpd.printCapitalCities(capitalCitiesInContinent);
        System.out.println(line);

        ArrayList<Capital> capitalCitiesInRegion = cpd.getAllCapitalCities(cpd.allCapitalCitiesInRegion(inputRegion));
        System.out.println("<<< All capital cities in the " + inputRegion + " region by largest Population to smallest >>>");
        cpd.printCapitalCities(capitalCitiesInRegion);
        System.out.println(line);

        ArrayList<Capital> populatedCapitalCitiesInWorld = cpd.getAllCapitalCities(cpd.topPopulatedCapitalCitiesInWorld(count));
        System.out.println("<<< Top " + count + " populated capital cities in the world >>>");
        cpd.printCapitalCities(populatedCapitalCitiesInWorld);
        System.out.println(line);

        ArrayList<Capital> populatedCapitalCitiesInContinent = cpd.getAllCapitalCities(cpd.topPopulatedCapitalCitiesInContinent(count, inputContinent));
        System.out.println("<<< Top " + count + " populated capital cities in the " + inputContinent + " continent >>>");
        cpd.printCapitalCities(populatedCapitalCitiesInContinent);
        System.out.println(line);

        ArrayList<Capital> populatedCapitalCitiesInRegion = cpd.getAllCapitalCities(cpd.topPopulatedCapitalCitiesInRegion(count, inputRegion));
        System.out.println("<<< Top " + count + " populated capital cities in the " + inputRegion + " region >>>");
        cpd.printCapitalCities(populatedCapitalCitiesInRegion);
        System.out.println(line);

//


        // Disconnect from database
        a.disconnect();
    }
}