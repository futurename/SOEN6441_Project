package riskgame.model.BasicClass;


import javafx.scene.paint.Color;
import riskgame.Main;
import riskgame.model.Utils.AttackResultProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class includes attributes a player need and required methods
 **/
public class Player extends Observable {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int armyNbr;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;
    private int ownedCountryNbr;
    private int continentBonus;

    /**
     * class contructor
     *
     * @param playerIndex index of current player
     */
    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.armyNbr = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
        this.ownedCountryNbr = 0;
        this.continentBonus = 0;


        this.cardsList.add(Card.ARTILLERY);
        this.cardsList.add(Card.INFANTRY);
        this.cardsList.add(Card.ARTILLERY);
        this.cardsList.add(Card.CAVALRY);
        this.cardsList.add(Card.INFANTRY);
    }


    public int getContinentBonus() {
        return continentBonus;
    }

    public void addContinentBonus(int bonus) {
        this.continentBonus += bonus;
    }

    public void reduceContinentBonus(int bonus) {
        this.continentBonus -= bonus;
    }

    /**
     * get the sum of army number in all owned countries
     */
    public void updateArmyNbr() {
        int result = 0;
        for (String countryName : ownedCountryNameList) {
            Country country = Main.graphSingleton
                    .get(countryName)
                    .getCountry();
            result += country.getCountryArmyNumber();
        }
        setArmyNbr(result);
    }

    /**
     * getter
     *
     * @return country number the player owns
     */
    public int getOwnedCountryNbr() {
        ownedCountryNbr = ownedCountryNameList.size();
        return ownedCountryNbr;
    }

    public void setOwnedCountryNbr(int ownedCountryNbr) {
        this.ownedCountryNbr = ownedCountryNbr;
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
     * setter
     *
     * @param armyNbr army number to be set
     */
    public void setArmyNbr(int armyNbr) {
        this.armyNbr = armyNbr;
    }

    /**
     * getter
     *
     * @return army number the player has
     */
    public int getArmyNbr() {
        return armyNbr;
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
     * Processing attack with following the game rules:
     * <p>
     * if attacker army number is equal or greater than three, attacker will roll three dices
     * if defender army number is equal or greater than two, defender will roll two dices
     * if army number is less than above requirements, it will roll the dices as many times as the remaining army number
     * compare the best dice with both sides then compare the second best dicewdd
     * deduct army number from the losing side
     * </p>
     *
     * @param attackingCountry
     * @param defendingCountry the defending country ojbect
     */
    public void attckCountry(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr) {
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int attackerIndex = attackingCountry.getCountryOwnerIndex();
        String continentName = defendingCountry.getContinentName();
        Continent curContinent = Main.worldContinentMap.get(continentName);

        ArrayList<Integer> attackerDiceResultList = getDiceResultList(attackArmyNbr);
        ArrayList<Integer> defenderDiceResultList = getDiceResultList(defendArmyNbr);

        System.out.println("attackerDiceList: " + attackerDiceResultList);
        System.out.println("defenderDiceResult:" + defenderDiceResultList);


        int compareTimes = defenderDiceResultList.size() > 1 ? 2 : 1;

        for (int i = 0; i < compareTimes; i++) {
            int bestAttackerDice = attackerDiceResultList.remove(0);
            int bestDefenderDice = defenderDiceResultList.remove(0);

            System.out.println("\nattacker: " + attackingCountry.getCountryArmyNumber()
                    + ", defender: " + defendingCountry.getCountryArmyNumber());

            if (bestAttackerDice > bestDefenderDice) {
                defendingCountry.reduceFromCountryArmyNumber(1);

                System.out.println("Attacker win! attacker: " + attackingCountry.getCountryArmyNumber()
                        + ", defender: " + defendingCountry.getCountryArmyNumber());

            } else {
                attackingCountry.reduceFromCountryArmyNumber(1);

                System.out.println("Defender win! attacker: " + attackingCountry.getCountryArmyNumber()
                        + ", defender: " + defendingCountry.getCountryArmyNumber());
            }
        }

        if (AttackResultProcess.isCountryConquered(defendingCountry)) {
            defendingCountry.setCountryOwnerIndex(attackingCountry.getCountryOwnerIndex());


            if (AttackResultProcess.isContinentConquered(defenderIndex, continentName)) {
                Player defenderPlayer = Main.playersList.get(defenderIndex);

                int continentBonus = curContinent.getContinentBonusValue();

                defenderPlayer.reduceContinentBonus(continentBonus);
                curContinent.setContinentOwnerIndex(-1);

                if (curContinent.getContinentCountryNameList().size() == 1) {
                    curContinent.setContinentOwnerIndex(attackerIndex);
                }

            }
            if (AttackResultProcess.isContinentConquered(attackerIndex, continentName)) {
                curContinent.setContinentOwnerIndex(attackerIndex);
                Player attackerPlayer=Main.playersList.get(attackerIndex);

                int continentBonus = curContinent.getContinentBonusValue();

                attackerPlayer.addContinentBonus(continentBonus);

            }
        } else {

        }


        int defenderDiceNumber = 0;
        int attackerBestDice = 0;
        int defenderBestDice = 0;
        int defenderSecondBestDice = 0;
        int attackerSecondBestDice = 0;
        List<Integer> attackerDiceList;
        List<Integer> defenderDiceList;

        /**
         * if defendinf army number is less than 1 , attacker conquered the country
         */

        /*if(defendingArmyNbr < 1 ) {
            return attackerIndex;
        }

        if (defendingArmyNbr >= 2 && attackerDiceNumber >= 2) {
            defenderDiceNumber = 2;
        } else {
            defenderDiceNumber = 1;
        }
        *//**
         * attacker select dice
         *//*

        attackerDiceList = getDiceList(attackerDiceNumber);
        defenderDiceList = getDiceList(defenderDiceNumber);

        attackerBestDice = attackerDiceList.get(0);
        if (attackerDiceList.size() > 1) {
            attackerSecondBestDice = attackerDiceList.get(1);
        }
        defenderBestDice = defenderDiceList.get(0);
        if (defenderDiceList.size() > 1) {
            defenderSecondBestDice = defenderDiceList.get(1);
        }

        switch (defenderDiceNumber) {
            case 1:
                if(attackerBestDice > defenderBestDice) {
                    defendingArmyNbr--;
                    return attackerIndex;
                } else {
                    attackingArmyNbr--;
                    return  defenderIndex;
                }

            case 2:
                if(attackerBestDice > defenderBestDice) {
                    defendingArmyNbr--;
                } else {
                    attackingArmyNbr--;
                }

                if(attackerSecondBestDice > defenderSecondBestDice) {
                    defendingArmyNbr--;
                    return attackerIndex;
                } else {
                    attackingArmyNbr--;
                    return  defenderIndex;
                }
        }
        return 0;*/
    }


    /**
     * get the random dice result list
     *
     * @param diceTimes number of dicing rolls
     * @return a new arrayList of Integer
     */
    private ArrayList<Integer> getDiceResultList(int diceTimes) {
        ArrayList<Integer> result;
        Dice dice = new Dice();
        result = dice.rollNDice(diceTimes);

        return result;
    }


    /**
     * public method for setting observable objects value.
     * Adding a new card to player
     *
     * @param newCard card
     */
    public void setObservableCard(Card newCard) {
        cardsList.add(newCard);
        setChanged();
    }

    public void initObservableCard() {
        setChanged();
    }

    /**
     * public method for setting observable objects value.
     * Removing a set of cards from player
     *
     * @param cards iterable cards, e.g. ObservableList
     */
    public void removeObservableCards(Iterable<Card> cards) {
        for (Card card : cards) {
            this.cardsList.remove(card);
        }
        setChanged();
    }
}




