package riskgame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import riskgame.model.BasicClass.GameRunningResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
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
    private ArrayList<String> mapFileList;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateFullGripPane(gamesValue, gameRoundValue);
    }

    public TournamentModeResultViewController(BlockingQueue<Future<GameRunningResult>> resultBlockingQueue, int gamesValue, int gameRoundValue) {
        this.resultBlockingQueue = resultBlockingQueue;
        this.gamesValue = gamesValue;
        this.gameRoundValue = gameRoundValue;
    }

    private void generateFullGripPane(int gamesValue, int gameRoundValue) {

        grp_gameResult.setHgap(40);
        grp_gameResult.setVgap(40);

        ArrayList<String> shortMapFileNameList = new ArrayList<>();
        for (String mapFullName : mapFileList) {
            String shortMapName = getShortMapName(mapFullName);
            shortMapFileNameList.add(shortMapName);
        }

        ArrayList<String> gameValueList = new ArrayList<>();
        for (int i = 1; i <= gamesValue; i++) {
            String oneGameValue = "Game " + i;
            gameValueList.add(oneGameValue);
        }

        generateGridFramework(shortMapFileNameList, gameValueList);


        System.out.println("in final view: " + getResultBlockingQueue());

        for (Future<GameRunningResult> future : resultBlockingQueue) {
            GameRunningResult curResult = null;
            try {
                curResult = future.get();

                System.out.println(curResult);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            String curMapFileName = curResult.getMapFileName();
            int curMapIndex = curResult.getMapIndex();
            String curGameName = getShortMapName(curResult.getGameName());
            String curWinnerName = curResult.getWinnerName();
            int curGameValue = curResult.getGameValue();

            Label curLabel = new Label(curWinnerName);
            //grp_gameResult.add(curLabel, curMapIndex, curGameValue);
        }


    }

    private void generateGridFramework(ArrayList<String> shortMapFileNameList, ArrayList<String> gameValueList) {
        for (int mapIndex = 0; mapIndex < shortMapFileNameList.size(); mapIndex++) {
            String oneMapName = shortMapFileNameList.get(mapIndex);
            Label oneMapLabel = new Label(oneMapName);
            oneMapLabel.setStyle("../view/css/labelfortitle.css");
            grp_gameResult.add(oneMapLabel, 0, mapIndex + 1);
        }

        for (int gameIndex = 0; gameIndex < gameValueList.size(); gameIndex++) {
            String oneGameName = gameValueList.get(gameIndex);
            Label oneGameValueLabel = new Label(oneGameName);
            grp_gameResult.add(oneGameValueLabel, gameIndex + 1, 0);
        }
    }

    private String getShortMapName(String gameName) {
        String[] splitStrings = gameName.split("\\\\");
        String mapFullName = splitStrings[splitStrings.length - 1];
        return mapFullName;
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

    public ArrayList<String> getMapFileList() {
        return mapFileList;
    }

    public void setMapFileList(ArrayList<String> mapFileList) {
        this.mapFileList = mapFileList;
    }
}
