package riskgame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import riskgame.model.BasicClass.GameRunningResult;
import riskgame.model.BasicClass.StrategyPattern.Strategy;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    @FXML
    private Button btn_end;

    //private BlockingQueue<Future<GameRunningResult>> resultBlockingQueue;
    private int gamesValue;
    private int gameRoundValue;
    private ArrayList<String> mapFileList;
    private ArrayList<Strategy> strategyArrayList;

    public TournamentModeResultViewController(int gamesValue, int gameRoundValue) {
        this.gamesValue = gamesValue;
        this.gameRoundValue = gameRoundValue;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateFullGripPane(gamesValue, gameRoundValue);
    }

    private void generateFullGripPane(int gamesValue, int gameRoundValue) {
        generateTitleInfo(gamesValue, gameRoundValue);

        grp_gameResult.setHgap(30);
        grp_gameResult.setVgap(30);

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
    }

    private void generateTitleInfo(int gamesValue, int gameRoundValue) {
        ArrayList<String> shortFileNameList = getShortFileNameList(mapFileList);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("M: ");

        for (int mapIndex = 0; mapIndex < shortFileNameList.size(); mapIndex++) {
            stringBuilder.append("<").append(shortFileNameList.get(mapIndex)).append("> ");
        }

        stringBuilder.append("\nP: ");

        for (int index = 0; index < strategyArrayList.size(); index++) {
            stringBuilder.append("<").append(strategyArrayList.get(index)).append(">  ");
        }

        stringBuilder.append("\nG: ").append(gamesValue);
        stringBuilder.append("\nD: ").append(gameRoundValue);

        txa_gameInitInfo.setText(stringBuilder.toString());
    }

    public void setOneGameResult(Future<GameRunningResult> oneGameResult) {
        GameRunningResult curResult = null;
        try {
            curResult = oneGameResult.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        int curMapIndex = curResult.getMapIndex();
        int curGameValue = curResult.getGameValue();
        String curWinner = curResult.getWinnerName();
        Label curLabel = new Label(curWinner);
        grp_gameResult.add(curLabel, curGameValue + 1, curMapIndex + 1);
    }

    private void generateGridFramework(ArrayList<String> shortMapFileNameList, ArrayList<String> gameValueList) {
        for (int mapIndex = 0; mapIndex < shortMapFileNameList.size(); mapIndex++) {
            String oneMapName = shortMapFileNameList.get(mapIndex);
            Label oneMapLabel = new Label(oneMapName);

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


    public ArrayList<String> getMapFileList() {
        return mapFileList;
    }

    @FXML
    public void clickEnd(ActionEvent actionEvent) {
        Stage curStage = (Stage) btn_end.getScene().getWindow();
        curStage.close();
        System.exit(0);
    }

    private ArrayList<String> getShortFileNameList(ArrayList<String> mapFileList) {
        ArrayList<String> shortNameList = new ArrayList<>();
        for (String fileName : mapFileList) {
            String[] splitStrings = fileName.split("\\\\");
            String shortFileName = splitStrings[splitStrings.length - 1];
            shortNameList.add(shortFileName);
        }
        return shortNameList;
    }

    public void setMapFileList(ArrayList<String> mapFileList) {
        this.mapFileList = mapFileList;
    }

    public void setStrategyArrayList(ArrayList<Strategy> strategyArrayList) {
        this.strategyArrayList = strategyArrayList;
    }
}
