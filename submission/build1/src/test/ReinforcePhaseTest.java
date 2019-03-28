package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.controllers.ReinforceViewController;
import riskgame.model.BasicClass.Country;

/**
 * ReinforcePhase Tester.
 */
public class ReinforcePhaseTest {
    private Country countryTester;

    @Before
    public void before() {
        countryTester = new Country("TestCountry");

        System.out.println(countryTester);
    }

    @After
    public void after() {
    }

    /**
     * Method: getStandardReinforceArmyNum(int countryNum)
     * Minimum army number is 3, calculated number is rounded down to closest integer.
     */
    @Test
    public void testGetStandardReinforceArmyNum() {
        Assert.assertEquals(3, ReinforceViewController.getStandardReinforceArmyNum(9));
        Assert.assertEquals(3, ReinforceViewController.getStandardReinforceArmyNum(10));
        Assert.assertEquals(3, ReinforceViewController.getStandardReinforceArmyNum(11));
        Assert.assertEquals(4, ReinforceViewController.getStandardReinforceArmyNum(12));

        Assert.assertEquals(3, ReinforceViewController.getStandardReinforceArmyNum(8));
        Assert.assertEquals(3, ReinforceViewController.getStandardReinforceArmyNum(0));
        Assert.assertEquals(3, ReinforceViewController.getStandardReinforceArmyNum(-1));
    }

} 
