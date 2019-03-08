package test;

import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.GraphSingleton;
import riskgame.model.Utils.InitWorldMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * demo graph for test
 **/

public class GraphTester {
    /**
     * default path of testing map file
     */
    private static final String TEST_MAP_FILE_PATH = "maps/TestMap/test_map.map";

    /**
     * demo world map singleton for testing
     */
    private LinkedHashMap<String, GraphNode> demoGraph;

    public GraphTester() throws IOException {
        demoGraph = GraphSingleton.INSTANCE.getInstance();
        InitWorldMap.buildWorldMapGraph(TEST_MAP_FILE_PATH, demoGraph);

        demoGraph.get("Middle East").getCountry().setCountryOwnerIndex(0);
        demoGraph.get("Ural").getCountry().setCountryOwnerIndex(0);
        demoGraph.get("Afghanistan").getCountry().setCountryOwnerIndex(0);
        demoGraph.get("India").getCountry().setCountryOwnerIndex(0);
        demoGraph.get("Siberia").getCountry().setCountryOwnerIndex(0);

        demoGraph.get("China").getCountry().setCountryOwnerIndex(1);
        demoGraph.get("Siam").getCountry().setCountryOwnerIndex(1);
        demoGraph.get("Mongolia").getCountry().setCountryOwnerIndex(1);
        demoGraph.get("Irkutsk").getCountry().setCountryOwnerIndex(1);
        demoGraph.get("Yatusk").getCountry().setCountryOwnerIndex(1);

        demoGraph.get("Kamchatka").getCountry().setCountryOwnerIndex(2);
        demoGraph.get("Japan").getCountry().setCountryOwnerIndex(2);
        demoGraph.get("Indonesia").getCountry().setCountryOwnerIndex(2);
        demoGraph.get("New Guinea").getCountry().setCountryOwnerIndex(2);
        demoGraph.get("Western Australia").getCountry().setCountryOwnerIndex(2);
        demoGraph.get("Eastern Australia").getCountry().setCountryOwnerIndex(2);
    }

    public ArrayList<String> getOwnedCountryNames(int playerIndex) {
        ArrayList<String> result = new ArrayList<>();

        for (Map.Entry<String, GraphNode> entry : demoGraph.entrySet()) {
            String curCountryName = entry.getKey();
            int curPlayerIndex = entry.getValue().getCountry().getCountryOwnerIndex();
            if (playerIndex == curPlayerIndex) {
                result.add(curCountryName);
            }
        }
        return result;
    }

    public ArrayList<String> getAdjacentCountryNames(String countryName) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Country> adjacentCountryList = demoGraph.get(countryName).getAdjacentCountryList();

        for (Country country : adjacentCountryList) {
            String curCountryName = country.getCountryName();
            result.add(curCountryName);
        }
        return result;
    }

    public ArrayList<String> getReachablePathPlayerZeroFromUral() {
        ArrayList<String> result = new ArrayList<String>() {
            {
                add("Middle East");
                add("Afghanistan");
                add("India");
                add("Siberia");
            }
        };
        Collections.sort(result);
        return result;
    }

    public ArrayList<String> getReachablePathPlayerOneFromChina() {
        ArrayList<String> result = new ArrayList<String>() {
            {
                add("Siam");
                add("Mongolia");
                add("Irkutsk");
                add("Yatusk");

            }
        };
        Collections.sort(result);
        return result;
    }

    public ArrayList<String> getReachablePathPlayerTwoFromIndonesia() {
        ArrayList<String> result = new ArrayList<String>() {
            {
                add("New Guinea");
                add("Western Australia");
                add("Eastern Australia");
            }
        };
        Collections.sort(result);
        return result;
    }

    public LinkedHashMap<String, GraphNode> getDemoGraph() {
        return demoGraph;
    }

}
