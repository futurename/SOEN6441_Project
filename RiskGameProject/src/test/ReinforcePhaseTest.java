package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import riskgame.model.BasicClass.Country;
import riskgame.model.phases.ReinforcePhase;

import javax.sound.midi.Soundbank;

/**
 * ReinforcePhase Tester.
 *
 * @author <WW>
 * @version 1.0
 * @since <pre>Mar 5, 2019</pre>
 */
public class ReinforcePhaseTest {
    private Country countryTester;

    @Before
    public void before() throws Exception {
        countryTester = new Country("TestCountry");

        System.out.println(countryTester);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getStandardReinforceArmyNum(int countryNum)
     * Minimum army number is 3, calculated number is rounded down to closest integer.
     */
    @Test
    public void testGetStandardReinforceArmyNum() throws Exception {
        Assert.assertEquals(3, ReinforcePhase.getStandardReinforceArmyNum(9));
        Assert.assertEquals(3, ReinforcePhase.getStandardReinforceArmyNum(10));
        Assert.assertEquals(3, ReinforcePhase.getStandardReinforceArmyNum(11));
        Assert.assertEquals(4, ReinforcePhase.getStandardReinforceArmyNum(12));

        Assert.assertEquals(3, ReinforcePhase.getStandardReinforceArmyNum(8));
        Assert.assertEquals(3, ReinforcePhase.getStandardReinforceArmyNum(0));
        Assert.assertEquals(3, ReinforcePhase.getStandardReinforceArmyNum(-1));
    }

} 
