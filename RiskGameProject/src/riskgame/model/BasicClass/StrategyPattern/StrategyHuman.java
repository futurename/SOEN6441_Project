package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

public class StrategyHuman implements Strategy {
    @Override
    public String doAttack(Player humanPlayer, Country attackingCountry, Country defendingCountry,
                           int attackArmyNbr, int defendArmyNbr, boolean allout) {
        if (allout){
            return humanPlayer.alloutAttackSimulate(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr, true);
        }
        return humanPlayer.oneAttackSimulate(attackingCountry, defendingCountry, attackArmyNbr, defendArmyNbr).toString();
    }

    @Override
    public void doReinforcement(Player humanPlayer, Country country, int army) {
        country.addToCountryArmyNumber(army);
        humanPlayer.addArmy(army);
        humanPlayer.addUndeployedArmy(-army);
    }

    @Override
    public void doFortification(Country from, Country to, int army) {
        from.reduceFromCountryArmyNumber(army);
        to.addToCountryArmyNumber(army);
    }

    @Override
    public String toString(){
        return "Human";
    }
}
