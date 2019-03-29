package riskgame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox cbb_gamesCount;
    @FXML
    private ComboBox cbb_gameMaxRounds;
    @FXML
    private Button btn_confirmSetting;
    @FXML
    private TableColumn<String, String> tbc_playerTypeColumn;
    @FXML
    private TableColumn<String, String> tbc_mapSlectionColumn;

    public void initialize(){

    }


    public void clickConfirmSetting(ActionEvent actionEvent) {
        Stage curStage = (Stage)btn_confirmSetting.getScene().getWindow();
        curStage.close();
    }

    public void clickGamesCount(MouseEvent mouseEvent) {
    }

    public void clickGameMaxRounds(MouseEvent mouseEvent) {
    }
}
