package test.riskgame.model.BasicClass.StrategyPattern; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.ObserverPattern.PhaseViewObservable;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.StrategyAggressive;
import riskgame.model.BasicClass.StrategyPattern.UtilMethods;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Random;

/** 
* StrategyAggressive Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 2, 2019</pre> 
* @version 1.0 
*/ 
public class StrategyAggressiveTest {

@Before
public void before() throws Exception {

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

    /**
     * Pick the strongest country among countries, if even powerful countries exist, randomly pick one
     *
     * @param from owned countries
     * @return selected one
     */
    private Country aggressivelyPickCountryFrom(ArrayList<Country> from) {
        int max = 0;
        ArrayList<Country> evenCountries = new ArrayList<>();
        for (Country country : from) {
            if (country.getCountryArmyNumber() > max) {
                evenCountries.clear();
                evenCountries.add(country);
            } else if (country.getCountryArmyNumber() == max) {
                evenCountries.add(country);
            }
        }
        return evenCountries.get(new Random().nextInt(evenCountries.size()));
    }


public void testDoReinforcement() throws Exception
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
