package riskgame.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Observe.CountryChangedObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * controller class for ReinforceView.fxml
 **/

public class ReinforceViewController implements Initializable {
    @FXML
    private Button btn_nextStep;
    @FXML
    private ListView<Country> lsv_ownedCountries;
    @FXML
    private ListView<Country> lsv_adjacentCountries;
    @FXML
    private Label lbl_undeployedArmy;
    @FXML
    private PieChart pct_countryDistributionChart;
    @FXML
    private StackedBarChart sbc_occupationRatio;
    @FXML
    private Label lbl_playerInfo;
    @FXML
    private ScrollBar scb_armyNbrAdjustment;
    @FXML
    private Label lbl_deployArmyCount;
    @FXML
    private Button btn_confirmDeployment;
    @FXML
    private Label lbl_undeployArmyPrompt;
    @FXML
    private Label lbl_deployCountPrompt;
    @FXML
    private Label lbl_adjacentCountriesInfo;
    @FXML
    private Label lbl_countriesInfo;

    /**
     * current player in this phase
     */
    private Player curPlayer;

    /**
     * default factor for calculting standard number of army used for reinforce phase
     */
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    /**
     * minimun army number that is assigned to each player
     */
    private static final int DEFAULT_MIN_REINFORCE_ARMY_NBR = 3;

    /**
     * init method for reinforce phase view
     *
     * @param location  default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reinforceViewInit(Main.curRoundPlayerIndex);
    }

    /**
     * init UI controls and corresponding varaibles
     *
     * @param playerIndex initialize UI controls and display information of current player
     */
    private void reinforceViewInit(int playerIndex) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> cur player: " + playerIndex);

        lbl_playerInfo.setText("Player : " + playerIndex);

        curPlayer = Main.playersList.get(playerIndex);

        setCountryObservers();

        Color curPlayerColor = curPlayer.getPlayerColor();
        int ownedCountryNum = curPlayer.getOwnedCountryNameList().size();
        int curUndeployedArmy = getStandardReinforceArmyNum(ownedCountryNum);

