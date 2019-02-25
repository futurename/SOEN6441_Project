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
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller class for AttackView.fxml
 *
 * @author WW
 */
public class AttackViewController implements Initializable {

    @FXML
    private ListView lsv_adjacentCountries;
    @FXML
    private ListView lsv_ownedCountries;
    @FXML
    private Button btn_nextStep;
    @FXML
    private Label lbl_playerInfo;

    //private int curPlayerIndex = Main.curRoundPlayerIndex;

    /**
     * @param location default value
     * @param resources default value
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Player curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        String playerInfo = "Player: " + Integer.toString(Main.curRoundPlayerIndex);
        lbl_playerInfo.setText(playerInfo);
        initCountryListviewDisplay(curPlayer);

    }

    /**
     * @param curPlayer display all country names of the current player
     */
    private void initCountryListviewDisplay(Player curPlayer) {
        lsv_ownedCountries.setItems(InfoRetriver.getPlayerCountryObservablelist(curPlayer));
        ListviewRenderer.getRenderedCountryItems(Main.curRoundPlayerIndex,lsv_ownedCountries);
    }

    /**
     * @param mouseEvent display its adjacent countries of the selected country
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();

        System.out.println("#############selected country index: " + countryIndex);

        ObservableList datalist = InfoRetriver.getAdjacentCountryObservablelist(Main.curRoundPlayerIndex,countryIndex);
        lsv_adjacentCountries.setItems(datalist);

        ListviewRenderer.getRenderedCountryItems(Main.curRoundPlayerIndex,lsv_adjacentCountries);
    }

    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane fortificationPane = new FXMLLoader(getClass().getResource("../view/FortificationView.fxml")).load();
        Scene fortificationScene = new Scene(fortificationPane, 1200, 900);

        curStage.setScene(fortificationScene);

        curStage.show();
    }
}
