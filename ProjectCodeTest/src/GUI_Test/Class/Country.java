package GUI_Test.Class;

import java.util.ArrayList;
import java.util.Random;

public class Country {
    String countryname;
    String continentname;
    int armies;
    ArrayList<String> adjacentCountries;

    public Country(String countryname) {
        this.countryname = countryname;
        this.armies = 1;
        this.continentname = null;
        this.adjacentCountries = new ArrayList<>();
    }


    public String getCountryname() {
        return countryname;
    }

    public int getArmies() {
        return armies;
    }

    public String getContinentname() {
        return continentname;
    }

    public ArrayList<String> getAdjacentCountries() {
        return adjacentCountries;
    }

    public void setAdjacentCountries(ArrayList<String> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public void setContinentname(String continentname) {
        this.continentname = continentname;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    public void addArmies(int armies) { this.armies += armies;}

}

