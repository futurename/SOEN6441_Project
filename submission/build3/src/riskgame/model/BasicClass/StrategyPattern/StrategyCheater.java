package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;

public class StrategyCheater implements Strategy {
    @Override
    public void doReinforcement(Player player, PhaseViewObservable observable) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in reinforcement.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        evillyExchangeCard(player, observable);
        evillyDeployArmy(player);
        UtilMethods.endReinforcement(player);
    }

    private void evillyExchangeCard(Player player, PhaseViewObservable observable) {
        CardExchangeViewObserver cardObserver = UtilMethods.initCardObserver(player, observable);
        int curExchangeTime = cardObserver.getExchangeTime();
        ArrayList<Card> cards = cardObserver.getPlayerCards();

        System.out.println("\n\n\n\nCheater player cards: " + cards + "\n\n\n\n");

        int code = UtilMethods.availableCombo(cards);
        if (code != -2) {
            UtilMethods.exchangeCard(player, code, curExchangeTime);
        }
        UtilMethods.deregisterCardObserver(player, observable, cardObserver);
    }

    private void evillyDeployArmy(Player player) {
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        for (Country country: countries){
            int baseArmy = country.getCountryArmyNumber();
            country.addToCountryArmyNumber(baseArmy);
            player.addArmy(baseArmy);
        }
    }

    @Override
    public void doAttack(Player player) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in attack.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        evillyConquer(player);
        UtilMethods.endAttack(player);
    }

    private void evillyConquer(Player player){
        player.setCardPermission(true);
        ArrayList<Country> ownedAttackerList = InfoRetriver.getOwnedAttackerList(player);
        while (!ownedAttackerList.isEmpty()) {
            for (Country attackerCountry : ownedAttackerList) {
                ArrayList<Country> adjacentEnemyList = InfoRetriver.getAdjacentEnemy(player, attackerCountry);
                for (Country enemyCountry : adjacentEnemyList) {
                    if (attackerCountry.getCountryArmyNumber() < 2) {
                        break;
                    }
                    int attackArmy = attackerCountry.getCountryArmyNumber() - 1;
                    int defenceArmy = enemyCountry.getCountryArmyNumber();
                    //all-out mode attack
                    player.alloutAttackSimulate(attackerCountry, enemyCountry, attackArmy, defenceArmy, false);
                    //only when the for loop for attackable reaches the last one. player can be the winner.
                    if (player.isFinalWinner()) {
                        break;
                    }
                }

                if (player.isFinalWinner()) {
                    break;
                }
            }
            ownedAttackerList = InfoRetriver.getOwnedAttackerList(player);
        }

    }

    @Override
    public void doFortification(Player player) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in fortification.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        evillyFortify(player);
        UtilMethods.endFortification(player);
    }

    private void evillyFortify(Player player){
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        for (Country country: countries){
            ArrayList<Country> enemyCountries = InfoRetriver.getAdjacentEnemy(player, country);
            if (!enemyCountries.isEmpty()){
                int baseArmy = country.getCountryArmyNumber();
                country.addToCountryArmyNumber(baseArmy);
            }
        }
    }

    @Override
    public String toString(){
        return "Cheater";
    }
}
