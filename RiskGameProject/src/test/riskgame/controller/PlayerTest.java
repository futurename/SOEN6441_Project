package test.riskgame.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.StrategyHuman;
import riskgame.model.Utils.AttackProcess;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Player class test cases
 * @author  Karamveer
 */
public class PlayerTest {
    private Country attackerCountry;
    private Country defenderCountry;
    private int attackArmyNbr;
    private int defnderArmyNbr;
    Player playerTester;

    @Before
    public void setUp() throws Exception {
        attackerCountry = new Country("attacker country");
        defenderCountry = new Country("defender country");
        attackArmyNbr = 3;
        defnderArmyNbr = 2;
        playerTester = new Player(2);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testGetDiceResultList(){
        ArrayList<Integer> result = AttackProcess.getDiceResultList(attackArmyNbr);
        assertEquals(attackArmyNbr, result.size());
    }
}