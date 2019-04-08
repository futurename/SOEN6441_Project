package riskgame.model.BasicClass;


import javafx.scene.paint.Color;
import riskgame.Main;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.StrategyPattern.StrategyHuman;
import riskgame.model.Utils.AttackProcess;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private final String playerName;
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
    private LinkedHashMap<String, GraphNode> worldMapInstance;
    private LinkedHashMap<String, Continent> continentMapInstance;
    private boolean isFinalWinner;

    /**
     * class constructor, player takes human strategy by default
     *
     * @param playerIndex index of current player
     */
    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.playerName = "Player_" + playerIndex;
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
        this.worldMapInstance = Main.graphSingleton;
        this.continentMapInstance = Main.worldContinentMap;
        this.isFinalWinner = false;

        System.out.println("\nPlayer constructor, player name: " + playerName + "\n\n");

    }

    public Player(int playerIndex, Strategy type, LinkedHashMap<String, GraphNode> worldMapInstance,
                  LinkedHashMap<String, Continent> continentMapInstance) {
        this.playerIndex = playerIndex;
        this.playerName = type.toString();
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
        this.worldMapInstance = worldMapInstance;
        this.continentMapInstance = continentMapInstance;

        System.out.println("\nPlayer constructor, player name: " + playerName + "\n\n");
    }

    public Player(int playerIndex, Strategy type, int armyNbr, ArrayList<Card> card, ArrayList<String> countryNameList,
                  Color color, int continentBonus, boolean status, LinkedHashMap<String, GraphNode> worldMapInstance,
                  LinkedHashMap<String, Continent> continentMapInstance) {
        this.playerIndex = playerIndex;
        this.playerName = type.toString();
        this.armyNbr = armyNbr;
        this.cardsList = card;
        this.ownedCountryNameList = countryNameList;
        this.playerColor = color;
        this.continentBonus = continentBonus;
        this.activeStatus = status;
        this.cardObtained = false;
        this.undeployedArmy = 0;
        this.strategy = type;
        this.worldMapInstance = worldMapInstance;
        this.continentMapInstance = continentMapInstance;

        System.out.println("\nPlayer constructor, player name: " + playerName + "\n\n");
    }

    /**
     * this functions calculates the result for every single attack and returns the attacker's army number
     *
     * @param attackingCountry  attacking country
     * @param defendingCountry  defending country
     * @param attackableArmyNbr army number of attacking country
     * @param defendableArmyNbr army number of defending country
     * @param stringBuilder     stringBuilder for storing combat information
     * @return remaining army number of the attacker
     */
    public static int getOneAttackResult(Country attackingCountry, Country defendingCountry, int attackableArmyNbr, int defendableArmyNbr,
                                         StringBuilder stringBuilder) {
        stringBuilder.append("Attacker: [ ")
                .append(attackingCountry.getCountryName())
                .append(" ], Defender: [ ")
                .append(defendingCountry.getCountryName())
                .append(" ]\n\n");

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

                System.out.println("Round: " + i + ", >>>" + attackingCountry.getOwner() + "(" + attackingCountry.getCountryArmyNumber()
                        + ") WIN : " + defendingCountry.getOwner() + "(" + defendingCountry.getCountryArmyNumber() + ")\n");

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

                System.out.println("Round: " + i + ", >>>Defender:" + defendingCountry.getOwner() + "(" + defendingCountry.getCountryArmyNumber()
                        + ") win: " + attackingCountry.getOwner() + "(" + attackingCountry + ")\n");

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

    public LinkedHashMap<String, Continent> getContinentMapInstance() {
        return continentMapInstance;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public LinkedHashMap<String, GraphNode> getWorldMapInstance() {
        return worldMapInstance;
    }

    public void addUndeployedArmy(int undeployedArmy) {
        this.undeployedArmy += undeployedArmy;
    }

    public int getUndeployedArmy() {
        return undeployedArmy;
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
            Country country = worldMapInstance
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
     * get controlledContinents
     *
     * @return
     */
    public ArrayList<String> getControlledContinents() {
        return controlledContinents;
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


    public String getPlayerName() {
        return playerName;
    }

    /**
     * get cardObtained
     *
     * @return
     */
    public boolean getCardObtained() {
        return cardObtained;
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

    public boolean isFinalWinner() {
        return isFinalWinner;
    }

    public void setFinalWinner(boolean finalWinner) {
        isFinalWinner = finalWinner;
    }

    /**
     * Util method for recursive attacks
     * allout attack mode, which results in either attacker wins or defender wins.
     *
     * @param attackingCountry attacking country
     * @param defendingCountry defending country
     * @param attackArmyNbr    army number of attacking country
     * @param defendArmyNbr    army number of defending country
     * @return battle info
     */
    public String alloutAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr,
                                       int defendArmyNbr, boolean UIOption) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attacker: [ ")
                .append(attackingCountry.getCountryName())
                .append(" ], Defender: [ ")
                .append(defendingCountry.getCountryName())
                .append(" ]\n\n");

        recursiveAttack(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, stringBuilder);

        if (AttackProcess.isCountryConquered(defendingCountry)) {
            int nondeployedAttackerArmyNbr = attackingCountry.getCountryArmyNumber() - 1;

            String continentName = defendingCountry.getContinentName();
            Continent curContinent = attackingCountry.getOwner().getContinentMapInstance().get(continentName);

            AttackProcess.updateConqueredCountry(attackingCountry, defendingCountry, nondeployedAttackerArmyNbr, UIOption);
            AttackProcess.updateContinentAndWorldStatus(attackingCountry.getOwner(), defendingCountry.getOwner(), curContinent, UIOption);
        }
        return stringBuilder.toString();
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
        while (attackArmyNbr != 0 && defendArmyNbr != 0) {
            int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
            int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

            getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, stringBuilder);

            attackArmyNbr = attackingCountry.getCountryArmyNumber() - 1;
            defendArmyNbr = defendingCountry.getCountryArmyNumber();

            if (!attackingCountry.getOwner().getStrategy().toString().equals("Human") && !defendingCountry.getOwner().getStrategy().toString().equals("Human")) {
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append("\n");
            }
        }

        /*if (attackArmyNbr == 0 || defendArmyNbr == 0) {
            return;
        }
        int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
        int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

        getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, stringBuilder);

        attackArmyNbr = attackingCountry.getCountryArmyNumber() - 1;
        defendArmyNbr = defendingCountry.getCountryArmyNumber();
        stringBuilder.append("\n");
        recursiveAttack(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, stringBuilder);*/
    }

    /**
     * this functions calculates that how many armies defender or attacker have after the attack
     *
     * @param attackingCountry attacking country
     * @param defendingCountry defending country
     * @param attackArmyNbr    army number of attacking country
     * @param defendArmyNbr    army number of defending country
     * @return battle info
     */
    public StringBuilder oneAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr) {
        int avaliableForAttackNbr = attackArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackArmyNbr;
        int avaliableForDefendNbr = defendArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defendArmyNbr;

        StringBuilder battleReport = new StringBuilder();

        int attackerRemainArmyNbr = getOneAttackResult(attackingCountry, defendingCountry, avaliableForAttackNbr, avaliableForDefendNbr, battleReport);

        AttackProcess.attackResultProcess(attackingCountry, defendingCountry, attackerRemainArmyNbr);

        return battleReport;
    }

    public void executeAttack() {
        this.strategy.doAttack(this);
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
     * @param attackingCountry attacking country
     * @param defendingCountry defending country
     * @param attackArmyNbr    army number of attacking country
     * @param defendArmyNbr    army number of defending country
     * @return battle info
     */
    public String executeAttack(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, boolean isAllout) {
        return this.strategy.doAttack(this, attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, isAllout);
    }

    public void executeReinforcement(PhaseViewObservable observable) {
        this.strategy.doReinforcement(this, observable);
    }

    /**
     * Human method.
     * Three steps:
     * 1. Add army to country.
     * 2. Add army to player.
     * 3. Subtract undeployed army from player.
     *
     * @param country deploying country
     * @param army    army count
     */
    public void executeReinforcement(Country country, int army) {
        this.strategy.doReinforcement(this, country, army);
    }

    public void executeFortification() {
        this.strategy.doFortification(this);
    }

    public void executeFortification(Country from, Country to, int army) {
        this.strategy.doFortification(from, to, army);
    }

    /**
     * public method for setting observable objects value.
     * Adding a new card to player
     *
     * @param newCard card
     */
    private void setObservableCard(Card newCard) {
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
            Player newOwner = ((Country) o).getOwner();
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
        }
    }

    @Override
    public String toString() {
        return this.getPlayerName();
    }
}




