package riskgame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import riskgame.Main;
import riskgame.model.BasicClass.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * created on 2019/03/17_23:04
 *
 * @author WW
 * @since build2
 **/
public class FinalViewController implements Initializable {
    @FXML
    private TextArea txa_gameOverInfo;

    private Player winner;


    public void initialize() {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("GAME OVER!")
                .append("\n\n\n")
                .append("Player <")
                .append(winner.getPlayerIndex())
                .append(">, Name: <")
                .append(winner.getPlayerName())
                .append("> WINS!");

/*stringBuilder.append("GAME OVER!")
        .append("\n\n\n")
        .append(winner)
        .append(" WINS!");*/

        txa_gameOverInfo.setText(stringBuilder.toString());
    }

    private void setWinner(Player winner) {
        this.winner = winner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWinner(Main.playersList.get(Main.phaseViewObserver.getPlayerIndex()));
        initialize();
    }
}
