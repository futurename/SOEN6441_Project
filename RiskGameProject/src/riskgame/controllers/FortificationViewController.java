package riskgame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.Main;

import java.io.IOException;

/**
 * @program: RiskGameProject
 * @description:
 * @author: WW
 * @date: 2019-02-20
 **/

public class FortificationViewController {

    @FXML
    private Label lbl_playerInfo;
    @FXML
    private Label lbl_maxArmyNumber;
    @FXML
    private Label lbl_deployArmyNumber;
    @FXML
    private ScrollBar scb_armyNbrAdjustment;
    @FXML
    private ListView lsv_ownedCountries;
    @FXML
    private ListView lsv_adjacentCountries;

    public void initialize(){
        String playerInfo = "Player: " + Integer.toString(Main.curRoundPlayerIndex);
        lbl_playerInfo.setText(playerInfo);
    }

    public void selectOneCountry(MouseEvent mouseEvent) {
    }

    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        Main.curRoundPlayerIndex = (Main.curRoundPlayerIndex + 1) % Main.totalNumOfPlayers;

        System.out.println("one round finished: " + Main.curRoundPlayerIndex);

        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/AttackView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane, 1200, 900);

        curStage.setScene(reinforceScene);
        curStage.show();

    }

    public void clickConfirmDeployment(ActionEvent actionEvent) {
    }
}
