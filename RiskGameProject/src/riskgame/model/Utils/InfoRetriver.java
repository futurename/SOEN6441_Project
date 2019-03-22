package riskgame.model.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import riskgame.Main;
import riskgame.model.BasicClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static riskgame.Main.*;

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



    public static int getPlayerControlPecentage(Player curPlayer){
        int result = 0;
        int ownedCountryNbr = curPlayer.getOwnedCountryNameList().size();
        int totalCountryNbr = Main.graphSingleton.size();
        float tmpRatio = (float)totalCountryNbr / ownedCountryNbr;
        result = Math.round(tmpRatio * 100);

        return result;
    }


    public static int getConqueredContinentNbr(Player curPlayer) {
        int playerIndex = curPlayer.getPlayerIndex();
        int result = 0;
        for(Map.Entry<String, Continent> entry: Main.worldContinentMap.entrySet()){
            Continent curContinent = entry.getValue();
            if(curContinent.getContinentOwnerIndex() == playerIndex){
                result++;
            }
        }
        return result;
    }

    /**
     * Set contents to player domination the pane
     * method called when updating or initialization
     *
     * @param arg notification message
     * @param view domination view
     */
    public static <V extends VBox> void updateDominationView(String arg, V view){
        playerDomiViewObservable.updateObservable();
        playerDomiViewObservable.notifyObservers(arg);
        ArrayList<Label> labelList = new ArrayList<>();

        //empty the vBox before adding new contents
        if (view.getChildren().size() != 0) {
            view.getChildren().remove(0, totalNumOfPlayers);
        }

        for (int playerIndex = 0; playerIndex < totalNumOfPlayers; playerIndex++) {
            Color curPlayerColor = playersList.get(playerIndex).getPlayerColor();
            Label oneLabel = new Label();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player:  ").append(playerIndex)
                    .append("\nControl countries:  ")
                    .append(playerDomiViewObserver.getControlledCountryNbrList().get(playerIndex))
                    .append("\nControl ratio:  ")
                    .append(String.format("%,.2f%%",(playerDomiViewObserver.getControlRatioList().get(playerIndex)*100)))
                    .append("\nControlled continents:  ")
                    .append(playerDomiViewObserver.getControlledContinentNbrList().get(playerIndex))
                    .append("\nTotal army:  ")
                    .append(playerDomiViewObserver.getTotalArmyNbrList().get(playerIndex))
                    .append("\nContinent bonus:  ")
                    .append(playerDomiViewObserver.getContinentBonusList().get(playerIndex))
                    .append("\n\n");
            oneLabel.setText(stringBuilder.toString());
            oneLabel.setTextFill(curPlayerColor);
            oneLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            labelList.add(oneLabel);
        }

        view.getChildren().addAll(labelList);
    }
}


