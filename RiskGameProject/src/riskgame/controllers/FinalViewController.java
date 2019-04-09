package riskgame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InitWorldMap;

import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<Player> playerArrayList;
    private int totalRounds;


    public void display() {
        StringBuilder stringBuilder = new StringBuilder();
        if (winner.getPlayerIndex() >= playerArrayList.size()) {
            stringBuilder.append("GAME OVER!")
                    .append("\n\n\n")
                    .append(("Total Rounds: <"))
                    .append(totalRounds)
                    .append(">\n\n")
                    .append("<")
                    .append(winner.getPlayerName())
                    .append("> WINS!");
        } else {
            stringBuilder.append("GAME OVER!")
                    .append("\n\n\n")
                    .append("Player <")
                    .append(winner.getPlayerIndex())
                    .append(">, Name: <")
                    .append(winner.getPlayerName())
                    .append("> WINS!");
        }
        txa_gameOverInfo.setText(stringBuilder.toString());

        System.out.println("\n\nfollwoing graph is in finalviewcontroller: " + winner.getPlayerName() + " in PlayerList" + playerArrayList + "\n\n");

        InitWorldMap.printGraph(winner.getWorldMapInstance(), playerArrayList);
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("final winner in final phase: " + winner.getPlayerName());
        display();
    }


    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }
}
