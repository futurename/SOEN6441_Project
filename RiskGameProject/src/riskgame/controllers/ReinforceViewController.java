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
import java.util.*;

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
    private VBox vbx_worldDomiView;
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
    @FXML
    private ListView lsv_cardsListView;
    @FXML
    private Button btn_skipCardsExchange;
    @FXML
    private Button btn_confirmExchangeCards;

    /**
     * current player in this phase
     */
    private int curPlayerIndex;

    private String curGamePhase;
    private String curActionString;
    private String curPlayerName;

    private Player curPlayer;

    private HashMap<String, ArrayList<Card>> playersCards;

    private int curUndeployedArmy = 0;

    private static int reinforceInitCounter = totalNumOfPlayers;


    private Alert alert = new Alert(Alert.AlertType.WARNING);

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
     * <p>
     * param playerIndex initialize UI controls and display information of current player
     */
    private void reinforceViewInit() {
        initPhaseView();
        initPlayerDominationView();
        initCurPlayerCardListView();

        Color curPlayerColor = curPlayer.getPlayerColor();
        int ownedCountryNum = curPlayer.getOwnedCountryNameList().size();
        curUndeployedArmy += getStandardReinforceArmyNum(ownedCountryNum);

        lbl_countriesInfo.setTextFill(curPlayerColor);
        lbl_adjacentCountriesInfo.setTextFill(curPlayerColor);
        lbl_undeployedArmy.setText(Integer.toString(curUndeployedArmy));
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));

        System.out.println("country display index: " + curPlayer.getPlayerIndex());

        ListviewRenderer.renderCountryItems(lsv_ownedCountries);

        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(1);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_deployArmyCount.setText(Integer.toString(curUndeployedArmy));

        scb_armyNbrAdjustment.valueProperty().addListener((observable, oldValue, newValue) -> lbl_deployArmyCount.setText(Integer.toString(newValue.intValue())));

        btn_confirmDeployment.setVisible(false);
    }

    private void initCurPlayerCardListView() {
        playersCards = cardExchangeViewObserver.getPlayersCards();
        ArrayList<Card> cardsList = playersCards.get(String.valueOf(curPlayerIndex));
        Collections.sort(cardsList, Collections.reverseOrder());
        ObservableList<Card> cardObservableList = FXCollections.observableList(cardsList);
        lsv_cardsListView.setItems(cardObservableList);
        ListviewRenderer.renderCardsListView(lsv_cardsListView);
        lsv_cardsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Set contents to player domination the pane
     */
    private void initPlayerDominationView() {
        ArrayList<Label> labelList = new ArrayList<>();

        for (int playerIndex = 0; playerIndex < totalNumOfPlayers; playerIndex++) {
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
     */
    private void initPhaseView() {
        initObserver();
        curPlayer = playersList.get(curPlayerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();

        lbl_phaseViewName.setText(curGamePhase);
        lbl_playerName.setText(curPlayerName);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_actionString.setText(curActionString);
        lbl_actionString.setWrapText(true);
    }

    private void initObserver() {
        curGamePhase = phaseViewObserver.getPhaseName();
        curPlayerIndex = phaseViewObserver.getPlayerIndex();
        curPlayerName = "Player_" + curPlayerIndex;
        curActionString = phaseViewObserver.getActionString();
    }


    private void initCardViewObserver() {
        playersCards = cardExchangeViewObserver.getPlayersCards();
        ArrayList<Label> labelList = new ArrayList<>();
        for (int playerIndex = 0; playerIndex < totalNumOfPlayers; playerIndex++) {
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
    }


    /**
     * onClick event for moving to next player if reinforcement phase is not finished or attack view from the first player
     *
     * @param actionEvent next player's turn or proceed to attackview if all players finish reinforcement
     * @throws IOException reinforcement.fxml or attakview.fxml are not found
     */
    @FXML
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
//        curRoundPlayerIndex = (curRoundPlayerIndex + 1) % totalNumOfPlayers;
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        System.out.println("\n???????????????????????????" + reinforceInitCounter);

        if (reinforceInitCounter > 1) {
            checkNextViewNeedChange(false);

            reinforceInitCounter--;

            Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/ReinforceView.fxml")).load();
            Scene reinforceScene = new Scene(reinforcePane, 1200, 900);

            curStage.setScene(reinforceScene);
            curStage.show();

        } else {
            checkNextViewNeedChange(true);

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
        ObservableList datalist = InfoRetriver.getAdjacentCountryObservablelist(curPlayerIndex, countryIndex);

        lsv_adjacentCountries.setItems(datalist);
        ListviewRenderer.renderCountryItems(lsv_adjacentCountries);

        System.out.println(">>>select owned country: " + lsv_ownedCountries.getSelectionModel().getSelectedItem());
    }

    /**
     * onClick event for confirming army deployment
     *
     * @param actionEvent deploy a selected number of army to the selected country
     */
    @FXML
    public void clickConfirmDeployment(ActionEvent actionEvent) {
        Country selectedCountry = lsv_ownedCountries.getSelectionModel().getSelectedItem();
        int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int undeloyedArmyCount = Integer.parseInt(lbl_undeployedArmy.getText());

        if (selectedCountryIndex == -1) {
            alert.setHeaderText(null);
            alert.setContentText("Please select a country for army deployment");
            alert.showAndWait();
        } else {
            int deployArmyCount = Integer.parseInt(lbl_deployArmyCount.getText());
            selectedCountry.addToCountryArmyNumber(deployArmyCount);

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


            System.out.println("\nfinish deployment to country " + selectedCountry.getCountryName() + ": " + selectedCountry.getCountryArmyNumber());
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
     *
     * @param isAttackPhase true for going to attack phase otherwise, next player's turn
     */
    private void checkNextViewNeedChange(boolean isAttackPhase) {
        if (isAttackPhase) {
            setAttackPhaseViewObservable();

            Main.phaseViewObservable.notifyObservers("reinforce to attack");
        } else {
            int nextPlayerIndex = (curPlayerIndex + 1) % Main.totalNumOfPlayers;

            Main.phaseViewObservable.setAllParam("Reinforcement Phase", nextPlayerIndex, curActionString);
            Main.phaseViewObservable.notifyObservers("continue reinforce");
        }
    }

    /**
     * set phase view observable parameters for next step
     */
    private void setAttackPhaseViewObservable() {
        String nextPhaseName = "Attack Phase";
        int nextPlayerIndex = curPlayerIndex;
        String nextActionString = "Action:\n" +
                "\n1. Select one of your country" +
                "\n2. Select an adjacent empty country" +
                "\n3_1. Click \"All-Out\" button to use all attacking army" +
                "\n3_2. Select certain number of army for attacker" +
                "\n4. Select certain number of army for defender" +
                "\n5. Click \"Accept\" button for confirming army number selection" +
                "\n6. Click \"Attack\" button to use selected army number";

        phaseViewObservable.setAllParam(nextPhaseName, nextPlayerIndex, nextActionString);
        phaseViewObservable.notifyObservers(phaseViewObservable);
    }

    /**
     * click button for confirming exchange cards
     *
     * @param actionEvent click this button
     */
    public void clickExchangeCards(ActionEvent actionEvent) {
        ObservableList<Card> selectedCardList = lsv_cardsListView.getSelectionModel().getSelectedItems();

        System.out.println("seleted cards: " + selectedCardList);

        if (selectedCardList.isEmpty()) {
            alert.setContentText("No card selected!");
            alert.showAndWait();
        } else if (validateCardsCombination(selectedCardList)) {
            int exchangedArmyNbr = getExchangedArmyNbr(selectedCardList);
            curUndeployedArmy += exchangedArmyNbr;

            removeCardsFromList(selectedCardList);
            lsv_cardsListView.refresh();

            btn_confirmDeployment.setVisible(true);
            btn_skipCardsExchange.setVisible(false);
            btn_confirmExchangeCards.setVisible(false);


        } else {
            alert.setContentText("Wrong cards combination, please try again!");
            alert.showAndWait();
        }
    }

    /**
     * Selected cards will be removed from cards list if the exchange succeeds.
     *
     * @param selectedCardList exchanged card list
     */
    private void removeCardsFromList(ObservableList<Card> selectedCardList) {
        for (Card card : selectedCardList) {
            curPlayer.getCardsList().remove(card);
        }
    }

    /**
     * if the selected cards list satifies the game rule, in this method it will be calculated to army number the player can exchange.
     *
     * @param seletectedCardList selected card list
     * @return exchanged army number
     */
    private int getExchangedArmyNbr(ObservableList<Card> seletectedCardList) {

        return 0;
    }

    /**
     * selected cards list will be validated with game rules
     *
     * @param seletectedCardList selected cards arraylist
     * @return true for correct combination, false for incorrect combination
     */
    private boolean validateCardsCombination(ObservableList<Card> seletectedCardList) {
        return true;
    }

    /**
     * skip exchanging cards in the round
     *
     * @param actionEvent click this button
     */
    public void clickSkipCardsExchange(ActionEvent actionEvent) {
        btn_skipCardsExchange.setVisible(false);
        btn_confirmExchangeCards.setVisible(false);
        btn_confirmDeployment.setVisible(true);
    }
}

