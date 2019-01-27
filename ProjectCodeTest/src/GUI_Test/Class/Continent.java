package GUI_Test.Class;

import java.util.ArrayList;

public class Continent {
    String continentname;
    int numOfCountries;
    int bonusValue;
    ArrayList<String> countryList;
    int ownedByPlayerNum;

    public Continent(String name){
        this.continentname = name;
        this.numOfCountries = 0;
        this.bonusValue = 0;
        this.countryList = new ArrayList<>();
        this.ownedByPlayerNum = -1;
    }

    public void setNumOfCountries(int numOfCountries) {
        this.numOfCountries = numOfCountries;
    }

    public void setBonusValue(int bonusValue) {
        this.bonusValue = bonusValue;
    }

    public void setCountryList(ArrayList<String> countryList) {
        this.countryList = countryList;
    }

    public String getContinentname() {
        return continentname;
    }

    public int getNumOfCountries() {
        return numOfCountries;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    public ArrayList<String> getCountryList() {
        return countryList;
    }
}
