package riskgame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import riskgame.model.BasicClass.GameRunningResult;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/02
 **/

public class TournamentModeResultViewController implements Initializable {

    @FXML
    private TextArea txa_gameInitInfo;
    @FXML
    private GridPane grp_gameResult;

    private BlockingQueue<Future<GameRunningResult>> resultBlockingQueue;
    private int gamesValue;
    private int gameRoundValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateFullGripPane(gamesValue, gameRoundValue);
    }

    private void generateFullGripPane(int gamesValue, int gameRoundValue) {

        grp_gameResult.add(new TextField("good"), 3, 4);

        for (int row = 1; row <= gamesValue; row++) {
            for (int column = 1; column <= gameRoundValue; column++) {

                grp_gameResult.add(new Label("T"), column, row);
            }
        }
    }


    public BlockingQueue<Future<GameRunningResult>> getResultBlockingQueue() {
        return resultBlockingQueue;
    }

    public void setResultBlockingQueue(BlockingQueue<Future<GameRunningResult>> resultBlockingQueue) {
        this.resultBlockingQueue = resultBlockingQueue;
    }

    public int getGamesValue() {
        return gamesValue;
    }

    public void setGamesValue(int gamesValue) {
        this.gamesValue = gamesValue;
    }

    public int getGameRoundValue() {
        return gameRoundValue;
    }

    public void setGameRoundValue(int gameRoundValue) {
        this.gameRoundValue = gameRoundValue;
    }
}
