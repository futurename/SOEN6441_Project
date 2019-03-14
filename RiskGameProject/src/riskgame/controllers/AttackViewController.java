package riskgame.controllers;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller class for AttackView.fxml
 **/
public class AttackViewController implements Initializable {

    @FXML
    private ListView lsv_adjacentCountries;
    @FXML
    private ListView lsv_ownedCountries;
    @FXML
    private Button btn_nextStep;
    @FXML
    private Button btn_confirmAttack;
    @FXML
    private Label lbl_phaseViewName;
    @FXML
    private Label lbl_playerName;
    @FXML
    private Label lbl_countries;
    @FXML
    private Label lbl_adjacentCountries;
    @FXML
    private ScrollBar scb_attackerArmyAdjust;
    @FXML
    private ScrollBar scb_defenderArmyAdjust;
    @FXML
    private Label lbl_attackerMaxArmyNbrPrompt;
    @FXML
    private Label lbl_attackerMaxArmyNbr;
    @FXML
    private Label lbl_attackerArmyPrompt;
    @FXML
    private Label lbl_attackerArmyNbr;
    @FXML
    private Label lbl_defenderMaxArmyPrompt;
    @FXML
    private Label lbl_defenderMaxArmyNbr;
    @FXML
    private Label lbl_defenderArmyPrompt;
    @FXML
    private Label lbl_defenderArmyNbr;
    @FXML
    private Label lbl_actionString;
    @FXML
    private Button btn_acceptArmySelection;
    @FXML
    private Button btn_alloutMode;


    /**
     * curent player index
     */
    private int curPlayerIndex;
    private Player curPlayer;
    private String curGamePhase;
    private String curPlayerName;
    private String curActionString;

    private final int MIN_ATTACKING_ARMY_NUMBER = 2;


    /**
     * Alert object
     */
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    /**
     * init method for attack phase view
     *
     * @param location  default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPhaseView();

        initCountryListviewDisplay(curPlayer);
    }

    private void initPhaseView() {
        initObserver();

        curPlayer = Main.playersList.get(curPlayerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();

        lbl_playerName.setText(curPlayerName);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_phaseViewName.setText(curGamePhase);
        lbl_actionString.setText(curActionString);
        lbl_actionString.setWrapText(true);

        lbl_countries.setTextFill(curPlayerColor);
        lbl_adjacentCountries.setTextFill(curPlayerColor);
        lbl_attackerArmyPrompt.setTextFill(curPlayerColor);
        lbl_attackerArmyNbr.setTextFill(curPlayerColor);

        initPlayerDominationView();
    }

    /**
     * initialize player domination information
     */
    private void initPlayerDominationView() {
    }

    private void initObserver() {
        curPlayerIndex = Main.phaseViewObserver.getPlayerIndex();
        curGamePhase = Main.phaseViewObserver.getPhaseName();
        curPlayerName = "Player_" + curPlayerIndex;
        curActionString = Main.phaseViewObservable.getActionString();
    }

    /**
     * display name and army number of countries the player owns
     *
     * @param curPlayer display all country names of the current player
     */
    private void initCountryListviewDisplay(Player curPlayer) {
        ObservableList<Country> ownedObservevableCountryList = InfoRetriver.getObservableCountryList(curPlayer);
        lsv_ownedCountries.setItems(ownedObservevableCountryList);
        ListviewRenderer.renderCountryItems(lsv_ownedCountries);
    }

    /**
     * onClick event when an attacking country item is selected
     *
     * @param mouseEvent mouse click on an owned country
     */
    @FXML
    public void selectAttackingCountry(MouseEvent mouseEvent) {
        Country selectedCountry = (Country) lsv_ownedCountries.getSelectionModel().getSelectedItem();

        int selectedArmyNbr = selectedCountry.getCountryArmyNumber();

        System.out.println("\nAttack phase, player: " + selectedCountry.getCountryOwnerIndex() + ", selected country: "
                + selectedCountry.getCountryName() + ", army nbr: " + selectedArmyNbr);

        if (selectedArmyNbr < MIN_ATTACKING_ARMY_NUMBER) {
            alert.setContentText("No enough army for attacking!");
            alert.showAndWait();
        } else {
            ObservableList<Country> datalist = InfoRetriver.getAttackableAdjacentCountryList(this.curPlayerIndex, selectedCountry);

            lsv_adjacentCountries.setItems(datalist);
            ListviewRenderer.renderCountryItems(lsv_adjacentCountries);

            scb_attackerArmyAdjust.setMax(selectedArmyNbr);
            scb_attackerArmyAdjust.setMin(MIN_ATTACKING_ARMY_NUMBER);
            scb_attackerArmyAdjust.adjustValue(selectedArmyNbr);
            lbl_attackerArmyNbr.setText(Integer.toString(selectedArmyNbr));
            lbl_attackerMaxArmyNbr.setText(Integer.toString(selectedArmyNbr));

            scb_attackerArmyAdjust.valueProperty()
                    .addListener((observable, oldValue, newValue)
                            -> lbl_attackerArmyNbr.setText(Integer.toString(newValue.intValue())));
        }
    }

