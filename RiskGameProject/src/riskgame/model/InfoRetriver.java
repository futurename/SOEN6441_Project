package riskgame.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    public static ObservableList getAdjacentCountryObservablelist(int countryIndex) {
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

    public static void getRenderedCountryItems(int curPlayerIndex, ListView lsv_countries) {
        lsv_countries.setCellFactory(cell -> {
            return new ListCell<String>() {
                private Text text;

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null && !empty) {
                        String curString = item.toString();
                        String[] curStringSplitArray = curString.split(" ");
                        int playerIndex;
                        if (curString.toLowerCase().contains("player")) {
                            playerIndex = Integer.parseInt(curStringSplitArray[1]);
                        }else{
                            playerIndex = curPlayerIndex;
                        }
                        Color curPlayerColor = Main.playersList.get(playerIndex).getPlayerColor();

                        text = new Text(item);
                        text.setFill(curPlayerColor);
                        setGraphic(text);
                    } else if (empty) {
                        setText(null);
                        setGraphic(null);
                    }
                }
            };
        });
    }

    public static ListView<String> getRenderedMapStartview(ObservableList<String> datalist, double avgListviewWidth) {
        ListView<String> result = new ListView<>();

        result.setItems(datalist);
        result.setPrefWidth(avgListviewWidth);

        result.setCellFactory(cell -> {
            return new ListCell<String>() {
                private Text text;

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null && !empty) {
                        text = new Text(item);
                        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                        text.setWrappingWidth(avgListviewWidth - 10);
                        text.setTextAlignment(TextAlignment.CENTER);
                        if (getIndex() == 0) {
                            text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            setStyle("-fx-background-color: yellow");
                            text.setUnderline(true);
                        }
                        setGraphic(text);
                    } else if (empty) {
                        setText(null);
                        setGraphic(null);
                    }
                }
            };
        });
        return result;
    }


    public static ListView<String> getRenderedStartview(int playerIndex, ObservableList<String> datalist, double avgListviewWidth) {
        ListView<String> result = new ListView<>();

        result.setItems(datalist);
        result.setPrefWidth(avgListviewWidth);

        result.setCellFactory(cell -> {
            return new ListCell<String>() {
                private Text text;

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null && !empty) {
                        text = new Text(item);

                        if(playerIndex != -1){
                            if(playerIndex == Main.totalNumOfPlayers){
                                if(getIndex() != 0 && getIndex() != 1){
                                    String curCountryName = text.toString().split("\"")[1];

                                    Country curCountry = Main.worldCountriesMap.get(curCountryName);
                                    int curCountryOwnerIndex = curCountry.getCountryOwnerIndex();

                                    Color curCountryColor = Main.playersList.get(curCountryOwnerIndex).getPlayerColor();
                                    text.setFill(curCountryColor);
                                }
                            }else{
                                Player curPlayer = Main.playersList.get(playerIndex);
                                Color curPlayerColor = curPlayer.getPlayerColor();
                                text.setFill(curPlayerColor);
                            }
                        }
                        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                        text.setWrappingWidth(avgListviewWidth - 10);
                        text.setTextAlignment(TextAlignment.CENTER);
                        if (getIndex() == 0) {
                            text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            setStyle("-fx-background-color: yellow");
                            text.setUnderline(true);
                            /*if(playerIndex >= 0 && playerIndex < Main.totalNumOfPlayers){
                                Player curPlayer = Main.playersList.get(playerIndex);
                                Color curPlayerColor = curPlayer.getPlayerColor();
                                text.setFill(curPlayerColor);
                            }*/
                        }
                        setGraphic(text);
                    } else if (empty) {
                        setText(null);
                        setGraphic(null);
                    }
                }
            };
        });
        return result;
    }
}


