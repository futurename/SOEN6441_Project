package test.riskgame.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;
import test.util.GraphTester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


/**
 * Fortification phase tester
 * @author WW
 */

public class FortificationPhaseTest {
    private GraphTester graphTester;
    private Player player0;
    private Player player1;
    private Player player2;

    @Before
    public void before() throws Exception {
        graphTester = new GraphTester();
        player0 = new Player(0);
        player1 = new Player(1);
        player2 = new Player(2);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: reachableCountryTest()
     * test reachable country list of player zero starting from Ural
     */
    @Test
    public void playerZeroReachableCountryTest() {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country uralCountry = graphTester.getDemoGraph().get("Ural").getCountry();
        ArrayList<Country> playerZeroCountryListFromUral = new ArrayList<>();
        worldGraph.get("Ural").getReachableCountryListBFS(player0, uralCountry, playerZeroCountryListFromUral);

        ArrayList<String> countryNameList = new ArrayList<>();
        for (Country country : playerZeroCountryListFromUral) {
            countryNameList.add(country.getCountryName());
        }
        Collections.sort(countryNameList);
        Assert.assertEquals(graphTester.getReachablePathPlayerZeroFromUral(), countryNameList);
    }

    /**
     * /**
     * Method: reachableCountryTest()
     * test reachable country list of player one starting from China
     */
    @Test
    public void playerOneReachableCountryTest() {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country chinaCountry = graphTester.getDemoGraph().get("China").getCountry();
        ArrayList<Country> playerOneCountryListFromChina = new ArrayList<>();
        worldGraph.get("China").getReachableCountryListBFS(player1, chinaCountry, playerOneCountryListFromChina);

        ArrayList<String> countryNameList = new ArrayList<>();
        for (Country country : playerOneCountryListFromChina) {
            countryNameList.add(country.getCountryName());
        }
        Collections.sort(countryNameList);
        Assert.assertEquals(graphTester.getReachablePathPlayerOneFromChina(), countryNameList);
    }

    /**
     * /**
     * Method: reachableCountryTest()
     * test reachable country list of player two starting from Indonesia
     */
    @Test
    public void playerTwoReachableCountryTest() {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country indonesiaCountry = graphTester.getDemoGraph().get("Indonesia").getCountry();
        ArrayList<Country> playerTwoCountryListFromIndonesia = new ArrayList<>();
        worldGraph.get("Indonesia").getReachableCountryListBFS(player2, indonesiaCountry, playerTwoCountryListFromIndonesia);

        ArrayList<String> countryNameList = new ArrayList<>();
        for (Country country : playerTwoCountryListFromIndonesia) {
            countryNameList.add(country.getCountryName());
        }
        Collections.sort(countryNameList);
        Assert.assertEquals(graphTester.getReachablePathPlayerTwoFromIndonesia(), countryNameList);
    }
}
