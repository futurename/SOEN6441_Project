package riskgame.model.BasicClass;


import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import riskgame.Main;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.StrategyPattern.StrategyHuman;
import riskgame.model.Utils.AttackProcess;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static riskgame.controllers.AttackViewController.MAX_ATTACKING_ARMY_NUMBER;
import static riskgame.controllers.AttackViewController.MAX_DEFENDING_ARMY_NUMBER;

/**
 * This class includes attributes a player need and required methods
 * Observer property of this class is updating changes triggered by countries
 * As a observable object, player is observed by CardExchangeViewObserver for cards' changes
 *
 * @author Zhanfan Zhou, Karamveer, Wei Wang
 * @since build1
 **/
public class Player extends Observable implements Observer {
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    private final int playerIndex;
    private int armyNbr;
    private ArrayList<Card> cardsList;
    private ArrayList<String> ownedCountryNameList;
    private Color playerColor;
    //private int ownedCountryNbr;
    private int continentBonus;
    private ArrayList<String> controlledContinents;
    private boolean activeStatus;
    private boolean cardObtained;
    private int undeployedArmy;
    private Strategy strategy;

    /**
     * class constructor, player takes human strategy by default
     * @param playerIndex index of current player
     */
    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.armyNbr = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
        this.continentBonus = 0;
        this.controlledContinents = new ArrayList<>();
        this.activeStatus = true;
        this.cardObtained = false;
        this.undeployedArmy = 0;
        this.strategy = new StrategyHuman();
    }

    public Player(int playerIndex, Strategy type) {
        this.playerIndex = playerIndex;
        this.armyNbr = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
        this.continentBonus = 0;
        this.controlledContinents = new ArrayList<>();
        this.activeStatus = true;
        this.cardObtained = false;
        this.undeployedArmy = 0;
        this.strategy = type;
    }

    /**
     * this functions calculates the result for every single attack and returns the attacker's army number
     *
     * @param attackingCountry  attacking country
     * @param defendingCountry  defending country
     * @param attackableArmyNbr army number of attacking country
     * @param defendableArmyNbr army number of defending country
     * @param stringBuilder     stringbuilder for storing combat information
     * @return remaining army number of the attacker
     */
    private static int getOneAttackResult(Country attackingCountry, Country defendingCountry, int attackableArmyNbr, int defendableArmyNbr,
                                          StringBuilder stringBuilder) {
        int result = 0;

        ArrayList<Integer> attackerDiceResultList = AttackProcess.getDiceResultList(attackableArmyNbr);
        ArrayList<Integer> defenderDiceResultList = AttackProcess.getDiceResultList(defendableArmyNbr);

        stringBuilder
                .append("attacker dices: ")
                .append(attackerDiceResultList)
                .append(", defender dices: ")
                .append(defenderDiceResultList)
                .append("\n");

        System.out.println("\nattackerDiceList: " + attackerDiceResultList);
        System.out.println("defenderDiceResult:" + defenderDiceResultList + "\n");

        int attackArmyCount = attackerDiceResultList.size();
        int defendArmyCount = defenderDiceResultList.size();

        int compareTimes = defenderDiceResultList.size() < attackerDiceResultList.size() ? defenderDiceResultList.size() :
                attackerDiceResultList.size();

        for (int i = 0; i < compareTimes; i++) {

            System.out.println("\nattacker: " + attackerDiceResultList.size()
                    + ", defender: " + defenderDiceResultList.size());

            int bestAttackerDice = attackerDiceResultList.remove(0);
            int bestDefenderDice = defenderDiceResultList.remove(0);

            if (bestAttackerDice > bestDefenderDice) {
                defendingCountry.reduceFromCountryArmyNumber(1);

                System.out.println("Round: " + i + ", >>>Attacker win! attacker: " + attackingCountry.getCountryArmyNumber()
                        + ", defender: " + defendingCountry.getCountryArmyNumber() + "\n");

                defendArmyCount--;

                stringBuilder
                        .append("Round: ")
                        .append(i)
                        .append("\n>>>[ ")
                        .append(attackingCountry.getCountryName())
                        .append(" ]<<< WINS ")
                        .append(">>>[ ")
                        .append(defendingCountry.getCountryName())
                        .append(" ]<<<\nattacker remaining army num: ")
                        .append(attackingCountry.getCountryArmyNumber())
                        .append(", defender remaining army num: ")
                        .append(defendingCountry.getCountryArmyNumber())
                        .append("\n")
                        .append("defend army count after reduction: ")
                        .append(defendArmyCount)
                        .append(", attack army count: ")
                        .append(attackArmyCount)
                        .append("\n");
            } else {
                attackingCountry.reduceFromCountryArmyNumber(1);

                System.out.println("Round: " + i + ", >>>Defender win! attacker: " + attackingCountry.getCountryArmyNumber()
                        + ", defender: " + defendingCountry.getCountryArmyNumber() + "\n");

                attackArmyCount--;

                stringBuilder
                        .append("Round: ")
                        .append(i)
                        .append("\n>>>[ ")
                        .append(attackingCountry.getCountryName())
                        .append(" ]<<< LOSES to ")
                        .append(">>>[ ")
                        .append(defendingCountry.getCountryName())
                        .append(" ]<<<\nattacker remaining army num: ")
                        .append(attackingCountry.getCountryArmyNumber())
                        .append(", defender remaining army num: ")
                        .append(defendingCountry.getCountryArmyNumber())
                        .append("\n")
                        .append("attack army count after reduction: ")
                        .append(attackArmyCount)
                        .append(", defend army count: ")
                        .append(defendArmyCount)
                        .append("\n");
            }
        }
        result = attackArmyCount;

        return result;
    }

    public void addUndeployedArmy(int undeployedArmy) {
        this.undeployedArmy += undeployedArmy;
    }

    public void setCardPermission(boolean b) {
        this.cardObtained = b;
    }

    /**
     * @return get the bonus point of that continent
     */
    public int getContinentBonus() {
        return continentBonus;
    }

    /**
     * @param bonus added
     */
    public void addContinentBonus(int bonus) {
        this.continentBonus += bonus;
    }

    /**
     * reducing the bonus if loses
     *
     * @param bonus continent bonus to be reduced
     */
    public void reduceContinentBonus(int bonus) {
        this.continentBonus -= bonus;
    }

    public void addControlledContinent(String continent) {
        if (!controlledContinents.contains(continent)) {
            controlledContinents.add(continent);
        }
    }

    /**
     * get whether this player is still in the game
     *
     * @return true for valid and false for quit
     */
    public boolean getActiveStatus() {
        return activeStatus;
    }

    /**
     * set active status for the player
     *
     * @param activeStatus true for valid and false for quit
     */
    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
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
     * this method will be called only when player exchanging card for army
     *
     * @param newArmy army number to be added
     */
    public void addArmy(int newArmy) {
        this.armyNbr += newArmy;
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
     * @return the index of player
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * getter
     *
     * @return the army number of player
     */
    public int getArmyNbr() {
        return armyNbr;
    }

    /**
     * setter
     *
     * @param armyNbr setting the army number
     */
    private void setArmyNbr(int armyNbr) {
        this.armyNbr = armyNbr;
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
     * @param attackingCountry      attacking country
     * @param defendingCountry      defending country
     * @param attackArmyNbr         army number of attacking country
     * @param defendArmyNbr         army number of defending country
     * @param txa_attackInfoDisplay UI control for displaying information
     */
    public void attackCountry(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, TextArea txa_attackInfoDisplay) {
        oneAttackSimulate(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, txa_attackInfoDisplay);
    }

    /**
     * allout attack mode, which results in either attacker wins or defender wins.
     *
     * @param attackingCountry      attakcing country
     * @param defendingCountry      defending country
     * @param attackArmyNbr         army number of attacking country
     * @param defendArmyNbr         army number of defending country
     * @param txa_attackInfoDisplay UI control for display information
     */
    public void alloutModeAttack(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, TextArea txa_attackInfoDisplay) {
        alloutAttackSimulate(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, txa_attackInfoDisplay);
    }

    /**
     * Util method for recursive attacks
     *
     * @param attackingCountry      attacking country
     * @param defendingCountry      defending country
     * @param attackArmyNbr         army number of attacking country
     * @param defendArmyNbr         army number of defending country
     * @param txa_attackInfoDisplay UI control for displaying information
     */
    public void alloutAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr,
                                     TextArea txa_attackInfoDisplay) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attacker: [ ")
                .append(attackingCountry.getCountryName())
                .append(" ], Defender: [ ")
                .append(defendingCountry.getCountryName())
                .append(" ]\n\n");

        recursiveAttack(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, stringBuilder);

        if (AttackProcess.isCountryConquered(defendingCountry)) {
            int nondeployedAttackerArmyNbr = attackingCountry.getCountryArmyNumber() - 1;
            Player attacker = Main.playersList.get(attackingCountry.getCountryOwnerIndex());
            Player defender = Main.playersList.get(defendingCountry.getCountryOwnerIndex());

            int attackerIndex = attacker.getPlayerIndex();
            int defenderIndex = defender.getPlayerIndex();

            String continentName = defendingCountry.getContinentName();
            Continent curContinent = Main.worldContinentMap.get(continentName);

            AttackProcess.updateConqueredCountry(attackingCountry, defendingCountry, nondeployedAttackerArmyNbr, attacker, defender);
            AttackProcess.updateContinentAndWorldStatus(attacker, defender, curContinent);
        }
        txa_attackInfoDisplay.setText(stringBuilder.toString());
    }

    /**
     * this function defines that wheather the attacker or defender is available to attack
     * cheks if both have enough armies to attack.
     *
     * @param attackingCountry attacking country
     * @param defendingCountry defending country
     * @param attackArmyNbr    army number of attacking country
     * @param defendArmyNbr    army number of defending country
     * @param stringBuilder    String builder for storing attacking information
     */
    private void recursiveAttack(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, StringBuilder stringBuilder) {
        if (attackArmyNbr == 0 || defendArmyNbr == 0) {
            return;
        }
        int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
        int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

        getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, stringBuilder);

        attackArmyNbr = attackingCountry.getCountryArmyNumber() - 1;
        defendArmyNbr = defendingCountry.getCountryArmyNumber();
        stringBuilder.append("\n");
        recursiveAttack(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, stringBuilder);
    }

    /**
     * this functions calculates that how many armies defender or attacker have after the attack
     *
     * @param attackingCountry      attacking country
     * @param defendingCountry      defending country
     * @param attackArmyNbr         army number of attacking country
     * @param defendArmyNbr         army number of defending country
     * @param txa_attackInfoDisplay UI control for display information
     */
    public void oneAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr,
                                  TextArea txa_attackInfoDisplay) {
        int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
        int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attacker: [ ")
                .append(attackingCountry.getCountryName())
                .append(" ], Defender: [ ")
                .append(defendingCountry.getCountryName())
                .append(" ]\n\n");

        int attackerRemainArmyNbr = getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, stringBuilder);

        txa_attackInfoDisplay.setText(stringBuilder.toString());

        AttackProcess.attackResultProcess(attackingCountry, defendingCountry, attackerRemainArmyNbr);
    }

    public void executeAttack(){
        this.strategy.doAttack();
    }

    public void executeReinforcement(){
        this.strategy.doReinforcement();
    }

    public void executeReinforcement(Country country, int army){
        this.strategy.doReinforcement(country, army);
    }

    public void exeFortification(){
        this.strategy.doFortification();
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

    /**
     * set card observable changed
     */
    public void initObservableCard() {
        setChanged();
    }

    /**
     * public method for setting observable objects value.
     * Removing a set of cards from player
     * <p>
     * Do not require setChanged because it will not notify any observer
     * Observer will only update changes when rendering view
     *
     * @param cards iterable cards, e.g. ObservableList
     */
    public void removeObservableCards(Iterable<Card> cards) {
        for (Card card : cards) {
            this.cardsList.remove(card);
        }
//        setChanged();
    }

    /**
     * 1. update former owner's owned country name list and army nbr.
     * 2. delete former owner(observer) from country(observable).
     * 3. update new owner's owned country name list and army nbr.
     * 4. new owner get a card.
     * 5. add new owner(observer) to country(observable).
     * army nbr can also update later in ReinforceViewController
     * since it does not need display until reinforcement phase
     *
     * @param o   Country(Observable)
     * @param arg update message
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Country) {
            Player formerOwner = this;
            Player newOwner = Main.playersList.get(((Country) o).getCountryOwnerIndex());
            System.out.println("former: " + formerOwner.playerIndex);
            System.out.println("now: " + newOwner.playerIndex);
            System.out.printf("player %d obs awake\n", this.playerIndex);
            formerOwner.ownedCountryNameList.remove(((Country) o).getCountryName());
            formerOwner.updateArmyNbr();
            o.deleteObserver(this);
            newOwner.ownedCountryNameList.add(((Country) o).getCountryName());
            newOwner.updateArmyNbr();
            if (!newOwner.cardObtained) {
                newOwner.setObservableCard(Card.getCard(Card.class));
                newOwner.setCardPermission(true);
            }
            o.addObserver(newOwner);
            System.out.printf("Player observer update: %s\n", arg);
        }
    }
}




