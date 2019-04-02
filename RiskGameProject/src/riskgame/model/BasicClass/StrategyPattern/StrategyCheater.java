package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;

import java.util.LinkedHashMap;

public class StrategyCheater implements Strategy {
    @Override
    public void doReinforcement(Player player, PhaseViewObservable observable) {

    }

    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doFortification(Player player) {

    }

    @Override
    public String toString(){
        return "Cheater";
    }
}
