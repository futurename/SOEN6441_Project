package riskgame.classes;

import java.util.ArrayList;

/**
 *
 */
public class Continent {
    private final String continentName;
    private int continentOwnerIndex;
    private final int continentBonusValue;
    private ArrayList<String> continentCountryNameList;

    public Continent(String continentName, int continentBonusValue) {
        this.continentName = continentName;
        this.continentOwnerIndex = -1;
        this.continentBonusValue = continentBonusValue;
        this.continentCountryNameList = new ArrayList<>();
    }

    public String getContinentName() {
        return continentName;
    }

    public int getContinentOwnerIndex() {
        return continentOwnerIndex;
    }

    public void setContinentOwnerIndex(int continentOwnerIndex) {
        this.continentOwnerIndex = continentOwnerIndex;
    }

    public int getContinentBonusValue() {
        return continentBonusValue;
    }

    public ArrayList<String> getContinentCountryNameList() {
        return continentCountryNameList;
    }

    public void addToContinentCountryNameList(String countryName) {
        this.continentCountryNameList.add(countryName);
    }
}
