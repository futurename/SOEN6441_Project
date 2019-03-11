package riskgame.model.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import riskgame.Main;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.GraphSingleton;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static riskgame.Main.playersList;

/**
 * This class includes methods for processing and organizing different type of data required for display in ListView
 **/
public class InfoRetriver {

    /**
     * acquire number of countries in each continent
     *
     * @param countryList country name arraylist of a player
     * @return a new hashmap that presents each continent name and number of countries in it
     */
    public static HashMap<String, Integer> getCountryDistributionMap(ArrayList<String> countryList) {
        HashMap<String, Integer> countryDistributionMap = new HashMap<>();
        for (String curCountryName : countryList) {
            String curContinentName = Main.graphSingleton.get(curCountryName).getCountry().getContinentName();
            int countOfCountry;
            countOfCountry = countryDistributionMap.getOrDefault(curContinentName, 0);
            countOfCountry++;
            countryDistributionMap.put(curContinentName, countOfCountry);
        }
        return countryDistributionMap;
    }

    /**
     * acquire adjacent country list for a selected country and player
     *
     * @param curPlayerIndex player index number
     * @param selectedCountryIndex   the index of a selected country name from the listview
     * @return a formatted ObservableList of adjacent country names and their army numbers
     */
    public static ObservableList<Country> getAdjacentCountryObservablelist(int curPlayerIndex, int selectedCountryIndex) {
        ObservableList<Country> result;

        ArrayList<String> countryList = playersList.get(curPlayerIndex).getOwnedCountryNameList();
        String selectedCountryName = countryList.get(selectedCountryIndex);
        ArrayList<Country> adjacentCountryList = Main.graphSingleton.get(selectedCountryName).getAdjacentCountryList();

        result = FXCollections.observableArrayList(adjacentCountryList);

        return result;
    }

    public static ObservableList<Country> getAttackableAdjacentCountryList(int curPlayerIndex, Country selectedCountry){
        ObservableList<Country> result;

        String selectedCountryName = selectedCountry.getCountryName();
        ArrayList<Country> adjacentCountryList = Main.graphSingleton.get(selectedCountryName).getAdjacentCountryList();

        ArrayList<Country> attackableAdjacentCountryList = new ArrayList<>();
        for(Country country: adjacentCountryList){
            if(country.getCountryOwnerIndex() != curPlayerIndex){
                attackableAdjacentCountryList.add(country);
            }
        }

        result = FXCollections.observableArrayList(attackableAdjacentCountryList);

        return result;
    }

    /**
     * acquire ObservableList of all reachable countries from a selected country and player
     *
     * @param playerIndex         current player index
     * @param selectedCountryName selected country name
     * @return ObservableList of all reachable countries
     */
    public static ObservableList<Country> getReachableCountryObservableList(int playerIndex, String selectedCountryName) {
        ObservableList<Country> result;
        ArrayList<Country> countryList = new ArrayList<>();

        GraphNode selectedGraphNode = Main.graphSingleton.get(selectedCountryName);
        GraphSingleton.INSTANCE.resetGraphVisitedFlag();
        Country selectedCountry = selectedGraphNode.getCountry();

        selectedGraphNode.getReachableCountryListBFS(playerIndex, selectedCountry, countryList);

        result = FXCollections.observableArrayList(countryList);
        return result;
    }

    /**
     * acquire all owned countries a player has
     *
     * @param player current player
     * @return ObservableList of owned countries
     */
    public static ObservableList<Country> getObservableCountryList(Player player) {
        ArrayList<String> ownedCountryNameList = player.getOwnedCountryNameList();

        ObservableList<Country> result;
        ArrayList<Country> countryList = new ArrayList<>();

        for (String name : ownedCountryNameList) {
            Country country = Main.graphSingleton.get(name).getCountry();
            countryList.add(country);
        }
        result = FXCollections.observableArrayList(countryList);
        return result;
    }
}


