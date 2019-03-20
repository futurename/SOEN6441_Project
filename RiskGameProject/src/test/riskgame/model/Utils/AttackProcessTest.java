package test.riskgame.model.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* AttackProcess Tester. 
* 
* @author <Authors name> 
* @since <pre>Mar 19, 2019</pre> 
* @version 1.0 
*/ 
public class AttackProcessTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: alloutAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, TextArea txa_attackInfoDisplay) 
* 
*/ 
@Test
public void testAlloutAttackSimulate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: oneAttackSimulate(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, TextArea txa_attackInfoDisplay) 
* 
*/ 
@Test
public void testOneAttackSimulate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getDiceResultList(int diceTimes) 
* 
*/ 
@Test
public void testGetDiceResultList() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isCountryConquered(Country country) 
* 
*/ 
@Test
public void testIsCountryConquered() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isContinentConquered(int playerIndex, String continentName) 
* 
*/ 
@Test
public void testIsContinentConquered() throws Exception { 
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


/** 
* 
* Method: recursiveAttack(Country attackingCountry, Country defendingCountry, int attackArmyNbr, int defendArmyNbr, StringBuilder stringBuilder) 
* 
*/ 
@Test
public void testRecursiveAttack() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("recursiveAttack", Country.class, Country.class, int.class, int.class, StringBuilder.class); 
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
* Method: getOneAttackResult(Country attackingCountry, Country defendingCountry, int attackableArmyNbr, int defendableArmyNbr, StringBuilder stringBuilder) 
* 
*/ 
@Test
public void testGetOneAttackResult() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("getOneAttackResult", Country.class, Country.class, int.class, int.class, StringBuilder.class); 
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
* Method: attackResultProcess(Country attackingCountry, Country defendingCountry, int remainingArmyNbr) 
* 
*/ 
@Test
public void testAttackResultProcess() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("attackResultProcess", Country.class, Country.class, int.class); 
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
* Method: updateContinentAndWorldStatus(int attackerIndex, int defenderIndex, Continent curContinent) 
* 
*/ 
@Test
public void testUpdateContinentAndWorldStatus() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("updateContinentAndWorldStatus", int.class, int.class, Continent.class); 
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
* Method: updateConqueredCountry(Country attackingCountry, Country defendingCountry, int remainingArmyNbr, Player attackPlayer, Player defendPlayer) 
* 
*/ 
@Test
public void testUpdateConqueredCountry() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("updateConqueredCountry", Country.class, Country.class, int.class, Player.class, Player.class); 
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
* Method: isPlayerHasCountry(Player player) 
* 
*/ 
@Test
public void testIsPlayerHasCountry() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("isPlayerHasCountry", Player.class); 
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
* Method: updateContinentOwner(int playerIndex, String continentName) 
* 
*/ 
@Test
public void testUpdateContinentOwner() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AttackProcess.getClass().getMethod("updateContinentOwner", int.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
