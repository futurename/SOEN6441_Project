package test.riskgame.model.Utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.StrategyPattern.StrategyAggressive;
import riskgame.model.BasicClass.StrategyPattern.StrategyRandom;
import riskgame.model.Utils.RobotGamingProcess;

import java.util.ArrayList;

/**
 * RobotGamingProcess Tester.
*
 * @author WW
* @since <pre>Mar 30, 2019</pre> 
* @version 1.0 
*/
public class RobotGamingProcessTest {
    private ArrayList<Strategy> strategyArrayList;
    private ArrayList<String> mapFileList;
    private int gamesValue;
    private int gameRoundValue;

@Before
public void before() {
    strategyArrayList = new ArrayList<>();
    strategyArrayList.add(new StrategyAggressive());
    strategyArrayList.add(new StrategyRandom());

    mapFileList = new ArrayList<>();
    mapFileList.add("maps/TournamentModeMaps/test_map.map");
    mapFileList.add("maps/TournamentModeMaps/London.map");
    mapFileList.add("maps/TournamentModeMaps/World.map");

    gamesValue = 3;
    gameRoundValue = 200;
} 

@After
public void after() throws Exception { 
}

    /**
     * test robot init gaming
     * @throws Exception map file not found
     */
    @Test
public void testInitRobotGaming() throws Exception {
    try {
        RobotGamingProcess.initRobotGaming(mapFileList, strategyArrayList, gamesValue, gameRoundValue);
    } catch (ExceptionInInitializerError e) {
        //e.printStackTrace();
    }
        Assert.assertTrue(strategyArrayList.get(0).toString().equals("Aggressive"));
        Assert.assertTrue(strategyArrayList.get(1).toString().equals("Random"));
}


} 
