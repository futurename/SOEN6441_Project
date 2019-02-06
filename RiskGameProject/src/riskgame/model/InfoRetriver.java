package riskgame.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import riskgame.Main;
import riskgame.classes.Country;
import riskgame.classes.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoRetriver {

    public static HashMap<String, Integer> getCountryDistributionMap(ArrayList<String> countryList) {
        HashMap<String, Integer> countryDistributionMap = new HashMap<>();
        for (int i = 0; i < countryList.size(); i++) {
            String curCountryName = countryList.get(i);
            String curContinentName = Main.worldCountriesMap.get(curCountryName).getContinentName();
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

    public static ObservableList getPlayerCountryObservablelist(Player player) {

        ObservableList result = FXCollections.observableArrayList();

        ArrayList<String> coutryList = player.getOwnedCountryNameList();

        for (int i = 0; i < coutryList.size(); i++) {
            String curCountryName = coutryList.get(i);
            int armyNum = Main.worldCountriesMap.get(curCountryName).getCountryArmyNumber();

            String printString = getPrintOneCountryInfo(curCountryName, armyNum);
            result.add(printString);
        }

        return result;
    }

    public static ObservableList getAdjacentCountryObservablelist(int curPlayerIndex, int countryIndex) {
        ObservableList result = FXCollections.observableArrayList();

        ArrayList<String> countryList = Main.playersList.get(Main.curRoundPlayerIndex).getOwnedCountryNameList();
        String selectedCountryName = countryList.get(countryIndex);
        ArrayList<String> adjacentCountryList = Main.worldCountriesMap.get(selectedCountryName).getAdjacentCountryNameList();

        for (int i = 0; i < adjacentCountryList.size(); i++) {
            String oneCountryName = adjacentCountryList.get(i);
            Country curCountry = Main.worldCountriesMap.get(oneCountryName);
            int armyNum = curCountry.getCountryArmyNumber();
            int countryOwnerIndex = curCountry.getCountryOwnerIndex();
            String printString = getPrintOneCountryInfo(oneCountryName, countryOwnerIndex, armyNum);

            System.out.println(printString);

            result.add(printString);
        }
        return result;
    }

    private static String getPrintOneCountryInfo(String oneCountryName, int countryOwnerIndex, int armyNum) {
        if (countryOwnerIndex == Main.curRoundPlayerIndex) {
            return getPrintOneCountryInfo(oneCountryName, armyNum);
        } else {
            return "player " + countryOwnerIndex + " : " + oneCountryName + " ( " + armyNum + " )";
        }
    }

    private static String getPrintOneCountryInfo(String oneCountryName, int armyNum) {
        return oneCountryName + " : " + armyNum;
    }

}


