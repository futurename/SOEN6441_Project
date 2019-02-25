package riskgame.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Observer.CountryChangedObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @program: RiskGameProject
 * @description:
 * @author: WW
 * @date: 2019-02-20
 **/

public class FortificationViewController {

    @FXML
    private Label lbl_playerInfo;
    @FXML
    private Label lbl_maxArmyNumber;
    @FXML
    private Label lbl_deployArmyNumber;
    @FXML
    private ScrollBar scb_armyNbrAdjustment;
    @FXML
    private ListView<Country> lsv_ownedCountries;
    @FXML
    private ListView<Country> lsv_reachableCountry;

    private Player curPlayer;
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    public void initialize() {
        String playerInfo = "Player: " + Integer.toString(Main.curRoundPlayerIndex);
        lbl_playerInfo.setText(playerInfo);
        curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));

        ListviewRenderer.renderCountryItems(lsv_ownedCountries);

        setCountryObservers();
    }

    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        String selectedCountryName = curPlayer.getOwnedCountryNameList().get(selectedCountryIndex);
        Country selectedCountry = Main.graphSingleton.get(selectedCountryName).getCountry();

        System.out.println("selected country name: " + selectedCountryName + ", army: " + selectedCountry.getCountryArmyNumber());

        if (selectedCountry.getCountryArmyNumber() < 2) {
            alert.setContentText("No enough army for fortification!");
            alert.showAndWait();
        } else {

            ObservableList<Country> reachableCountryList = InfoRetriver.getReachableCountryObservableList(curPlayer.getPlayerIndex(),
                    selectedCountryName);
            lsv_reachableCountry.setItems(reachableCountryList);
            ListviewRenderer.renderCountryItems(lsv_reachableCountry);

            updateDeploymentInfo(selectedCountry);

            scb_armyNbrAdjustment.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    lbl_deployArmyNumber.setText(Integer.toString(newValue.intValue()));
                }
            });
        }

    }

    private void setCountryObservers() {
        ArrayList<String> ownedCountryNameList = curPlayer.getOwnedCountryNameList();
        CountryChangedObserver observer = curPlayer.getCountryChangedObserver();
        for (String name : ownedCountryNameList) {
            Main.graphSingleton.get(name).getCountry().getCountryObservable().deleteObservers();
            Main.graphSingleton.get(name).getCountry().getCountryObservable().addObserver(observer);
        }
    }

    @FXML
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        Main.curRoundPlayerIndex = (Main.curRoundPlayerIndex + 1) % Main.totalNumOfPlayers;

        System.out.println("one round finished: " + Main.curRoundPlayerIndex);

        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/AttackView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane, 1200, 900);

        curStage.setScene(reinforceScene);
        curStage.show();

    }

    @FXML
    public void clickConfirmMoveArmy(ActionEvent actionEvent) {
        int selectedOwnedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int selectedReachableCountryIndex = lsv_reachableCountry.getSelectionModel().getSelectedIndex();
        ArrayList<String> ownedCountryNameList = curPlayer.getOwnedCountryNameList();

        if (selectedOwnedCountryIndex == -1 && selectedReachableCountryIndex == -1) {
            alert.setContentText("Both source and target countries are selected!");
            alert.showAndWait();
        } else if (selectedOwnedCountryIndex == -1) {
            alert.setContentText("Please select a country to be fortified!");
            alert.showAndWait();
        } else if (selectedReachableCountryIndex == -1) {
            alert.setContentText("Please select a target country for fortification!");
            alert.showAndWait();
        } else {
            String selectedCountryName = ownedCountryNameList.get(selectedOwnedCountryIndex);
            Country selectedCountry = Main.graphSingleton.get(selectedCountryName).getCountry();

            String selectedReachableItemString = lsv_reachableCountry.getSelectionModel().getSelectedItem().toString();
            String[] splitString = selectedReachableItemString.split(":");

            System.out.println("reachable index: " + selectedReachableCountryIndex + ", splitstring: " + selectedReachableItemString);

            String selectedTargetCountryName = splitString[0].trim();
            Country selecctedTargetCountry = Main.graphSingleton.get(selectedTargetCountryName).getCountry();

            System.out.println("before move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: " + selectedTargetCountryName + ": " + selecctedTargetCountry.getCountryArmyNumber() + ", display: " + splitString[1]);

            int deployArmyNumber = Integer.parseInt(lbl_deployArmyNumber.getText());

            System.out.println("deploy number: " + deployArmyNumber);

            selectedCountry.reduceFromCountryArmyNumber(deployArmyNumber);
            selecctedTargetCountry.addToCountryArmyNumber(deployArmyNumber);

            System.out.println("after move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: " + selectedTargetCountryName + ": " + selecctedTargetCountry.getCountryArmyNumber());

            lsv_ownedCountries.refresh();
            lsv_reachableCountry.refresh();
        }
    }

    private void updateDeploymentInfo(Country selectedCountry) {
        int curUndeployedArmy = selectedCountry.getCountryArmyNumber() - 1;
        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(1);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_maxArmyNumber.setText(Integer.toString(curUndeployedArmy));
        int minNumber = curUndeployedArmy >= 1 ? curUndeployedArmy : 0;
        lbl_deployArmyNumber.setText(String.valueOf(minNumber));
    }
}
