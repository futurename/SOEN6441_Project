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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.util.ArrayList;

import static riskgame.Main.*;
import static riskgame.controllers.StartViewController.firstRoundCounter;

/**
 * controller class for FortificationView.fxml
 *
 * @author WW
 * @since build1
 **/
public class FortificationViewController {

    /**
     * default minimum army number in a country
     */
    private static final int MIN_ARMY_NUMBER_IN_COUNTRY = 1;
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
    @FXML
    private Button btn_saveGame;
    @FXML
    private Label lbl_phaseViewName;
    @FXML
    private Label lbl_rechanble_countries;
    @FXML
    private Label lbl_countries;
    @FXML
    private Label lbl_playerName;
    @FXML
    private Label lbl_undeployArmyPrompt;
    @FXML
    private Label lbl_deployCountPrompt;
    @FXML
    private Label lbl_actionString;

    /**
     * fortification move counter
     */
    private int counter = 1;

    /**
     * current player in this phase
     */
    /**
     * index of current player
     */
    private int curPlayerIndex;

    /**
     * current game phase string
     */
    private String curGamePhase;

    /**
     * current player name for display
     */
    private String curPlayerName;

    /**
     * current action string for display
     */
    private String curActionString;

    /**
     * current player object
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
        initPhaseView();

        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));
        ListviewRenderer.renderCountryItems(lsv_ownedCountries);

        boolean isOneCountryCanFortificate = isExistFortificationCountry(lsv_ownedCountries.getItems());
        if (!isOneCountryCanFortificate) {
            btn_nextStep.setVisible(true);
            btn_confirmMoveArmy.setVisible(false);
            btn_skipFortification.setVisible(false);
        }
        curPlayer.executeFortification();
    }

    /**
     * validate whether exists a country which can fortificate to other countries.
     *
     * @param countryObservableList country object list
     * @return true for valid, false for none
     */
    private boolean isExistFortificationCountry(ObservableList<Country> countryObservableList) {
        boolean result = false;
        for (Country country : countryObservableList) {
            int armyNbr = country.getCountryArmyNumber();
            if (armyNbr > 1) {
                ObservableList<Country> reachableCountryList = InfoRetriver.getReachableCountryObservableList(curPlayerIndex,
                        country.getCountryName());
                if (!reachableCountryList.isEmpty()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * init phaseview observer pattern
     */
    private void initPhaseView() {
        initObserver();

        curPlayer = playersList.get(curPlayerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();

        lbl_phaseViewName.setText(curGamePhase);
        lbl_phaseViewName.setTextFill(curPlayerColor);
        lbl_playerName.setText(curPlayerName);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_actionString.setText(curActionString);
        lbl_actionString.setWrapText(true);

        lbl_countries.setTextFill(curPlayerColor);
        lbl_rechanble_countries.setTextFill(curPlayerColor);
    }

    /**
     * init phaseview observer value
     */
    private void initObserver() {
        curGamePhase = phaseViewObserver.getPhaseName();
        curPlayerIndex = phaseViewObserver.getPlayerIndex();
        curActionString = phaseViewObservable.getActionString();
        curPlayerName = "Player_" + curPlayerIndex;
    }

    /**
     * set ownedcountry and adjacentcountry ListViews uncontrollable.
     */
    private void setListViewsTransparent() {
        lsv_ownedCountries.setMouseTransparent(true);
        lsv_ownedCountries.setFocusTraversable(false);
        lsv_reachableCountry.setMouseTransparent(true);
        lsv_reachableCountry.setFocusTraversable(false);
    }

    /**
     * onClick event when a country item in the ListView is selected, display its adjacent country list in adjacent ListView
     *
     * @param mouseEvent a country item is selected
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        if (counter == 0) {
            btn_skipFortification.setVisible(false);
            btn_confirmMoveArmy.setVisible(false);
            btn_nextStep.setVisible(true);
            setListViewsTransparent();
        } else {
            int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
            String selectedCountryName = curPlayer.getOwnedCountryNameList().get(selectedCountryIndex);
            Country selectedCountry = lsv_ownedCountries.getSelectionModel().getSelectedItem();

            System.out.println("selected country name: " + selectedCountryName + ", army: " + selectedCountry.getCountryArmyNumber());

            if (selectedCountry.getCountryArmyNumber() <= MIN_ARMY_NUMBER_IN_COUNTRY) {
                btn_confirmMoveArmy.setVisible(false);
                alert.setContentText("No enough army for fortification!");
                alert.showAndWait();
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
                    btn_skipFortification.setVisible(true);
                    lsv_reachableCountry.setItems(reachableCountryList);
                    ListviewRenderer.renderCountryItems(lsv_reachableCountry);
                    updateDeploymentInfo(selectedCountry);
                    scb_armyNbrAdjustment.valueProperty()
                            .addListener((observable, oldValue, newValue) -> lbl_deployArmyNumber.setText(Integer.toString(newValue.intValue())));
                }
            }
        }
    }

    /**
     * get count of countries whose army number is greate than one
     *
     * @return true for two or monre countries, false for less than two countries.
     */
    private boolean getCountOfValidCountries() {
        ObservableList<Country> countryObservableList = lsv_ownedCountries.getItems();
        int count = 0;
        for (Country country : countryObservableList) {
            if (country.getCountryArmyNumber() > MIN_ARMY_NUMBER_IN_COUNTRY) {
                count++;
            }
        }
        return count > 1;
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
            GraphNode curGraphNode = graphSingleton.get(countryName);
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
     * onClick event for moving to attack phase view.
     *
     * @param actionEvent button is clicked
     * @throws IOException AttackView.fxml is not found
     */
    @FXML
    public void clickNextStep(ActionEvent actionEvent) throws IOException {

        System.out.println("\n\nfirst round counter: " + firstRoundCounter + "\n\n");

        if (firstRoundCounter > 0) {
            firstRoundCounter--;

            if (firstRoundCounter == 0) {
                curRoundPlayerIndex = -1;
            }
            int nextPlayerIndex = (curPlayerIndex + 1) % totalNumOfPlayers;
            notifyGameStageChanged("Attack Phase", nextPlayerIndex, "Attack Action");

            Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Pane attackPane = new FXMLLoader(getClass().getResource("../view/AttackView.fxml")).load();
            Scene attackScene = new Scene(attackPane, 1200, 900);
            curStage.setScene(attackScene);
            curStage.show();
        } else {
            curRoundPlayerIndex = InfoRetriver.getNextActivePlayer(curPlayerIndex);
            notifyGameStageChanged("Reinforce Phase", curRoundPlayerIndex, "Reinforcement Action");

            Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/ReinforceView.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane, 1200, 900);
            curStage.setScene(reinforceScene);
            curStage.show();
        }
    }

    /**
     * onClick event for confirming moving army from one country to another owned country
     *
     * @param actionEvent button is clicked
     */
    public void clickConfirmMoveArmy(ActionEvent actionEvent) {
        counter--;

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
            Country selectedCountry = graphSingleton.get(selectedCountryName).getCountry();

            String selectedTargetCountryName = adjacentCountryObservableList.get(selectedReachableCountryIndex).getCountryName();
            Country selectedTargetCountry = graphSingleton.get(selectedTargetCountryName).getCountry();

            System.out.println("before move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: "
                    + selectedTargetCountryName + ": " + selectedTargetCountry.getCountryArmyNumber());

            int deployArmyNumber = Integer.parseInt(lbl_deployArmyNumber.getText());

            System.out.println("deploy number: " + deployArmyNumber);

            curPlayer.executeFortification(selectedCountry, selectedTargetCountry, deployArmyNumber);
//            selectedCountry.reduceFromCountryArmyNumber(deployArmyNumber);
//            selectedTargetCountry.addToCountryArmyNumber(deployArmyNumber);

            System.out.println("after move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: "
                    + selectedTargetCountryName + ": " + selectedTargetCountry.getCountryArmyNumber());

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

    /**
     * phaseview observable notify its observers.
     *
     * @param phase           phase name string
     * @param nextPlayerIndex next valid player index
     * @param actionType      action string
     */
    private void notifyGameStageChanged(String phase, int nextPlayerIndex, String actionType) {
        phaseViewObservable.setAllParam(phase, nextPlayerIndex, actionType);
        phaseViewObservable.notifyObservers("from fortification view");
        System.out.printf("player %s finished fortification, player %s's turn\n", curPlayerIndex, nextPlayerIndex);
    }

    public void clickSaveGame(ActionEvent actionEvent) {
        String titleString = "Select Location to Save Game:";
        InfoRetriver.showFileChooser(titleString);
    }

    public void clickLoadGame(ActionEvent actionEvent) {
        String titleString = "Select Saved Map File:";
        InfoRetriver.showFileChooser(titleString);
    }
}
