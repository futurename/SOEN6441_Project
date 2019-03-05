package test;

import riskgame.Main;
import riskgame.controllers.ReinforceViewController;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.GraphSingleton;

import java.io.IOException;
import java.util.*;

/**
 * demo graph for test
 *
 * @author WW
 **/

public class GraphTester {
    /**
     * default path of testing map file
     */
    private static final String TEST_MAP_FILE_PATH = "maps/test_map.map";

    /**
     * demo world map singleton for testing
     */
    private LinkedHashMap<String, GraphNode> demoGraph;

    public GraphTester() throws IOException {
        demoGraph = GraphSingleton.INSTANCE.getInstance();
        StartViewController.buildWorldMapGraph(TEST_MAP_FILE_PATH, demoGraph);

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

    private ArrayList<String> getOwnedCountryNames(int playerIndex){
        ArrayList<String> result = new ArrayList<>();

        for(Map.Entry<String, GraphNode> entry: demoGraph.entrySet()){
            String curCountryName = entry.getKey();
            int curPlayerIndex = entry.getValue().getCountry().getCountryOwnerIndex();
            if(playerIndex == curPlayerIndex){
                result.add(curCountryName);
            }
        }
        return result;
    }

    private ArrayList<String> getAdjacentCountryNames(String countryName){
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Country> adjacentCountryList = demoGraph.get(countryName).getAdjacentCountryList();

        for(Country country: adjacentCountryList){
            String curCountryName = country.getCountryName();
            result.add(curCountryName);
        }
        return result;
    }

    private ArrayList<String> getReachablePathPlayerZeroFromUral(){
        ArrayList<String> result = new ArrayList<>();
        result.addAll(Arrays.asList("Middle East", "Afghanistan", "India", "Siberia"));
        return result;
    }

    private ArrayList<String> getReachablePathPlayerOneFromChina(){
        ArrayList<String> result = new ArrayList<>();
        result.addAll(Arrays.asList("Siam", "mongolia", "Irkustk", "Yatusk"));
        return result;
    }

    private ArrayList<String> getReachablePathPlayerTwoFromIndonesia(){
        ArrayList<String> result = new ArrayList<>();
        result.addAll(Arrays.asList("Japan", "Kamchatka", "New Guinea", "Western Australia", "Eastern Australia"));
        return result;
    }

    public LinkedHashMap<String, GraphNode> getDemoGraph() {
        return demoGraph;
    }

}
