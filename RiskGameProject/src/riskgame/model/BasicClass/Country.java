package riskgame.model.BasicClass;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;



public class Country extends Observable {
    private final String countryName;
    private String continentName;
    private String coordinateX;
    private String coordinateY;
    private int countryOwnerIndex;
    private int countryArmyNumber;

    /**
     * constructor for class Country
     *
     * @param countryName string of a country name
     */
    public Country(String countryName) {
        this.countryName = countryName;
        this.continentName = null;
        this.coordinateX = "";
        this.coordinateY = "";
        this.countryOwnerIndex = -1;
        this.countryArmyNumber = 1;
    }

    public Country(String countryName, String continentName) {
        this.countryName = countryName;
        this.continentName = null;
        this.coordinateX = "";
        this.coordinateY = "";
        this.countryOwnerIndex = -1;
        this.countryArmyNumber = 1;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void addCountryObserver(Observer observer) {
        this.addObserver(observer);
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryOwnerIndex() {
        return countryOwnerIndex;
    }

    public void setCountryOwnerIndex(int countryOwnerIndex) {
        this.countryOwnerIndex = countryOwnerIndex;
        this.setChanged();
        this.notifyObservers();
    }

    public int getCountryArmyNumber() {
        return countryArmyNumber;
    }

    public boolean addToCountryArmyNumber(int addedNumber) {
        boolean result = false;
        if (this.countryArmyNumber >= 0) {
            this.countryArmyNumber += addedNumber;
            result = true;
            this.setChanged();
            this.notifyObservers();
        }
        return result;
    }

    public boolean reduceFromCountryArmyNumber(int reducedNumber) {
        boolean result = false;
        if (this.countryArmyNumber >= reducedNumber) {
            this.countryArmyNumber -= reducedNumber;
            result = true;
            this.setChanged();
            this.notifyObservers();
        }
        return result;
    }

    @Override
    public String toString() {
        return getCountryName() + " : " + getCountryArmyNumber();
    }
}
