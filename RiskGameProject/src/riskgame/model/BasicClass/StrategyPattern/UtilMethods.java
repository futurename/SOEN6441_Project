package riskgame.model.BasicClass.StrategyPattern;

import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;

import static riskgame.Main.*;
import static riskgame.Main.totalNumOfPlayers;
import static riskgame.controllers.StartViewController.firstRoundCounter;

public class UtilMethods {

    /**
     * Making exchange in "code" way.
     * Then notify the observable to update exchange time.
     * @param player player
     * @param code combo type
     */
    public static void exchangeCard(Player player, int code) {
        if (code == -1){
            ArrayList<Card> removes = new ArrayList<>();
            removes.add(Card.ARTILLERY);
            removes.add(Card.CAVALRY);
            removes.add(Card.INFANTRY);
            player.removeObservableCards(removes);
            phaseViewObservable.addOneExchangeTime();
            phaseViewObservable.notifyObservers("Add Exchange Time");
        }else if(code == Card.INFANTRY.ordinal()){
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3,Card.INFANTRY)));
            phaseViewObservable.addOneExchangeTime();
            phaseViewObservable.notifyObservers("Add Exchange Time");
        }else if (code == Card.CAVALRY.ordinal()){
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3,Card.CAVALRY)));
            phaseViewObservable.addOneExchangeTime();
            phaseViewObservable.notifyObservers("Add Exchange Time");
        }else if (code == Card.ARTILLERY.ordinal()){
            player.removeObservableCards(new ArrayList<>(Collections.nCopies(3,Card.ARTILLERY)));
            phaseViewObservable.addOneExchangeTime();
            phaseViewObservable.notifyObservers("Add Exchange Time");
        }
    }

    /**
     * Automatically looking for available combo to exchange
     * @param cards cards list with size that > 2 (do not required)
     * @return an integer that stands for a possible combo
     */
    public static int availableCombo(ArrayList<Card> cards){
        int cavalry = 0;
        int infantry = 0;
        int artillery = 0;
        for (Card card: cards){
            switch (card){
                case CAVALRY:
                    cavalry += 1;
                    break;
                case INFANTRY:
                    infantry += 1;
                    break;
                case ARTILLERY:
                    artillery += 1;
            }
        }
        if (cavalry >= 3){
            return Card.CAVALRY.ordinal();
        }else if (infantry >= 3){
            return Card.INFANTRY.ordinal();
        }else if (artillery >= 3){
            return Card.ARTILLERY.ordinal();
        }else if (cavalry>0 && infantry>0 && artillery>0){
            return -1;
        }else return -2;
    }

    public static void endReinforcement(Player player){
        if (StartViewController.reinforceInitCounter > 1) {
            notifyReinforcementEnd(false, player);
            StartViewController.reinforceInitCounter--;
        } else {
            notifyReinforcementEnd(true, player);
        }
    }

    /**
     * Notify all phase view observer that game stage changed.
     * Changes can be next player's reinforcement or going to attack phase.
     *
     * @param isAttackPhase true for going to attack phase otherwise, next player's turn
     */
    private static void notifyReinforcementEnd(boolean isAttackPhase, Player player){
        if (!isAttackPhase) {
            int nextPlayerIndex = (player.getPlayerIndex() + 1) % totalNumOfPlayers;
            phaseViewObservable.setAllParam("Reinforcement Phase", nextPlayerIndex, "Reinforcement Action");
            phaseViewObservable.notifyObservers("continue reinforce");
        } else {
            phaseViewObservable.setAllParam("Attack Phase", curRoundPlayerIndex, "Attack Action");
            phaseViewObservable.notifyObservers("From ReinforceView to AttackView");
        }
    }

    public static void endFortification(Player player){
        if (firstRoundCounter > 0) {
            firstRoundCounter--;
            if (firstRoundCounter == 0) {
                curRoundPlayerIndex = -1;
            }
            int nextPlayerIndex = (player.getPlayerIndex() + 1) % totalNumOfPlayers;
            notifyFortificationEnd("Attack Phase", nextPlayerIndex, "Attack Action");

        } else {
            curRoundPlayerIndex = InfoRetriver.getNextActivePlayer(player.getPlayerIndex());
            notifyFortificationEnd("Reinforce Phase", curRoundPlayerIndex, "Reinforcement Action");
        }
    }

    /**
     * Call phase view observable notify its observers.
     *
     * @param phase           phase name string
     * @param nextPlayerIndex next valid player index
     * @param actionType      action string
     */
    private static void notifyFortificationEnd(String phase, int nextPlayerIndex, String actionType) {
        phaseViewObservable.setAllParam(phase, nextPlayerIndex, actionType);
        phaseViewObservable.notifyObservers("from fortification view");
        System.out.printf("A player finished fortification, player %s's turn\n", nextPlayerIndex);
    }
}
