package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;
import riskgame.model.Utils.InfoRetriver;

import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class StrategyCheater implements Strategy {
    @Override
    public void doReinforcement(Player player, PhaseViewObservable observable) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in reinforcement.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        evillyExchangeCard();
        evillyDeployArmy(player);
        UtilMethods.endReinforcement(player);
    }

    private void evillyExchangeCard() {
        //Never exchange card.
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
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        for (Country country: countries){
            ArrayList<Country> enemyCountries = InfoRetriver.getAdjacentEnemy(player, country);
            for (Country enemy: enemyCountries){
                Player formerOwner = enemy.getOwner();
                System.out.printf("%s(using %s) conquered %s(%s).\n", player, country.getCountryName(), formerOwner, enemy.getCountryName());
                enemy.setObservableArmyWhenOwnerChanged(player, enemy.getCountryArmyNumber());
                enemy.notifyObservers("Conquered a country");
                UtilMethods.checkDefenderAlive(formerOwner);
                System.out.printf("Defender owned country %s?: %s", enemy.getCountryName(), formerOwner.getOwnedCountryNameList().contains(enemy.getCountryName()));
            }
        }
        if (AttackProcess.isWorldConquered(player)){
            player.setFinalWinner(true);
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
