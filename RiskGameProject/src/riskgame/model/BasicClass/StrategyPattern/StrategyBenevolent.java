package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Zhanfan
 */
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
        countries.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getCountryArmyNumber() - o2.getCountryArmyNumber();
            }
        });

        return countries.get(0);

     /*   int selection = 0;
        int min = 998;
        ArrayList<Country> evenCountries = new ArrayList<>();
        for (Country country: countries){
            if (country.getCountryArmyNumber() < min){
                min= country.getCountryArmyNumber();
                evenCountries.clear();
                evenCountries.add(country);
            }else if (country.getCountryArmyNumber() == min){
                evenCountries.add(country);
            }
        }
        selection = evenCountries.size() > 1 ? evenCountries.size() : 1;
        return evenCountries.get(new Random().nextInt(selection));*/
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
        //fortifies in order to move armies to weaker countries
        countries.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                int total_1 = 0;
                int total_2 = 0;
                for (Country e1: InfoRetriver.getAdjacentEnemy(player, o1)){
                    total_1 += e1.getCountryArmyNumber();
                }
                for (Country e2: InfoRetriver.getAdjacentEnemy(player, o2)){
                    total_2 += e2.getCountryArmyNumber();
                }
                if (total_1 == total_2){
                    return o1.getCountryArmyNumber() - o2.getCountryArmyNumber();
                }
                return total_2 - total_1;
            }
        });
        Country weakest = countries.get(0);
        countries.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                int a1 = o1.getCountryArmyNumber();
                int a2 = o2.getCountryArmyNumber();
                return a2 - a1;
            }
        });
        Country strongest = countries.get(0);
        int transferArmyNbr = strongest.getCountryArmyNumber() - 1;
        weakest.addToCountryArmyNumber(transferArmyNbr);
        strongest.reduceFromCountryArmyNumber(transferArmyNbr);
    }

    @Override
    public String toString(){
        return "Benevolent";
    }
}
