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
                cty.code = res.getString("Code");
                cty.name = res.getString("Name");
                cty.continent = res.getString("Continent");
                cty.region = res.getString("Region");
                // retrieve it as an integer
                int intPopulation = res.getInt("Population");
                // format with number format
                cty.population = nf.format(intPopulation);
                cty.capital = res.getString("capital");
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
                                    "Code", "Name", "Continent", "Region", "Population", "capital"));
        try
        {
            // Loop over all countries in the list
            for (Country cty : countries) {
                String cty_string =
                        String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                                cty.code, cty.name, cty.continent, cty.region, cty.population, cty.capital);
                System.out.println(cty_string);
            }
        }
        catch (NullPointerException ne)
        {
            System.out.println(ne.getMessage());
            System.out.println("The country list is empty. Something wrong!");
        }
    }

    /**
     * sort cities in world by largest population to smallest
     */
    public String allCitiesInWorld() {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a continent by largest population to smallest
     * @param continent The continent to print.
     */
    public String allCitiesInContinent(String continent)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code AND country.Continent = '" + continent + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a region by largest population to smallest
     * @param region The region to print.
     */
    public String allCitiesInRegion(String region)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code AND country.Region = '" + region + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a country by largest population to smallest
     * @param country The region to print.
     */
    public String allCitiesInCountry(String country)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city, country "
                + "WHERE city.CountryCode = country.Code AND country.Name = '" + country + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * sort cities in a district by largest population to smallest
     * @param district The continent to print.
     */
    public String allCitiesInDistrict(String district)
    {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population "
                + "FROM city,country "
                + "WHERE city.CountryCode = country.Code AND city.District = '" + district + "' "
                + "ORDER BY Population DESC";
    }

    /**
     * get the cities information as an arraylist
     * @param query the SQL query to execute
     */
    public ArrayList<City> getAllCities(String query)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(query);

            // Extract cities information
            ArrayList<City> cities = new ArrayList<>();

            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();

            while (res.next())
            {
                // creating a city object
                City city = new City();
                city.cityName = res.getString("Name");
                city.country = res.getString("Country");
                city.district = res.getString("District");
                // retrieve it as an integer
                int intPopulation = res.getInt("Population");
                // format with number format
                city.cityPopulation = nf.format(intPopulation);
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    public void printCities(ArrayList<City> cities)
    {
        // Print header for cities information
        System.out.println(String.format("%-45s %-40s %-30s %-10s",
                "Name", "Country", "District", "Population"));
        try
        {
            // Loop over all cities in the list
            for (City city : cities) {
                String city_string = String.format("%-45s %-40s %-30s %-10s",
                        city.cityName, city.country, city.district, city.cityPopulation);
                System.out.println(city_string);
            }
        }
        catch (NullPointerException ne)
        {
            System.out.println(ne.getMessage());
            System.out.println("The city list is empty. Something wrong!");
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

        ArrayList<City> citiesInWorld = a.getAllCities(a.allCitiesInWorld());
        System.out.println("All cities in the world by largest population to smallest");
        System.out.println(titleLine);
        a.printCities(citiesInWorld);
        System.out.println(line);

        ArrayList<City> citiesInContinent = a.getAllCities(a.allCitiesInContinent("Europe"));
        System.out.println("All cities in the continent by largest population to smallest");
        System.out.println(titleLine);
        a.printCities(citiesInContinent);
        System.out.println(line);

        ArrayList<City> citiesInRegion = a.getAllCities(a.allCitiesInRegion("Polynesia"));
        System.out.println("All cities in the region by largest population to smallest");
        System.out.println(titleLine);
        a.printCities(citiesInRegion);
        System.out.println(line);

        ArrayList<City> citiesInCountry = a.getAllCities(a.allCitiesInCountry("Argentina"));
        System.out.println("All cities in the country by largest population to smallest");
        System.out.println(titleLine);
        a.printCities(citiesInCountry);
        System.out.println(line);

        ArrayList<City> citiesInDistrict = a.getAllCities(a.allCitiesInDistrict("Limburg"));
        System.out.println("All cities in the district by largest population to smallest");
        System.out.println(titleLine);
        a.printCities(citiesInDistrict);
        System.out.println(line);


        // Disconnect from database
        a.disconnect();
    }
}