package riskgame.model.BasicClass;


import javafx.scene.paint.Color;
import riskgame.Main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class includes attributes a player need and required methods
 **/
public class Player {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int armyNbr;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;
    private int ownedCountryNbr;

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
     * compare the best dice with both sides then compare the second best dice
     * deduct army number from the losing side
     * </p>
     *
     * @param attackingCountry
     * @param defendingCountry the defending country ojbect
     */
    public void attckCountry(Country attackingCountry, Country defendingCountry,int attackerDiceNumber) {
        int defenderIndex = defendingCountry.getCountryOwnerIndex();
        int defendingArmyNbr = defendingCountry.getCountryArmyNumber();
        int attackingArmyNbr = attackingCountry.getCountryArmyNumber();
        int attackerDice=0;
        int defenderDice=0;
        int attackerBestDice = 0;
        int defenderBestDice = 0;
        int defenderSecondBestDice = 0;
        int attackerSecondBestDice = 0;
        ArrayList<Integer> list=new ArrayList<Integer>();

        Dice dice = new Dice(128);

        /**
         * calculating attacker best dice  when attacker country number is greater than 3 and attacker decides dice number
         */
        if (attackingArmyNbr >= 3)
        {
            while (attackerDiceNumber <=3 && attackerDiceNumber>0)
            {
                for (int roll = 0; roll <= attackerDiceNumber; roll++)
                {
                    attackerDice = dice.rollADice();
                    list.add(attackerDice);
                }
                Collections.sort(list);
                attackerBestDice = list.get(list.size() - 1);
                attackerSecondBestDice = list.get(list.size() - 2);
            }
        }
        /**
         * calculating attacker best dice  when attacker country number is less than  3
         */
        if (attackingArmyNbr>0 && attackingArmyNbr<=2 )
        {
            while (attackerDiceNumber <=2)
                for (int turn = 1; turn <=attackerDiceNumber; turn++) {
                    attackerDice = dice.rollADice();
                    list.add(attackerDice);
                }
            Collections.sort(list);
            attackerBestDice = list.get(list.size() - 1);
            attackerSecondBestDice = list.get(list.size() - 2);
        }
        /**
         * calculating defender best dice when defender country number is less than 1
         */
         if (defendingArmyNbr < 1) {
            /**attacker conquerd that country
             *
             */
        }
         /**
          * calculating attacker best dice  when defender country number is 1
          */
         else if (defendingArmyNbr == 1) {
             defenderBestDice = dice.rollADice();
         }
        /**
         * calculating defender best dice when defender country number is less than 1
         */
        else
        if (defendingArmyNbr >= 2) {
            for (int turn = 1; turn <=2; turn++) {
                defenderDice = dice.rollADice();
                list.add(defenderDice);
            }
            Collections.sort(list);
            defenderBestDice = list.get(list.size() - 1);
            defenderSecondBestDice = list.get(list.size() - 2);
        }
        /**
         * comparing attacker and defender best dice when defending army number is one
         */
        if (defendingArmyNbr == 1) {
            if (defenderBestDice > attackerBestDice) {
                /**
                 * defender wins and attacker loses one army
                 */
            }
        }
        /**
         * comparing attacker and defender best dice when defending army number greater one and attacking army number is eual and greater than three
         */
        else if (defendingArmyNbr > 1 && attackingArmyNbr >= 2) {
            if (attackerBestDice > defenderBestDice) {
                /**
                 * attacker wins and defender loses one army
                 */

            } else {
                /**
                 * defender wins and attacker loses one army
                 */
            }
            if (attackerSecondBestDice > defenderSecondBestDice) {
                /**
                 * attacker again wins and defender loses one army
                 */

            } else {
                /**
                 * defender wins and attacker loses one army
                 */
            }
        }
        /**
         * comparing attacker and defender best dice when attacking army number is less than 3
         */
        if (defendingArmyNbr > 1 && attackingArmyNbr < 2) {
            if (attackerBestDice > defenderBestDice) {
                /**
                 *  attacker wins and defender loses one army
                 */
            } else {
                /**
                 * defender wins and attacker loses one army
                 */
            }
        }

    }
}




