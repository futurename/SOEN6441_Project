package riskgame.model.BasicClass;


import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import riskgame.Main;
import riskgame.model.BasicClass.ObserverPattern.CountryObservable;
import riskgame.model.BasicClass.ObserverPattern.CountryObserver;
import riskgame.model.Utils.AttackProcess;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class includes attributes a player need and required methods
 * Observer property of this class is updating changes triggered by countries
 * As a observable object, player is observed by CardExchangeViewObserver for cards' changes
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

    /**
     * class constructor
     *
     * @param playerIndex index of current player
     */
    public Player(int playerIndex) {
        this.playerIndex = playerIndex;
        this.armyNbr = 0;
        this.cardsList = new ArrayList<>();
        this.ownedCountryNameList = new ArrayList<>();
        this.playerColor = PlayerColor.values()[playerIndex].colorValue;
        //this.ownedCountryNbr = 0;
        this.continentBonus = 0;
        this.controlledContinents = new ArrayList<>();
        this.activeStatus = true;

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

    public void addControlledContinent(String continent){
        if (!controlledContinents.contains(continent)){
            controlledContinents.add(continent);
        }
    }

    public int getControlledContinentCount(){
        return controlledContinents.size();
    }


    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }



    public boolean getActiveStatus() {
        return activeStatus;
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
     */
    public void addArmy(int newArmy){
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
    private void setArmyNbr(int armyNbr) {
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
     *  @param attackingCountry
     * @param defendingCountry the defending country ojbect
     * @param txa_attackInfoDisplay
     */

    public void attackCountry(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, TextArea txa_attackInfoDisplay) {

        AttackProcess.oneAttackSimulate(attackingCountry, defendingCountry, attackArmyNbr,defendArmyNbr, txa_attackInfoDisplay);
    }

    public void alloutModeAttack(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, TextArea txa_attackInfoDisplay){
        AttackProcess.alloutAttackSimulate(attackingCountry,defendingCountry,attackArmyNbr,defendArmyNbr,txa_attackInfoDisplay);
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
     * Do not require setChanged because it will not notify any observer
     * Observer will only update changes when rendering view
     * @param cards iterable cards, e.g. ObservableList
     */
    public void removeObservableCards(Iterable<Card> cards) {
        for (Card card : cards) {
            this.cardsList.remove(card);
        }
//        setChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Doing update?");
        if (o instanceof Country){
            Player formerOwner = this;
            Player newOwner = Main.playersList.get(((Country)o).getCountryOwnerIndex());
            System.out.println("former: "+formerOwner.playerIndex);
            System.out.printf("player %d obs awake\n", this.playerIndex);
            formerOwner.ownedCountryNameList.remove(((Country)o).getCountryName());
            o.deleteObserver(this);
            newOwner.ownedCountryNameList.add(((Country)o).getCountryName());
            o.addObserver(newOwner);
            System.out.printf("Player observer update: %s\n", arg);
        }
    }
}




