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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static riskgame.Main.*;

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
    private VBox vbx_worldDomiView;
    @FXML
    private VBox vbx_cardExchangeView;
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
    @FXML
    private Label lbl_actionString;
    @FXML
    private Label lbl_playerName;
    @FXML
    private Label lbl_phaseViewName;


    /**
     * current player in this phase
     */
    private Player curPlayer;
    private int curPlayerIndex;
    private String curGamePhase;

    private HashMap<String, ArrayList<Card>> playersCards;

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
        reinforceViewInit();
    }

    /**
     * init UI controls and corresponding varaibles
     *
     * param playerIndex initialize UI controls and display information of current player
     */
    private void reinforceViewInit() {

        initPhaseView();
        initPlayerDominationView();
        initCardViewObserver();

        Color curPlayerColor = curPlayer.getPlayerColor();
        int ownedCountryNum = curPlayer.getOwnedCountryNameList().size();
        int curUndeployedArmy = getStandardReinforceArmyNum(ownedCountryNum);

        // lbl_playerInfo.setTextFill(curPlayerColor);
        lbl_countriesInfo.setTextFill(curPlayerColor);
        lbl_adjacentCountriesInfo.setTextFill(curPlayerColor);
        lbl_undeployedArmy.setText(Integer.toString(curUndeployedArmy));
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));

        System.out.println("country display index: " + curPlayer.getPlayerIndex());

        ListviewRenderer.renderCountryItems(lsv_ownedCountries);

        pct_countryDistributionChart.setData(getPieChartData(curPlayer));

        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(1);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_deployArmyCount.setText(Integer.toString(curUndeployedArmy));

        scb_armyNbrAdjustment.valueProperty().addListener((observable, oldValue, newValue) -> lbl_deployArmyCount.setText(Integer.toString(newValue.intValue())));
    }

    /**
     *Set contents to player domination the pane
     */
    private void initPlayerDominationView() {
        ArrayList<Label> labelList = new ArrayList<>();

        for(int playerIndex = 0; playerIndex < totalNumOfPlayers; playerIndex++){
            Color curPlayerColor = playersList.get(playerIndex).getPlayerColor();
            Label oneLabel = new Label();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player: ").append(playerIndex)
                    .append("\n").append("Control ratio: ")
                    .append(playerDomiViewObserver.getControlRatioList().get(playerIndex))
                    .append("\n").append("Controlled continents: ")
                    .append(playerDomiViewObserver.getControlledContinentNbrList().get(playerIndex))
                    .append("\n").append("Total army: ")
                    .append(playerDomiViewObserver.getTotalArmyNbrList().get(playerIndex))
                    .append("\n\n");
            oneLabel.setText(stringBuilder.toString());
            oneLabel.setTextFill(curPlayerColor);
            oneLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            labelList.add(oneLabel);
        }

        vbx_worldDomiView.getChildren().addAll(labelList);
    }

    /**
     * set contents to phase view labels
     *
     */
    private void initPhaseView() {
        curGamePhase = phaseViewObserver.getPhaseName();
        curPlayerIndex = phaseViewObserver.getPlayerIndex();
        curPlayer = Main.playersList.get(curPlayerIndex);
        String playerName = "Player_" + curPlayerIndex;
        lbl_phaseViewName.setText(curGamePhase);
        lbl_playerName.setText(playerName);
        lbl_playerName.setTextFill(curPlayer.getPlayerColor());
        lbl_actionString.setText(phaseViewObserver.getActionString());
    }


    private void setPhaseViewObservable() {
        String phaseName = "Attack Phase";
        int nextPlayerIndex = curRoundPlayerIndex + 1;
        String actionString = "Action:\nBegin Attack phase, please select one of your country and adjacent country to attack";

        phaseViewObservable.setAllParam(phaseName, nextPlayerIndex, actionString);
        phaseViewObservable.notifyObservers(phaseViewObservable);
    }

    private void initCardViewObserver(){
        playersCards = cardExchangeViewObserver.getPlayersCards();
        ArrayList<Label> labelList = new ArrayList<>();
        for (int playerIndex = 0; playerIndex < totalNumOfPlayers; playerIndex++){
            Color curPlayerColor = playersList.get(playerIndex).getPlayerColor();
            String playerCardsDisplayable = playersCards.get(String.valueOf(playerIndex)).toString();
            Label oneLabel = new Label();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player: ").append(playerIndex)
                    .append("\n").append(playerCardsDisplayable)
                    .append("\n\n");
            oneLabel.setText(stringBuilder.toString());
            oneLabel.setTextFill(curPlayerColor);
            oneLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            labelList.add(oneLabel);
        }
        vbx_worldDomiView.getChildren().addAll(labelList);
    }


    /**
     * onClick event for moving to next player if reinforcement phase is not finished or attack view from the first player
     *
     * @param actionEvent next player's turn or proceed to attackview if all players finish reinforcement
     * @throws IOException reinforcement.fxml or attakview.fxml are not found
     */
    @FXML
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
//        Main.curRoundPlayerIndex++;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        if (curPlayerIndex+1 < Main.totalNumOfPlayers) {
            notifyGameStageChanged(false);
            Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/ReinforceView.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane, 1200, 900);
            reinforceViewInit();
            curStage.setScene(reinforceScene);
            curStage.show();
        } else {
            notifyGameStageChanged(true);
//            Main.curRoundPlayerIndex = Main.curRoundPlayerIndex % Main.totalNumOfPlayers;
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

    /**
     * notify all phase view observer that game stage changed.
     * Changes can be next player's reinforcement or going to attack phase.
     * @param nextPhase true for going to attack phase otherwise, next player's turn
     */
    private void notifyGameStageChanged(boolean nextPhase){
        int nextPlayerIndex = (curPlayerIndex + 1) % Main.totalNumOfPlayers;
        if (nextPhase){
            Main.phaseViewObservable.setAllParam("Attack Phase", nextPlayerIndex, "NO ACT");
            Main.phaseViewObservable.notifyObservers("from reinforcement");
        }else {
            Main.phaseViewObservable.setAllParam("Reinforcement Phase", nextPlayerIndex, "NO ACT");
            Main.phaseViewObservable.notifyObservers("from reinforcement");
        }
        System.out.printf("player %s finished, player %s's turn\n", curPlayerIndex, nextPlayerIndex);
    }
}

