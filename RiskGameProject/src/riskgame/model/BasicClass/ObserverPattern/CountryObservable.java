package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;

public class CountryObservable extends Observable {
    private int countryOwnerIndex;
    private int countryArmyNumber;
    private String countryName;

    public CountryObservable(String name, int id, int army){
        countryOwnerIndex = id;
        countryArmyNumber = army;
        this.countryName = name;
    }

    public void setName(String countryName) {
        this.countryName = countryName;
        setChanged();
    }

    public void setOwner(int countryOwnerIndex) {
        this.countryOwnerIndex = countryOwnerIndex;
        setChanged();
    }

    public void setArmy(int countryArmyNumber) {
        this.countryArmyNumber = countryArmyNumber;
        setChanged();
    }

    public int getOwner() {
        return countryOwnerIndex;
    }

    public int getArmy() {
        return countryArmyNumber;
    }

    public String getName() {
        return countryName;
    }
}
