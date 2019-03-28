package riskgame.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.Main;
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
    private Label lbl_playerInfo;

    /**
     * init method for attack phase view
     *
     * @param location  default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Player curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        String playerInfo = "Player: " + Main.curRoundPlayerIndex;
        lbl_playerInfo.setText(playerInfo);
        initCountryListviewDisplay(curPlayer);

    }

    /**
     * display name and army number of countries the player owns
     *
     * @param curPlayer display all country names of the current player
     */
    private void initCountryListviewDisplay(Player curPlayer) {
        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));
        ListviewRenderer.renderCountryItems(lsv_ownedCountries);
    }

    /**
     * onClick event when a country item is selected from country ListView
     *
     * @param mouseEvent display its adjacent countries of the selected country
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();

        System.out.println("#############selected country index: " + countryIndex);

        ObservableList<Country> datalist = InfoRetriver.getAdjacentCountryObservablelist(Main.curRoundPlayerIndex, countryIndex);
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
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane fortificationPane = new FXMLLoader(getClass().getResource("../view/FortificationView.fxml")).load();
        Scene fortificationScene = new Scene(fortificationPane, 1200, 900);

        curStage.setScene(fortificationScene);

        curStage.show();
    }

    /**
     * onClick event for confirmting attack
     *
     * @param actionEvent button clicked
     */
    public void clickConfirmAttack(ActionEvent actionEvent) {
    }
}
