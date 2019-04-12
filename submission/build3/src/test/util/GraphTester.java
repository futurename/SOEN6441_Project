package test.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import riskgame.Main;
import riskgame.model.BasicClass.*;
import riskgame.model.Utils.InitWorldMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * demo graph for test
 *
 * @author WW
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

    private LinkedHashMap<String, Continent> demoContinentGraph;

    private ArrayList<Player> demoPlayerArrayList;



    /**
     * GraphTester constructor
     *
     * @throws IOException map file not found
     */
    public GraphTester() throws IOException {
        demoGraph = GraphSingleton.INSTANCE.getInstance();
        demoContinentGraph = new LinkedHashMap<>();
        demoPlayerArrayList = new ArrayList<>();
        InitWorldMap.buildWorldMapGraph(TEST_MAP_FILE_PATH, demoGraph, demoContinentGraph);

        for (int i = 0; i <= 2; i++) {
            Player player = new Player(i);
            Main.playersList.add(player);
        }

        Player player_0 = Main.playersList.get(0);
        demoGraph.get("Middle East").getCountry().setCountryOwner(player_0);
        player_0.getOwnedCountryNameList().add("Middle East");
        demoGraph.get("Ural").getCountry().setCountryOwner(player_0);
        player_0.getOwnedCountryNameList().add("Ural");
        demoGraph.get("Afghanistan").getCountry().setCountryOwner(player_0);
        player_0.getOwnedCountryNameList().add("Afghanistan");
        demoGraph.get("India").getCountry().setCountryOwner(player_0);
        player_0.getOwnedCountryNameList().add("India");
        demoGraph.get("Siberia").getCountry().setCountryOwner(player_0);
        player_0.getOwnedCountryNameList().add("Siberia");

        Player player_1 = Main.playersList.get(1);
        demoGraph.get("China").getCountry().setCountryOwner(player_1);
        player_1.getOwnedCountryNameList().add("China");
        demoGraph.get("Siam").getCountry().setCountryOwner(player_1);
        player_1.getOwnedCountryNameList().add("Siam");
        demoGraph.get("Mongolia").getCountry().setCountryOwner(player_1);
        player_1.getOwnedCountryNameList().add("Mongolia");
        demoGraph.get("Irkutsk").getCountry().setCountryOwner(player_1);
        player_1.getOwnedCountryNameList().add("Irkutsk");
        demoGraph.get("Yatusk").getCountry().setCountryOwner(player_1);
        player_1.getOwnedCountryNameList().add("Yatusk");

        Player player_2 = Main.playersList.get(2);
        demoGraph.get("Kamchatka").getCountry().setCountryOwner(player_2);
        player_2.getOwnedCountryNameList().add("Kamchatka");
        demoGraph.get("Japan").getCountry().setCountryOwner(player_2);
        player_2.getOwnedCountryNameList().add("Japan");
        demoGraph.get("Indonesia").getCountry().setCountryOwner(player_2);
        player_2.getOwnedCountryNameList().add("Indonesia");
        demoGraph.get("New Guinea").getCountry().setCountryOwner(player_2);
        player_2.getOwnedCountryNameList().add("New Guinea");
        demoGraph.get("Western Australia").getCountry().setCountryOwner(player_2);
        player_2.getOwnedCountryNameList().add("Western Australia");
        demoGraph.get("Eastern Australia").getCountry().setCountryOwner(player_2);
        player_2.getOwnedCountryNameList().add("Eastern Australia");


    }

    /**
     * get player's owned country list
     *
     * @param player current player
     * @return arraylist of owned country names
     */
    public ArrayList<String> getOwnedCountryNames(Player player) {
        ArrayList<String> result = new ArrayList<>();
        int playerIndex = player.getPlayerIndex();
        for (Map.Entry<String, GraphNode> entry : demoGraph.entrySet()) {
            String curCountryName = entry.getKey();
            int curPlayerIndex = entry.getValue().getCountry().getOwnerIndex();
            if (playerIndex == curPlayerIndex) {
                result.add(curCountryName);
            }
        }
        return result;
    }

    /**
     * get adjacent country names of a selected country
     *
     * @param countryName selected country name
     * @return arraylist of adjacent country names
     */
    public ArrayList<String> getAdjacentCountryNames(String countryName) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Country> adjacentCountryList = demoGraph.get(countryName).getAdjacentCountryList();

        for (Country country : adjacentCountryList) {
            String curCountryName = country.getCountryName();
            result.add(curCountryName);
        }
        return result;
    }

    /**
     * get reachable country name list from Ural and by player 0
     *
     * @return arraylist of country names
     */
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

    /**
     * get reachable country name list from China and by player 1
     *
     * @return arraylist of country names.
     */
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

    /**
     * get reachable country name list from Indonesia and by player 2
     *
     * @return arraylist of country names
     */
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

    /**
     * get demo graph object
     *
     * @return instance of demo graph
     */
    public LinkedHashMap<String, GraphNode> getDemoGraph() {
        return demoGraph;
    }

    /**
     * get adjacent country list from player 1, country list sequence 2
     *
     * @return ObservableList of country objects.
     */
    public ObservableList<Country> getAdjacentCountryObservablelisFromPlayerOneCountryTwo() {
        ObservableList<Country> result = FXCollections.observableArrayList();

        ArrayList<String> ownedCountryNameList = new ArrayList<String>() {
            {
                add("Siberia");
                add("Japan");
                add("Kamchatka");
                add("China");
                add("Irkutsk");
            }
        };
        Collections.sort(ownedCountryNameList);
        for (String countryName : ownedCountryNameList) {
            result.add(demoGraph.get(countryName).getCountry());
        }

        return result;
    }

    /**
     * get attackable country list from player 2, country list sequence 0
     *
     * @return ObservableList of country objects.
     */
    public ObservableList<Country> getAttackableAdjacentCountryListFromPlayerTwoCountryZero(){
        ObservableList<Country> result = FXCollections.observableArrayList();

        ArrayList<String> ownedCountryNameList = new ArrayList<String>() {
            {
                add("Mongolia");
                add("Irkutsk");
                add("Yatusk");
            }
        };
        Collections.sort(ownedCountryNameList);
        for (String countryName : ownedCountryNameList) {
            result.add(demoGraph.get(countryName).getCountry());
        }

        return result;
    }

    /**
     * get reachable country list from player 0, country list sequence 3
     *
     * @return ObservableList of country objects.
     */
    public ObservableList<Country> getReachableCountryObservableListFromPlayerZeroCountryThree(){
        ObservableList<Country> result = FXCollections.observableArrayList();

        ArrayList<String> ownedCountryNameList = new ArrayList<String>() {
            {
                add("Middle East");
                add("Ural");
                add("Afghanistan");
                add("Siberia");
            }
        };
        Collections.sort(ownedCountryNameList);
        for (String countryName : ownedCountryNameList) {
            result.add(demoGraph.get(countryName).getCountry());
        }
        return result;
    }

    /**
     * get selected player's country list
     * @param player current player
     * @return observablelist of country objects the player owns
     */
    public ObservableList<Country> getObservableCountryList(Player player){
        ArrayList<String> countryNameList = player.getOwnedCountryNameList();
        ObservableList<Country> result = FXCollections.observableArrayList();

        Collections.sort(countryNameList);
        for(String countryName: countryNameList){
            result.add(demoGraph.get(countryName).getCountry());
        }
        return result;
    }
}
