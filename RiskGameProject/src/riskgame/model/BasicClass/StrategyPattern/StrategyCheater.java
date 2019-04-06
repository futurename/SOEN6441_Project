package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;

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
        player.setCardPermission(false);
        ArrayList<Country> attackableCountryList = InfoRetriver.getOwnedAttackerList(player);


        if (!attackableCountryList.isEmpty()) {
            //The list should contain a country base on aggressive rule
            for (Country attacker : attackable) {
                ArrayList<Country> enemies = InfoRetriver.getAdjacentEnemy(player, attacker);
                Collections.shuffle(enemies);
                //keep attacking util all armies are fucked up
                for (Country enemy : enemies) {
                    //cannot attack any more
                    if (attacker.getCountryArmyNumber() < 2) {
                        break;
                    }
                    int attackArmy = attacker.getCountryArmyNumber() - 1;
                    int defenceArmy = enemy.getCountryArmyNumber();
                    //all-out mode attack
                    player.alloutAttackSimulate(attacker, enemy, attackArmy, defenceArmy, false);
                    //only when the for loop for attackable reaches the last one. player can be the winner.
                    if (player.isFinalWinner()) {
                        break;
                    }
                }

                if (player.isFinalWinner()) {
                    break;
                }
            }
        }


        for (Country country: countries){
            ArrayList<Country> enemyCountries = InfoRetriver.getAdjacentEnemy(player, country);
            //For every enemies:
            for (Country enemy: enemyCountries){
                Player formerOwner = enemy.getOwner();
                System.out.printf("%s(using %s) conquered %s(%s).\n", player, country.getCountryName(), formerOwner, enemy.getCountryName());
                //Conquer it anyway
                enemy.setObservableArmyWhenOwnerChanged(player, enemy.getCountryArmyNumber());
                enemy.notifyObservers("Conquered a country");
                //if player eliminated?
                UtilMethods.checkDefenderAlive(formerOwner);
                System.out.printf("Defender owned country %s?: %s\n", enemy.getCountryName(), formerOwner.getOwnedCountryNameList().contains(enemy.getCountryName()));
                //Take over continent? or world?
                String continentName = enemy.getContinentName();
                Continent curContinent = country.getOwner().getContinentMapInstance().get(continentName);
                AttackProcess.updateContinentAndWorldStatus(player, formerOwner, curContinent, false);
                System.out.println("CHEATER A WINNER??: "+ player.isFinalWinner());
                if (player.isFinalWinner()) {
                    break;
                }
            }

            if (player.isFinalWinner()) {
                break;
            }
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
