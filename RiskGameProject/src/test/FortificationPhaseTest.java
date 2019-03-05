package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.phases.ReinforcePhase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * Fortification phase tester
 *
 * @author <WW>
 * @version 1.0
 * @since <pre>Mar 5, 2019</pre>
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
     * test reachable country list
     */
    @Test
    public void reachableCountryTest() throws Exception {
        LinkedHashMap<String, GraphNode> worldGraph = graphTester.getDemoGraph();
        Country uralCountry = graphTester.getDemoGraph().get("Ural").getCountry();
        //ArrayList<Country> playerZeroCountryListFromUral = worldGraph.get("Ural").getReachableCountryListBFS(0, uralCountry,);



    }

}
