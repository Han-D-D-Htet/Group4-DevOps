package com.napier.sem;

public class Language {

    // language name
    private String language;

    // number of people speaking the language
    private String totalLanguageSpeaker;

    // percentage of world population
    private String worldPopPercentage;

    // getters
    public String getLanguage() {
        return language;
    }

    public String getTotalLanguageSpeaker() {
        return totalLanguageSpeaker;
    }

    public String getWorldPopPercentage() {
        return worldPopPercentage;
    }

    // setters
    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTotalLanguageSpeaker(String totalLanguageSpeaker) {
        this.totalLanguageSpeaker = totalLanguageSpeaker;
    }

    public void setWorldPopPercentage(String worldPopPercentage) {
        this.worldPopPercentage = worldPopPercentage;
    }
}
