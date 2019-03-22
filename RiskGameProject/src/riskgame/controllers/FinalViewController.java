package riskgame.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import riskgame.model.Utils.AttackProcess;

/**
 * created on 2019/03/17_23:04
 **/

public class FinalViewController {
    @FXML
    private TextArea txa_gameOverInfo;

    public void initialize(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("GAME OVER!")
                .append("\n\n\n")
                .append("Player <")
                .append(AttackProcess.winnerPlayerIndex)
                .append("> WINS!");

        txa_gameOverInfo.setText(stringBuilder.toString());
    }
}
