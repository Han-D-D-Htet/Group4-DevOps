package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;
    static CountryData cd;
    static CityData ct;
    static CapitalData cap;
    static Connection con;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);
        con = app.getCon();
        cd = new CountryData(con);
        ct = new CityData(con);
        cap = new CapitalData(con);
    }

    /**
     * integration tests for countries report
     */
    @Test
    void testGetAllCountriesInWorld() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInWorld());
        Country cty = countries.getFirst();
        assertEquals(cty.getCode(), "CHN");
        assertEquals(cty.getName(), "China");
        assertEquals(cty.getContinent(), "Asia");
        assertEquals(cty.getRegion(), "Eastern Asia");
        assertEquals(cty.getPopulation(), "1,277,558,000");
        assertEquals(cty.getCapital(), "Peking");
    }

    @Test
    void testTopPopulatedCountriesInWorld() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInWorld(5));
        Country cty = countries.get(2);
        assertEquals(cty.getCode(), "USA");
        assertEquals(cty.getName(), "United States");
        assertEquals(cty.getContinent(), "North America");
        assertEquals(cty.getRegion(), "North America");
        assertEquals(cty.getPopulation(), "278,357,000");
        assertEquals(cty.getCapital(), "Washington");
    }

    @Test
    void testGetAllCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInContinent("Europe"));
        Country cty = countries.getFirst();
        assertEquals(cty.getCode(), "RUS");
        assertEquals(cty.getName(), "Russian Federation");
        assertEquals(cty.getContinent(), "Europe");
        assertEquals(cty.getRegion(), "Eastern Europe");
        assertEquals(cty.getPopulation(), "146,934,000");
        assertEquals(cty.getCapital(), "Moscow");
    }

    @Test
    void testTopPopulatedCountriesInContinent() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInContinent(5,"Asia"));
        assertEquals(5, countries.size());
    }

    @Test
    void testGetAllCountriesInRegion() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInRegion("Southeast Asia"));
        Country cty = countries.getLast();
        assertEquals(cty.getCode(), "BRN");
        assertEquals(cty.getName(), "Brunei");
        assertEquals(cty.getContinent(), "Asia");
        assertEquals(cty.getRegion(), "Southeast Asia");
        assertEquals(cty.getPopulation(), "328,000");
        assertEquals(cty.getCapital(), "Bandar Seri Begawan");
    }

    @Test
    void testTopPopulatedCountriesInRegion() {
        ArrayList<Country> countries = cd.getAllCountries(cd.topPopulatedCountriesInRegion(5,"Southeast Asia"));
        Country cty = countries.get(1);
        assertEquals(cty.getCode(), "VNM");
        assertEquals(cty.getName(), "Vietnam");
        assertEquals(cty.getContinent(), "Asia");
        assertEquals(cty.getRegion(), "Southeast Asia");
        assertEquals(cty.getPopulation(), "79,832,000");
        assertEquals(cty.getCapital(), "Hanoi");
    }


}