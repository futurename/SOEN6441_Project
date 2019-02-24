package riskgame.model;

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
     * @param player a player instance
     * @return an ObservableList of the player's country names
     */
    public static ObservableList getPlayerCountryObservablelist(Player player) {

        ObservableList result = FXCollections.observableArrayList();

        ArrayList<String> coutryList = player.getOwnedCountryNameList();

        for (int i = 0; i < coutryList.size(); i++) {
            String curCountryName = coutryList.get(i);
            int armyNum = Main.graphSingleton.get(curCountryName).getCountry().getCountryArmyNumber();

            String printString = getPrintOneCountryInfo(curCountryName, armyNum);
            result.add(printString);
        }
        return result;
    }

    /**
     * @param curPlayerIndex player index number
     * @param countryIndex the index of a selected country name from the listview
     * @return a formatted ObservableList of adjacent country names and their army numbers
     */
    public static ObservableList getAdjacentCountryObservablelist(int curPlayerIndex, int countryIndex) {
        ObservableList result = FXCollections.observableArrayList();

        ArrayList<String> countryList = playersList.get(Main.curRoundPlayerIndex).getOwnedCountryNameList();
        String selectedCountryName = countryList.get(countryIndex);
        ArrayList<Country> adjacentCountryList = Main.graphSingleton.get(selectedCountryName).getAdjacentCountryList();

        System.out.println("adjacent country: " + adjacentCountryList);

        for (int i = 0; i < adjacentCountryList.size(); i++) {
            Country curAdjacentCountry = adjacentCountryList.get(i);
            String curAdjacentCountryName = curAdjacentCountry.getCountryName();
            int armyNum = curAdjacentCountry.getCountryArmyNumber();
            int countryOwnerIndex = curAdjacentCountry.getCountryOwnerIndex();
            String printString = getPrintOneCountryInfo(curAdjacentCountryName, countryOwnerIndex, armyNum);

            System.out.println(printString);

            result.add(printString);
        }
        return result;
    }

    /**
     * @param oneCountryName a country name
     * @param countryOwnerIndex its owner index number
     * @param armyNum army number of the country
     * @return a formatted combination string of above information
     */
    private static String getPrintOneCountryInfo(String oneCountryName, int countryOwnerIndex, int armyNum) {
        if (countryOwnerIndex == Main.curRoundPlayerIndex) {
            return getPrintOneCountryInfo(oneCountryName, armyNum);
        } else {
            return "player " + countryOwnerIndex + " : " + oneCountryName + " ( " + armyNum + " )";
        }
    }

    /**
     * @param oneCountryName one country name
     * @param armyNum its army number
     * @return a formatted string of above information
     */
    private static String getPrintOneCountryInfo(String oneCountryName, int armyNum) {
        return oneCountryName + " : " + armyNum;
    }

    public static ObservableList<String> getReachableCountryObservableList(int playerIndex, String selectedCountryName) {
        ArrayList<String> updatedCountryInfoList = new ArrayList<>();
        ArrayList<Country> countryList;

        GraphNode selectedGraphNode = Main.graphSingleton.get(selectedCountryName);
        GraphSingleton.INSTANCE.resetGraphVisitedFlag();

        countryList = selectedGraphNode.DepthFirstSearch(playerIndex);

        for(Country country: countryList){
            String updatedCountryInfoString = getPrintOneCountryInfo(country.getCountryName(), country.getCountryArmyNumber());
            updatedCountryInfoList.add(updatedCountryInfoString);
        }

        return FXCollections.observableArrayList(updatedCountryInfoList);
    }
}


