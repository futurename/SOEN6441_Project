package riskgame.model.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static riskgame.Main.*;

/**
 * This class includes methods for processing and organizing different type of data required for display in ListView
 *
 * @author WW
 **/
public class InfoRetriver {

    /**
     * default factor for calculting standard number of army used for reinforce phase
     */
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    /**
     * minimun army number that is assigned to each player
     */
    private static final int DEFAULT_MIN_REINFORCE_ARMY_NBR = 3;

    /**
     * acquire adjacent country list for a selected country and player
     *
     * @param curPlayerIndex       player index number
     * @param selectedCountryIndex the index of a selected country name from the listview
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

    /**
     * Acquire all adjacent enemy countries of the selected country
     *
     * @param player          index of current player
     * @param selectedCountry selected country by the user
     * @return Observablelist of enemy country list
     */
    public static ObservableList<Country> getAttackableAdjacentCountryList(Player player, Country selectedCountry) {
        ObservableList<Country> result;
        ArrayList<Country> attackableAdjacentCountryList = getAdjacentEnemy(player, selectedCountry);
        result = FXCollections.observableArrayList(attackableAdjacentCountryList);
        return result;
    }

    public static ArrayList<Country> getAdjacentEnemy(Player player, Country selectedCountry) {
        int curPlayerIndex = player.getPlayerIndex();
        String selectedCountryName = selectedCountry.getCountryName();
        ArrayList<Country> adjacentCountryList = player.getWorldMapInstance().get(selectedCountryName).getAdjacentCountryList();

        ArrayList<Country> attackableAdjacentCountryList = new ArrayList<>();
        for (Country country : adjacentCountryList) {
            if (country.getOwnerIndex() != curPlayerIndex) {
                attackableAdjacentCountryList.add(country);
            }
        }
        return attackableAdjacentCountryList;
    }

    /**
     * acquire ObservableList of all reachable countries from a selected country and player
     *
     * @param player              current player index
     * @param selectedCountryName selected country name
     * @return ObservableList of all reachable countries
     */
    public static ObservableList<Country> getReachableCountryObservableList(Player player, String selectedCountryName) {
        ObservableList<Country> result;
        ArrayList<Country> countryList = getReachableCountry(player, selectedCountryName);
        result = FXCollections.observableArrayList(countryList);
        return result;
    }

    /**
     * acquire ObservableList of all reachable countries from a selected country and player
     *
     * @param player              current player
     * @param selectedCountryName selected country name
     * @return ObservableList of all reachable countries
     */
    public static ArrayList<Country> getReachableCountry(Player player, String selectedCountryName) {
        int curPlayerIndex = player.getPlayerIndex();
        ArrayList<Country> countryList = new ArrayList<>();
        GraphNode selectedGraphNode = player.getWorldMapInstance().get(selectedCountryName);
        resetCountryVisitFlag(player);
        Country selectedCountry = selectedGraphNode.getCountry();
        selectedGraphNode.getReachableCountryListBFS(player, selectedCountry, countryList);
        return countryList;
    }

    private static void resetCountryVisitFlag(Player player) {
        LinkedHashMap<String, GraphNode> worldHashMap = player.getWorldMapInstance();
        for (Map.Entry<String, GraphNode> node : worldHashMap.entrySet()) {
            node.getValue().setVisited(false);
        }
    }

    /**
     * acquire all owned countries a player has
     *
     * @param player current player
     * @return ObservableList of owned countries
     */
    public static ObservableList<Country> getObservableCountryList(Player player) {
        ObservableList<Country> result;
        ArrayList<Country> countryList = getCountryList(player);
        result = FXCollections.observableArrayList(countryList);
        return result;
    }

    public static ArrayList<Country> getCountryList(Player player) {
        ArrayList<String> ownedCountryNameList = player.getOwnedCountryNameList();
        ArrayList<Country> countryList = new ArrayList<>();
        for (String name : ownedCountryNameList) {
            Country country = player.getWorldMapInstance().get(name).getCountry();
            countryList.add(country);
        }
        return countryList;
    }


