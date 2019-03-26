package test.riskgame.model.Utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * AttackProcess Tester.
 *
 * @author Karamveer, Wei Wang
 * @version 1.0
 * @since <pre>Mar 21, 2019</pre>
 */
public class AttackProcessTest {
    private Country defendingCountry;
    private Country attackingCountry;
    private Player playerAttacker;
    private Player playerDefender;
    private Continent demoContinent;

    @Before
    public void before() throws Exception {
        initGameSimulator();
    }

    private void initGameSimulator() {
        StartViewController.resetStaticVariables();

        defendingCountry = new Country("defending country");
        attackingCountry = new Country("attacking country");
        playerAttacker = new Player(0);
        playerDefender = new Player(1);

        defendingCountry.setCountryOwnerIndex(playerDefender.getPlayerIndex());
        attackingCountry.setCountryOwnerIndex(playerAttacker.getPlayerIndex());

        String demoContinentName = "DemoContinent";
        demoContinent = new Continent(demoContinentName, 4);
        demoContinent.getContinentCountryGraph().put(attackingCountry.getCountryName(), attackingCountry);
        demoContinent.getContinentCountryGraph().put(defendingCountry.getCountryName(), defendingCountry);
        Main.worldContinentMap.put(demoContinentName, demoContinent);

        defendingCountry.setContinentName(demoContinent.getContinentName());
        attackingCountry.setContinentName(demoContinent.getContinentName());
        attackingCountry.addToCountryArmyNumber(10);
        defendingCountry.addToCountryArmyNumber(4);

        playerAttacker.addToOwnedCountryNameList("attacking country");
        playerDefender.addToOwnedCountryNameList("defending country");
        defendingCountry.addObserver(playerDefender);
        attackingCountry.addObserver(playerAttacker);
        Main.playersList.add(playerAttacker);
        Main.playersList.add(playerDefender);
        GraphNode atkGraphNode = new GraphNode(attackingCountry);
        GraphNode defGraphNode = new GraphNode(defendingCountry);
        Main.graphSingleton.put("defending country", defGraphNode);
        Main.graphSingleton.put("attacking country", atkGraphNode);
    }

    @After
    public void after() throws Exception {
        StartViewController.resetStaticVariables();
    }

    /**
     * Method: attackResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr)
     */
    @Test
    public void testAttackResultProcess() throws Exception {
        initGameSimulator();
        defendingCountry.setCountryArmyNumber(0);
        int remainingArmyNbr = 5;
        try {

            AttackProcess.attackResultProcess(attackingCountry, defendingCountry, remainingArmyNbr);

        } catch (Error e) {
            System.out.println("ignore alert window");
        } finally {
            Assert.assertTrue(defendingCountry.getCountryArmyNumber() == remainingArmyNbr);

            System.out.println("\narmy number: " + defendingCountry.getCountryArmyNumber() + ", " + (defendingCountry.getCountryArmyNumber() == remainingArmyNbr));
        }
    }

    /**
     * Method: updateContinentAndWorldStatus(int attackerIndex, int defenderIndex, Continent curContinent)
     */
    @Test
    public void testUpdateContinentAndWorldStatus() throws Exception {
        initGameSimulator();
        attackingCountry.setCountryOwnerIndex(playerDefender.getPlayerIndex());

        try {
            AttackProcess.updateContinentAndWorldStatus(playerDefender, playerAttacker, demoContinent);
        } catch (NumberFormatException e) {
            System.out.println("ignore popup alert window");
        } finally {
            Assert.assertTrue(playerDefender.getContinentBonus() == demoContinent.getContinentBonusValue());

            System.out.println("\nattacker bonus: " + playerAttacker.getContinentBonus() + ", defender bonus: " + playerDefender.getContinentBonus()
                    + ", continent owner: " + demoContinent.getContinentOwnerIndex() + "\n");
        }

        attackingCountry.setCountryOwnerIndex(playerAttacker.getPlayerIndex());
        defendingCountry.setCountryOwnerIndex(playerAttacker.getPlayerIndex());
        try {
            AttackProcess.updateContinentAndWorldStatus(playerAttacker, playerDefender, demoContinent);
        } catch (ExceptionInInitializerError e) {
            System.out.println("ignore popup alert window");
        } finally {
            Assert.assertTrue(playerAttacker.getContinentBonus() == demoContinent.getContinentBonusValue());

            System.out.println("\nattacker bonus: " + playerAttacker.getContinentBonus() + ", defender bonus: " + playerDefender.getContinentBonus()
                    + ", continent owner: " + demoContinent.getContinentOwnerIndex() + "\n");
        }

    }

