package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

public class StrategyHuman implements Strategy {
    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doReinforcement(Country country, int army) {
        country.addToCountryArmyNumber(army);
    }

    @Override
    public void doFortification(Player player) {

    }
}
