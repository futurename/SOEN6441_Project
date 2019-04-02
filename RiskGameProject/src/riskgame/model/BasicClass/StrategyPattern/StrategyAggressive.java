package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Random;

public class StrategyAggressive implements Strategy {
    @Override
    public void doReinforcement(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
        aggressivelyExchangeCard(player);
        aggressivelyDeployArmy(player, worldHashMap);
        UtilMethods.endReinforcement(player, worldHashMap);
    }

    private void aggressivelyExchangeCard(Player player){
        CardExchangeViewObserver cardObserver = UtilMethods.initCardObserver(player);
        int curExchangeTime = cardObserver.getExchangeTime();
        ArrayList<Card> cards = cardObserver.getPlayerCards();
        int code = UtilMethods.availableCombo(cards);
        if (code != -2){
            UtilMethods.exchangeCard(player, code, curExchangeTime);
        }
        UtilMethods.deregisterCardObserver(player, cardObserver);
    }

    private void aggressivelyDeployArmy(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
        UtilMethods.getNewArmyPerRound(player);
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player, worldHashMap);
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
    public void doAttack(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
        aggressivelyAttack(player, worldHashMap);
        UtilMethods.endAttack(player, worldHashMap);
    }

    private void aggressivelyAttack(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
        ArrayList<Country> attackable = InfoRetriver.getAttackableCountry(player, worldHashMap);
        if (!attackable.isEmpty()) {
            //The list should contain a country base on aggressive rule
            for (Country attacker: attackable){
                ArrayList<Country> enemies = InfoRetriver.getAdjacentEnemy(player.getPlayerIndex(), attacker);
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
    public void doFortification(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
        aggressivelyFortify(worldHashMap);
        UtilMethods.endFortification(player, worldHashMap);
    }

    private void aggressivelyFortify(LinkedHashMap<String, GraphNode> worldHashMap) {
        //do nothing
    }

    @Override
    public String toString(){
        return "Aggressive";
    }
}
