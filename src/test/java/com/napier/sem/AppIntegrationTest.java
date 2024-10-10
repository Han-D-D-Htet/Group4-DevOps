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

    @Test
    void testGetAllCountries() {
        ArrayList<Country> countries = cd.getAllCountries(cd.allCountriesInWorld());
        Country cty = countries.get(0);
        assertEquals(cty.getCode(), "CHN");
        assertEquals(cty.getName(), "China");
        assertEquals(cty.getContinent(), "Asia");
        assertEquals(cty.getRegion(), "Eastern Asia");
        assertEquals(cty.getPopulation(), "1,277,558,000");
        assertEquals(cty.getCapital(), "Peking");
    }
}