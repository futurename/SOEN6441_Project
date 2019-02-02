package riskgame.classes;

import javafx.collections.ObservableList;

import java.util.ArrayList;


public class Player {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int reinforcementArmyCount;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;

    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.reinforcementArmyCount = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
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

    public void AddToOwnedCountryNameList(String countryName) {
        this.ownedCountryNameList.add(countryName);
    }
}
