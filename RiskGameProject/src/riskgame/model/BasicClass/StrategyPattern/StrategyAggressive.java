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

    public void aggressivelyExchangeCard(Player player, PhaseViewObservable observable){
        CardExchangeViewObserver cardObserver = UtilMethods.initCardObserver(player, observable);
        int curExchangeTime = cardObserver.getExchangeTime();
        ArrayList<Card> cards = cardObserver.getPlayerCards();
        int code = UtilMethods.availableCombo(cards);
        if (code != -2){
            UtilMethods.exchangeCard(player, code, curExchangeTime);
        }
        UtilMethods.deregisterCardObserver(player, observable, cardObserver);
    }

    public void aggressivelyDeployArmy(Player player) {
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
     * @param from owned countries
     * @return selected one
     */
    public Country aggressivelyPickCountryFrom(ArrayList<Country> from){
        int max = 0;
        ArrayList<Country> evenCountries = new ArrayList<>();
        for (Country country: from){
            if (country.getCountryArmyNumber() > max){
                evenCountries.clear();
                evenCountries.add(country);
            }else if (country.getCountryArmyNumber() == max){
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
        //Remember to turn on card getting permission starting attack phase. otherwise the player will no getting card
        player.setCardPermission(true);
        //attackable: army>1 & has enemy neighbors
        ArrayList<Country> attackable = InfoRetriver.getAttackableCountry(player);
        if (!attackable.isEmpty()) {
            //The list should contain a country base on aggressive rule
            for (Country attacker: attackable){
                ArrayList<Country> enemies = InfoRetriver.getAdjacentEnemy(player, attacker);
                Collections.shuffle(enemies);
                //keep attacking util all armies are fucked up
                for (Country enemy: enemies){
                    //cannot attack any more
                    if (attacker.getCountryArmyNumber() < 2){
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
    }

    @Override
    public void doFortification(Player player) {
        System.out.printf("Player %s %s strategy. alive: %s, has army: %s in fortification.\n", player.getPlayerIndex(), player, player.getActiveStatus(), player.getArmyNbr());
        aggressivelyFortify();
        UtilMethods.endFortification(player);
    }

    private void aggressivelyFortify() {
        //do nothing
    }

    @Override
    public String toString(){
        return "Aggressive";
    }
}
