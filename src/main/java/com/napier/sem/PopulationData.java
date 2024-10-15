package com.napier.sem;

import java.sql.*;
import java.text.NumberFormat;

public class PopulationData {

    private final Connection con;

    //Constructor
    public PopulationData(Connection con) {
        this.con = con;
    }

    /**
     * query for the sum of total population in a continent
     * @param continent The continent for total population.
     */
    public String totalPopulationInContinent(String continent) {
        return "SELECT country.Continent as popName, SUM(country.Population) as totalPopulation "
                + "FROM country, city "
                + "WHERE country.capital = city.ID AND country.Continent = '" + continent + "'";
    }

    /**
     * query for the sum of total population living in cities in a continent
     * @param continent The continent for total population.
     */
    public String totalPopulationLivingInCitiesInContinent(String continent) {
        return "SELECT country.Continent, SUM(city.Population) as totalCityPopulation "
                + "FROM country, city "
                + "WHERE country.Code = city.CountryCode AND country.Continent = '" + continent + "'";
    }

    /**
     * get the countries information as an arraylist
     * @param query1 the SQL query to calculate total population
     * @param query2 the SQL query to calculate total population living in cities
     */
    public Population getPopulationInformation(String query1, String query2) {
        try {
            // Create an SQL statement
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();
            // Execute first SQL statement for total population
            ResultSet res1 = stmt1.executeQuery(query1);
            // Execute second SQL statement for total population living in cities
            ResultSet res2 = stmt2.executeQuery(query2);
            // checking both results set
            if (res1.next() && res2.next())
            {
                // creating a population object
                Population pop = new Population();
                // long type for variable as it is a big number
                long intTotalPop = res1.getLong("totalPopulation");
                // total city population from result set 2
                long intTotalCityPop = res2.getLong("totalCityPopulation");
                // total population not living in cities
                long intTotalNotCityPop = intTotalPop - intTotalCityPop;
                // percentage of population living in cities
                String percentCityPop = Math.round(((double) intTotalCityPop / intTotalPop) * 100) + "%";
                // percentage of population not living in cities
                String percentNotCityPop = Math.round(((double) intTotalNotCityPop / intTotalPop) * 100) + "%";
                // setting values for population arraylist
                pop.setPopName(res1.getString("popName"));
                pop.setTotalPopulation(nf.format(intTotalPop));
                pop.setTotalPopulationCities(nf.format(intTotalCityPop));
                pop.setTotalPopulationNotCities(nf.format(intTotalNotCityPop));
                pop.setPercentageCityPopulation(percentCityPop);
                pop.setPercentageNotCityPopulation(percentNotCityPop);
                return pop;
            }
            else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");
            return null;
        }
    }

    /**
     * Prints population information in a continent, region or country.
     * @param pop The population information to print.
     */
    public void printPopulation(Population pop) {
        // Check population is not null
        if (pop != null) {
            // Print header for population information
            System.out.println(String.format("%-15s %-25s %-25s %-20s",
                    "Name", "Total Population", "Living in Cities", "Not living in Cities"));
            System.out.println(String.format("%-15s %-25s %-25s %-20s",
                    "----", "----------------", "----------------", "--------------------"));

            String cty_string =
                    String.format("%-15s %-25s %-25s %-20s",
                            pop.getPopName(), pop.getTotalPopulation() + "(" + pop.getPercentageCityPopulation() + ")", pop.getTotalPopulationCities(), pop.getTotalPopulationNotCities() + "(" + pop.getPercentageNotCityPopulation() + ")");
            System.out.println(cty_string);
        } else {
            System.out.println("No population information");
        }
    }

}

