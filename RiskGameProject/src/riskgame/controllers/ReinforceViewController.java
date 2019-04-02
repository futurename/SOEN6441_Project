package riskgame.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.UtilMethods;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static riskgame.Main.*;
import static riskgame.controllers.StartViewController.reinforceInitCounter;

/**
 * controller class for ReinforceView.fxml
 *
 * @author WW, Zhanfan
 * @since build1
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
    @FXML
    private Button btn_saveGame;
    /**
     * current player in this phase
     */
    private int curPlayerIndex;
    private String curGamePhase;
    private String curActionString;
    private String curPlayerName;
    private Player curPlayer;
    private CardExchangeViewObserver cardExchangeViewObserver;
    private ArrayList<Card> playerCards;

    private Alert alert = new Alert(Alert.AlertType.WARNING);


    /**
     * selected cards list will be validated with game rules
     *
     * @param selectedCardList selected cards arraylist
     * @return true for correct combination, false for incorrect combination
     */
    public static boolean validateCardsCombination(ObservableList<Card> selectedCardList) {
        if (selectedCardList.size() != 3) {
            //set true for testing
            return false;
        } else {
            int sum = 0;
            for (Card card : selectedCardList) {
                sum += card.ordinal() + 1;
            }
            return sum == 3 || sum == 6 || sum == 9;
        }
    }

    /**
     * init method for reinforce phase view
     *
     * @param location  default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reinforceViewInit();
        curPlayer.executeReinforcement();
        setUIInitStatus();
    }

    /**
     * init UI controls and corresponding varaibles
     * <p>
     * param playerIndex initialize UI controls and display information of current player
     */
    private void reinforceViewInit() {
        initPhaseView();
        initCurPlayerCardListView();
        UtilMethods.getNewArmyPerRound(curPlayer);
        InfoRetriver.updateDominationView("From reinforcement initial", vbx_worldDomiView);
    }

    /**
     * set UI init status
     */
    private void setUIInitStatus() {
        btn_confirmDeployment.setVisible(false);

        int cardsNbr = curPlayer.getCardsList().size();

        System.out.println("\n\ncards number: " + cardsNbr);

        if (cardsNbr <= 2) {
            btn_confirmExchangeCards.setVisible(false);
            btn_skipCardsExchange.setVisible(false);
            btn_confirmDeployment.setVisible(true);
        }
        if (cardsNbr >= 5) {
            btn_skipCardsExchange.setVisible(false);
        }

        Color curPlayerColor = curPlayer.getPlayerColor();
        int undeployed = curPlayer.getUndeployedArmy();
        lbl_countriesInfo.setTextFill(curPlayerColor);
        lbl_adjacentCountriesInfo.setTextFill(curPlayerColor);
        lbl_undeployedArmy.setText(Integer.toString(undeployed));
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));

        System.out.println("country display index: " + curPlayer.getPlayerIndex());

        ListviewRenderer.renderCountryItems(lsv_ownedCountries);

        scb_armyNbrAdjustment.setMax(undeployed);
        scb_armyNbrAdjustment.setMin(1);
        scb_armyNbrAdjustment.adjustValue(undeployed);
        lbl_deployArmyCount.setText(Integer.toString(undeployed));

        scb_armyNbrAdjustment.valueProperty().addListener((observable, oldValue, newValue) -> lbl_deployArmyCount.setText(Integer.toString(newValue.intValue())));
    }

    /**
     * init card obserser pattern
     */
    private void initCurPlayerCardListView() {
        initObserver("CardView");
        ArrayList<Card> cardsList = playerCards;
        cardsList.sort(Collections.reverseOrder());
        ObservableList<Card> cardObservableList = FXCollections.observableList(cardsList);
        lsv_cardsListView.setItems(cardObservableList);
        ListviewRenderer.renderCardsListView(lsv_cardsListView, curPlayer);
        lsv_cardsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /**
     * set contents to phase view labels
     * contents are obtained via phaseViewObserver
     */
    private void initPhaseView() {
        initObserver("PhaseView");
        curPlayer = playersList.get(curPlayerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();
        lbl_phaseViewName.setText(curGamePhase);
        lbl_phaseViewName.setTextFill(curPlayerColor);
        lbl_playerName.setText(curPlayerName);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_actionString.setText(curActionString);
        lbl_actionString.setWrapText(true);
    }

    /**
     * retrieving newest data from observer
     * it will be called every time the view initializes
     *
     * @param whichObs observer name, two available so far
     */
    private void initObserver(String whichObs) {
        switch (whichObs) {
            case "PhaseView":
                curGamePhase = phaseViewObserver.getPhaseName();
                curPlayerIndex = phaseViewObserver.getPlayerIndex();
                curPlayerName = "Player_" + curPlayerIndex;
                curActionString = phaseViewObserver.getActionString();
                break;
            case "CardView":
                this.cardExchangeViewObserver = UtilMethods.initCardObserver(curPlayer);
                break;
        }
    }

    /**
     * onClick event for moving to next player if reinforcement phase is not finished or attack view from the first player
     *
     * Removing the cardExchangeViewObserver first,
     * if not, phaseViewObservable will keep adding new cardObserver without removing the old one!
     * cardObserver is not a static observer.
     * @param actionEvent next player's turn or proceed to attackView if all players finish reinforcement
     */
    @FXML
    public void clickNextStep(ActionEvent actionEvent) {
        UtilMethods.deregisterCardObserver(curPlayer, cardExchangeViewObserver);
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        System.out.println("\n???????????????????????????" + reinforceInitCounter);

        if (reinforceInitCounter > 1) {
            UtilMethods.notifyReinforcementEnd(false, curPlayer);
            reinforceInitCounter--;
        } else {
            UtilMethods.notifyReinforcementEnd(true, curPlayer);
        }
        //if not robot phase, method does nothing
        UtilMethods.callNextRobotPhase();
        Scene scene = UtilMethods.startView(phaseViewObserver.getPhaseName(), this);
        curStage.setScene(scene);
        curStage.show();
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

        if (selectedCountryIndex == -1) {
            alert.setHeaderText(null);
            alert.setContentText("Please select a country for army deployment");
            alert.showAndWait();
        } else {
            int deployArmyCount = Integer.parseInt(lbl_deployArmyCount.getText());
            curPlayer.executeReinforcement(selectedCountry, deployArmyCount);
            int remainUndeployedArmyCount = curPlayer.getUndeployedArmy();

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
                lsv_ownedCountries.getSelectionModel().select(-1);
            } else {
                scb_armyNbrAdjustment.setMax(remainUndeployedArmyCount);
                scb_armyNbrAdjustment.adjustValue(remainUndeployedArmyCount);
                lbl_deployArmyCount.setText(Integer.toString(remainUndeployedArmyCount));
            }

            System.out.println("\nfinish deployment to country " + selectedCountry.getCountryName() + ": " + selectedCountry.getCountryArmyNumber());
        }
        InfoRetriver.updateDominationView("deploy new army", vbx_worldDomiView);
    }

    /**
     * click button for confirming exchange cards
     *
     * @param actionEvent click this button
     */
    public void clickExchangeCards(ActionEvent actionEvent) {
        ObservableList<Card> selectedCardList =
                FXCollections.observableArrayList(lsv_cardsListView.getSelectionModel().getSelectedItems());

        System.out.println("selected cards: " + selectedCardList);

        if (selectedCardList.isEmpty()) {
            alert.setContentText("No card selected!");
            alert.showAndWait();
        } else if (validateCardsCombination(selectedCardList)) {
            int exchangedArmyNbr = UtilMethods.getExchangedArmy(cardExchangeViewObserver.getExchangeTime());
            int undeployed = UtilMethods.addUndeployedArmyAfterExchanging(curPlayer, exchangedArmyNbr);

            System.out.printf("GET NEW %d ARMY!\n", exchangedArmyNbr);

            removeCardsFromList(selectedCardList);

            lsv_cardsListView.refresh();
            lsv_cardsListView.getSelectionModel().select(-1);


            lbl_undeployedArmy.setText(Integer.toString(undeployed));
            scb_armyNbrAdjustment.setMax(undeployed);
            scb_armyNbrAdjustment.adjustValue(undeployed);
            lbl_deployArmyCount.setText(Integer.toString(undeployed));

            btn_confirmDeployment.setVisible(true);
            btn_skipCardsExchange.setVisible(false);
            btn_confirmExchangeCards.setVisible(false);

            phaseViewObservable.addOneExchangeTime();
            phaseViewObservable.notifyObservers("Add Exchange Time");

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
        curPlayer.removeObservableCards(selectedCardList);
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

    public void clickSaveGame(ActionEvent actionEvent) {
        String titleString = "Select Location to Save Game:";
        InfoRetriver.showFileChooser(titleString);
    }

    public void clickLoadGame(ActionEvent actionEvent) {
        String titleString = "Select Saved Map File:";
        InfoRetriver.showFileChooser(titleString);
    }
}

