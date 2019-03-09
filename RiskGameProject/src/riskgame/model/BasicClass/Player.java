package riskgame.model.BasicClass;


import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class includes attributes a player need and required methods
 **/
public class Player{
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int reinforcementArmyCount;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;



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
        int attackingArmyNbr = attackingCountry.getCountryArmyNumber();int attackerFirstDice = 0;
        int attackerSecondDice = 0;
        int attackerThirdDice = 0;
        int attackerBestDice = 0;
        int defenderFirstDice = 0;
        int defenderSecondDice = 0;
        int defenderBestDice = 0;
        int defenderSecondBestDice = 0;
        int attackerSecondBestDice = 0;

        Dice dice = new Dice(128);

        /**
         * calculating attacker best dice and second best dice when attacker country number is greater than 3
         */
        if (attackingArmyNbr >= 3)
        {
            attackerFirstDice = dice.rollADice();
            attackerSecondDice = dice.rollADice();
            attackerThirdDice = dice.rollADice();
            /**
             * calculating attacker best dice when attacker country number is greater than 3
             */
            if (attackerFirstDice > attackerSecondDice && attackerFirstDice > attackerSecondDice) {
                attackerBestDice = attackerFirstDice;
            } else if (attackerSecondDice > attackerFirstDice && attackerSecondDice > attackerThirdDice) {
                attackerBestDice = attackerSecondDice;
            } else if (attackerThirdDice > attackerSecondDice && attackerThirdDice > attackerFirstDice) {
                attackerBestDice = attackerThirdDice;
            }
            /**
             * calculating attacker second best dice when attacker country number is greater than 3
             */
            if ((attackerFirstDice < attackerSecondDice && attackerSecondDice < attackerThirdDice) || (attackerThirdDice < attackerSecondDice && attackerSecondDice < attackerFirstDice)) {
                attackerSecondBestDice = attackerSecondDice;
            } else if ((attackerSecondDice < attackerFirstDice && attackerFirstDice < attackerThirdDice) || (attackerThirdDice < attackerFirstDice && attackerFirstDice < attackerSecondDice)) {
                attackerSecondBestDice = attackerFirstDice;
            } else {
                attackerSecondBestDice = attackerThirdDice;
            }
        }
        /**
         * * * calculating attacker best dice  when attacker country number is 1
         */
        else if (attackingArmyNbr == 1) {
            attackerBestDice = dice.rollADice();
        }
        /**
         * calculating attacker best dice  when attacker country number is 2
         */
        else if (attackingArmyNbr == 2)
        {
            attackerFirstDice = dice.rollADice();
            attackerSecondDice = dice.rollADice();
            if (attackerFirstDice > attackerSecondDice)
            {
                attackerBestDice = attackerFirstDice;
            } else
            {
                attackerBestDice = attackerSecondDice;
            }
        }
        /**
         * calculating defender best dice  when attacker country number greater than or equal 2
         */
        if (defendingArmyNbr >= 2) {
            defenderFirstDice = dice.rollADice();
            defenderSecondDice = dice.rollADice();
            if (defenderSecondDice > defenderFirstDice)
            {
                defenderBestDice = defenderSecondDice;
                defenderSecondBestDice = defenderFirstDice;
            } else
            {
                defenderBestDice = defenderFirstDice;
                defenderSecondBestDice = defenderSecondDice;
            }


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
        else if (defendingArmyNbr < 1)
        {
            /**attacker conquerd that country
             *
             */
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
        else if (defendingArmyNbr > 1 && attackingArmyNbr >=2) {
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
            }
            else {
                /**
                 * defender wins and attacker loses one army
                 */
            }
        }

    }
}




