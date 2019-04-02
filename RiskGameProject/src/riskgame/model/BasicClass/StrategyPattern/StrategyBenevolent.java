package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;

import java.util.LinkedHashMap;

public class StrategyBenevolent implements Strategy {
    @Override
    public void doReinforcement(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {

    }

    @Override
    public void doAttack(Player player, LinkedHashMap<String, GraphNode> worldHashMap) {

    }

    @Override
    public void doFortification(Player player, LinkedHashMap<String, GraphNode> worldhashMap) {

    }

    @Override
    public String toString(){
        return "Benevolent";
    }
}
