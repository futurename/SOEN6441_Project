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
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.classes.Country;
import riskgame.classes.Player;
import riskgame.model.PlayerInfoRetriver;
import riskgame.model.ReinforcePhase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_playerInfo.setText("Player : " + Main.curRoundPlayerIndex);

        Player curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        int ownedCountryNum = curPlayer.getOwnedCountryNameList().size();
        int curUndeployedArmy = ReinforcePhase.getStandardReinforceArmyNum(ownedCountryNum);

        lbl_undeployedArmy.setText(Integer.toString(curUndeployedArmy));
        lsv_ownedCountries.setItems(PlayerInfoRetriver.getPlayerCountryObservablelist(curPlayer));

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

    private void displayStackedBarChart(StackedBarChart sbc_occupationRatio) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        ArrayList<String> continentNameList = ReinforcePhase.getContinentNameList();

        xAxis.setCategories(FXCollections.observableArrayList(continentNameList));


    }

    public void clickNextToAttackPhase(ActionEvent actionEvent) throws IOException {
            Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            Pane attackPane = new FXMLLoader(getClass().getResource("../views/attackview.fxml")).load();
            Scene attackScene = new Scene(attackPane, 1200, 900);

            curStage.setScene(attackScene);
            curStage.show();
    }


    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        ObservableList datalist = PlayerInfoRetriver.getAdjacentCountryObservablelist(countryIndex);
        lsv_adjacentCountries.setItems(datalist);
    }


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

            if (undeloyedArmyCount < deployArmyCount) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("No enough army for deployment!");
                alert.showAndWait();
            } else {

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
    }

    private void updateCountryListview(Player player) {
        lsv_ownedCountries.setItems(PlayerInfoRetriver.getPlayerCountryObservablelist(player));
    }
}
