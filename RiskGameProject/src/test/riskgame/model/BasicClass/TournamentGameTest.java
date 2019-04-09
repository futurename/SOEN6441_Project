package test.riskgame.model.BasicClass;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.StrategyPattern.StrategyAggressive;
import riskgame.model.BasicClass.StrategyPattern.StrategyRandom;
import riskgame.model.BasicClass.TournamentGame;

import java.util.ArrayList;

/** 
* TournamentGame Tester. 
* 
* @author <Authors name> 
* @since <pre>Mar 30, 2019</pre> 
* @version 1.0 
*/ 
public class TournamentGameTest {
    private String mapPath;
    private ArrayList<Strategy> strategyArrayList;
    private int gamesvalue;
    private int gameRoundValue;

@Before
public void before() throws Exception {
    mapPath = "maps/World.map";

    strategyArrayList = new ArrayList<>();


    strategyArrayList.add(new StrategyRandom());
    strategyArrayList.add(new StrategyAggressive());

    gamesvalue = 3;
    gameRoundValue = 30;
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: gameStart() 
* 
*/ 
@Test
public void testGameStart() throws Exception {


}


/** 
* 
* Method: initMapAndPlayers() 
* 
*/ 
@Test
public void testInitMapAndPlayers() throws Exception { 
//TODO: Test goes here...
    String mapFile=" ";
    ArrayList<Strategy> playerStrategyList=new ArrayList<>();
    int gameRoundValue=0;
    TournamentGame games=new TournamentGame(mapFile, playerStrategyList,gameRoundValue);
    String result = "Draw";
    String actualResult=games.getGameWinner();
    Assert.assertEquals(actualResult,result);
/*
try { 
   Method method = TournamentGame.getClass().getMethod("initMapAndPlayers"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
} 

} 
