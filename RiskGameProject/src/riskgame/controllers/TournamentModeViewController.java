package riskgame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/29
 **/

public class TournamentModeViewController {

    @FXML
    private ChoiceBox chb_maxRounds;
    @FXML
    private ChoiceBox chb_gamesRounds;
    @FXML
    private Button btn_confirmSetting;
    @FXML
    private TableColumn<String, String> tbc_playerTypeColumn;
    @FXML
    private TableColumn<String, String> tbc_mapSlectionColumn;

    public void initialize(){

    }

    public void clickGameRounds(MouseEvent mouseEvent) {
    }

    public void clickMaxRounds(MouseEvent mouseEvent) {
    }

    public void clickConfirmSetting(ActionEvent actionEvent) {
        Stage curStage = (Stage)btn_confirmSetting.getScene().getWindow();
        curStage.close();
    }
}
