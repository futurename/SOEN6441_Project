package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Random;

public class StrategyAggressive implements Strategy {
    @Override
    public void doReinforcement(Player player) {
        aggressivelyExchangeCard(player);
        aggressivelyDeployArmy(player);
        UtilMethods.endReinforcement(player);
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

    private void aggressivelyDeployArmy(Player player){
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

    }

    @Override
    public void doFortification(Player player) {

    }

    @Override
    public String toString(){
        return "Aggressive";
    }
}