    /**
     * onClick event when a defending country item is selected
     *
     * @param mouseEvent mouse click on an empty country
     */
    public void selectDefendingCountry(MouseEvent mouseEvent) {
        if (selectedItemNotEmpty(lsv_adjacentCountries)) {
            Country selectedDefenderCountry = (Country) lsv_adjacentCountries.getSelectionModel().getSelectedItem();
            Player defenderPlayer = Main.playersList.get(selectedDefenderCountry.getCountryOwnerIndex());
            Color defenderColor = defenderPlayer.getPlayerColor();
            int defenderArmyNbr = selectedDefenderCountry.getCountryArmyNumber();

            lbl_defenderMaxArmyPrompt.setVisible(true);
            lbl_defenderMaxArmyNbr.setVisible(true);
            lbl_defenderArmyPrompt.setVisible(true);
            lbl_defenderArmyNbr.setVisible(true);

            lbl_defenderArmyPrompt.setTextFill(defenderColor);
            lbl_defenderArmyNbr.setTextFill(defenderColor);

            lbl_defenderMaxArmyNbr.setText(Integer.toString(defenderArmyNbr));
            lbl_defenderArmyNbr.setText(Integer.toString(defenderArmyNbr));
        }
    }

    /**
     * Check whether the selected item contains country object
     *
     * @return true for not empty, false for empty selection
     */
    private boolean selectedItemNotEmpty(ListView listVIew) {
        boolean result = false;

        if (listVIew.getSelectionModel().getSelectedItem().equals(null)) {
            alert.setContentText("Please select a country!");
            alert.showAndWait();
        } else {
            result = true;
        }
        return result;
    }

    /**
     * onClick event for moving to fortification phase of the game
     *
     * @param actionEvent button is clicked
     * @throws IOException FotificationView.fxml is not found
     */
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        notifyGamePhaseChanged();
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Pane fortificationPane = new FXMLLoader(getClass().getResource("../view/FortificationView.fxml")).load();
        Scene fortificationScene = new Scene(fortificationPane, 1200, 900);

        curStage.setScene(fortificationScene);
        curStage.show();
    }

    /**
     * onClick event for confirming attack
     *
     * @param actionEvent button clicked
     */
    public void clickAttack(ActionEvent actionEvent) {
        Country attackingCountry = (Country) lsv_ownedCountries
                .getSelectionModel()
                .getSelectedItem();

        Country defendingCountry = (Country) lsv_adjacentCountries
                .getSelectionModel()
                .getSelectedItem();

        int attackArmyNbr = Integer.parseInt(lbl_attackerArmyNbr.getText());
        int defendArmyNbr = Integer.parseInt(lbl_defenderArmyNbr.getText());

        curPlayer.attckCountry(attackingCountry, defendingCountry, attackArmyNbr,defendArmyNbr);

        System.out.println("!!!!!!!!!!!attacking!!!!!!!!!!!!!!!!");

    }

    private void notifyGamePhaseChanged() {
        Main.phaseViewObservable.setAllParam("Fortification Phase", curPlayerIndex, "select one owned country from left and another country of the " +
                "right as target");
        Main.phaseViewObservable.notifyObservers(Main.phaseViewObservable);

        System.out.printf("player %s finished attack, player %s's turn\n", curPlayerIndex, curPlayerIndex);
    }

    private void notifyCardChanged() {
        Main.playersList.get(curPlayerIndex).setObservableCard(Card.ARTILLERY);
        Main.playersList.get(curPlayerIndex).notifyObservers("from attack view: get a new card");
    }

    /**
     * Use all-out mode for attacking, it will result in either attacker wins or defender wins
     *
     * @param actionEvent mouse click
     */
    public void clickAllOutMode(ActionEvent actionEvent) {
        if (isBothCountriesSelected()) {
            btn_confirmAttack.setVisible(false);
            btn_acceptArmySelection.setVisible(false);

            Country selectedAttackerCountry = (Country) lsv_ownedCountries.getSelectionModel().getSelectedItem();
            Country selectedDefenderCountry = (Country) lsv_adjacentCountries.getSelectionModel().getSelectedItem();
            int attackingArmyNumber = selectedAttackerCountry.getCountryArmyNumber();
            int defendingArmyNumber = selectedDefenderCountry.getCountryArmyNumber();

            curPlayer.attckCountry(selectedAttackerCountry, selectedDefenderCountry, attackingArmyNumber, defendingArmyNumber);

            System.out.println("\nSelect ALL-OUT mode for attacking!!!!");
        }
    }

    /**
     * check whether attacking and defending countries are selected first, then secure the selected army numbers, set \"Attack\" button visible
     *
     * @param actionEvent mouse click
     */
    public void clickAcceptArmySelection(ActionEvent actionEvent) {
        if (isBothCountriesSelected()) {
            scb_attackerArmyAdjust.setVisible(false);
            scb_defenderArmyAdjust.setVisible(false);

            btn_acceptArmySelection.setVisible(false);
            btn_confirmAttack.setVisible(true);

            lbl_attackerMaxArmyNbrPrompt.setVisible(false);
            lbl_attackerMaxArmyNbr.setVisible(false);

            lbl_defenderMaxArmyPrompt.setVisible(false);
            lbl_defenderMaxArmyNbr.setVisible(false);


            System.out.println("\nAccept army selection!!!!!!!!!");
        }
    }

    /**
     * check whether both attacking and defending countries are selected
     *
     * @return true for both countries are selected, false for not satisfied
     */
    private boolean isBothCountriesSelected() {
        boolean result = false;

        int selectedAttackerCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int selectedDefenderCountryIndex = lsv_adjacentCountries.getSelectionModel().getSelectedIndex();

        if (selectedAttackerCountryIndex == -1) {
            alert.setContentText("Please select an attacking country!");
            alert.showAndWait();
        } else if (selectedDefenderCountryIndex == -1) {
            alert.setContentText("Please select a defending country!");
            alert.showAndWait();
        } else {
            result = true;
        }
        return result;
    }


}
