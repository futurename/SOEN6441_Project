package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;

import java.util.LinkedHashMap;

public class StrategyBenevolent implements Strategy {
    @Override
    public void doReinforcement(Player player) {

    }

    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doFortification(Player player) {

    }

    @Override
    public String toString(){
        return "Benevolent";
    }
}
