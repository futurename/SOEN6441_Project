package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Random;

public class StrategyRandom implements Strategy {
    private Random r = new Random();

    @Override
    public void doReinforcement(Player player) {
        int availableArmy = player.getUndeployedArmy();
        ArrayList<Country> countries = InfoRetriver.getCountryList(player);
        while (availableArmy > 0){
            int randomArmy = r.nextInt(availableArmy)+1;
            int randomIndex = r.nextInt(countries.size());
            countries.get(randomIndex).addToCountryArmyNumber(randomArmy);
            availableArmy -= randomArmy;
        }
        player.addUndeployedArmy(-player.getUndeployedArmy());
        //TODO go to next step

    }

    @Override
    public void doAttack(Player player) {

    }

    @Override
    public void doFortification(Player player) {

    }
}