    /**
     * Method: updateConqueredCountry(Country attackingCountry, Country defendingCountry, int remainingArmyNbr, Player attackPlayer, Player defendPlayer)
     */
    @Test
    public void testUpdateConqueredCountry() {
        int armyNbrBeforeAttack = attackingCountry.getCountryArmyNumber();
        int assumedRemainingArmyNbr = 3;
        try {
            AttackProcess.updateConqueredCountry(attackingCountry, defendingCountry, assumedRemainingArmyNbr, playerAttacker, playerDefender);
        } catch (Error e) {
            System.out.println("No alert window!");
        } finally {
            boolean attackerConquered = playerAttacker.getOwnedCountryNameList().contains("defending country");
            assertTrue(attackerConquered);
            boolean defenderLoss = playerDefender.getOwnedCountryNameList().contains("defending country");
            assertFalse(defenderLoss);
            int attackingCountryArmy = attackingCountry.getCountryArmyNumber();
            assertEquals(armyNbrBeforeAttack - assumedRemainingArmyNbr, attackingCountryArmy);
            int defendingCountryArmy = defendingCountry.getCountryArmyNumber();
            assertEquals(3, defendingCountryArmy);
        }
    }

    /**
     * Method: isPlayerHasCountry(Player player)
     */
    @Test
    public void testIsPlayerHasCountry() throws Exception {
        Player player = new Player(5);
        Assert.assertFalse(AttackProcess.isPlayerHasCountry(player));

        player.getOwnedCountryNameList().add("demo country");
        Assert.assertTrue(AttackProcess.isPlayerHasCountry(player));
    }


    /**
     * Method: getDiceResultList(int diceTimes)
     */
    @Test
    public void testGetDiceResultList() throws Exception {
        int max = 6;
        int min = 1;

        ArrayList<Integer> result = new ArrayList<>();
        int diceTimes = 3;
        for (int i = 0; i <= diceTimes; i++) {
            result = AttackProcess.getDiceResultList(diceTimes);

        }
        Assert.assertEquals(AttackProcess.getDiceResultList(diceTimes).size(), result.size());
        for (Integer nbr : result) {
            Assert.assertTrue(nbr <= max && nbr >= min);
        }
    }

    /**
     * Method: isCountryConquered(Country country)
     */
    @Test
    public void testIsCountryConquered() throws Exception {
        Country country = new Country("demo country");
        Assert.assertFalse(AttackProcess.isCountryConquered(country));
        country.setCountryArmyNumber(0);
        Assert.assertTrue(AttackProcess.isCountryConquered(country));
    }

    /**
     * Method: isContinentConquered(int playerIndex, String continentName)
     */
    @Test
    public void testIsContinentConquered() throws Exception {
        initGameSimulator();

        Player player = new Player(1);
        Main.playersList.add(player);
        String continentName = "demo continent";
        Continent continent = new Continent(continentName, 2);
        Main.worldContinentMap.put(continentName, continent);
        continent.setContinentOwnerIndex(player.getPlayerIndex());
        Country country = new Country("demoCountry");
        continent.getContinentCountryGraph().put("demoCountry", country);

        Assert.assertFalse(AttackProcess.isContinentConquered(player, continent));
        continent.setContinentOwnerIndex(player.getPlayerIndex());
        country.setCountryOwnerIndex(player.getPlayerIndex());
        Assert.assertTrue(AttackProcess.isContinentConquered(player, continent));
        StartViewController.resetStaticVariables();
    }

    /**
     * Method: updateContinentOwner(int playerIndex, String continentName)
     */
    @Test
    public void testUpdateContinentOwner() throws Exception {
        initGameSimulator();
        Assert.assertFalse(AttackProcess.isContinentConquered(playerAttacker, demoContinent));
        defendingCountry.setCountryOwnerIndex(playerAttacker.getPlayerIndex());
        Assert.assertTrue(AttackProcess.isContinentConquered(playerAttacker, demoContinent));
    }

    /**
     * Method: isWorldConquered(int playerIndex)
     */
    @Test
    public void testIsWorldConquered() throws Exception {
        Assert.assertFalse(AttackProcess.isWorldConquered(playerAttacker.getPlayerIndex()));
        demoContinent.setContinentOwnerIndex(playerAttacker.getPlayerIndex());
        Assert.assertTrue(AttackProcess.isWorldConquered(playerAttacker.getPlayerIndex()));
    }


}
