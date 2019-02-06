package riskgame.classes;


import java.util.ArrayList;

public class Country {
    private final String countryName;
    private final String continentName;
    private int countryOwnerIndex;
    private int countryArmyNumber;
    private ArrayList<String> adjacentCountryNameList;

    public String getContinentName() {
        return continentName;
    }

    public Country(String countryName, String continentName) {
        this.countryName = countryName;
        this.continentName = continentName;
        this.countryOwnerIndex = -1;
        this.countryArmyNumber = 1;
        this.adjacentCountryNameList = new ArrayList<>();
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryOwnerIndex() {
        return countryOwnerIndex;
    }

    public void setCountryOwnerIndex(int countryOwnerIndex) {
        this.countryOwnerIndex = countryOwnerIndex;
    }

    public int getCountryArmyNumber() {
        return countryArmyNumber;
    }

    public boolean addToCountryArmyNumber(int addedNumber) {
        boolean result = false;
        if(this.countryArmyNumber >= 0) {
            this.countryArmyNumber += addedNumber;
            result = true;
        }
        return result;
    }

    public boolean reduceFromCountryArmyNumber(int reducedNumber){
        boolean result = false;
        if(this.countryArmyNumber >= reducedNumber){
            this.countryArmyNumber -= reducedNumber;
            result = true;
        }
        return result;
    }

    public ArrayList<String> getAdjacentCountryNameList() {
        return adjacentCountryNameList;
    }

    public void addToAdjacentCountryNameList(String adjacentCountryName) {
        this.adjacentCountryNameList.add(adjacentCountryName);
    }
}
