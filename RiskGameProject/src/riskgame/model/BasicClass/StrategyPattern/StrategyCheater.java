package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Player;

public class StrategyCheater implements Strategy {
    @Override
    public int doReinforcement(Player player) {
        return 0;
    }

    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doFortification(Player player) {

    }
}
