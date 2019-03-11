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
    private Button btn_finishAttack;
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
    private ScrollBar scb_armyNbrAdjustment;
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


    /**
     * curent player index
     */
    private int curPlayerIndex;
    private Player curPlayer;
    private String curGamePhase;

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
        String playerInfo = "Player: " + curPlayerIndex;
        Color curPlayerColor = curPlayer.getPlayerColor();
        lbl_playerName.setText(playerInfo);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_countries.setTextFill(curPlayerColor);
        lbl_adjacentCountries.setTextFill(curPlayerColor);
        lbl_phaseViewName.setText(curGamePhase);
    }

    private void initObserver() {
        curPlayerIndex = Main.phaseViewObserver.getPlayerIndex();
        curGamePhase = Main.phaseViewObserver.getPhaseName();
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
     * onClick event when a country item is selected from country ListView
     *
     * @param mouseEvent display its adjacent countries of the selected country
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
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

            scb_armyNbrAdjustment.setMax(selectedArmyNbr);
            scb_armyNbrAdjustment.setMin(MIN_ATTACKING_ARMY_NUMBER);
            scb_armyNbrAdjustment.adjustValue(selectedArmyNbr);
            lbl_attackerArmyNbr.setText(Integer.toString(selectedArmyNbr));
            lbl_attackerMaxArmyNbr.setText(Integer.toString(selectedArmyNbr));

            scb_armyNbrAdjustment.valueProperty()
                    .addListener((observable, oldValue, newValue)
                            -> lbl_attackerArmyNbr.setText(Integer.toString(newValue.intValue())));
        }
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
        int defendingCountryIndex = lsv_adjacentCountries
                .getSelectionModel()
                .getSelectedIndex();

        if (defendingCountryIndex == -1) {
            alert.setContentText("Please select an adjacent country to attack!");
            alert.showAndWait();
        } else {
            Player attacker = Main.playersList.get(curPlayerIndex);

            Country attackingCountry = (Country) lsv_ownedCountries
                    .getSelectionModel()
                    .getSelectedItem();

            Country defendingCountry = (Country) lsv_adjacentCountries
                    .getSelectionModel()
                    .getSelectedItem();

            if (attackingCountry.getCountryArmyNumber() < 2) {
                alert.setContentText("No enough army for attacking! Please select another country!");
                alert.showAndWait();
            } else {
                int attackArmyNbr = Integer.parseInt(lbl_attackerArmyNbr.getText());

                attacker.attckCountry(attackingCountry, defendingCountry, attackArmyNbr);

                System.out.println("!!!!!!!!!!!attacking!!!!!!!!!!!!!!!!");
            }
        }
    }

    /**
     * Click the button to finish this round of attack.
     *
     * @param actionEvent onClick event
     */
    public void clickFinishAttack(ActionEvent actionEvent) {
        //for testing card observer pattern TODO
        notifyCardChanged();
        btn_confirmAttack.setVisible(false);
        btn_finishAttack.setVisible(false);
        btn_nextStep.setVisible(true);
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

    public void clickAllOutMode(ActionEvent actionEvent) {
    }

    public void clickAcceptArmySelection(ActionEvent actionEvent) {
    }
}
