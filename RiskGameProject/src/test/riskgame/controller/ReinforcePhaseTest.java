package test.riskgame.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.controllers.ReinforceViewController;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.Utils.InfoRetriver;

/**
 * ReinforcePhase Tester.
 * @author WW
 */
public class ReinforcePhaseTest {
    private Country countryTester;
    ObservableList<Card> cardArrayList;

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
        Assert.assertEquals(3, InfoRetriver.getStandardReinforceArmyNum(9));
        Assert.assertEquals(3, InfoRetriver.getStandardReinforceArmyNum(10));
        Assert.assertEquals(3, InfoRetriver.getStandardReinforceArmyNum(11));
        Assert.assertEquals(4, InfoRetriver.getStandardReinforceArmyNum(12));

        Assert.assertEquals(3, InfoRetriver.getStandardReinforceArmyNum(8));
        Assert.assertEquals(3, InfoRetriver.getStandardReinforceArmyNum(0));
        Assert.assertEquals(3, InfoRetriver.getStandardReinforceArmyNum(-1));
    }

    @Test
    public void testValidateCardsCombination() {
        cardArrayList = FXCollections.observableArrayList();
        cardArrayList.add(Card.INFANTRY);
        cardArrayList.add(Card.INFANTRY);
        Assert.assertFalse(ReinforceViewController.validateCardsCombination(cardArrayList));

        cardArrayList.add(Card.ARTILLERY);
        Assert.assertFalse(ReinforceViewController.validateCardsCombination(cardArrayList));

        cardArrayList.add(Card.INFANTRY);
        Assert.assertFalse(ReinforceViewController.validateCardsCombination(cardArrayList));

        cardArrayList.remove(Card.INFANTRY);
        Assert.assertFalse(ReinforceViewController.validateCardsCombination(cardArrayList));

        cardArrayList.remove(Card.ARTILLERY);
        cardArrayList.add(Card.INFANTRY);
        Assert.assertTrue(ReinforceViewController.validateCardsCombination(cardArrayList));
    }
} 
