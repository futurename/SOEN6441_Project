package riskgame.model.BasicClass;


import javafx.scene.paint.Color;
import riskgame.Main;
import riskgame.model.BasicClass.Observer.CountryChangedObserver;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class Player {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int reinforcementArmyCount;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;
    private CountryChangedObserver countryChangedObserver;


    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.reinforcementArmyCount = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
        this.countryChangedObserver = new CountryChangedObserver(playerIndex);
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getReinforcementArmyCount() {
        return reinforcementArmyCount;
    }

    public void setReinforcementArmyCount(int reinforcementArmyCount) {
        this.reinforcementArmyCount = reinforcementArmyCount;
    }

    public ArrayList<Card> getCardsList() {
        return cardsList;
    }

    public void addToCardsList(Card oneCard) {
        this.cardsList.add(oneCard);
    }

    public ArrayList<String> getOwnedCountryNameList() {
        return ownedCountryNameList;
    }

    public void addToOwnedCountryNameList(String countryName) {
        this.ownedCountryNameList.add(countryName);
    }

    public CountryChangedObserver getCountryChangedObserver() {
        return countryChangedObserver;
    }
}
