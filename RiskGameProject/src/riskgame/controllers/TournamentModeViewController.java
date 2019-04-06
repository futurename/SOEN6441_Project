package riskgame.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import riskgame.model.BasicClass.StrategyPattern.*;
import riskgame.model.Utils.RobotGamingProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/29
 **/

public class TournamentModeViewController {


    private static ObservableList<String> mapShortNameList;
    private final String DEFAULT_MAPS_FOLDER_PATH = "maps/TournamentModeMaps/";
    private final int MAX_GAMES_TO_BE_PLAYED = 5;
    private final int MIN_GAME_ROUND = 10;
    private final int MAX_GAME_ROUND = 500;
    @FXML
    private ComboBox cbb_gamesCount;
    @FXML
    private ComboBox cbb_gameMaxRounds;
    @FXML
    private Button btn_confirmSetting;
    @FXML
    private ComboBox cbx_mapFileOne;
    @FXML
    private ComboBox cbx_mapFileTwo;
    @FXML
    private ComboBox cbx_mapFileThree;
    @FXML
    private ComboBox cbx_mapFileFour;
    @FXML
    private ComboBox cbx_mapFileFive;
    @FXML
    private CheckBox ckb_aggressivePlayer;
    @FXML
    private CheckBox ckb_benevolentPlayer;
    @FXML
    private CheckBox ckb_randomPlayer;
    @FXML
    private CheckBox ckb_cheaterPlayer;
    private ArrayList<Strategy> robotPlayerList = new ArrayList<>();
    private ArrayList<String> mapFullFileList;
    private int gamesValue;
    private int gameRoundValue;
    private ArrayList<String> selectedMapList;

    public void initialize() {
        mapFullFileList = getMapFiles(DEFAULT_MAPS_FOLDER_PATH);
        mapShortNameList = getShortFileNameList(mapFullFileList);
        setOnlyFirstComboxVisible(mapShortNameList);
        initGamesCombobox(MAX_GAMES_TO_BE_PLAYED);
        initMaxGameRoundCombobox(MIN_GAME_ROUND, MAX_GAME_ROUND);
        selectedMapList = new ArrayList<>();


        System.out.println("mapfile: " + mapFullFileList);
        System.out.println("short map files: " + mapShortNameList);
    }

