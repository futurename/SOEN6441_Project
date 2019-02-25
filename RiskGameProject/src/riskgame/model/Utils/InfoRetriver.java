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
 * model class for generating different types of required data
 *
 * @author WW
 */
public class InfoRetriver {

    /**
     * @param countryList country name arraylist of a player
     * @return hashmap(continent name, number of coutries) of this player
     */
    public static HashMap<String, Integer> getCountryDistributionMap(ArrayList<String> countryList) {
        HashMap<String, Integer> countryDistributionMap = new HashMap<>();
        for (int i = 0; i < countryList.size(); i++) {
            String curCountryName = countryList.get(i);
            String curContinentName = Main.graphSingleton.get(curCountryName).getCountry().getContinentName();
            int countOfCountry;
            if (countryDistributionMap.containsKey(curContinentName)) {
                countOfCountry = countryDistributionMap.get(curContinentName);
            } else {
                countOfCountry = 0;
            }
            countOfCountry++;
            countryDistributionMap.put(curContinentName, countOfCountry);
        }
        return countryDistributionMap;
    }

    /**
     * @param curPlayerIndex player index number
     * @param countryIndex   the index of a selected country name from the listview
     * @return a formatted ObservableList of adjacent country names and their army numbers
     */
    public static ObservableList<Country> getAdjacentCountryObservablelist(int curPlayerIndex, int countryIndex) {
        ObservableList<Country> result;

        ArrayList<String> countryList = playersList.get(curPlayerIndex).getOwnedCountryNameList();
        String selectedCountryName = countryList.get(countryIndex);
        ArrayList<Country> adjacentCountryList = Main.graphSingleton.get(selectedCountryName).getAdjacentCountryList();

        //System.out.println("adjacent country: " + adjacentCountryList);

        result = FXCollections.observableArrayList(adjacentCountryList);

        return result;
    }

    public static ObservableList<Country> getReachableCountryObservableList(int playerIndex, String selectedCountryName) {
        ObservableList<Country> result;
        ArrayList<Country> countryList = new ArrayList<>();

        GraphNode selectedGraphNode = Main.graphSingleton.get(selectedCountryName);
        GraphSingleton.INSTANCE.resetGraphVisitedFlag();
        Country selectedCountry = selectedGraphNode.getCountry();

        selectedGraphNode.getReachableCountryListBFS(playerIndex, selectedCountry, countryList);
        //selectedGraphNode.getReachableCountryListDFS(playerIndex, selectedCountry, countryList);

        result = FXCollections.observableArrayList(countryList);
        return result;
    }

    public static ObservableList<Country> getObservableCountryList(Player player){
        ArrayList<String> ownedCountryNameList = player.getOwnedCountryNameList();

        ObservableList<Country> result;
        ArrayList<Country> countryList = new ArrayList<>();

        for(String name: ownedCountryNameList){
            Country country = Main.graphSingleton.get(name).getCountry();
            countryList.add(country);
        }

        result = FXCollections.observableArrayList(countryList);

        return result;
    }
}


