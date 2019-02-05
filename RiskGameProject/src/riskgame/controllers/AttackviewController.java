package riskgame.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import riskgame.Main;
import riskgame.classes.Player;
import riskgame.model.PlayerInfoRetriver;

import java.net.URL;
import java.util.ResourceBundle;

public class AttackviewController implements Initializable {

    @FXML
    private ListView lsv_adjacentCountries;
    @FXML
    private ListView lsv_ownedCountries;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Player curPlayer = Main.playersList.get(Main.curRoundPlayerIndex);
        initCountryListviewDisplay(curPlayer);

    }

    private void initAdjacentCountryListviewDisplay(int selectedCountryIndex) {

    }

    private void initCountryListviewDisplay(Player curPlayer) {
        lsv_ownedCountries.setItems(PlayerInfoRetriver.getPlayerCountryObservablelist(curPlayer));
    }

    public void selectOneCountry(MouseEvent mouseEvent) {
        int countryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        ObservableList datalist = PlayerInfoRetriver.getAdjacentCountryObservablelist(countryIndex);
        lsv_adjacentCountries.setItems(datalist);


        lsv_adjacentCountries.setCellFactory(cell -> {
            return new ListCell<String>() {
                private Text text;

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        String curString = item.toString();
                        String[] curStringSplitArray = curString.split(" ");
                        int playerIndex;
                        if(curString.toLowerCase().contains("player")) {
                            playerIndex = Integer.parseInt(curStringSplitArray[1]);
                        }else{
                            playerIndex = Main.curRoundPlayerIndex;
                        }

                        System.out.println("rendering, player index: " + playerIndex);

                        Color curPlayerColor = Main.playersList.get(playerIndex).getPlayerColor();
                        System.out.println(curPlayerColor.toString());

                        text = new Text(item);
                        text.setFill(curPlayerColor);
                        setGraphic(text);

                    }
                }
            };
        });


    }
}
