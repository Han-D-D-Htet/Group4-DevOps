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
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException e) {
                System.out.println("Failed to connect to database attempt " +  i);
                System.out.println(e.getMessage());
            } catch (InterruptedException ie) {
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

    public Connection getCon() {
        return con;
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect("localhost:33060", 10000);

        // division line between prints
        String line = "=".repeat(130);

        // variables
        int count = 5;
        String inputContinent = "Asia";
        String inputRegion = "Southeast Asia";
        String inputCountry = "China";
        String inputDistrict = "Buenos Aires";
        String inputCity = "Seoul";

        CountryData cd = new CountryData(a.con);
        //all country in world by largest population
        ArrayList<Country> countriesInWorld = cd.getAllCountries(cd.allCountriesInWorld());
        System.out.println("<<< 1. All countries in the world by largest population to smallest >>>");
        cd.printCountries(countriesInWorld);
        System.out.println(line);

        // All countries in a continent
        ArrayList<Country> countriesInContinent = cd.getAllCountries(cd.allCountriesInContinent(inputContinent));
        System.out.println("<<< 2. All countries in the " + inputContinent + " continent by largest population to smallest >>>");
        cd.printCountries(countriesInContinent);
        System.out.println(line);

        // All countries in a region
        ArrayList<Country> countriesInRegion = cd.getAllCountries(cd.allCountriesInRegion(inputRegion));
        System.out.println("<<< 3. All countries in the " + inputRegion + " region by largest population to smallest >>>");
        cd.printCountries(countriesInRegion);
        System.out.println(line);

        // Top N populated countries in the world
        ArrayList<Country> populatedCountriesInWorld = cd.getAllCountries(cd.topPopulatedCountriesInWorld(count));
        System.out.println("<<< 4. Top " + count + " populated countries in the world >>>");
        cd.printCountries(populatedCountriesInWorld);
        System.out.println(line);

        // Top N populated countries in a continent
        ArrayList<Country> populatedCountriesInContinent = cd.getAllCountries(cd.topPopulatedCountriesInContinent(count, inputContinent));
        System.out.println("<<< 5. Top " + count + " populated countries in the " + inputContinent + " continent >>>");
        cd.printCountries(populatedCountriesInContinent);
        System.out.println(line);

        // Top N populated countries in a region
        ArrayList<Country> populatedCountriesInRegion = cd.getAllCountries(cd.topPopulatedCountriesInRegion(count, inputRegion));
        System.out.println("<<< 6. Top " + count + " populated countries in the " + inputRegion + " region >>>");
        cd.printCountries(populatedCountriesInRegion);
        System.out.println(line);

        CityData cid = new CityData(a.con);
        //all cities in the world by largest population
        ArrayList<City> citiesInWorld = cid.getAllCities(cid.allCitiesInWorld());
        System.out.println("<<< 7. All cities in the world by largest population to smallest >>>");
        cid.printCities(citiesInWorld);
        System.out.println(line);

        // All capital cities in a continent
        ArrayList<City> citiesInContinent = cid.getAllCities(cid.allCitiesInContinent(inputContinent));
        System.out.println("<<< 8. All cities in the " + inputContinent + " continent by largest population to smallest >>>");
        cid.printCities(citiesInContinent);
        System.out.println(line);

        // All capital cities in a region
        ArrayList<City> citiesInRegion = cid.getAllCities(cid.allCitiesInRegion(inputRegion));
        System.out.println("<<< 9. All cities in the " + inputRegion + " region by largest population to smallest >>>");
        cid.printCities(citiesInRegion);
        System.out.println(line);

        // All capital cities in a country
        ArrayList<City> citiesInCountry = cid.getAllCities(cid.allCitiesInCountry(inputCountry));
        System.out.println("<<< 10. All cities in the " + inputCountry + " country by largest population to smallest >>>");
        cid.printCities(citiesInCountry);
        System.out.println(line);

        // All capital cities in a district
        ArrayList<City> citiesInDistrict = cid.getAllCities(cid.allCitiesInDistrict(inputDistrict));
        System.out.println("<<< 11. All cities in the " + inputDistrict + " by largest population to smallest >>>");
        cid.printCities(citiesInDistrict);
        System.out.println(line);

        // Top N populated cities in the world
        ArrayList<City> topCitiesInWorld = cid.getAllCities(cid.topPopulatedCitiesInWorld(count));
        System.out.println("<<< 12. Top " + count + " populated cities in the world >>>");
        cid.printCities(topCitiesInWorld);
        System.out.println(line);

        // Top N populated cities in a continent
        ArrayList<City> topCitiesInContinent = cid.getAllCities(cid.topPopulatedCitiesInContinent(count, inputContinent));
        System.out.println("<<< 13. Top " + count + " populated cities in the " + inputContinent + " continent>>>");
        cid.printCities(topCitiesInContinent);
        System.out.println(line);

        //top N populated cities in a region
        ArrayList<City> topCitiesInRegion = cid.getAllCities(cid.topPopulatedCitiesInRegion(count, inputRegion));
        System.out.println("<<< 14. Top " + count + " populated cities in the " + inputRegion + " region >>>");
        cid.printCities(topCitiesInRegion);
        System.out.println(line);

        // top N populated cities in a country
        ArrayList<City> topPopulatedCitiesInCountry = cid.getAllCities(cid.topPopulatedCitiesInCountry(count, inputCountry));
        System.out.println("<<< 15. Top " + count + " populated cities in the " + inputCountry + " country >>>");
        cid.printCities(topPopulatedCitiesInCountry);
        System.out.println(line);

        // top N populated cities in a district
        ArrayList<City> topCitiesInDistrict = cid.getAllCities(cid.topPopulatedCitiesInDistrict(count, inputDistrict));
        System.out.println("<<< 16. Top " + count + " populated cities in the " + inputDistrict + " district >>>");
        cid.printCities(topCitiesInDistrict);
        System.out.println(line);

        // All capital cities in the world
        CapitalData cpd = new CapitalData(a.con);
        ArrayList<Capital> capitalCitiesInWorld = cpd.getAllCapitalCities(cpd.allCapitalCitiesInWorld());
        System.out.println("<<< 17. All capital cities in the world by largest Population to smallest >>>");
        cpd.printCapitalCities(capitalCitiesInWorld);
        System.out.println(line);

        // All capital cities in a continent
        ArrayList<Capital> capitalCitiesInContinent = cpd.getAllCapitalCities(cpd.allCapitalCitiesInContinent(inputContinent));
        System.out.println("<<< 18. All capital cities in the " + inputContinent + " continent by largest Population to smallest >>>");
        cpd.printCapitalCities(capitalCitiesInContinent);
        System.out.println(line);

        // All capital cities in a region
        ArrayList<Capital> capitalCitiesInRegion = cpd.getAllCapitalCities(cpd.allCapitalCitiesInRegion(inputRegion));
        System.out.println("<<< 19. All capital cities in the " + inputRegion + " region by largest Population to smallest >>>");
        cpd.printCapitalCities(capitalCitiesInRegion);
        System.out.println(line);

        // Top N populated capital cities in the world
        ArrayList<Capital> populatedCapitalCitiesInWorld = cpd.getAllCapitalCities(cpd.topPopulatedCapitalCitiesInWorld(count));
        System.out.println("<<< 20. Top " + count + " populated capital cities in the world >>>");
        cpd.printCapitalCities(populatedCapitalCitiesInWorld);
        System.out.println(line);

        // Top N populated capital cities in a continent
        ArrayList<Capital> populatedCapitalCitiesInContinent = cpd.getAllCapitalCities(cpd.topPopulatedCapitalCitiesInContinent(count, inputContinent));
        System.out.println("<<< 21. Top " + count + " populated capital cities in the " + inputContinent + " continent >>>");
        cpd.printCapitalCities(populatedCapitalCitiesInContinent);
        System.out.println(line);

        // Top N populated capital cities in a region
        ArrayList<Capital> populatedCapitalCitiesInRegion = cpd.getAllCapitalCities(cpd.topPopulatedCapitalCitiesInRegion(count, inputRegion));
        System.out.println("<<< 22. Top " + count + " populated capital cities in the " + inputRegion + " region >>>");
        cpd.printCapitalCities(populatedCapitalCitiesInRegion);
        System.out.println(line);

        // The population of people, people living in cities, and people not living in cities in each continent.
        PopulationData pdt = new PopulationData(a.con);
        String query1 = pdt.totalPopulationInContinent(inputContinent);
        String query2 = pdt.totalPopulationLivingInCitiesInContinent(inputContinent);
        Population popInContinent = pdt.getPopulationInformation(query1, query2);
        System.out.println("<<< 23. Population information in " + inputContinent + " continent >>>");
        pdt.printPopulation(popInContinent);
        System.out.println(line);

        // The population of people, people living in cities, and people not living in cities in each region.
        String query3 = pdt.totalPopulationInRegion(inputRegion);
        String query4 = pdt.totalPopulationLivingInCitiesInRegion(inputRegion);
        Population popInRegion = pdt.getPopulationInformation(query3, query4);
        System.out.println("<<< 24. Population information in " + inputRegion + " region >>>");
        pdt.printPopulation(popInRegion);
        System.out.println(line);

        // The population of people, people living in cities, and people not living in cities in each country.
        String query5 = pdt.totalPopulationInCountry(inputCountry);
        String query6 = pdt.totalPopulationLivingInCitiesInCountry(inputCountry);
        Population popInCountry = pdt.getPopulationInformation(query5, query6);
        System.out.println("<<< 25. Population information in " + inputCountry + " country >>>");
        pdt.printPopulation(popInCountry);
        System.out.println(line);

        AdditionalPopulation add = new AdditionalPopulation(a.con);
        System.out.println("<<< Additional Population Information >>>");
        // Population of the world
        add.worldPopulation();

        // Population of a continent
        add.continentPopulation(inputContinent);

        //Population of a region
        add.regionPopulation(inputRegion);

        // Population of a country
        add.countryPopulation(inputCountry);

        // Population of a district
        add.districtPopulation(inputDistrict);

        // Population of a city
        add.cityPopulation(inputCity);

        System.out.println(line);

        // Disconnect from database
        a.disconnect();
    }
}