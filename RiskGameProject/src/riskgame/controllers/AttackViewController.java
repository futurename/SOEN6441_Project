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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.UtilMethods;
import riskgame.model.Utils.AttackProcess;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static riskgame.Main.phaseViewObserver;

/**
 * controller class for AttackView.fxml
 *
 * @author WW, Zhanfan, Karamveer
 * @since build1
 **/
public class AttackViewController implements Initializable {

    public static final int MIN_ATTACKING_ARMY_NUMBER = 1;
    public static final int MAX_ATTACKING_ARMY_NUMBER = 3;
    public static final int MAX_DEFENDING_ARMY_NUMBER = 2;
    public static final int MIN_DEFENDING_ARMY_NUMBER = 1;
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
    private Button btn_alloutMode;
    @FXML
    private TextArea txa_attackInfoDisplay;
    @FXML
    private VBox vbx_worldDomiView;
    @FXML
    private Button btn_saveGame;

    /**
     * curent player index
     */
    private int curPlayerIndex;
    private Player curPlayer;
    private String curGamePhase;
    private String curPlayerName;
    private String curActionString;
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
        InfoRetriver.updateDominationView("From attackView init", vbx_worldDomiView);
        initCountryListviewDisplay(curPlayer);
        validateExistAttackableCountry();
    }

    /**
     * valide whether there exists attackable country
     */
    private void validateExistAttackableCountry() {
        boolean isOneCountryCanAttack = InfoRetriver.validateAttackerStatus(curPlayer, lsv_ownedCountries.getItems());
        if (!isOneCountryCanAttack) {
            setPhaseFinish();
        }
    }

    private void initPhaseView() {
        initObserver();

        curPlayer = Main.playersList.get(curPlayerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();
        curPlayer.setCardPermission(false);

        lbl_playerName.setText(curPlayerName);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_phaseViewName.setText(curGamePhase);
        lbl_phaseViewName.setTextFill(curPlayerColor);
        lbl_actionString.setText(curActionString);
        lbl_actionString.setWrapText(true);

        lbl_countries.setTextFill(curPlayerColor);
        lbl_adjacentCountries.setTextFill(curPlayerColor);
        lbl_attackerArmyPrompt.setTextFill(curPlayerColor);
        lbl_attackerArmyNbr.setTextFill(curPlayerColor);

        txa_attackInfoDisplay.setEditable(false);

    }

    /**
     * init Observer object
     */
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
        lsv_adjacentCountries.getSelectionModel().select(-1);

        Country selectedCountry = (Country) lsv_ownedCountries.getSelectionModel().getSelectedItem();

        int selectedArmyNbr = selectedCountry.getCountryArmyNumber();

        System.out.println("\nAttack phase, player: " + selectedCountry.getOwnerIndex() + ", selected country: "
                + selectedCountry.getCountryName() + ", army nbr: " + selectedArmyNbr);

        ObservableList<Country> datalist = InfoRetriver.getAttackableAdjacentCountryList(curPlayer, selectedCountry);

        lsv_adjacentCountries.setItems(datalist);
        ListviewRenderer.renderCountryItems(lsv_adjacentCountries);

        updateAttackerArmyAdjustment(selectedCountry);

        if (selectedArmyNbr <= MIN_ATTACKING_ARMY_NUMBER) {
            alert.setContentText("No enough army for attacking!");
            alert.showAndWait();
            btn_alloutMode.setVisible(false);
            btn_confirmAttack.setVisible(false);
        } else {
            btn_confirmAttack.setVisible(true);
            btn_alloutMode.setVisible(true);
        }

    }

    /**
     * Update army number information when select a country for attacking
     *
     * @param selectedAttackCountry the country selected for attacking
     */
    private void updateAttackerArmyAdjustment(Country selectedAttackCountry) {
        int attackableArmyNbr = selectedAttackCountry.getCountryArmyNumber() - 1;
        int attackArmyNbr = attackableArmyNbr > MAX_ATTACKING_ARMY_NUMBER ? MAX_ATTACKING_ARMY_NUMBER : attackableArmyNbr;

        scb_attackerArmyAdjust.setMax(attackArmyNbr);
        scb_attackerArmyAdjust.setMin(MIN_ATTACKING_ARMY_NUMBER);
        scb_attackerArmyAdjust.adjustValue(attackArmyNbr);
        lbl_attackerArmyNbr.setText(Integer.toString(attackArmyNbr));
        lbl_attackerMaxArmyNbr.setText(Integer.toString(attackArmyNbr));

        scb_attackerArmyAdjust.valueProperty()
                .addListener((observable, oldValue, newValue)
                        -> lbl_attackerArmyNbr.setText(Integer.toString(newValue.intValue())));
    }

    /**
     * onClick event when a defending country item is selected
     *
     * @param mouseEvent mouse click on an empty country
     */
    public void selectDefendingCountry(MouseEvent mouseEvent) {
        if (!isSelectedItemEmpty(lsv_adjacentCountries)) {
            Country selectedDefenderCountry = (Country) lsv_adjacentCountries.getSelectionModel().getSelectedItem();
//            Player defenderPlayer = Main.playersList.get(selectedDefenderCountry.getCountryOwnerIndex());
            Player defenderPlayer = selectedDefenderCountry.getOwner();
            Color defenderColor = defenderPlayer.getPlayerColor();

            lbl_defenderMaxArmyPrompt.setVisible(true);
            lbl_defenderMaxArmyNbr.setVisible(true);
            lbl_defenderArmyPrompt.setVisible(true);
            lbl_defenderArmyNbr.setVisible(true);
            scb_defenderArmyAdjust.setVisible(true);

            lbl_defenderArmyPrompt.setTextFill(defenderColor);
            lbl_defenderArmyNbr.setTextFill(defenderColor);

            updateDefenderArmyAdjustment(selectedDefenderCountry);

            System.out.println("select defend country: " + selectedDefenderCountry.getCountryName()
                    + ", army num: " + selectedDefenderCountry.getCountryArmyNumber());
        }
    }

    /**
     * update defending country inforamtion when defending country is selected
     *
     * @param selectedDefenderCountry the country selected for defending
     */
    private void updateDefenderArmyAdjustment(Country selectedDefenderCountry) {
        int defenderArmyNbr = selectedDefenderCountry.getCountryArmyNumber();
        int defendArmyNbr = defenderArmyNbr > MAX_DEFENDING_ARMY_NUMBER ? MAX_DEFENDING_ARMY_NUMBER : defenderArmyNbr;

        scb_defenderArmyAdjust.setMax(defendArmyNbr);
        scb_defenderArmyAdjust.setMin(MIN_DEFENDING_ARMY_NUMBER);
        scb_defenderArmyAdjust.adjustValue(defendArmyNbr);
        lbl_defenderMaxArmyNbr.setText(Integer.toString(defendArmyNbr));
        lbl_defenderArmyNbr.setText(Integer.toString(defendArmyNbr));
        scb_defenderArmyAdjust.valueProperty().addListener((observable, oldValue, newValue)
                -> lbl_defenderArmyNbr.setText(Integer.toString(newValue.intValue())));
    }

    /**
     * Check whether the selected item contains country object
     *
     * @param listVIew the listview of displaying country information
     * @return true for not empty, false for empty selection
     */
    private boolean isSelectedItemEmpty(ListView listVIew) {
        boolean result = false;

        if (listVIew.getSelectionModel().getSelectedItem().equals(null)) {
            result = true;
            alert.setContentText("Please select a country!");
            alert.showAndWait();
        }
        return result;
    }

    /**
     * onClick event for moving to fortification phase of the game
     *
     * @param actionEvent button is clicked
     */
    public void clickNextStep(ActionEvent actionEvent) {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UtilMethods.endAttack(curPlayer);
        Scene scene = UtilMethods.startView(phaseViewObserver.getPhaseName(), this);
        curStage.setScene(scene);
        curStage.show();
    }

    /**
     * onClick event for confirming attack
     *
     * @param actionEvent button clicked
     * @throws IOException gameover view file not found
     */
    public void clickAttack(ActionEvent actionEvent) throws IOException {
        if (isBothCountriesSelected()) {
            Country attackingCountry = (Country) lsv_ownedCountries
                    .getSelectionModel()
                    .getSelectedItem();
            //TODO zhezhong fangshi de hua ke yi qu diao 2 ge param, tong li allout mode
            Player attacker = attackingCountry.getOwner();

            Country defendingCountry = (Country) lsv_adjacentCountries
                    .getSelectionModel()
                    .getSelectedItem();
            Player defender = defendingCountry.getOwner();

            int attackArmyNbr = Integer.parseInt(lbl_attackerArmyNbr.getText());
            int defendArmyNbr = Integer.parseInt(lbl_defenderArmyNbr.getText());

            String battleReport = curPlayer.executeAttack(attackingCountry, attacker, defendingCountry, defender, attackArmyNbr,
                    defendArmyNbr, false);
            txa_attackInfoDisplay.setText(battleReport);

            refreshListView(attackingCountry);
            InfoRetriver.updateDominationView("from attack view attack", vbx_worldDomiView);

            if (AttackProcess.winnerPlayerIndex != -1) {
                callGameOverView();
            } else {
                validateExistAttackableCountry();
            }
        }
    }

    /**
     * Current phase finishes, set buttons status.
     */
    private void setPhaseFinish() {
        btn_nextStep.setVisible(true);
        btn_confirmAttack.setVisible(false);
        btn_alloutMode.setVisible(false);
        lsv_ownedCountries.getSelectionModel().select(-1);
        lsv_adjacentCountries.setItems(null);
        lsv_ownedCountries.setMouseTransparent(true);
        lsv_adjacentCountries.setMouseTransparent(true);
    }


    /**
     * refresh data display in Listviews
     *
     * @param attackingCountry the country for attacking
     */
    private void refreshListView(Country attackingCountry) {
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));
        lsv_ownedCountries.refresh();
        lsv_adjacentCountries.setItems(InfoRetriver.getAttackableAdjacentCountryList(curPlayer, attackingCountry));
        lsv_adjacentCountries.refresh();

        resetArmyAdjustment();
        lsv_ownedCountries.getSelectionModel().select(-1);
        lsv_adjacentCountries.getSelectionModel().select(-1);
    }

    /**
     * reset army number to default value
     */
    private void resetArmyAdjustment() {
        lbl_defenderMaxArmyNbr.setText("0");
        lbl_defenderArmyNbr.setText("0");
        lbl_attackerArmyNbr.setText("0");
        lbl_attackerMaxArmyNbr.setText("0");
    }

    /**
     * Use all-out mode for attacking, it will result in either attacker wins or defender wins
     *
     * @param actionEvent mouse click
     * @throws IOException next view file not found
     */
    public void clickAllOutMode(ActionEvent actionEvent) throws IOException {
        if (isBothCountriesSelected()) {
            Country selectedAttackerCountry = (Country) lsv_ownedCountries.getSelectionModel().getSelectedItem();
            Country selectedDefenderCountry = (Country) lsv_adjacentCountries.getSelectionModel().getSelectedItem();
            //TODO
            Player attacker = selectedAttackerCountry.getOwner();
            Player defender = selectedDefenderCountry.getOwner();

            int availableForAttackNbr = selectedAttackerCountry.getCountryArmyNumber() - 1;
            int availableForDefendNbr = selectedDefenderCountry.getCountryArmyNumber();

            String battleReport = curPlayer.executeAttack(selectedAttackerCountry, attacker, selectedDefenderCountry,
                    defender, availableForAttackNbr, availableForDefendNbr, true);
            txa_attackInfoDisplay.setText(battleReport);
            refreshListView(selectedAttackerCountry);
            InfoRetriver.updateDominationView("from attack all out mode", vbx_worldDomiView);

            if (AttackProcess.winnerPlayerIndex != -1) {
                callGameOverView();
            } else {
                validateExistAttackableCountry();
            }
        }
    }

    /**
     * call game over view
     *
     * @throws IOException FinalView.fxml not found
     */
    private void callGameOverView() throws IOException {
        Stage curStage = (Stage) txa_attackInfoDisplay.getScene().getWindow();

        Pane finalPane = new FXMLLoader(getClass().getResource("../view/FinalView.fxml")).load();
        Scene finalScene = new Scene(finalPane, 1200, 900);
        curStage.setScene(finalScene);
        curStage.show();
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


    public void clickSaveGame(ActionEvent actionEvent) {
        String titleString = "Select Location to Save Game:";
        InfoRetriver.showFileChooser(titleString);
    }

    public void clickLoadGame(ActionEvent actionEvent) {
        String titleString = "Select Saved Map File:";
        InfoRetriver.showFileChooser(titleString);
    }
}
