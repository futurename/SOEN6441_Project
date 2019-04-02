package riskgame.model.BasicClass.StrategyPattern;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

public class StrategyHuman implements Strategy {
    @Override
    public String doAttack(Player humanPlayer, Country attackingCountry, Player attacker, Country defendingCountry,
                           Player defender, int attackArmyNbr, int defendArmyNbr, boolean allout) {
        if (allout){
            return humanPlayer.alloutAttackSimulate(attackingCountry, attacker, defendingCountry, defender, attackArmyNbr, defendArmyNbr, true);
        }
        return humanPlayer.oneAttackSimulate(attackingCountry, attacker, defendingCountry, defender, attackArmyNbr, defendArmyNbr).toString();
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
