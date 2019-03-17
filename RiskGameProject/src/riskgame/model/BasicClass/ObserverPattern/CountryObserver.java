package riskgame.model.BasicClass.ObserverPattern;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * this class will be instantiate in class player
 * serving as a observer to update changes triggered by countries
 */
public class CountryObserver implements Observer {
    private ArrayList<String> ownedCountryNameList;
    private int armyNbr;

    @Override
    public void update(Observable o, Object arg) {

    }
}
