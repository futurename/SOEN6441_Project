package riskgame.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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


    /**
     * curent player index
     */
    private int curPlayerIndex;

    private String curGamePhase;

    /**
     * Alert object
     */
    private Alert alert;

    /**
     * init method for attack phase view
     *
     * @param location  default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initObserver();

        Player curPlayer = Main.playersList.get(curPlayerIndex);
        String playerInfo = "Player: " + curPlayerIndex;
        lbl_playerName.setText(playerInfo);
        lbl_phaseViewName.setText(curGamePhase);
        alert = new Alert(Alert.AlertType.WARNING);

        initCountryListviewDisplay(curPlayer);
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
        int countryIndex = lsv_ownedCountries
                .getSelectionModel()
                .getSelectedIndex();

        System.out.println("#############selected country index: " + countryIndex + ", " + lsv_ownedCountries.getSelectionModel().getSelectedItem());

        ObservableList<Country> datalist = InfoRetriver.getAttackableAdjacentCountryList(this.curPlayerIndex, countryIndex);

        lsv_adjacentCountries.setItems(datalist);
        ListviewRenderer.renderCountryItems(lsv_adjacentCountries);
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
                attacker.attckCountry(attackingCountry, defendingCountry);

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

    private void initObserver(){
        curPlayerIndex = Main.phaseViewObserver.getPlayerIndex();
        curGamePhase = Main.phaseViewObserver.getPhaseName();
    }

    private void notifyGamePhaseChanged(){
        Main.phaseViewObservable.setAllParam("Fortification Phase", curPlayerIndex, "no action");
        Main.phaseViewObservable.notifyObservers("from attack view");

        System.out.printf("player %s finished attack, player %s's turn\n", curPlayerIndex, curPlayerIndex);
    }

    private void notifyCardChanged(){
        Main.playersList.get(curPlayerIndex).setObservableCard(Card.ARTILLERY);
        Main.playersList.get(curPlayerIndex).notifyObservers("from attack view: get a new card");
    }
}
