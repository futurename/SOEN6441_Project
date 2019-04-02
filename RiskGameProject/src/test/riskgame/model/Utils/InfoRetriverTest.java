package riskgame.model.Utils;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import test.util.GraphTester;

import java.util.Collections;

/**
 * InfoRetriver Tester.
 *
 * @author WW
 * @version 1.0
 * @since <pre>Mar 26, 2019</pre>
 */
public class InfoRetriverTest {

    GraphTester graphTester;

    @Before
    public void before() throws Exception {
        StartViewController.resetStaticVariables();
        graphTester = new GraphTester();
    }

    @After
    public void after() {
        StartViewController.resetStaticVariables();
    }

    /**
     * Method: getAdjacentCountryObservablelist(int curPlayerIndex, int selectedCountryIndex)
     */
    @Test
    public void testGetAdjacentCountryObservablelist() {
        int playerIndex = 1;
        int selectedCountryIndex = 2;

        ObservableList<Country> acturalList = InfoRetriver.getAdjacentCountryObservablelist(playerIndex, selectedCountryIndex);
        Collections.sort(acturalList, (o1, o2) -> o1.getCountryName().compareTo(o2.getCountryName()));

        ObservableList<Country> expectedList = graphTester.getAdjacentCountryObservablelisFromPlayerOneCountryTwo();
        Assert.assertEquals(expectedList, acturalList);
    }

    /**
     * Method: getAttackableAdjacentCountryList(int curPlayerIndex, Country selectedCountry)
     */
    @Test
    public void testGetAttackableAdjacentCountryList() {
        int playerIndex = 2;
        int selectedCountryIndex = 0;
        String selectedCountryName = Main.playersList.get(playerIndex).getOwnedCountryNameList().get(selectedCountryIndex);
        Country selectedCountry = graphTester.getDemoGraph().get(selectedCountryName).getCountry();

        ObservableList<Country> acturalList = InfoRetriver.getAttackableAdjacentCountryList(playerIndex, selectedCountry);
        Collections.sort(acturalList, (o1, o2) -> o1.getCountryName().compareTo(o2.getCountryName()));

        ObservableList<Country> expectedList = graphTester.getAttackableAdjacentCountryListFromPlayerTwoCountryZero();
        Assert.assertEquals(expectedList, acturalList);
    }

    /**
     * Method: getReachableCountryObservableList(int playerIndex, String selectedCountryName)
     */
    @Test
    public void testGetReachableCountryObservableList() {
        int playerIndex = 0;
        int selectedCountryIndex = 3;
        String selectedCountryName = Main.playersList.get(playerIndex).getOwnedCountryNameList().get(selectedCountryIndex);

        ObservableList<Country> acturalList = InfoRetriver.getReachableCountryObservableList(playerIndex, selectedCountryName);
        Collections.sort(acturalList, (o1, o2) -> o1.getCountryName().compareTo(o2.getCountryName()));

        ObservableList<Country> expectedList = graphTester.getReachableCountryObservableListFromPlayerZeroCountryThree();
        Assert.assertEquals(expectedList, acturalList);
    }

    /**
     * Method: getObservableCountryList(Player player)
     */
    @Test
    public void testGetObservableCountryList() {
        int playerIndex = 1;
        Player player = Main.playersList.get(playerIndex);

        ObservableList<Country> acturalList = InfoRetriver.getObservableCountryList(player, graphTester.getDemoGraph());
        Collections.sort(acturalList, (o1, o2) -> o1.getCountryName().compareTo(o2.getCountryName()));

        ObservableList<Country> expectedList = graphTester.getObservableCountryList(player);
        Assert.assertEquals(expectedList, acturalList);
    }



} 
