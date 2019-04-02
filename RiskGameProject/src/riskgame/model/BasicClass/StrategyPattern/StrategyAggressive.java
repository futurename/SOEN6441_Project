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
        aggressivelyExchangeCard(player, observable);
        aggressivelyDeployArmy(player);
        UtilMethods.endReinforcement(player);
    }

    private void aggressivelyExchangeCard(Player player, PhaseViewObservable observable){
        CardExchangeViewObserver cardObserver = UtilMethods.initCardObserver(player, observable);
        int curExchangeTime = cardObserver.getExchangeTime();
        ArrayList<Card> cards = cardObserver.getPlayerCards();
        int code = UtilMethods.availableCombo(cards);
        if (code != -2){
            UtilMethods.exchangeCard(player, code, curExchangeTime);
        }
        UtilMethods.deregisterCardObserver(player, observable, cardObserver);
    }

    private void aggressivelyDeployArmy(Player player) {
        UtilMethods.getNewArmyPerRound(player);
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        aggressivelyPickCountryFrom(countries).addToCountryArmyNumber(availableArmy);
        player.addArmy(availableArmy);
        player.addUndeployedArmy(-player.getUndeployedArmy());
    }

    private Country aggressivelyPickCountryFrom(ArrayList<Country> from){
        int max = 0;
        ArrayList<Country> evenCountries = new ArrayList<>();
        for (Country country: from){
            if (country.getCountryArmyNumber()>max){
                evenCountries.clear();
                evenCountries.add(country);
            }else if (country.getCountryArmyNumber()==max){
                evenCountries.add(country);
            }
        }
        return evenCountries.get(new Random().nextInt(evenCountries.size()));
    }

    @Override
    public void doAttack(Player player) {
        aggressivelyAttack(player);
        UtilMethods.endAttack(player);
    }

    private void aggressivelyAttack(Player player) {
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
                    player.alloutAttackSimulate(attacker, enemy, attackArmy, defenceArmy, false);
                }
            }
        }
    }

    @Override
    public void doFortification(Player player) {
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
