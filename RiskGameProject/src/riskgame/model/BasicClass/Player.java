package riskgame.model.BasicClass;


import javafx.scene.paint.Color;
import riskgame.model.BasicClass.Observe.CountryChangedObserver;

import java.util.ArrayList;

/**
 * This class includes attributes a player need and required methods
 **/
public class Player {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int reinforcementArmyCount;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;
    private CountryChangedObserver countryChangedObserver;


    /**
     * class contructor
     *
     * @param playerIndex index of current player
     */
    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.reinforcementArmyCount = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
        this.countryChangedObserver = new CountryChangedObserver(playerIndex);
    }

    /**
     * getter
     *
     * @return color the player is preassigned
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * getter
     *
     * @return player index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * getter
     *
     * @return army number the player has
     */
    public int getReinforcementArmyCount() {
        return reinforcementArmyCount;
    }

    /**
     * getter
     *
     * @return arraylist of cards the player owns
     */
    public ArrayList<Card> getCardsList() {
        return cardsList;
    }

    /**
     * add a Card to the card list the player owns
     *
     * @param oneCard card object
     */
    public void addToCardsList(Card oneCard) {
        this.cardsList.add(oneCard);
    }

    /**
     * getter
     *
     * @return arraylist of country names the player owns
     */
    public ArrayList<String> getOwnedCountryNameList() {
        return ownedCountryNameList;
    }

    /**
     * add one country name to the list the player owns
     *
     * @param countryName country name
     */
    public void addToOwnedCountryNameList(String countryName) {
        this.ownedCountryNameList.add(countryName);
    }

    /**
     * observer method
     *
     * @return changer indicates change
     */
    public CountryChangedObserver getCountryChangedObserver() {
        return countryChangedObserver;
    }


    /**
     * Processing attack with following the game rules:
     * <p>
     * if attacker army number is equal or greater than three, attacker will roll three dices
     * if defender army number is equal or greater than two, defender will roll two dices
     * if army number is less than above requirements, it will roll the dices as many times as the remaining army number
     * compare the best dice with both sides then compare the second best dice
     * deduct army number from the losing side
     * </p>
     *
     * @param attackingCountry
     * @param defendingCountry the defending country ojbect
     */
    public void attckCountry(Country attackingCountry, Country defendingCountry) {
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int defendingArmyNbr = defendingCountry.getCountryArmyNumber();
        int attackingArmyNbr = attackingCountry.getCountryArmyNumber();


    }


}
