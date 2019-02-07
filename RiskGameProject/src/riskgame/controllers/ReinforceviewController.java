package riskgame.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.classes.Country;
import riskgame.classes.Player;
import riskgame.model.InfoRetriver;
import riskgame.model.ListviewRenderer;
import riskgame.model.ReinforcePhase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * controller class for reinforcement phase
 *
 * @author: WW
 */
public class ReinforceviewController implements Initializable {
    @FXML
    private Button btn_nextStep;
    @FXML
    private ListView lsv_ownedCountries;
    @FXML
    private ListView lsv_adjacentCountries;
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

    /**     *
     * @param location default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reinforceViewInit(Main.curRoundPlayerIndex);

    }


    /**
     * @param playerIndex initialize UI controls and display information of current player
     */
    private void reinforceViewInit(int playerIndex) {
        lbl_playerInfo.setText("Player : " +playerIndex);

        Player curPlayer = Main.playersList.get(playerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();
        int ownedCountryNum = curPlayer.getOwnedCountryNameList().size();
        int curUndeployedArmy = ReinforcePhase.getStandardReinforceArmyNum(ownedCountryNum);

        lbl_playerInfo.setTextFill(curPlayerColor);
        lbl_countriesInfo.setTextFill(curPlayerColor);
        lbl_adjacentCountriesInfo.setTextFill(curPlayerColor);
        lbl_undeployedArmy.setText(Integer.toString(curUndeployedArmy));
        lsv_ownedCountries.setItems(InfoRetriver.getPlayerCountryObservablelist(curPlayer));

        System.out.println("country display index: " + curPlayer.getPlayerIndex());

        ListviewRenderer.getRenderedCountryItems(playerIndex, lsv_ownedCountries);

        pct_countryDistributionChart.setData(ReinforcePhase.getPieChartData(curPlayer));

        displayStackedBarChart(sbc_occupationRatio);

        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(1);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_deployArmyCount.setText(Integer.toString(curUndeployedArmy));

        scb_armyNbrAdjustment.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                lbl_deployArmyCount.setText(Integer.toString(newValue.intValue()));
            }
        });
    }


    /**
     * @param sbc_occupationRatio display a stack bar chart that shows quantity of conquered country by the current player and<br/>
     *        total amount of country in each continent
     */
    private void displayStackedBarChart(StackedBarChart sbc_occupationRatio) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        ArrayList<String> continentNameList = ReinforcePhase.getContinentNameList();

        xAxis.setCategories(FXCollections.observableArrayList(continentNameList));


    }

    /**
     * @param actionEvent next player's turn or proceed to attackview if all players finish reinforcement
     * @throws IOException reinforcement.fxml or attakview.fxml not found     *
     */
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        Main.curRoundPlayerIndex++;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        if (Main.curRoundPlayerIndex < Main.totalNumOfPlayers) {
            Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/reinforceview.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane, 1200, 900);
            reinforceViewInit(Main.curRoundPlayerIndex);
            curStage.setScene(reinforceScene);
            curStage.show();
        } else {
            Main.curRoundPlayerIndex = Main.curRoundPlayerIndex % Main.totalNumOfPlayers;
            Pane attackPane = new FXMLLoader(getClass().getResource("../views/attackview.fxml")).load();
            Scene attackScene = new Scene(attackPane, 1200, 900);

            curStage.setScene(attackScene);
            curStage.show();
        }
    }


    /**
     * @param mouseEvent clicking one country name in the listview will display its adjacent countries
     */
    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        ObservableList datalist = InfoRetriver.getAdjacentCountryObservablelist(Main.curRoundPlayerIndex, countryIndex);

        lsv_adjacentCountries.setItems(datalist);
        ListviewRenderer.getRenderedCountryItems(Main.curRoundPlayerIndex, lsv_adjacentCountries);

    }


    /**
     * @param actionEvent deploy a selected number of army to the selected country
     */
    public void clickConfirmDeployment(ActionEvent actionEvent) {
        int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int undeloyedArmyCount = Integer.parseInt(lbl_undeployedArmy.getText());
        Player curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);

        if (selectedCountryIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please select a country for army deployment");
            alert.showAndWait();
        } else {
            int deployArmyCount = Integer.parseInt(lbl_deployArmyCount.getText());

            ArrayList<String> countryList = Main.playersList.get(Main.curRoundPlayerIndex).getOwnedCountryNameList();
            String selectedCountryName = countryList.get(selectedCountryIndex);
            Country curCountry = Main.worldCountriesMap.get(selectedCountryName);
            curCountry.addToCountryArmyNumber(deployArmyCount);

            int remainUndeployedArmyCount = undeloyedArmyCount - deployArmyCount;
            lbl_undeployedArmy.setText(Integer.toString(remainUndeployedArmyCount));

            updateCountryListview(curPlayer);

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
     * @param player update country list view after a deployment
     */
    private void updateCountryListview(Player player) {
        lsv_ownedCountries.setItems(InfoRetriver.getPlayerCountryObservablelist(player));
        ListviewRenderer.getRenderedCountryItems(Main.curRoundPlayerIndex, lsv_ownedCountries);

    }
}
