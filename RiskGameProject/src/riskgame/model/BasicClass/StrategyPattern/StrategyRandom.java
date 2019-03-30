package riskgame.model.BasicClass.StrategyPattern;

import javafx.scene.Node;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static riskgame.Main.curRoundPlayerIndex;
import static riskgame.Main.phaseViewObservable;

public class StrategyRandom implements Strategy {
    private Random r = new Random();

    @Override
    public void doReinforcement(Player player) {
        randomlyExchangeCard(player);
        randomlyDeployArmy(player);
    }

    private void randomlyDeployArmy(Player player) {
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        while (availableArmy > 0){
            int randomArmy = r.nextInt(availableArmy)+1;
            int randomIndex = r.nextInt(countries.size());
            countries.get(randomIndex).addToCountryArmyNumber(randomArmy);
            availableArmy -= randomArmy;
        }
        player.addUndeployedArmy(-player.getUndeployedArmy());
    }

    private void randomlyExchangeCard(Player player) {
        ArrayList<Card> cards = player.getCardsList();
        if (cards.size()>=5){
            int code = UtilMethods.availableCombo(cards);
            UtilMethods.exchangeCard(player, code);

        }else if (cards.size()>=3){
            int code = UtilMethods.availableCombo(cards);
            if (code != -2) {
                //50% probability
                if (r.nextInt(2) == 0){
                    UtilMethods.exchangeCard(player, code);
                }
            }
        }
    }

    @Override
    public void doAttack(Player player) {
//        randomPlayAttack(Player);

    }

    @Override
    public void doFortification(Player player) {

    }
}
