package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StrategyAggressive implements Strategy {
    @Override
    public void doReinforcement(Player player, PhaseViewObservable observable) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in reinforcement.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        aggressivelyExchangeCard(player, observable);
        aggressivelyDeployArmy(player);
        UtilMethods.endReinforcement(player);
    }

    private void aggressivelyExchangeCard(Player player, PhaseViewObservable observable) {
        CardExchangeViewObserver cardObserver = UtilMethods.initCardObserver(player, observable);
        int curExchangeTime = cardObserver.getExchangeTime();
        ArrayList<Card> cards = cardObserver.getPlayerCards();

        System.out.println("\n\n\n\nAggressive player cards: " + cards + "\n\n\n\n");

        int code = UtilMethods.availableCombo(cards);
        if (code != -2) {
            UtilMethods.exchangeCard(player, code, curExchangeTime);
        }
        UtilMethods.deregisterCardObserver(player, observable, cardObserver);
    }

    private void aggressivelyDeployArmy(Player player) {
        UtilMethods.getNewArmyPerRound(player);
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        //Adding army to the selected; adding army to player; subtracting army from undeployed army.
        aggressivelyPickCountryFrom(countries).addToCountryArmyNumber(availableArmy);
        player.addArmy(availableArmy);
        player.addUndeployedArmy(-player.getUndeployedArmy());
    }
    /**
     * Pick the strongest country among countries, if even powerful countries exist, randomly pick one
     *
     * @param from owned countries
     * @return selected one
     */
    private Country aggressivelyPickCountryFrom(ArrayList<Country> from) {
        int max = 0;
        ArrayList<Country> evenCountries = new ArrayList<>();
        for (Country country : from) {
            if (country.getCountryArmyNumber() > max) {
                evenCountries.clear();
                evenCountries.add(country);
            } else if (country.getCountryArmyNumber() == max) {
                evenCountries.add(country);
            }
        }
        return evenCountries.get(new Random().nextInt(evenCountries.size()));
    }

    @Override
    public void doAttack(Player player) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in attack.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        aggressivelyAttack(player);
        UtilMethods.endAttack(player);
    }

    public void aggressivelyAttack(Player player) {
        player.setCardPermission(false);
        Country ownedStrongestCountry = InfoRetriver.getOwnedStrongestCountry(player);
        while (ownedStrongestCountry != null) {
            ArrayList<Country> enemyCountryList = InfoRetriver.getAdjacentEnemy(player, ownedStrongestCountry);
            Collections.shuffle(enemyCountryList);
            if (!enemyCountryList.isEmpty()) {
                Collections.shuffle(enemyCountryList);
                Country enemyCountry = enemyCountryList.get(0);
                int attackArmy = ownedStrongestCountry.getCountryArmyNumber() - 1;
                int defenceArmy = enemyCountry.getCountryArmyNumber();
                //all-out mode attack
                player.alloutAttackSimulate(ownedStrongestCountry, enemyCountry, attackArmy, defenceArmy, false);
                //only when the for loop for attackable reaches the last one. player can be the winner.

                if (player.isFinalWinner()) {
                    break;
                }
                ownedStrongestCountry = InfoRetriver.getOwnedStrongestCountry(player);
            } else {
                break;
            }
        }
    }


    @Override
    public void doFortification(Player player) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in fortification.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        aggressivelyFortify(player);
        UtilMethods.endFortification(player);
    }

    private void aggressivelyFortify(Player player) {
        ArrayList<Country> sortedOwnedCountryList = InfoRetriver.getSortedCountryListByArmyNbr(player);
        for (int i = 0; i < sortedOwnedCountryList.size() - 1; i++) {
            Country strongestCountry = sortedOwnedCountryList.get(i);
            Country subStrongestCountry = sortedOwnedCountryList.get(i + 1);
            if (InfoRetriver.getAdjacentEnemy(player, strongestCountry).size() != 0) {
                int transferArmyNbr = subStrongestCountry.getCountryArmyNumber() - 1;
                strongestCountry.addToCountryArmyNumber(transferArmyNbr);
                subStrongestCountry.reduceFromCountryArmyNumber(transferArmyNbr);
            }
        }
    }

    @Override
    public String toString() {
        return "Aggressive";
    }
}
