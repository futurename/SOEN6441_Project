package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Fortification phase tester
 */

public class FortificationPhaseTest {
    private GraphTester graphTester;

    @Before
    public void before() throws Exception {
        graphTester = new GraphTester();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: reachableCountryTest()
     * test reachable country list of player zero starting from Ural
     */
    @Test
    public void playerZeroReachableCountryTest() throws Exception {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country uralCountry = graphTester.getDemoGraph().get("Ural").getCountry();
        ArrayList<Country> playerZeroCountryListFromUral = new ArrayList<>();
        worldGraph.get("Ural").getReachableCountryListBFS(0, uralCountry, playerZeroCountryListFromUral);

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
    public void playerOneReachableCountryTest() throws Exception {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country chinaCountry = graphTester.getDemoGraph().get("China").getCountry();
        ArrayList<Country> playerOneCountryListFromChina = new ArrayList<>();
        worldGraph.get("China").getReachableCountryListBFS(1, chinaCountry, playerOneCountryListFromChina);

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
    public void playerTwoReachableCountryTest() throws Exception {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country indonesiaCountry = graphTester.getDemoGraph().get("Indonesia").getCountry();
        ArrayList<Country> playerTwoCountryListFromIndonesia = new ArrayList<>();
        worldGraph.get("Indonesia").getReachableCountryListBFS(2, indonesiaCountry, playerTwoCountryListFromIndonesia);

        ArrayList<String> countryNameList = new ArrayList<>();
        for (Country country : playerTwoCountryListFromIndonesia) {
            countryNameList.add(country.getCountryName());
        }
        Collections.sort(countryNameList);
        Assert.assertEquals(graphTester.getReachablePathPlayerTwoFromIndonesia(), countryNameList);
    }
}