        lbl_playerInfo.setTextFill(curPlayerColor);
        lbl_countriesInfo.setTextFill(curPlayerColor);
        lbl_adjacentCountriesInfo.setTextFill(curPlayerColor);
        lbl_undeployedArmy.setText(Integer.toString(curUndeployedArmy));
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));

        System.out.println("country display index: " + curPlayer.getPlayerIndex());

        ListviewRenderer.renderCountryItems(lsv_ownedCountries);

        pct_countryDistributionChart.setData(getPieChartData(curPlayer));

        displayStackedBarChart(sbc_occupationRatio);

        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(1);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_deployArmyCount.setText(Integer.toString(curUndeployedArmy));

        scb_armyNbrAdjustment.valueProperty().addListener((observable, oldValue, newValue) -> lbl_deployArmyCount.setText(Integer.toString(newValue.intValue())));
    }

    /**
     * set observer for a country
     */
    private void setCountryObservers() {
        ArrayList<String> ownedCountryNameList = curPlayer.getOwnedCountryNameList();
        CountryChangedObserver observer = curPlayer.getCountryChangedObserver();
        for (String name : ownedCountryNameList) {
            Main.graphSingleton.get(name).getCountry().getCountryObservable().deleteObservers();
            Main.graphSingleton.get(name).getCountry().getCountryObservable().addObserver(observer);
        }
    }

    /**
     * unfinished
     *
     * @param sbc_occupationRatio display a stack bar chart that shows quantity of conquered country by the current player and
     *                            total amount of country in each continent
     */
    private void displayStackedBarChart(StackedBarChart sbc_occupationRatio) {

    }

    /**
     * onClick event for moving to next player if reinforcement phase is not finished or attack view from the first player
     *
     * @param actionEvent next player's turn or proceed to attackview if all players finish reinforcement
     * @throws IOException reinforcement.fxml or attakview.fxml are not found
     */
    @FXML
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        Main.curRoundPlayerIndex++;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        if (Main.curRoundPlayerIndex < Main.totalNumOfPlayers) {
            Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/ReinforceView.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane, 1200, 900);
            reinforceViewInit(Main.curRoundPlayerIndex);
            curStage.setScene(reinforceScene);
            curStage.show();
        } else {
            Main.curRoundPlayerIndex = Main.curRoundPlayerIndex % Main.totalNumOfPlayers;
            Pane attackPane = new FXMLLoader(getClass().getResource("../view/AttackView.fxml")).load();
            Scene attackScene = new Scene(attackPane, 1200, 900);

            curStage.setScene(attackScene);
            curStage.show();
        }
    }

    /**
     * onClick event when a country item in the ListView is selected and display its adjacent country data in adjacent ListView
     *
     * @param mouseEvent clicking one country name in the listview will display its adjacent countries
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        ObservableList datalist = InfoRetriver.getAdjacentCountryObservablelist(Main.curRoundPlayerIndex, countryIndex);

        lsv_adjacentCountries.setItems(datalist);
        ListviewRenderer.renderCountryItems(lsv_adjacentCountries);
    }

    /**
     * onClick event for confirming army deployment
     *
     * @param actionEvent deploy a selected number of army to the selected country
     */
    @FXML
    public void clickConfirmDeployment(ActionEvent actionEvent) {
        int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int undeloyedArmyCount = Integer.parseInt(lbl_undeployedArmy.getText());

        if (selectedCountryIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please select a country for army deployment");
            alert.showAndWait();
        } else {
            int deployArmyCount = Integer.parseInt(lbl_deployArmyCount.getText());

            ArrayList<String> countryList = Main.playersList.get(Main.curRoundPlayerIndex).getOwnedCountryNameList();
            String selectedCountryName = countryList.get(selectedCountryIndex);
            Country curCountry = Main.graphSingleton.get(selectedCountryName).getCountry();
            curCountry.addToCountryArmyNumber(deployArmyCount);

            int remainUndeployedArmyCount = undeloyedArmyCount - deployArmyCount;
            lbl_undeployedArmy.setText(Integer.toString(remainUndeployedArmyCount));

            lsv_ownedCountries.refresh();

            if (remainUndeployedArmyCount == 0) {
                btn_confirmDeployment.setVisible(false);
                lbl_undeployedArmy.setVisible(false);
                lbl_deployArmyCount.setVisible(false);
                lbl_deployCountPrompt.setVisible(false);
                scb_armyNbrAdjustment.setVisible(false);
                lbl_undeployArmyPrompt.setVisible(false);

                btn_nextStep.setVisible(true);
            } else {
                scb_armyNbrAdjustment.setMax(remainUndeployedArmyCount);
                scb_armyNbrAdjustment.adjustValue(remainUndeployedArmyCount);
                lbl_deployArmyCount.setText(Integer.toString(remainUndeployedArmyCount));
            }
        }
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

    /**
     * acquire ObservableList for displaying in pie chart. The pie chart presents number of countries in different continents a play has.
     *
     * @param player a player instance
     * @return distribution of (continent, number of country) this player owns
     */
    public static ObservableList<PieChart.Data> getPieChartData(Player player) {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();

        ArrayList<String> countryList = player.getOwnedCountryNameList();

        HashMap<String, Integer> countryDistributionMap = InfoRetriver.getCountryDistributionMap(countryList);

        for (Map.Entry<String, Integer> entry : countryDistributionMap.entrySet()) {
            String oneCountryName = entry.getKey();
            int count = entry.getValue();
            PieChart.Data onePieChartData = new PieChart.Data(oneCountryName, count);
            result.add(onePieChartData);

            System.out.println("country name: " + oneCountryName + ", curCount: " + count);
        }
        return result;
    }
}

