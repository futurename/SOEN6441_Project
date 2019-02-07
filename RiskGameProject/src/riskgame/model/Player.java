package riskgame.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Player {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int reinforcementArmyCount;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;

    public Color getPlayerColor() {
        return playerColor;
    }

    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.reinforcementArmyCount = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
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
}
