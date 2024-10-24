package com.napier.sem;

public class Language {

    // language name
    private String languageName;

    // number of people speaking the language
    private String totalLanguageSpeaker;

    // percentage of world population
    private String worldPopPercentage;

    // getters
    public String getLanguageName() {
        return languageName;
    }

    public String getTotalLanguageSpeaker() {
        return totalLanguageSpeaker;
    }

    public String getWorldPopPercentage() {
        return worldPopPercentage;
    }

    // setters
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setTotalLanguageSpeaker(String totalLanguageSpeaker) {
        this.totalLanguageSpeaker = totalLanguageSpeaker;
    }

    public void setWorldPopPercentage(String worldPopPercentage) {
        this.worldPopPercentage = worldPopPercentage;
    }
}
