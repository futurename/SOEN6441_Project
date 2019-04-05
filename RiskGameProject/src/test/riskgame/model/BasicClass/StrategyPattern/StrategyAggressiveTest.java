package test.riskgame.model.BasicClass.StrategyPattern; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.StrategyAggressive;

import java.util.ArrayList;

/** 
* StrategyAggressive Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 2, 2019</pre> 
* @version 1.0 
*/ 
public class StrategyAggressiveTest {
    private Player curPlayer;
    private Country attackingCountry;
    private PhaseViewObservable curObserver;

@Before
public void before() throws Exception {
    initGameSimulator();
}
    private void initGameSimulator(){
    curPlayer = new Player(0);
    attackingCountry.setCountryOwner(curPlayer);

} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: doReinforcement(Player player, PhaseViewObservable observable) 
* 
*/ 
@Test


public void testDoReinforcement() throws Exception
{
    initGameSimulator();
    StrategyAggressive aggressiveStrategy = new StrategyAggressive();
    Player player = new Player(0);
    curPlayer.setCardPermission(true);
    curPlayer.setActiveStatus(true);
    curPlayer.setFinalWinner(true);
    int curPlayerArmyNbr = curPlayer.getArmyNbr();
    curPlayerArmyNbr = 3;
    try {
        aggressiveStrategy.doReinforcement(curPlayer, curObserver);
    } catch (Error e) {
        System.out.println("ignore alert window");
    } finally {
        // Assert.assertEquals((player.getPlayerIndex()==0, player, player.getActiveStatus()==true, curPlayerArmyNbr == player.getArmyNbr()),(player.getPlayerIndex(),player.getActiveStatus(),curPlayerArmyNbr));

    }
}
    public void testaggressivelyExchangeCard() throws Exception
   {

    }

    public void aggressivelyDeployArmy()
    {

    }
    public void aggressivelyPickCountryFrom()
    {

    }
/** 
* 
* Method: doAttack(Player player) 
* 
*/ 
@Test
public void testDoAttack() throws Exception {




} 

/** 
* 
* Method: doFortification(Player player) 
* 
*/ 
@Test
public void testDoFortification() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: aggressivelyExchangeCard(Player player, PhaseViewObservable observable) 
* 
*/ 
@Test
public void testAggressivelyExchangeCard() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = StrategyAggressive.getClass().getMethod("aggressivelyExchangeCard", Player.class, PhaseViewObservable.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: aggressivelyDeployArmy(Player player) 
* 
*/ 
@Test
public void testAggressivelyDeployArmy() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = StrategyAggressive.getClass().getMethod("aggressivelyDeployArmy", Player.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: aggressivelyPickCountryFrom(ArrayList<Country> from) 
* 
*/ 
@Test
public void testAggressivelyPickCountryFrom() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = StrategyAggressive.getClass().getMethod("aggressivelyPickCountryFrom", ArrayList<Country>.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: aggressivelyAttack(Player player) 
* 
*/ 
@Test
public void testAggressivelyAttack() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = StrategyAggressive.getClass().getMethod("aggressivelyAttack", Player.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: aggressivelyFortify() 
* 
*/ 
@Test
public void testAggressivelyFortify() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = StrategyAggressive.getClass().getMethod("aggressivelyFortify"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
