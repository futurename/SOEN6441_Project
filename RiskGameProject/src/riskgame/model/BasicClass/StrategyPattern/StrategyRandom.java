package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import static riskgame.controllers.AttackViewController.MAX_ATTACKING_ARMY_NUMBER;
import static riskgame.controllers.AttackViewController.MAX_DEFENDING_ARMY_NUMBER;
import static riskgame.model.BasicClass.Player.getOneAttackResult;

public class StrategyRandom implements Strategy {
    private Random r = new Random();

    @Override
    public void doReinforcement(Player player, PhaseViewObservable observable) {
        randomlyExchangeCard(player, observable);
        randomlyDeployArmy(player);
        UtilMethods.endReinforcement(player);
    }

    private void randomlyDeployArmy(Player player) {
        UtilMethods.getNewArmyPerRound(player);
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        while (availableArmy > 0) {
            int randomArmy = r.nextInt(availableArmy) + 1;
            randomlyPickCountryFrom(countries).addToCountryArmyNumber(randomArmy);
            player.addArmy(randomArmy);
            availableArmy -= randomArmy;
        }
        player.addUndeployedArmy(player.getUndeployedArmy());

        System.out.println("Random robot randomly deploy army\n");
    }

    private Country randomlyPickCountryFrom(ArrayList<Country> from){
        int randomIndex = r.nextInt(from.size());
        return from.get(randomIndex);
    }

    private void randomlyExchangeCard(Player player, PhaseViewObservable observable) {
        CardExchangeViewObserver cardObserver = UtilMethods.initCardObserver(player, observable);
        int curExchangeTime = cardObserver.getExchangeTime();
        ArrayList<Card> cards = cardObserver.getPlayerCards();
        if (cards.size() >= 5) {
            int code = UtilMethods.availableCombo(cards);
            UtilMethods.exchangeCard(player, code, curExchangeTime);
        } else if (cards.size() >= 3) {
            int code = UtilMethods.availableCombo(cards);
            if (code != -2) {
                //50% probability
                if (r.nextInt(2) == 0) {
                    UtilMethods.exchangeCard(player, code, curExchangeTime);
                }
            }
        }
        UtilMethods.deregisterCardObserver(player, observable, cardObserver);

        System.out.println("Random robot randomly exchange cards!\n");
    }

    @Override
    public void doAttack(Player player) {
        randomlyAttack(player);
        UtilMethods.endAttack(player);
    }

    private void randomlyAttack(Player player) {
        //pick a country that can attack
        ArrayList<Country> attackable = InfoRetriver.getAttackableCountry(player);
        if (!attackable.isEmpty()) {
            Country attacker = randomlyPickCountryFrom(attackable);
            //pick an enemy
            ArrayList<Country> enemies = InfoRetriver.getAdjacentEnemy(player, attacker);
            Country enemy = randomlyPickCountryFrom(enemies);
            int defenceArmy = enemy.getCountryArmyNumber() > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : enemy.getCountryArmyNumber();
            //attack a country a number of times as grading sheet
            int randomAttackTime = r.nextInt(attacker.getCountryArmyNumber() - 1) + 1;
            for (int time = 0; time < randomAttackTime; time++) {
                //randomly send army
                int randomArmy = r.nextInt(attacker.getCountryArmyNumber() - 1) + 1;
                int actualArmy = randomArmy > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : randomArmy;
                //Attack
                int armyLeft = getOneAttackResult(attacker, enemy, actualArmy, defenceArmy, new StringBuilder());
                AttackProcess.autoResultProcess(attacker, enemy, armyLeft);
                randomAttackTime = attacker.getCountryArmyNumber() - 1;
            }
        }
    }

    @Override
    public void doFortification(Player player) {
        randomlyFortify(player);
        UtilMethods.endFortification(player);
    }

    private void randomlyFortify(Player player) {
        Country from = randomlyPickCountryFrom(InfoRetriver.getCountryList(player));
        ArrayList<Country> reachableCountries = InfoRetriver.getReachableCountry(player, from.getCountryName());
        if (!reachableCountries.isEmpty()) {
            Country target = randomlyPickCountryFrom(reachableCountries);
            int army = from.getCountryArmyNumber();
            from.reduceFromCountryArmyNumber(army);
            target.addToCountryArmyNumber(army);
        }
    }

    @Override
    public String toString(){
        return "Random";
    }
}
