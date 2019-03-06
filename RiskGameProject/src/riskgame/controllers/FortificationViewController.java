package riskgame.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Observe.CountryChangedObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * controller class for FortificationView.fxml
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
    @FXML
    private Button btn_confirmMoveArmy;
    @FXML
    private Button btn_nextStep;
    @FXML
    private Button btn_skipFortification;

    /**
     * default minimum army number in a country
     */
    private static final int MIN_ARMY_NUMBER_IN_COUNTRY = 0;

    /**
     * current player in this phase
     */
    private Player curPlayer;

    /**
     * warning alert used for notification
     */
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    /**
     * init method for fortification phase view
     */
    public void initialize() {
        String playerInfo = "Player: " + Main.curRoundPlayerIndex;
        lbl_playerInfo.setText(playerInfo);
        curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));
        ListviewRenderer.renderCountryItems(lsv_ownedCountries);
        setCountryObservers();
        if (isAllReachableCountryEmpty(curPlayer)) {
            btn_confirmMoveArmy.setVisible(false);
            btn_skipFortification.setVisible(false);
            btn_nextStep.setVisible(true);
        }
    }

    /**
     * onClick event when a country item in the ListView is selected, display its adjacent country list in adjacent ListView
     *
     * @param mouseEvent a country item is selected
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        String selectedCountryName = curPlayer.getOwnedCountryNameList().get(selectedCountryIndex);
        Country selectedCountry = Main.graphSingleton.get(selectedCountryName).getCountry();

        System.out.println("selected country name: " + selectedCountryName + ", army: " + selectedCountry.getCountryArmyNumber());

        if (selectedCountry.getCountryArmyNumber() <= MIN_ARMY_NUMBER_IN_COUNTRY) {
            alert.setContentText("No enough army for fortification!");
            alert.showAndWait();
            btn_skipFortification.setVisible(false);
            btn_skipFortification.setVisible(false);
        } else {
            ObservableList<Country> reachableCountryList = InfoRetriver.getReachableCountryObservableList(curPlayer.getPlayerIndex(),
                    selectedCountryName);
            if (reachableCountryList.isEmpty()) {
                btn_confirmMoveArmy.setVisible(false);
                lsv_reachableCountry.setItems(null);
                lbl_deployArmyNumber.setText("0");
                lbl_maxArmyNumber.setText("0");
            } else {
                btn_nextStep.setVisible(false);
                btn_confirmMoveArmy.setVisible(true);
                lsv_reachableCountry.setItems(reachableCountryList);
                ListviewRenderer.renderCountryItems(lsv_reachableCountry);
                updateDeploymentInfo(selectedCountry);
                scb_armyNbrAdjustment.valueProperty().addListener((observable, oldValue, newValue) -> lbl_deployArmyNumber.setText(Integer.toString(newValue.intValue())));
            }
        }
    }

    /**
     * check whether reachable country lists from all owned countries are empty
     *
     * @param curPlayer current player
     * @return true for all list empty, false for existing reachable country list
     */
    private boolean isAllReachableCountryEmpty(Player curPlayer) {
        boolean result = true;
        ArrayList<String> ownedCountryList = curPlayer.getOwnedCountryNameList();
        int playerIndex = curPlayer.getPlayerIndex();
        for (String countryName : ownedCountryList) {
            GraphNode curGraphNode = Main.graphSingleton.get(countryName);
            Country curCountry = curGraphNode.getCountry();
            ArrayList<Country> tempCountryList = new ArrayList<>();
            curGraphNode.getReachableCountryListBFS(playerIndex, curCountry, tempCountryList);
            if (!tempCountryList.isEmpty()) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * set observers of country objects.
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
     * onClick event for moving to attack phase view.
     *
     * @param actionEvent button is clicked
     * @throws IOException AttackView.fxml is not found
     */
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

    /**
     * onClick event for confirming moving army from one country to another owned country
     *
     * @param actionEvent button is clicked
     */
    @FXML
    public void clickConfirmMoveArmy(ActionEvent actionEvent) {
        ObservableList<Country> countryObservableList = lsv_ownedCountries.getItems();
        ObservableList<Country> adjacentCountryObservableList = lsv_reachableCountry.getItems();

        int selectedOwnedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int selectedReachableCountryIndex = lsv_reachableCountry.getSelectionModel().getSelectedIndex();

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
            String selectedCountryName = countryObservableList.get(selectedOwnedCountryIndex).getCountryName();
            Country selectedCountry = Main.graphSingleton.get(selectedCountryName).getCountry();

            String selectedTargetCountryName = adjacentCountryObservableList.get(selectedReachableCountryIndex).getCountryName();
            Country selecctedTargetCountry = Main.graphSingleton.get(selectedTargetCountryName).getCountry();

            System.out.println("before move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: " + selectedTargetCountryName + ": " + selecctedTargetCountry.getCountryArmyNumber());

            int deployArmyNumber = Integer.parseInt(lbl_deployArmyNumber.getText());

            System.out.println("deploy number: " + deployArmyNumber);

            selectedCountry.reduceFromCountryArmyNumber(deployArmyNumber);
            selecctedTargetCountry.addToCountryArmyNumber(deployArmyNumber);

            System.out.println("after move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: " + selectedTargetCountryName + ": " + selecctedTargetCountry.getCountryArmyNumber());

            lsv_ownedCountries.refresh();
            lsv_reachableCountry.refresh();
            updateDeploymentInfo(selectedCountry);
            setUIControllers();
        }
    }

    /**
     * set fortification realted UI controllers invisible and visibalize Next button
     */
    private void setUIControllers() {
        btn_confirmMoveArmy.setVisible(false);
        btn_skipFortification.setVisible(false);
        btn_nextStep.setVisible(true);
        scb_armyNbrAdjustment.setVisible(false);

    }

    /**
     * calculate modification of army numbers and update undeployment data
     *
     * @param selectedCountry selected country object
     */
    private void updateDeploymentInfo(Country selectedCountry) {
        int curUndeployedArmy = selectedCountry.getCountryArmyNumber() - MIN_ARMY_NUMBER_IN_COUNTRY;
        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(MIN_ARMY_NUMBER_IN_COUNTRY);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_maxArmyNumber.setText(Integer.toString(curUndeployedArmy));
        int minNumber = curUndeployedArmy >= MIN_ARMY_NUMBER_IN_COUNTRY ? curUndeployedArmy : 0;
        lbl_deployArmyNumber.setText(String.valueOf(minNumber));
    }

    /**
     * onClick event for skipping fortification phase this round
     *
     * @param actionEvent skip button is pressed
     */
    public void clickSkipFortification(ActionEvent actionEvent) {
        btn_confirmMoveArmy.setVisible(false);
        btn_skipFortification.setVisible(false);
        btn_nextStep.setVisible(true);
    }
}
