package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;

import java.util.LinkedHashMap;

public interface Strategy {
    default void doReinforcement(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
    }

    default void doReinforcement(Player humanPlayer, Country country, int army) {
    }

    default void doAttack(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
    }

    default String doAttack(Player humanPlayer, Country attackingCountry, Country defendingCountry, int attackArmy,
                            int defendArmy, boolean isAllout) {
        return "";
    }

    default void doFortification(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {
    }

    default void doFortification(Country from, Country to, int army) {
    }

}
