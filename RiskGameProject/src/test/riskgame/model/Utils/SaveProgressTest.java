package test.riskgame.model.Utils; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.*;
import riskgame.model.Utils.SaveProgress;

/** 
* SaveProgress Tester. 
* 
* @author Karamveer
* @since <pre>Apr 8, 2019</pre> 
* @version 1.0 
*/ 
public class SaveProgressTest {

    @Before
    public void before() throws Exception {
        GameSimulator();
    }

    private void GameSimulator() {
        StartViewController.resetStaticVariables();

        Country defendingCountry = new Country("defending country");
        Country attackingCountry = new Country("attacking country");
        Player playerAttacker = new Player(0);
        Player playerDefender = new Player(1);

        defendingCountry.setCountryOwner(playerDefender);
        attackingCountry.setCountryOwner(playerAttacker);

        String demoContinentName = "DemoContinent";
        Continent demoContinent = new Continent(demoContinentName, 4);
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

    /**
    *
    * Method: SaveFile(String phase, int curPlayer, String path, String mapName, boolean AorF, boolean AOC)
    *
    */
    @Test
    public void testSaveFile() throws Exception {
        SaveProgress sp = new SaveProgress();
        sp.SaveFile("Attack",0,"./","saveProgressTest",true,
                0,0);
        Assert.assertTrue(sp.CheckSave("./saveProgressTest.save"));
    }
} 
