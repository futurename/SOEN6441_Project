package riskgame.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import riskgame.Main;
import riskgame.classes.Player;
import riskgame.model.InfoRetriver;
import riskgame.model.ListviewRenderer;

import java.net.URL;
import java.util.ResourceBundle;

public class AttackviewController implements Initializable {

    @FXML
    private ListView lsv_adjacentCountries;
    @FXML
    private ListView lsv_ownedCountries;

    private int curPlayerIndex = Main.curRoundPlayerIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Player curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        initCountryListviewDisplay(curPlayer);

    }

    private void initAdjacentCountryListviewDisplay(int selectedCountryIndex) {

    }

    private void initCountryListviewDisplay(Player curPlayer) {
        lsv_ownedCountries.setItems(InfoRetriver.getPlayerCountryObservablelist(curPlayer));
        ListviewRenderer.getRenderedCountryItems(Main.curRoundPlayerIndex,lsv_ownedCountries);
    }

    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();

        System.out.println("#############selected country index: " + countryIndex);

        ObservableList datalist = InfoRetriver.getAdjacentCountryObservablelist(Main.curRoundPlayerIndex,countryIndex);
        lsv_adjacentCountries.setItems(datalist);

        ListviewRenderer.getRenderedCountryItems(Main.curRoundPlayerIndex,lsv_adjacentCountries);


    }


}
