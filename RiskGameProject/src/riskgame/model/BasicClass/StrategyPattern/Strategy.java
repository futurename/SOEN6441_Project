package riskgame.model.BasicClass.StrategyPattern;

import javafx.stage.Stage;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

public interface Strategy {
    default void doReinforcement(Player player){};
    default void doReinforcement(Player humanPlayer, Country country ,int army){};
    default void doAttack(Player player){};
    default void doFortification(Player player){};
    default void doFortification(Country from, Country to ,int army){};

}
