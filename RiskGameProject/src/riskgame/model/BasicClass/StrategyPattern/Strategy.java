package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;

public interface Strategy {
    default void doReinforcement(Player player, PhaseViewObservable observable) {
    }

    default void doReinforcement(Player humanPlayer, Country country, int army) {
    }

    default void doAttack(Player player) {
    }

    default String doAttack(Player humanPlayer, Country attackingCountry, Player attacker, Country defendingCountry,
                            Player defender, int attackArmy, int defendArmy, boolean isAllout) {
        return "";
    }

    default void doFortification(Player player) {
    }

    default void doFortification(Country from, Country to, int army) {
    }

}
