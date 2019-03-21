package test.riskgame.model.Utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.AttackProcess;

/** 
* AttackProcess Tester. 
* 
* @author <Authors name> 
* @since <pre>Mar 21, 2019</pre> 
* @version 1.0 
*/ 
public class AttackProcessTest { 

@Before
public void before() throws Exception {
    Country defendingCountry=new Country("defending country");
    Country attackingCountry=new Country("attacking country");
    Player playerAttacker=new Player(0);
    Player playerDefender=new Player(1);
    Continent demoContinent=new Continent("continentName",4);
    defendingCountry.setCountryOwnerIndex(playerDefender.getPlayerIndex());
    attackingCountry.setCountryOwnerIndex(playerAttacker.getPlayerIndex());
    defendingCountry.setContinentName(demoContinent.getContinentName());
    attackingCountry.setContinentName(demoContinent.getContinentName());

    attackingCountry.addToCountryArmyNumber(10);
    defendingCountry.addToCountryArmyNumber(4);

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
public void testUpdateConqueredCountry() throws Exception { 
//TODO: Test goes here... 
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
