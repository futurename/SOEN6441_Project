package test.riskgame.model.Utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.Main;
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
* @author <Authors name> 
* @since <pre>Mar 21, 2019</pre> 
* @version 1.0 
*/ 
public class AttackProcessTest {
    private Country defendingCountry;
    private Country attackingCountry;
    private Player playerAttacker;
    private Player playerDefender;
    private Continent demoContinent;

    @Before
    public void before() throws Exception {
        defendingCountry=new Country("defending country");
        attackingCountry=new Country("attacking country");
        playerAttacker=new Player(0);
        playerDefender=new Player(1);
        demoContinent=new Continent("continentName",4);
        defendingCountry.setCountryOwnerIndex(playerDefender.getPlayerIndex());
        attackingCountry.setCountryOwnerIndex(playerAttacker.getPlayerIndex());
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
    }

    /**
    *
    * Method: attackResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr)
    *
    */
    @Test
    public void testAttackResultProcess() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: updateContinentAndWorldStatus(int attackerIndex, int defenderIndex, Continent curContinent)
    *
    */
    @Test
    public void testUpdateContinentAndWorldStatus() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: updateConqueredCountry(Country attackingCountry, Country defendingCountry, int remainingArmyNbr, Player attackPlayer, Player defendPlayer)
    *
    */
    @Test
    public void testUpdateConqueredCountry() {
    //TODO: Test goes here...
        try {
            AttackProcess.updateConqueredCountry(attackingCountry, defendingCountry, 3, playerAttacker, playerDefender);
        }catch (ExceptionInInitializerError e){
            System.out.println("No alert window!");
        }
        boolean attackerConquered = playerAttacker.getOwnedCountryNameList().contains("defending country");
        assertTrue(attackerConquered);
        boolean defenderLoss = playerDefender.getOwnedCountryNameList().contains("defending country");
        assertFalse(defenderLoss);
        int attackingCountryArmy = attackingCountry.getCountryArmyNumber();
        assertEquals(10-3, attackingCountryArmy);
        int defendingCountryArmy = defendingCountry.getCountryArmyNumber();
        assertEquals(3, defendingCountryArmy);
    }

    /**
    *
    * Method: isPlayerHasCountry(Player player)
    *
    */
    @Test
    public void testIsPlayerHasCountry() throws Exception {
        Player player=new Player(5);
        Assert.assertFalse(AttackProcess.isPlayerHasCountry(player));

        player.getOwnedCountryNameList().add("demo country");
        Assert.assertTrue(AttackProcess.isPlayerHasCountry(player));
    }


    /**
    *
    * Method: getDiceResultList(int diceTimes)
    *
    */
    @Test
    public void testGetDiceResultList() throws Exception {
        ArrayList<Integer> result=new ArrayList<>();
        int diceTimes=3;
        for(int i=0;i<=diceTimes;i++) {
            result = AttackProcess.getDiceResultList(diceTimes);

        }
        Assert.assertEquals(AttackProcess.getDiceResultList(diceTimes),result);

    }

    /**
    *
    * Method: isCountryConquered(Country country)
    *
    */
    @Test
    public void testIsCountryConquered() throws Exception {
        Country country=new Country("demo country");
        Assert.assertFalse(AttackProcess.isCountryConquered(country));
        country.setCountryArmyNumber(0);
        Assert.assertTrue(AttackProcess.isCountryConquered(country));


    //TODO: Test goes here...
    }

    /**
    *
    * Method: isContinentConquered(int playerIndex, String continentName)
    *
    */
    @Test
    public void testIsContinentConquered() throws Exception {
        Player player=new Player(5);
        Continent continent=new Continent("demo continent",2);
        Assert.assertFalse(AttackProcess.isContinentConquered(player.getPlayerIndex(),continent.getContinentName()));
        continent.setContinentOwnerIndex(player.getPlayerIndex());
        Assert.assertTrue(AttackProcess.isContinentConquered(player.getPlayerIndex(),continent.getContinentName()));

        //TODO: Test goes here...
    }

    /**
    *
    * Method: updateContinentOwner(int playerIndex, String continentName)
    *
    */
    @Test
    public void testUpdateContinentOwner() throws Exception {

    //TODO: Test goes here...
    }

    /**
    *
    * Method: updateWorldOwner(int playerIndex)
    *
    */
    @Test
    public void testUpdateWorldOwner() throws Exception {
    //TODO: Test goes here...
    }


    }
