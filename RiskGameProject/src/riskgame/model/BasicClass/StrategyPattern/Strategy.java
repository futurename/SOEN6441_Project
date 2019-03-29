package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;

public interface Strategy {
    default void doReinforcement(){};
    default void doReinforcement(Country country ,int army){};
    void doAttack();
    void doFortification();

}
