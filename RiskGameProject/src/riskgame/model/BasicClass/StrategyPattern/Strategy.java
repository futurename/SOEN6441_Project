package riskgame.model.BasicClass.StrategyPattern;

import javafx.stage.Stage;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

public interface Strategy {
    default int doReinforcement(Player player){return -1;};
    default void doReinforcement(Country country ,int army){};
    default void doAttack(Player player){};
    default void doFortification(Player player){};
    default void doFortification(Country from, Country to ,int army){};

}
