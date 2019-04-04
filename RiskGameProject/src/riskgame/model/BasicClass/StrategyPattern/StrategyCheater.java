package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;

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
