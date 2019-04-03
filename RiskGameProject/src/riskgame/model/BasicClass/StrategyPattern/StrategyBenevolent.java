package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Random;

public class StrategyBenevolent implements Strategy {
    @Override
    public void doReinforcement(Player player, PhaseViewObservable observable) {
        neverExchangeCard();
        benevolentlyDeployArmy(player);
        UtilMethods.endReinforcement(player);
    }

    private void neverExchangeCard() {
        //Since it never attack as a result it will not get any card
    }

    private void benevolentlyDeployArmy(Player player) {
        UtilMethods.getNewArmyPerRound(player);
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        pickWeakestCountry(countries).addToCountryArmyNumber(availableArmy);
        player.addArmy(availableArmy);
        player.addUndeployedArmy(-player.getUndeployedArmy());
    }

    private Country pickWeakestCountry(ArrayList<Country> countries) {
        int min = 998;
        ArrayList<Country> evenCountries = new ArrayList<>();
        for (Country country: countries){
            if (country.getCountryArmyNumber() < min){
                evenCountries.clear();
                evenCountries.add(country);
            }else if (country.getCountryArmyNumber() == min){
                evenCountries.add(country);
            }
        }
        return evenCountries.get(new Random().nextInt(evenCountries.size()));
    }

    @Override
    public void doAttack(Player player) {
        neverAttack();
        UtilMethods.endAttack(player);
    }

    private void neverAttack() {
        //Never attack
    }

    @Override
    public void doFortification(Player player) {
        benevolentlyFortify(player);
        UtilMethods.endFortification(player);
    }

    private void benevolentlyFortify(Player player) {
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        //TODO then fortifies in order to move armies to weaker countries????
    }

    @Override
    public String toString(){
        return "Benevolent";
    }
}