    private void initMaxGameRoundCombobox(int min_game_round, int max_game_round) {
        ArrayList<Integer> gameRoundSequenceList = (ArrayList<Integer>) IntStream.rangeClosed(min_game_round, max_game_round)
                .boxed().collect(Collectors.toList());
        ObservableList<Integer> observableGameRoundSeqList = FXCollections.observableArrayList(gameRoundSequenceList);
        cbb_gameMaxRounds.setItems(observableGameRoundSeqList);

        cbb_gameMaxRounds.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                gameRoundValue = Integer.parseInt(newValue.toString());

                System.out.println("\ngameRoundValue: " + gameRoundValue + "\n");
            }
        });
    }

    private void initGamesCombobox(int max_games_to_be_played) {
        ArrayList<Integer> sequenceNumberList = (ArrayList<Integer>) IntStream.rangeClosed(1, max_games_to_be_played).boxed().collect(Collectors.toList());
        ObservableList<Integer> observableSequenceList = FXCollections.observableArrayList(sequenceNumberList);
        cbb_gamesCount.setItems(observableSequenceList);

        cbb_gamesCount.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                gamesValue = Integer.parseInt(newValue.toString());

                System.out.println("\ngameValue:: " + gamesValue + "\n");
            }
        });
    }

    private ObservableList<String> getShortFileNameList(ArrayList<String> mapFileList) {
        ArrayList<String> shortNameList = new ArrayList<>();
        for (String fileName : mapFileList) {
            String[] splitStrings = fileName.split("\\\\");
            String shortFileName = splitStrings[splitStrings.length - 1];
            shortNameList.add(shortFileName);
        }
        return FXCollections.observableArrayList(shortNameList);
    }

    private void setOnlyFirstComboxVisible(ObservableList<String> mapFileNameList) {

        ObservableList<String> comboboxOneList = FXCollections.observableArrayList(mapFileNameList);
        cbx_mapFileOne.setItems(comboboxOneList);
        cbx_mapFileTwo.setVisible(false);
        cbx_mapFileThree.setVisible(false);
        cbx_mapFileFour.setVisible(false);
        cbx_mapFileFive.setVisible(false);
    }


    private ArrayList<String> getMapFiles(String default_maps_folder_path) {
        File folder = new File(default_maps_folder_path);
        File[] files = folder.listFiles();

        ArrayList<String> result = new ArrayList<>();

        for (File file : files) {
            result.add(file.toString());
        }
        return result;
    }

    public void clickConfirmSetting(ActionEvent actionEvent) {
        if (isEnoughPlayerTypes() && isMapSelected() && isGamesSelected() && isRoundNumberSelected()) {
            Stage curStage = (Stage) btn_confirmSetting.getScene().getWindow();

            curStage.close();
            RobotGamingProcess.initRobotGaming(selectedMapList, robotPlayerList, gamesValue, gameRoundValue);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Error, selection not completed!");
            alert.showAndWait();
        }
    }

    private boolean isRoundNumberSelected() {
        return cbb_gameMaxRounds.getSelectionModel().getSelectedIndex() != -1;
    }

    private boolean isGamesSelected() {
        return cbb_gamesCount.getSelectionModel().getSelectedIndex() != -1;
    }

    private boolean isMapSelected() {
        return cbx_mapFileOne.getSelectionModel().getSelectedIndex() != -1;
    }

    private boolean isEnoughPlayerTypes() {
        int counter = 0;
        if (ckb_cheaterPlayer.isSelected()) {
            counter++;
        }
        if (ckb_randomPlayer.isSelected()) {
            counter++;
        }
        if (ckb_benevolentPlayer.isSelected()) {
            counter++;
        }
        if (ckb_aggressivePlayer.isSelected()) {
            counter++;
        }
        return counter >= 2;
    }

    public void selectMapFileOne(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileOne.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxOneList = cbx_mapFileOne.getItems();
        if (comboboxOneList.size() > 1) {
            ObservableList<String> comboboxTwoList = FXCollections.observableArrayList(comboboxOneList);
            comboboxTwoList.remove(selectedFileName);
            cbx_mapFileOne.setMouseTransparent(true);
            cbx_mapFileTwo.setItems(comboboxTwoList);
            cbx_mapFileTwo.setVisible(true);

            int mapFileIndex = cbx_mapFileOne.getSelectionModel().getSelectedIndex();
            String selectMapPath = comboboxOneList.get(mapFileIndex);
            String mapFullPath = getMapFullName(selectedFileName);
            selectedMapList.add(mapFullPath);

            System.out.println(selectMapPath);
        }
    }

    public void selectMapFileTwo(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileTwo.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxTwoList = cbx_mapFileTwo.getItems();
        if (comboboxTwoList.size() > 1) {
            ObservableList<String> comboboxThreeList = FXCollections.observableArrayList(comboboxTwoList);
            comboboxThreeList.remove(selectedFileName);
            cbx_mapFileTwo.setMouseTransparent(true);
            cbx_mapFileThree.setItems(comboboxThreeList);
            cbx_mapFileThree.setVisible(true);

            int mapFileIndex = cbx_mapFileTwo.getSelectionModel().getSelectedIndex();
            String selectMapPath = comboboxTwoList.get(mapFileIndex);
            String mapFullPath = getMapFullName(selectedFileName);
            selectedMapList.add(mapFullPath);

            System.out.println(selectMapPath);
        }
    }

    public void selectMapFileThree(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileThree.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxThreeList = cbx_mapFileThree.getItems();
        if (comboboxThreeList.size() > 1) {
            ObservableList<String> comboboxFourList = FXCollections.observableArrayList(comboboxThreeList);
            comboboxFourList.remove(selectedFileName);
            cbx_mapFileThree.setMouseTransparent(true);
            cbx_mapFileFour.setItems(comboboxFourList);
            cbx_mapFileFour.setVisible(true);

            int mapFileIndex = cbx_mapFileThree.getSelectionModel().getSelectedIndex();
            String selectMapPath = comboboxThreeList.get(mapFileIndex);
            String mapFullPath = getMapFullName(selectedFileName);
            selectedMapList.add(mapFullPath);

            System.out.println(selectMapPath);
        }
    }


    public void selectMapFileFour(ActionEvent actionEvent) {
        String selectedFileName = (String) cbx_mapFileFour.getSelectionModel().getSelectedItem();
        ObservableList<String> comboboxFourList = cbx_mapFileFour.getItems();
        if (comboboxFourList.size() > 1) {
            ObservableList<String> comboboxFiveList = FXCollections.observableArrayList(comboboxFourList);
            comboboxFiveList.remove(selectedFileName);
            cbx_mapFileFour.setMouseTransparent(true);
            cbx_mapFileFive.setItems(comboboxFiveList);
            cbx_mapFileFive.setVisible(true);

            int mapFileIndex = cbx_mapFileFour.getSelectionModel().getSelectedIndex();
            String selectMapPath = comboboxFourList.get(mapFileIndex);
            String mapFullPath = getMapFullName(selectedFileName);
            selectedMapList.add(mapFullPath);

            System.out.println(selectMapPath);
        }
    }


    public void selectMapFileFive(ActionEvent actionEvent) {
        cbx_mapFileFive.setMouseTransparent(true);

        String selectMapPath = (String) cbx_mapFileFive.getSelectionModel().getSelectedItem();
        String mapFullPath = getMapFullName(selectMapPath);
        selectedMapList.add(mapFullPath);

        System.out.println(selectMapPath);
    }


    private String getMapFullName(String selectedFileName) {
        String result = null;
        for (String fullName : mapFullFileList) {
            if (fullName.contains(selectedFileName)) {
                result = fullName;
            }
        }
        return result;
    }

    public void selectAggressivePlayer(ActionEvent actionEvent) {
        Strategy aggressivePlayer = new StrategyAggressive();
        robotPlayerList.add(aggressivePlayer);

    }

    public void selectbenevolent(ActionEvent actionEvent) {
        Strategy benevolentPlayer = new StrategyBenevolent();
        robotPlayerList.add(benevolentPlayer);
    }

    public void selectRandomPlayer(ActionEvent actionEvent) {
        Strategy randomPlayer = new StrategyRandom();
        robotPlayerList.add(randomPlayer);

    }

    public void selectCheaterPlayer(ActionEvent actionEvent) {
        Strategy cheaterPlayer = new StrategyCheater();
        robotPlayerList.add(cheaterPlayer);

    }

    public void clickResetSetting(ActionEvent actionEvent) {

        cbx_mapFileOne.getSelectionModel().clearSelection();
        cbx_mapFileTwo.getSelectionModel().clearSelection();
        cbx_mapFileThree.getSelectionModel().clearSelection();
        cbx_mapFileFour.getSelectionModel().clearSelection();
        cbx_mapFileFive.getSelectionModel().clearSelection();

        cbb_gameMaxRounds.getSelectionModel().clearSelection();
        cbb_gamesCount.getSelectionModel().clearSelection();

        cbx_mapFileOne.setMouseTransparent(false);
        cbx_mapFileTwo.setMouseTransparent(false);
        cbx_mapFileThree.setMouseTransparent(false);
        cbx_mapFileFour.setMouseTransparent(false);
        cbx_mapFileFive.setMouseTransparent(false);
        cbx_mapFileTwo.setVisible(false);
        cbx_mapFileThree.setVisible(false);
        cbx_mapFileFour.setVisible(false);
        cbx_mapFileFive.setVisible(false);

        ckb_cheaterPlayer.setSelected(false);
        ckb_randomPlayer.setSelected(false);
        ckb_benevolentPlayer.setSelected(false);
        ckb_aggressivePlayer.setSelected(false);


        selectedMapList.clear();
        robotPlayerList.clear();

    }
}
