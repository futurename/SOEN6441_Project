package GUI_Test.Class;

import java.util.Random;

public class Country {
    String countryname;
    Continent continentname;
    int armies;

    public Country(String countryname) {
        this.countryname = countryname;
        this.armies = new Random().nextInt(10) + 1;
    }


    public String getCountryname() {
        return countryname;
    }

    public int getArmies() {
        return armies;
    }
}
