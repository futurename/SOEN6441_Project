package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;

public class StrategyHuman implements Strategy {
    @Override
    public void doAttack() {

    }

    @Override
    public void doReinforcement(Country country, int army) {
        country.addToCountryArmyNumber(army);
    }

    @Override
    public void doFortification() {

    }
}
