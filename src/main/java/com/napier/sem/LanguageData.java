package com.napier.sem;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class LanguageData {

    private final Connection con;

    public LanguageData(Connection con) {
        this.con = con;
    }

    /**
     * get the language information as an arraylist
     */
    public ArrayList<Language> getLanguageInformation()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // query for language information
            String query = "SELECT countrylanguage.Language, SUM(countrylanguage.Percentage * country.Population / 100) as speakers, (SELECT SUM(Population) FROM country) as worldPopulation "
                    + "FROM country, countrylanguage "
                    + "WHERE country.Code = countrylanguage.CountryCode AND countrylanguage.language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') "
                    + "GROUP BY countrylanguage.Language ORDER BY speakers DESC";
            // Execute SQL statement
            ResultSet res = stmt.executeQuery(query);
            // creating an arraylist for language data
            ArrayList<Language> languages = new ArrayList<>();
            // use number format to format integers
            NumberFormat nf = NumberFormat.getNumberInstance();
            while (res.next())
            {
                // creating a language object
                Language lan = new Language();
                lan.setLanguageName(res.getString("Language"));
                long totalSpeakers = Math.round(res.getDouble("speakers"));
                lan.setTotalLanguageSpeaker(nf.format(totalSpeakers));
                long worldPopulation = Math.round(res.getDouble("worldPopulation"));
                double worldPopPercent = (double) totalSpeakers / worldPopulation;
                lan.setWorldPopPercentage(Math.round(worldPopPercent *100)+"%");
                languages.add(lan);
            }
            return languages;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details");
            return null;
        }
    }

    /**
     * Prints language information.
     * @param languages The languages to print.
     */
    public void printLanguage(ArrayList<Language> languages) {
        // Check languages is not null
        if (languages == null)
        {
            System.out.println("No language information found");
            return;
        }
        // Print header for language information
        System.out.println(String.format("%-15s %-20s %-20s",
                "Language", "Total Speakers", "World Population(%)"));
        System.out.println(String.format("%-15s %-20s %-20s",
                "--------", "--------------", "-------------------"));

        // Loop over all languages in the list
        for (Language lan : languages) {
            if (lan == null)
                continue;
            String lan_string =
                    String.format("%-15s %-20s %-20s",
                            lan.getLanguageName(), lan.getTotalLanguageSpeaker(), lan.getWorldPopPercentage());
            System.out.println(lan_string);
        }
    }
}