    /**
     * get count of conquered continents by the specified player
     *
     * @param curPlayer current player
     * @return number of conquered continents
     */
    public static int getConqueredContinentNbr(Player curPlayer) {
        int playerIndex = curPlayer.getPlayerIndex();
        int result = 0;
        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            Continent curContinent = entry.getValue();
            if (curContinent.getContinentOwnerIndex() == playerIndex) {
                result++;
            }
        }
        return result;
    }

    /**
     * Set contents to player domination the pane
     * method called when updating or initialization
     *
     * @param arg  notification message
     * @param view domination view
     * @param <V>  Vbox UI control
     */
    public static <V extends VBox> void updateDominationView(String arg, V view) {
        playerDomiViewObservable.updateObservable();
        playerDomiViewObservable.notifyObservers(arg);
        ArrayList<Label> labelList = new ArrayList<>();

        //empty the vBox before adding new contents
        if (view.getChildren().size() != 0) {
            view.getChildren().remove(0, totalNumOfPlayers);
        }

        for (int playerIndex = 0; playerIndex < totalNumOfPlayers; playerIndex++) {
            Player curPlayer = playersList.get(playerIndex);
            Color curPlayerColor = curPlayer.getPlayerColor();
            Label oneLabel = new Label();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player: ").append(playerIndex)
                    .append(" [").append(curPlayer.getPlayerName()).append("]")
                    .append("\nControl ratio:  ")
                    .append(String.format("%,.2f%%", (playerDomiViewObserver.getControlRatioList().get(playerIndex) * 100)))
                    .append("\nControl countries:  ")
                    .append(playerDomiViewObserver.getControlledCountryNbrList().get(playerIndex))
                    .append("\nTotal army:  ")
                    .append(playerDomiViewObserver.getTotalArmyNbrList().get(playerIndex))
                    .append("\nControlled continents:  ")
                    .append(playerDomiViewObserver.getControlledContinentNbrList().get(playerIndex))
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

    /**
     * valid whether the attacker has an attackable country, if not, move to next phase.
     *
     * @param player           index of current player
     * @param ownedCountryList country object list
     * @return true for valid, false for none
     */
    public static boolean validateAttackerStatus(Player player, Iterable<Country> ownedCountryList) {

        boolean isOneCountryHasAttackableCountry = false;

        for (Country country : ownedCountryList) {
            if (country.getCountryArmyNumber() > 1) {
                ObservableList<Country> attackableCountryList = InfoRetriver.getAttackableAdjacentCountryList(player, country);

                if (!attackableCountryList.isEmpty()) {
                    isOneCountryHasAttackableCountry = true;
                    System.out.println(player.getPlayerName() + "NO attackable country!");
                    break;
                }
            }
        }
        return isOneCountryHasAttackableCountry;
    }

    public static ArrayList<Country> getOwnedAttackerList(Player player) {
        ArrayList<Country> attackerList = new ArrayList<>();
        ArrayList<Country> owned = InfoRetriver.getCountryList(player);
        for (Country country : owned) {
            if (country.getCountryArmyNumber() > 1) {
                if (!InfoRetriver.getAdjacentEnemy(player, country).isEmpty()) {
                    attackerList.add(country);
                }
            }
        }
        return attackerList;
    }

    public static Country getOwnedStrongestCountry(Player player) {
        Country result = null;
        ArrayList<Country> ownedCountryList = getOwnedAttackerList(player);
        if (!ownedCountryList.isEmpty()) {

            System.out.println("get strongest country, list: " + ownedCountryList);

            ownedCountryList.sort(new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    return o2.getCountryArmyNumber() - o1.getCountryArmyNumber();
                }
            });
            result = ownedCountryList.get(0).getCountryArmyNumber() > 1 ? ownedCountryList.get(0) : null;
            System.out.println("\n\n\n\nfirst: " + ownedCountryList.get(0).getCountryArmyNumber() + ", last: " + ownedCountryList.get(ownedCountryList.size() - 1).getCountryArmyNumber() + "\n\n\n\n");
        }
        return result;
    }

    /**
     * check next player is still in the game and return index of next valid player
     *
     * @return valid player index
     */
    public static int getNextActivePlayer(int fromPlayer) {
        int tempIndex = fromPlayer;
        while (true) {
            tempIndex = (tempIndex + 1) % totalNumOfPlayers;
            Player tempPlayer = playersList.get(tempIndex);
            if (tempPlayer.getActiveStatus()) {
                break;
            }
        }
        return tempIndex;
    }

    public static File showFileChooser(String titleString) {
        Stage fileStage = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titleString);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files(*.map)", "*.map");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(fileStage);
    }

    public static File showSavedFileChooser(String titleString) {
        Stage fileStage = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titleString);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files(*.save)", "*.save");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(fileStage);
    }

    /**
     * calculate army number for reinforcement with default value
     *
     * @param countryNum number of all countries a player owns
     * @return calculated number of army for reinforcement phase
     */
    public static int getStandardReinforceArmyNum(int countryNum) {
        int calResult = countryNum / DEFAULT_DIVISION_FACTOR;
        return calResult > DEFAULT_MIN_REINFORCE_ARMY_NBR ? calResult : DEFAULT_MIN_REINFORCE_ARMY_NBR;
    }

    public static void updatePiechart(PieChart pct_countryDomiChart) {
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        for (int i = 0; i < playersList.size(); i++) {
            Player curPlayer = playersList.get(i);
            String curPlayerName = curPlayer.getPlayerName();
            float curRatio = playerDomiViewObserver.getControlRatioList().get(i);
            dataList.add(new PieChart.Data(curPlayerName, curRatio));

            System.out.println("name:" + curPlayerName + ", ratio: " + curRatio);
        }
        pct_countryDomiChart.setData(dataList);
        pct_countryDomiChart.setLabelsVisible(false);

    }

    public static ArrayList<Country> getSortedCountryListByArmyNbr(Player player) {
        ArrayList<Country> ownedCountryList = getCountryList(player);
        ownedCountryList.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o2.getCountryArmyNumber() - o1.getCountryArmyNumber();
            }
        });
        return ownedCountryList;
    }
}


