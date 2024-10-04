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
        System.out.println(String.format("%-5s %-50s %-20s %-30s %-15s %-5s",
                                    "----", "----", "---------", "------", "----------", "-------"));

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
     * Retrieves the top N populated cities in the world.
     * @param number The number of cities to retrieve.
     * @return SQL query string to get top N cities in the world.
     */
    public String topPopulatedCitiesInWorld(int number) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population  "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a continent.
     * @param number The number of cities to retrieve.
     * @param continent The continent to retrieve cities from.
     * @return SQL query string to get top N cities in a continent.
     */
    public String topPopulatedCitiesInContinent(int number, String continent) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population  "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Continent = '" + continent + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a region.
     * @param number The number of cities to retrieve.
     * @param region The region to retrieve cities from.
     * @return SQL query string to get top N cities in a region.
     */
    public String topPopulatedCitiesInRegion(int number, String region) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population  "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Region = '" + region + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a country.
     * @param number The number of cities to retrieve.
     * @param country The country to retrieve cities from.
     * @return SQL query string to get top N cities in a country.
     */
    public String topPopulatedCitiesInCountry(int number, String country) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population  "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Name = '" + country + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
    }

    /**
     * Retrieves the top N populated cities in a district.
     * @param number The number of cities to retrieve.
     * @param district The district to retrieve cities from.
     * @return SQL query string to get top N cities in a district.
     */
    public String topPopulatedCitiesInDistrict(int number, String district) {
        return "SELECT city.Name, country.Name as Country, city.District, city.Population  "
                + "FROM city "
                + "JOIN country ON city.CountryCode = country.Code "
                + "WHERE city.District = '" + district + "' "
                + "ORDER BY city.Population DESC "
                + "LIMIT " + number;
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
        System.out.println(String.format("%-45s %-40s %-30s %-10s",
                "----", "-------", "--------", "----------"));

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
                + "ORDER BY Population DESC "
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
                cc.capitalName = res.getString("Name");
                cc.country = res.getString("Country");
                // retrieve it as an integer
                int intPopulation = res.getInt("Population");
                // format with number format
                cc.capitalPopulation = nf.format(intPopulation);
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
                                cc.capitalName, cc.country, cc.capitalPopulation);
                System.out.println(cc_string);
            }
        }
        catch (NullPointerException ne)
        {
            System.out.println(ne.getMessage());
            System.out.println("The capital cities list is empty. Something wrong!");
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

        ArrayList<Country> countriesInWorld = a.getAllCountries(a.allCountriesInWorld());
        System.out.println("<<< All countries in the world by largest population to smallest >>>");
        a.printCountries(countriesInWorld);
        System.out.println(line);

        ArrayList<Country> countriesInContinent = a.getAllCountries(a.allCountriesInContinent("Asia"));
        System.out.println("<<< All countries in the continent by largest population to smallest >>>");
        a.printCountries(countriesInContinent);
        System.out.println(line);

        ArrayList<Country> countriesInRegion = a.getAllCountries(a.allCountriesInRegion("Southeast Asia"));
        System.out.println("<<< All countries in the region by largest population to smallest >>>");
        a.printCountries(countriesInRegion);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInWorld = a.getAllCountries(a.topPopulatedCountriesInWorld(5));
        System.out.println("<<< Top N populated countries in the world >>>");
        a.printCountries(populatedCountriesInWorld);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInContinent = a.getAllCountries(a.topPopulatedCountriesInContinent(5, "Europe"));
        System.out.println("<<< Top N populated countries in the continent >>>");
        a.printCountries(populatedCountriesInContinent);
        System.out.println(line);

        ArrayList<Country> populatedCountriesInRegion = a.getAllCountries(a.topPopulatedCountriesInRegion(5,"Caribbean"));
        System.out.println("<<< Top N populated countries in the region >>>");
        a.printCountries(populatedCountriesInRegion);
        System.out.println(line);

        ArrayList<City> citiesInWorld = a.getAllCities(a.allCitiesInWorld());
        System.out.println("<<< All cities in the world by largest population to smallest >>>");
        a.printCities(citiesInWorld);
        System.out.println(line);

        ArrayList<City> citiesInContinent = a.getAllCities(a.allCitiesInContinent("Europe"));
        System.out.println("<<< All cities in the continent by largest population to smallest >>>");
        a.printCities(citiesInContinent);
        System.out.println(line);

        ArrayList<City> citiesInRegion = a.getAllCities(a.allCitiesInRegion("Polynesia"));
        System.out.println("<<< All cities in the region by largest population to smallest >>>");
        a.printCities(citiesInRegion);
        System.out.println(line);

        ArrayList<City> citiesInCountry = a.getAllCities(a.allCitiesInCountry("Argentina"));
        System.out.println("<<< All cities in the country by largest population to smallest >>>");
        a.printCities(citiesInCountry);
        System.out.println(line);

        ArrayList<City> citiesInDistrict = a.getAllCities(a.allCitiesInDistrict("Limburg"));
        System.out.println("<<< All cities in the district by largest population to smallest >>>");
        a.printCities(citiesInDistrict);
        System.out.println(line);

        // top N populated cities in the world
        ArrayList<City> topCitiesInWorld = a.getAllCities(a.topPopulatedCitiesInWorld(5)); // Change the number as needed
        System.out.println("<<< Top N populated cities in the world >>>");
        a.printCities(topCitiesInWorld);
        System.out.println(line);

        // top N populated cities in a specific continent (e.g., Asia)
        ArrayList<City> topCitiesInContinent = a.getAllCities(a.topPopulatedCitiesInContinent(5, "Asia")); // Change the number as needed
        System.out.println("<<< Top N populated cities in the continent (Asia) >>>");
        a.printCities(topCitiesInContinent);
        System.out.println(line);

        //top N populated cities in a specific region (e.g., Southeast Asia)
        ArrayList<City> topCitiesInRegion = a.getAllCities(a.topPopulatedCitiesInRegion(5, "Southeast Asia")); // Change the number as needed
        System.out.println("<<< Top N populated cities in the region (Southeast Asia) >>>");
        a.printCities(topCitiesInRegion);
        System.out.println(line);

        // top N populated cities in a specific country (e.g., "United States")
        ArrayList<City> topPopulatedCitiesInCountry = a.getAllCities(a.topPopulatedCitiesInCountry(5, "United States")); // Change the number as needed
        System.out.println("<<< Top N populated cities in the country (United States) >>>");
        a.printCities(topPopulatedCitiesInCountry);
        System.out.println(line);

        // top N populated cities in a specific district (e.g., "Buenos Aires")
        ArrayList<City> topCitiesInDistrict = a.getAllCities(a.topPopulatedCitiesInDistrict(5, "Buenos Aires")); // Change the number as needed
        System.out.println("<<< Top N populated cities in the district (Buenos Aires) >>>");
        a.printCities(topCitiesInDistrict);
        System.out.println(line);

        ArrayList<Capital> capitalCitiesInWorld = a.getAllCapitalCities(a.allCapitalCitiesInWorld());
        System.out.println("<<< All capital cities in the world by largest Population to smallest >>>");
        a.printCapitalCities(capitalCitiesInWorld);
        System.out.println(line);

        ArrayList<Capital> capitalCitiesInContinent = a.getAllCapitalCities(a.allCapitalCitiesInContinent("Asia"));
        System.out.println("<<< All capital cities in a continent by largest Population to smallest >>>");
        a.printCapitalCities(capitalCitiesInContinent);
        System.out.println(line);

        ArrayList<Capital> capitalCitiesInRegion = a.getAllCapitalCities(a.allCapitalCitiesInRegion("Southeast Asia"));
        System.out.println("<<< All capital cities in a region by largest Population to smallest >>>");
        a.printCapitalCities(capitalCitiesInRegion);
        System.out.println(line);

        ArrayList<Capital> populatedCapitalCitiesInWorld = a.getAllCapitalCities(a.topPopulatedCapitalCitiesInWorld(5));
        System.out.println("<<< Top N populated capital cities in the world >>>");
        a.printCapitalCities(populatedCapitalCitiesInWorld);
        System.out.println(line);

        ArrayList<Capital> populatedCapitalCitiesInContinent = a.getAllCapitalCities(a.topPopulatedCapitalCitiesInContinent(5, "Europe"));
        System.out.println("<<< Top N populated capital cities in the continent >>>");
        a.printCapitalCities(populatedCapitalCitiesInContinent);
        System.out.println(line);

        ArrayList<Capital> populatedCapitalCitiesInRegion = a.getAllCapitalCities(a.topPopulatedCapitalCitiesInRegion(5,"Caribbean"));
        System.out.println("<<< Top N populated capital cities in the region >>>");
        a.printCapitalCities(populatedCapitalCitiesInRegion);
        System.out.println(line);

        // Disconnect from database
        a.disconnect();
    }
}