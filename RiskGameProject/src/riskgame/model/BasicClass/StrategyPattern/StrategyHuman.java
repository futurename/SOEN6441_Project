package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

public class StrategyHuman implements Strategy {
    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doReinforcement(Player humanPlayer, Country country, int army) {
        country.addToCountryArmyNumber(army);
        humanPlayer.addArmy(army);
        humanPlayer.addUndeployedArmy(-army);
    }

    @Override
    public void doFortification(Country from, Country to, int army) {
        from.reduceFromCountryArmyNumber(army);
        to.addToCountryArmyNumber(army);
    }

    @Override
    public String toString(){
        return "Human";
    }
}
